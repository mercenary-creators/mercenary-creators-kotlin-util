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
class ConstrainedOnceIterator<T> @CreatorsDsl constructor(private val iter: Iterator<T>) : ConstrainedIterator<T> {

    private val open = true.toAtomic()

    @CreatorsDsl
    private fun iterOf(): Iterator<T> = iter

    @CreatorsDsl
    private fun nextOf(): T = iterOf().next()

    @CreatorsDsl
    @IgnoreForSerialize
    fun isExhausted(): Boolean = iterOf().hasNext()

    @CreatorsDsl
    override fun hasNext(): Boolean {
        if (isNotValid()) {
            throw MercenaryFatalExceptiion("")
        }
        return when (isEmpty()) {
            true -> open.toNotTrue().isTrue()
            else -> true
        }
    }

    @CreatorsDsl
    override fun next(): T {
        if (isNotValid()) {
            throw MercenaryFatalExceptiion("")
        }
        if (hasNext().isNotTrue()) {
            throw MercenaryFatalExceptiion("")
        }
        return nextOf()
    }

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isConstrained(): Boolean = true

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isDone(): Boolean = open.isNotTrue()

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isEmpty(): Boolean = isExhausted().isNotTrue()

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isValid(): Boolean = isNotDone() && isNotEmpty()

    @CreatorsDsl
    override fun hashCode() = idenOf()

    @CreatorsDsl
    override fun toString() = toMapNames().toSafeString()

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is ConstrainedOnceIterator<*> -> this === other || toMapNames() == other.toMapNames()
        else -> false
    }

    @CreatorsDsl
    override fun toMapNames() = dictOf("type" to nameOf(), "done" to isDone(), "empty" to isEmpty(), "valid" to isValid(), "constrained" to isConstrained())
}