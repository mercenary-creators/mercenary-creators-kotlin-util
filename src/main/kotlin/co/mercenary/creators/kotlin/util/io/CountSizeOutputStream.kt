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

class CountSizeOutputStream(private val proxy: OutputStream) : OutputStream() {

    private val count = 0L.toAtomic()

    override fun write(b: Int) {
        count.increment()
        proxy.write(b)
    }

    override fun write(b: ByteArray?) {
        if (b != null) {
            count + b.size
            proxy.write(b)
        }
    }

    override fun write(b: ByteArray?, off: Int, len: Int) {
        count + len
        if (b != null) {
            proxy.write(b, off, len)
        }
    }

    override fun flush() {
        proxy.flush()
    }

    override fun close() {
        proxy.close()
    }

    val size: Long
        get() = count.toLong()
}