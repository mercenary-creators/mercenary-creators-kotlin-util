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

class MercenaryLoggingLayout : LayoutBase<ILoggingEvent>() {

    private val proxy: PatternLayout by lazy {
        PatternLayout()
    }

    private var descr: String = CoreConstants.EMPTY_STRING

    var description: String
        @JvmName("getDescription")
        get() = descr
        @JvmName("setDescription")
        set(value) {
            descr = value
        }

    override fun doLayout(event: ILoggingEvent?): String {
        return if (!isStarted) CoreConstants.EMPTY_STRING else proxy.doLayout(event)
    }

    override fun setContext(context: Context?) {
        super.setContext(context)
        proxy.context = context
    }

    override fun start() {
        proxy.pattern = "%yellow([%d{\"yyyy-MM-dd HH:mm:ss,SSS z\",\"UTC\"}]) %blue([%level]) %magenta([%c]) %cyan([${description}] -) %m %n"
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