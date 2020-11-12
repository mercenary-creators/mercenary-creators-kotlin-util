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

@file:kotlin.jvm.JvmName("TestKt")
@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package co.mercenary.creators.kotlin.util

@CreatorsDsl
internal const val SHOULD_BE_SAME_ARRAY_FAILED = "shouldBeSameArray failed"

@CreatorsDsl
internal const val SHOULD_NOT_BE_SAME_ARRAY_FAILED = "shouldNotBeSameArray failed"

@CreatorsDsl
inline fun LazyMessage.toSafeString(): String = Formatters.toSafeString(this)

@CreatorsDsl
inline fun fail(text: String): Nothing {
    throw MercenaryAssertionExceptiion(text)
}

@CreatorsDsl
inline fun fail(func: LazyMessage): Nothing {
    fail(func.toSafeString())
}

@CreatorsDsl
inline fun shouldBeTrue(value: Boolean, text: String) {
    if (value.isNotTrue()) {
        fail(text)
    }
}

@CreatorsDsl
inline fun shouldBeTrue(value: Boolean, func: LazyMessage) {
    if (value.isNotTrue()) {
        fail(func)
    }
}

@CreatorsDsl
inline fun shouldNotBeTrue(value: Boolean, text: String) {
    if (value.isTrue()) {
        fail(text)
    }
}

@CreatorsDsl
inline fun shouldNotBeTrue(value: Boolean, func: LazyMessage) {
    if (value.isTrue()) {
        fail(func)
    }
}

@CreatorsDsl
inline fun shouldBeValid(value: Validated, text: String) {
    if (value.isNotValid()) {
        fail(text)
    }
}

@CreatorsDsl
inline fun shouldBeValid(value: Validated, func: LazyMessage) {
    if (value.isNotValid()) {
        fail(func)
    }
}

@CreatorsDsl
inline fun shouldNotBeValid(value: Validated, text: String) {
    if (value.isValid()) {
        fail(text)
    }
}

@CreatorsDsl
inline fun shouldNotBeValid(value: Validated, func: LazyMessage) {
    if (value.isValid()) {
        fail(func)
    }
}

@CreatorsDsl
infix fun <T : Any?> T.isSameAs(value: T) = SameAndHashCode.isSameAs(this, value)

@CreatorsDsl
infix fun <T : Any?> T.isNotSameAs(value: T) = SameAndHashCode.isNotSameAs(this, value)

@CreatorsDsl
infix fun <T : Any?> T.isContentSameAs(value: T) = SameAndHashCode.isContentSameAs(this, value)

@CreatorsDsl
infix fun <T : Any?> T.isContentNotSameAs(value: T) = SameAndHashCode.isContentNotSameAs(this, value)

@CreatorsDsl
infix fun <T : Any?> T.shouldBe(value: T) = shouldBeTrue(value isSameAs this, "shouldBe failed")

@CreatorsDsl
infix fun <T : Any?> T.shouldNotBe(value: T) = shouldBeTrue(value isNotSameAs this, "shouldNotBe failed")

@CreatorsDsl
infix fun <T : Any?> T.shouldBeSameContent(value: T) = shouldBeTrue(value isContentSameAs this, "shouldBeSameContent failed")

@CreatorsDsl
infix fun <T : Any?> T.shouldNotBeSameContent(value: T) = shouldBeTrue(value isContentNotSameAs this, "shouldNotBeSameContent failed")

@CreatorsDsl
infix fun Array<*>.shouldBeSameArray(value: Array<*>) = shouldBeTrue(value isSameArrayAs this, SHOULD_BE_SAME_ARRAY_FAILED)

@CreatorsDsl
infix fun IntArray.shouldBeSameArray(value: IntArray) = shouldBeTrue(value isSameArrayAs this, SHOULD_BE_SAME_ARRAY_FAILED)

@CreatorsDsl
infix fun ByteArray.shouldBeSameArray(value: ByteArray) = shouldBeTrue(value isSameArrayAs this, SHOULD_BE_SAME_ARRAY_FAILED)

@CreatorsDsl
infix fun CharArray.shouldBeSameArray(value: CharArray) = shouldBeTrue(value isSameArrayAs this, SHOULD_BE_SAME_ARRAY_FAILED)

