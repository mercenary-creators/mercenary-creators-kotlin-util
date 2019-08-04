/*
 * Copyright (c) 2019, Mercenary Creators Company. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.mercenary.creators.kotlin.util.security

import co.mercenary.creators.kotlin.util.Throwables
import java.io.*
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.*
import kotlin.math.max

object Ciphers {

    @JvmStatic
    @JvmOverloads
    fun text(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherEncrypting<String, String> {
        return text(SecretKeys.getSecret(pass, salt, algorithm), algorithm)
    }

    @JvmStatic
    @JvmOverloads
    fun text(secret: SecretKey, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherEncrypting<String, String> {
        val proxy = data(secret, algorithm)
        return object : CipherEncrypting<String, String> {
            override fun decrypt(data: String) = String(proxy.decrypt(Encoders.hex().decode(data)), Charsets.UTF_8)
            override fun encrypt(data: String) = Encoders.hex().encode(proxy.encrypt(data.toByteArray(Charsets.UTF_8)))
        }
    }

    @JvmStatic
    @JvmOverloads
    fun data(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherEncrypting<ByteArray, ByteArray> {
        return data(SecretKeys.getSecret(pass, salt, algorithm), algorithm)
    }

    @JvmStatic
    @JvmOverloads
    fun data(secret: SecretKey, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherEncrypting<ByteArray, ByteArray> {
        return InternalEncryptingData(algorithm, getCipher(algorithm), getCipher(algorithm), secret, SimpleCipherKeysFactory(algorithm))
    }

    @JvmStatic
    @JvmOverloads
    fun copy(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherCopyStreams {
        return copy(SecretKeys.getSecret(pass, salt, algorithm), algorithm)
    }

    @JvmStatic
    @JvmOverloads
    fun copy(secret: SecretKey, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherCopyStreams {
        return InternalEncryptingCopy(algorithm, getCipher(algorithm), getCipher(algorithm), secret, SimpleCipherKeysFactory(algorithm))
    }

    @JvmStatic
    fun getAlgorithms(): Algorithm = Algorithms.getAlgorithmForName("Cipher")

    internal fun getBufferOf(data: InputStream): Int {
        try {
            return max(data.available(), DEFAULT_BUFFER_SIZE)
        }
        catch (cause: Throwable) {
            Throwables.assert(cause)
        }
        return DEFAULT_BUFFER_SIZE
    }

    internal fun getCipher(algorithm: CipherAlgorithm): Cipher = Cipher.getInstance(algorithm.getCipherTransform())

    internal fun setCypher(cipher: Cipher, mode: Int, secret: SecretKey, parameter: AlgorithmParameterSpec): Cipher = cipher.also { it.init(mode, secret, parameter) }

    internal fun getParams(algorithm: CipherAlgorithm, vector: ByteArray): AlgorithmParameterSpec = algorithm.getAlgorithmParams(vector)

    private class InternalEncryptingData(private val algorithm: CipherAlgorithm, private val encrypt: Cipher, private val decrypt: Cipher, private val secret: SecretKey, private val factory: CipherKeysFactory) : CipherEncrypting<ByteArray, ByteArray> {

        override fun encrypt(data: ByteArray): ByteArray = synchronized(encrypt) {
            factory.getKeys().let { vector -> vector + setCypher(encrypt, Cipher.ENCRYPT_MODE, secret, getParams(algorithm, vector)).doFinal(data) }
        }

        override fun decrypt(data: ByteArray): ByteArray = synchronized(decrypt) {
            data.copyOfRange(0, factory.getSize()).let { vector -> setCypher(decrypt, Cipher.DECRYPT_MODE, secret, getParams(algorithm, vector)).doFinal(data.copyOfRange(vector.size, data.size)) }
        }
    }

    private class InternalEncryptingCopy(private val algorithm: CipherAlgorithm, private val encrypt: Cipher, private val decrypt: Cipher, private val secret: SecretKey, private val factory: CipherKeysFactory) : CipherCopyStreams {

        override fun encrypt(data: InputStream, copy: OutputStream) = synchronized(encrypt) {
            val buffer = getBufferOf(data)
            val vector = factory.getKeys().also { copy.write(it) }
            val output = FastCipherOutputStream(copy.buffered(buffer), setCypher(encrypt, Cipher.ENCRYPT_MODE, secret, getParams(algorithm, vector)))
            data.copyTo(output)
            output.close()
        }

        override fun decrypt(data: InputStream, copy: OutputStream) = synchronized(decrypt) {
            val buffer = getBufferOf(data)
            val vector = ByteArray(factory.getSize()).also { data.read(it) }
            val output = FastCipherOutputStream(copy.buffered(buffer), setCypher(decrypt, Cipher.DECRYPT_MODE, secret, getParams(algorithm, vector)))
            data.copyTo(output)
            output.close()
        }
    }

    private class FastCipherOutputStream(private val proxy: OutputStream, private val cipher: Cipher) : OutputStream() {
        private var obuf: ByteArray? = null
        private val sbuf: ByteArray = ByteArray(1)
        override fun write(b: Int) {
            sbuf[0] = b.toByte()
            obuf = cipher.update(sbuf, 0, 1)
            update()
        }

        override fun write(b: ByteArray) {
            write(b, 0, b.size)
        }

        override fun write(b: ByteArray, off: Int, len: Int) {
            obuf = cipher.update(b, off, len)
            update()
        }

        private fun update() {
            if (obuf != null) {
                proxy.write(obuf!!)
                obuf = null
            }
        }

        override fun flush() {
            update()
            proxy.flush()
        }

        override fun close() {
            try {
                obuf = cipher.doFinal()
            }
            catch (cause: Throwable) {
                obuf = null
                Throwables.assert(cause)
            }
            flush()
        }
    }
}
