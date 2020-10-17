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

package co.mercenary.creators.kotlin.util.test

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.logging.*
import co.mercenary.creators.kotlin.util.logging.Logging
import mu.KLogger
import java.io.File
import java.util.*
import kotlin.reflect.KClass

@IgnoreForSerialize
open class KotlinTestBase(name: String?) : Logging(name), IKotlinTestBase {

    @CreatorsDsl
    constructor() : this(null)

    private val nope = ArrayList<Class<*>>()

    init {
        Encoders
        addThrowableAsFatal(OutOfMemoryError::class)
        addThrowableAsFatal(StackOverflowError::class)
    }

    private val conf: Properties by lazy {
        getConfigPropertiesBuilder().invoke()
    }

    @CreatorsDsl
    @IgnoreForSerialize
    override val author = CREATORS_AUTHOR_INFO

    @CreatorsDsl
    @IgnoreForSerialize
    override val loader = CONTENT_RESOURCE_LOADER

    @CreatorsDsl
    @IgnoreForSerialize
    override val cached = CACHED_CONTENT_RESOURCE_LOADER

    @CreatorsDsl
    @IgnoreForSerialize
    override val prober = getDefaultContentTypeProbe()

    @CreatorsDsl
    override fun getTempFileNamed(name: String, suff: String): File {
        return getTempFile(name, suff)
    }

    @CreatorsDsl
    override fun getTempFileNamedPath(name: String, suff: String): String {
        return getTempFileNamed(name, suff).path
    }

    @CreatorsDsl
    @IgnoreForSerialize
    override val printer: (Int, String) -> Unit = { i, s -> info { "%2d : %s".format(i + 1, s) } }

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getConfigPropertiesBuilder(): () -> Properties = { Properties() }

    @CreatorsDsl
    override fun dash(size: Int): String = "-".repeat(size.maxOf(0))

    @CreatorsDsl
    override fun uuid(): String = Randoms.uuid()

    @CreatorsDsl
    override fun getConfigProperty(name: String, other: String): String = conf.getProperty(name, other)

    @CreatorsDsl
    override fun setConfigProperties(vararg args: Pair<String, Any?>) {
        setConfigProperties(args.toMap())
    }

    @CreatorsDsl
    override fun setConfigProperties(args: Map<String, Any?>) {
        if (args.isNotEmpty()) {
            val temp = conf
            for ((k, v) in args) {
                when (v == null) {
                    true -> temp.remove(k)
                    else -> temp.setProperty(k, v.toString())
                }
            }
        }
    }

    @CreatorsDsl
    final override fun <T : Throwable> addThrowableAsFatal(type: Class<T>) {
        nope += type
    }

    @CreatorsDsl
    final override fun <T : Throwable> addThrowableAsFatal(type: KClass<T>) {
        nope += type.java
    }

    @CreatorsDsl
    override fun here(): Map<String, Any> {
        val type = javaClass
        val name = type.name
        val most = IS_NOT_FOUND.toAtomic()
        val mine = type.declaredMethods.map { it.name }
        val list = ArrayList<MutableMap<String, Any>>()
        Exception().stackTrace.forEach { item ->
            if (item.className.startsWith(name)) {
                if (item.methodName in mine) {
                    list += dictOfMutable("func" to item.methodName, "type" to name, "file" to item.fileName, "line" to item.lineNumber)
                }
                else if (item.methodName == "invoke") {
                    // I'm coming from inside anonymous blocks that are not inlined,
                    // especially in logging where I am with AssumeEach, assumeThat,
                    // and AssumeCollector DSL.
                    most.maxOf(item.lineNumber)
                }
            }
        }
        if (list.isEmpty()) {
            return dictOf("func" to DUNNO_STRING, "type" to name, "file" to DUNNO_STRING, "line" to 0)
        }
        val line = most.toInt()
        val maps = list.first()
        if (line > maps["line"] as Int) {
            maps["line"] = line
        }
        return maps.toMap()
    }

    @CreatorsDsl
    override fun toString() = nameOf()

    @LoggingWarnDsl
    override fun dashes(size: Int) {
        warn { dash(size) }
    }