@CreatorsDsl
infix fun LongArray.shouldBeSameArray(value: LongArray) = shouldBeTrue(value isSameArrayAs this, SHOULD_BE_SAME_ARRAY_FAILED)

@CreatorsDsl
infix fun ShortArray.shouldBeSameArray(value: ShortArray) = shouldBeTrue(value isSameArrayAs this, SHOULD_BE_SAME_ARRAY_FAILED)

@CreatorsDsl
infix fun FloatArray.shouldBeSameArray(value: FloatArray) = shouldBeTrue(value isSameArrayAs this, SHOULD_BE_SAME_ARRAY_FAILED)

@CreatorsDsl
infix fun DoubleArray.shouldBeSameArray(value: DoubleArray) = shouldBeTrue(value isSameArrayAs this, SHOULD_BE_SAME_ARRAY_FAILED)

@CreatorsDsl
infix fun BooleanArray.shouldBeSameArray(value: BooleanArray) = shouldBeTrue(value isSameArrayAs this, SHOULD_BE_SAME_ARRAY_FAILED)

@CreatorsDsl
infix fun Array<*>.shouldNotBeSameArray(value: Array<*>) = shouldBeTrue(value isNotSameArrayAs this, SHOULD_NOT_BE_SAME_ARRAY_FAILED)

@CreatorsDsl
infix fun IntArray.shouldNotBeSameArray(value: IntArray) = shouldBeTrue(value isNotSameArrayAs this, SHOULD_NOT_BE_SAME_ARRAY_FAILED)

@CreatorsDsl
infix fun ByteArray.shouldNotBeSameArray(value: ByteArray) = shouldBeTrue(value isNotSameArrayAs this, SHOULD_NOT_BE_SAME_ARRAY_FAILED)

@CreatorsDsl
infix fun CharArray.shouldNotBeSameArray(value: CharArray) = shouldBeTrue(value isNotSameArrayAs this, SHOULD_NOT_BE_SAME_ARRAY_FAILED)

@CreatorsDsl
infix fun LongArray.shouldNotBeSameArray(value: LongArray) = shouldBeTrue(value isNotSameArrayAs this, SHOULD_NOT_BE_SAME_ARRAY_FAILED)

@CreatorsDsl
infix fun ShortArray.shouldNotBeSameArray(value: ShortArray) = shouldBeTrue(value isNotSameArrayAs this, SHOULD_NOT_BE_SAME_ARRAY_FAILED)

@CreatorsDsl
infix fun FloatArray.shouldNotBeSameArray(value: FloatArray) = shouldBeTrue(value isNotSameArrayAs this, SHOULD_NOT_BE_SAME_ARRAY_FAILED)

@CreatorsDsl
infix fun DoubleArray.shouldNotBeSameArray(value: DoubleArray) = shouldBeTrue(value isNotSameArrayAs this, SHOULD_NOT_BE_SAME_ARRAY_FAILED)

@CreatorsDsl
infix fun BooleanArray.shouldNotBeSameArray(value: BooleanArray) = shouldBeTrue(value isNotSameArrayAs this, SHOULD_NOT_BE_SAME_ARRAY_FAILED)

@IgnoreForSerialize
class MercenaryMultipleAssertionExceptiion @JvmOverloads @CreatorsDsl constructor(args: Iterable<Throwable>, message: String = EMPTY_STRING, cause: Throwable? = null) : AssertionError(message, cause), MutableSizedContainer {

    @CreatorsDsl
    @JvmOverloads
    constructor(args: Iterator<Throwable>, message: String = EMPTY_STRING, cause: Throwable? = null) : this(args.toIterable(), message, cause)

    @CreatorsDsl
    @JvmOverloads
    constructor(args: Sequence<Throwable>, message: String = EMPTY_STRING, cause: Throwable? = null) : this(args.toIterable(), message, cause)

    @CreatorsDsl
    private val list = ArrayList<Throwable>(args.toList())

    @CreatorsDsl
    override val size: Int
        @IgnoreForSerialize
        get() = list.size

    override val cause: Throwable?
        @IgnoreForSerialize
        get() = super.cause

