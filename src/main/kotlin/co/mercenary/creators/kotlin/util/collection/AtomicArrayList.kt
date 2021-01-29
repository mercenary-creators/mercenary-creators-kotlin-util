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

class AtomicArrayList<T> @FrameworkDsl constructor(args: MutableList<T>) : ProxyMutableList<T>(args) {

    @FrameworkDsl
    @JvmOverloads
    constructor(capacity: Int = DEFAULT_LIST_CAPACITY) : this(capacity.toArrayList())

    @FrameworkDsl
    @Synchronized
    override fun clear() {
        super.clear()
    }

    @FrameworkDsl
    @Synchronized
    override operator fun set(index: Int, element: T): T {
        return super.set(index, element)
    }

    @FrameworkDsl
    @Synchronized
    override fun addAll(elements: Collection<T>): Boolean {
        return super.addAll(elements)
    }

    @FrameworkDsl
    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {
        return super.subList(fromIndex, toIndex)
    }

    @FrameworkDsl
    override fun copyOf() = AtomicArrayList(this)

    @FrameworkDsl
    override fun hashCode() = super.hashCode()

    @FrameworkDsl
    override fun toString() = super.toString()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is AtomicArrayList<*> -> other === this || super.equals(other)
        else -> false
    }

    companion object {
        private const val serialVersionUID = 5L
    }
}