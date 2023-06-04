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
open class ProxiedOutputStream @JvmOverloads @FrameworkDsl constructor(proxy: OutputStream, flush: Boolean = true) : FilterOutputStream(proxy), OpenAutoClosable {

    @FrameworkDsl
    private val open = getAtomicTrue()

    @FrameworkDsl
    private val save = flush.copyOf()

    @FrameworkDsl
    protected fun proxyOf(): OutputStream {
        return out
    }

    @FrameworkDsl
    protected open fun before(size: Int) {
        //
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isOpen() = open.isTrue()

    @FrameworkDsl
    override fun write(b: Int) {
        before(1)
        proxyOf().write(b)
    }

    @FrameworkDsl
    override fun write(b: ByteArray) {
        before(b.sizeOf())
        proxyOf().write(b)
    }

    @FrameworkDsl
    override fun write(b: ByteArray, off: Int, len: Int) {
        before(len.copyOf())
        proxyOf().write(b, off, len)
    }

    @FrameworkDsl
    override fun flush() {
        proxyOf().flush()
    }

    @FrameworkDsl
    override fun close() {
        if (open.isTrueToFalse()) {
            if (save.isTrue()) {
                try {
                    flush()
                } catch (cause: Throwable) {
                    Throwables.thrown(cause)
                }
            }
            try {
                proxyOf().close()
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
            }
        }
    }
}