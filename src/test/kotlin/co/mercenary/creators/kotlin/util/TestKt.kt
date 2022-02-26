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

@file:JvmName("TestKt")

package co.mercenary.creators.kotlin.util

@FrameworkDsl
const val HORZ_STRING = "Maël Hörz\n"

@FrameworkDsl
const val CODE_STRING = "\uD83E\uDD70"


typealias KotlinTest = co.mercenary.creators.kotlin.util.test.AbstractKotlinTest

typealias KotlinDataTest = co.mercenary.creators.kotlin.util.test.AbstractDataTest

typealias KotlinSecurityTest = co.mercenary.creators.kotlin.util.test.AbstractSecurityTest

