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

@file:JvmName("SameKt")
@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST", "FunctionName", "HttpUrlsUsage")

package co.mercenary.creators.kotlin.util

import kotlin.contracts.contract

@FrameworkDsl
const val SHOULD_BE_SAME_ARRAY_FAILED = "shouldBeSameArray failed"

@FrameworkDsl
const val SHOULD_NOT_BE_SAME_ARRAY_FAILED = "shouldNotBeSameArray failed"

@FrameworkDsl
inline fun LazyMessage.toSafeString(): String = Formatters.toSafeString { this }

@FrameworkDsl
inline fun fatal(text: String): Nothing {
    MercenaryFatalExceptiion(text).failed()
}

@FrameworkDsl
inline fun fatal(cause: Throwable): Nothing {
    MercenaryFatalExceptiion(Throwables.check(cause)).failed()
}

@FrameworkDsl
inline fun fail(text: String): Nothing {
    MercenaryAssertionExceptiion(text).failed()
}

@FrameworkDsl
inline fun fail(func: LazyMessage): Nothing {
    fail(func.toSafeString())
}

@FrameworkDsl
inline fun shouldBeTrue(value: Boolean, text: String) {
    contract {
        returns() implies value
    }
    if (value.isNotTrue()) {
        fail(text)
    }
}

@FrameworkDsl
inline fun shouldBeTrue(value: Boolean, func: LazyMessage) {
    contract {
        returns() implies value
    }
    if (value.isNotTrue()) {
        fail(func)
    }
}

@FrameworkDsl
inline fun shouldNotBeTrue(value: Boolean, text: String) {
    contract {
        returns() implies !value
    }
    if (value.isTrue()) {
        fail(text)
    }
}

@FrameworkDsl
inline fun shouldNotBeTrue(value: Boolean, func: LazyMessage) {
    contract {
        returns() implies !value
    }
    if (value.isTrue()) {
        fail(func)
    }
}

@FrameworkDsl
fun shouldBeValid(value: Validated, text: String) {
    if (value.isNotValid()) {
        fail(text)
    }
}

@FrameworkDsl
fun shouldBeValid(value: Validated, func: LazyMessage) {
    if (value.isNotValid()) {
        fail(func)
    }
}

@FrameworkDsl
fun shouldNotBeValid(value: Validated, text: String) {
    if (value.isValid()) {
        fail(text)
    }
}

@FrameworkDsl
fun shouldNotBeValid(value: Validated, func: LazyMessage) {
    if (value.isValid()) {
        fail(func)
    }
}

@FrameworkDsl
inline fun <reified T : Throwable> assumeThrows(crossinline block: () -> Unit) {
    try {
        block.invoke()
    } catch (cause: Throwable) {
        when (cause is T) {
            true -> return
            else -> fail("assumeThrows failed ${cause.javaClass.name} not ${T::class.java.name}")
        }
    }
    fail("assumeThrows failed for ${T::class.java.name}")
}

@FrameworkDsl
inline fun <reified T : Throwable> assumeNotThrows(crossinline block: () -> Unit) {
    try {
        block.invoke()
    } catch (cause: Throwable) {
        when (cause !is T) {
            true -> return
            else -> fail("assumeNotThrows failed ${cause.javaClass.name} not ${T::class.java.name}")
        }
    }
}

@FrameworkDsl
infix fun Map<*, *>.isSameAs(value: Map<*, *>) = SameAndHashCode.isSameAs(this, value)

@FrameworkDsl
infix fun List<*>.isSameAs(value: List<*>) = SameAndHashCode.isSameAs(this, value)

@FrameworkDsl
infix fun Set<*>.isSameAs(value: Set<*>) = SameAndHashCode.isSameAs(this, value)

@FrameworkDsl
infix fun Collection<*>.isSameAs(value: Collection<*>) = SameAndHashCode.isSameAs(this, value)

@FrameworkDsl
infix fun CharSequence.isSameAs(value: CharSequence) = copyOf() == value.copyOf()

@FrameworkDsl
infix fun CharSequence.isNotSameAs(value: CharSequence) = copyOf() != value.copyOf()

@FrameworkDsl
infix fun <T : Any?> T.isSameAs(value: T) = SameAndHashCode.isSameAs(this, value)

@FrameworkDsl
infix fun <T : Any?> T.isNotSameAs(value: T) = SameAndHashCode.isNotSameAs(this, value)

@FrameworkDsl
infix fun <T : Any?> T.isContentSameAs(value: T) = SameAndHashCode.isContentSameAs(this, value)

@FrameworkDsl
infix fun <T : Any?> T.isContentNotSameAs(value: T) = SameAndHashCode.isContentNotSameAs(this, value)

@FrameworkDsl
infix fun <T : Any?> T.shouldBe(value: T) = shouldBeTrue(value isSameAs this, "shouldBe failed")

