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

interface ILogging {
    val isLoggingInfoEnabled: Boolean
    val isLoggingWarnEnabled: Boolean
    val isLoggingTraceEnabled: Boolean
    val isLoggingDebugEnabled: Boolean
    val isLoggingErrorEnabled: Boolean
    fun info(block: () -> Any?)
    fun info(cause: Throwable, block: () -> Any?)
    fun info(marker: String, block: () -> Any?)
    fun info(cause: Throwable, marker: String, block: () -> Any?)
    fun info(marker: IMarker, block: () -> Any?)
    fun info(cause: Throwable, marker: IMarker, block: () -> Any?)
    fun warn(block: () -> Any?)
    fun warn(cause: Throwable, block: () -> Any?)
    fun warn(marker: String, block: () -> Any?)
    fun warn(cause: Throwable, marker: String, block: () -> Any?)
    fun warn(marker: IMarker, block: () -> Any?)
    fun warn(cause: Throwable, marker: IMarker, block: () -> Any?)
    fun trace(block: () -> Any?)
    fun trace(cause: Throwable, block: () -> Any?)
    fun trace(marker: String, block: () -> Any?)
    fun trace(cause: Throwable, marker: String, block: () -> Any?)
    fun trace(marker: IMarker, block: () -> Any?)
    fun trace(cause: Throwable, marker: IMarker, block: () -> Any?)
    fun debug(block: () -> Any?)
    fun debug(cause: Throwable, block: () -> Any?)
    fun debug(marker: String, block: () -> Any?)
    fun debug(cause: Throwable, marker: String, block: () -> Any?)
    fun debug(marker: IMarker, block: () -> Any?)
    fun debug(cause: Throwable, marker: IMarker, block: () -> Any?)
    fun error(block: () -> Any?)
    fun error(cause: Throwable, block: () -> Any?)
    fun error(marker: String, block: () -> Any?)
    fun error(cause: Throwable, marker: String, block: () -> Any?)
    fun error(marker: IMarker, block: () -> Any?)
    fun error(cause: Throwable, marker: IMarker, block: () -> Any?)
}