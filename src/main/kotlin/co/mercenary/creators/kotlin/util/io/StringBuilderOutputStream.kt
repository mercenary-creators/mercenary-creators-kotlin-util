/*
 * Copyright (c) 2019, Mercenary Creators Company. All rights reserved.
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

package co.mercenary.creators.kotlin.util.io


import org.apache.commons.io.output.*
import java.nio.charset.Charset

class StringBuilderOutputStream(private val builder: StringBuilder = StringBuilder(DEFAULT_BUFFER_SIZE), charset: Charset = Charsets.UTF_8) : WriterOutputStream(StringBuilderWriter(builder), charset) {
    fun getBuilder(): StringBuilder = builder
    override fun toString(): String = getBuilder().toString()
}