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
import java.io.*

@IgnoreForSerialize
abstract class AbstractChainInputStream @CreatorsDsl constructor(private val chain: Iterator<InputStream>) : SequenceInputStream(chain.toEnumeration()), OpenAutoClosable, Container, HasMapNames, Validated {

    private val open = true.toAtomic()

    @CreatorsDsl
    override fun close() {
        if (open.isTrueToFalse()) {
            try {
                super.close()
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
            }
        }
    }

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isEmpty(): Boolean {
        return try {
            chain.hasNext()
        } catch (cause: Throwable) {
            true
        }
    }

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isOpen(): Boolean = open.isTrue()

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isValid(): Boolean = isOpen() && isNotEmpty()

    @CreatorsDsl
    override fun hashCode() = idenOf()

    @CreatorsDsl
    override fun toString() = toMapNames().toSafeString()

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is AbstractChainInputStream -> this === other || isOpen() == other.isOpen() && isEmpty() == other.isEmpty()
        else -> false
    }

    @CreatorsDsl
    override fun toMapNames() = dictOf("name" to nameOf(), "open" to isOpen(), "empty" to isEmpty(), "valid" to isValid())
}