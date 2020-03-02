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

import co.mercenary.creators.kotlin.util.HasMapNames
import co.mercenary.creators.kotlin.util.type.Copyable

interface BaseNumber<T : BaseNumber<T>> : Copyable<T>, Cloneable, HasMapNames {
    operator fun unaryPlus(): T
    operator fun unaryMinus(): T
    operator fun div(value: T): T
    operator fun plus(value: T): T
    operator fun minus(value: T): T
    operator fun times(value: T): T
}