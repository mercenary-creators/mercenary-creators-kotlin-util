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

package co.mercenary.creators.kotlin.util.security

import co.mercenary.creators.kotlin.util.*
import java.security.Security

@IgnoreForSerialize
data class Algorithm @FrameworkDsl constructor(val service: String, val algorithms: List<String>) : HasMapNames {

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun toMapNames() = dictOf("service" to service, "algorithms" to algorithms)

    @FrameworkDsl
    fun forEach(block: (String) -> Unit) {
        if (algorithms.isNotExhausted()) {
            algorithms.forEach(block)
        }
    }

    @FrameworkDsl
    fun forEachIndexed(block: (Int, String) -> Unit) {
        if (algorithms.isNotExhausted()) {
            algorithms.forEachIndexed(block)
        }
    }

    companion object {

        @JvmStatic
        @FrameworkDsl
        fun forName(name: String): Algorithm = Algorithm(name, Security.getAlgorithms(name).toSorted())
    }
}