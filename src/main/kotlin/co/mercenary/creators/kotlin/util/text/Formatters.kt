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

package co.mercenary.creators.kotlin.util.text

import co.mercenary.creators.kotlin.util.*

@IgnoreForSerialize
object Formatters {

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    @IgnoreForSerialize
    fun getFormatterList(load: ClassLoader? = null): List<StringFormatterService> {
        return try {
            ServiceLoading.loaderOf(StringFormatterService::class, load).toSorted(true)
        } catch (cause: Throwable) {
            toListOf()
        }
    }

    @FrameworkDsl
    private val list: List<StringFormatterService> by lazy {
        getFormatterList()
    }

    @JvmStatic
    @FrameworkDsl
    private fun safe(data: Any?): String {
        return when (data) {
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

    @JvmStatic
    @FrameworkDsl
    fun toSafeString(func: LazyMessage): String {
        return try {
            safe(func.create())
        } catch (cause: Throwable) {
            cause.printStackTrace(getStandardError())
            "toSafeString() failed ${cause.nameOf()}"
        }
    }
}