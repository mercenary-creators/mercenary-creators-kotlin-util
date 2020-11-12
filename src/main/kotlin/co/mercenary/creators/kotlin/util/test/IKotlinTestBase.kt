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

package co.mercenary.creators.kotlin.util.test

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.io.*
import co.mercenary.creators.kotlin.util.logging.*
import co.mercenary.creators.kotlin.util.logging.ILoggingBase
import java.io.File
import java.util.*
import kotlin.reflect.KClass

interface IKotlinTestBase : ILoggingBase {

    val author: String

    val prober: ContentTypeProbe

    val loader: ContentResourceLoader

    val cached: CachedContentResourceLoader

    val printer: (Int, String) -> Unit

    @CreatorsDsl
    @IgnoreForSerialize
    fun getTempFileNamed(name: String = uuid(), suff: String = ".tmp"): File

    @CreatorsDsl
    fun getTempFileNamedPath(name: String, suff: String): String

    @CreatorsDsl
    @IgnoreForSerialize
    fun getConfigPropertiesBuilder(): () -> Properties

    @CreatorsDsl
    fun dash(size: Int = 64): String

    @LoggingWarnDsl
    fun dashes(size: Int = 64)

    @CreatorsDsl
    fun uuid(): String

    @CreatorsDsl
    fun String.toLink() = toURL()

    @CreatorsDsl
    fun getConfigProperty(name: String, other: String = EMPTY_STRING): String

    @CreatorsDsl
    fun setConfigProperties(vararg args: Pair<String, Any?>)

    @CreatorsDsl
    fun setConfigProperties(args: Map<String, Any?>)

    @CreatorsDsl
    fun <T : Throwable> addThrowableAsFatal(type: Class<T>)

    @CreatorsDsl
    fun <T : Throwable> addThrowableAsFatal(type: KClass<T>)

    @CreatorsDsl
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
    fun fail(text: String): Nothing

    @CreatorsDsl
    fun fail(func: () -> Any?): Nothing

    @CreatorsDsl
    fun assertTrueOf(condition: Boolean, func: () -> Any?)

    @CreatorsDsl
    fun assertNotTrueOf(condition: Boolean, func: () -> Any?)

    @CreatorsDsl
    fun getThrowableOf(func: () -> Unit): Throwable?

    @CreatorsDsl
    fun assumeEach(block: IAssumeCollector.() -> Unit)

    @CreatorsDsl
    infix fun <T : Any?> T.shouldBe(value: T)

    @CreatorsDsl
    infix fun <T : Any?> T.shouldNotBe(value: T)

    @CreatorsDsl
    infix fun <T : Any?> T.shouldBeSameContent(value: T)

    @CreatorsDsl
    infix fun <T : Any?> T.shouldNotBeSameContent(value: T)

    @CreatorsDsl
    infix fun <T : Any> T.shouldBeIdentity(value: T)

    @CreatorsDsl
    infix fun <T : Any> T.shouldNotBeIdentity(value: T)

    @CreatorsDsl
    fun <T : Any?> T.shouldBeNull()

    @CreatorsDsl
    fun <T : Any?> T.shouldNotBeNull()
}