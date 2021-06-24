/*
 * Copyright (c) 2021, Mercenary Creators Company. All rights reserved.
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

package co.mercenary.creators.kotlin.util.test.main

import co.mercenary.creators.kotlin.util.*
import org.junit.jupiter.api.Test

class LogsTest : KotlinTest() {

    @Test
    fun test() {
        info { 16 }
        info { 1L }
        info { 1..3 }
        info { MainData() }
        info { dateOf() }
        info { dateTimeOf() }
        info { toListOf<Any>() }
        info { toListOf(1) }
        info { toListOf(1, 2, 3) }
        info { sequenceOf(4, 5, 6) }
        info { toDoubleArrayOf(4, 5, 6) }
        info { TimeAndDate.nanosOf() }
        info { dictOf("name" to author, "time" to 56.5.years, "date" to dateOf(), "horz" to "Maël Hörz\n", "size" to 1, "code" to String(CharArray(16) { it.toChar() })) }
        info { false }
        info { true.toAtomic() }
        info { 16.toAtomic() }
        info { 1L.toAtomic() }
        info { loader["test.htm"] }
        info { loader["https://jsonplaceholder.typicode.com/posts"] }
        info { 255.toBinaryString() }
        info { 255.toBinaryString().sizeOf() }
        info { 255.toBinaryString(0) }
        warn { dash() }
        error { loggerOf().javaClass }
        error { loggerOf().underlyingLogger.javaClass }
        warn { dash() + BREAK_STRING + dash() }
        error { dash() }
        error { Common.getExposedMethods(MainDean, true) }
        error { dash() }
        dashes()
        error { Common.getExposedMethods(MainData()) }
        dashes()
        error { Common.getExposedMethods(Encoders, true) }
        dashes()
        error { MainData().isDataClass() }
        dashes()
        toParameterizedTypeReference<List<Int>>().getType().also {
            info { it.nameOf() }
            dashes()
            error { it is Class<*> }
            dashes()
            info { it.toErased().nameOf() }
            dashes()
            info { it.typeName }
            dashes()
        }
        toParameterizedTypeReference<MainData>().getType().also {
            info { it.nameOf() }
            dashes()
            error { it is Class<*> }
            dashes()
            info { it.toErased().nameOf() }
            dashes()
            info { it.typeName }
            dashes()
        }
        toParameterizedTypeReference<Array<MainData>>().getType().also {
            info { it.nameOf() }
            dashes()
            error { it is Class<*> }
            dashes()
            info { it.toErased().nameOf() }
            dashes()
            info { it.typeName }
            dashes()
        }
        toParameterizedTypeReference<DoubleArray>().getType().also {
            info { it.nameOf() }
            dashes()
            error { it is Class<*> }
            dashes()
            info { it.toErased().nameOf() }
            dashes()
            info { it.typeName }
            dashes()
        }
        toParameterizedTypeReference<DoubleArray>().toJavaTypeOf().also {
            warn { it.genericSignature }
            dashes()
            warn { it.rawClass.isKotlinClass() }
            dashes()
            warn { it.rawClass.name }
            dashes()
            warn { it.typeName }
            dashes()
            error { it.toCanonical() }
        }
        toParameterizedTypeReference<List<MainData>>().toJavaTypeOf().also {
            warn { it.genericSignature }
            dashes()
            warn { it.rawClass.isKotlinClass() }
            dashes()
            warn { it.rawClass.name }
            dashes()
            warn { it.typeName }
            dashes()
            error { it.toCanonical() }
        }
        toParameterizedTypeReference<Array<MainData>>().toJavaTypeOf().also {
            warn { it.genericSignature }
            dashes()
            warn { it.rawClass.isKotlinClass() }
            dashes()
            warn { it.rawClass.name }
            dashes()
            warn { it.typeName }
            dashes()
            error { it.toCanonical() }
        }
        toParameterizedTypeReference<TimeDuration>().toJavaTypeOf().also {
            warn { it.genericSignature }
            dashes()
            warn { it.rawClass.isKotlinClass() }
            dashes()
            warn { it.rawClass.name }
            dashes()
            warn { it.typeName }
            dashes()
            error { it.toCanonical() }
        }
        toParameterizedTypeReference<Pair<String, TimeDuration>>().toJavaTypeOf().also {
            warn { it.genericSignature }
            dashes()
            warn { it.rawClass.isKotlinClass() }
            dashes()
            warn { it.rawClass.name }
            dashes()
            warn { it.typeName }
            dashes()
            error { it.toCanonical() }
        }
        toParameterizedTypeReference<Sequence<Pair<String, TimeDuration>>>().toJavaTypeOf().also {
            warn { it.genericSignature }
            dashes()
            warn { it.rawClass.isKotlinClass() }
            dashes()
            warn { it.rawClass.name }
            dashes()
            warn { it.typeName }
            dashes()
            error { it.toCanonical() }
        }
        DoubleArray::class.toJavaTypeOf().also {
            error { it.genericSignature }
            dashes()
            error { it.rawClass.isKotlinClass() }
            dashes()
            error { it.rawClass.name }
            dashes()
            error { it.typeName }
            dashes()
            error { it.toCanonical() }
            dashes()
            error { it.isArrayType }
        }
    }
}