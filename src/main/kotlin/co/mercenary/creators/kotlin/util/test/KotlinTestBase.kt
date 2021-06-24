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

package co.mercenary.creators.kotlin.util.test

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.Logging
import co.mercenary.creators.kotlin.util.logging.*

import mu.KLogger
import java.io.File
import kotlin.reflect.KClass

@IgnoreForSerialize
open class KotlinTestBase @FrameworkDsl constructor(name: String?) : Logging(name), IKotlinTestBase {

    @FrameworkDsl
    constructor() : this(null)

    init {
        Encoders
    }

    @FrameworkDsl
    private val conf: SystemProperties by lazy {
        getConfigPropertiesBuilder().build()
    }

    @FrameworkDsl
    private val config: SystemProperties
        @IgnoreForSerialize
        get() = conf

    @FrameworkDsl
    @IgnoreForSerialize
    override val author = CREATORS_AUTHOR_INFO

    @FrameworkDsl
    @IgnoreForSerialize
    override val loader = CONTENT_RESOURCE_LOADER

    @FrameworkDsl
    @IgnoreForSerialize
    override val cached = CACHED_CONTENT_RESOURCE_LOADER

    @FrameworkDsl
    @IgnoreForSerialize
    override val prober = getDefaultContentTypeProbe()

    @FrameworkDsl
    override fun getTempFileNamed(name: String, suff: String): File {
        return getTempFile(name, suff)
    }

    @FrameworkDsl
    override fun getTempFileNamedPath(name: String, suff: String): String {
        return getTempFileNamed(name, suff).path
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override val printer: (Int, String) -> Unit = { i, s -> info { "%2d : %s".format(i + 1, s) } }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getConfigPropertiesBuilder(): Builder<SystemProperties> = Builder { SystemProperties() }

    @FrameworkDsl
    override fun dash(size: Int): String = "-".repeat(size.maxOf(0))

    @FrameworkDsl
    override fun uuid(): String = Randoms.uuid()

    @FrameworkDsl
    override fun getConfigProperty(name: String, other: String): String = config.getProperty(name, other)

    @FrameworkDsl
    override fun setConfigProperties(vararg args: Pair<String, Maybe>) {
        if (args.isNotExhausted()) {
            setConfigProperties(args.mapTo())
        }
    }

    @FrameworkDsl
    override fun setConfigProperties(args: Map<String, Any?>) {
        if (args.isNotExhausted()) {
            for ((k, v) in args) {
                when (v == null) {
                    true -> config.remove(k)
                    else -> config.setProperty(k, v.toString())
                }
            }
        }
    }

    @CreatorsDsl
    override fun here(): Map<String, Any> {
        val type = javaClass
        val name = type.name
        val most = IS_NOT_FOUND.toAtomic()
        val mine = type.declaredMethods.map { it.name }
        val list = BasicArrayList<BasicAnyDictionary>()
        Exception().stackTrace.forEach { item ->
            if (item.className.startsWith(name)) {
                if (item.methodName in mine) {
                    list.add(BasicAnyDictionary("func" to item.methodName, "type" to name, "file" to item.fileName.otherwise(DUNNO_STRING), "line" to item.lineNumber))
                } else if (item.methodName == "invoke") {
                    // I'm coming from inside anonymous blocks that are not inlined,
                    // especially in logging where I am with AssumeEach, assumeThat,
                    // and AssumeCollector DSL.
                    most.maxOf(item.lineNumber)
                }
            }
        }
        if (list.isExhausted()) {
            return dictOf("func" to DUNNO_STRING, "type" to name, "file" to DUNNO_STRING, "line" to 0)
        }
        val line = most.getValue()
        val maps = list.head()
        if (line > maps["line"] as Int) {
            maps["line"] = line
        }
        return maps.toReadOnly()
    }

    @FrameworkDsl
    override fun toString() = nameOf()

    @LoggingWarnDsl
    override fun dashes(size: Int) {
        warn { dash(size) }
    }

    @LoggingInfoDsl
    override fun measured(size: Int, call: (Int) -> Unit) {
        if (size.isMoreThan(0)) {
            val list = (size + 4).toDoubleArray()
            for (loop in list.indices) {
                list[loop] = elapsed(true) {
                    call.invoke(loop)
                }.realOf()
            }
            info { TimeAndDate.toElapsedString(list.sorted(false).average(2, list.sizeOf() - 2, 0.0).longOf()) }
        } else {
            warn { TimeAndDate.toElapsedString(0, "error ") }
        }
    }

    @LoggingInfoDsl
    override fun annotations(data: Any) {
        annotations(data.javaClass)
    }

    @LoggingInfoDsl
    override fun annotations(type: KClass<*>) {
        annotations(type.java)
    }

    @LoggingInfoDsl
    override fun annotations(type: Class<*>) {
        if (isLoggingInfoEnabled()) {
            info { type.name }
            type.annotations.forEach { anno ->
                val name = anno.annotationClass.java.name
                if (name != KOTLIN_METAS) {
                    info { name }
                }
            }
        }
    }

    @FrameworkDsl
    override fun loggerOf(): KLogger {
        return super.loggerOf()
    }
}