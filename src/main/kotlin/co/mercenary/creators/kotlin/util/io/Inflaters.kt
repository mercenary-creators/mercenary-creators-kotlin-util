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

package co.mercenary.creators.kotlin.util.io

import co.mercenary.creators.kotlin.util.*
import java.io.*
import java.util.zip.*

@FrameworkDsl
@IgnoreForSerialize
object Inflaters {

    @JvmStatic
    @FrameworkDsl
    fun gzip(): Inflater = GZIPInflater

    @FrameworkDsl
    @IgnoreForSerialize
    private object GZIPInflater : Inflater {

        @FrameworkDsl
        override fun inflate(data: InputStream, copy: OutputStream) = GZIPInputStream(NoCloseInputStream(data)).use { gzip ->
            gzip.copyTo(copy).also { copy.flush() }
        }

        @FrameworkDsl
        override fun deflate(data: InputStream, copy: OutputStream) = CountSizeOutputStream(copy).let { buff ->
            GZIPOutputStream(NoCloseOutputStream(buff)).use { gzip ->
                data.copyTo(gzip)
            }
            buff.getContentSize()
        }

        @FrameworkDsl
        override fun toString() = toMapNames().toSafeString()

        @FrameworkDsl
        override fun toMapNames() = dictOf("type" to "GZIP")
    }
}