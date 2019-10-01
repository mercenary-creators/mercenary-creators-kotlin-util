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

import co.mercenary.creators.kotlin.util.*

import java.net.URL

@SerialIgnore
class URLContentResource @JvmOverloads constructor(internal val data: URL, private val type: String = DEFAULT_CONTENT_TYPE) : AbstractContentResource(data.toString(), type), OutputContentResource {

    override fun isContentThere() = IO.isContentThere(data)
    override fun getContentSize() = IO.getContentSize(data)
    override fun getContentTime() = IO.getContentTime(data)
    override fun getContentType() = IO.getContentType(data, type)

    @SerialIgnore
    override fun getInputStream() = data.toInputStream()

    @SerialIgnore
    override fun getOutputStream() = data.toOutputStream()

    override fun toString() = getDescription()

    override fun equals(other: Any?) = when (other) {
        is URLContentResource -> data == other.data
        else -> false
    }

    override fun hashCode() = data.hashCode()
}