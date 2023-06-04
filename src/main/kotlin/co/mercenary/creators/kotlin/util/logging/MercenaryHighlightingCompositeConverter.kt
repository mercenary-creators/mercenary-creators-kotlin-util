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

package co.mercenary.creators.kotlin.util.logging

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.pattern.color.*
import co.mercenary.creators.kotlin.util.*

@IgnoreForSerialize
open class MercenaryHighlightingCompositeConverter : ForegroundCompositeConverterBase<ILoggingEvent>() {

    @CreatorsDsl
    override fun getForegroundColorCode(event: ILoggingEvent): String {
        return when (event.level.toInt()) {
            Level.INFO_INT -> INFOC
            Level.WARN_INT -> WARNC
            Level.ERROR_INT -> OOPSC
            else -> OTHER
        }
    }

    companion object {

        @CreatorsDsl
        const val CLOSE = ANSIConstants.ESC_END

        @CreatorsDsl
        const val START = ANSIConstants.ESC_START

        @CreatorsDsl
        const val OTHER = ANSIConstants.DEFAULT_FG

        @CreatorsDsl
        const val OOPSC = ANSIConstants.BOLD + ANSIConstants.RED_FG

        @CreatorsDsl
        const val WARNC = ANSIConstants.BOLD + ANSIConstants.YELLOW_FG

        @CreatorsDsl
        const val INFOC = ANSIConstants.BOLD + ANSIConstants.DEFAULT_FG

        @CreatorsDsl
        const val RESET = START + "0;" + OTHER + CLOSE
    }
}