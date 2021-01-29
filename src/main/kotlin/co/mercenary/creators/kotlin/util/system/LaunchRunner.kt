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

package co.mercenary.creators.kotlin.util.system

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.io.*

@IgnoreForSerialize
interface LaunchRunner : OpenAutoClosable, InputStreamSupplier, OutputStreamSupplier, ErrorInputStreamSupplier, Alive, HasMapNames {

    @CreatorsDsl
    fun codeOf(): Int

    @CreatorsDsl
    fun waitOn(): Int

    @CreatorsDsl
    fun waitOn(time: TimeDuration): Boolean

    @CreatorsDsl
    fun destroy(force: Boolean = true): Boolean

    @CreatorsDsl
    @IgnoreForSerialize
    fun getCommandLine(): List<String>

    @CreatorsDsl
    @IgnoreForSerialize
    fun getEnvironment(): Map<String, String>

    @CreatorsDsl
    fun isUnknownCode(code: Int): Boolean = code == UNKNOWN_CODE

    @CreatorsDsl
    fun isSuccessCode(code: Int): Boolean = code == SUCCESS_CODE

    companion object {

        @CreatorsDsl
        const val SUCCESS_CODE = 0

        @CreatorsDsl
        const val UNKNOWN_CODE = Int.MIN_VALUE
    }
}