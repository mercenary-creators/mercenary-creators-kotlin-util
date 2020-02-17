/*
 * Copyright (c) 2020, Mercenary Creators Company. All rights reserved.
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

import co.mercenary.creators.kotlin.util.*

abstract class AbstractContentResourceBase @JvmOverloads constructor(private val path: String, private val type: String = DEFAULT_CONTENT_TYPE, private val time: Long = 0L) : ContentResource {

    private val kind: String by lazy {
        getContentKind().toLowerTrim().let { if (getContentPath().startsWith(it, true)) EMPTY_STRING else it }
    }

    private val desc: String by lazy {
        "${javaClass.name}($kind${getContentPath()}, ${getContentType()}, ${isContentCache()})"
    }

    override fun getContentPath() = path
    override fun getContentTime() = time
    override fun getDescription() = desc
    override fun getContentType() = type.toLowerTrim()
}