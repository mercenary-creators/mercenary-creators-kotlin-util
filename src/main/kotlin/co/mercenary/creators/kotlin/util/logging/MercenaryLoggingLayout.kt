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

package co.mercenary.creators.kotlin.util.logging

import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.*
import co.mercenary.creators.kotlin.util.*

open class MercenaryLoggingLayout : LayoutBase<ILoggingEvent>() {

    private val proxy: PatternLayout by lazy {
        PatternLayout()
    }

    private val flags = true.toAtomic()

    private var descr: String = EMPTY_STRING

    var description: String
        @JvmName("getDescription")
        get() = descr
        @JvmName("setDescription")
        set(value) {
            descr = value
        }

    var newlines: Boolean
        @JvmName("getNewlines")
        get() = flags.toBoolean()
        @JvmName("setNewlines")
        set(value) {
            flags.set(value)
        }

    override fun doLayout(event: ILoggingEvent): String {
        return if (!isStarted) EMPTY_STRING else proxy.doLayout(event)
    }

    override fun setContext(context: Context) {
        super.setContext(context)
        proxy.context = context
    }

    open fun colorOf(): String {
        return when (newlines.not()) {
            true -> MercenaryHighlightingCompositeConverter::class.java.name
            else -> MercenaryHighlightingBodyCompositeConverter::class.java.name
        }
    }

    open fun levelOf(): String = MercenaryLevelConverter::class.java.name

    override fun start() {
        PatternLayout.defaultConverterMap["_color_"] = colorOf()
        PatternLayout.defaultConverterMap["_level_"] = levelOf()
        proxy.pattern = "%yellow([%d{\"yyyy-MM-dd HH:mm:ss,SSS z\",\"UTC\"}]) %blue([%_level_]) %magenta([%c]) %cyan([${description}] -) %_color_(%m) %n"
        proxy.start()
        super.start()
    }

    override fun stop() {
        proxy.stop()
        super.stop()
    }

    override fun toString() = "${javaClass.name}($description)"

    override fun hashCode() = description.hashCode()

    override fun equals(other: Any?) = when (other) {
        is MercenaryLoggingLayout -> this === other || description == other.description
        else -> false
    }
}