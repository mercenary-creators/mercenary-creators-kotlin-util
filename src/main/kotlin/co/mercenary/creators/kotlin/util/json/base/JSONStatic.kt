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

package co.mercenary.creators.kotlin.util.json.base

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.io.InputStreamSupplier
import co.mercenary.creators.kotlin.util.type.ParameterizedTypeReference
import com.fasterxml.jackson.core.PrettyPrinter
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.core.util.*
import com.fasterxml.jackson.databind.JavaType
import com.jayway.jsonpath.TypeRef
import java.io.*
import java.lang.reflect.Type
import java.math.*
import java.net.*
import java.nio.channels.ReadableByteChannel
import java.nio.file.Path
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.atomic.*
import kotlin.reflect.KClass

object JSONStatic {

    @FrameworkDsl
    private val PRETTY: JSONMapper by lazy {
        JSONMapper(true)
    }

    @FrameworkDsl
    private val NORMAL: JSONMapper by lazy {
        JSONMapper(false)
    }

    @FrameworkDsl
    private const val IR_MAX = Int.MAX_VALUE

    @FrameworkDsl
    private const val IR_MIN = Int.MIN_VALUE

    @FrameworkDsl
    private val IV_MAX = Int.MAX_VALUE.toBigInteger()

    @FrameworkDsl
    private val IV_MIN = Int.MIN_VALUE.toBigInteger()

    @FrameworkDsl
    private val LV_MAX = Long.MAX_VALUE.toBigInteger()

    @FrameworkDsl
    private val LV_MIN = Long.MIN_VALUE.toBigInteger()

    @FrameworkDsl
    private val DV_MAX = Double.MAX_VALUE.toBigDecimal()

    @FrameworkDsl
    private val DV_MIN = Double.MAX_VALUE.toBigDecimal().negate()

    @FrameworkDsl
    private val DI_MAX = Double.MAX_VALUE.toBigDecimal().toBigInteger()

    @FrameworkDsl
    private val DI_MIN = Double.MAX_VALUE.toBigDecimal().negate().toBigInteger()

    @FrameworkDsl
    private val TO_INDENT_PRINTS = DefaultIndenter().withIndent("    ")

    @FrameworkDsl
    private val TO_PRETTY_PRINTS =
        DefaultPrettyPrinter().withArrayIndenter(TO_INDENT_PRINTS).withObjectIndenter(TO_INDENT_PRINTS)

    @JvmStatic
    @FrameworkDsl
    private fun getElementType(look: Number): JSONElementType =
        if (isNumber(look)) JSONElementType.NUMBER else JSONElementType.UNDEFINED

