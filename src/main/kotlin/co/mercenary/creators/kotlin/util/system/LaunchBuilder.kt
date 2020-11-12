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

package co.mercenary.creators.kotlin.util.system

import co.mercenary.creators.kotlin.util.*
import java.io.File
import java.nio.file.Path

@IgnoreForSerialize
class LaunchBuilder @FrameworkDsl constructor(list: List<String>) : Builder<LaunchRunner> {

    @FrameworkDsl
    constructor(args: String) : this(toListOf(args))

    @FrameworkDsl
    constructor(vararg args: String) : this(toListOf(args.toIterable()))

    private val make = ProcessBuilder(list)

    @FrameworkDsl
    fun home(home: File): LaunchBuilder {
        make.directory(home)
        return this
    }

    @FrameworkDsl
    fun home(home: Path): LaunchBuilder {
        return home(home.toFile())
    }

    @FrameworkDsl
    override fun build(): LaunchRunner {
        TODO("Not yet implemented")
    }
}