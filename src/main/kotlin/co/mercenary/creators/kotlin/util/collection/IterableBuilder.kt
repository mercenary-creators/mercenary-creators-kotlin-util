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
class IterableBuilder<T> @JvmOverloads @FrameworkDsl constructor(capacity: Int = DEFAULT_LIST_CAPACITY) : Builder<Iterable<T>>, MutableSizedContainer, HasMapNames {

    @FrameworkDsl
    private val list = BasicArrayList<T>(capacity.maxOf(2))

    @FrameworkDsl
    override fun sizeOf(): Int = list.sizeOf()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }

    @FrameworkDsl
    override fun build(): Iterable<T> {
        return list.toList().also { clear() }
    }

    @FrameworkDsl
    fun append(head: T, vararg args: T): IterableBuilder<T> {
        list.add(head)
        if (args.isNotExhausted()) {
            return append(args.toCollection())
        }
        return this
    }

    @FrameworkDsl
    fun append(args: Iterator<T>): IterableBuilder<T> {
        if (args.isNotExhausted()) {
            return append(args.toCollection())
        }
        return this
    }

    @FrameworkDsl
    fun append(args: Iterable<T>): IterableBuilder<T> {
        if (args.isNotExhausted()) {
            list.add(args)

        }
        return this
    }

    @FrameworkDsl
    fun append(args: Sequence<T>): IterableBuilder<T> {
        return append(args.toIterator())
    }

    @FrameworkDsl
    override fun clear() {
        list.clear()
    }

    @FrameworkDsl
    override fun hashCode() = idenOf()

    @FrameworkDsl
    override fun toString() = nameOf()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is IterableBuilder<*> -> this === other || sizeOf() == other.sizeOf()
        else -> false
    }

    @FrameworkDsl
    override fun toMapNames() = dictOf("name" to nameOf(), "size" to sizeOf())
}