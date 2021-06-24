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

class JSONEmoji @FrameworkDsl constructor(private val unicode: String, private val description: String, private val fitzpatrick: Boolean, tags: List<String>, aliases: List<String>) : Emoji {

    @FrameworkDsl
    private val look = tags.unique().toSorted()

    @FrameworkDsl
    private val nick = aliases.unique().toSorted()

    @FrameworkDsl
    override fun getTags(): List<String> {
        return look
    }

    @FrameworkDsl
    override fun getAliases(): List<String> {
        return nick
    }

    @FrameworkDsl
    override fun getUnicode(code: FitzpatrickExtensionCode): String {
        if (isNotFitzpatrick() || code.isNotValid()) {
            return getUnicode()
        }
        return getUnicode(code.toFitzpatrickExtensionType())
    }

    @FrameworkDsl
    override fun getUnicode(type: FitzpatrickExtensionType): String {
        if (isNotFitzpatrick() || type.isNotValid()) {
            return getUnicode()
        }
        return getUnicode() + type.getUnicode()
    }

    @FrameworkDsl
    override fun getDescription(): String {
        return description.copyOf()
    }

    @FrameworkDsl
    override fun isFitzpatrick(): Boolean {
        return fitzpatrick.copyOf()
    }

    @FrameworkDsl
    override fun getUnicode(): String {
        return unicode.copyOf()
    }

    @FrameworkDsl
    override fun sizeOf(): Int {
        return getUnicode().sizeOf()
    }

    @FrameworkDsl
    override fun toString(): String {
        return toJSONString()
    }

    @FrameworkDsl
    override fun hashCode() = idenOf()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is JSONEmoji -> this === other || getUnicode() == other.getUnicode() && getDescription() == other.getDescription() && isFitzpatrick() == other.isFitzpatrick() && getTags() isSameAs other.getTags() && getAliases() isSameAs other.getAliases()
        else -> false
    }
}