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

import co.mercenary.creators.kotlin.util.timed

interface ILoggingBase : ILogging {

    val logger: mu.KLogger

    override val isLoggingInfoEnabled: Boolean
        get() = logger.isInfoEnabled

    override val isLoggingWarnEnabled: Boolean
        get() = logger.isWarnEnabled

    override val isLoggingTraceEnabled: Boolean
        get() = logger.isTraceEnabled

    override val isLoggingDebugEnabled: Boolean
        get() = logger.isDebugEnabled

    override val isLoggingErrorEnabled: Boolean
        get() = logger.isErrorEnabled

    override val isLoggingFatalEnabled: Boolean
        get() = isLoggingErrorEnabled.and(isLoggingDebugEnabled.or(isLoggingTraceEnabled))

    fun <T> timed(block: () -> T): T = timed({ info { it } }, block)

    override fun info(block: () -> Any?) {
        logger.info(block)
    }

    override fun info(cause: Throwable, block: () -> Any?) {
        logger.info(cause, block)
    }

    override fun warn(block: () -> Any?) {
        logger.warn(block)
    }

    override fun warn(cause: Throwable, block: () -> Any?) {
        logger.warn(cause, block)
    }

    override fun trace(block: () -> Any?) {
        logger.trace(block)
    }

    override fun trace(cause: Throwable, block: () -> Any?) {
        logger.trace(cause, block)
    }

    override fun debug(block: () -> Any?) {
        logger.debug(block)
    }

    override fun debug(cause: Throwable, block: () -> Any?) {
        logger.debug(cause, block)
    }

    override fun error(block: () -> Any?) {
        logger.error(block)
    }

    override fun error(cause: Throwable, block: () -> Any?) {
        logger.error(cause, block)
    }

    override fun fatal(block: () -> Any?) {
        if (isLoggingFatalEnabled) {
            logger.error(block)
        }
    }

    override fun fatal(cause: Throwable, block: () -> Any?) {
        if (isLoggingFatalEnabled) {
            logger.error(cause, block)
        }
    }
}