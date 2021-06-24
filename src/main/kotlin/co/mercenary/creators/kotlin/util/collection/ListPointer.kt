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

@IgnoreForSerialize
class ListPointer<T> @JvmOverloads @FrameworkDsl constructor(value: List<T>, index: Int = 0) : ListPointerBase<T, ListPointer<T>> {

    @FrameworkDsl
    constructor(value: ListPointer<T>) : this(value.pointOf(), value.indexOf())

    @FrameworkDsl
    private val list = value.toArrayList()

    @FrameworkDsl
    private val base = index.maxOf(0).toAtomic()

    @Synchronized
    @FrameworkDsl
    internal fun pointOf(): ArrayList<T> {
        return list.trim()
    }

    @Synchronized
    @FrameworkDsl
    override fun advance() {
        base.advance()
    }

    @Synchronized
    @FrameworkDsl
    override fun current(): T {
        return list[indexOf()]
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isEmpty(): Boolean {
        if (sizeOf() == 0) {
            return true
        }
        return indexOf() > sizeOf()
    }

    @Synchronized
    @FrameworkDsl
    override fun clear() {
        reset()
    }

    @Synchronized
    @FrameworkDsl
    override fun reset() {
        base.reset()
        list.reset()
    }

    @FrameworkDsl
    override fun indexOf(): Int {
        return base.getValue()
    }

    @FrameworkDsl
    override fun sizeOf(): Int {
        return list.sizeOf()
    }

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = ListPointer(this)

    @FrameworkDsl
    override fun hashCode() = idenOf()

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is ListPointer<*> -> other === this || sizeOf() == other.sizeOf()
        else -> false
    }

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to nameOf(), "size" to sizeOf())

    @FrameworkDsl
    override fun forEachRemaining(reset: Boolean, block: Convert<T, Unit>) {
        if (isNotExhausted()) {
            do {
                val value = current()
                advance()
                try {
                    block.convert(value)
                } catch (cause: Throwable) {
                    Throwables.check(cause)
                }
            } while (isNotExhausted())
        }
        if (reset.isTrue()) {
            reset()
        }
    }

    @FrameworkDsl
    override fun forEachRemainingIndexed(reset: Boolean, block: Indexed<T, Unit>) {
        if (isNotExhausted()) {
            do {
                val index = indexOf()
                val value = current()
                advance()
                try {
                    block.indexed(index, value)
                } catch (cause: Throwable) {
                    Throwables.check(cause)
                }
            } while (isNotExhausted())
        }
        if (reset.isTrue()) {
            reset()
        }
    }
}