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

open class BasicLinkedMap<K, V> @JvmOverloads @CreatorsDsl constructor(capacity: Int = DEFAULT_MAP_CAPACITY, factor: Double = DEFAULT_MAP_FACTOR, order: Boolean = false) : LinkedHashMap<K, V>(capacity, factor.toMapFactorOrElse(), order), MutableMapBase<K, V, BasicLinkedMap<K, V>> {

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
    constructor(args: Iterator<Pair<K, V>>) : this() {
        append(args)
    }

    @CreatorsDsl
    constructor(args: Sequence<Pair<K, V>>) : this() {
        append(args)
    }

    @CreatorsDsl
    override val size: Int
        @IgnoreForSerialize
        get() = super.size

    @CreatorsDsl
    override fun clone() = copyOf()

    @CreatorsDsl
    override fun copyOf() = BasicLinkedMap(this)

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isEmpty() = size == 0

    @CreatorsDsl
    override fun hashCode() = toSafeHashUf()

    @CreatorsDsl
    override fun toString() = toSafeString()

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is BasicLinkedMap<*, *> -> this === other || size == other.size && super.equals(other)
        else -> false
    }

    @CreatorsDsl
    override fun clear() = super.clear()

    companion object {

        private const val serialVersionUID = 2L
    }
}