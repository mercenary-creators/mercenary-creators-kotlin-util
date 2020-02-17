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

package co.mercenary.creators.kotlin.util.security

import java.security.*
import java.util.*
import kotlin.reflect.KClass

object ServiceLoading {

    @JvmStatic
    @JvmOverloads
    fun <T : Any> loader(type: Class<T>, load: ClassLoader? = null): ServiceLoader<T> = when (System.getSecurityManager()) {
        null -> build(type, load)
        else -> AccessController.doPrivileged(PrivilegedAction { build(type, load) })
    }

    @JvmStatic
    @JvmOverloads
    fun <T : Any> loader(type: KClass<T>, load: ClassLoader? = null): ServiceLoader<T> = loader(type.java, load)

    private fun <T : Any> build(type: Class<T>, load: ClassLoader?): ServiceLoader<T> = if (load == null) ServiceLoader.load(type) else ServiceLoader.load(type, load)
}