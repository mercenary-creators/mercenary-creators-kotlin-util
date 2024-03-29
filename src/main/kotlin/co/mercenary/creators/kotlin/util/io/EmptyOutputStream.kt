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
import java.io.OutputStream

@FrameworkDsl
@IgnoreForSerialize
object EmptyOutputStream : OutputStream(), Copyable<EmptyOutputStream>, Cloneable {

    @FrameworkDsl
    override fun close() = Unit

    @FrameworkDsl
    override fun flush() = Unit

    @FrameworkDsl
    override fun write(b: Int) = Unit

    @FrameworkDsl
    override fun write(b: ByteArray) = Unit

    @FrameworkDsl
    override fun write(b: ByteArray, off: Int, len: Int) = Unit

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = EmptyOutputStream

    @FrameworkDsl
    override fun toString() = dictOf("type" to nameOf()).toSafeString()
}