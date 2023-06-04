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

package co.mercenary.creators.kotlin.util.json.typicode

import co.mercenary.creators.kotlin.util.*

data class TypicodePhotoData @FrameworkDsl constructor(val albumId: Int, val id: Int, val title: String, val url: String, val thumbnailUrl: String) : TypicodeBase<TypicodePhotoData> {

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = toDeepCopy()

    @FrameworkDsl
    override fun toString() = toJSONString()

    companion object {

        @FrameworkDsl
        const val RESULTS = 5000

        @FrameworkDsl
        const val SUBPATH = "/photos"

        @JvmStatic
        @FrameworkDsl
        fun sizeOf() = RESULTS.copyOf()

        @JvmStatic
        @FrameworkDsl
        @JvmOverloads
        fun linkOf(secure: Boolean = false) = pathOf(secure.copyOf()).linkOf()

        @JvmStatic
        @FrameworkDsl
        @JvmOverloads
        fun pathOf(secure: Boolean = false) = SUBPATH.toTypicodePath(secure.copyOf())
    }
}