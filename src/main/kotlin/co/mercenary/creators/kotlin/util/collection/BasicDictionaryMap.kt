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

open class BasicDictionaryMap<V> @JvmOverloads @FrameworkDsl constructor(capacity: Int = DEFAULT_MAP_CAPACITY, order: Boolean = false, capped: Boolean = false) : BasicLinkedMap<String, V>(capacity, order, capped) {

    @FrameworkDsl
    constructor(k: String, v: V) : this() {
        append(k, v)
    }

    @FrameworkDsl
    constructor(args: Map<String, V>) : this() {
        if (args.isNotExhausted()) {
            append(args)
        }
    }

    @FrameworkDsl
    constructor(args: Pair<String, V>) : this() {
        append(args)
    }

    @FrameworkDsl
    constructor(vararg args: Pair<String, V>) : this() {
        if (args.isNotExhausted()) {
            append(args.mapTo())
        }
    }

    @FrameworkDsl
    constructor(args: Iterable<Pair<String, V>>) : this() {
        if (args.isNotExhausted()) {
            append(args.mapTo())
        }
    }

    @FrameworkDsl
    constructor(args: Sequence<Pair<String, V>>) : this() {
        if (args.isNotExhausted()) {
            append(args.mapTo())
        }
    }

    @FrameworkDsl
    constructor(args: BasicDictionaryMap<V>) : this(order = args.isOrdered(), capped = args.isSizeCapped()) {
        if (args.isNotEmpty()) {
            append(args)
        }
    }

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = BasicDictionaryMap(this)

    @FrameworkDsl
    override fun hashCode() = super.hashCode()

    @FrameworkDsl
    override fun toString() = super.toString()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is BasicDictionaryMap<*> -> this === other || sizeOf() == other.sizeOf() && super.equals(other)
        else -> false
    }

    companion object {

        private const val serialVersionUID = 4L
    }
}