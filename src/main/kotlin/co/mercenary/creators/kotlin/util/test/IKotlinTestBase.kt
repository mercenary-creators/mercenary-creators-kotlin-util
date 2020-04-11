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
import mu.KLoggable
import java.io.File
import java.util.*
import kotlin.reflect.KClass

interface IKotlinTestBase : KLoggable, ILoggingBase {

    val author: String

    val prober: ContentTypeProbe

    val loader: ContentResourceLoader

    val cached: CachedContentResourceLoader

    val printer: (Int, String) -> Unit

    fun getTempFileNamed(name: String = uuid(), suff: String = ".tmp"): File

    fun getTempFileNamedPath(name: String, suff: String): String

    fun getConfigPropertiesBuilder(): () -> Properties

    @AssumptionDsl
    fun dash(loop: Int = 64): String

    @AssumptionDsl
    fun uuid(): String

    @AssumptionDsl
    fun String.toLink() = toURL()

    fun getConfigProperty(name: String, other: String = EMPTY_STRING): String

    fun setConfigProperty(vararg args: Pair<String, Any?>)

    @AssumptionDsl
    fun <T : Throwable> addThrowableAsFatal(type: Class<T>)

    @AssumptionDsl
    fun <T : Throwable> addThrowableAsFatal(type: KClass<T>)

    @AssumptionDsl
    fun here(): Map<String, Any>

    @LoggingInfoDsl
    fun measured(loop: Int, call: (Int) -> Unit)

    @LoggingInfoDsl
    fun annotations(data: Any)

    @LoggingInfoDsl
    fun annotations(type: KClass<*>)

    @LoggingInfoDsl
    fun annotations(type: Class<*>)

    @AssumptionDsl
    fun fail(text: String): Nothing

    @AssumptionDsl
    fun fail(func: () -> Any?): Nothing

    @AssumptionDsl
    fun assertTrueOf(condition: Boolean, func: () -> Any?)

    @AssumptionDsl
    fun assertNotTrueOf(condition: Boolean, func: () -> Any?)

    @AssumptionDsl
    fun getThrowableOf(func: () -> Unit): Throwable?

    @AssumptionDsl
    fun assumeEach(block: KotlinTestBase.AssumeCollector.() -> Unit)

    @AssumptionDsl
    infix fun <T : Any?> T.shouldBe(value: T)

    @AssumptionDsl
    infix fun <T : Any?> T.shouldNotBe(value: T)

    @AssumptionDsl
    infix fun <T : Any> T.shouldBeIdentity(value: T)

    @AssumptionDsl
    infix fun <T : Any> T.shouldNotBeIdentity(value: T)

    @AssumptionDsl
    fun <T : Any?> T.shouldBeNull()

    @AssumptionDsl
    fun <T : Any?> T.shouldNotBeNull()
}