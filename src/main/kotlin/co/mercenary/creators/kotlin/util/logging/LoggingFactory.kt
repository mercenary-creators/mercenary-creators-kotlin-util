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

@file:Suppress("NOTHING_TO_INLINE")

package co.mercenary.creators.kotlin.util.logging

import co.mercenary.creators.kotlin.util.*
import mu.*
import org.slf4j.*
import org.slf4j.bridge.SLF4JBridgeHandler
import kotlin.reflect.KClass

@IgnoreForSerialize
object LoggingFactory : HasMapNames {

    @CreatorsDsl
    @IgnoreForSerialize
    const val KE = "$"

    @CreatorsDsl
    @IgnoreForSerialize
    const val KT = "Kt$"

    @CreatorsDsl
    @IgnoreForSerialize
    const val ROOT_LOGGER_NAME = Logger.ROOT_LOGGER_NAME

    private val open = false.toAtomic()

    @CreatorsDsl
    private val auto = LoggingConfig.isAutoStart()

    @CreatorsDsl
    private val stop = LoggingConfig.isAutoClose()

    @CreatorsDsl
    private val juli = LoggingConfig.isBridgeing()

    @CreatorsDsl
    private val list = LoggingConfig.getIgnoring()

    init {
        if (open.isFalseToTrue()) {
            context().frameworkPackages += list
            if (auto.isTrue()) {
                start()
            }
            if (stop.isTrue()) {
                onExitOfProcess(true) {
                    val exit = close()
                    getStandardError().echo(exit).newline()
                }
            }
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun start(): Boolean {
        if (isStarted().isNotTrue()) {
            context().start()
            if (juli.isTrue()) {
                if (LoggingBridge.isNotStarted()) {
                    LoggingBridge.start()
                }
            }
            return true
        }
        return false
    }

    @JvmStatic
    @CreatorsDsl
    fun close(): Boolean {
        if (isStarted()) {
            context().stop()
            if (juli.isTrue()) {
                if (LoggingBridge.isStarted()) {
                    LoggingBridge.close()
                }
            }
            return true
        }
        return false
    }

    @CreatorsDsl
    override fun toString() = nameOf()

    @CreatorsDsl
    override fun toMapNames() = dictOf("type" to nameOf(), "open" to open.isTrue(), "started" to isStarted(), "bridge" to LoggingBridge.isStarted(), "conf" to dictOf("list" to list, "auto" to auto, "stop" to stop))

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun isStarted(): Boolean = context().isStarted

    @JvmStatic
    @CreatorsDsl
    fun logger(name: String): ILogging = Logging(name)

    @JvmStatic
    @CreatorsDsl
    fun logger(type: Class<*>): ILogging = logger(type.name)

    @JvmStatic
    @CreatorsDsl
    fun logger(type: KClass<*>): ILogging = logger(type.java)

    @JvmStatic
    @CreatorsDsl
    fun logger(self: Any): ILogging = logger(self.javaClass)

    @JvmStatic
    @CreatorsDsl
    inline fun logger(noinline func: () -> Unit): ILogging {
        val name = func.nameOf()
        return when {
            name.contains(KT) -> logger(name.substringBefore(KT))
            name.contains(KE) -> logger(name.substringBefore(KE))
            else -> logger(name)
        }
    }

    @JvmStatic
    @CreatorsDsl
    inline fun marker(noinline func: () -> Unit): IMarker {
        val name = func.nameOf()
        return when {
            name.contains(KT) -> LoggingMarker(name.substringBefore(KT))
            name.contains(KE) -> LoggingMarker(name.substringBefore(KE))
            else -> LoggingMarker(name)
        }
    }

    @CreatorsDsl
    internal inline fun markerOf(noinline func: () -> Unit): mu.Marker {
        val name = func.nameOf()
        return when {
            name.contains(KT) -> markerOf(name.substringBefore(KT))
            name.contains(KE) -> markerOf(name.substringBefore(KE))
            else -> markerOf(name)
        }
    }

    @CreatorsDsl
    internal inline fun markerOf(name: String): mu.Marker = KMarkerFactory.getMarker(name)

    @CreatorsDsl
    internal inline fun Logger.loggerOf(): KLogger = KotlinLogging.logger(this)

    @CreatorsDsl
    internal fun loggerOf(name: String): KLogger = LoggerFactory.getLogger(name).loggerOf()

    @CreatorsDsl
    internal fun loggerOf(type: Class<*>): KLogger = LoggerFactory.getLogger(type).loggerOf()

    @CreatorsDsl
    internal fun loggerOf(type: Any): KLogger = LoggerFactory.getLogger(type.javaClass).loggerOf()

    @CreatorsDsl
    internal fun context() = LoggerFactory.getILoggerFactory() as ch.qos.logback.classic.LoggerContext

    @CreatorsDsl
    internal fun classic(name: CharSequence) = when (name.toUpperCaseEnglish() == ROOT_LOGGER_NAME) {
        true -> context().getLogger(ROOT_LOGGER_NAME)
        else -> context().exists(name.toString())
    }

    @JvmStatic
    @CreatorsDsl
    @IgnoreForSerialize
    fun getLevel() = getLevel(ROOT_LOGGER_NAME)

    @JvmStatic
    @CreatorsDsl
    fun getLevel(type: KLogger) = getLevel(type.name)

    @JvmStatic
    @CreatorsDsl
    fun getLevel(type: Class<*>) = getLevel(type.name)

    @JvmStatic
    @CreatorsDsl
    fun getLevel(type: KClass<*>) = getLevel(type.java)

    @JvmStatic
    @CreatorsDsl
    fun getLevel(name: CharSequence): LoggingLevel {
        return when (val classic = classic(name).level) {
            null -> getLevel(ROOT_LOGGER_NAME)
            else -> LoggingLevel.from(classic)
        }
    }

    @JvmStatic
    @CreatorsDsl
    fun setLevel(level: LoggingLevel) = setLevel(ROOT_LOGGER_NAME, level)

    @JvmStatic
    @CreatorsDsl
    fun setLevel(type: KLogger, level: LoggingLevel) = setLevel(type.name, level)

    @JvmStatic
    @CreatorsDsl
    fun setLevel(type: Class<*>, level: LoggingLevel) = setLevel(type.name, level)

    @JvmStatic
    @CreatorsDsl
    fun setLevel(type: KClass<*>, level: LoggingLevel) = setLevel(type.java, level)

    @JvmStatic
    @CreatorsDsl
    fun setLevel(name: CharSequence, level: LoggingLevel) {
        classic(name).level = level.toLevel()
    }

    @JvmStatic
    @CreatorsDsl
    fun withLevel(using: LoggingLevel, block: () -> Unit) {
        withLevel(ROOT_LOGGER_NAME, using, block)
    }

    @JvmStatic
    @CreatorsDsl
    fun withLevel(type: Class<*>, using: LoggingLevel, block: () -> Unit) {
        withLevel(type.name, using, block)
    }

    @JvmStatic
    @CreatorsDsl
    fun withLevel(type: KClass<*>, using: LoggingLevel, block: () -> Unit) {
        withLevel(type.java, using, block)
    }

    @JvmStatic
    @CreatorsDsl
    fun withLevel(type: KLogger, using: LoggingLevel, block: () -> Unit) {
        withLevel(type.name, using, block)
    }

    @JvmStatic
    @CreatorsDsl
    fun withLevel(name: CharSequence, using: LoggingLevel, block: () -> Unit) {
        scope(classic(name.whenRoot())) {
            val saved = level
            level = using.toLevel()
            try {
                block()
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
                throw cause
            } finally {
                level = saved
            }
        }
    }

    @CreatorsDsl
    private fun CharSequence.whenRoot(): CharSequence = if (toUpperCaseEnglish() == ROOT_LOGGER_NAME) ROOT_LOGGER_NAME else this

    @JvmStatic
    @CreatorsDsl
    fun toSafeString(func: () -> Any?): String {
        return Formatters.toSafeString(func)
    }

    @IgnoreForSerialize
    private object LoggingBridge {

        @JvmStatic
        @FrameworkDsl
        @IgnoreForSerialize
        fun isStarted(): Boolean {
            return try {
                SLF4JBridgeHandler.isInstalled()
            } catch (cause: Throwable) {
                false
            }
        }

        @JvmStatic
        @FrameworkDsl
        @IgnoreForSerialize
        fun isNotStarted(): Boolean = isStarted().isNotTrue()

        @JvmStatic
        @FrameworkDsl
        fun start(): Boolean {
            if (isNotStarted()) {
                return try {
                    SLF4JBridgeHandler.install()
                    true
                } catch (cause: Throwable) {
                    false
                }
            }
            return false
        }

        @JvmStatic
        @FrameworkDsl
        fun close(): Boolean {
            if (isStarted()) {
                return try {
                    SLF4JBridgeHandler.uninstall()
                    true
                } catch (cause: Throwable) {
                    false
                }
            }
            return false
        }
    }
}