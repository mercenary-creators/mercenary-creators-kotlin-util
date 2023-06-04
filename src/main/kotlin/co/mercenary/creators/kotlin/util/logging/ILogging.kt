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
    fun info(block: LazyMessage)

    @LoggingInfoDsl
    fun info(cause: Throwable, block: LazyMessage)

    @LoggingInfoDsl
    fun info(marker: IMarker, block: LazyMessage)

    @LoggingInfoDsl
    fun info(cause: Throwable, marker: IMarker, block: LazyMessage)

    @LoggingWarnDsl
    fun warn(block: LazyMessage)

    @LoggingWarnDsl
    fun warn(cause: Throwable, block: LazyMessage)

    @LoggingWarnDsl
    fun warn(marker: IMarker, block: LazyMessage)

    @LoggingWarnDsl
    fun warn(cause: Throwable, marker: IMarker, block: LazyMessage)

    @CreatorsDsl
    fun trace(block: LazyMessage)

    @CreatorsDsl
    fun trace(cause: Throwable, block: LazyMessage)

    @CreatorsDsl
    fun trace(marker: IMarker, block: LazyMessage)

    @CreatorsDsl
    fun trace(cause: Throwable, marker: IMarker, block: LazyMessage)

    @CreatorsDsl
    fun debug(block: LazyMessage)

    @CreatorsDsl
    fun debug(cause: Throwable, block: LazyMessage)

    @CreatorsDsl
    fun debug(marker: IMarker, block: LazyMessage)

    @CreatorsDsl
    fun debug(cause: Throwable, marker: IMarker, block: LazyMessage)

    fun error(block: LazyMessage)

    fun error(marker: IMarker, block: LazyMessage)

    fun error(cause: Throwable, block: LazyMessage)

    fun error(cause: Throwable, marker: IMarker, block: LazyMessage)
}