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

package co.mercenary.creators.kotlin.util.json.text

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.json.base.JSONStatic
import co.mercenary.creators.kotlin.util.text.StringFormatterService
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

@IgnoreForSerialize
class JSONStringFormatterService : StringFormatterService(1) {

    @CreatorsDsl
    override fun toSafeString(data: Any): String {
        return JSONStatic.toJSONString(data, true)
    }

    @CreatorsDsl
    override fun isValidClass(data: Any): Boolean {
        return when (data) {
            is String -> false
            is Number -> false
            is Boolean -> false
            is ByteArray -> false
            is CharArray -> false
            is Date -> false
            is LocalDateTime -> false
            is AtomicBoolean -> false
            is Function0<*> -> false
            is HasMapNames -> false
            else -> JSONStatic.canSerializeValue(data)
        }
    }
}