@FrameworkDsl
infix fun <T : Any?> T.shouldNotBe(value: T) = shouldBeTrue(value isNotSameAs this, "shouldNotBe failed")

@FrameworkDsl
infix fun <T : Any?> T.shouldBeSameContent(value: T) = shouldBeTrue(value isContentSameAs this, "shouldBeSameContent failed")

@FrameworkDsl
infix fun <T : Any?> T.shouldNotBeSameContent(value: T) = shouldBeTrue(value isContentNotSameAs this, "shouldNotBeSameContent failed")

@FrameworkDsl
infix fun Array<*>.shouldBeSameArray(value: Array<*>) = shouldBeTrue(value isSameArrayAs this, SHOULD_BE_SAME_ARRAY_FAILED)

@FrameworkDsl
infix fun IntArray.shouldBeSameArray(value: IntArray) = shouldBeTrue(value isSameArrayAs this, SHOULD_BE_SAME_ARRAY_FAILED)

@FrameworkDsl
infix fun ByteArray.shouldBeSameArray(value: ByteArray) = shouldBeTrue(value isSameArrayAs this, SHOULD_BE_SAME_ARRAY_FAILED)

@FrameworkDsl
infix fun CharArray.shouldBeSameArray(value: CharArray) = shouldBeTrue(value isSameArrayAs this, SHOULD_BE_SAME_ARRAY_FAILED)

@FrameworkDsl
infix fun LongArray.shouldBeSameArray(value: LongArray) = shouldBeTrue(value isSameArrayAs this, SHOULD_BE_SAME_ARRAY_FAILED)

@FrameworkDsl
infix fun ShortArray.shouldBeSameArray(value: ShortArray) = shouldBeTrue(value isSameArrayAs this, SHOULD_BE_SAME_ARRAY_FAILED)

@FrameworkDsl
infix fun FloatArray.shouldBeSameArray(value: FloatArray) = shouldBeTrue(value isSameArrayAs this, SHOULD_BE_SAME_ARRAY_FAILED)

@FrameworkDsl
infix fun DoubleArray.shouldBeSameArray(value: DoubleArray) = shouldBeTrue(value isSameArrayAs this, SHOULD_BE_SAME_ARRAY_FAILED)

@FrameworkDsl
infix fun BooleanArray.shouldBeSameArray(value: BooleanArray) = shouldBeTrue(value isSameArrayAs this, SHOULD_BE_SAME_ARRAY_FAILED)

@FrameworkDsl
infix fun Array<*>.shouldNotBeSameArray(value: Array<*>) = shouldBeTrue(value isNotSameArrayAs this, SHOULD_NOT_BE_SAME_ARRAY_FAILED)

@FrameworkDsl
infix fun IntArray.shouldNotBeSameArray(value: IntArray) = shouldBeTrue(value isNotSameArrayAs this, SHOULD_NOT_BE_SAME_ARRAY_FAILED)

@FrameworkDsl
infix fun ByteArray.shouldNotBeSameArray(value: ByteArray) = shouldBeTrue(value isNotSameArrayAs this, SHOULD_NOT_BE_SAME_ARRAY_FAILED)

@FrameworkDsl
infix fun CharArray.shouldNotBeSameArray(value: CharArray) = shouldBeTrue(value isNotSameArrayAs this, SHOULD_NOT_BE_SAME_ARRAY_FAILED)

@FrameworkDsl
infix fun LongArray.shouldNotBeSameArray(value: LongArray) = shouldBeTrue(value isNotSameArrayAs this, SHOULD_NOT_BE_SAME_ARRAY_FAILED)

@FrameworkDsl
infix fun ShortArray.shouldNotBeSameArray(value: ShortArray) = shouldBeTrue(value isNotSameArrayAs this, SHOULD_NOT_BE_SAME_ARRAY_FAILED)

@FrameworkDsl
infix fun FloatArray.shouldNotBeSameArray(value: FloatArray) = shouldBeTrue(value isNotSameArrayAs this, SHOULD_NOT_BE_SAME_ARRAY_FAILED)

@FrameworkDsl
infix fun DoubleArray.shouldNotBeSameArray(value: DoubleArray) = shouldBeTrue(value isNotSameArrayAs this, SHOULD_NOT_BE_SAME_ARRAY_FAILED)

@FrameworkDsl
infix fun BooleanArray.shouldNotBeSameArray(value: BooleanArray) = shouldBeTrue(value isNotSameArrayAs this, SHOULD_NOT_BE_SAME_ARRAY_FAILED)

@IgnoreForSerialize
class MercenaryMultipleAssertionExceptiion @JvmOverloads @FrameworkDsl constructor(args: Iterable<Throwable>, message: String = EMPTY_STRING, cause: Throwable? = null) : AssertionError(message, cause), MutableSizedContainer {

