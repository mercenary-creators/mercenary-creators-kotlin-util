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

package co.mercenary.creators.kotlin.util.collection

import co.mercenary.creators.kotlin.util.*

open class BasicLinkedSet<T> @FrameworkDsl @JvmOverloads constructor(capacity: Int = DEFAULT_SET_CAPACITY) : LinkedHashSet<T>(capacity.toSetCapacity()), MutableSetBase<T, BasicLinkedSet<T>> {

    @FrameworkDsl
    constructor(args: T) : this(DEFAULT_SET_CAPACITY) {
        append(args)
    }

    @FrameworkDsl
    constructor(args: Collection<T>) : this(args.sizeOf()) {
        if (args.isNotExhausted()) {
            append(args)
        }
    }

    @FrameworkDsl
    constructor(vararg args: T) : this(args.toCollection())

    @FrameworkDsl
    constructor(args: Iterable<T>) : this(args.toCollection())

    @FrameworkDsl
    constructor(args: Iterator<T>) : this(args.toCollection())

    @FrameworkDsl
    constructor(args: Sequence<T>) : this(args.toCollection())

    @FrameworkDsl
    constructor(args: BasicLinkedSet<T>) : this(args.sizeOf()) {
        if (args.isNotEmpty()) {
            append(args)
        }
    }

    @FrameworkDsl
    override fun sizeOf(): Int {
        return super.size
    }

    @FrameworkDsl
    override val size: Int
        @IgnoreForSerialize
        get() = sizeOf()

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = BasicLinkedSet(this)

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isEmpty() = sizeOf() == 0

    @FrameworkDsl
    override operator fun iterator(): MutableIterator<T> {
        return super.iterator()
    }

    @FrameworkDsl
    override operator fun contains(element: @UnsafeVariance T): Boolean {
        return when (isEmpty()) {
            true -> false
            else -> super.contains(element)
        }
    }

    @FrameworkDsl
    override fun add(element: T): Boolean {
        return super.add(element)
    }

    @FrameworkDsl
    override fun addAll(elements: Collection<@UnsafeVariance T>): Boolean {
        if (elements.isExhausted()) {
            return false
        }
        return super.addAll(elements)
    }

    @FrameworkDsl
    override fun removeAll(elements: Collection<@UnsafeVariance T>): Boolean {
        if (isEmpty() || elements.isExhausted()) {
            return false
        }
        return super.removeAll(elements.toSet())
    }

    @FrameworkDsl
    override fun retainAll(elements: Collection<@UnsafeVariance T>): Boolean {
        if (elements.isExhausted()) {
            return false
        }
        return super.retainAll(elements.toSet())
    }

    @FrameworkDsl
    override fun containsAll(elements: Collection<@UnsafeVariance T>): Boolean {
        if (isEmpty() || elements.isExhausted()) {
            return false
        }
        return super.containsAll(elements)
    }

    @FrameworkDsl
    override fun remove(element: T): Boolean {
        return isNotEmpty() && super.remove(element)
    }

    @FrameworkDsl
    override fun reset() {
        clear()
    }

    @FrameworkDsl
    override fun clear() {
        super.clear()
    }

    @FrameworkDsl
    override fun hashCode() = hashOf()

    @FrameworkDsl
    override fun toString() = toSafeString()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getList(): List<T> {
        return if (isEmpty()) toListOf() else toList()
    }

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is BasicLinkedSet<*> -> other === this || sizeOf() == other.sizeOf() && this isSameAs other
        else -> false
    }

    companion object {

        private const val serialVersionUID = 6L
    }
}