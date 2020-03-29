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
import co.mercenary.creators.kotlin.util.logging.ILogging
import co.mercenary.creators.kotlin.util.type.Copyable
import javax.activation.MimeType

@IgnoreForSerialize
class ContentMimeType @JvmOverloads constructor(type: String = DEFAULT_CONTENT_TYPE) : Copyable<ContentMimeType>, Cloneable, HasMapNames {

    private val mime = make(type)

    override fun clone() = copyOf()

    override fun copyOf() = ContentMimeType(mime.toString())

    override fun toString() = mime.baseType.toString()

    override fun hashCode() = mime.toString().hashCode()

    override fun toMapNames() = mapOf("base" to mime.primaryType, "kind" to mime.subType)

    @AssumptionDsl
    override fun equals(other: Any?) = when (other) {
        is ContentMimeType -> this === other || mime.toString() isSameAs other.mime.toString()
        else -> false
    }

    @AssumptionDsl
    fun isMatchOf(other: String): Boolean {
        return try {
            mime.match(other)
        }
        catch (cause: Throwable) {
            logs.error(cause) {
                other
            }
            Throwables.thrown(cause)
            false
        }
    }

    @AssumptionDsl
    fun isMatchOf(other: ContentMimeType): Boolean {
        return mime.match(other.mime)
    }

    companion object {

        private val logs: ILogging by lazy {
            LoggingFactory.logger(ContentMimeType::class)
        }

        @JvmStatic
        fun make(string: String): MimeType {
            val type = toTrimOrElse(string, DEFAULT_CONTENT_TYPE)
            return try {
                MimeType(type)
            }
            catch (cause: Throwable) {
                logs.error(cause) {
                    type
                }
                Throwables.thrown(cause)
                MimeType(DEFAULT_CONTENT_TYPE)
            }
        }
    }
}