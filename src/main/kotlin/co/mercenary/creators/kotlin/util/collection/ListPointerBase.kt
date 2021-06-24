/*
 * Copyright (c) 2021, Mercenary Creators Company. All rights reserved.
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

@IgnoreForSerialize
interface ListPointerBase<T, B : ListPointerBase<T, B>> : MutableBase<B>, HasMapNames {

    @FrameworkDsl
    fun advance()

    @FrameworkDsl
    fun current(): T

    @FrameworkDsl
    fun indexOf(): Int

    @FrameworkDsl
    @IgnoreForSerialize
    fun isExhausted(): Boolean = isEmpty()

    @FrameworkDsl
    @IgnoreForSerialize
    fun isNotExhausted(): Boolean = isNotEmpty()

    @FrameworkDsl
    fun forEachRemaining(reset: Boolean = false, block: Convert<T, Unit>)

    @FrameworkDsl
    fun forEachRemainingIndexed(reset: Boolean = false, block: Indexed<T, Unit>)
}