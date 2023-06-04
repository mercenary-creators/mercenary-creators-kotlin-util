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

import co.mercenary.creators.kotlin.util.*

interface ILoggingBase : ILogging {

    @FrameworkDsl
    fun loggerOf(): mu.KLogger

    @LoggingInfoDsl
    @IgnoreForSerialize
    override fun isLoggingInfoEnabled(): Boolean = loggerOf().isInfoEnabled

    @LoggingWarnDsl
    @IgnoreForSerialize
    override fun isLoggingWarnEnabled(): Boolean = loggerOf().isWarnEnabled

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isLoggingTraceEnabled(): Boolean = loggerOf().isTraceEnabled

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isLoggingDebugEnabled(): Boolean = loggerOf().isDebugEnabled

    @IgnoreForSerialize
    override fun isLoggingErrorEnabled(): Boolean = loggerOf().isErrorEnabled

    @LoggingInfoDsl
    override fun info(block: LazyMessage) {
        if (isLoggingInfoEnabled()) {
            loggerOf().info {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    @LoggingInfoDsl
    override fun info(cause: Throwable, block: LazyMessage) {
        if (isLoggingInfoEnabled()) {
            loggerOf().info(cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    @LoggingInfoDsl
    override fun info(marker: IMarker, block: LazyMessage) {
        if (isLoggingInfoEnabled()) {
            loggerOf().info(marker.markerOf()) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    @LoggingInfoDsl
    override fun info(cause: Throwable, marker: IMarker, block: LazyMessage) {
        if (isLoggingInfoEnabled()) {
            loggerOf().info(marker.markerOf(), cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    @LoggingWarnDsl
    override fun warn(block: LazyMessage) {
        if (isLoggingWarnEnabled()) {
            loggerOf().warn {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    @LoggingWarnDsl
    override fun warn(cause: Throwable, block: LazyMessage) {
        if (isLoggingWarnEnabled()) {
            loggerOf().warn(cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    @LoggingWarnDsl
    override fun warn(marker: IMarker, block: LazyMessage) {
        if (isLoggingWarnEnabled()) {
            loggerOf().warn(marker.markerOf()) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    @LoggingWarnDsl
    override fun warn(cause: Throwable, marker: IMarker, block: LazyMessage) {
        if (isLoggingWarnEnabled()) {
            loggerOf().warn(marker.markerOf(), cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    @CreatorsDsl
    override fun trace(block: LazyMessage) {
        if (isLoggingTraceEnabled()) {
            loggerOf().trace {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    @CreatorsDsl
    override fun trace(cause: Throwable, block: LazyMessage) {
        if (isLoggingTraceEnabled()) {
            loggerOf().trace(cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    @CreatorsDsl
    override fun trace(marker: IMarker, block: LazyMessage) {
        if (isLoggingTraceEnabled()) {
            loggerOf().trace(marker.markerOf()) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    @CreatorsDsl
    override fun trace(cause: Throwable, marker: IMarker, block: LazyMessage) {
        if (isLoggingTraceEnabled()) {
            loggerOf().trace(marker.markerOf(), cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    @CreatorsDsl
    override fun debug(block: LazyMessage) {
        if (isLoggingDebugEnabled()) {
            loggerOf().debug {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    @CreatorsDsl
    override fun debug(cause: Throwable, block: LazyMessage) {
        if (isLoggingDebugEnabled()) {
            loggerOf().debug(cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    @CreatorsDsl
    override fun debug(marker: IMarker, block: LazyMessage) {
        if (isLoggingDebugEnabled()) {
            loggerOf().debug(marker.markerOf()) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    @CreatorsDsl
    override fun debug(cause: Throwable, marker: IMarker, block: LazyMessage) {
        if (isLoggingDebugEnabled()) {
            loggerOf().debug(marker.markerOf(), cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun error(block: LazyMessage) {
        if (isLoggingErrorEnabled()) {
            loggerOf().error {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun error(cause: Throwable, block: LazyMessage) {
        if (isLoggingErrorEnabled()) {
            loggerOf().error(cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun error(marker: IMarker, block: LazyMessage) {
        if (isLoggingErrorEnabled()) {
            loggerOf().error(marker.markerOf()) {
                LoggingFactory.toSafeString(block)
            }
        }
    }

    override fun error(cause: Throwable, marker: IMarker, block: LazyMessage) {
        if (isLoggingErrorEnabled()) {
            loggerOf().error(marker.markerOf(), cause) {
                LoggingFactory.toSafeString(block)
            }
        }
    }
}