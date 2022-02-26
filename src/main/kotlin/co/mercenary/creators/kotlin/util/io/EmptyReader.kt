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
import java.io.Reader
import java.nio.CharBuffer as NIOSource

@FrameworkDsl
@IgnoreForSerialize
object EmptyReader : Reader(), HasMapNames {

    @FrameworkDsl
    override fun ready() = true

    @FrameworkDsl
    override fun reset() = Unit

    @FrameworkDsl
    override fun close() = Unit

    @FrameworkDsl
    override fun mark(mark: Int) = Unit

    @FrameworkDsl
    override fun read(): Int = IS_NOT_FOUND

    @FrameworkDsl
    override fun read(cbuf: NIOSource): Int = IS_NOT_FOUND

    @FrameworkDsl
    override fun read(cbuf: CharArray): Int = IS_NOT_FOUND

    @FrameworkDsl
    override fun read(cbuf: CharArray, off: Int, len: Int): Int = IS_NOT_FOUND

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to nameOf())
}