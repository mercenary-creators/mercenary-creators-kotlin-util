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

@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package co.mercenary.creators.kotlin.util.collection

import co.mercenary.creators.kotlin.util.*

abstract class AbstractBasicMutableList<T, M : AbstractBasicMutableList<T, M>> @JvmOverloads constructor(capacity: Int = DEFAULT_LIST_CAPACITY) : ArrayList<T>(capacity.toListCapacity()), MutableSizedContainer, MutableList<T>, Copyable<M>, Cloneable {

    @CreatorsDsl
    override val size: Int
        @IgnoreForSerialize
        get() = super.size

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isEmpty(): Boolean = size == 0

    @CreatorsDsl
    override fun toArray(): Array<T> {
        return super.toArray().copyOf() as Array<T>
    }

    @CreatorsDsl
    override fun clone(): M {
        return copyOf()
    }

    @CreatorsDsl
    override fun hashCode() = toListHashOf()

    @CreatorsDsl
    override fun toString() = toSafeString()

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is AbstractBasicMutableList<*, *> -> other === this || size == other.size && super.equals(other)
        else -> false
    }

    companion object {
        private const val serialVersionUID = 4L
    }
}