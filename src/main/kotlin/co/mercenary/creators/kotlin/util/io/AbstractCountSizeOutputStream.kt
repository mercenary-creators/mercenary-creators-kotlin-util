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
import java.io.*

@IgnoreForSerialize
abstract class AbstractCountSizeOutputStream @JvmOverloads constructor(proxy: OutputStream, private val flush: Boolean = false) : FilterOutputStream(proxy), OpenAutoClosable, HasContentSize, HasMapNames, Clearable {

    private val count = 0L.toAtomic()

    private val state = true.toAtomic()

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isOpen() = state.isTrue()

    override fun write(b: Int) {
        count.increment()
        super.write(b)
    }

    override fun write(b: ByteArray) {
        count.plus(b.toContentSize())
        super.write(b)
    }

    override fun write(b: ByteArray, off: Int, len: Int) {
        count.plus(len.maxOf(0))
        super.write(b, off, len)
    }

    @CreatorsDsl
    override fun close() {
        if (state.isTrueToFalse()) {
            if (flush.isTrue()) {
                try {
                    super.flush()
                }
                catch (cause: Throwable) {
                    Throwables.thrown(cause)
                }
            }
            try {
                super.close()
            }
            catch (cause: Throwable) {
                Throwables.thrown(cause)
            }
        }
    }

    @CreatorsDsl
    override fun clear() {
        count.set(0L)
    }

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getContentSize() = count.toLong().maxOf(0)

    @CreatorsDsl
    override fun toMapNames() = dictOf("name" to nameOf(), "open" to isOpen(), "size" to getContentSize(), "flush" to flush.isTrue())
}