    @JvmStatic
    @FrameworkDsl
    fun getElementType(look: Maybe) = when (look) {
        null -> JSONElementType.NULL
        is Date -> JSONElementType.DATE
        is LocalDateTime -> JSONElementType.DATE
        is Number -> getElementType(look)
        is String -> JSONElementType.STRING
        is CharSequence -> JSONElementType.STRING
        is Boolean -> JSONElementType.BOOLEAN
        is AtomicBoolean -> JSONElementType.BOOLEAN
        else -> {
            if (isArray(look)) {
                JSONElementType.ARRAY
            }
            if (isObject(look)) {
                JSONElementType.OBJECT
            }
            JSONElementType.UNDEFINED
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun isFunction(look: Maybe) = when (look) {
        null -> false
        is Function0<*> -> true
        else -> false
    }

    @JvmStatic
    @FrameworkDsl
    fun isArray(look: Maybe) = when (look) {
        null -> false
        is List<*> -> true
        is Array<*> -> true
        is Sequence<*> -> true
        is Iterable<*> -> true
        is CharArray -> true
        else -> false
    }

    @JvmStatic
    @FrameworkDsl
    fun isObject(look: Maybe) = when (look) {
        null -> false
        is Map<*, *> -> true
        else -> {
            if (isDataClass(look)) true else if (isFunction(look)) false else asObject(look) != null
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun isDataClass(look: Maybe) = when (look) {
        null -> false
        else -> look.javaClass.kotlin.isData
    }

    @JvmStatic
    @FrameworkDsl
    fun isString(look: Maybe) = when (look) {
        null -> false
        is String -> true
        is CharSequence -> true
        else -> false
    }

    @JvmStatic
    @FrameworkDsl
    fun isBoolean(look: Maybe) = when (look) {
        null -> false
        is Boolean -> true
        is AtomicBoolean -> true
        else -> false
    }

    @JvmStatic
    @FrameworkDsl
    fun isNumber(look: Maybe) = when (look) {
        null -> false
        is Number -> isDouble(look)
        else -> false
    }

    @JvmStatic
    @FrameworkDsl
    fun isDate(look: Maybe) = when (look) {
        null -> false
        is Date -> true
        is LocalDateTime -> true
        else -> false
    }

    @JvmStatic
    @FrameworkDsl
    fun isNull(look: Maybe) = when (look) {
        null -> true
        else -> false
    }

    @JvmStatic
    @FrameworkDsl
    fun isLong(look: Maybe) = when (look) {
        null -> false
        is Long, Int, Short, Char, Byte -> true
        is AtomicLong -> true
        is AtomicInteger -> true
        is BigInteger -> look in LV_MIN..LV_MAX
        else -> false
    }

    @JvmStatic
    @FrameworkDsl
    fun isInteger(look: Maybe) = when (look) {
        null -> false
        is Int, Short, Char, Byte -> true
        is AtomicInteger -> true
        is Long -> look in IR_MIN..IR_MAX
        is BigInteger -> look in IV_MIN..IV_MAX
        is AtomicLong -> look.toBigInteger() in IV_MIN..IV_MAX
        else -> false
    }

    @JvmStatic
    @FrameworkDsl
    fun isDouble(look: Maybe) = when (look) {
        null -> false
        is Float -> look.isValid()
        is Double -> look.isValid()
        is Int, Long, Short, Char, Byte -> true
        is BigDecimal -> look <= DV_MAX && look >= DV_MIN
        is BigInteger -> look <= DI_MAX && look >= DI_MIN
        is AtomicLong -> true
        is AtomicInteger -> true
        else -> false
    }

    @JvmStatic
    @FrameworkDsl
    fun asInteger(look: Maybe): Int? = when (look) {
        null -> null
        is Number -> if (isInteger(look)) look.toInt() else null
        else -> null
    }

    @JvmStatic
    @FrameworkDsl
    fun asLong(look: Maybe): Long? = when (look) {
        null -> null
        is Number -> if (isLong(look)) look.toLong() else null
        else -> null
    }

    @JvmStatic
    @FrameworkDsl
    fun asDate(look: Maybe): Date? = when (look) {
        null -> null
        is Date -> look
        is LocalDateTime -> look.convertTo()
        else -> null
    }

    @JvmStatic
    @FrameworkDsl
    fun asString(look: Maybe): String? = when (look) {
        null -> null
        is String -> look
        is CharSequence -> look.copyOf()
        else -> null
    }

    @JvmStatic
    @FrameworkDsl
    fun asBoolean(look: Maybe): Boolean? = when (look) {
        null -> null
        is Boolean -> look.toBoolean()
        is AtomicBoolean -> look.toBoolean()
        else -> null
    }

    @JvmStatic
    @FrameworkDsl
    fun asDouble(look: Maybe): Double? = when (look) {
        null -> null
        is Number -> if (isDouble(look)) look.toDouble() else null
        else -> null
    }

    @JvmStatic
    @FrameworkDsl
    fun asObject(look: Any?): JSONObject? = when (look) {
        null -> null
        is JSONObject -> look
        else -> asDataTypeOf(look, JSONObject::class)
    }

    @JvmStatic
    @FrameworkDsl
    fun asArray(look: Any?): JSONArray? = when (look) {
        null -> null
        is JSONArray -> look
        is List<*> -> JSONArray(look)
        is Array<*> -> JSONArray(look)
        is Sequence<*> -> JSONArray(look)
        is Iterable<*> -> JSONArray(look)
        else -> asDataTypeOf(look, JSONArray::class)
    }

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> asDataType(look: Any, type: Class<T>): T? = try {
        toDataType(look, type)
    } catch (cause: Throwable) {
        Throwables.thrown(cause)
        null
    }

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> asDataTypeOf(look: Any?, type: Class<T>): T? = look?.let { asDataType(it, type) }

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> asDataType(look: Any, type: KClass<T>): T? = try {
        toDataType(look, type)
    } catch (cause: Throwable) {
        Throwables.thrown(cause)
        null
    }

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> asDataTypeOf(look: Any?, type: KClass<T>): T? = look?.let { asDataType(it, type) }

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> asDataType(look: Any, type: TypeReference<T>): T? = try {
        toDataType(look, type)
    } catch (cause: Throwable) {
        Throwables.thrown(cause)
        null
    }

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> asDataTypeOf(look: Any?, type: TypeReference<T>): T? = look?.let { asDataType(it, type) }

    @JvmStatic
    @FrameworkDsl
    fun toByteArray(data: Any) = NORMAL.toByteArray(data)

    @JvmStatic
    @FrameworkDsl
    fun canSerializeValue(data: Any?) = NORMAL.canSerializeValue(data)

    @JvmStatic
    @FrameworkDsl
    fun canSerializeClass(type: Class<*>?) = NORMAL.canSerializeClass(type)

    @JvmStatic
    @FrameworkDsl
    fun canSerializeClass(type: KClass<*>?) = NORMAL.canSerializeClass(type)

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getFormatter(pretty: Boolean = true): JSONFormatter {
        return mapperOf(pretty).getFormatter()
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getPrettyPrinter(maps: Int = 4, list: Int = maps): PrettyPrinter {
        return DefaultPrettyPrinter().withArrayIndenter(DefaultIndenter().withIndent(SPACE_STRING.repeat(list.maxOf(0))))
            .withObjectIndenter(DefaultIndenter().withIndent(SPACE_STRING.repeat(maps.maxOf(0))))
    }

    @JvmStatic
    @FrameworkDsl
    fun getDefaultPrettyPrinter(): PrettyPrinter {
        return TO_PRETTY_PRINTS
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun toJSONString(data: Any, pretty: Boolean = true, escape: Boolean = false) =
        getFormatter(pretty).toJSONString(data, escape)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: URI, type: TypeReference<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: URL, type: TypeReference<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: File, type: TypeReference<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: Path, type: TypeReference<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: CharSequence, type: TypeReference<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> jsonReadOf(data: ByteArray, type: TypeReference<T>, copy: Boolean = false) =
        NORMAL.jsonRead(data, type, copy)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: InputStreamSupplier, type: TypeReference<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: ReadableByteChannel, type: TypeReference<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> jsonReadOf(data: Reader, type: TypeReference<T>, done: Boolean = true) =
        NORMAL.jsonRead(data, type, done)

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> jsonReadOf(data: InputStream, type: TypeReference<T>, done: Boolean = true) =
        NORMAL.jsonRead(data, type, done)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: URI, type: Class<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: URL, type: Class<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: File, type: Class<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: Path, type: Class<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: CharSequence, type: Class<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: ByteArray, type: Class<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: InputStreamSupplier, type: Class<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: ReadableByteChannel, type: Class<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> jsonReadOf(data: Reader, type: Class<T>, done: Boolean = true) = NORMAL.jsonRead(data, type, done)

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> jsonReadOf(data: InputStream, type: Class<T>, done: Boolean = true) =
        NORMAL.jsonRead(data, type, done)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: URI, type: KClass<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: URL, type: KClass<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: File, type: KClass<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: Path, type: KClass<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: CharSequence, type: KClass<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: ByteArray, type: KClass<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: InputStreamSupplier, type: KClass<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> jsonReadOf(data: ReadableByteChannel, type: KClass<T>) = NORMAL.jsonRead(data, type)

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> jsonReadOf(data: Reader, type: KClass<T>, done: Boolean = true) = NORMAL.jsonRead(data, type, done)

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> jsonReadOf(data: InputStream, type: KClass<T>, done: Boolean = true) =
        NORMAL.jsonRead(data, type, done)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> toDeepCopy(data: T) = NORMAL.toDeepCopy(data)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> toDeepCopy(data: T, type: Class<T>) = NORMAL.toDeepCopy(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> toDeepCopy(data: T, type: KClass<T>) = NORMAL.toDeepCopy(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> toDeepCopy(data: T, type: TypeReference<T>) = NORMAL.toDeepCopy(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> toDataType(data: Any, type: Class<T>) = NORMAL.toDataType(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> toDataType(data: Any, type: KClass<T>) = NORMAL.toDataType(data, type)

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> toDataType(data: Any, type: TypeReference<T>) = NORMAL.toDataType(data, type)

    @JvmStatic
    @FrameworkDsl
    fun toJavaType(data: Any): JavaType {
        return when (data) {
            is JavaType -> data
            is Type -> NORMAL.constructTypeOf(data)
            is KClass<*> -> NORMAL.constructTypeOf(data)
            is TypeRef<*> -> NORMAL.constructTypeOf(data)
            is TypeReference<*> -> NORMAL.constructTypeOf(data)
            is ParameterizedTypeReference<*> -> NORMAL.constructTypeOf(data)
            else -> NORMAL.constructTypeOf(data.javaClass)
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun mapperOf(pretty: Boolean) = if (pretty) PRETTY else NORMAL

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> toJSONReader(data: URI, type: TypeReference<T>): JSONReader<T> {
        return JSONReader { jsonReadOf(data, type) }
    }

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> toJSONReader(data: URL, type: TypeReference<T>): JSONReader<T> {
        return JSONReader { jsonReadOf(data, type) }
    }

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> toJSONReader(data: File, type: TypeReference<T>): JSONReader<T> {
        return JSONReader { jsonReadOf(data, type) }
    }

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> toJSONReader(data: Path, type: TypeReference<T>): JSONReader<T> {
        return JSONReader { jsonReadOf(data, type) }
    }

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> toJSONReader(data: CharSequence, type: TypeReference<T>): JSONReader<T> {
        return JSONReader { jsonReadOf(data.copyOf(), type) }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> toJSONReader(data: ByteArray, type: TypeReference<T>, copy: Boolean = false): JSONReader<T> {
        return JSONReader { jsonReadOf(data, type, copy) }
    }

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> toJSONReader(data: InputStreamSupplier, type: TypeReference<T>): JSONReader<T> {
        return JSONReader { jsonReadOf(data, type) }
    }

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> toJSONReader(data: ReadableByteChannel, type: TypeReference<T>): JSONReader<T> {
        return JSONReader { jsonReadOf(data, type) }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> toJSONReader(data: Reader, type: TypeReference<T>, done: Boolean = true): JSONReader<T> {
        return JSONReader { jsonReadOf(data, type, done) }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> toJSONReader(data: InputStream, type: TypeReference<T>, done: Boolean = true): JSONReader<T> {
        return JSONReader { jsonReadOf(data, type, done) }
    }
}