    override val message: String
        @IgnoreForSerialize
        get() = format(text(), list, size)

    @CreatorsDsl
    private fun text(): String {
        return super.message.toTrimOr("multiple failures")
    }

    @CreatorsDsl
    override fun clear() {
        list.clear()
    }

    @CreatorsDsl
    fun append(cause: Throwable, vararg args: Throwable): MercenaryMultipleAssertionExceptiion {
        list += cause
        args.forEach {
            list += it
        }
        return this
    }

    @CreatorsDsl
    fun append(args: Iterator<Throwable>): MercenaryMultipleAssertionExceptiion {
        args.forEach {
            list += it
        }
        return this
    }

    @CreatorsDsl
    fun append(args: Iterable<Throwable>): MercenaryMultipleAssertionExceptiion {
        args.forEach {
            list += it
        }
        return this
    }

    @CreatorsDsl
    fun append(args: Sequence<Throwable>): MercenaryMultipleAssertionExceptiion {
        args.forEach {
            list += it
        }
        return this
    }

    @CreatorsDsl
    fun suppress(): MercenaryMultipleAssertionExceptiion {
        list.forEach { suppress(it) }
        return this
    }

    @CreatorsDsl
    fun suppress(cause: Throwable): MercenaryMultipleAssertionExceptiion {
        addSuppressed(cause)
        return this
    }

    @CreatorsDsl
    fun suppress(vararg args: Throwable): MercenaryMultipleAssertionExceptiion {
        args.forEach { suppress(it) }
        return this
    }

    @CreatorsDsl
    fun suppress(args: Iterable<Throwable>): MercenaryMultipleAssertionExceptiion {
        args.forEach { suppress(it) }
        return this
    }

    @CreatorsDsl
    fun suppress(args: Iterator<Throwable>): MercenaryMultipleAssertionExceptiion {
        args.forEach { suppress(it) }
        return this
    }

    @CreatorsDsl
    fun suppress(args: Sequence<Throwable>): MercenaryMultipleAssertionExceptiion {
        args.forEach { suppress(it) }
        return this
    }

    companion object {

        private const val serialVersionUID = 2L

        @JvmStatic
        @CreatorsDsl
        private fun format(oops: Throwable, head: Char = SPACE_LETTER): String {
            return "$head${oops.nameOf()}: ${oops.message.toTrimOr("<no message>")}"
        }

        @JvmStatic
        @CreatorsDsl
        private fun format(text: String, args: List<Throwable>, size: Int = args.size, last: Int = size - 1): String {
            return when (size) {
                0 -> text
                else -> stringOf {
                    add(text, " ($size failure".pluralOf(size, ")")).newline()
                    last.forEach { each ->
                        add(format(args[each])).newline()
                    }
                    add(format(args[last], Escapers.ASCII_VTCHAR))
                }
            }
        }
    }
}

@IgnoreForSerialize
fun interface AssumptionContainer {

    @CreatorsDsl
    fun assumeThat(block: () -> Unit)
}

@CreatorsDsl
fun assumeEach(block: AssumptionContainer.() -> Unit) {
    AssumptionCollector(block).also { self -> self.invoke() }
}

@IgnoreForSerialize
class AssumptionCollector @CreatorsDsl constructor(block: AssumptionCollector.() -> Unit) : AssumptionContainer {

    @CreatorsDsl
    private val list = ArrayList<() -> Unit>()

    init {
        block(this)
    }

    @CreatorsDsl
    operator fun invoke() {
        if (list.isNotEmpty()) {
            val look = ArrayList<Throwable>(list.size)
            list.forEach { function ->
                try {
                    function.invoke()
                } catch (oops: Throwable) {
                    look += Throwables.check(oops)
                }
            }
            if (look.isNotEmpty()) {
                throw MercenaryMultipleAssertionExceptiion(look).suppress()
            }
        }
    }

    @CreatorsDsl
    override fun assumeThat(block: () -> Unit) {
        list += block
    }

    @CreatorsDsl
    override fun toString() = nameOf()

    @CreatorsDsl
    override fun hashCode() = idenOf()

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is AssumptionCollector -> this === other
        else -> false
    }
}

