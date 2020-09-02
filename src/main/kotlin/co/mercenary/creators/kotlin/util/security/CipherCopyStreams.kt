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
import java.net.URL
import java.nio.channels.ReadableByteChannel
import java.nio.file.Path

interface CipherCopyStreams {

    @CreatorsDsl
    fun encrypt(data: InputStream, copy: OutputStream)

    @CreatorsDsl
    fun decrypt(data: InputStream, copy: OutputStream)
    fun encrypt(data: URL, copy: OutputStream) = data.toInputStream().use { read -> encrypt(read, copy) }
    fun decrypt(data: URL, copy: OutputStream) = data.toInputStream().use { read -> decrypt(read, copy) }
    fun encrypt(data: File, copy: OutputStream) = data.toInputStream().use { read -> encrypt(read, copy) }
    fun decrypt(data: File, copy: OutputStream) = data.toInputStream().use { read -> decrypt(read, copy) }
    fun encrypt(data: Path, copy: OutputStream) = data.toInputStream().use { read -> encrypt(read, copy) }
    fun decrypt(data: Path, copy: OutputStream) = data.toInputStream().use { read -> decrypt(read, copy) }
    fun encrypt(data: ByteArray, copy: OutputStream) = data.toInputStream().use { read -> encrypt(read, copy) }
    fun decrypt(data: ByteArray, copy: OutputStream) = data.toInputStream().use { read -> decrypt(read, copy) }
    fun encrypt(data: ReadableByteChannel, copy: OutputStream) = data.toInputStream().use { read -> encrypt(read, copy) }
    fun decrypt(data: ReadableByteChannel, copy: OutputStream) = data.toInputStream().use { read -> decrypt(read, copy) }
    fun encrypt(data: InputStreamSupplier, copy: OutputStream) = data.toInputStream().use { read -> encrypt(read, copy) }
    fun decrypt(data: InputStreamSupplier, copy: OutputStream) = data.toInputStream().use { read -> decrypt(read, copy) }
    fun encrypt(data: URL, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> encrypt(read, send) } }
    fun decrypt(data: URL, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> decrypt(read, send) } }
    fun encrypt(data: File, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> encrypt(read, send) } }
    fun decrypt(data: File, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> decrypt(read, send) } }
    fun encrypt(data: Path, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> encrypt(read, send) } }
    fun decrypt(data: Path, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> decrypt(read, send) } }
    fun encrypt(data: ReadableByteChannel, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> encrypt(read, send) } }
    fun decrypt(data: ReadableByteChannel, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> decrypt(read, send) } }
    fun encrypt(data: InputStreamSupplier, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> encrypt(read, send) } }
    fun decrypt(data: InputStreamSupplier, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> decrypt(read, send) } }
    fun encrypt(data: URL, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> encrypt(read, send) } }
    fun decrypt(data: URL, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> decrypt(read, send) } }
    fun encrypt(data: File, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> encrypt(read, send) } }
    fun decrypt(data: File, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> decrypt(read, send) } }
    fun encrypt(data: Path, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> encrypt(read, send) } }
    fun decrypt(data: Path, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> decrypt(read, send) } }
    fun encrypt(data: ReadableByteChannel, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> encrypt(read, send) } }
    fun decrypt(data: ReadableByteChannel, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> decrypt(read, send) } }
    fun encrypt(data: InputStreamSupplier, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> encrypt(read, send) } }
    fun decrypt(data: InputStreamSupplier, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> decrypt(read, send) } }
}