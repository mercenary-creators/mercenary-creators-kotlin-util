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

class BasicArrayDeck<E> @FrameworkDsl constructor(args: Collection<E>) : MutableDoubleEndedQueue<E>, Copyable<BasicArrayDeck<E>>, Cloneable {

    @FrameworkDsl
    constructor() : this(toListOf<E>())

    @FrameworkDsl
    constructor(vararg args: E) : this(args.toCollection())

    @FrameworkDsl
    constructor(args: Iterator<E>) : this(args.toCollection())

    @FrameworkDsl
    constructor(args: Iterable<E>) : this(args.toCollection())

    @FrameworkDsl
    constructor(args: Sequence<E>) : this(args.toCollection())

    @FrameworkDsl
    private val list = args.toLinkedDeque()

    @FrameworkDsl
    override val size: Int
        @IgnoreForSerialize
        get() = sizeOf()

    @FrameworkDsl
    override operator fun contains(element: E): Boolean {
        return list.contains(element)
    }

    @FrameworkDsl
    override fun containsAll(elements: Collection<E>): Boolean {
        return list.containsAll(elements)
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isEmpty(): Boolean {
        return sizeOf() == 0
    }

    @FrameworkDsl
    override fun sizeOf(): Int {
        return list.sizeOf()
    }

    @FrameworkDsl
    override fun add(element: E): Boolean {
        return list.add(element)
    }

    @FrameworkDsl
    override fun addAll(elements: Collection<E>): Boolean {
        return list.addAll(elements)
    }

    @FrameworkDsl
    override fun clear() {
        list.clear()
    }

    @FrameworkDsl
    override fun remove(element: E): Boolean {
        return list.remove(element)
    }

    @FrameworkDsl
    override fun removeAll(elements: Collection<E>): Boolean {
        return list.removeAll(elements)
    }

    @FrameworkDsl
    override fun retainAll(elements: Collection<E>): Boolean {
        return list.retainAll(elements)
    }

    @FrameworkDsl
    override fun addFirst(element: E) {
        list.addFirst(element)
    }

    @FrameworkDsl
    override fun addLast(element: E) {
        list.addLast(element)
    }

    @FrameworkDsl
    override fun offerFirst(element: E): Boolean {
        return list.offerFirst(element)
    }

    @FrameworkDsl
    override fun offerLast(element: E): Boolean {
        return list.offerLast(element)
    }

    @FrameworkDsl
    override fun removeFirst(): E {
        return list.removeFirst()
    }

    @FrameworkDsl
    override fun removeLast(): E {
        return list.removeLast()
    }

    @FrameworkDsl
    override fun removeFirstOccurrence(element: E): Boolean {
        return list.removeFirstOccurrence(element)
    }

    @FrameworkDsl
    override fun removeLastOccurrence(element: E): Boolean {
        return list.removeLastOccurrence(element)
    }

    @FrameworkDsl
    override fun pollFirst(): E {
        return list.pollFirst()
    }

    @FrameworkDsl
    override fun pollLast(): E {
        return list.pollLast()
    }

    @FrameworkDsl
    override fun getFirst(): E {
        return list.first
    }

    @FrameworkDsl
    override fun getLast(): E {
        return list.last
    }

    @FrameworkDsl
    override fun peekFirst(): E {
        return list.peekFirst()
    }

    @FrameworkDsl
    override fun peekLast(): E {
        return list.peekFirst()
    }

    @FrameworkDsl
    override fun push(element: E) {
        list.push(element)
    }

    @FrameworkDsl
    override fun pop(): E {
        return list.pop()
    }

    @FrameworkDsl
    override fun descendingIterator(): MutableIterator<E> {
        return list.descendingIterator()
    }

    @FrameworkDsl
    override fun poll(): E {
        return list.poll()
    }

    @FrameworkDsl
    override fun peek(): E {
        return list.peek()
    }

    @FrameworkDsl
    override fun remove(): E {
        return list.remove()
    }

    @FrameworkDsl
    override fun element(): E {
        return list.element()
    }

    @FrameworkDsl
    override fun offer(element: E): Boolean {
        return list.offer(element)
    }

    @FrameworkDsl
    override operator fun iterator(): MutableIterator<E> {
        return list.iterator()
    }

    @FrameworkDsl
    override fun clone() = copyOf()

    @FrameworkDsl
    override fun copyOf() = BasicArrayDeck(this)

    @FrameworkDsl
    override fun hashCode() = hashOf()

    @FrameworkDsl
    override fun toString() = toReadOnly().toSafeString()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is BasicArrayDeck<*> -> this === other || sizeOf() == other.sizeOf() && this isSameAs other
        else -> false
    }

    companion object {

        private const val serialVersionUID = 6L
    }
}