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

@IgnoreForSerialize
abstract class AbstractCountSizeOutputStream @JvmOverloads constructor(proxy: OutputStream, flush: Boolean = false) : FilterOutputStream(proxy), OpenAutoClosable, HasContentSize, HasMapNames, Clearable {

    @FrameworkDsl
    private val save = flush.toAtomic()

    @FrameworkDsl
    private val many = 0L.toAtomic()

    @FrameworkDsl
    private val open = getAtomicTrue()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isOpen() = open.isTrue()

    override fun write(b: Int) {
        many.increment()
        super.write(b)
    }

    override fun write(b: ByteArray) {
        many.plus(b.toContentSize())
        super.write(b)
    }

    override fun write(b: ByteArray, off: Int, len: Int) {
        many.plus(len.maxOf(0))
        super.write(b, off, len)
    }

    @FrameworkDsl
    override fun close() {
        if (open.isTrueToFalse()) {
            if (save.isTrue()) {
                try {
                    super.flush()
                } catch (cause: Throwable) {
                    Throwables.thrown(cause)
                }
            }
            try {
                super.close()
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
            }
        }
    }

    @FrameworkDsl
    override fun clear() {
        many.setValue(0)
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentSize() = many.getValue().maxOf(0)

    @FrameworkDsl
    override fun toMapNames() = dictOf("name" to nameOf(), "open" to isOpen(), "size" to getContentSize(), "flush" to save.isTrue())
}