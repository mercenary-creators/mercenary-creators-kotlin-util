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

package co.mercenary.creators.kotlin.util.test

import co.mercenary.creators.kotlin.util.*
import java.io.*

@IgnoreForSerialize
abstract class AbstractDataTest : AbstractKotlinTest() {

    @FrameworkDsl
    fun file(name: String) = name.fileOf()

    @FrameworkDsl
    fun look(file: File) {
        dashes()
        warn { file.getPathName() }
        warn { file.isContentThere() }
        warn { file.isValidToRead() }
        warn { file.isValidToWrite() }
        warn { file.getContentSize() }
        val (d, f) = dateOf() to file.getContentDate()
        warn { f }
        warn { d }
        dashes()
    }

    @FrameworkDsl
    fun File.data(data: ByteArray): ByteArray {
        return toDataInput().readByteArray(data)
    }
}