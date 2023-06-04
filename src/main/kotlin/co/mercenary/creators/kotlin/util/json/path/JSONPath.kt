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

package co.mercenary.creators.kotlin.util.json.path

import co.mercenary.creators.kotlin.util.*

import co.mercenary.creators.kotlin.util.io.InputStreamSupplier
import co.mercenary.creators.kotlin.util.json.base.JSONMapper
import co.mercenary.creators.kotlin.util.json.base.JSONStatic

import com.jayway.jsonpath.*
import com.jayway.jsonpath.Option.SUPPRESS_EXCEPTIONS
import com.jayway.jsonpath.spi.json.JacksonJsonProvider
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider
import java.io.*
import java.net.*
import java.nio.channels.ReadableByteChannel
import java.nio.file.Path
import kotlin.reflect.KClass

@IgnoreForSerialize
object JSONPath {

    @FrameworkDsl
    private val CACHED = atomicMapOf<String, JSONCompiledPath>()

    @FrameworkDsl
    private val CONFIG: Configuration by lazy {
        Configuration
            .builder()
            .jsonProvider(JSONJacksonProvider(JSONStatic.mapperOf(true)))
            .mappingProvider(JSONJacksonMappingProvider(JSONStatic.mapperOf(false)))
            .options(SUPPRESS_EXCEPTIONS).build()
    }

    @JvmStatic
    @FrameworkDsl
    private fun lookup(path: String) = cached(path).compiled

    @JvmStatic
    @FrameworkDsl
    private fun lookup(path: CompiledPath) = lookup(path.getPathSpec())

    @JvmStatic
    @FrameworkDsl
    private fun cached(path: String) = CACHED.computeIfAbsent(path) { JSONCompiledPath(it) }

