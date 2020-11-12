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

package co.mercenary.creators.kotlin.util.collection

import co.mercenary.creators.kotlin.util.*

@IgnoreForSerialize
class IterableBuilder<T> @JvmOverloads @CreatorsDsl constructor(capacity: Int = DEFAULT_MAP_CAPACITY) : Builder<Iterable<T>>, Clearable, SizedContainer, HasMapNames {

    @CreatorsDsl
    @JvmOverloads
    constructor(capacity: Int = DEFAULT_MAP_CAPACITY, args: T) : this(capacity) {
        append(args)
    }

    @CreatorsDsl
    @JvmOverloads
    constructor(capacity: Int = DEFAULT_MAP_CAPACITY, vararg args: T) : this(capacity) {
        append(args.toIterable())
    }

    @CreatorsDsl
    @JvmOverloads
    constructor(capacity: Int = DEFAULT_MAP_CAPACITY, args: Iterator<T>) : this(capacity) {
        append(args)
    }

    @CreatorsDsl
    @JvmOverloads
    constructor(capacity: Int = DEFAULT_MAP_CAPACITY, args: Iterable<T>) : this(capacity) {
        append(args)
    }

    @CreatorsDsl
    @JvmOverloads
    constructor(capacity: Int = DEFAULT_MAP_CAPACITY, args: Sequence<T>) : this(capacity) {
        append(args)
    }

    private val list = ArrayList<T>(capacity.maxOf(2))

    @CreatorsDsl
    internal fun sizeOf(): Int = list.size

    @CreatorsDsl
    override val size: Int
        @IgnoreForSerialize
        get() = sizeOf()

    @CreatorsDsl
    override fun build(): Iterable<T> {
        return list.toList().also { clear() }
    }

    @CreatorsDsl
    fun typeOf(): Class<*> {
        return list.toArray().javaClass.componentType
    }

    @CreatorsDsl
    fun append(args: T): IterableBuilder<T> {
        list += args
        return this
    }

    @CreatorsDsl
    fun append(vararg args: T): IterableBuilder<T> {
        args.forEach {
            list += it
        }
        return this
    }

    @CreatorsDsl
    fun append(args: Iterator<T>): IterableBuilder<T> {
        args.forEach {
            list += it
        }
        return this
    }

    @CreatorsDsl
    fun append(args: Iterable<T>): IterableBuilder<T> {
        list += args
        return this
    }

    @CreatorsDsl
    fun append(args: Sequence<T>): IterableBuilder<T> {
        list += args
        return this
    }

    @CreatorsDsl
    override fun clear() {
        list.clear()
    }

    @CreatorsDsl
    override fun hashCode() = toMapNames().toSafeHashUf()

    @CreatorsDsl
    override fun toString() = toMapNames().toSafeString()

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is IterableBuilder<*> -> this === other || sizeOf() == other.sizeOf()
        else -> false
    }

    @CreatorsDsl
    override fun toMapNames() = dictOf("name" to nameOf(), "size" to sizeOf(), "type" to typeOf().nameOf())
}