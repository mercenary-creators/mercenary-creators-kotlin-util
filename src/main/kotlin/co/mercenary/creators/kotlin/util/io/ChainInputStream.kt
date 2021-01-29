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
import java.io.InputStream
import java.util.*

@IgnoreForSerialize
class ChainInputStream @CreatorsDsl constructor(args: Iterator<InputStream>) : AbstractChainInputStream(args) {

    @FrameworkDsl
    constructor(args: Iterable<InputStream>) : this(args.toIterator())

    @FrameworkDsl
    constructor(args: Sequence<InputStream>) : this(args.toIterator())

    @FrameworkDsl
    constructor(args: Enumeration<InputStream>) : this(args.toIterator())

    @FrameworkDsl
    constructor(head: InputStream, next: InputStream, vararg more: InputStream) : this(append(head).append(next).append(more.toIterable()).toIterable())

    @FrameworkDsl
    override fun hashCode() = super.hashCode()

    @FrameworkDsl
    override fun toString() = super.toString()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is ChainInputStream -> this === other || super.equals(other)
        else -> false
    }

    companion object Companion {

        @FrameworkDsl
        private val list = BasicArrayList<InputStream>(2)

        @FrameworkDsl
        fun append(data: InputStream): Companion {
            list.add(data)
            return this
        }

        @FrameworkDsl
        fun append(data: Iterable<InputStream>): Companion {
            list.add(data)
            return this
        }

        @FrameworkDsl
        fun toIterable(): Iterable<InputStream> {
            return list.toList().also { list.clear() }
        }
    }
}