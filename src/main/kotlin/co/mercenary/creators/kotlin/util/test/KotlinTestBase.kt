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
import co.mercenary.creators.kotlin.util.logging.LoggingInfoDsl
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.KClass

@IgnoreForSerialize
open class KotlinTestBase : Logging() {

    private val nope = arrayListOf<Class<*>>()

    init {
        Encoders
        addThrowableAsFatal(OutOfMemoryError::class)
        addThrowableAsFatal(StackOverflowError::class)
    }

    private val conf: Properties by lazy {
        getConfigPropertiesBuilder().invoke()
    }

    @IgnoreForSerialize
    protected val author = CREATORS_AUTHOR_INFO

    @IgnoreForSerialize
    protected val loader = CONTENT_RESOURCE_LOADER

    @IgnoreForSerialize
    protected val cached = CACHED_CONTENT_RESOURCE_LOADER

    @JvmOverloads
    protected open fun getTempFileNamed(name: String = uuid(), suff: String = ".tmp"): File {
        return getTempFile(name, suff)
    }

    protected open fun getTempFileNamedPath(name: String, suff: String): String {
        return getTempFileNamed(name, suff).path
    }

    @IgnoreForSerialize
    protected open val printer: (Int, String) -> Unit = { i, s -> info { "%2d : %s".format(i + 1, s) } }

    @IgnoreForSerialize
    protected open fun getConfigPropertiesBuilder(): () -> Properties = { Properties() }

    @JvmOverloads
    @AssumptionDsl
    fun dash(loop: Int = 64): String = "-".repeat(loop.abs())

    @AssumptionDsl
    fun uuid(): String = Randoms.uuid()

    @JvmOverloads
    fun getConfigProperty(name: String, other: String = EMPTY_STRING): String = conf.getProperty(name, other)

    fun setConfigProperty(vararg args: Pair<String, Any?>) {
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

    @AssumptionDsl
    fun <T : Throwable> addThrowableAsFatal(type: Class<T>) {
        nope += type
    }

    @AssumptionDsl
    fun <T : Throwable> addThrowableAsFatal(type: KClass<T>) {
        nope += type.java
    }

    @AssumptionDsl
    fun here(): Map<String, Any?> {
        val type = javaClass
        val name = type.name
        val most = 0.toAtomic().decrement()
        val mine = type.declaredMethods.map { it.name }
        val list = ArrayList<MutableMap<String, Any>>()
        Exception().stackTrace.forEach { item ->
            if (item.className.startsWith(name)) {
                if (item.methodName in mine) {
                    list += mutableMapOf("func" to item.methodName, "type" to name, "file" to item.fileName, "line" to item.lineNumber)
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
            return mapOf("func" to DUNNO_STRING, "type" to name, "file" to DUNNO_STRING, "line" to 0)
        }
        val line = most.toInt()
        val maps = list.first()
        if (line > maps["line"] as Int) {
            maps["line"] = line
        }
        return maps
    }

    override fun toString(): String {
        return javaClass.name
    }

    @LoggingInfoDsl
    fun measured(loop: Int, call: (Int) -> Unit) {
        val list = LongArray(loop)
        loop forEach {
            val time = TimeAndDate.nanos()
            call.invoke(it)
            list[it] = TimeAndDate.nanos() - time
        }
        info { TimeAndDate.toElapsedString(list.average().toLong()) }
    }

    @LoggingInfoDsl
    fun annotations(data: Any) {
        annotations(data.javaClass)
    }

    @LoggingInfoDsl
    fun annotations(type: KClass<*>) {
        annotations(type.java)
    }

    @LoggingInfoDsl
    fun annotations(type: Class<*>) {
        if (isLoggingInfoEnabled) {
            info { type.name }
            type.annotations.forEach { anno ->
                val name = anno.annotationClass.java.name
                if (name != KOTLIN_METAS) {
                    info { name }
                }
            }
        }
    }

    fun String.toLink() = toURL()

    @AssumptionDsl
    protected open fun fail(text: String): Nothing {
        throw MercenaryAssertExceptiion(text)
    }

    @AssumptionDsl
    protected open fun fail(func: () -> Any?): Nothing {
        fail(Formatters.toSafeString(func))
    }

    @AssumptionDsl
    protected open fun assertTrueOf(condition: Boolean, func: () -> Any?) {
        if (!condition) {
            fail(func)
        }
    }

    @AssumptionDsl
    protected open fun assertNotTrueOf(condition: Boolean, func: () -> Any?) {
        if (condition) {
            fail(func)
        }
    }

    private fun getThrowableOf(func: () -> Unit): Throwable? {
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

    @AssumptionDsl
    fun assumeEach(block: AssumeCollector.() -> Unit) {
        AssumeCollector(block).also { it.invoke() }
    }

    @AssumptionDsl
    inner class AssumeCollector(block: AssumeCollector.() -> Unit) {

        private val list = ArrayList<() -> Unit>()

        init {
            block(this)
        }

        @AssumptionDsl
        fun assumeThat(block: () -> Unit) {
            list += block
        }

        operator fun invoke() {
            if (list.isNotEmpty()) {
                val look = list.mapNotNull { getThrowableOf(it) }
                if (look.isNotEmpty()) {
                    val oops = MercenaryMultipleAssertExceptiion(look)
                    look.forEach {
                        oops.addSuppressed(it)
                    }
                    throw oops
                }
            }
        }
    }

    @AssumptionDsl
    protected infix fun <T : Any?> T.shouldBe(value: T) = assertTrueOf(value isSameAs this) {
        "shouldBe failed"
    }

    @AssumptionDsl
    protected infix fun <T : Any?> T.shouldNotBe(value: T) = assertTrueOf(value isNotSameAs this) {
        "shouldNotBe failed"
    }

    @AssumptionDsl
    protected infix fun <T : Any> T.shouldBeIdentity(value: T) = assertTrueOf(value === this) {
        "shouldBeIdentity failed"
    }

    @AssumptionDsl
    protected infix fun <T : Any> T.shouldNotBeIdentity(value: T) = assertNotTrueOf(value === this) {
        "shouldNotBeIdentity failed"
    }

    @AssumptionDsl
    protected fun <T : Any?> T.shouldBeNull() = assertTrueOf(this == null) {
        "shouldBeNull failed"
    }

    @AssumptionDsl
    protected fun <T : Any?> T.shouldNotBeNull() = assertTrueOf(this != null) {
        "shouldNotBeNull failed"
    }

    @AssumptionDsl
    protected inline fun <reified T : Throwable> assumeThrows(block: () -> Unit) {
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

    @AssumptionDsl
    protected inline fun <reified T : Throwable> assumeNotThrows(block: () -> Unit) {
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