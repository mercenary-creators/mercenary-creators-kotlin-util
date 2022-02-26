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

package co.mercenary.creators.kotlin.util.json.base

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.io.InputStreamSupplier
import co.mercenary.creators.kotlin.util.json.module.MercenaryPackageVersion
import co.mercenary.creators.kotlin.util.time.TimeAndDate
import co.mercenary.creators.kotlin.util.type.ParameterizedTypeReference
import com.fasterxml.jackson.annotation.JsonIgnoreType
import com.fasterxml.jackson.core.*
import com.fasterxml.jackson.core.JsonGenerator.Feature.*
import com.fasterxml.jackson.core.JsonParser.Feature.*
import com.fasterxml.jackson.core.json.JsonWriteFeature
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.DeserializationFeature.*
import com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT
import com.jayway.jsonpath.TypeRef
import java.io.*
import java.lang.reflect.Type
import java.net.*
import java.nio.channels.ReadableByteChannel
import java.nio.file.Path
import kotlin.reflect.KClass

@IgnoreForSerialize
open class JSONMapper : ObjectMapper, StandardInterfaces<JSONMapper>, JSONVersioned<Version> {

    @FrameworkDsl
    @JvmOverloads
    constructor(pretty: Boolean = true) : super() {
        pretty(pretty).setDefaultModules().setDefaultDateFormat().setDefaultTimeZone()
            .features(true, ALLOW_COMMENTS, WRITE_BIGDECIMAL_AS_PLAIN)
            .features(false, AUTO_CLOSE_SOURCE, AUTO_CLOSE_TARGET, FAIL_ON_UNKNOWN_PROPERTIES, FAIL_ON_IGNORED_PROPERTIES, ACCEPT_FLOAT_AS_INT, JsonWriteFeature.ESCAPE_NON_ASCII)
    }

    @FrameworkDsl
    protected constructor(parent: JSONMapper) : super(parent)

    @FrameworkDsl
    private fun ObjectMapper.setDefaultModules(): ObjectMapper = findAndRegisterModules()

    @FrameworkDsl
    private fun ObjectMapper.setDefaultTimeZone(): ObjectMapper = setTimeZone(TimeAndDate.getDefaultTimeZone())

    @FrameworkDsl
    private fun ObjectMapper.setDefaultDateFormat(): ObjectMapper = setDateFormat(TimeAndDate.getDefaultDateFormat())

    @FrameworkDsl
    private fun ObjectMapper.features(flag: Boolean, data: Any, vararg list: Any): ObjectMapper {
        toListOf(data, *list).forEach { feature ->
            when (feature) {
                is JsonParser.Feature -> configure(feature, flag)
                is SerializationFeature -> configure(feature, flag)
                is JsonGenerator.Feature -> configure(feature, flag)
                is DeserializationFeature -> configure(feature, flag)
                is JsonWriteFeature -> configure(feature.mappedFeature(), flag)
            }
        }
        return this
    }

    @FrameworkDsl
    @IgnoreForSerialize
    fun getFormatter(): JSONFormatter {
        return ObjectWriterJSONFormatter(writer())
    }

    @FrameworkDsl
    override fun copy() = copyOf()

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = JSONMapper(this)

    @FrameworkDsl
    override fun version() = MercenaryPackageVersion.version()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getVersion(): Version = version()

    @FrameworkDsl
    override fun canSerialize(type: Class<*>?): Boolean {
        return when {
            type == null -> false
            type.isAnnotationPresent(JsonIgnoreType::class.java) -> {
                type.getAnnotation(JsonIgnoreType::class.java).value.isNotTrue()
            }
            type.isAnnotationPresent(IgnoreForSerialize::class.java) -> {
                type.getAnnotation(IgnoreForSerialize::class.java).value.isNotTrue()
            }
            else -> super.canSerialize(type)
        }
    }

    @FrameworkDsl
    override fun toString() = nameOf()

