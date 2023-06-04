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

open class StringSet @FrameworkDsl @JvmOverloads constructor(capacity: Int = DEFAULT_SET_CAPACITY) : BasicLinkedSet<String>(capacity.toSetCapacity()) {

    @FrameworkDsl
    constructor(args: String) : this(DEFAULT_SET_CAPACITY) {
        append(args)
    }

    @FrameworkDsl
    constructor(vararg args: String) : this(args.toCollection())

    @FrameworkDsl
    constructor(args: Collection<String>) : this(args.sizeOf()) {
        if (args.isNotExhausted()) {
            append(args)
        }
    }

    @FrameworkDsl
    constructor(args: Iterator<String>) : this(args.toCollection())

    @FrameworkDsl
    constructor(args: Iterable<String>) : this(args.toCollection())

    @FrameworkDsl
    constructor(args: Sequence<String>) : this(args.toCollection())

    @FrameworkDsl
    constructor(args: StringSet) : this(args.sizeOf()) {
        if (args.isNotEmpty()) {
            append(args)
        }
    }

    @FrameworkDsl
    override val size: Int
        @IgnoreForSerialize
        get() = sizeOf()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isEmpty() = sizeOf() == 0

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = StringSet(this)

    @FrameworkDsl
    override operator fun iterator(): MutableIterator<String> {
        return super.iterator()
    }

    @FrameworkDsl
    override operator fun contains(element: String): Boolean {
        return when (isEmpty()) {
            true -> false
            else -> super.contains(element)
        }
    }

    @FrameworkDsl
    operator fun contains(element: CharSequence): Boolean {
        return when (isEmpty()) {
            true -> false
            else -> super.contains(element.copyOf())
        }
    }

    @FrameworkDsl
    override fun remove(element: String): Boolean {
        return isNotEmpty() && super.remove(element)
    }

    @FrameworkDsl
    override fun addAll(elements: Collection<String>): Boolean {
        if (elements.isExhausted()) {
            return false
        }
        return super.addAll(elements)
    }

    @FrameworkDsl
    override fun containsAll(elements: Collection<String>): Boolean {
        if (isEmpty() || elements.isExhausted()) {
            return false
        }
        return super.containsAll(elements)
    }

    @FrameworkDsl
    override fun reset() {
        super.reset()
    }

    @FrameworkDsl
    override fun clear() {
        super.clear()
    }

    @FrameworkDsl
    override fun hashCode() = super.hashCode()

    @FrameworkDsl
    override fun toString() = super.toString()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is StringSet -> other === this || sizeOf() == other.sizeOf() && super.equals(other)
        else -> false
    }

    companion object {

        private const val serialVersionUID = 3L
    }
}