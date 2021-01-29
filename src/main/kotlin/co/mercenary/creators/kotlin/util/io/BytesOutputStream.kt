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

package co.mercenary.creators.kotlin.util.io

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.ByteArrayOutputStream
import java.io.*
import java.nio.charset.Charset

@IgnoreForSerialize
class BytesOutputStream @JvmOverloads @CreatorsDsl constructor(size: Int = DEFAULT_BUFFER_SIZE) : OutputStream(), SizedContainer, Clearable, Resetable, ByteArraySupplier, AutoCloseable, Flushable {

    @FrameworkDsl
    private val buff = ByteArrayOutputStream(size)

    @FrameworkDsl
    override fun sizeOf(): Int {
        return buff.size()
    }

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentData(copy: Boolean): ByteArray {
        return toByteArray().toByteArray(copy)
    }

    @FrameworkDsl
    fun toByteArray(): ByteArray {
        return buff.toByteArray()
    }

    @FrameworkDsl
    override fun reset() {
        buff.reset()
    }

    @FrameworkDsl
    override fun clear() {
        reset()
    }

    @CreatorsDsl
    override fun hashCode() = if (isEmpty()) HASH_BASE_VALUE else toByteArray().hashOf()

    @CreatorsDsl
    override fun toString(): String {
        return toString(Charsets.UTF_8)
    }

    @CreatorsDsl
    fun toString(name: String): String {
        return when (isEmpty()) {
            true -> EMPTY_STRING
            else -> buff.toString(charset(name))
        }
    }

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is BytesOutputStream -> other === this
        else -> false
    }

    @FrameworkDsl
    fun toString(charset: Charset): String {
        return toString(charset.toString())
    }

    @FrameworkDsl
    override fun flush() {
        buff.flush()
    }

    @FrameworkDsl
    override fun write(b: Int) {
        buff.write(b)
    }

    @FrameworkDsl
    override fun write(data: ByteArray) {
        buff.write(data, 0, data.size)
    }

    @FrameworkDsl
    override fun write(data: ByteArray, head: Int, tail: Int) {
        buff.write(data, head, tail)
    }

    @FrameworkDsl
    override fun close() {
        flush()
    }

    @FrameworkDsl
    fun append(b: Int): BytesOutputStream {
        write(b)
        return this
    }

    @FrameworkDsl
    fun append(data: ByteArray, head: Int, tail: Int): BytesOutputStream {
        write(data, head, tail)
        return this
    }

    @FrameworkDsl
    fun append(data: ByteArray): BytesOutputStream {
        write(data)
        return this
    }

    companion object {

        @JvmStatic
        @FrameworkDsl
        fun charset(name: String): String = if (Charset.isSupported(name)) name else Charsets.UTF_8.toString()
    }
}