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

import java.io.*
import java.util.zip.*

object Inflaters {

    @JvmStatic
    fun gzip(): Inflater = GZIPInflater

    private object GZIPInflater : Inflater {

        override val type: InflaterType
            get() = InflaterType("GZIP")

        override fun inflate(data: InputStream, copy: OutputStream) = GZIPInputStream(NoCloseInputStream(data)).use { gzip ->
            gzip.copyTo(copy).also { copy.flush() }
        }

        override fun deflate(data: InputStream, copy: OutputStream) = CountSizeOutputStream(copy).let { buff ->
            GZIPOutputStream(NoCloseOutputStream(buff)).use { gzip ->
                data.copyTo(gzip)
            }
            buff.size
        }

        override fun toString() = type.name
    }
}