    @FrameworkDsl
    override fun hashCode() = getVersion().hashCode()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is JSONMapper -> this === other || super.equals(other)
        else -> false
    }

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to nameOf())

    @FrameworkDsl
    private fun pretty(pretty: Boolean): ObjectMapper = if (pretty) setDefaultPrettyPrinter(JSONStatic.getDefaultPrettyPrinter()).enable(INDENT_OUTPUT) else disable(INDENT_OUTPUT)

    @FrameworkDsl
    fun canSerializeClass(type: Class<*>?) = canSerialize(type)

    @FrameworkDsl
    fun canSerializeClass(type: JavaType?) = if (null == type) false else canSerializeClass(type.rawClass)

    @FrameworkDsl
    fun canSerializeClass(type: KClass<*>?) = if (null == type) false else canSerializeClass(type.java)

    @FrameworkDsl
    fun canSerializeValue(value: Any?) = if (null == value) false else canSerializeClass(value.javaClass)

    @FrameworkDsl
    fun canDeserializeValue(value: Any?) = when (value) {
        null -> false
        is JavaType -> canDeserialize(constructTypeOf(value))
        is Class<*> -> canDeserialize(constructTypeOf(value))
        is KClass<*> -> canDeserialize(constructTypeOf(value))
        is TypeRef<*> -> canDeserialize(constructTypeOf(value))
        is TypeReference<*> -> canDeserialize(constructTypeOf(value))
        is ParameterizedTypeReference<*> -> canDeserialize(constructTypeOf(value))
        else -> canDeserialize(constructTypeOf(value.javaClass))
    }

    @FrameworkDsl
    fun toByteArray(data: Any): ByteArray = writeValueAsBytes(data)

    @FrameworkDsl
    fun toJSONString(data: Any): String = writeValueAsString(data)

    @FrameworkDsl
    fun constructTypeOf(type: Type): JavaType = constructType(type)

    @FrameworkDsl
    fun constructTypeOf(type: KClass<*>): JavaType = constructType(type.java)

    @FrameworkDsl
    fun constructTypeOf(type: JavaType): JavaType = type

    @FrameworkDsl
    fun constructTypeOf(type: TypeRef<*>): JavaType = constructType(type.type)

    @FrameworkDsl
    fun constructTypeOf(type: TypeReference<*>): JavaType = constructType(type)

    @FrameworkDsl
    fun constructTypeOf(type: ParameterizedTypeReference<*>): JavaType = constructType(type.getType())

    @FrameworkDsl
    fun <T : Any> toDataType(value: Any, type: Class<T>): T = convertValue(value, type)

    @FrameworkDsl
    fun <T : Any> toDataType(value: Any, type: KClass<T>): T = convertValue(value, type.java)

    @FrameworkDsl
    fun <T : Any> toDataType(value: Any, type: TypeReference<T>): T = convertValue(value, type)

    @FrameworkDsl
    fun <T : Any> toDeepCopy(value: T): T = readerFor(value.javaClass).readValue(toByteArray(value))

    @FrameworkDsl
    fun <T : Any> toDeepCopy(value: T, type: Class<T>): T = readerFor(type).readValue(toByteArray(value))

    @FrameworkDsl
    fun <T : Any> toDeepCopy(value: T, type: KClass<T>): T = readerFor(type.java).readValue(toByteArray(value))

    @FrameworkDsl
    fun <T : Any> toDeepCopy(value: T, type: TypeReference<T>): T = readerFor(type).readValue(toByteArray(value))

    @FrameworkDsl
    fun <T : Any> jsonRead(value: URI, type: TypeReference<T>): T = value.toInputStream().use { readerFor(type).readValue(it) }

    @FrameworkDsl
    fun <T : Any> jsonRead(value: URL, type: TypeReference<T>): T = value.toInputStream().use { readerFor(type).readValue(it) }

    @FrameworkDsl
    fun <T : Any> jsonRead(value: CharSequence, type: TypeReference<T>): T = readerFor(type).readValue(value.copyOf())

    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> jsonRead(value: ByteArray, type: TypeReference<T>, copy: Boolean = false): T = readerFor(type).readValue(value.toByteArray(copy))

    @FrameworkDsl
    fun <T : Any> jsonRead(value: File, type: TypeReference<T>): T = value.toInputStream().use { readerFor(type).readValue(it) }

    @FrameworkDsl
    fun <T : Any> jsonRead(value: Path, type: TypeReference<T>): T = value.toInputStream().use { readerFor(type).readValue(it) }

    @FrameworkDsl
    fun <T : Any> jsonRead(value: InputStreamSupplier, type: TypeReference<T>): T = value.toInputStream().use { readerFor(type).readValue(it) }

    @FrameworkDsl
    fun <T : Any> jsonRead(value: ReadableByteChannel, type: TypeReference<T>): T = value.toInputStream().use { readerFor(type).readValue(it) }

    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> jsonRead(value: Reader, type: TypeReference<T>, done: Boolean = true): T = if (done) value.use { readerFor(type).readValue(it) } else readerFor(type).readValue(value)

    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> jsonRead(value: InputStream, type: TypeReference<T>, done: Boolean = true): T = if (done) value.use { readerFor(type).readValue(it) } else readerFor(type).readValue(value)

    @FrameworkDsl
    fun <T : Any> jsonRead(value: URI, type: Class<T>): T = value.toInputStream().use { readerFor(type).readValue(it) }

    @FrameworkDsl
    fun <T : Any> jsonRead(value: URL, type: Class<T>): T = value.toInputStream().use { readerFor(type).readValue(it) }

    @FrameworkDsl
    fun <T : Any> jsonRead(value: CharSequence, type: Class<T>): T = readerFor(type).readValue(value.copyOf())

    @FrameworkDsl
    fun <T : Any> jsonRead(value: ByteArray, type: Class<T>): T = readerFor(type).readValue(value)

    @FrameworkDsl
    fun <T : Any> jsonRead(value: File, type: Class<T>): T = value.toInputStream().use { readerFor(type).readValue(it) }

    @FrameworkDsl
    fun <T : Any> jsonRead(value: Path, type: Class<T>): T = value.toInputStream().use { readerFor(type).readValue(it) }

    @FrameworkDsl
    fun <T : Any> jsonRead(value: InputStreamSupplier, type: Class<T>): T = value.toInputStream().use { readerFor(type).readValue(it) }

    @FrameworkDsl
    fun <T : Any> jsonRead(value: ReadableByteChannel, type: Class<T>): T = value.toInputStream().use { readerFor(type).readValue(it) }

    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> jsonRead(value: Reader, type: Class<T>, done: Boolean = true): T = if (done) value.use { readerFor(type).readValue(it) } else readerFor(type).readValue(value)

    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> jsonRead(value: InputStream, type: Class<T>, done: Boolean = true): T = if (done) value.use { readerFor(type).readValue(it) } else readerFor(type).readValue(value)

    @FrameworkDsl
    fun <T : Any> jsonRead(value: URI, type: KClass<T>): T = value.toInputStream().use { readerFor(type.java).readValue(it) }

    @FrameworkDsl
    fun <T : Any> jsonRead(value: URL, type: KClass<T>): T = value.toInputStream().use { readerFor(type.java).readValue(it) }

    @FrameworkDsl
    fun <T : Any> jsonRead(value: CharSequence, type: KClass<T>): T = readerFor(type.java).readValue(value.copyOf())

    @FrameworkDsl
    fun <T : Any> jsonRead(value: ByteArray, type: KClass<T>): T = readerFor(type.java).readValue(value)

    @FrameworkDsl
    fun <T : Any> jsonRead(value: File, type: KClass<T>): T = value.toInputStream().use { readerFor(type.java).readValue(it) }

    @FrameworkDsl
    fun <T : Any> jsonRead(value: Path, type: KClass<T>): T = value.toInputStream().use { readerFor(type.java).readValue(it) }

    @FrameworkDsl
    fun <T : Any> jsonRead(value: InputStreamSupplier, type: KClass<T>): T = value.toInputStream().use { readerFor(type.java).readValue(it) }

    @FrameworkDsl
    fun <T : Any> jsonRead(value: ReadableByteChannel, type: KClass<T>): T = value.toInputStream().use { readerFor(type.java).readValue(it) }

    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> jsonRead(value: Reader, type: KClass<T>, done: Boolean = true): T = if (done) value.use { readerFor(type.java).readValue(it) } else readerFor(type.java).readValue(value)

    @FrameworkDsl
    @JvmOverloads
    fun <T : Any> jsonRead(value: InputStream, type: KClass<T>, done: Boolean = true): T = if (done) value.use { readerFor(type.java).readValue(it) } else readerFor(type.java).readValue(value)

    companion object {

        private const val serialVersionUID = 2L

        @IgnoreForSerialize
        private class ObjectWriterJSONFormatter @FrameworkDsl constructor(args: ObjectWriter) : JSONFormatter {

            @FrameworkDsl
            private val look = args

            @FrameworkDsl
            private fun lookOf() = look

            @FrameworkDsl
            @IgnoreForSerialize
            override fun isPretty(): Boolean {
                return lookOf().isEnabled(INDENT_OUTPUT)
            }

            @FrameworkDsl
            @IgnoreForSerialize
            override fun setPrettyPrinter(pretty: PrettyPrinter): JSONFormatter {
                return ObjectWriterJSONFormatter(lookOf().with(pretty))
            }

            @FrameworkDsl
            @IgnoreForSerialize
            override fun setPretty(pretty: Boolean): JSONFormatter {
                return ObjectWriterJSONFormatter(pretty(lookOf(), pretty))
            }

            @FrameworkDsl
            @IgnoreForSerialize
            override fun isEscaped(): Boolean {
                return lookOf().isEnabled(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature())
            }

            @FrameworkDsl
            @IgnoreForSerialize
            override fun setEscaped(escape: Boolean): JSONFormatter {
                return ObjectWriterJSONFormatter(escaped(lookOf(), escape))
            }

            @FrameworkDsl
            override fun toJSONString(data: Any, escape: Boolean): String {
                if (escape == isEscaped()) {
                    return lookOf().writeValueAsString(data)
                }
                return escaped(lookOf(), escape).writeValueAsString(data)
            }

            @FrameworkDsl
            override fun clone() = copyOf()

            @FrameworkDsl
            override fun copyOf(): JSONFormatter {
                return ObjectWriterJSONFormatter(lookOf())
            }

            @FrameworkDsl
            override fun hashCode() = idenOf()

            @FrameworkDsl
            override fun toString() = toJSONString(toMapNames())

            @FrameworkDsl
            override fun equals(other: Any?) = when (other) {
                is ObjectWriterJSONFormatter -> this === other || lookOf() === other.lookOf() && toMapNames() isSameAs other.toMapNames()
                else -> false
            }

            @FrameworkDsl
            override fun toMapNames() = dictOf("pretty" to isPretty(), "escaped" to isEscaped(), "version" to CREATORS_VERSION_INFO)

            companion object {

                @JvmStatic
                @FrameworkDsl
                private fun escaped(look: ObjectWriter, escape: Boolean): ObjectWriter {
                    return when (escape) {
                        true -> look.with(JsonWriteFeature.ESCAPE_NON_ASCII)
                        else -> look.without(JsonWriteFeature.ESCAPE_NON_ASCII)
                    }
                }

                @JvmStatic
                @FrameworkDsl
                private fun pretty(look: ObjectWriter, pretty: Boolean): ObjectWriter {
                    return when (pretty) {
                        true -> look.with(INDENT_OUTPUT)
                        else -> look.without(INDENT_OUTPUT)
                    }
                }
            }
        }
    }
}