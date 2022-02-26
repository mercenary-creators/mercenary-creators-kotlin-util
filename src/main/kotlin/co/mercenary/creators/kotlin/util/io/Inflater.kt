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

package co.mercenary.creators.kotlin.util.io

import co.mercenary.creators.kotlin.util.*
import java.io.*
import java.net.*
import java.nio.channels.*
import java.nio.file.Path

@IgnoreForSerialize
interface Inflater : HasMapNames {

    @FrameworkDsl
    fun inflate(data: InputStream, copy: OutputStream): Long

    @FrameworkDsl
    fun deflate(data: InputStream, copy: OutputStream): Long

    @FrameworkDsl
    fun inflate(data: ByteArray): ByteArray = inflate(data.toInputStream())

    @FrameworkDsl
    fun deflate(data: ByteArray): ByteArray = deflate(data.toInputStream())

    @FrameworkDsl
    fun inflate(data: URL): ByteArray = data.toInputStream().use { read -> inflate(read) }

    @FrameworkDsl
    fun deflate(data: URL): ByteArray = data.toInputStream().use { read -> deflate(read) }

    @FrameworkDsl
    fun inflate(data: URI): ByteArray = data.toInputStream().use { read -> inflate(read) }

    @FrameworkDsl
    fun deflate(data: URI): ByteArray = data.toInputStream().use { read -> deflate(read) }

    @FrameworkDsl
    fun inflate(data: File): ByteArray = data.toInputStream().use { read -> inflate(read) }

    @FrameworkDsl
    fun deflate(data: File): ByteArray = data.toInputStream().use { read -> deflate(read) }

    @FrameworkDsl
    fun inflate(data: Path): ByteArray = data.toInputStream().use { read -> inflate(read) }

    @FrameworkDsl
    fun deflate(data: Path): ByteArray = data.toInputStream().use { read -> deflate(read) }

    @FrameworkDsl
    fun inflate(data: InputStreamSupplier): ByteArray = data.toInputStream().use { read -> inflate(read) }

    @FrameworkDsl
    fun deflate(data: InputStreamSupplier): ByteArray = data.toInputStream().use { read -> deflate(read) }

    @FrameworkDsl
    fun inflate(data: ReadableByteChannel): ByteArray = data.toInputStream().use { read -> inflate(read) }

    @FrameworkDsl
    fun deflate(data: ReadableByteChannel): ByteArray = data.toInputStream().use { read -> deflate(read) }

    @FrameworkDsl
    fun inflate(data: URL, copy: OutputStream) = data.toInputStream().use { read -> inflate(read, copy) }

    @FrameworkDsl
    fun deflate(data: URL, copy: OutputStream) = data.toInputStream().use { read -> deflate(read, copy) }

    @FrameworkDsl
    fun inflate(data: URI, copy: OutputStream) = data.toInputStream().use { read -> inflate(read, copy) }

    @FrameworkDsl
    fun deflate(data: URI, copy: OutputStream) = data.toInputStream().use { read -> deflate(read, copy) }

    @FrameworkDsl
    fun inflate(data: File, copy: OutputStream) = data.toInputStream().use { read -> inflate(read, copy) }

    @FrameworkDsl
    fun deflate(data: File, copy: OutputStream) = data.toInputStream().use { read -> deflate(read, copy) }

    @FrameworkDsl
    fun inflate(data: Path, copy: OutputStream) = data.toInputStream().use { read -> inflate(read, copy) }

    @FrameworkDsl
    fun deflate(data: Path, copy: OutputStream) = data.toInputStream().use { read -> deflate(read, copy) }

    @FrameworkDsl
    fun inflate(data: InputStreamSupplier, copy: OutputStream) = data.toInputStream().use { read -> inflate(read, copy) }

    @FrameworkDsl
    fun deflate(data: InputStreamSupplier, copy: OutputStream) = data.toInputStream().use { read -> deflate(read, copy) }

    @FrameworkDsl
    fun inflate(data: ReadableByteChannel, copy: OutputStream) = data.toInputStream().use { read -> inflate(read, copy) }

    @FrameworkDsl
    fun deflate(data: ReadableByteChannel, copy: OutputStream) = data.toInputStream().use { read -> deflate(read, copy) }

    @FrameworkDsl
    fun inflate(data: URL, copy: URL) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: URL, copy: URL) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: URI, copy: URL) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: URI, copy: URL) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: File, copy: URL) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: File, copy: URL) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: Path, copy: URL) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: Path, copy: URL) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: InputStreamSupplier, copy: URL) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: InputStreamSupplier, copy: URL) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: ReadableByteChannel, copy: URL) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: ReadableByteChannel, copy: URL) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: URL, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: URL, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: URI, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: URI, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: File, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: File, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: Path, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: Path, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: InputStreamSupplier, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: InputStreamSupplier, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: ReadableByteChannel, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: ReadableByteChannel, copy: File) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: URL, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: URL, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: URI, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: URI, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: File, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: File, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: Path, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: Path, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: InputStreamSupplier, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: InputStreamSupplier, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: ReadableByteChannel, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: ReadableByteChannel, copy: Path) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: URL, copy: WritableByteChannel) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: URL, copy: WritableByteChannel) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: URI, copy: WritableByteChannel) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: URI, copy: WritableByteChannel) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: File, copy: WritableByteChannel) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: File, copy: WritableByteChannel) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: Path, copy: WritableByteChannel) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: Path, copy: WritableByteChannel) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: InputStreamSupplier, copy: WritableByteChannel) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: InputStreamSupplier, copy: WritableByteChannel) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: ReadableByteChannel, copy: WritableByteChannel) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: ReadableByteChannel, copy: WritableByteChannel) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: URL, copy: OutputStreamSupplier) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: URL, copy: OutputStreamSupplier) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: URI, copy: OutputStreamSupplier) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: URI, copy: OutputStreamSupplier) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: File, copy: OutputStreamSupplier) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: File, copy: OutputStreamSupplier) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: Path, copy: OutputStreamSupplier) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: Path, copy: OutputStreamSupplier) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: InputStreamSupplier, copy: OutputStreamSupplier) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: InputStreamSupplier, copy: OutputStreamSupplier) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: ReadableByteChannel, copy: OutputStreamSupplier) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> inflate(read, send) } }

    @FrameworkDsl
    fun deflate(data: ReadableByteChannel, copy: OutputStreamSupplier) = copy.toOutputStream().use { send -> data.toInputStream().use { read -> deflate(read, send) } }

    @FrameworkDsl
    fun inflate(data: InputStream): ByteArray = BytesOutputStream().also { send -> inflate(data, send) }.getContentData()

    @FrameworkDsl
    fun deflate(data: InputStream): ByteArray = BytesOutputStream().also { send -> deflate(data, send) }.getContentData()
}