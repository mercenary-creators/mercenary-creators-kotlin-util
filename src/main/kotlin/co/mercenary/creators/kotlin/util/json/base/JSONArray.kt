/*
 * Copyright (c) 2022, Mercenary Creators Company. All rights reserved.
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

package co.mercenary.creators.kotlin.util.json.base

import co.mercenary.creators.kotlin.util.*

class JSONArray : BasicArrayList<Maybe>, JSONBase<Int, JSONArray> {

    @FrameworkDsl
    constructor() : super()

    @FrameworkDsl
    constructor(vararg args: Maybe) : super(args.toCollection())

    @FrameworkDsl
    constructor(args: Iterator<Maybe>) : super(args.toCollection())

    @FrameworkDsl
    constructor(args: Iterable<Maybe>) : super(args.toCollection())

    @FrameworkDsl
    constructor(args: Sequence<Maybe>) : super(args.toCollection())

    @FrameworkDsl
    constructor(args: JSONArray, deep: Boolean) : super(args.copyOf(deep))

    @FrameworkDsl
    override val size: Int
        @IgnoreForSerialize
        get() = sizeOf()

    @FrameworkDsl
    override fun clone() = copyOf(true)

    @FrameworkDsl
    override fun copyOf() = copyOf(false)

    @FrameworkDsl
    override fun copyOf(deep: Boolean): JSONArray {
        if (sizeOf() == 0) {
            return JSONArray()
        }
        return when (deep) {
            true -> toDeepCopy()
            else -> JSONArray(this)
        }
    }

    @FrameworkDsl
    override fun findOf(look: Int) = get(look)

    @FrameworkDsl
    override infix fun isDefined(look: Int) = isNotEmpty() && look in 0 until sizeOf()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is JSONArray -> this === other || sizeOf() == other.sizeOf() && super.equals(other)
        else -> false
    }

    @FrameworkDsl
    override fun hashCode() = hashOf()

    @FrameworkDsl
    override fun toString() = toJSONString()

    companion object {

        private const val serialVersionUID = 2L
    }
}