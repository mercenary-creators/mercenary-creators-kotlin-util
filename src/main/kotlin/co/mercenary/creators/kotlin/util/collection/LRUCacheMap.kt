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
class LRUCacheMap<K, V> @JvmOverloads @CreatorsDsl constructor(private val threshold: Int = DEFAULT_LRU_THRESHOLD, capacity: Int = DEFAULT_MAP_CAPACITY, factor: Double = DEFAULT_MAP_FACTOR) : BasicLinkedMap<K, V>(capacity, factor, true), Threshold {

    @CreatorsDsl
    constructor(k: K, v: V) : this() {
        append(k, v)
    }

    @CreatorsDsl
    constructor(args: Map<K, V>) : this() {
        append(args)
    }

    @CreatorsDsl
    constructor(args: Pair<K, V>) : this() {
        append(args)
    }

    @CreatorsDsl
    constructor(vararg args: Pair<K, V>) : this() {
        append(*args)
    }

    @CreatorsDsl
    constructor(args: Iterable<Pair<K, V>>) : this() {
        append(args)
    }

    @CreatorsDsl
    constructor(args: Sequence<Pair<K, V>>) : this() {
        append(args)
    }

    @CreatorsDsl
    constructor(args: LRUCacheMap<K, V>) : this(args.threshold()) {
        append(args)
    }

    @CreatorsDsl
    override fun threshold(): Int = threshold

    @CreatorsDsl
    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?) = size > threshold()

    @CreatorsDsl
    override fun clone() = copyOf()

    @CreatorsDsl
    override fun copyOf() = LRUCacheMap(this)

    @CreatorsDsl
    override fun hashCode() = toSafeHashUf()

    @CreatorsDsl
    override fun toString() = toSafeString()

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is LRUCacheMap<*, *> -> this === other || threshold() == other.threshold() && super.equals(other)
        else -> false
    }

    companion object {

        private const val serialVersionUID = 3L
    }
}