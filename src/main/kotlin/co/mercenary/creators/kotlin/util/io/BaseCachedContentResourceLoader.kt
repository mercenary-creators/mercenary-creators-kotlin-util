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
open class BaseCachedContentResourceLoader @JvmOverloads @FrameworkDsl constructor(name: String = EMPTY_STRING, loader: ClassLoader? = null) : BaseContentResourceLoader(name, loader), CachedContentResourceLoader {

    @FrameworkDsl
    private val maps = atomicMapOf<String, CachedContentResource>()

    override operator fun get(path: CharSequence): CachedContentResource {
        return maps.computeIfAbsent(path.copyOf()) {
            super.get(it).toContentCache()
        }
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isContentCache() = true

    @FrameworkDsl
    override val keys: MutableCachedKeys
        @IgnoreForSerialize
        get() = ViewOfKeys(maps)

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun hashCode() = toMapNames().hashOf()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is BaseCachedContentResourceLoader -> this === other || toMapNames() isSameAs other.toMapNames()
        else -> false
    }

    private class ViewOfKeys @FrameworkDsl constructor(maps: AtomicHashMap<String, CachedContentResource>) : MutableCachedKeys {

        @FrameworkDsl
        private val view: AtomicHashMapKeysView<String, CachedContentResource> = maps.keys

        @FrameworkDsl
        override fun sizeOf(): Int {
            return view.sizeOf()
        }

        @FrameworkDsl
        override operator fun iterator() = view.iterator()

        @FrameworkDsl
        override operator fun contains(data: String): Boolean = view.contains(data)

        @FrameworkDsl
        override fun clear() = view.clear()

        @FrameworkDsl
        override fun hashCode() = iterator().hashOf()

        @FrameworkDsl
        override fun toString() = view.toSafeString()

        @FrameworkDsl
        override fun equals(other: Any?) = when (other) {
            is ViewOfKeys -> this === other || view == other.view
            else -> false
        }
    }

    companion object {

        @FrameworkDsl
        val INSTANCE: CachedContentResourceLoader by lazy {
            BaseCachedContentResourceLoader()
        }
    }
}