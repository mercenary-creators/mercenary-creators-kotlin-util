/*
 * Copyright (c) 2021, Mercenary Creators Company. All rights reserved.
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
import java.nio.charset.Charset

@IgnoreForSerialize
class BytesOutputStream @JvmOverloads @FrameworkDsl constructor(size: Int = DEFAULT_BUFFERED_SIZE) : OutputStream(), AppendableStream<BytesOutputStream> {

    @FrameworkDsl
    private val buff = ByteArrayOutputStream(size.maxOf(MINIMUM_BUFFERED_SIZE).minOf(MAXIMUM_BUFFERED_SIZE))

    @FrameworkDsl
    override fun sizeOf(): Int {
        return buff.size()
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentData(copy: Boolean): ByteArray {
        return toByteArray().toByteArray(copy)
    }

    @FrameworkDsl
    override fun toByteArray(): ByteArray {
        return if (isEmpty()) EMPTY_BYTE_ARRAY else buff.toByteArray()
    }

    @FrameworkDsl
    override fun reset() {
        buff.reset()
    }

    @FrameworkDsl
    override fun clear() {
        reset()
    }

    @FrameworkDsl
    override fun hashCode() = if (isEmpty()) HASH_BASE_VALUE else toByteArray().hashOf()

    @FrameworkDsl
    override fun toString(): String {
        return when (isEmpty()) {
            true -> EMPTY_STRING
            else -> toString(DEFAULT_CHARSET_UTF_8)
        }
    }

    @FrameworkDsl
    fun toString(name: String): String {
        return when (isEmpty()) {
            true -> EMPTY_STRING
            else -> buff.toString(IO.getSupportedCharsetName(name, DEFAULT_CHARSET_UTF_8))
        }
    }

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is BytesOutputStream -> other === this
        else -> false
    }

    @FrameworkDsl
    fun toString(charset: Charset): String {
        return when (isEmpty()) {
            true -> EMPTY_STRING
            else -> buff.toString(IO.getSupportedCharsetName(charset, DEFAULT_CHARSET_UTF_8))
        }
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
        if (data.isNotExhausted()) {
            buff.write(data, 0, data.sizeOf())
        }
    }

    @FrameworkDsl
    override fun write(data: ByteArray, head: Int, tail: Int) {
        if (head < 0 || head > data.sizeOf() || tail < 0 || ((head + tail) - data.sizeOf()) > 0) {
            throw MercenaryFatalExceptiion(MATH_INVALID_SIZE_ERROR)
        }
        buff.write(data, head, tail)
    }

    @FrameworkDsl
    override fun close() {
        flush()
    }

    @FrameworkDsl
    override fun append(data: Int): BytesOutputStream {
        write(data)
        return this
    }

    @FrameworkDsl
    override fun append(data: ByteArray, head: Int, tail: Int): BytesOutputStream {
        write(data, head, tail)
        return this
    }

    @FrameworkDsl
    override fun append(data: ByteArray): BytesOutputStream {
        write(data)
        return this
    }

    @FrameworkDsl
    override fun sendTo(data: OutputStream, flush: Boolean): BytesOutputStream {
        if (isNotEmpty()) {
            buff.writeTo(data)
        }
        if (flush.isTrue()) {
            data.flush()
        }
        return this
    }
}