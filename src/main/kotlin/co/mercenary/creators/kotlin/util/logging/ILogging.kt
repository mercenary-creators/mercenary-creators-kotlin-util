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

import co.mercenary.creators.kotlin.util.*

interface ILogging {

    @LoggingInfoDsl
    @IgnoreForSerialize
    fun isLoggingInfoEnabled(): Boolean

    @LoggingWarnDsl
    @IgnoreForSerialize
    fun isLoggingWarnEnabled(): Boolean

    @CreatorsDsl
    @IgnoreForSerialize
    fun isLoggingTraceEnabled(): Boolean

    @CreatorsDsl
    @IgnoreForSerialize
    fun isLoggingDebugEnabled(): Boolean

    @IgnoreForSerialize
    fun isLoggingErrorEnabled(): Boolean

    @LoggingInfoDsl
    @IgnoreForSerialize
    fun isLoggingInfoEnabled(marker: IMarker): Boolean

    @LoggingWarnDsl
    @IgnoreForSerialize
    fun isLoggingWarnEnabled(marker: IMarker): Boolean

    @CreatorsDsl
    @IgnoreForSerialize
    fun isLoggingTraceEnabled(marker: IMarker): Boolean

    @CreatorsDsl
    @IgnoreForSerialize
    fun isLoggingDebugEnabled(marker: IMarker): Boolean

    @IgnoreForSerialize
    fun isLoggingErrorEnabled(marker: IMarker): Boolean

    @LoggingInfoDsl
    fun info(block: () -> Any?)

    @LoggingInfoDsl
    fun info(cause: Throwable, block: () -> Any?)

    @LoggingInfoDsl
    fun info(marker: IMarker, block: () -> Any?)

    @LoggingInfoDsl
    fun info(cause: Throwable, marker: IMarker, block: () -> Any?)

    @LoggingWarnDsl
    fun warn(block: () -> Any?)

    @LoggingWarnDsl
    fun warn(cause: Throwable, block: () -> Any?)

    @LoggingWarnDsl
    fun warn(marker: IMarker, block: () -> Any?)

    @LoggingWarnDsl
    fun warn(cause: Throwable, marker: IMarker, block: () -> Any?)

    @CreatorsDsl
    fun trace(block: () -> Any?)

    @CreatorsDsl
    fun trace(cause: Throwable, block: () -> Any?)

    @CreatorsDsl
    fun trace(marker: IMarker, block: () -> Any?)

    @CreatorsDsl
    fun trace(cause: Throwable, marker: IMarker, block: () -> Any?)

    @CreatorsDsl
    fun debug(block: () -> Any?)

    @CreatorsDsl
    fun debug(cause: Throwable, block: () -> Any?)

    @CreatorsDsl
    fun debug(marker: IMarker, block: () -> Any?)

    @CreatorsDsl
    fun debug(cause: Throwable, marker: IMarker, block: () -> Any?)

    fun error(block: () -> Any?)
    fun error(cause: Throwable, block: () -> Any?)
    fun error(marker: IMarker, block: () -> Any?)
    fun error(cause: Throwable, marker: IMarker, block: () -> Any?)
}