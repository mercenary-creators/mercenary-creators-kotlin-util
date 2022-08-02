/*
 * Copyright (c) 2022, Mercenary Creators Company. All rights reserved.
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

package co.mercenary.creators.kotlin.util.test

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.io.*
import co.mercenary.creators.kotlin.util.logging.*
import co.mercenary.creators.kotlin.util.logging.ILoggingBase
import java.io.File
import kotlin.reflect.KClass

interface IKotlinTestBase : ILoggingBase {

    val author: String

    val prober: ContentTypeProbe

    val loader: ContentResourceLoader

    val cached: CachedContentResourceLoader

    val printer: (Int, String) -> Unit

    @FrameworkDsl
    @IgnoreForSerialize
    fun getTempFileNamed(name: String = uuid(), suff: String = ".tmp"): File

    @FrameworkDsl
    fun getTempFileNamedPath(name: String, suff: String): String

    @FrameworkDsl
    @IgnoreForSerialize
    fun getConfigPropertiesBuilder(): Builder<SystemProperties>

    @FrameworkDsl
    fun dash(size: Int = 64): String

    @LoggingWarnDsl
    fun dashes(size: Int = 64)

    @FrameworkDsl
    fun uuid(): String

    @FrameworkDsl
    fun String.toLink() = linkOf()

    @FrameworkDsl
    fun getConfigProperty(name: String, other: String = EMPTY_STRING): String

    @FrameworkDsl
    fun setConfigProperties(vararg args: Pair<String, Any?>)

    @FrameworkDsl
    fun setConfigProperties(args: Map<String, Any?>)

    @FrameworkDsl
    fun here(): Map<String, Any>

    @LoggingInfoDsl
    fun measured(size: Int, call: (Int) -> Unit)

    @LoggingInfoDsl
    fun annotations(data: Any)

    @LoggingInfoDsl
    fun annotations(type: KClass<*>)

    @LoggingInfoDsl
    fun annotations(type: Class<*>)

    @CreatorsDsl
    fun ignored(execute: Boolean = false, function: () -> Unit)
}