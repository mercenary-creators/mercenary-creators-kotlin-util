/*
 * Copyright (c) 2019, Mercenary Creators Company. All rights reserved.
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

package co.mercenary.creators.kotlin.util.meta

import kotlin.math.abs

abstract class AbstractMutableMetaData<T : Any> : LinkedHashMap<String, T>, MutableMetaDataType<T> {
    protected constructor() : super()
    protected constructor(size: Int) : super(size)
    protected constructor(args: Map<String, T>) : super(make(args))
    protected constructor(args: MetaDataSupplier) : super(make(args))
    protected constructor(args: Array<Pair<String, T>>) : super(make(args))
    protected constructor(args: Iterable<Pair<String, T>>) : super(make(args))
    protected constructor(args: Sequence<Pair<String, T>>) : super(make(args))

    private var spaces: String = " ".repeat(4)

    fun getPadding(): String = spaces

    fun setSpaces(many: Int) {
        spaces = " ".repeat(abs(many))
    }

    protected companion object {

        fun <T : Any> make(args: Map<String, T>): Map<String, T> {
            return if (args.isEmpty()) emptyMap()
            else LinkedHashMap<String, T>(args.size).also {
                for ((k, v) in args) it[k] = v
            }
        }

        fun <T : Any> make(args: Array<Pair<String, T>>): Map<String, T> {
            return if (args.isEmpty()) emptyMap()
            else LinkedHashMap<String, T>(args.size).also {
                for ((k, v) in args) it[k] = v
            }
        }

        fun <T : Any> make(args: Iterable<Pair<String, T>>): Map<String, T> {
            return LinkedHashMap<String, T>().also {
                for ((k, v) in args) it[k] = v
            }
        }

        fun <T : Any> make(args: Sequence<Pair<String, T>>): Map<String, T> {
            return LinkedHashMap<String, T>().also {
                for ((k, v) in args) it[k] = v
            }
        }

        fun <T : Any> make(args: MetaDataSupplier): Map<String, T> {
            return make(args.getMetaData())
        }

        fun <T : Any> hash(args: Map<String, T>): Int {
            var hash = 31
            args.onEach {
                val self = abs(it.hashCode())
                if (hash > Int.MAX_VALUE - self) {
                    hash = 31
                }
                hash += self
            }
            return hash
        }

        fun <T : Any> same(self: Map<String, T>, args: Map<String, T>): Boolean {
            if (self.size != args.size) {
                return false
            }
            for ((k, v) in self) {
                if (args.contains(k)) {
                    if (args[k] != v) {
                        return false
                    }
                }
                else return false
            }
            for ((k, v) in args) {
                if (self.contains(k)) {
                    if (self[k] != v) {
                        return false
                    }
                }
                else return false
            }
            return true
        }
    }
}