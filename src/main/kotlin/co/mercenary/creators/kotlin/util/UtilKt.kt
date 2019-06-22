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

@file:kotlin.jvm.JvmName("UtilKt")

package co.mercenary.creators.kotlin.util

import co.mercenary.creators.kotlin.util.core.CoreValidated
import co.mercenary.creators.kotlin.util.time.NanoTicker
import java.util.*
import java.util.concurrent.atomic.*

const val IS_NOT_FOUND = -1

const val EMPTY_STRING = ""

const val CHAR_INVALID = Char.MIN_VALUE

typealias TimeUnit = java.util.concurrent.TimeUnit

fun uuid() = UUID.randomUUID().toString()

fun getProcessors(): Int = Runtime.getRuntime().availableProcessors()

fun getTimeStamp(nano: Boolean = false): Long = if (nano) System.nanoTime() else System.currentTimeMillis()

fun getLowerTrim(data: String): String = data.trim().toLowerCase()

fun toTrimOrNull(data: String?): String? = data?.trim().takeUnless { it.isNullOrEmpty() }

fun toTrimOrElse(data: String?, other: () -> String): String = toTrimOrNull(data) ?: other()

fun toTrimOrElse(data: String?, other: String = EMPTY_STRING): String = toTrimOrNull(data) ?: other

fun toDecimalPlaces(data: Double, tail: String = EMPTY_STRING): String = "(%.3f)%s".format(data, tail)

fun toElapsedString(data: Long): String = if (data < 1000000L) "($data) nanoseconds." else if (data < 1000000000L) toDecimalPlaces(1.0E-6 * data, " milliseconds.") else toDecimalPlaces(1.0E-9 * data, " seconds.")

fun toSafeString(block: () -> Any?): String = try {
    when (val data = block()) {
        null -> "null"
        else -> data.toString()
    }
}
catch (e: Throwable) {
    "toSafeString(${e.message})"
}

fun getCheckedString(data: String): String {
    val size = data.length
    for (posn in 0 until size) {
        if (data[posn] == CHAR_INVALID) {
            throw MercenaryExceptiion("Null byte present in file/path name. There are no known legitimate use cases for such data, but several injection attacks may use it.")
        }
    }
    return data
}

fun sleepFor(duration: Long, unit: TimeUnit = TimeUnit.MILLISECONDS): Long {
    if (duration <= 0) {
        return 0
    }
    val time = getTimeStamp()
    try {
        return duration.also { TimeUnit.MILLISECONDS.sleep(unit.convert(it, TimeUnit.MILLISECONDS)) }
    }
    catch (_: Throwable) {
    }
    return getTimeStamp() - time
}

fun Date?.copyOf(): Date = when (this) {
    null -> Date()
    else -> Date(time)
}

fun isValid(value: Any?): Boolean = when (value) {
    null -> false
    is CoreValidated -> {
        try {
            value.isValid()
        }
        catch (_: Throwable) {
            false
        }
    }
    else -> true
}

fun isValid(func: () -> Any?): Boolean = try {
    isValid(func())
}
catch (_: Throwable) {
    false
}

fun Date.toLong(): Long = this.time

fun Long.toDate(): Date = Date(this)

fun Long.toAtomic(): AtomicLong = AtomicLong(this)

fun AtomicLong.toValue(): Long = this.get()

fun Int.toAtomic(): AtomicInteger = AtomicInteger(this)

fun AtomicInteger.toValue(): Int = this.get()

fun Boolean.toAtomic(): AtomicBoolean = AtomicBoolean(this)

fun AtomicBoolean.toValue(): Boolean = this.get()

fun AtomicLong.assign(value: Long): Boolean {
    return this.compareAndSet(value, value)
}

fun AtomicInteger.assign(value: Int): Boolean {
    return this.compareAndSet(value, value)
}

fun AtomicBoolean.assign(value: Boolean): Boolean {
    return this.compareAndSet(value, value)
}

operator fun AtomicInteger.inc(): AtomicInteger {
    this.incrementAndGet()
    return this
}

operator fun AtomicInteger.dec(): AtomicInteger {
    this.decrementAndGet()
    return this
}

operator fun AtomicInteger.plus(delta: Int): AtomicInteger {
    return AtomicInteger(this.get() + delta)
}

operator fun AtomicInteger.plus(delta: AtomicInteger): AtomicInteger {
    return AtomicInteger(this.get() + delta.get())
}

operator fun AtomicInteger.minus(delta: Int): AtomicInteger {
    return AtomicInteger(this.get() - delta)
}

operator fun AtomicInteger.minus(delta: AtomicInteger): AtomicInteger {
    return AtomicInteger(this.get() - delta.get())
}

operator fun AtomicInteger.plusAssign(delta: Int) {
    this.addAndGet(delta)
}

operator fun AtomicInteger.plusAssign(delta: AtomicInteger) {
    this.addAndGet(delta.get())
}

operator fun AtomicInteger.minusAssign(delta: Int) {
    this.addAndGet(delta)
}

operator fun AtomicInteger.minusAssign(delta: AtomicInteger) {
    this.addAndGet(delta.get())
}

operator fun AtomicLong.inc(): AtomicLong {
    this.incrementAndGet()
    return this
}

operator fun AtomicLong.dec(): AtomicLong {
    this.decrementAndGet()
    return this
}

operator fun AtomicLong.plus(delta: Int): AtomicLong {
    return AtomicLong(this.get() + delta.toLong())
}

operator fun AtomicLong.plus(delta: Long): AtomicLong {
    return AtomicLong(this.get() + delta)
}

operator fun AtomicLong.minus(delta: Int): AtomicLong {
    return AtomicLong(this.get() - delta.toLong())
}

operator fun AtomicLong.minus(delta: Long): AtomicLong {
    return AtomicLong(this.get() - delta)
}

operator fun AtomicLong.plusAssign(delta: Int) {
    this.addAndGet(delta.toLong())
}

operator fun AtomicLong.plusAssign(delta: Long) {
    this.addAndGet(delta)
}

operator fun AtomicLong.minusAssign(delta: Int) {
    this.addAndGet(delta.toLong())
}

operator fun AtomicLong.minusAssign(delta: Long) {
    this.addAndGet(delta)
}

fun <T> timed(after: (String) -> Unit, block: () -> T): T = NanoTicker().let { block().also { after(it(false)) } }