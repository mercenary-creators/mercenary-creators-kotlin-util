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

package co.mercenary.creators.kotlin.util.test.io

import co.mercenary.creators.kotlin.util.*
import org.junit.jupiter.api.Test

class JSONTest : KotlinDataTest() {

    @Test
    fun test() {
        dashes()
        getJSONFormatter().toJSONString(dictOf("name" to author, "time" to 56.weeks, "text" to "\uD83E\uDD70")).also {
            info { it }
        }
        dashes()
        getJSONFormatter().toJSONString(dictOf("name" to author, "time" to 56.weeks, "text" to "\uD83E\uDD70"), true).also {
            info { it }
        }
        dashes()
        getJSONFormatter().toJSONString(56.weeks).also {
            info { it }
        }
        dashes()
        json("name" to author, "time" to 56.weeks, "text" to "\uD83E\uDD70").toJSONString().toJSONReader<JSONObject>().readOf().toString().also {
            info { it }
        }
        json("name" to author, "time" to 56.weeks, "text" to "\uD83E\uDD70").append("date" to dateOf(), "horz" to "Maël Hörz\n").toJSONString().toJSONReader<JSONObject>().readOf().toString().also {
            info { it }
        }
        if (isSystemWindows()) {
            getEmojiManager().getEmojiList().withEachIndexed { index, emoji ->
                dashes()
                warn { index }
                warn { emoji.toJSONString() }
            }
        }
        getJSONFormatter().setEscaped().toJSONString(getEmojiManager().getEmojiList()).also {
            error { it }
        }
    }
}