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

package co.mercenary.creators.kotlin.util.logging

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.collection.StringSet

@IgnoreForSerialize
abstract class LoggingConfigService(protected val order: Int) : Comparable<LoggingConfigService> {

    @FrameworkDsl
    @IgnoreForSerialize
    open fun isAutoStart(): Boolean = false

    @FrameworkDsl
    @IgnoreForSerialize
    open fun isAutoClose(): Boolean = false

    @FrameworkDsl
    @IgnoreForSerialize
    open fun isBridgeing(): Boolean = false

    @FrameworkDsl
    @IgnoreForSerialize
    abstract fun getIgnoring(): List<String>

    @FrameworkDsl
    override operator fun compareTo(other: LoggingConfigService): Int {
        return order.compareTo(other.order)
    }

    companion object {

        @JvmStatic
        @FrameworkDsl
        fun toIgnoring(args: String): List<String> {
            return args.toTrimOr(EMPTY_STRING).let { if (it.isEmptyOrBlank()) toListOf() else toListOf(it) }
        }

        @JvmStatic
        @FrameworkDsl
        fun toIgnoring(vararg args: String): List<String> {
            if (args.isExhausted()) {
                return toListOf()
            }
            return StringSet(args.mapNotNull { toTrimOrNull(it) }).getList()
        }
    }
}