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

package co.mercenary.creators.kotlin.util.security

import co.mercenary.creators.kotlin.util.*
import java.util.*
import kotlin.reflect.KClass

@IgnoreForSerialize
object ServiceLoading {

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> loaderOf(type: Class<T>, load: ClassLoader? = null): ServiceLoader<T> = serviceOf(type, load)


    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> loaderOf(type: KClass<T>, load: ClassLoader? = null): ServiceLoader<T> = serviceOf(type.java, load)

    @JvmStatic
    @FrameworkDsl
    private fun <T : Any> serviceOf(type: Class<T>, load: ClassLoader?): ServiceLoader<T> = when (load) {
        null -> ServiceLoader.load(type)
        else -> ServiceLoader.load(type, load)
    }
}