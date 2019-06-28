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

package co.mercenary.creators.kotlin.util.test

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.io.*
import java.io.File

abstract class AbstractDataTest : AbstractKotlinTest() {

    protected fun getTempFileNamed(name: String, suff: String): File {
        return getTempFile(name, suff)
    }

    protected fun getTempFileNamedPath(name: String, suff: String): String {
        return getTempFileNamed(name, suff).path
    }

    protected fun getContentResourceByteURL(resource: ContentResource): String {
        return StringBuilder(IO.PREFIX_BYTES).also {
            it.append(resource.getContentPath().trim())
            it.append(IO.COL_SEPARATOR_CHAR)
            it.append(resource.getContentType().trim())
            it.append(IO.COL_SEPARATOR_CHAR)
            it.append(Encoders.hex().encode(resource.getContentData()))
        }.toString()
    }

    protected fun getContentResourceDetails(resource: ContentResource): String {
        return StringBuilder().also {
            it.append(resource)
        }.toString()
    }
}