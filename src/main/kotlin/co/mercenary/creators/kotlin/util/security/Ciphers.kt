/*
 * Copyright (c) 2022, Mercenary Creators Company. All rights reserved.
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
    @FrameworkDsl
    @JvmOverloads
    fun text(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherEncrypting<String, String> {
        return text(SecretKeys.getSecret(pass, salt, algorithm), algorithm)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun text(secret: SecretKey, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherEncrypting<String, String> {
        val proxy = data(secret, algorithm)
        return object : CipherEncrypting<String, String> {
            override fun decrypt(data: String) = proxy.decrypt(Encoders.hex().decode(data)).getContentText()
            override fun encrypt(data: String) = Encoders.hex().encode(proxy.encrypt(data.getContentData()))
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun data(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherEncrypting<ByteArray, ByteArray> {
        return data(SecretKeys.getSecret(pass, salt, algorithm), algorithm)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun data(secret: SecretKey, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherEncrypting<ByteArray, ByteArray> {
        return InternalEncryptingData(algorithm, getCipher(algorithm), getCipher(algorithm), secret, SimpleCipherKeysFactory(algorithm))
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun copy(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherCopyStreams {
        return copy(SecretKeys.getSecret(pass, salt, algorithm), algorithm)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun copy(secret: SecretKey, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherCopyStreams {
        return InternalEncryptingCopy(algorithm, getCipher(algorithm), getCipher(algorithm), secret, SimpleCipherKeysFactory(algorithm))
    }

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getAlgorithms(): Algorithm = Algorithm.forName("Cipher")

    @JvmStatic
    @FrameworkDsl
    fun getMaxKeySize(algorithm: String): Int {
        return try {
            Cipher.getMaxAllowedKeyLength(algorithm)
        } catch (cause: Throwable) {
            Throwables.fatal(cause, IS_NOT_FOUND)
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun getMaxKeySize(algorithm: CipherAlgorithm): Int {
        return getMaxKeySize(algorithm.getCipherTransform())
    }

    @JvmStatic
    @FrameworkDsl
    fun getMaxAlgorithmParameterSpec(algorithm: String): AlgorithmParameterSpec? {
        return try {
            Cipher.getMaxAllowedParameterSpec(algorithm)
        } catch (cause: Throwable) {
            Throwables.fatal(cause, null)
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun getMaxAlgorithmParameterSpec(algorithm: CipherAlgorithm): AlgorithmParameterSpec? {
        return getMaxAlgorithmParameterSpec(algorithm.getCipherTransform())
    }

    @FrameworkDsl
    override fun toString() = nameOf()

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to nameOf(), "ciphers" to getAlgorithms())

    @FrameworkDsl
    private fun getCipher(algorithm: CipherAlgorithm): Cipher = Cipher.getInstance(algorithm.getCipherTransform())

    @FrameworkDsl
    private fun setCypher(cipher: Cipher, mode: Int, secret: SecretKey, parameter: AlgorithmParameterSpec): Cipher = cipher.also { it.init(mode, secret, parameter) }

    @FrameworkDsl
    private fun getParams(algorithm: CipherAlgorithm, vector: ByteArray): AlgorithmParameterSpec = algorithm.getAlgorithmParams(vector)

    @IgnoreForSerialize
    private class InternalEncryptingData @FrameworkDsl constructor(private val algorithm: CipherAlgorithm, private val encrypt: Cipher, private val decrypt: Cipher, private val secret: SecretKey, private val factory: CipherKeysFactory) : CipherEncrypting<ByteArray, ByteArray> {

        @FrameworkDsl
        override fun encrypt(data: ByteArray): ByteArray = locked(encrypt) {
            factory.getKeys().let { vector -> vector + setCypher(encrypt, Cipher.ENCRYPT_MODE, secret, getParams(algorithm, vector)).doFinal(data) }
        }

        @FrameworkDsl
        override fun decrypt(data: ByteArray): ByteArray = locked(decrypt) {
            data.copyOfRange(0, factory.getSize()).let { vector -> setCypher(decrypt, Cipher.DECRYPT_MODE, secret, getParams(algorithm, vector)).doFinal(data.copyOfRange(vector.sizeOf(), data.sizeOf())) }
        }
    }

    @IgnoreForSerialize
    private class InternalEncryptingCopy @FrameworkDsl constructor(private val algorithm: CipherAlgorithm, private val encrypt: Cipher, private val decrypt: Cipher, private val secret: SecretKey, private val factory: CipherKeysFactory) : CipherCopyStreams {
        @FrameworkDsl
        private fun factory() = factory
        @FrameworkDsl
        override fun encrypt(data: InputStream, copy: OutputStream) = locked(encrypt) {
            val buffer = data.getBufferSize()
            val vector = factory().getKeys().also { copy.write(it) }
            val output = FastCipherOutputStream(copy, setCypher(encrypt, Cipher.ENCRYPT_MODE, secret, getParams(algorithm, vector)))
            data.copyTo(output, buffer)
            output.close()
        }

        @FrameworkDsl
        override fun decrypt(data: InputStream, copy: OutputStream) = locked(decrypt) {
            val buffer = data.getBufferSize()
            val vector = ByteArray(factory().getSize()).also { data.read(it) }
            val output = FastCipherOutputStream(copy, setCypher(decrypt, Cipher.DECRYPT_MODE, secret, getParams(algorithm, vector)))
            data.copyTo(output, buffer)
            output.close()
        }
    }

    @FrameworkDsl
    private fun ByteArray.push(b: Int): ByteArray {
        this[0] = b.byteOf()
        return this
    }

    @IgnoreForSerialize
    private class FastCipherOutputStream @FrameworkDsl constructor(private val proxy: OutputStream, private val cipher: Cipher) : OutputStream(), OpenCloseState {

        @FrameworkDsl
        private var obuf: ByteArray? = null

        @FrameworkDsl
        private val sbuf: ByteArray = 1.toByteArray()

        @FrameworkDsl
        private val open = getAtomicTrue()

        @FrameworkDsl
        override fun write(b: Int) {
            obuf = cipher.update(sbuf.push(b), 0, 1)
            update()
        }

        @FrameworkDsl
        override fun write(b: ByteArray) {
            write(b, 0, b.sizeOf())
        }

        @FrameworkDsl
        override fun write(b: ByteArray, off: Int, len: Int) {
            obuf = cipher.update(b, off, len)
            update()
        }

        @FrameworkDsl
        private fun update() {
            if (obuf != null) {
                proxy.write(obuf!!)
                obuf = null
            }
        }

        @FrameworkDsl
        override fun flush() {
            update()
            proxy.flush()
        }

        @FrameworkDsl
        override fun close() {
            if (open.isTrueToFalse()) {
                obuf = try {
                    cipher.doFinal()
                } catch (cause: Throwable) {
                    null
                }
            }
            flush()
            proxy.close()
        }

        @FrameworkDsl
        override fun isOpen(): Boolean {
            return open.isTrue()
        }
    }
}
