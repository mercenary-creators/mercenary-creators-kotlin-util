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

package co.mercenary.creators.kotlin.util.io

import co.mercenary.creators.kotlin.util.*
import java.io.*

@IgnoreForSerialize
class NoCloseInputStream @FrameworkDsl constructor(data: InputStream) : FilterInputStream(data), OpenAutoClosable {

    @FrameworkDsl
    private val open = getAtomicTrue()

    @FrameworkDsl
    override fun close() {
        if (open.isTrueToFalse().isNotTrue()) {
            logsOfType<NoCloseInputStream>().warn { "closed()" }
        }
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isOpen() = open.isTrue()
}