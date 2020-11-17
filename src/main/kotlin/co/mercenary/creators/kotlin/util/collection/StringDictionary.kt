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
import java.util.*

open class StringDictionary @JvmOverloads @CreatorsDsl constructor(capacity: Int = DEFAULT_MAP_CAPACITY, factor: Double = DEFAULT_MAP_FACTOR, order: Boolean = false) : BasicDictionaryMap<String>(capacity, factor, order) {

    @CreatorsDsl
    constructor(k: String, v: String) : this() {
        append(k, v)
    }

    @CreatorsDsl
    constructor(args: Properties) : this() {
        args.stringPropertyNames().forEach { k ->
            if (k != null) {
                args.getProperty(k).also { v ->
                    if (v != null) {
                        append(k, v)
                    }
                }
            }
        }
    }

    @CreatorsDsl
    constructor(args: Map<String, String>) : this() {
        append(args)
    }

    @CreatorsDsl
    constructor(args: Pair<String, String>) : this() {
        append(args)
    }

    @CreatorsDsl
    constructor(vararg args: Pair<String, String>) : this() {
        append(args.toIterator())
    }

    @CreatorsDsl
    constructor(args: Iterator<Pair<String, String>>) : this() {
        append(args)
    }

    @CreatorsDsl
    constructor(args: Iterable<Pair<String, String>>) : this() {
        append(args)
    }

    @CreatorsDsl
    constructor(args: Sequence<Pair<String, String>>) : this() {
        append(args)
    }

    @CreatorsDsl
    override fun clone() = copyOf()

    @CreatorsDsl
    override fun copyOf() = StringDictionary(this)

    @CreatorsDsl
    override fun hashCode() = toSafeHashUf()

    @CreatorsDsl
    override fun toString() = toSafeString()

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is StringDictionary -> this === other || super.equals(other)
        else -> false
    }

    companion object {

        private const val serialVersionUID = 5L
    }
}