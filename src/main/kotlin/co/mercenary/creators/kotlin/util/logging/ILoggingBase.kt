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

    override fun info(block: () -> Any?) {
        if (isLoggingInfoEnabled) {
            logger.info {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun info(cause: Throwable, block: () -> Any?) {
        if (isLoggingInfoEnabled) {
            logger.info(cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun info(marker: IMarker, block: () -> Any?) {
        if (isLoggingInfoEnabled) {
            logger.info(marker.markerOf()) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun info(cause: Throwable, marker: IMarker, block: () -> Any?) {
        if (isLoggingInfoEnabled) {
            logger.info(marker.markerOf(), cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun warn(block: () -> Any?) {
        if (isLoggingWarnEnabled) {
            logger.warn {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun warn(cause: Throwable, block: () -> Any?) {
        if (isLoggingWarnEnabled) {
            logger.warn(cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun warn(marker: IMarker, block: () -> Any?) {
        if (isLoggingWarnEnabled) {
            logger.warn(marker.markerOf()) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun warn(cause: Throwable, marker: IMarker, block: () -> Any?) {
        if (isLoggingWarnEnabled) {
            logger.warn(marker.markerOf(), cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun trace(block: () -> Any?) {
        if (isLoggingTraceEnabled) {
            logger.trace {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun trace(cause: Throwable, block: () -> Any?) {
        if (isLoggingTraceEnabled) {
            logger.trace(cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun trace(marker: IMarker, block: () -> Any?) {
        if (isLoggingTraceEnabled) {
            logger.trace(marker.markerOf()) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun trace(cause: Throwable, marker: IMarker, block: () -> Any?) {
        if (isLoggingTraceEnabled) {
            logger.trace(marker.markerOf(), cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun debug(block: () -> Any?) {
        if (isLoggingDebugEnabled) {
            logger.debug {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun debug(cause: Throwable, block: () -> Any?) {
        if (isLoggingDebugEnabled) {
            logger.debug(cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun debug(marker: IMarker, block: () -> Any?) {
        if (isLoggingDebugEnabled) {
            logger.debug(marker.markerOf()) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun debug(cause: Throwable, marker: IMarker, block: () -> Any?) {
        if (isLoggingDebugEnabled) {
            logger.debug(marker.markerOf(), cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun error(block: () -> Any?) {
        if (isLoggingErrorEnabled) {
            logger.error {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun error(cause: Throwable, block: () -> Any?) {
        if (isLoggingErrorEnabled) {
            logger.error(cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun error(marker: IMarker, block: () -> Any?) {
        if (isLoggingErrorEnabled) {
            logger.error(marker.markerOf()) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun error(cause: Throwable, marker: IMarker, block: () -> Any?) {
        if (isLoggingErrorEnabled) {
            logger.error(marker.markerOf(), cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }
}