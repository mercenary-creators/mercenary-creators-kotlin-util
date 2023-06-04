/*
 * Copyright (c) 2023, Mercenary Creators Company. All rights reserved.
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

@IgnoreForSerialize
interface AppendableStream<S : AppendableStream<S>> : Appendable, MutableSizedContainer, ByteArraySupplier, HasContentSize, AutoCloseable, Flushable {

    @FrameworkDsl
    fun append(data: Int): S

    @FrameworkDsl
    fun append(data: ByteArray): S

    @FrameworkDsl
    fun append(data: ByteArray, head: Int, tail: Int): S

    @FrameworkDsl
    override fun append(data: CharSequence?): S {
        return when (data) {
            null -> append(IO.NULLS_BYTE_ARRAY)
            else -> append(data.getContentData())
        }
    }

    @FrameworkDsl
    override fun append(data: CharSequence?, head: Int, tail: Int): S {
        return when (data) {
            null -> append(IO.NULLS_BYTE_ARRAY)
            else -> append(data.subSequence(head, tail))
        }
    }

    @FrameworkDsl
    override fun append(c: Char): S {
        return append(c.toCharSequence())
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentSize(): Long {
        return sizeOf().toContentSize()
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isEmpty(): Boolean {
        return sizeOf() == 0
    }

    @FrameworkDsl
    fun toByteArray(): ByteArray

    @FrameworkDsl
    fun sendTo(data: OutputStream, flush: Boolean = false): S
}