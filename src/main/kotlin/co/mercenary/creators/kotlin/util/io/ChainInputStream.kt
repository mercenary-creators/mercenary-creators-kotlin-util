/*
 * Copyright (c) 2023, Mercenary Creators Company. All rights reserved.
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

package co.mercenary.creators.kotlin.util.io

import co.mercenary.creators.kotlin.util.*
import java.io.InputStream

@IgnoreForSerialize
class ChainInputStream @FrameworkDsl constructor(args: List<InputStream>) : InputStream(), Exhausted, SizedContainer, OpenAutoClosable, HasMapNames {

    @FrameworkDsl
    constructor(args: Iterator<InputStream>) : this(args.toIterator().toList())

    @FrameworkDsl
    constructor(args: Iterable<InputStream>) : this(args.toIterator().toList())

    @FrameworkDsl
    constructor(args: Sequence<InputStream>) : this(args.toIterator().toList())

    @FrameworkDsl
    constructor(head: InputStream, next: InputStream, vararg more: InputStream) : this(toListOf(head, next, *more))

    @FrameworkDsl
    private val open = getAtomicTrue()

    @FrameworkDsl
    private val list = BasicArrayList(args)

    @FrameworkDsl
    private val many = args.sizeOf().copyOf()

    @FrameworkDsl
    private fun manyOf() = many.toListCapacity()

    @FrameworkDsl
    private fun headOf() = list[0]

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isOpen() = open.isTrue()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isExhausted(): Boolean {
        return isClosed() || isEmpty()
    }

    @FrameworkDsl
    override fun sizeOf() = list.sizeOf()

    init {
        if (sizeOf() < 2) {
            fail(MATH_INVALID_SIZE_ERROR)
        }
    }

    @FrameworkDsl
    override fun hashCode() = idenOf()

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to nameOf(), "open" to isOpen(), "size" to sizeOf(), "many" to manyOf())

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is ChainInputStream -> this === other
        else -> false
    }

    @FrameworkDsl
    private fun bump() {
        if (sizeOf() > 0) {
            use { list.pop() }
        }
    }

    @FrameworkDsl
    override fun read(): Int {
        while (sizeOf() > 0) {
            try {
                val read = headOf().read()
                if (read != IS_NOT_FOUND) {
                    return read
                }
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
            }
            bump()
        }
        return IS_NOT_FOUND
    }

    @FrameworkDsl
    override fun read(buff: ByteArray): Int {
        return read(buff, 0, buff.sizeOf())
    }

    @FrameworkDsl
    override fun read(buff: ByteArray, off: Int, len: Int): Int {
        if (isEmpty()) {
            return IS_NOT_FOUND
        }
        if (off < 0 || len < 0 || len > (buff.sizeOf() - off)) {
            fail(MATH_INVALID_SIZE_ERROR)
        }
        if (len == 0) {
            return 0
        }
        do {
            try {
                val read = headOf().read(buff, off, len)
                if (read > 0) {
                    return read
                }
            } catch (cause: Throwable) {
                Throwables.thrown(cause)
            }
            bump()
        } while (sizeOf() > 0)
        return IS_NOT_FOUND
    }

    @FrameworkDsl
    override fun close() {
        if (open.isTrueToFalse()) {
            do {
                try {
                    bump()
                } catch (cause: Throwable) {
                    Throwables.thrown(cause)
                }
            } while (sizeOf() > 0)
            list.reset()
        } else {
            logsOfType<ChainInputStream>().warn { "closed()" }
        }
    }

    @FrameworkDsl
    override fun mark(read: Int) {
        fail("${nameOf()}.mark() not supported")
    }

    @FrameworkDsl
    override fun reset() {
        fail("${nameOf()}.reset() not supported")
    }

    @FrameworkDsl
    override fun available(): Int {
        return if (isEmpty()) 0 else headOf().getAvailableSize()
    }

    @IgnoreForSerialize
    class ChainInputStreamBuilder @FrameworkDsl internal constructor(args: Iterable<InputStream>) : MutableSizedContainer, Builder<ChainInputStream>, HasMapNames {

        @FrameworkDsl
        private val list = BasicArrayList(args)

        @FrameworkDsl
        operator fun contains(element: InputStream): Boolean {
            if (isEmpty()) {
                return false
            }
            list.forEach { input ->
                if (input === element) {
                    return true
                }
            }
            return list.contains(element)
        }

        @FrameworkDsl
        fun append(element: InputStream, vararg args: InputStream): ChainInputStreamBuilder {
            list.add(element)
            if (args.isNotExhausted()) {
                args.forEach { input ->
                    list.add(input)
                }
            }
            return this
        }

        @FrameworkDsl
        fun append(element: InputStreamSupplier, vararg args: InputStreamSupplier): ChainInputStreamBuilder {
            list.add(element.toInputStream())
            if (args.isNotExhausted()) {
                args.forEach { input ->
                    list.add(input.toInputStream())
                }
            }
            return this
        }

        @FrameworkDsl
        fun append(args: Iterable<InputStream>): ChainInputStreamBuilder {
            if (args.isNotExhausted()) {
                args.forEach { input ->
                    list.add(input)
                }
            }
            return this
        }

        @FrameworkDsl
        fun append(args: Iterator<InputStream>): ChainInputStreamBuilder {
            if (args.isNotExhausted()) {
                args.forEach { input ->
                    list.add(input)
                }
            }
            return this
        }

        @FrameworkDsl
        fun append(args: Sequence<InputStream>): ChainInputStreamBuilder {
            if (args.isNotExhausted()) {
                args.forEach { input ->
                    list.add(input)
                }
            }
            return this
        }

        @FrameworkDsl
        override fun build(): ChainInputStream {
            if (sizeOf() < 2) {
                fail(MATH_INVALID_SIZE_ERROR)
            }
            return ChainInputStream(list)
        }

        @FrameworkDsl
        @IgnoreForSerialize
        override fun isEmpty(): Boolean {
            return sizeOf() == 0
        }

        @FrameworkDsl
        override fun clear() {
            list.clear()
        }

        @FrameworkDsl
        override fun reset() {
            list.reset()
        }

        @FrameworkDsl
        override fun sizeOf(): Int {
            return list.sizeOf()
        }

        @FrameworkDsl
        override fun hashCode() = idenOf()

        @FrameworkDsl
        override fun toString() = toMapNames().toSafeString()

        @FrameworkDsl
        override fun equals(other: Any?) = when (other) {
            is ChainInputStreamBuilder -> this === other
            else -> false
        }

        @FrameworkDsl
        override fun toMapNames() = dictOf("type" to nameOf(), "size" to sizeOf())
    }

    companion object {

        @JvmStatic
        @JvmOverloads
        @FrameworkDsl
        fun builder(args: Iterable<InputStream> = toListOf()) = ChainInputStreamBuilder(args)

        @JvmStatic
        @FrameworkDsl
        fun builder(args: Iterator<InputStream>) = builder(args.toIterable())

        @JvmStatic
        @FrameworkDsl
        fun builder(args: Sequence<InputStream>) = builder(args.toIterable())

        @JvmStatic
        @FrameworkDsl
        fun builder(head: InputStream, next: InputStream, vararg more: InputStream) = builder(toListOf(head, next, *more))
    }
}