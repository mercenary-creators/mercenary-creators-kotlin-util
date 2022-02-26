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

package co.mercenary.creators.kotlin.util.system

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.io.*

@IgnoreForSerialize
interface LaunchRunner : OpenAutoClosable, InputStreamSupplier, OutputStreamSupplier, ErrorInputStreamSupplier, Alive, HasMapNames {

    @FrameworkDsl
    fun codeOf(): Int

    @FrameworkDsl
    fun waitOn(): Int

    @FrameworkDsl
    fun waitOn(time: KotlinTimeDuration): Boolean

    @FrameworkDsl
    fun waitOn(time: CreatorsTimeDuration): Boolean

    @FrameworkDsl
    fun destroy(force: Boolean = true): Boolean

    @FrameworkDsl
    @IgnoreForSerialize
    fun getCommandLine(): List<String>

    @FrameworkDsl
    @IgnoreForSerialize
    fun getEnvironment(): Map<String, String>

    @FrameworkDsl
    fun isUnknownCode(code: Int): Boolean = code == UNKNOWN_CODE

    @FrameworkDsl
    fun isSuccessCode(code: Int): Boolean = code == SUCCESS_CODE

    companion object {

        @FrameworkDsl
        const val SUCCESS_CODE = 0

        @FrameworkDsl
        const val UNKNOWN_CODE = Int.MIN_VALUE
    }
}