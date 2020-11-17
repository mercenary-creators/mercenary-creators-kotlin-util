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

package co.mercenary.creators.kotlin.util

class MaybeValued<T> @CreatorsDsl internal constructor(value: T?) : StandardInterfaces<MaybeValued<T>> {

    @CreatorsDsl
    internal constructor(value: MaybeValued<T>) : this(value.maybe)

    @FrameworkDsl
    private var maybe: T? = value

    @CreatorsDsl
    override fun clone() = copyOf()

    @CreatorsDsl
    override fun copyOf() = MaybeValued(this)

    @CreatorsDsl
    @IgnoreForSerialize
    fun getValue(): T {
        return maybe ?: throw MercenaryFatalExceptiion("no value present")
    }

    @CreatorsDsl
    @IgnoreForSerialize
    fun setValue(value: T): MaybeValued<T> {
        maybe = value
        return this
    }

    @CreatorsDsl
    @IgnoreForSerialize
    fun isPresent(): Boolean = maybe != null

    @CreatorsDsl
    @IgnoreForSerialize
    fun isNotPresent(): Boolean = isPresent().isNotTrue()

    @CreatorsDsl
    override fun toString() = toMapNames().toSafeString()

    @CreatorsDsl
    override fun hashCode() = idenOf() * 31 + maybe.hashOf()

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is MaybeValued<*> -> other === this || maybe isSameAs other.maybe
        else -> false
    }

    @CreatorsDsl
    override fun toMapNames() = dictOf("type" to nameOf(), "present" to isPresent(), "value" to maybe)

    @CreatorsDsl
    fun filter(filter: (T) -> Boolean): MaybeValued<T> {
        if (isNotPresent()) {
            return this
        }
        return if (filter(maybe!!)) this else empty()
    }

    @CreatorsDsl
    fun onPresent(action: (T) -> Unit) {
        if (isPresent()) {
            action(maybe!!)
        }
    }

    @CreatorsDsl
    fun <M> mapper(mapper: (T) -> M?): MaybeValued<M> {
        if (isNotPresent()) {
            return empty()
        }
        return nullable(mapper(maybe!!))
    }

    companion object {

        @CreatorsDsl
        private val EMPTY = MaybeValued(null)

        @JvmStatic
        @CreatorsDsl
        fun <T> empty(): MaybeValued<T> = EMPTY as MaybeValued<T>

        @JvmStatic
        @CreatorsDsl
        fun <T> of(value: T): MaybeValued<T> = MaybeValued(value)

        @JvmStatic
        @CreatorsDsl
        fun <T> nullable(value: T?): MaybeValued<T> = if (value == null) empty() else of(value)
    }
}