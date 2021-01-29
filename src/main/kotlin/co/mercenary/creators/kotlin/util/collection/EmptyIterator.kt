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

@FrameworkDsl
@IgnoreForSerialize
object EmptyIterator : ListIterator<Nothing>, SizedContainer, Copyable<EmptyIterator>, Cloneable, java.io.Serializable {

    private const val serialVersionUID = 5L

    @FrameworkDsl
    override fun next() = fail("invalid next() on EmptyIterator")

    @FrameworkDsl
    override fun previous() = fail("invalid previous() on EmptyIterator")

    @FrameworkDsl
    override fun hasNext() = false

    @FrameworkDsl
    override fun hasPrevious() = false

    @FrameworkDsl
    override fun nextIndex() = sizeOf()

    @FrameworkDsl
    override fun previousIndex() = IS_NOT_FOUND

    @FrameworkDsl
    override fun sizeOf(): Int {
        return 0
    }

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = EmptyIterator

    @FrameworkDsl
    override fun toString() = toListOf<Nothing>().toSafeString()

    @FrameworkDsl
    override fun hashCode() = HASH_BASE_VALUE

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is EmptyIterator -> this === other
        else -> false
    }

    @FrameworkDsl
    private fun readResolve(): Any = EmptyIterator
}