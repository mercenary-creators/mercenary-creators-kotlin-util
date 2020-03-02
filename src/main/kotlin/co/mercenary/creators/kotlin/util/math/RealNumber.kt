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

interface RealNumber<T : RealNumber<T>> : BaseNumber<T> {
    operator fun div(value: Int): T = div(value.toDouble())
    operator fun div(value: Long): T = div(value.toDouble())
    operator fun div(value: Short): T = div(value.toDouble())
    operator fun div(value: Float): T = div(value.toDouble())
    operator fun div(value: Double): T
    operator fun plus(value: Int): T = plus(value.toDouble())
    operator fun plus(value: Long): T = plus(value.toDouble())
    operator fun plus(value: Short): T = plus(value.toDouble())
    operator fun plus(value: Float): T = plus(value.toDouble())
    operator fun plus(value: Double): T
    operator fun minus(value: Int): T = minus(value.toDouble())
    operator fun minus(value: Long): T = minus(value.toDouble())
    operator fun minus(value: Short): T = minus(value.toDouble())
    operator fun minus(value: Float): T = minus(value.toDouble())
    operator fun minus(value: Double): T
    operator fun times(value: Int): T = times(value.toDouble())
    operator fun times(value: Long): T = times(value.toDouble())
    operator fun times(value: Short): T = times(value.toDouble())
    operator fun times(value: Float): T = times(value.toDouble())
    operator fun times(value: Double): T
}