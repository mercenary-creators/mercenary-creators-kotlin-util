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

import co.mercenary.creators.kotlin.util.*
import java.security.*

@IgnoreForSerialize
object SecureAccess {

    private val open = false.toAtomic()

    private val list = ArrayList<() -> Unit>(2)

    @JvmStatic
    @CreatorsDsl
    @Synchronized
    private fun doExit() {
        if (list.isNotEmpty()) {
            list.reversed().also { list.clear() }.forEach {
                try {
                    it.invoke()
                }
                catch (cause: Throwable) {
                }
            }
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun <T : Any> priviledgedActionOf(func: () -> T): T {
        return when (System.getSecurityManager()) {
            null -> func.invoke()
            else -> AccessController.doPrivileged(PrivilegedAction { func.invoke() })
        }
    }

    @JvmStatic
    @CreatorsDsl
    @Synchronized
    fun onExitOfProcess(func: () -> Unit) {
        if (open.isFalseToTrue()) {
            object : Thread() {
                override fun run() {
                    doExit()
                }
            }.also { Runtime.getRuntime().addShutdownHook(it) }
        }
        list += func
    }
}