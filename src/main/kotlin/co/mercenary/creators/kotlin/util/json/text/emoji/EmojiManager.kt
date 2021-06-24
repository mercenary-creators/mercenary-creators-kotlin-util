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

package co.mercenary.creators.kotlin.util.json.text.emoji

import co.mercenary.creators.kotlin.util.*

@FrameworkDsl
@IgnoreForSerialize
object EmojiManager {

    @FrameworkDsl
    private val list by lazy {
        parse()
    }

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getEmojiList(): List<Emoji> {
        return list
    }

    @JvmStatic
    @FrameworkDsl
    fun isEmoji(text: CharSequence): Boolean {
        if (text.isEmptyOrBlank() || Escapers.isAscii(text)) {
            return false
        }
        return false
    }

    @JvmStatic
    @FrameworkDsl
    fun findByTag(pattern: Regex): List<Emoji> {
        val list = BasicArrayList<Emoji>()
        getEmojiList().withEach { emoji ->
            emoji.getTags().withEach { name ->
                if (pattern.matches(name)) {
                    if (list.contains(emoji).isNotTrue()) {
                        list.add(emoji)
                    }
                }
            }
        }
        return list.toReadOnly()
    }

    @JvmStatic
    @FrameworkDsl
    fun findByTag(name: String): List<Emoji> {
        if (name.isEmptyOrBlank()) {
            return toListOf()
        }
        val list = BasicArrayList<Emoji>()
        getEmojiList().withEach { emoji ->
            if (name in emoji.getTags()) {
                list.add(emoji)
            }
        }
        return list.toReadOnly()
    }

    @JvmStatic
    @FrameworkDsl
    fun findByAlias(pattern: Regex): List<Emoji> {
        val list = BasicArrayList<Emoji>()
        getEmojiList().withEach { emoji ->
            emoji.getAliases().withEach { alias ->
                if (pattern.matches(alias)) {
                    if (list.contains(emoji).isNotTrue()) {
                        list.add(emoji)
                    }
                }
            }
        }
        return list.toReadOnly()
    }

    @JvmStatic
    @FrameworkDsl
    fun findByAlias(alias: String): List<Emoji> {
        if (alias.isEmptyOrBlank()) {
            return toListOf()
        }
        val list = BasicArrayList<Emoji>()
        getEmojiList().withEach { emoji ->
            if (alias in emoji.getAliases()) {
                list.add(emoji)
            }
        }
        return list.toReadOnly()
    }

    @JvmStatic
    @FrameworkDsl
    fun findByDescription(pattern: Regex): List<Emoji> {
        val list = BasicArrayList<Emoji>()
        getEmojiList().withEach { emoji ->
            if(pattern.matches(emoji.getDescription())) {
                if (list.contains(emoji).isNotTrue()) {
                    list.add(emoji)
                }
            }
        }
        return list.toReadOnly()
    }

    @JvmStatic
    @FrameworkDsl
    fun findByDescription(description: String): List<Emoji> {
        if (description.isEmptyOrBlank()) {
            return toListOf()
        }
        val list = BasicArrayList<Emoji>()
        getEmojiList().withEach { emoji ->
            if (description isSameAs emoji.getDescription()) {
                list.add(emoji)
            }
        }
        return list.toReadOnly()
    }

    @FrameworkDsl
    private fun parse(): List<Emoji> {
        return try {
            CONTENT_RESOURCE_LOADER["emojis.json"].readOf<List<JSONEmoji>>()
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
            toListOf()
        }
    }
}