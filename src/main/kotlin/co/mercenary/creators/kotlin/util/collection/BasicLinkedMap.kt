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
import co.mercenary.creators.kotlin.util.type.SameAndHashCode

open class BasicLinkedMap<K, V> @JvmOverloads @FrameworkDsl constructor(capacity: Int = DEFAULT_MAP_CAPACITY, factor: Double = DEFAULT_MAP_FACTOR, order: Boolean = false) : LinkedHashMap<K, V>(capacity, factor.toMapFactorOrElse(), order), MutableMapBase<K, V, BasicLinkedMap<K, V>>, InsertOrdered by order.toInsertOrdered() {

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
    constructor(args: BasicLinkedMap<K, V>) : this(order = args.isOrdered()) {
        if (args.isNotExhausted()) {
            append(args)
        }
    }

    @FrameworkDsl
    override val size: Int
        @IgnoreForSerialize
        get() = sizeOf()

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = BasicLinkedMap(this)

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isEmpty() = sizeOf() == 0

    @FrameworkDsl
    override fun hashCode() = hashOf()

    @FrameworkDsl
    override fun toString() = toSafeString()

    @FrameworkDsl
    override fun containsKey(key: K): Boolean {
        return isNotExhausted() && super.containsKey(key)
    }

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is BasicLinkedMap<*, *> -> this === other || sizeOf() == other.sizeOf() && isOrdered() == other.isOrdered() && isSameLinkedHashMap(this, other)
        else -> false
    }

    @FrameworkDsl
    override fun clear() = super.clear()

    companion object {

        private const val serialVersionUID = 2L

        @JvmStatic
        @FrameworkDsl
        fun isSameLinkedHashMap(value: LinkedHashMap<*, *>, other: LinkedHashMap<*, *>): Boolean {
            if (value === other) {
                return true
            }
            if (value.sizeOf() != other.sizeOf()) {
                return false
            }
            if (value.sizeOf() == 0) {
                return true
            }
            try {
                for ((k, v) in value) {
                    if (other isKeyNotDefined k) {
                        return false
                    }
                    val o = other[k]
                    if (o === v) {
                        continue
                    }
                    if (v == null && o != null) {
                        return false
                    }
                    if (v.isSameAs(o)) {
                        continue
                    }
                }
            } catch (cause: Throwable) {
                return Throwables.fatal(cause, false)
            }
            return true
        }
    }
}