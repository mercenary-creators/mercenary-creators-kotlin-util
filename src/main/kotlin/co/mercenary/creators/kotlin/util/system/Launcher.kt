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

package co.mercenary.creators.kotlin.util.system

import co.mercenary.creators.kotlin.util.*

@FrameworkDsl
@IgnoreForSerialize
object Launcher : HasMapNames {

    @FrameworkDsl
    private val many = 0L.toAtomic()

    @JvmStatic
    @FrameworkDsl
    @Synchronized
    internal fun bump() {
        if (many.isNegative() || many.getValue() > Long.MAX_VALUE - 2L) {
            many.setValue(0L)
        }
        many.increment()
    }

    @JvmStatic
    @FrameworkDsl
    fun count(): Long {
        return many.getValue()
    }

    @JvmStatic
    @FrameworkDsl
    fun builder(prog: String, vararg args: String): LaunchBuilder {
        return builder(prog, args.toIterator())
    }

    @JvmStatic
    @FrameworkDsl
    fun builder(prog: String, args: Iterable<String>): LaunchBuilder {
        return builder(prog, args.toIterator())
    }

    @JvmStatic
    @FrameworkDsl
    fun builder(prog: String, args: Sequence<String>): LaunchBuilder {
        return builder(prog, args.toIterator())
    }

    @JvmStatic
    @FrameworkDsl
    fun builder(prog: String, args: Iterator<String>): LaunchBuilder {
        return LaunchBuilder(prog.toChecked()).arguments(args).also { bump() }
    }

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to nameOf(), "unix" to isSystemUnixLike(), "many" to count())
}