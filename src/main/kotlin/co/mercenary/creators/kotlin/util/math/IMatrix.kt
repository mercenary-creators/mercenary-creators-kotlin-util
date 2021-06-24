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

package co.mercenary.creators.kotlin.util.math

import co.mercenary.creators.kotlin.util.*

interface IMatrix<R : IVector<R>, M : IMatrix<R, M>> : Sliced<M>, Iterable<R> {

    @FrameworkDsl
    fun rows(): Int

    @FrameworkDsl
    fun cols(): Int

    @FrameworkDsl
    @IgnoreForSerialize
    fun isSquare(): Boolean

    @FrameworkDsl
    @IgnoreForSerialize
    fun isNotSquare(): Boolean = isSquare().isNotTrue()

    @FrameworkDsl
    operator fun get(index: Int): R

    @FrameworkDsl
    operator fun get(index: Int, place: Int): Double

    @FrameworkDsl
    operator fun set(index: Int, place: Int, value: Double)
}