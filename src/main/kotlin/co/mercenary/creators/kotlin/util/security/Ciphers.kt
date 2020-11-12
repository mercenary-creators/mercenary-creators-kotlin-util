/*
 * Copyright (c) 2020, Mercenary Creators Company. All rights reserved.
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

import co.mercenary.creators.kotlin.util.*
import java.io.*
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.*

@IgnoreForSerialize
object Ciphers : HasMapNames {

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun text(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherEncrypting<String, String> {
        return text(SecretKeys.getSecret(pass, salt, algorithm), algorithm)
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun text(secret: SecretKey, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherEncrypting<String, String> {
        val proxy = data(secret, algorithm)
        return object : CipherEncrypting<String, String> {
            override fun decrypt(data: String) = proxy.decrypt(Encoders.hex().decode(data)).getContentText()
            override fun encrypt(data: String) = Encoders.hex().encode(proxy.encrypt(data.getContentData()))
        }
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun data(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherEncrypting<ByteArray, ByteArray> {
        return data(SecretKeys.getSecret(pass, salt, algorithm), algorithm)
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun data(secret: SecretKey, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherEncrypting<ByteArray, ByteArray> {
        return InternalEncryptingData(algorithm, getCipher(algorithm), getCipher(algorithm), secret, SimpleCipherKeysFactory(algorithm))
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun copy(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherCopyStreams {
        return copy(SecretKeys.getSecret(pass, salt, algorithm), algorithm)
    }

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun copy(secret: SecretKey, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherCopyStreams {
        return InternalEncryptingCopy(algorithm, getCipher(algorithm), getCipher(algorithm), secret, SimpleCipherKeysFactory(algorithm))
    }

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun getAlgorithms(): Algorithm = Algorithm.forName("Cipher")

    @CreatorsDsl
    override fun toString() = nameOf()

    @CreatorsDsl
    override fun toMapNames() = dictOf("type" to nameOf(), "ciphers" to getAlgorithms())

    @CreatorsDsl
    private fun getCipher(algorithm: CipherAlgorithm): Cipher = Cipher.getInstance(algorithm.getCipherTransform())

    @CreatorsDsl
    private fun setCypher(cipher: Cipher, mode: Int, secret: SecretKey, parameter: AlgorithmParameterSpec): Cipher = cipher.also { it.init(mode, secret, parameter) }

    @CreatorsDsl
    private fun getParams(algorithm: CipherAlgorithm, vector: ByteArray): AlgorithmParameterSpec = algorithm.getAlgorithmParams(vector)

    @IgnoreForSerialize
    private class InternalEncryptingData @CreatorsDsl constructor(private val algorithm: CipherAlgorithm, private val encrypt: Cipher, private val decrypt: Cipher, private val secret: SecretKey, private val factory: CipherKeysFactory) : CipherEncrypting<ByteArray, ByteArray> {

        @CreatorsDsl
        override fun encrypt(data: ByteArray): ByteArray = synchronized(encrypt) {
            factory.getKeys().let { vector -> vector + setCypher(encrypt, Cipher.ENCRYPT_MODE, secret, getParams(algorithm, vector)).doFinal(data) }
        }

        @CreatorsDsl
        override fun decrypt(data: ByteArray): ByteArray = synchronized(decrypt) {
            data.copyOfRange(0, factory.getSize()).let { vector -> setCypher(decrypt, Cipher.DECRYPT_MODE, secret, getParams(algorithm, vector)).doFinal(data.copyOfRange(vector.size, data.size)) }
        }
    }

    @IgnoreForSerialize
    private class InternalEncryptingCopy @CreatorsDsl constructor(private val algorithm: CipherAlgorithm, private val encrypt: Cipher, private val decrypt: Cipher, private val secret: SecretKey, private val factory: CipherKeysFactory) : CipherCopyStreams {

        @CreatorsDsl
        override fun encrypt(data: InputStream, copy: OutputStream) = synchronized(encrypt) {
            val buffer = data.getBufferSize()
            val vector = factory.getKeys().also { copy.write(it) }
            val output = FastCipherOutputStream(copy, setCypher(encrypt, Cipher.ENCRYPT_MODE, secret, getParams(algorithm, vector)))
            data.copyTo(output, buffer)
            output.close()
        }

        @CreatorsDsl
        override fun decrypt(data: InputStream, copy: OutputStream) = synchronized(decrypt) {
            val buffer = data.getBufferSize()
            val vector = ByteArray(factory.getSize()).also { data.read(it) }
            val output = FastCipherOutputStream(copy, setCypher(decrypt, Cipher.DECRYPT_MODE, secret, getParams(algorithm, vector)))
            data.copyTo(output, buffer)
            output.close()
        }
    }

    @IgnoreForSerialize
    private class FastCipherOutputStream @CreatorsDsl constructor(private val proxy: OutputStream, private val cipher: Cipher) : OutputStream() {

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

        @CreatorsDsl
        private fun update() {
            if (obuf != null) {
                proxy.write(obuf!!)
                obuf = null
            }
        }

        @CreatorsDsl
        override fun flush() {
            update()
            proxy.flush()
        }

        @CreatorsDsl
        override fun close() {
            obuf = try {
                cipher.doFinal()
            }
            catch (cause: Throwable) {
                null
            }
            flush()
        }
    }
}
