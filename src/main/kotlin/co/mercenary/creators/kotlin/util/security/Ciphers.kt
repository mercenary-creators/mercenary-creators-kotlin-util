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

import java.io.*
import java.security.SecureRandom
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.*

object Ciphers {

    @JvmStatic
    @JvmOverloads
    fun text(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherEncrypting<String, String> {
        return text(SecretKeys.getSecret(pass, salt), algorithm)
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
        return data(SecretKeys.getSecret(pass, salt), algorithm)
    }

    @JvmStatic
    @JvmOverloads
    fun data(secret: SecretKey, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherEncrypting<ByteArray, ByteArray> {
        return InternalEncryptingData(algorithm, getCipher(algorithm), getCipher(algorithm), secret, InternalCipherKeysFactory(16, SecureRandom()))
    }

    @JvmStatic
    @JvmOverloads
    fun copy(pass: CharSequence, salt: CharSequence, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherCopyStreams {
        return copy(SecretKeys.getSecret(pass, salt), algorithm)
    }

    @JvmStatic
    @JvmOverloads
    fun copy(secret: SecretKey, algorithm: CipherAlgorithm = CipherAlgorithm.CBC): CipherCopyStreams {
        return InternalEncryptingCopy(algorithm, getCipher(algorithm), getCipher(algorithm), secret, InternalCipherKeysFactory(16, SecureRandom()))
    }

    @JvmStatic
    fun getAlgorithms() = Algorithms.getAlgorithmForName("Cipher")

    internal fun getCipher(algorithm: CipherAlgorithm): Cipher = Cipher.getInstance(algorithm.getCipherTransform())

    internal fun setCypher(cipher: Cipher, mode: Int, secret: SecretKey, parameter: AlgorithmParameterSpec): Cipher = cipher.also { it.init(mode, secret, parameter) }

    internal fun getParams(algorithm: CipherAlgorithm, vector: ByteArray) = algorithm.getAlgorithmParams(vector)

    internal class InternalEncryptingData(private val algorithm: CipherAlgorithm, private val encrypt: Cipher, private val decrypt: Cipher, private val secret: SecretKey, private val factory: CipherKeysFactory) : CipherEncrypting<ByteArray, ByteArray> {

        override fun encrypt(data: ByteArray): ByteArray = synchronized(encrypt) {
            factory.getKeys().let { vector -> vector + setCypher(encrypt, Cipher.ENCRYPT_MODE, secret, getParams(algorithm, vector)).doFinal(data) }
        }

        override fun decrypt(data: ByteArray): ByteArray = synchronized(decrypt) {
            data.copyOfRange(0, factory.getSize()).let { vector -> setCypher(decrypt, Cipher.DECRYPT_MODE, secret, getParams(algorithm, vector)).doFinal(data.copyOfRange(vector.size, data.size)) }
        }
    }

    internal class InternalEncryptingCopy(private val algorithm: CipherAlgorithm, private val encrypt: Cipher, private val decrypt: Cipher, private val secret: SecretKey, private val factory: CipherKeysFactory) : CipherCopyStreams {

        override fun encrypt(data: InputStream, copy: OutputStream) = synchronized(encrypt) {
            val output = ByteArrayOutputStream(DEFAULT_BUFFER_SIZE)
            data.copyTo(output)
            val vector = factory.getKeys().also { copy.write(it) }
            copy.write(setCypher(encrypt, Cipher.ENCRYPT_MODE, secret, getParams(algorithm, vector)).doFinal(output.toByteArray()))
            copy.flush()
        }

        override fun decrypt(data: InputStream, copy: OutputStream) = synchronized(decrypt) {
            val output = ByteArrayOutputStream(DEFAULT_BUFFER_SIZE)
            val vector = ByteArray(factory.getSize()).also { data.read(it) }
            data.copyTo(output)
            copy.write(setCypher(decrypt, Cipher.DECRYPT_MODE, secret, getParams(algorithm, vector)).doFinal(output.toByteArray()))
            copy.flush()
        }
    }

    internal class InternalCipherKeysFactory(private val size: Int, private val random: SecureRandom) : CipherKeysFactory {
        override fun getSize() = size
        override fun getKeys() = Randoms.getByteArray(random, getSize())
    }
}
