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

package co.mercenary.creators.kotlin.util.json.module

import co.mercenary.creators.kotlin.util.*
import com.fasterxml.jackson.core.*
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.std.StdDeserializer

@FrameworkDsl
@IgnoreForSerialize
object TimeDurationDeserializer : StdDeserializer<TimeDuration>(TimeDuration::class.java) {
    override fun deserialize(parser: JsonParser, context: DeserializationContext): TimeDuration {
        return when (parser.currentToken) {
            JsonToken.VALUE_STRING -> {
                val text = parser.text
                try {
                    TimeDuration.parse(text)
                }
                catch (cause: Throwable) {
                    Throwables.thrown(cause)
                    throw JsonMappingException(parser, "error string for TimeDuration($text)", cause)
                }
            }
            else -> throw JsonMappingException(parser, "not a string for TimeDuration")
        }
    }
}