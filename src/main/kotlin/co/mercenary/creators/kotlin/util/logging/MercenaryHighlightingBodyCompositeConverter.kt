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

package co.mercenary.creators.kotlin.util.logging

import ch.qos.logback.classic.spi.ILoggingEvent
import co.mercenary.creators.kotlin.util.*

open class MercenaryHighlightingBodyCompositeConverter : MercenaryHighlightingCompositeConverter() {

    @CreatorsDsl
    override fun transform(event: ILoggingEvent, data: String?): String {
         getForegroundColorCode(event).also { color ->
             return stringOf {
                 when {
                     data == null -> {
                         add(START, color, CLOSE, NULLS_STRING, RESET)
                     }
                     data.contains(BREAK_STRING) -> {
                         val list = data.split(BREAK_STRING)
                         list.forEachIndexed { line, text ->
                             add(START, color, CLOSE, text, if (line < list.lastIndex) BREAK_STRING else EMPTY_STRING, RESET)
                         }
                     }
                     else -> {
                         add(START, color, CLOSE, data, RESET)
                     }
                 }
             }
        }

    }
}