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
import java.util.*

interface JSONAccess<A> : MutableSizedContainer {

    @FrameworkDsl
    val size: Int

    @FrameworkDsl
    fun findOf(look: A): Maybe

    @FrameworkDsl
    infix fun isDefined(look: A): Boolean

    @FrameworkDsl
    infix fun isNull(look: A) = JSONStatic.isNull(accessOf(look))

    @FrameworkDsl
    infix fun isLong(look: A) = JSONStatic.isLong(accessOf(look))

    @FrameworkDsl
    infix fun isDate(look: A) = JSONStatic.isDate(accessOf(look))

    @FrameworkDsl
    infix fun isArray(look: A) = JSONStatic.isArray(accessOf(look))

    @FrameworkDsl
    infix fun isObject(look: A) = JSONStatic.isObject(accessOf(look))

    @FrameworkDsl
    infix fun isNumber(look: A) = JSONStatic.isNumber(accessOf(look))

    @FrameworkDsl
    infix fun isString(look: A) = JSONStatic.isString(accessOf(look))

    @FrameworkDsl
    infix fun isDouble(look: A) = JSONStatic.isDouble(accessOf(look))

    @FrameworkDsl
    infix fun isInteger(look: A) = JSONStatic.isInteger(accessOf(look))

    @FrameworkDsl
    infix fun isBoolean(look: A) = JSONStatic.isBoolean(accessOf(look))

    @FrameworkDsl
    infix fun isFunction(look: A) = JSONStatic.isFunction(accessOf(look))

    @FrameworkDsl
    fun getElementType(look: A) = JSONStatic.getElementType(accessOf(look))

    @FrameworkDsl
    fun asLong(look: A): Long? = JSONStatic.asLong(accessOf(look))

    @FrameworkDsl
    fun asDate(look: A): Date? = JSONStatic.asDate(accessOf(look))

    @FrameworkDsl
    fun asInteger(look: A): Int? = JSONStatic.asInteger(accessOf(look))

    @FrameworkDsl
    fun asDouble(look: A): Double? = JSONStatic.asDouble(accessOf(look))

    @FrameworkDsl
    fun asString(look: A): String? = JSONStatic.asString(accessOf(look))

    @FrameworkDsl
    fun asBoolean(look: A): Boolean? = JSONStatic.asBoolean(accessOf(look))

    @FrameworkDsl
    fun asArray(look: A): JSONArray? = JSONStatic.asArray(accessOf(look))

    @FrameworkDsl
    fun asObject(look: A): JSONObject? = JSONStatic.asObject(accessOf(look))

    @FrameworkDsl
    fun accessOf(look: A): Maybe = if (isDefined(look)) findOf(look) else null
}