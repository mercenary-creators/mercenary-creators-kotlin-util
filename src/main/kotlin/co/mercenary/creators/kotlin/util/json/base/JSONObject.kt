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

package co.mercenary.creators.kotlin.util.json.base

import co.mercenary.creators.kotlin.util.*

class JSONObject : BasicDictionaryMap<Maybe>, JSONBase<String, JSONObject> {

    @FrameworkDsl
    constructor() : super()

    @FrameworkDsl
    constructor(k: String, v: Maybe) : super(k to v)

    @FrameworkDsl
    constructor(args: Map<String, Maybe>) : super(args)

    @FrameworkDsl
    constructor(args: Pair<String, Maybe>) : super(args)

    @FrameworkDsl
    constructor(vararg args: Pair<String, Maybe>) : super(args.mapTo())

    @FrameworkDsl
    constructor(args: Iterator<Pair<String, Maybe>>) : super(args.mapTo())

    @FrameworkDsl
    constructor(args: Iterable<Pair<String, Maybe>>) : super(args.mapTo())

    @FrameworkDsl
    constructor(args: Sequence<Pair<String, Maybe>>) : super(args.mapTo())

    @FrameworkDsl
    override val size: Int
        @IgnoreForSerialize
        get() = sizeOf()

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = toDeepCopy()

    @FrameworkDsl
    override infix fun isDefined(look: String) = isKeyDefined(look)

    @FrameworkDsl
    override fun findOf(look: String) = get(look)

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is JSONObject -> this === other || sizeOf() == other.sizeOf() && super.equals(other)
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


