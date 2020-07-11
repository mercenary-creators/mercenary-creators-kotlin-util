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
open class BaseCachedContentResourceLoader @JvmOverloads @CreatorsDsl constructor(name: String = EMPTY_STRING, loader: ClassLoader? = null) : BaseContentResourceLoader(name, loader), CachedContentResourceLoader {

    private val maps = atomicMapOf<String, CachedContentResource>()

    private val keep = object : MutableCachedKeys {

        @CreatorsDsl
        private fun keys() = maps.keys

        @CreatorsDsl
        override fun clear() {
            keys().clear()
        }

        @CreatorsDsl
        override val size: Int
            @IgnoreForSerialize
            get() = keys().size

        @CreatorsDsl
        @IgnoreForSerialize
        override fun isEmpty() = keys().isEmpty()

        @CreatorsDsl
        override operator fun iterator() = keys().iterator()

        @CreatorsDsl
        override operator fun contains(data: CharSequence) = keys().contains(data.toString())
    }

    override operator fun get(path: String): CachedContentResource {
        return maps.computeIfAbsent(path) {
            super.get(it).toContentCache()
        }
    }

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isContentCache() = true

    @CreatorsDsl
    override val keys: MutableCachedKeys
        @IgnoreForSerialize
        get() = keep

    override fun toString() = toMapNames().toString()

    override fun hashCode() = toMapNames().hashCode()

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is BaseCachedContentResourceLoader -> this === other || toMapNames() isSameAs other.toMapNames()
        else -> false
    }

    companion object {

        @CreatorsDsl
        val INSTANCE: CachedContentResourceLoader by lazy {
            BaseCachedContentResourceLoader()
        }
    }
}