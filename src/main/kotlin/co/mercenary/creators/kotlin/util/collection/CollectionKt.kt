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

@file:kotlin.jvm.JvmName("CollectionKt")
@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST", "FunctionName")

package co.mercenary.creators.kotlin.util.collection

import co.mercenary.creators.kotlin.util.*
import java.util.*
import kotlin.collections.*

internal typealias Collections = java.util.Collections

internal typealias DeckArrayList<T> = ArrayDeque<T>

internal typealias LinkArrayList<T> = LinkedList<T>

internal typealias CopyArrayList<T> = java.util.concurrent.CopyOnWriteArrayList<T>

@FrameworkDsl
inline fun <T> Int.toArrayList(): ArrayList<T> {
    return ArrayList(toListCapacity())
}

@FrameworkDsl
inline fun <T> Collection<T>.toArrayList(): ArrayList<T> {
    return ArrayList(this)
}

@FrameworkDsl
inline fun <T> Array<T>.toArrayList(): ArrayList<T> {
    return toCollection().toArrayList()
}

@FrameworkDsl
inline fun <T> Iterable<T>.toArrayList(): ArrayList<T> {
    return toCollection().toArrayList()
}

@FrameworkDsl
inline fun <T> Iterator<T>.toArrayList(): ArrayList<T> {
    return toCollection().toArrayList()
}

@FrameworkDsl
inline fun <T> Sequence<T>.toArrayList(): ArrayList<T> {
    return toCollection().toArrayList()
}

@FrameworkDsl
inline fun <T> MutableList<T>.reset() {
    clear()
}

@FrameworkDsl
inline fun <T> ArrayList<T>.trim(): ArrayList<T> {
    trimToSize()
    return this
}

@FrameworkDsl
inline fun Sized.rangecheckget(index: Int): Int {
    if (index >= sizeOf()) {
        fail("illegal index get($index) for size(${sizeOf()})")
    }
    return index
}

@FrameworkDsl
inline fun Sized.rangecheckadd(index: Int): Int {
    if ((index < 0) || (index > sizeOf())) {
        fail("illegal index add($index) for size(${sizeOf()})")
    }
    return index
}

@FrameworkDsl
inline fun <T> ArrayList<T>.toArray(copy: Boolean): Array<T> {
    return (toArray() as Array<T>).toArray(copy.isTrue())
}

@FrameworkDsl
 fun <T> toArray(list: Collection<T>, copy: Boolean = false): Array<T> {
    return list.toArrayList().toArray(copy.isTrue())
}



