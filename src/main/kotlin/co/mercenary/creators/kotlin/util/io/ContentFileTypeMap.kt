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
import java.io.File
import javax.activation.MimetypesFileTypeMap

@IgnoreForSerialize
class ContentFileTypeMap : HasMapNames {

    @FrameworkDsl
    constructor() : this(DEFAULT_FILE, DEFAULT_PROP)

    @FrameworkDsl
    constructor(file: String, prop: String) {
        val (name, maps) = read(prop(file, prop))
        this.name = name
        this.maps = maps
    }

    @FrameworkDsl
    private val name: String

    @FrameworkDsl
    private val maps: MimetypesFileTypeMap

    @FrameworkDsl
    fun getContentType(file: File): String = maps.getContentType(file).toLowerTrim()

    @FrameworkDsl
    fun getContentType(name: String): String = maps.getContentType(name).toLowerTrim()

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun toMapNames() = dictOf("name" to name, "type" to nameOf())

    companion object {

        @FrameworkDsl
        const val DEFAULT_PROP = "co.mercenary.creators.kotlin.mime.file"

        @FrameworkDsl
        const val DEFAULT_FILE = "MIME-INF/co-mercenary-creators-kotlin-mime.types"

        @FrameworkDsl
        private fun prop(file: String, prop: String): String {
            return System.getProperty(prop.toTrimOr(DEFAULT_PROP), file.toTrimOr(DEFAULT_FILE))
        }

        @FrameworkDsl
        private fun read(file: String): Pair<String, MimetypesFileTypeMap> {
            return when (val data = IO.getInputStream(file)) {
                null -> when (file != DEFAULT_FILE) {
                    true -> when (val open = IO.getInputStream(DEFAULT_FILE)) {
                        null -> DUNNO_STRING to MimetypesFileTypeMap()
                        else -> DEFAULT_FILE to open.use { MimetypesFileTypeMap(it) }
                    }
                    else -> DUNNO_STRING to MimetypesFileTypeMap()
                }
                else -> file to data.use { MimetypesFileTypeMap(it) }
            }
        }
    }
}