    @LoggingInfoDsl
    override fun measured(size: Int, call: (Int) -> Unit) {
        if (size > 0) {
            val many = size + 4
            val list = vectorOf(many)
            for (loop in 0 until many) {
                val time = TimeAndDate.nanos()
                call.invoke(loop)
                list[loop] = TimeAndDate.nanos() - time.toDouble()
            }
            list.sort()
            val look = list.copyOfRange(2, list.size - 2).average().toLong()
            info { TimeAndDate.toElapsedString(look) }
        }
        else {
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

    @CreatorsDsl
    override fun fail(text: String): Nothing {
        throw MercenaryAssertExceptiion(text)
    }

    @CreatorsDsl
    override fun fail(func: () -> Any?): Nothing {
        fail(Formatters.toSafeString(func))
    }

    @CreatorsDsl
    override fun assertTrueOf(condition: Boolean, func: () -> Any?) {
        if (!condition) {
            fail(func)
        }
    }

    @CreatorsDsl
    override fun assertNotTrueOf(condition: Boolean, func: () -> Any?) {
        if (condition) {
            fail(func)
        }
    }

    @CreatorsDsl
    override fun getThrowableOf(func: () -> Unit): Throwable? {
        return try {
            func.invoke()
            null
        }
        catch (oops: Throwable) {
            nope.forEach { type ->
                if (type.isInstance(oops)) {
                    throw oops
                }
            }
            oops
        }
    }

    @CreatorsDsl
    override fun assumeEach(block: IAssumeCollector.() -> Unit) {
        AssumeCollector(block).also { it.invoke() }
    }

    @IgnoreForSerialize
    inner class AssumeCollector @CreatorsDsl constructor(block: AssumeCollector.() -> Unit) : IAssumeCollector {

        private val list = ArrayList<() -> Unit>()

        init {
            block(this)
        }

        @CreatorsDsl
        override fun assumeThat(block: () -> Unit) {
            list += block
        }

        @CreatorsDsl
        override fun clear() {
            list.clear()
        }

        @CreatorsDsl
        operator fun invoke() = list.whenNotNull { func -> getThrowableOf(func) }.whenNotEmpty { look ->
            throw MercenaryMultipleAssertExceptiion(look).suppress()
        }
    }

    @CreatorsDsl
    override infix fun <T : Any?> T.shouldBe(value: T) = assertTrueOf(value isSameAs this) {
        "shouldBe failed"
    }

    @CreatorsDsl
    override infix fun <T : Any?> T.shouldNotBe(value: T) = assertTrueOf(value isNotSameAs this) {
        "shouldNotBe failed"
    }

    @CreatorsDsl
    override infix fun <T : Any?> T.shouldBeSameContent(value: T) = assertTrueOf(value isContentSameAs this) {
        "shouldBeSameContent failed"
    }

    @CreatorsDsl
    override infix fun <T : Any?> T.shouldNotBeSameContent(value: T) = assertTrueOf(value isContentNotSameAs this) {
        "shouldNotBeSameContent failed"
    }

    @CreatorsDsl
    override infix fun <T : Any> T.shouldBeIdentity(value: T) = assertTrueOf(value === this) {
        "shouldBeIdentity failed"
    }

    @CreatorsDsl
    override infix fun <T : Any> T.shouldNotBeIdentity(value: T) = assertNotTrueOf(value === this) {
        "shouldNotBeIdentity failed"
    }

    @CreatorsDsl
    override fun <T : Any?> T.shouldBeNull() = assertTrueOf(this == null) {
        "shouldBeNull failed"
    }

    @CreatorsDsl
    override fun <T : Any?> T.shouldNotBeNull() = assertTrueOf(this != null) {
        "shouldNotBeNull failed"
    }

    @CreatorsDsl
    override fun loggerOf(): KLogger {
        return super.loggerOf()
    }

    @CreatorsDsl
    inline fun <reified T : Throwable> assumeThrows(noinline block: () -> Unit) {
        try {
            block.invoke()
        }
        catch (cause: Throwable) {
            assertTrueOf(cause is T) {
                "assumeThrows failed ${cause.javaClass.name} not ${T::class.java.name}"
            }
            return
        }
        fail("assumeThrows failed for ${T::class.java.name}")
    }

    @CreatorsDsl
    inline fun <reified T : Throwable> assumeNotThrows(noinline block: () -> Unit) {
        try {
            block.invoke()
        }
        catch (cause: Throwable) {
            assertNotTrueOf(cause is T) {
                "assumeNotThrows failed ${cause.javaClass.name} not ${T::class.java.name}"
            }
        }
    }
}