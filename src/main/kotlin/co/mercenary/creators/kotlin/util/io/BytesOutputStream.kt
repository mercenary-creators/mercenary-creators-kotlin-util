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
import java.nio.charset.Charset

@IgnoreForSerialize
class BytesOutputStream @JvmOverloads @CreatorsDsl constructor(size: Int = DEFAULT_BUFFER_SIZE) : ByteArrayOutputStream(size), Resetable, Clearable, HasContentSize, ByteArraySupplier {

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentData(copy: Boolean): ByteArray {
        return toByteArray().toByteArray(copy)
    }

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentSize() = size().toLong()

    @CreatorsDsl
    override fun reset() {
        super.reset()
    }

    @CreatorsDsl
    override fun toByteArray(): ByteArray {
        return super.toByteArray()
    }

    @CreatorsDsl
    override fun clear() {
        reset()
    }

    @CreatorsDsl
    override fun hashCode() = toByteArray().contentHashCode()

    @CreatorsDsl
    override fun toString(): String {
        return toString(Charsets.UTF_8)
    }

    @CreatorsDsl
    override fun toString(name: String): String {
        return super.toString(name)
    }

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is BytesOutputStream -> other === this
        else -> false
    }

    @CreatorsDsl
    fun toString(charset: Charset): String {
        return toString(charset.name())
    }
}