    @JvmStatic
    @FrameworkDsl
    private fun make(data: Any): PathEvaluationContext {
        return when (data) {
            is DocumentContext -> JSONEvaluationContext(data)
            is URI -> JSONEvaluationContext(data.toInputStream())
            is URL -> JSONEvaluationContext(data.toInputStream())
            is File -> JSONEvaluationContext(data.toInputStream())
            is Path -> JSONEvaluationContext(data.toInputStream())
            is Reader -> JSONEvaluationContext(data.toInputStream())
            is ByteArray -> JSONEvaluationContext(data.toInputStream())
            is InputStreamSupplier -> JSONEvaluationContext(data.toInputStream())
            is ReadableByteChannel -> JSONEvaluationContext(data.toInputStream())
            is InputStream -> JSONEvaluationContext(data)
            is CharSequence -> JSONEvaluationContext(data)
            else -> JSONEvaluationContext(data)
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun toPathEvaluationContext(data: Any) = make(data)

    @JvmStatic
    @FrameworkDsl
    fun compile(path: CharSequence): CompiledPath = cached(path.copyOf())

    @IgnoreForSerialize
    internal class JSONJacksonMappingProvider @FrameworkDsl constructor(mapper: JSONMapper) : JacksonMappingProvider(mapper)

    @IgnoreForSerialize
    internal class JSONJacksonProvider @FrameworkDsl constructor(mapper: JSONMapper) : JacksonJsonProvider(mapper) {

        @FrameworkDsl
        override fun createMap() = JSONObject()

        @FrameworkDsl
        override fun createArray() = JSONArray()
    }

    @IgnoreForSerialize
    internal class JSONCompiledPath(internal val compiled: JsonPath) : CompiledPath {

        @FrameworkDsl
        constructor(path: CharSequence) : this(JsonPath.compile(path.copyOf()))

        @FrameworkDsl
        override fun toString(): String = toMapNames().toSafeString()

        @FrameworkDsl
        @IgnoreForSerialize
        override fun isDefinite(): Boolean = compiled.isDefinite

        @FrameworkDsl
        @IgnoreForSerialize
        override fun getPathSpec(): String = compiled.path.copyOf()

        @FrameworkDsl
        override fun toMapNames() = dictOf("path" to getPathSpec(), "definite" to isDefinite())
    }

    @IgnoreForSerialize
    internal class JSONEvaluationContext @FrameworkDsl constructor(context: DocumentContext) : PathEvaluationContext {

        @FrameworkDsl
        constructor(data: Any) : this(JsonPath.parse(data, CONFIG))

        @FrameworkDsl
        constructor(data: InputStream) : this(JsonPath.parse(data, CONFIG))

        @FrameworkDsl
        constructor(data: CharSequence) : this(JsonPath.parse(data.copyOf(), CONFIG))

        @FrameworkDsl
        private val document = context

        @FrameworkDsl
        override fun clone() = copyOf()

        @FrameworkDsl
        override fun copyOf(): PathEvaluationContext = make(root() as Any)

        @FrameworkDsl
        override fun add(path: String, data: Maybe) = make(document.add(lookup(path), data))

        @FrameworkDsl
        override fun add(path: CompiledPath, data: Maybe) = make(document.add(lookup(path), data))

        @FrameworkDsl
        override fun set(path: String, data: Maybe) = make(document.set(lookup(path), data))

        @FrameworkDsl
        override fun set(path: CompiledPath, data: Maybe) = make(document.set(lookup(path), data))

        @FrameworkDsl
        override fun put(path: String, name: String, data: Maybe) = make(document.put(lookup(path), name, data))

        @FrameworkDsl
        override fun put(path: CompiledPath, name: String, data: Maybe) = make(document.put(lookup(path), name, data))

        @FrameworkDsl
        override fun delete(path: String) = make(document.delete(lookup(path)))

        @FrameworkDsl
        override fun delete(path: CompiledPath) = make(document.delete(lookup(path)))

        @FrameworkDsl
        override fun rename(path: String, last: String, name: String) = make(document.renameKey(lookup(path), last, name))

        @FrameworkDsl
        override fun rename(path: CompiledPath, last: String, name: String) = make(document.renameKey(lookup(path), last, name))

        @FrameworkDsl
        override fun <T : Any> eval(path: String, type: Class<T>): T = document.read(lookup(path), type)

        @FrameworkDsl
        override fun <T : Any> eval(path: String, type: TypeRef<T>): T = document.read(lookup(path), type)

        @FrameworkDsl
        override fun <T : Any> eval(path: String, type: KClass<T>): T = document.read(lookup(path), type.java)

        @FrameworkDsl
        override fun <T : Any> read(path: String, type: Class<T>): T? = document.read(lookup(path), type)

        @FrameworkDsl
        override fun <T : Any> read(path: String, type: TypeRef<T>): T? = document.read(lookup(path), type)

        @FrameworkDsl
        override fun <T : Any> read(path: String, type: KClass<T>): T? = document.read(lookup(path), type.java)

        @FrameworkDsl
        override fun <T : Any> eval(path: CompiledPath, type: Class<T>): T = document.read(lookup(path), type)

        @FrameworkDsl
        override fun <T : Any> eval(path: CompiledPath, type: TypeRef<T>): T = document.read(lookup(path), type)

        @FrameworkDsl
        override fun <T : Any> eval(path: CompiledPath, type: KClass<T>): T = document.read(lookup(path), type.java)

        @FrameworkDsl
        override fun <T : Any> read(path: CompiledPath, type: Class<T>): T? = document.read(lookup(path), type)

        @FrameworkDsl
        override fun <T : Any> read(path: CompiledPath, type: TypeRef<T>): T? = document.read(lookup(path), type)

        @FrameworkDsl
        override fun <T : Any> read(path: CompiledPath, type: KClass<T>): T? = document.read(lookup(path), type.java)

        @FrameworkDsl
        override fun map(path: String, func: (Maybe, PathEvaluationContext) -> Maybe) = make(document.map(lookup(path), mapper(func, this)).otherwise(document))

        @FrameworkDsl
        override fun map(path: CompiledPath, func: (Maybe, PathEvaluationContext) -> Maybe) = make(document.map(lookup(path), mapper(func, this)).otherwise(document))

        @FrameworkDsl
        override fun <T : Any> root(): T = document.json()

        @FrameworkDsl
        override fun toString(): String = JSONStatic.toJSONString(root())

        companion object {

            @JvmStatic
            @FrameworkDsl
            private fun mapper(mapper: (Maybe, PathEvaluationContext) -> Maybe, context: PathEvaluationContext) = { data: Maybe, _: Configuration -> mapper(data, context) } as MapFunction
        }
    }
}