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

package co.mercenary.creators.kotlin.util.test.json

import co.mercenary.creators.kotlin.util.*
import kotlinx.coroutines.reactive.asFlow
import org.junit.jupiter.api.Test

class JSONTest : KotlinDataTest() {

    @Test
    fun test() {
        getJSONFormatter().setEscaped(false).toJSONString(JSONArray().append(CODE_STRING).append(HORZ_STRING)).also {
            info { it }
        }
        dashes()
        dictOf("name" to author, "time" to 56.Weeks, "text" to CODE_STRING, "oops" to null).toDataType<Map<String, String?>>().filterValues { it != null }.also {
            error { it }
        }
        getJSONFormatter().toJSONString(dictOf("name" to author, "time" to 56.Weeks, "text" to CODE_STRING)).also {
            info { it }
        }
        dashes()
        getJSONFormatter().toJSONString(dictOf("name" to author, "time" to 56.Weeks, "text" to CODE_STRING), true).also {
            info { it }
        }
        dashes()
        getJSONFormatter().toJSONString(56.Weeks).also {
            info { it }
        }
        dashes()
        json("name" to author, "time" to 56.Weeks, "text" to CODE_STRING).toJSONString().toJSONReader<JSONObject>().readOf().toString().also {
            info { it }
        }
        json("name" to author, "time" to 56.Weeks, "text" to CODE_STRING).append("date" to dateOf(), "horz" to HORZ_STRING).toJSONString().toJSONReader<JSONObject>().readOf().toString().also {
            info { it }
        }
        dashes()
        error {
            HORZ_STRING.codePointsOf()
        }
        error {
            HORZ_STRING.sizeOf()
        }
        dashes()
        error {
            CODE_STRING.codePointsOf()
        }
        error {
            CODE_STRING.sizeOf()
        }
        dashes()
        error {
            DUNNO_STRING.codePointsOf()
        }
        error {
            DUNNO_STRING.sizeOf()
        }
        getEmojiManager().getEmojiList().sizeOf().also {
            warn { it }
        }
        dashes()
        if (isSystemWindows()) {
            getEmojiManager().getEmojiList().withEachIndexed { index, emoji ->
                dashes()
                warn { index }
                warn { emoji.toJSONString() }
            }
        }
        getJSONFormatter().setEscaped().toJSONString(getEmojiManager().getEmojiList()).also {
            debug { it }
        }
        getEmojiManager().getEmojiList().toSorted().toFlux().asFlow()
        getProcessors().also { size ->
            ParallelReactiveScheduler(uuid(), size).also { on ->
                getEmojiManager().getEmojiList().toSorted().toFlux().parallel(size).runOn(on).map {
                    stringOf {
                        add(it.id()).add(", [").add(getCurrentThreadName()).add("]")
                    }
                }.sequential(size).toList().also {
                    on.close()
                }.also {
                    error { it }
                }
            }
        }
    }
}