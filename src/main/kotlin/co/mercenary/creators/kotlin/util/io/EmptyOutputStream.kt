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
import java.io.OutputStream

@CreatorsDsl
@IgnoreForSerialize
object EmptyOutputStream : OutputStream(), HasMapNames {

    @CreatorsDsl
    override fun close() = Unit

    @CreatorsDsl
    override fun flush() = Unit

    @CreatorsDsl
    override fun write(b: Int) = Unit

    @CreatorsDsl
    override fun write(b: ByteArray) = Unit

    @CreatorsDsl
    override fun write(b: ByteArray, off: Int, len: Int) = Unit

    @CreatorsDsl
    override fun toString() = toMapNames().toSafeString()

    @CreatorsDsl
    override fun toMapNames() = dictOf("type" to nameOf())
}