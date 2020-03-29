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
abstract class AbstractCachedContentResource @JvmOverloads constructor(data: ByteArray, path: String, type: String = DEFAULT_CONTENT_TYPE, time: Long = getTimeStamp()) : AbstractContentResourceBase(path, type, time), CachedContentResource {

    private val save = data.copyOf()

    @IgnoreForSerialize
    override fun getContentData() = save.copyOf()

    @IgnoreForSerialize
    override fun getInputStream() = save.inputStream()

    @IgnoreForSerialize
    override fun getContentSize() = save.size.toLong()

    @AssumptionDsl
    @IgnoreForSerialize
    override fun isContentThere() = true

    @AssumptionDsl
    @IgnoreForSerialize
    override fun isContentCache() = true

    @IgnoreForSerialize
    override fun toContentCache() = this

    @AssumptionDsl
    @IgnoreForSerialize
    protected fun toDataHashOf(): Int = getContentPath().hashOf(save)

    @AssumptionDsl
    protected fun isDataSameAs(data: AbstractCachedContentResource): Boolean = save isSameAs data.save
}