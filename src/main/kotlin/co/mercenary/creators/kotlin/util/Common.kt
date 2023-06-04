/*
 * Copyright (c) 2023, Mercenary Creators Company. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * mailto:deansjones@mercenary-creators.io
 */

@file:Suppress("NOTHING_TO_INLINE", "FunctionName", "HttpUrlsUsage", "RedundantSuppression", "RedundantSuppression",
               "RedundantSuppression", "RedundantSuppression", "RedundantSuppression"
)

package co.mercenary.creators.kotlin.util

import co.mercenary.creators.kotlin.util.collection.StringSet
import co.mercenary.creators.kotlin.util.io.InputStreamSupplier
import java.io.*
import java.lang.reflect.*
import java.net.*
import java.nio.channels.ReadableByteChannel
import java.nio.file.Path
import kotlin.reflect.KClass

@Suppress("RedundantIf", "RedundantIf")
@FrameworkDsl
@IgnoreForSerialize
object Common : HasMapNames {

    @FrameworkDsl
    private val DIGITS = DIGIT_STRING.toCharArray(false)

    @FrameworkDsl
    private val CACHED = atomicMapOf<Int, String>(0xFFFF)

    @FrameworkDsl
    private val IGNORE = toSetOf("equals", "hashCode", "toString", "clone")

    @FrameworkDsl
    private val NOPERS = Regex("component\\d+")

    @JvmStatic
    @FrameworkDsl
    fun getHexChar(code: Int): Char = DIGITS[code and 0xF]

    @FrameworkDsl
    internal inline fun Method.toModifiers(): Int = modifiers

    @FrameworkDsl
    internal inline fun Method.isPublicMethod(): Boolean = Modifier.isPublic(toModifiers())

    @FrameworkDsl
    internal inline fun Method.isNotPublicMethod(): Boolean = isPublicMethod().isNotTrue()

    @FrameworkDsl
    internal inline fun Method.isStaticMethod(): Boolean = Modifier.isStatic(toModifiers()) && getDeclaredAnnotationsByType(JvmStatic::class.java).isNotExhausted()

    @FrameworkDsl
    internal inline fun Method.isNotStaticMethod(): Boolean = isStaticMethod().isNotTrue()

    @FrameworkDsl
    internal inline fun Method.isAbstractMethod(): Boolean = Modifier.isAbstract(toModifiers())

    @FrameworkDsl
    internal inline fun Method.isNotAbstractMethod(): Boolean = isAbstractMethod().isNotTrue()

    @FrameworkDsl
    internal inline fun Method.isSyntheticMethod(): Boolean = isSynthetic

    @FrameworkDsl
    internal inline fun Method.isNotSyntheticMethod(): Boolean = isSyntheticMethod().isNotTrue()

    @FrameworkDsl
    internal inline fun Method.toModifierString(): String = Modifier.toString(toModifiers())

    @FrameworkDsl
    internal inline fun Method.toReturnTypeString(): String = toReturnType(returnType).copyOf()

    @FrameworkDsl
    internal inline fun Method.isIgnoredMethods(static: Boolean, type: Class<*>): Boolean {
        if (isNotPublicMethod()) {
            return true
        }
        if (isAbstractMethod()) {
            return true
        }
        if (isSyntheticMethod()) {
            return true
        }
        if (static.isTrue() && isNotStaticMethod()) {
            return true
        }
        if (name in IGNORE) {
            return true
        }
        if (type.isDataClass() && (name.startsWith("copy") || name.matches(NOPERS))) {
            return true
        }
        return false
    }

    @FrameworkDsl
    internal fun Class<*>.isIgnoredTypes(): Boolean {
        return when {
            isArray || isEnum || isPrimitive -> true
            isAnnotation || isInterface || isAnonymousClass -> true
            isKotlinClass() && kotlin.isNotIgnoredTypes() -> true
            else -> false
        }
    }

    @FrameworkDsl
    internal inline fun KClass<*>.isIgnoredTypes(): Boolean {
        return when {
            isValue || isSealed || isFun -> true
            else -> false
        }
    }

    @FrameworkDsl
    internal inline fun KClass<*>.isNotIgnoredTypes(): Boolean = isIgnoredTypes().isNotTrue()

    @FrameworkDsl
    internal fun toReturnType(value: Class<*>, builder: StringBuilder = stringBuilderOf()): StringBuilder {
        builder.add(toReturnType(value.kotlin.simpleName))
        return toReturnType(value.typeParameters.toArrayList(), builder)
    }

    @FrameworkDsl
    internal fun toReturnType(value: List<TypeVariable<out Class<*>>>, builder: StringBuilder = stringBuilderOf()): StringBuilder {
        if (value.isNotExhausted()) {
            val first = getAtomicTrue()
            builder.add("<")
            value.forEach { type ->
                if (first.isNotTrue()) {
                    builder.add(", ")
                }
                builder.add(type.name)
                first.toNotTrue()
            }
            builder.add(">")
        }
        return builder
    }

