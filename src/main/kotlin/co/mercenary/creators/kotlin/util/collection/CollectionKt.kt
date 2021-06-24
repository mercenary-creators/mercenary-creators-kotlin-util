/*
 * Copyright (c) 2021, Mercenary Creators Company. All rights reserved.
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

internal typealias Collections = java.util.Collections

internal typealias LinkCachedMap<T> = LRUCacheMap<T, Maybe>

internal typealias DictMaybeMap = BasicDictionaryMap<Maybe>

internal typealias LinkedDeque<T> = java.util.concurrent.ConcurrentLinkedDeque<T>

@FrameworkDsl
fun <T> Collection<T>.toLinkedDeque(): LinkedDeque<T> {
    return LinkedDeque(this)
}

@FrameworkDsl
inline fun <T : Any> Array<T>.toBasicLinkedSet(): BasicLinkedSet<T> {
    return BasicLinkedSet(toIterator())
}

@FrameworkDsl
inline fun <T> Collection<T>.toBasicLinkedSet(): BasicLinkedSet<T> {
    return BasicLinkedSet(toIterator())
}

@FrameworkDsl
inline fun <T> Iterable<T>.toBasicLinkedSet(): BasicLinkedSet<T> {
    return BasicLinkedSet(toIterator())
}

@FrameworkDsl
inline fun <T> Iterator<T>.toBasicLinkedSet(): BasicLinkedSet<T> {
    return BasicLinkedSet(toIterator())
}

@FrameworkDsl
inline fun <T> Sequence<T>.toBasicLinkedSet(): BasicLinkedSet<T> {
    return BasicLinkedSet(toIterator())
}

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
    return ArrayList(toList())
}

@FrameworkDsl
inline fun <T> Iterable<T>.toArrayList(): ArrayList<T> {
    return toIterator().toArrayList()
}

@FrameworkDsl
inline fun <T> Iterator<T>.toArrayList(): ArrayList<T> {
    val self = this
    return ArrayList<T>().also { list ->
        self.forEach { list.add(it) }
    }
}

@FrameworkDsl
inline fun <T> Sequence<T>.toArrayList(): ArrayList<T> {
    return toIterator().toArrayList()
}

@FrameworkDsl
inline fun <T> ArrayList<T>.reset() {
    locked(this) {
        clear()
        trimToSize()
    }
}

@FrameworkDsl
fun isJustArrayList(list: MutableList<*>): Boolean = list is ArrayList

@FrameworkDsl
fun isCopyArrayList(list: MutableList<*>): Boolean = list is CopyArrayList

@FrameworkDsl
fun isDeckArrayList(list: MutableList<*>): Boolean = list is DeckArrayList

@FrameworkDsl
fun isLinkArrayList(list: MutableList<*>): Boolean = list is LinkArrayList

@FrameworkDsl
inline fun <T> ArrayList<T>.trim(): ArrayList<T> {
    locked(this) {
        trimToSize()
    }
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
inline fun Sized.isOverLimit(limit: Int): Boolean {
    if (limit.isNegative()) {
        return false
    }
    return sizeOf() >= 0 && sizeOf() > limit
}

@FrameworkDsl
inline fun <T> ArrayList<T>.toArray(copy: Boolean): Array<T> {
    return (toArray() as Array<T>).toArray(copy.isTrue())
}

@FrameworkDsl
inline fun <T> List<T>.toArray(copy: Boolean): Array<T> {
    return toArrayList().toArray(copy.isTrue())
}



