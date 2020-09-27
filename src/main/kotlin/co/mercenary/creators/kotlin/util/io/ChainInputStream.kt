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
import java.util.*

@IgnoreForSerialize
class ChainInputStream @CreatorsDsl constructor(args: Enumeration<InputStream>) : SequenceInputStream(args), OpenCloseable, HasMapNames {

    @CreatorsDsl
    constructor(args: Iterator<InputStream>) : this(args.toEnumeration())

    @CreatorsDsl
    constructor(args: Iterable<InputStream>) : this(args.toEnumeration())

    @CreatorsDsl
    constructor(args: Sequence<InputStream>) : this(args.toEnumeration())

    @CreatorsDsl
    constructor(head: InputStream, next: InputStream, vararg more: InputStream) : this(iteratorOf(head, next, *more))

    private val open = true.toAtomic()

    @CreatorsDsl
    override fun close() {
        if (open.isTrueToFalse()) {
            try {
                super.close()
            }
            catch (cause: Throwable) {
                Throwables.thrown(cause)
            }
        }
    }

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isOpen() = open.isTrue()

    @CreatorsDsl
    override fun toString() = nameOf()

    @CreatorsDsl
    override fun hashCode() = idenOf()

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is ChainInputStream -> this === other
        else -> false
    }

    @CreatorsDsl
    override fun toMapNames() = dictOf("name" to nameOf(), "open" to isOpen())
}