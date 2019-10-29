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

    private val form = TimeAndDate.getThreadLocalDefaultDateFormat()

    protected fun getTempFileNamed(name: String, suff: String): File {
        return getTempFile(name, suff)
    }

    protected fun getTempFileNamedPath(name: String, suff: String): String {
        return getTempFileNamed(name, suff).path
    }

    protected fun getContentResourceByteURL(resource: ContentResource): String = buildString {
        append(IO.PREFIX_BYTES)
            .append(resource.getContentPath().trim())
            .append(IO.COL_SEPARATOR_CHAR)
            .append(resource.getContentType().trim())
            .append(IO.COL_SEPARATOR_CHAR)
            .append(Encoders.hex().encode(resource.getContentData()))
    }

    protected fun getContentResourceDetails(resource: ContentResource): String = buildString {
        append(resource.getDescription())
            .append(dets("good", resource.isContentThere()))
            .append(dets("size", resource.getContentSize()))
            .append(date("date", resource.getContentTime()))
            .append(date("time"))
    }

    protected fun dets(text: String, args: Any): String {
        return ", $text($args)"
    }

    @JvmOverloads
    protected fun date(text: String, args: Long = getTimeStamp()): String {
        return dets(text, form.get().format(args.toDate()))
    }
}