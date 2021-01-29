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

package co.mercenary.creators.kotlin.util.collection

import co.mercenary.creators.kotlin.util.*

interface MutableMapBase<K, V, M : MutableMapBase<K, V, M>> : MutableBase<M>, MutableMap<K, V> {

    @FrameworkDsl
    override fun sizeOf(): Int {
        return size.copyOf()
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isEmpty(): Boolean {
        return sizeOf() == 0
    }

    @FrameworkDsl
    fun toMap(): Map<K, V> {
        return when (sizeOf()) {
            0 -> toMapOf()
            else -> toPairs().mapTo()
        }
    }

    @FrameworkDsl
    fun enhance(base: MutableMap<K, V>): MutableMap<K, V> {
        return base
    }
}