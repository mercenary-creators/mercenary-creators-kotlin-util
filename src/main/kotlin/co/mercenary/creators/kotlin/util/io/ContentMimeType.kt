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

package co.mercenary.creators.kotlin.util.io

import co.mercenary.creators.kotlin.util.*

@IgnoreForSerialize
class ContentMimeType @FrameworkDsl constructor(private val mime: MimeBase) : StandardInterfaces<ContentMimeType> {

    @FrameworkDsl
    @JvmOverloads
    constructor(type: String = DEFAULT_CONTENT_TYPE) : this(build(type))

    @FrameworkDsl
    val base: String
        @IgnoreForSerialize
        get() = mime.base

    @FrameworkDsl
    val type: String
        @IgnoreForSerialize
        get() = mime.type

    @FrameworkDsl
    val part: String
        @IgnoreForSerialize
        get() = mime.part

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = ContentMimeType(mime)

    @FrameworkDsl
    override fun toString() = mime.toString()

    @FrameworkDsl
    override fun hashCode() = mime.hashCode()

    @FrameworkDsl
    override fun toMapNames() = mime.toMapNames()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is ContentMimeType -> this === other || mime == other.mime
        else -> false
    }

    @FrameworkDsl
    infix fun isMatchOf(other: String): Boolean {
        return mime isMatchOf other
    }

    @FrameworkDsl
    infix fun isMatchOf(other: ContentMimeType): Boolean {
        return mime isMatchOf other.mime
    }

    companion object {

        @FrameworkDsl
        private val DEFAULT_CONTENT_MIME_TYPE: ContentMimeType by lazy {
            ContentMimeType(BASE)
        }

        @FrameworkDsl
        private val BASE = MimeBase()

        @FrameworkDsl
        private val HASH = atomicMapOf(DEFAULT_CONTENT_TYPE to BASE)

        @JvmStatic
        @FrameworkDsl
        fun getDefaultContentMimeType(): ContentMimeType = DEFAULT_CONTENT_MIME_TYPE

        @JvmStatic
        @FrameworkDsl
        private fun build(type: String): MimeBase {
            return when (type.isDefaultContentType()) {
                true -> BASE
                else -> HASH.computeIfAbsent(type) { name ->
                    parse(name)
                }
            }
        }

        @JvmStatic
        @FrameworkDsl
        private fun parse(type: String): MimeBase {
            return try {
                MimeBase(type)
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
                BASE
            }
        }

        @IgnoreForSerialize
        class MimeBase @FrameworkDsl private constructor(private val mime: jakarta.activation.MimeType) : StandardInterfaces<MimeBase> {

            @FrameworkDsl
            constructor() : this("application", "octet-stream")

            @FrameworkDsl
            constructor(type: String) : this(jakarta.activation.MimeType(type))

            @FrameworkDsl
            constructor(base: String, part: String) : this(jakarta.activation.MimeType(base, part))

            @FrameworkDsl
            private val list = mime.parameters

            @FrameworkDsl
            private val prop: Map<String, String> by lazy {
                StringDictionary().also { self ->
                    list.names.toList().map { name: Any -> name.toString().toLowerTrimEnglish() }.sorted().forEach { name ->
                        self[name] = list[name]
                    }
                }.toReadOnly()
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

            @FrameworkDsl
            override fun toMapNames(): Map<String, Any?> {
                return dictOf("type" to type, "part" to part).let {
                    if (list.isEmpty) it else it.plus("prop" to prop)
                }
            }

            @FrameworkDsl
            override fun clone() = copyOf()

            @FrameworkDsl
            override fun copyOf() = MimeBase(toString())

            @FrameworkDsl
            override fun toString() = mime.toString()

            @FrameworkDsl
            override fun hashCode() = toString().hashCode()

            @FrameworkDsl
            override fun equals(other: Any?) = when (other) {
                is MimeBase -> this === other || toString() == other.toString()
                else -> false
            }

            @FrameworkDsl
            infix fun isMatchOf(other: String): Boolean = try {
                mime.match(other)
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
                false
            }

            @FrameworkDsl
            infix fun isMatchOf(other: MimeBase): Boolean = try {
                mime.match(other.mime)
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
                false
            }
        }
    }
}