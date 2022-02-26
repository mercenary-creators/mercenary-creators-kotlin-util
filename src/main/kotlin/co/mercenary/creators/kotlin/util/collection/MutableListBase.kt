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

@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST", "FunctionName")

package co.mercenary.creators.kotlin.util.collection

import co.mercenary.creators.kotlin.util.*

interface MutableListBase<T, B : MutableListBase<T, B>> : MutableBase<B>, MutableList<T>, RandomAccess {

    @FrameworkDsl
    fun trim()

    @FrameworkDsl
    @IgnoreForSerialize
    fun headOf(): T

    @FrameworkDsl
    @IgnoreForSerialize
    fun isArrayList(): Boolean

    @FrameworkDsl
    fun pop(): T

    @FrameworkDsl
    fun cut(): T

    @FrameworkDsl
    fun add(args: Array<T>): Boolean {
        if (args.isExhausted()) {
            return false
        }
        return add(args.toCollection())
    }

    @FrameworkDsl
    fun add(args: Iterator<T>): Boolean {
        if (args.isExhausted()) {
            return false
        }
        return add(args.toCollection())
    }

    @FrameworkDsl
    fun add(args: Iterable<T>): Boolean {
        if (args.isExhausted()) {
            return false
        }
        return add(args.toCollection())
    }

    @FrameworkDsl
    fun add(args: Collection<T>): Boolean {
        if (args.isExhausted()) {
            return false
        }
        return try {
            addAll(args)
        } catch (cause: Throwable) {
            Throwables.fatal(cause, false)
        }
    }

    @FrameworkDsl
    fun add(args: Sequence<T>): Boolean {
        if (args.isExhausted()) {
            return false
        }
        return add(args.toCollection())
    }

    @FrameworkDsl
    fun add(index: Int, args: Array<T>): Boolean {
        if (args.isExhausted()) {
            return false
        }
        rangecheckadd(index)
        return add(index, args.toCollection())
    }

    @FrameworkDsl
    fun add(index: Int, args: Iterator<T>): Boolean {
        if (args.isExhausted()) {
            return false
        }
        rangecheckadd(index)
        return add(index, args.toCollection())
    }

    @FrameworkDsl
    fun add(index: Int, args: Iterable<T>): Boolean {
        if (args.isExhausted()) {
            return false
        }
        rangecheckadd(index)
        return add(index, args.toCollection())
    }

    @FrameworkDsl
    fun add(index: Int, args: Sequence<T>): Boolean {
        if (args.isExhausted()) {
            return false
        }
        rangecheckadd(index)
        return add(index, args.toCollection())
    }

    @FrameworkDsl
    fun add(index: Int, args: Collection<T>): Boolean {
        if (args.isExhausted()) {
            return false
        }
        rangecheckadd(index)
        return try {
            addAll(index, args)
        } catch (cause: Throwable) {
            Throwables.fatal(cause, false)
        }
    }

    @FrameworkDsl
    fun toReadOnly(): List<T> = toList()

    @FrameworkDsl
    fun toSequence(): Sequence<T> = asSequence()
}