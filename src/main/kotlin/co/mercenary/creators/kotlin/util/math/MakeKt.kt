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

@file:kotlin.jvm.JvmName("MakeKt")
@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package co.mercenary.creators.kotlin.util.math

import co.mercenary.creators.kotlin.util.*

@FrameworkDsl
val Sliced<*>.lastIndex: Int
    get() = sizeOf() - 1

@FrameworkDsl
val Sliced<*>.indices: IntRange
    get() = IntRange(0, lastIndex)

@FrameworkDsl
inline fun Int.toVector(convert: Convert<Int, Double>): Vector = Vector(this.toDoubleArray(convert), false)

@FrameworkDsl
fun Vector.sort(descending: Boolean = false): Vector {
    if (data.sizeOf() > 1) {
        if (isNotSlice()) {
            data.sort()
            if (descending.isTrue()) {
                data.reverse()
            }
        }
    }
    return this
}

@FrameworkDsl
inline fun Vector.forEach(action: Convert<Double, Unit>) {
    indices.forEach { index ->
        action.convert(get(index))
    }
}

@FrameworkDsl
inline fun Vector.forEachIndexed(action: Indexed<Double, Unit>) {
    indices.forEach { index ->
        action.indexed(index, get(index))
    }
}

