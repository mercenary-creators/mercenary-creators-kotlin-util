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
import org.apache.commons.logging.*
import java.io.*
import javax.activation.*

class ContentFileTypeMap : FileTypeMap() {

    private val logs: Log by lazy {
        LogFactory.getLog(ContentFileTypeMap::class.java)
    }

    private val maps = getMimetypesFileTypeMapInputStream()?.use { MimetypesFileTypeMap(it) } ?: MimetypesFileTypeMap()

    private fun getMimetypesFileTypeMapInputStream(): InputStream? {
        val name = getMimetypesFileName()
        val data = IO.getInputStream(name)
        if (data == null) {
            logs.error("can't load mime file $name")
            if (name != DEFAULT_FILE) {
                val file = IO.getInputStream(DEFAULT_FILE)
                if (file == null) {
                    logs.error("can't load mime file $DEFAULT_FILE")
                }
                return file
            }
        }
        return data
    }

    override fun getContentType(file: File): String = getContentType(file.name)

    override fun getContentType(name: String): String = maps.getContentType(name).toLowerTrim().toValidated()

    companion object {
        const val DEFAULT_FILE = "MIME-INF/co-mercenary-creators-kotlin-mime.types"
        fun getMimetypesFileName(): String = System.getProperty("co.mercenary.creators.kotlin.mime.file", DEFAULT_FILE)
    }
}