/*
 * Copyright (c) 2023, Mercenary Creators Company. All rights reserved.
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

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import co.mercenary.creators.kotlin.util.*
import mu.*
import org.slf4j.*
import org.slf4j.bridge.SLF4JBridgeHandler
import kotlin.reflect.KClass

internal typealias LoggerContext = ch.qos.logback.classic.LoggerContext

@FrameworkDsl
fun Level.intsOf(): Int = toInt().absOf()

@FrameworkDsl
fun ILoggingEvent.levelOf(): Level = level

@FrameworkDsl
fun ILoggingEvent.intsOf(): Int = levelOf().intsOf()

@FrameworkDsl
@IgnoreForSerialize
object LoggingFactory : HasMapNames {

    @FrameworkDsl
    @IgnoreForSerialize
    const val KE = "$"

    @FrameworkDsl
    @IgnoreForSerialize
    const val KT = "Kt$"

    @FrameworkDsl
    @IgnoreForSerialize
    const val ROOT_LOGGER_NAME = Logger.ROOT_LOGGER_NAME

    @FrameworkDsl
    private val open = false.toAtomic()

    @FrameworkDsl
    private val auto = LoggingConfig.isAutoStart()

    @FrameworkDsl
    private val stop = LoggingConfig.isAutoClose()

    @FrameworkDsl
    private val juli = LoggingConfig.isBridgeing()

    @FrameworkDsl
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
    @FrameworkDsl
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
    @FrameworkDsl
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

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to nameOf(), "open" to open.isTrue(), "started" to isStarted(), "bridge" to LoggingBridge.isStarted(), "conf" to dictOf("list" to list, "auto" to auto, "stop" to stop))

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun isStarted(): Boolean = context().isStarted.isTrue()

    @JvmStatic
    @FrameworkDsl
    fun logger(name: CharSequence): ILogging = Logging(name.copyOf())

    @JvmStatic
    @FrameworkDsl
    fun logger(type: Class<*>): ILogging = logger(type.nameOf())

    @JvmStatic
    @FrameworkDsl
    fun logger(type: KClass<*>): ILogging = logger(type.nameOf())

    @JvmStatic
    @FrameworkDsl
    fun logger(self: Any): ILogging = logger(self.nameOf())

    @JvmStatic
    @FrameworkDsl
    inline fun logger(noinline func: Factory<Unit>): ILogging {
        val name = func.nameOf()
        return when {
            name.contains(KT) -> logger(name.substringBefore(KT))
            name.contains(KE) -> logger(name.substringBefore(KE))
            else -> logger(name)
        }
    }

    @JvmStatic
    @FrameworkDsl
    inline fun marker(noinline func: () -> Unit): IMarker {
        val name = func.nameOf()
        return when {
            name.contains(KT) -> LoggingMarker(name.substringBefore(KT))
            name.contains(KE) -> LoggingMarker(name.substringBefore(KE))
            else -> LoggingMarker(name)
        }
    }

    @FrameworkDsl
    internal inline fun markerOf(noinline func: () -> Unit): mu.Marker {
        val name = func.nameOf()
        return when {
            name.contains(KT) -> markerOf(name.substringBefore(KT))
            name.contains(KE) -> markerOf(name.substringBefore(KE))
            else -> markerOf(name)
        }
    }

    @FrameworkDsl
    internal inline fun markerOf(name: CharSequence): mu.Marker = KMarkerFactory.getMarker(name.copyOf())

    @FrameworkDsl
    internal inline fun Logger.loggerOf(): KLogger = KotlinLogging.logger(this)

    @FrameworkDsl
    internal fun loggerOf(name: CharSequence): KLogger = LoggerFactory.getLogger(name.copyOf()).loggerOf()

    @FrameworkDsl
    internal fun loggerOf(type: Class<*>): KLogger = LoggerFactory.getLogger(type).loggerOf()

    @FrameworkDsl
    internal fun loggerOf(type: Any): KLogger = LoggerFactory.getLogger(type.javaClass).loggerOf()

    @FrameworkDsl
    internal fun context() = LoggerFactory.getILoggerFactory() as LoggerContext

    @FrameworkDsl
    internal fun classic(name: CharSequence) = when (name.toUpperCaseEnglish() == ROOT_LOGGER_NAME) {
        true -> context().getLogger(ROOT_LOGGER_NAME)
        else -> context().exists(name.copyOf())
    }

    @JvmStatic
    @FrameworkDsl
    fun isLoggerDefined(type: Class<*>): Boolean {
        return isLoggerDefined(type.nameOf())
    }

    @JvmStatic
    @FrameworkDsl
    fun isLoggerDefined(name: CharSequence): Boolean {
        return context().exists(name.copyOf()) != null
    }

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getLevel() = getLevel(ROOT_LOGGER_NAME)

    @JvmStatic
    @FrameworkDsl
    fun getLevel(type: KLogger) = getLevel(type.name)

    @JvmStatic
    @FrameworkDsl
    fun getLevel(type: Class<*>) = getLevel(type.nameOf())

    @JvmStatic
    @FrameworkDsl
    fun getLevel(type: KClass<*>) = getLevel(type.nameOf())

    @JvmStatic
    @FrameworkDsl
    fun getLevel(name: CharSequence): LoggingLevel {
        return when (val classic = classic(name).level) {
            null -> getLevel(ROOT_LOGGER_NAME)
            else -> LoggingLevel.from(classic)
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun setLevel(level: LoggingLevel) = setLevel(ROOT_LOGGER_NAME, level)

    @JvmStatic
    @FrameworkDsl
    fun setLevel(type: KLogger, level: LoggingLevel) = setLevel(type.name, level)

    @JvmStatic
    @FrameworkDsl
    fun setLevel(type: Class<*>, level: LoggingLevel) = setLevel(type.nameOf(), level)

    @JvmStatic
    @FrameworkDsl
    fun setLevel(type: KClass<*>, level: LoggingLevel) = setLevel(type.nameOf(), level)

    @JvmStatic
    @FrameworkDsl
    fun setLevel(name: CharSequence, level: LoggingLevel) {
        classic(name).level = level.toLevel()
    }

    @JvmStatic
    @FrameworkDsl
    fun <R> withContext(block: LoggerContext.() -> R): R {
        return context().withIn(block)
    }

    @JvmStatic
    @FrameworkDsl
    fun withLevel(using: LoggingLevel, block: () -> Unit) {
        withLevel(ROOT_LOGGER_NAME, using, block)
    }

    @JvmStatic
    @FrameworkDsl
    fun withLevel(type: Class<*>, using: LoggingLevel, block: () -> Unit) {
        withLevel(type.nameOf(), using, block)
    }

    @JvmStatic
    @FrameworkDsl
    fun withLevel(type: KClass<*>, using: LoggingLevel, block: () -> Unit) {
        withLevel(type.nameOf(), using, block)
    }

    @JvmStatic
    @FrameworkDsl
    fun withLevel(type: KLogger, using: LoggingLevel, block: () -> Unit) {
        withLevel(type.name, using, block)
    }

    @JvmStatic
    @FrameworkDsl
    fun withLevel(name: CharSequence, using: LoggingLevel, block: () -> Unit) {
        classic(name.whenRoot()).withIn {
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

    @FrameworkDsl
    private fun CharSequence.whenRoot(): CharSequence = if (toUpperCaseEnglish() == ROOT_LOGGER_NAME) ROOT_LOGGER_NAME else this

    @JvmStatic
    @FrameworkDsl
    fun toSafeString(func: LazyMessage): String {
        return Formatters.toSafeString(func)
    }

    @FrameworkDsl
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