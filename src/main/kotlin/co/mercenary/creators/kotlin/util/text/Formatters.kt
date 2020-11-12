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

package co.mercenary.creators.kotlin.util.text

import co.mercenary.creators.kotlin.util.*

object Formatters {

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun getFormatterList(load: ClassLoader? = null): List<StringFormatterService> {
        return try {
            ServiceLoading.loaderOf(StringFormatterService::class, load).toReverseSortedListOf()
        }
        catch (cause: Throwable) {
            toListOf()
        }
    }

    private val list: List<StringFormatterService> by lazy {
        getFormatterList()
    }

    @JvmStatic
    @CreatorsDsl
    fun toSafeString(func: () -> Any?): String {
        return try {
            when (val data = func.invoke()) {
                null -> NULLS_STRING
                else -> {
                    list.forEach {
                        if (it.isValidClass(data)) {
                            return it.toSafeString(data)
                        }
                    }
                    data.toString()
                }
            }
        }
        catch (cause: Throwable) {
            "toSafeString() failed ${cause.message}"
        }
    }
}