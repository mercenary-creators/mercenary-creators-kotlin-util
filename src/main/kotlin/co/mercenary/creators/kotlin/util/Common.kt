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

package co.mercenary.creators.kotlin.util

import co.mercenary.creators.kotlin.util.io.InputStreamSupplier
import java.io.*
import java.net.*
import java.nio.channels.ReadableByteChannel
import java.nio.file.Path
import java.util.*

@FrameworkDsl
@IgnoreForSerialize
object Common : HasMapNames {

    @FrameworkDsl
    private val DIGITS = DIGIT_STRING.toCharArray(false)

    @FrameworkDsl
    private val CACHED = atomicMapOf<Int, String>(0xFFFF)

    @JvmStatic
    @FrameworkDsl
    fun getHexChar(code: Int): Char = DIGITS[code and 0xF]

    @JvmStatic
    @FrameworkDsl
    fun getUniCode(code: Int): String {
        return CACHED.computeIfAbsent(code.boxIn(0, 0xFFFF)) { calc ->
            CharArray(6) { posn ->
                when (posn) {
                    1 -> 'u'
                    2 -> getHexChar(calc mask 12)
                    3 -> getHexChar(calc mask 8)
                    4 -> getHexChar(calc mask 4)
                    5 -> getHexChar(calc mask 0)
                    else -> Escapers.ESCAPE_SLASH
                }
            }.getContentText(false)
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun user(): String = System.getProperty("user.name", "unknown")

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getSystemProperties(): SystemProperties {
        return System.getProperties()
    }

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getSystemEnvironment(): MutableDictionary<String> {
        return System.getenv()
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(data: Reader): SystemProperties {
        return getProperties(data, SystemProperties())
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(data: InputStream): Properties {
        return getProperties(data, Properties())
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(data: URI): Properties {
        return getProperties(data, Properties())
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(data: URI, properties: Properties): Properties {
        return getProperties(data.toInputStream(), properties)
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(data: URL): Properties {
        return getProperties(data, Properties())
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(data: URL, properties: Properties): Properties {
        return getProperties(data.toInputStream(), properties)
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(data: File): Properties {
        return getProperties(data, Properties())
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(data: File, properties: Properties): Properties {
        return getProperties(data.toInputStream(), properties)
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(data: Path): Properties {
        return getProperties(data, Properties())
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(data: Path, properties: Properties): Properties {
        return getProperties(data.toInputStream(), properties)
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(data: InputStreamSupplier): Properties {
        return getProperties(data, Properties())
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(data: InputStreamSupplier, properties: Properties): Properties {
        return getProperties(data.toInputStream(), properties)
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(data: ReadableByteChannel): Properties {
        return getProperties(data, Properties())
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(data: ReadableByteChannel, properties: Properties): Properties {
        return getProperties(data.toInputStream(), properties)
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(data: Reader, properties: SystemProperties): SystemProperties {
        try {
            data.use { look -> properties.load(look) }
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return properties
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(data: InputStream, properties: Properties): Properties {
        try {
            data.use { look -> properties.load(look) }
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return properties
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(data: CharSequence): Properties {
        return getProperties(data, Properties())
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(data: CharSequence, properties: Properties): Properties {
        val name = data.toTrimOr(EMPTY_STRING)
        if (name.isEmptyOrBlank().isNotTrue()) {
            try {
                return getProperties(CONTENT_RESOURCE_LOADER[name], properties)
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
            }
        }
        return properties
    }

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to nameOf())
}