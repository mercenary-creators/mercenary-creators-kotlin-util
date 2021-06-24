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

@IgnoreForSerialize
enum class FitzpatrickExtensionType(text: String) : UnicodeString, Validated {

    @CreatorsDsl
    UNDEFINED(EMPTY_STRING),

    @CreatorsDsl
    TYPE_1_2("\uD83C\uDFFB"),

    @CreatorsDsl
    TYPE_3("\uD83C\uDFFC"),

    @CreatorsDsl
    TYPE_4("\uD83C\uDFFD"),

    @CreatorsDsl
    TYPE_5("\uD83C\uDFFE"),

    @CreatorsDsl
    TYPE_6("\uD83C\uDFFF");

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getUnicode(): String {
        return unicode
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isValid(): Boolean {
        return this != UNDEFINED && isNotEmpty()
    }

    @FrameworkDsl
    override fun sizeOf(): Int {
        return getUnicode().sizeOf()
    }

    @FrameworkDsl
    private val unicode = text

    companion object {

        @JvmStatic
        @FrameworkDsl
        fun fromText(text: CharSequence): FitzpatrickExtensionType {
            if (text.isEmptyOrBlank()) {
                return UNDEFINED
            }
            values().forEach { type ->
                if (type.getUnicode().isSameAs(text)) {
                    return type
                }
            }
            return UNDEFINED
        }

        @JvmStatic
        @FrameworkDsl
        fun fromType(type: CharSequence): FitzpatrickExtensionType {
            if (type.isEmptyOrBlank()) {
                return UNDEFINED
            }
            return when (type.toUpperTrimEnglish()) {
                "3", "TYPE_3" -> TYPE_3
                "4", "TYPE_4" -> TYPE_4
                "5", "TYPE_5" -> TYPE_5
                "6", "TYPE_6" -> TYPE_6
                "1", "2", "TYPE_1", "TYPE_2", "TYPE_1_2" -> TYPE_1_2
                else -> UNDEFINED
            }
        }

        @JvmStatic
        @FrameworkDsl
        fun fromCode(code: FitzpatrickExtensionCode): FitzpatrickExtensionType {
            return when (code.boxIn(0, 6)) {
                1 -> TYPE_1_2
                2 -> TYPE_1_2
                3 -> TYPE_3
                4 -> TYPE_4
                5 -> TYPE_5
                6 -> TYPE_6
                else -> UNDEFINED
            }
        }
    }
}