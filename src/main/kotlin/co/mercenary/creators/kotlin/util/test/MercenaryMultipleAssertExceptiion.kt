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

package co.mercenary.creators.kotlin.util.test

import co.mercenary.creators.kotlin.util.*

@IgnoreForSerialize
class MercenaryMultipleAssertExceptiion @CreatorsDsl constructor(private val args: Iterable<Throwable>, message: String, cause: Throwable?) : AssertionError(message, cause) {

    @CreatorsDsl
    constructor(vararg list: Throwable) : this(list.toList())

    @CreatorsDsl
    constructor(list: Iterable<Throwable>) : this(list, EMPTY_STRING)

    @CreatorsDsl
    constructor(list: Iterable<Throwable>, message: String) : this(list, message, null)

    @CreatorsDsl
    constructor(list: Iterable<Throwable>, cause: Throwable?) : this(list, EMPTY_STRING, cause)

    @CreatorsDsl
    constructor(list: Sequence<Throwable>) : this(list.toList())

    @CreatorsDsl
    constructor(list: Sequence<Throwable>, message: String) : this(list.toList(), message)

    @CreatorsDsl
    constructor(list: Sequence<Throwable>, cause: Throwable?) : this(list.toList(), cause)

    @CreatorsDsl
    constructor(list: Sequence<Throwable>, message: String, cause: Throwable?) : this(list.toList(), message, cause)

    override val cause: Throwable?
        @IgnoreForSerialize
        get() = super.cause

    override val message: String
        @IgnoreForSerialize
        get() = make(args, super.message.toTrimOr("multiple failures"))

    @CreatorsDsl
    fun suppress(cause: Throwable): MercenaryMultipleAssertExceptiion {
        addSuppressed(cause)
        return this
    }

    @CreatorsDsl
    fun suppress(args: Iterable<Throwable>): MercenaryMultipleAssertExceptiion {
        args.forEach { addSuppressed(it) }
        return this
    }

    @CreatorsDsl
    fun suppress(): MercenaryMultipleAssertExceptiion {
        return suppress(args)
    }

    companion object {

        private const val serialVersionUID = 2L

        @JvmStatic
        @CreatorsDsl
        private fun feed(): String = BREAK_STRING

        @JvmStatic
        @CreatorsDsl
        private fun make(args: Iterable<Throwable>, mess: String): String {
            val list = args.toList()
            return when (val size = list.size) {
                0 -> mess
                else -> stringOf {
                    append(mess).append(SPACE_STRING).append("(").append(size).append(SPACE_STRING)
                    append(if (size == 1) "failure" else "failures")
                    append(")")
                    append(feed())
                    val last = size - 1
                    for (oops in list.subList(0, last)) {
                        add(SPACE_STRING, make(oops), feed())
                    }
                    add('\t', make(list[last]))
                }
            }
        }

        @CreatorsDsl
        private fun make(oops: Throwable) = "${oops.nameOf()}: ${oops.message.toTrimOr("<no message>")}"
    }
}