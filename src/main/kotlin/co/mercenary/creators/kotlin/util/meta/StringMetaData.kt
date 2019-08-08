/*
 * Copyright (c) 2019, Mercenary Creators Company. All rights reserved.
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

package co.mercenary.creators.kotlin.util.meta

import co.mercenary.creators.kotlin.util.type.*

class StringMetaData : AbstractMutableMetaData<String> {
    constructor() : super()
    constructor(size: Int) : super(size)
    constructor(args: MetaDataSupplier) : super(args)
    constructor(args: Map<String, String>) : super(args)
    constructor(vararg args: Pair<String, String>) : super(*args)
    constructor(args: Iterable<Pair<String, String>>) : super(args)
    constructor(args: Sequence<Pair<String, String>>) : super(args)

    override fun toMETAString(pretty: Boolean): String {
        val pads = padding()
        val buff = StringBuilder("{")
        val seps = if (pretty) " : " else ":"
        for ((k, v) in this) {
            if (pretty) {
                buff.append(Escapers.NL).append(pads)
            }
            buff.append(Escapers.escape(transformers.keys.encode(k))).append(seps).append(Escapers.escape(transformers.vals.encode(v))).append(",")
        }
        if (isNotEmpty()) {
            buff.setLength(buff.length - 1)
        }
        if (pretty) {
            buff.append(Escapers.NL)
        }
        return buff.append("}").toString()
    }

    override fun toString() = toMETAString()

    override fun hashCode() = hash(this)

    override fun equals(other: Any?): Boolean = when (other) {
        is StringMetaData -> same(this, other)
        else -> false
    }
}