    @FrameworkDsl
    @JvmOverloads
    constructor(args: Iterator<Throwable>, message: String = EMPTY_STRING, cause: Throwable? = null) : this(args.toIterable(), message, cause)

    @FrameworkDsl
    @JvmOverloads
    constructor(args: Sequence<Throwable>, message: String = EMPTY_STRING, cause: Throwable? = null) : this(args.toIterable(), message, cause)

    @FrameworkDsl
    private val list = BasicArrayList(args)

    @FrameworkDsl
    override fun sizeOf(): Int {
        return list.sizeOf()
    }

    override val cause: Throwable?
        @IgnoreForSerialize
        get() = super.cause

    override val message: String
        @IgnoreForSerialize
        get() = format(text(), list)

    @FrameworkDsl
    private fun text(): String {
        return super.message.toTrimOr("multiple failures")
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }

    @FrameworkDsl
    override fun clear() {
        list.clear()
    }

    @FrameworkDsl
    fun append(cause: Throwable, vararg args: Throwable): MercenaryMultipleAssertionExceptiion {
        list.append(cause)
        if (args.isNotExhausted()) {
            list.append(*args)
        }
        return this
    }

    @FrameworkDsl
    fun append(args: Iterator<Throwable>): MercenaryMultipleAssertionExceptiion {
        if (args.isNotExhausted()) {
            list.append(args)
        }
        return this
    }

    @FrameworkDsl
    fun append(args: Iterable<Throwable>): MercenaryMultipleAssertionExceptiion {
        if (args.isNotExhausted()) {
            list.append(args)
        }
        return this
    }

    @FrameworkDsl
    fun append(args: Sequence<Throwable>): MercenaryMultipleAssertionExceptiion {
        if (args.isNotExhausted()) {
            list.append(args)
        }
        return this
    }

    @FrameworkDsl
    fun suppress(): MercenaryMultipleAssertionExceptiion {
        list.forEach { suppress(it) }
        return this
    }

    @FrameworkDsl
    fun suppress(cause: Throwable): MercenaryMultipleAssertionExceptiion {
        addSuppressed(cause)
        return this
    }

    @FrameworkDsl
    fun suppress(vararg args: Throwable): MercenaryMultipleAssertionExceptiion {
        args.forEach { suppress(it) }
        return this
    }

    @FrameworkDsl
    fun suppress(args: Iterable<Throwable>): MercenaryMultipleAssertionExceptiion {
        args.forEach { suppress(it) }
        return this
    }

    @FrameworkDsl
    fun suppress(args: Iterator<Throwable>): MercenaryMultipleAssertionExceptiion {
        args.forEach { suppress(it) }
        return this
    }

    @FrameworkDsl
    fun suppress(args: Sequence<Throwable>): MercenaryMultipleAssertionExceptiion {
        args.forEach { suppress(it) }
        return this
    }

    companion object {

        private const val serialVersionUID = 2L

        @JvmStatic
        @FrameworkDsl
        private fun format(oops: Throwable, head: Char = SPACE_LETTER): String {
            return "$head${oops.nameOf()}: ${oops.message.toTrimOr("<no message>")}"
        }

        @JvmStatic
        @FrameworkDsl
        private fun format(text: String, args: List<Throwable>, size: Int = args.sizeOf(), last: Int = size - 1): String {
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

typealias Assumption = () -> Unit

@IgnoreForSerialize
fun interface AssumptionContainer {

    @FrameworkDsl
    fun assumeThat(block: Assumption)
}

@FrameworkDsl
fun assumeEach(block: AssumptionContainer.() -> Unit) {
    AssumptionCollector(block).also { self -> self.invoke() }
}

@IgnoreForSerialize
class AssumptionCollector @FrameworkDsl constructor(block: AssumptionCollector.() -> Unit) : AssumptionContainer {

    @FrameworkDsl
    private val list = BasicArrayList<Assumption>()

    init {
        block(this)
    }

    @FrameworkDsl
    operator fun invoke() {
        if (list.isNotEmpty()) {
            val look = BasicArrayList<Throwable>(list.sizeOf())
            list.forEach { function ->
                try {
                    function.invoke()
                } catch (oops: Throwable) {
                    look.add(Throwables.check(oops))
                }
            }
            if (look.isNotEmpty()) {
                MercenaryMultipleAssertionExceptiion(look).suppress().failed()
            }
        }
    }

    @FrameworkDsl
    override fun assumeThat(block: Assumption) {
        list.add(block)
    }

    @FrameworkDsl
    override fun toString() = nameOf()

    @FrameworkDsl
    override fun hashCode() = idenOf()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is AssumptionCollector -> this === other
        else -> false
    }
}

