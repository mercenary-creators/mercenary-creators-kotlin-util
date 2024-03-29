/*
 * Copyright (c) 2023, Mercenary Creators Company. All rights reserved.
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

package co.mercenary.creators.kotlin.util.logging

import co.mercenary.creators.kotlin.util.*

@IgnoreForSerialize
object LoggingConfig {

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    internal fun getLoggingConfigServiceList(load: ClassLoader? = null): List<LoggingConfigService> {
        return try {
            ServiceLoading.loaderOf(LoggingConfigService::class, load).toSorted()
        } catch (cause: Throwable) {
            toListOf()
        }
    }

    @FrameworkDsl
    private val list: List<LoggingConfigService> by lazy {
        getLoggingConfigServiceList()
    }

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getIgnoring(): List<String> {
        return list.flatMapTo(ArrayList()) { it.getIgnoring() }.uniqueTrimmedOf()
    }

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun isAutoStart(): Boolean = list.last().isAutoStart()

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun isAutoClose(): Boolean = list.last().isAutoClose()

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun isBridgeing(): Boolean = list.last().isBridgeing()
}