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

class MercenaryMultipleAssertExceptiion @JvmOverloads constructor(private val list: List<Throwable>, text: String = EMPTY_STRING) : AssertionError(text) {

    override val message: String
        get() = make()

    private fun make(): String {
        val head = toTrimOrElse(super.message, "multiple failures")
        return when (val size = list.size) {
            0 -> head
            else -> buildString {
                append(head)
                append(SPACE_STRING)
                append("(")
                append(size)
                append(SPACE_STRING)
                append(if (size == 1) "failure" else "failures")
                append(")")
                append(EOL)
                val last = size - 1
                for (oops in list.subList(0, last)) {
                    append(SPACE_STRING)
                    append(make(oops))
                    append(EOL)
                }
                append('\t')
                append(make(list[last]))
            }
        }
    }

    private fun make(oops: Throwable): String {
        return if (toTrimOrElse(oops.message).isEmpty()) {
            "${oops.javaClass.name}: <no message>"
        }
        else "${oops.javaClass.name}: ${oops.message}"
    }

    companion object {
        private const val serialVersionUID = 2L
        private val EOL = System.getProperty("line.separator")
    }
}