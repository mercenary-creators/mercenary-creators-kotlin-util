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

package co.mercenary.creators.kotlin.util.json.text.emoji

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.json.base.JSONStringAware

interface Emoji : UnicodeString, JSONStringAware, Comparable<Emoji> {

    @FrameworkDsl
    fun id(): Long

    @FrameworkDsl
    fun getTags(): List<String>

    @FrameworkDsl
    fun getAliases(): List<String>

    @FrameworkDsl
    fun getDescription(): String

    @FrameworkDsl
    fun isFitzpatrick(): Boolean

    @FrameworkDsl
    fun getUnicode(code: FitzpatrickExtensionCode): String

    @FrameworkDsl
    fun getUnicode(type: FitzpatrickExtensionType): String
}