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

package co.mercenary.creators.kotlin.util.json.base

import co.mercenary.creators.kotlin.util.*
import com.fasterxml.jackson.core.PrettyPrinter

@IgnoreForSerialize
interface JSONFormatter : StandardInterfaces<JSONFormatter> {

    @FrameworkDsl
    @IgnoreForSerialize
    fun isPretty(): Boolean

    @FrameworkDsl
    @IgnoreForSerialize
    fun isNotPretty(): Boolean = isPretty().isNotTrue()

    @FrameworkDsl
    @IgnoreForSerialize
    fun setPretty(pretty: Boolean = true): JSONFormatter

    @FrameworkDsl
    @IgnoreForSerialize
    fun setPrettyPrinter(pretty: PrettyPrinter): JSONFormatter

    @FrameworkDsl
    @IgnoreForSerialize
    fun isEscaped(): Boolean

    @FrameworkDsl
    @IgnoreForSerialize
    fun isNotEscaped(): Boolean = isEscaped().isNotTrue()

    @FrameworkDsl
    @IgnoreForSerialize
    fun setEscaped(escape: Boolean = true): JSONFormatter

    @FrameworkDsl
    fun toJSONString(data: Any, escape: Boolean = false): String
}