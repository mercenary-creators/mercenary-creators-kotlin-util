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

open class ProxyMutableList<T> @JvmOverloads @FrameworkDsl constructor(self: MutableList<T>, copy: Boolean = false) : MutableListBase<T, ProxyMutableList<T>> {

    @JvmOverloads
    @FrameworkDsl
    constructor(capacity: Int = DEFAULT_LIST_CAPACITY) : this(capacity.toArrayList<T>(), false)

    @FrameworkDsl
    constructor(args: ProxyMutableList<T>) : this(args.toProxy(), false)

    @FrameworkDsl
    private val list = build(self, copy.isTrue())

    @FrameworkDsl
    override val size: Int
        @IgnoreForSerialize
        get() = list.sizeOf()

    @FrameworkDsl
    internal fun toProxy() = list

    @FrameworkDsl
    @IgnoreForSerialize
    internal fun isArrayList(): Boolean {
        return list is ArrayList
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isEmpty(): Boolean {
        return list.isExhausted()
    }

    @FrameworkDsl
    override fun trim() {
        if (isArrayList()) {
            when (list) {
                is ArrayList -> synchronized(list) {
                    list.trimToSize()
                }
            }
        }
    }

    @FrameworkDsl
    override fun clear() {
        list.clear()
    }

    @FrameworkDsl
    override operator fun contains(element: @UnsafeVariance T): Boolean {
        return if (isEmpty()) false else list.contains(element)
    }

    @FrameworkDsl
    override fun containsAll(elements: Collection<@UnsafeVariance T>): Boolean {
        return if (isEmpty() || elements.isExhausted()) false else list.containsAll(elements)
    }

    @FrameworkDsl
    override fun pop(): T {
        if (isEmpty()) {
            fail("${nameOf()}.pop()")
        }
        return removeAt(0)
    }

    @FrameworkDsl
    override operator fun get(index: Int): T {
        return list[rangecheckget(index)]
    }

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf(): ProxyMutableList<T> {
        return ProxyMutableList(this)
    }

    @FrameworkDsl
    override fun indexOf(element: T): Int {
        if (isEmpty()) {
            return IS_NOT_FOUND
        }
        return list.indexOf(element)
    }

    @FrameworkDsl
    override operator fun iterator(): MutableIterator<T> {
        return enhance(list.iterator())
    }

    @FrameworkDsl
    override fun lastIndexOf(element: T): Int {
        if (isEmpty()) {
            return IS_NOT_FOUND
        }
        return list.lastIndexOf(element)
    }

    @FrameworkDsl
    override fun add(element: T): Boolean {
        return list.add(element)
    }

    @FrameworkDsl
    override fun add(index: Int, element: T) {
        list.add(rangecheckadd(index), element)
    }

    @FrameworkDsl
    override fun addAll(index: Int, elements: Collection<@UnsafeVariance T>): Boolean {
        if (elements.isExhausted()) {
            return false
        }
        return list.addAll(rangecheckadd(index), elements)
    }

    @FrameworkDsl
    override fun addAll(elements: Collection<@UnsafeVariance T>): Boolean {
        if (elements.isExhausted()) {
            return false
        }
        return list.addAll(elements)
    }

    @FrameworkDsl
    override fun listIterator(): MutableListIterator<T> {
        return enhance(list.listIterator())
    }

    @FrameworkDsl
    override fun listIterator(index: Int): MutableListIterator<T> {
        return enhance(list.listIterator(index))
    }

    @FrameworkDsl
    override fun remove(element: T): Boolean {
        return list.remove(element)
    }

    @FrameworkDsl
    override fun removeAll(elements: Collection<@UnsafeVariance T>): Boolean {
        if (elements.isExhausted()) {
            return false
        }
        return list.removeAll(elements)
    }

    @FrameworkDsl
    override fun removeAt(index: Int): T {
        return list.removeAt(index)
    }

    @FrameworkDsl
    override fun retainAll(elements: Collection<@UnsafeVariance T>): Boolean {
        return list.retainAll(elements)
    }

    @FrameworkDsl
    override operator fun set(index: Int, element: T): T {
        return list.set(index, element)
    }

    @FrameworkDsl
    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {
        return enhance(list.subList(fromIndex, toIndex))
    }

    @FrameworkDsl
    override fun hashCode() = hashOf()

    @FrameworkDsl
    override fun toString() = toSafeString()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is ProxyMutableList<*> -> other === this || sizeOf() == other.sizeOf() && SameAndHashCode.isSameAs(toProxy(), other.toProxy())
        else -> false
    }

    companion object {

        private const val serialVersionUID = 5L

        @JvmStatic
        @FrameworkDsl
        internal fun <T> build(list: MutableList<T>, copy: Boolean): MutableList<T> {
            return when (copy.isNotTrue()) {
                true -> list
                else -> list.toArrayList()
            }
        }
    }
}