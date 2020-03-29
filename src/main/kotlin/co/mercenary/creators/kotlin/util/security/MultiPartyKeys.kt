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
import co.mercenary.creators.kotlin.util.io.InputStreamSupplier
import java.io.*
import java.net.*
import java.nio.file.Path
import java.security.*
import java.security.spec.X509EncodedKeySpec
import javax.crypto.KeyAgreement
import javax.xml.bind.DatatypeConverter.*

@IgnoreForSerialize
object MultiPartyKeys : HasMapNames {

    @JvmStatic
    fun keyPairOf(): KeyPair {
        return KeyPairGenerator.getInstance("EC").apply { initialize(256) }.genKeyPair()
    }

    @JvmStatic
    @JvmOverloads
    fun toMultiParty(party: PublicKey, pair: KeyPair = keyPairOf()): ByteArray {
        return toMultiParty(party.encoded, pair)
    }

    @JvmStatic
    @JvmOverloads
    fun toMultiParty(party: ByteArray, pair: KeyPair = keyPairOf()): ByteArray {
        return KeyAgreement.getInstance("ECDH").let {
            it.init(pair.private)
            it.doPhase(KeyFactory.getInstance("EC").generatePublic(X509EncodedKeySpec(party)), true)
            Digests.proxy(Digests.sha256()).update(it.generateSecret()).update(listOf(party.toByteBuffer(), pair.public.encoded.toByteBuffer()).sorted()).digest()
        }
    }

    @JvmStatic
    fun encode(data: ByteArray): String {
        return printHexBinary(data.copyOf())
    }

    @JvmStatic
    fun encode(data: Array<Byte>): String {
        return encode(data.toByteArray())
    }

    @JvmStatic
    fun encode(data: InputStream): String {
        return encode(data.toByteArray())
    }

    @JvmStatic
    fun encode(data: InputStreamSupplier): String {
        return encode(data.toByteArray())
    }

    @JvmStatic
    fun encode(data: File): String {
        return encode(data.toByteArray())
    }

    @JvmStatic
    fun encode(data: Path): String {
        return encode(data.toByteArray())
    }

    @JvmStatic
    fun encode(data: URL): String {
        return encode(data.toByteArray())
    }

    @JvmStatic
    fun encode(data: URI): String {
        return encode(data.toInputStream())
    }

    @JvmStatic
    fun decode(data: String): ByteArray {
        return parseHexBinary(data).copyOf()
    }

    @JvmStatic
    fun decode(data: CharSequence): ByteArray {
        return decode(data.toString())
    }

    override fun toString() = toMapNames().toString()

    override fun toMapNames() = mapOf("type" to javaClass.name)
}