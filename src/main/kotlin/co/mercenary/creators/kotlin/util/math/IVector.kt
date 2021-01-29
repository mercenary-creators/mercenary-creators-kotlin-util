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

package co.mercenary.creators.kotlin.util.math

import co.mercenary.creators.kotlin.util.*

interface IVector<V : IVector<V>> : Sliced<V> {

    @FrameworkDsl
    fun sort(index: Int, place: Int, descending: Boolean = false): V

    @FrameworkDsl
    fun reverse(): V

    @FrameworkDsl
    fun reverse(index: Int, place: Int): V

    @FrameworkDsl
    fun average(default: Double = 0.0): Double

    @FrameworkDsl
    fun average(index: Int, place: Int, default: Double = 0.0): Double

    @FrameworkDsl
    operator fun get(index: Int): Double

    @FrameworkDsl
    operator fun set(index: Int, value: Double)

    @FrameworkDsl
    fun reduce(reducer: (Double, Double) -> Double): Double

    @FrameworkDsl
    fun reduce(index: Int, place: Int, reducer: (Double, Double) -> Double): Double
}