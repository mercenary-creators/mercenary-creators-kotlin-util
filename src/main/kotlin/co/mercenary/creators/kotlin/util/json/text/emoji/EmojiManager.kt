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

@file:Suppress("NOTHING_TO_INLINE", "FunctionName", "HttpUrlsUsage")

package co.mercenary.creators.kotlin.util.json.text.emoji

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.io.ClassPathContentResource

@FrameworkDsl
@IgnoreForSerialize
object EmojiManager : SizedContainer, HasMapNames {

    @CreatorsDsl
    internal val list: List<Emoji> by lazy {
        parse()
    }

    @CreatorsDsl
    internal inline fun listOf(): List<Emoji> {
        return list
    }

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to nameOf(), "size" to sizeOf())

    @FrameworkDsl
    override fun toString(): String {
        return toMapNames().toSafeString()
    }

    @FrameworkDsl
    override fun sizeOf(): Int {
        return listOf().sizeOf()
    }

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getEmojiList(): List<Emoji> {
        return listOf()
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
        if (isExhausted()) {
            return toListOf()
        }
        val list = BasicArrayList<Emoji>()
        getEmojiList().withEach { emoji ->
            emoji.getTags().withEach { name ->
                if (pattern.matches(name) && list.contains(emoji).isNotTrue()) {
                    list.add(emoji)
                }
            }
        }
        return list.toReadOnly()
    }

    @JvmStatic
    @FrameworkDsl
    fun findByTag(name: String): List<Emoji> {
        if (isExhausted() || name.isEmptyOrBlank()) {
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
        if (isExhausted()) {
            return toListOf()
        }
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
        if (isExhausted() || alias.isEmptyOrBlank()) {
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
        if (isExhausted()) {
            return toListOf()
        }
        val list = BasicArrayList<Emoji>()
        getEmojiList().withEach { emoji ->
            if (pattern.matches(emoji.getDescription())) {
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
        if (isExhausted() || description.isEmptyOrBlank()) {
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
            ClassPathContentResource("emojis.json", EmojiManager::class, JSON_CONTENT_TYPE_UTF_8).toJSONReader<List<JSONEmoji>>().readOf()
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
            toListOf()
        }
    }
}