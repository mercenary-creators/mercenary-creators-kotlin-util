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

@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")
package co.mercenary.creators.kotlin.util.security

import co.mercenary.creators.kotlin.util.*

@IgnoreForSerialize
object SecureAccess {

    @FrameworkDsl
    private val open = getAtomicNotTrue()

    @FrameworkDsl
    private val list = BasicArrayList<() -> Unit>(2)

    @JvmStatic
    @FrameworkDsl
    @Synchronized
    private fun doExit() {
        if (list.isNotEmpty()) {
            list.reversed().forEach {
                try {
                    it.invoke()
                } catch (cause: Throwable) {
                    Throwables.thrown(cause)
                }
            }
        }
        list.clear()
    }

    @JvmStatic
    @FrameworkDsl
    @Synchronized
    @JvmOverloads
    fun onExitOfProcess(push: Boolean = false, func: () -> Unit) {
        if (open.isFalseToTrue()) {
            ExitThread(nameOf()) {
                doExit()
            }
        }
        if (push.isTrue()) {
            list.push(func)
        } else {
            list.append(func)
        }
    }

    private class ExitThread @FrameworkDsl constructor(name: String, task: () -> Unit) : Thread(task, name) {
        init {
            Runtime.getRuntime().addShutdownHook(this)
        }
    }
}