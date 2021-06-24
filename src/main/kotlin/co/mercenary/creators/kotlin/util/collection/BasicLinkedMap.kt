/*
 * Copyright (c) 2021, Mercenary Creators Company. All rights reserved.
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

open class BasicLinkedMap<K, V> @JvmOverloads @FrameworkDsl constructor(capacity: Int = DEFAULT_MAP_CAPACITY, order: Boolean = false, capped: Boolean = false) : LinkedHashMap<K, V>(capacity.toMapCapacity(), DEFAULT_MAP_FACTOR_VALUE, order), MutableMapBase<K, V, BasicLinkedMap<K, V>>, InsertOrdered by order.toInsertOrdered(), SizeCapped by capped.toSizeCapped() {

    @FrameworkDsl
    constructor(k: K, v: V) : this() {
        append(k, v)
    }

    @FrameworkDsl
    constructor(args: Map<K, V>) : this() {
        if (args.isNotExhausted()) {
            append(args)
        }
    }

    @FrameworkDsl
    constructor(args: Pair<K, V>) : this() {
        append(args)
    }

    @FrameworkDsl
    constructor(vararg args: Pair<K, V>) : this() {
        if (args.isNotExhausted()) {
            append(args.mapTo())
        }
    }

    @FrameworkDsl
    constructor(args: Iterable<Pair<K, V>>) : this() {
        if (args.isNotExhausted()) {
            append(args.mapTo())
        }
    }

    @FrameworkDsl
    constructor(args: Iterator<Pair<K, V>>) : this() {
        if (args.isNotExhausted()) {
            append(args.mapTo())
        }
    }

    @FrameworkDsl
    constructor(args: Sequence<Pair<K, V>>) : this() {
        if (args.isNotExhausted()) {
            append(args.mapTo())
        }
    }

    @FrameworkDsl
    constructor(args: BasicLinkedMap<K, V>) : this(order = args.isOrdered(), capped = args.isSizeCapped()) {
        if (args.isNotExhausted()) {
            append(args)
        }
    }

    @FrameworkDsl
    override fun keysOf(): MutableSet<K> {
        return keys
    }

    @FrameworkDsl
    override fun sizeOf(): Int {
        return super.size.copyOf()
    }

    @FrameworkDsl
    override val size: Int
        @IgnoreForSerialize
        get() = sizeOf()

    @FrameworkDsl
    override operator fun get(key: K): V? {
        return super.get(key)
    }

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = BasicLinkedMap(this)

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isEmpty() = sizeOf() == 0

    @FrameworkDsl
    override fun reset() {
        clear()
    }

    @FrameworkDsl
    override fun clear() {
        super.clear()
    }

    @FrameworkDsl
    override fun containsKey(key: K): Boolean {
        return isNotExhausted() && super.containsKey(key)
    }

    @FrameworkDsl
    override fun containsValue(value: V): Boolean {
        return isNotExhausted() && super.containsValue(value)
    }

    @FrameworkDsl
    override fun hashCode() = hashOf()

    @FrameworkDsl
    override fun toString() = toSafeString()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is BasicLinkedMap<*, *> -> this === other || sizeOf() == other.sizeOf() && isOrdered() == other.isOrdered() && isSizeCapped() == other.isSizeCapped() && this isSameAs other
        else -> false
    }

    companion object {

        private const val serialVersionUID = 2L
    }
}