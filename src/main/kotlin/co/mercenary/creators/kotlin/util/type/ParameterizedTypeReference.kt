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

package co.mercenary.creators.kotlin.util.type

import co.mercenary.creators.kotlin.util.*
import java.lang.reflect.Type

abstract class ParameterizedTypeReference<T> : Comparable<ParameterizedTypeReference<T>> {

    @FrameworkDsl
    private val type = getParameterizedType(javaClass)

    @FrameworkDsl
    @IgnoreForSerialize
    fun getType(): Type {
        return type
    }

    @FrameworkDsl
    override operator fun compareTo(other: ParameterizedTypeReference<T>): Int {
        return 0
    }

    @FrameworkDsl
    override fun toString(): String {
        return type.typeName
    }

    protected companion object {

        @JvmStatic
        @FrameworkDsl
        fun getParameterizedType(kind: Class<*>): Type {
            return TypeTools.getParameterizedType(kind)
        }
    }
}