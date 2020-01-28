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

import co.mercenary.creators.kotlin.util.AtomicHashMap

open class BaseCachedContentResourceLoader @JvmOverloads constructor(loader: ClassLoader? = null, parent: ContentResourceLoader? = null) : BaseContentResourceLoader(loader, parent), CachedContentResourceLoader {

    private val maps = AtomicHashMap<String, CachedContentResource>()

    override operator fun get(path: String): CachedContentResource {
        return maps.computeIfAbsent(path) {
            super.get(it).toContentCache()
        }
    }

    override val keys: MutableSet<String>
        get() = maps.keys

    companion object {
        val INSTANCE: CachedContentResourceLoader by lazy {
            BaseCachedContentResourceLoader()
        }
    }
}