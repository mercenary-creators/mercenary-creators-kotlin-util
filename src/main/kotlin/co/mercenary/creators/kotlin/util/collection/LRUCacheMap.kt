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
class LRUCacheMap<K, V> @JvmOverloads @CreatorsDsl constructor(private val threshold: Int = DEFAULT_MAX_SIZE, capacity: Int = DEFAULT_CAPACITY, factor: Double = DEFAULT_FACTOR) : LinkedHashMap<K, V>(capacity, factor.toFiniteOrElse(DEFAULT_FACTOR).toFloat(), true), Clearable, Copyable<LRUCacheMap<K, V>>, Cloneable, MutableMap<K, V> {

    @CreatorsDsl
    constructor(vararg args: Pair<K, V>) : this() {
        if (args.isNotEmpty()) {
            putAll(args.toMap())
        }
    }

    @CreatorsDsl
    constructor(args: Map<K, V>) : this() {
        if (args.isNotEmpty()) {
            putAll(args.toMap())
        }
    }

    @CreatorsDsl
    constructor(args: LRUCacheMap<K, V>) : this(args.threshold) {
        if (args.isNotEmpty()) {
            putAll(args.toMap())
        }
    }

    @CreatorsDsl
    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean {
        return size > threshold
    }

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isEmpty(): Boolean = super.isEmpty()

    @CreatorsDsl
    @IgnoreForSerialize
    fun isNotEmpty(): Boolean = isEmpty().isNotTrue()

    @CreatorsDsl
    override fun clone() = copyOf()

    @CreatorsDsl
    override fun copyOf() = LRUCacheMap(this)

    @CreatorsDsl
    override fun hashCode() = super.hashCode()

    @CreatorsDsl
    override fun toString() = super.toString()

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is LRUCacheMap<*, *> -> this === other
        else -> false
    }

    @CreatorsDsl
    override fun clear() = super.clear()

    companion object {

        private const val serialVersionUID = 2L

        @CreatorsDsl
        private const val DEFAULT_FACTOR = 0.75

        @CreatorsDsl
        private const val DEFAULT_CAPACITY = 16

        @CreatorsDsl
        private const val DEFAULT_MAX_SIZE = DEFAULT_CAPACITY * 8
    }
}