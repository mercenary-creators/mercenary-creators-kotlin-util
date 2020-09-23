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

package co.mercenary.creators.kotlin.util.logging

import co.mercenary.creators.kotlin.util.*

@IgnoreForSerialize
object LoggingConfig {

    @JvmStatic
    @CreatorsDsl
    @JvmOverloads
    fun getLoggingConfigServiceList(load: ClassLoader? = null): List<LoggingConfigService> {
        return try {
            ServiceLoading.loaderOf(LoggingConfigService::class, load).sorted().toList()
        }
        catch (cause: Throwable) {
            listOf()
        }
    }

    private val list: List<LoggingConfigService> by lazy {
        getLoggingConfigServiceList()
    }

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun getIgnoring(): List<String> {
        return list.flatMapTo(ArrayList()) { it.getIgnoring() }.uniqueTrimmedOf()
    }

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun isAutoStart(): Boolean = list.last().isAutoStart()

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun isAutoClose(): Boolean = list.last().isAutoClose()
}