    @FrameworkDsl
    internal fun toReturnType(value: String?): String {
        return when (value) {
            null -> Nothing::class.java.simpleName
            "Void" -> Unit::class.java.simpleName
            else -> value
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun getHexCode(code: Char): Int {
        if (code in '0'..'9') {
            return (code - '0')
        }
        if (code in 'A'..'F') {
            return (code - 'A' + 10)
        }
        if (code in 'a'..'f') {
            return (code - 'a' + 10)
        }
        return IS_NOT_FOUND
    }

    @JvmStatic
    @FrameworkDsl
    fun encodeHexBinary(data: ByteArray): String {
        if (data.isExhausted()) {
            return EMPTY_STRING
        }
        return stringOf(data.sizeOf() * 2) {
            data.forEach { code ->
                add(getHexChar(code mask 4)).add(getHexChar(code mask 0))
            }
        }.toTrimOr(EMPTY_STRING)
    }

    @JvmStatic
    @FrameworkDsl
    fun decodeHexBinary(args: String): ByteArray {
        return args.toUpperTrimEnglish().withSize { data, size ->
            when {
                size.isZero() -> EMPTY_BYTE_ARRAY
                size.isEven() -> (size / 2).toByteArray().also { buff ->
                    size.forEachStep(2) { calc ->
                        buff[calc / 2] = byteOf(getHexCode(data[calc]), getHexCode(data[calc + 1]))
                    }
                }

                else -> fail(data)
            }
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun getUniCode(code: Int): String {
        return CACHED.computeIfAbsent(code.boxIn(0, 0xFFFF)) { calc ->
            6.toCharArray { posn ->
                when (posn) {
                    1 -> 'u'
                    2 -> getHexChar(calc mask 12)
                    3 -> getHexChar(calc mask 8)
                    4 -> getHexChar(calc mask 4)
                    5 -> getHexChar(calc mask 0)
                    else -> Escapers.ESCAPE_SLASH
                }
            }.getContentText(false)
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getExposedMethods(value: Any, static: Boolean = false, args: List<Class<Annotation>> = toListOf()): List<String> {
        return when (value) {
            is Class<*> -> getExposedMethods(value, static.isTrue(), args)
            is KClass<*> -> getExposedMethods(value, static.isTrue(), args)
            else -> getExposedMethods(value.javaClass, static.isTrue(), args)
        }
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getExposedMethods(value: Class<*>, static: Boolean = false, args: List<Class<Annotation>> = toListOf()): List<String> {
        if (value.isIgnoredTypes()) {
            return toListOf()
        }
        val names = StringSet()
        value.forEachMethod { method ->
            getExposedMethod(value, method, static, args).withSize { name, size ->
                if (size.isNotExhausted()) {
                    names.append("${name.copyOf()}(): ${method.toReturnTypeString()}")
                }
            }
        }
        return names.getList().toSorted()
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getExposedMethods(value: KClass<*>, static: Boolean = false, args: List<Class<Annotation>> = toListOf()): List<String> {
        return getExposedMethods(value.java, static.isTrue(), args)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getExposedMethod(value: Class<*>, method: Method, static: Boolean = false, args: List<Class<Annotation>> = toListOf()): String {
        if (method.isIgnoredMethods(static.isTrue(), value)) {
            return EMPTY_STRING
        }
        if (args.isNotExhausted() && (method.annotations.map { it.javaClass }.containsAll(args).isNotTrue())) {
            return EMPTY_STRING
        }
        return method.name.copyOf()
    }

    @JvmStatic
    @FrameworkDsl
    fun toAnnotationList(vararg args: Annotation): List<Class<Annotation>> {
        if (args.isExhausted()) {
            return toListOf()
        }
        return args.map { it.javaClass }.toBasicLinkedSet().getList()
    }

    @JvmStatic
    @FrameworkDsl
    fun <C : Class<out Annotation>> toAnnotationList(vararg args: C): List<C> {
        return args.unique()
    }

    @JvmStatic
    @FrameworkDsl
    fun getAnnotationList(vararg args: Class<out Annotation>): List<Class<Annotation>> {
        if (args.isExhausted()) {
            return toListOf()
        }
        return args.filterIsInstance<Annotation>().map { it.javaClass }.toBasicLinkedSet().getList()
    }

    @JvmStatic
    @FrameworkDsl
    fun user(): String = System.getProperty("user.name", "unknown")

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getSystemProperties(): SystemProperties {
        return System.getProperties()
    }

    @JvmStatic
    @FrameworkDsl
    @IgnoreForSerialize
    fun getSystemEnvironment(): MutableDictionary<String> {
        return System.getenv()
    }

    @JvmStatic
    @FrameworkDsl
    fun getProperties(): SystemProperties = SystemProperties()

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getProperties(data: URI, properties: SystemProperties = getProperties()): SystemProperties {
        return getProperties(data.toInputStream(), properties)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getProperties(data: URL, properties: SystemProperties = getProperties()): SystemProperties {
        return getProperties(data.toInputStream(), properties)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getProperties(data: File, properties: SystemProperties = getProperties()): SystemProperties {
        return getProperties(data.toInputStream(), properties)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getProperties(data: Path, properties: SystemProperties = getProperties()): SystemProperties {
        return getProperties(data.toInputStream(), properties)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getProperties(data: InputStreamSupplier, properties: SystemProperties = getProperties()): SystemProperties {
        return getProperties(data.toInputStream(), properties)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getProperties(data: ReadableByteChannel, properties: SystemProperties = getProperties()): SystemProperties {
        return getProperties(data.toInputStream(), properties)
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getProperties(data: Reader, properties: SystemProperties = getProperties()): SystemProperties {
        try {
            data.use { look -> properties.load(look) }
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return properties
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getProperties(data: InputStream, properties: SystemProperties = getProperties()): SystemProperties {
        try {
            data.use { look -> properties.load(look) }
        } catch (cause: Throwable) {
            Throwables.thrown(cause)
        }
        return properties
    }

    @JvmStatic
    @FrameworkDsl
    @JvmOverloads
    fun getProperties(data: CharSequence, properties: SystemProperties = getProperties()): SystemProperties {
        if (data.toTrimOr(EMPTY_STRING).isEmptyOrBlank().isNotTrue()) {
            try {
                return getProperties(getContentResourceByPath(data.toTrimOr(data.copyOf())), properties)
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
            }
        }
        return properties
    }

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to nameOf())
}