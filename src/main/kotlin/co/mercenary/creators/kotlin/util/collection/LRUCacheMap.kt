/*
 * Copyright (c) 2022, Mercenary Creators Company. All rights reserved.
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
class LRUCacheMap<K, V> @JvmOverloads @FrameworkDsl constructor(threshold: Int = DEFAULT_LRU_THRESHOLD) : BasicLinkedMap<K, V>(DEFAULT_MAP_CAPACITY, true, true), InsertLimited by threshold.toInsertLimited() {

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
    constructor(args: Iterator<Pair<K, V>>) : this() {
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
    constructor(args: Sequence<Pair<K, V>>) : this() {
        if (args.isNotExhausted()) {
            append(args.mapTo())
        }
    }

    @FrameworkDsl
    constructor(args: LRUCacheMap<K, V>) : this(args.getLimit()) {
        if (args.isNotEmpty()) {
            append(args)
        }
    }

    @FrameworkDsl
    override fun removeEldestEntry(eldest: Map.Entry<K, V>): Boolean {
        return (isOrdered() && isSizeCapped() && isOverLimit(getLimit()))
    }

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = LRUCacheMap(this)

    @FrameworkDsl
    override fun hashCode() = super.hashCode()

    @FrameworkDsl
    override fun toString() = super.toString()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is LRUCacheMap<*, *> -> this === other || getLimit() == other.getLimit() && super.equals(other)
        else -> false
    }

    companion object {

        private const val serialVersionUID = 3L
    }
}