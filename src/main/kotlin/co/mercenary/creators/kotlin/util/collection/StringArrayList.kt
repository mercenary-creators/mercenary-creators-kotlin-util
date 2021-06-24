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

class StringArrayList @JvmOverloads @FrameworkDsl constructor(capacity: Int = DEFAULT_LIST_CAPACITY) : AbstractBasicMutableList<String, StringArrayList>(capacity) {

    @FrameworkDsl
    constructor(args: String) : this(DEFAULT_LIST_CAPACITY) {
        append(args)
    }

    @FrameworkDsl
    constructor(args: Collection<String>) : this(args.sizeOf()) {
        if (args.isNotExhausted()) {
            append(args)
        }
    }

    @FrameworkDsl
    constructor(vararg args: String) : this(args.toCollection())

    @FrameworkDsl
    constructor(args: Iterator<String>) : this(args.toCollection())

    @FrameworkDsl
    constructor(args: Iterable<String>) : this(args.toCollection())

    @FrameworkDsl
    constructor(args: Sequence<String>) : this(args.toCollection())

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = StringArrayList(this)

    @FrameworkDsl
    override fun hashCode() = super.hashCode()

    @FrameworkDsl
    override fun toString() = super.toString()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is StringArrayList -> other === this || sizeOf() == other.sizeOf() && super.equals(other)
        else -> false
    }

    companion object {

        private const val serialVersionUID = 6L
    }
}