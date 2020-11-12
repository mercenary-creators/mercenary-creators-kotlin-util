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

package co.mercenary.creators.kotlin.util.io

import co.mercenary.creators.kotlin.util.*

@IgnoreForSerialize
class ContentMimeType @CreatorsDsl private constructor(private val mime: MimeBase) : StandardInterfaces<ContentMimeType> {

    @CreatorsDsl
    @JvmOverloads
    constructor(type: String = DEFAULT_CONTENT_TYPE) : this(build(type))

    @CreatorsDsl
    val base: String
        @IgnoreForSerialize
        get() = mime.base

    @CreatorsDsl
    val type: String
        @IgnoreForSerialize
        get() = mime.type

    @CreatorsDsl
    val part: String
        @IgnoreForSerialize
        get() = mime.part

    @CreatorsDsl
    override fun clone() = copyOf()

    @CreatorsDsl
    override fun copyOf() = ContentMimeType(mime.copyOf())

    @CreatorsDsl
    override fun toString() = mime.toString()

    @CreatorsDsl
    override fun hashCode() = mime.hashCode()

    @CreatorsDsl
    override fun toMapNames() = mime.toMapNames()

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is ContentMimeType -> this === other || mime == other.mime
        else -> false
    }

    @CreatorsDsl
    infix fun isMatchOf(other: String): Boolean {
        return mime isMatchOf other
    }

    @CreatorsDsl
    infix fun isMatchOf(other: ContentMimeType): Boolean {
        return mime isMatchOf other.mime
    }

    companion object {

        @CreatorsDsl
        val DEFAULT_CONTENT_MIME_TYPE: ContentMimeType by lazy {
            ContentMimeType(BASE)
        }

        @CreatorsDsl
        private val BASE = MimeBase()

        @CreatorsDsl
        private val HASH = atomicMapOf(DEFAULT_CONTENT_TYPE to BASE)

        @JvmStatic
        @CreatorsDsl
        private fun build(type: String): MimeBase {
            return when (type.isDefaultContentType()) {
                true -> BASE
                else -> HASH.computeIfAbsent(type) { name ->
                    parse(name)
                }
            }
        }

        @JvmStatic
        @CreatorsDsl
        private fun parse(type: String): MimeBase {
            return try {
                MimeBase(type)
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
                BASE
            }
        }

        @IgnoreForSerialize
        private class MimeBase private constructor(private val mime: javax.activation.MimeType) : StandardInterfaces<MimeBase> {

            @CreatorsDsl
            constructor() : this("application", "octet-stream")

            @CreatorsDsl
            constructor(type: String) : this(javax.activation.MimeType(type))

            @CreatorsDsl
            constructor(base: String, part: String) : this(javax.activation.MimeType(base, part))

            private val list = mime.parameters

            private val prop: Map<String, String> by lazy {
                LinkedHashMap<String, String>(list.size()).also { self ->
                    list.names.toList().map { name: Any -> name.toString().toLowerTrimEnglish() }.sorted().forEach { name ->
                        self[name] = list[name]
                    }
                }
            }

            val base: String
                @IgnoreForSerialize
                get() = mime.baseType

            val part: String
                @IgnoreForSerialize
                get() = mime.subType

            val type: String
                @IgnoreForSerialize
                get() = mime.primaryType

            @CreatorsDsl
            override fun toMapNames(): Map<String, Any?> {
                return dictOf("type" to type, "part" to part).let {
                    if (list.isEmpty) it else it.plus("prop" to prop.toMap())
                }
            }

            @CreatorsDsl
            override fun clone() = copyOf()

            @CreatorsDsl
            override fun copyOf() = MimeBase(toString())

            @CreatorsDsl
            override fun toString() = mime.toString()

            @CreatorsDsl
            override fun hashCode() = mime.toString().hashCode()

            @CreatorsDsl
            override fun equals(other: Any?) = when (other) {
                is MimeBase -> this === other || toString() isSameAs other.toString()
                else -> false
            }

            @CreatorsDsl
            infix fun isMatchOf(other: String): Boolean = try {
                mime.match(other)
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
                false
            }

            @CreatorsDsl
            infix fun isMatchOf(other: MimeBase): Boolean = try {
                mime.match(other.mime)
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
                false
            }
        }
    }
}