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

package co.mercenary.creators.kotlin.util.collection

import co.mercenary.creators.kotlin.util.*

interface MutableDoubleEndedQueue<E> : MutableSingleEndedQueue<E> {

    @FrameworkDsl
    fun addFirst(element: E)

    @FrameworkDsl
    fun addLast(element: E)

    @FrameworkDsl
    fun offerFirst(element: E): Boolean

    @FrameworkDsl
    fun offerLast(element: E): Boolean

    @FrameworkDsl
    fun removeFirst(): E

    @FrameworkDsl
    fun removeLast(): E

    @FrameworkDsl
    fun removeFirstOccurrence(element: E): Boolean

    @FrameworkDsl
    fun removeLastOccurrence(element: E): Boolean

    @FrameworkDsl
    fun pollFirst(): E

    @FrameworkDsl
    fun pollLast(): E

    @FrameworkDsl
    @IgnoreForSerialize
    fun getFirst(): E

    @FrameworkDsl
    @IgnoreForSerialize
    fun getLast(): E

    @FrameworkDsl
    fun peekFirst(): E

    @FrameworkDsl
    fun peekLast(): E

    @FrameworkDsl
    fun push(element: E)

    @FrameworkDsl
    fun pop(): E

    @FrameworkDsl
    fun descendingIterator(): MutableIterator<E>
}