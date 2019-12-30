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

@file:kotlin.jvm.JvmName("MainKt")
@file:kotlin.jvm.JvmMultifileClass

package co.mercenary.creators.kotlin.util

import org.reactivestreams.Publisher
import reactor.core.publisher.*
import java.util.concurrent.*
import java.util.stream.Collectors

typealias SingleScheduler = co.mercenary.creators.kotlin.util.reactive.SingleScheduler

typealias ElasticScheduler = co.mercenary.creators.kotlin.util.reactive.ElasticScheduler

typealias ParallelScheduler = co.mercenary.creators.kotlin.util.reactive.ParallelScheduler

fun <T : Any> Flux<T>.toList(): List<T> = collect(Collectors.toList<T>()).block().orElse { emptyList() }

fun <T : Any> Flux<T>.limit(size: Long): Flux<T> = limitRequest(size)

fun <T : Any> Flux<T>.limit(size: Int): Flux<T> = limitRequest(size.toLong())

fun <T : Any> Flux<T>.rated(size: Int): Flux<T> = limitRate(size)

fun <T : Any> Flux<T>.rated(high: Int, lows: Int): Flux<T> = limitRate(high, lows)

fun <T : Any> Flux<T>.cache(time: TimeDuration): Flux<T> = cache(time.duration)

fun <T : Any> Flux<T>.cache(size: Int, time: TimeDuration): Flux<T> = cache(size, time.duration)

fun <T : Any> T.toMono(): Mono<T> = Mono.just(this)

fun <T> Throwable.toMono(): Mono<T> = Mono.error(this)

fun <T> Publisher<T>.toMono(): Mono<T> = Mono.from(this)

fun <T> (() -> T?).toMono(): Mono<T> = Mono.fromSupplier(this)

fun <T> Callable<T?>.toMono(): Mono<T> = Mono.fromCallable(this::call)

fun <T> CompletableFuture<out T?>.toMono(): Mono<T> = Mono.fromFuture(this)

inline fun <reified T : Any> Mono<*>.cast(): Mono<T> {
    return this.cast(T::class.java)
}

inline fun <reified T : Any> Mono<*>.ofType(): Mono<T> {
    return ofType(T::class.java)
}

fun <T> Throwable.toFlux(): Flux<T> = Flux.error(this)

fun <T> Array<out T>.toFlux(): Flux<T> = Flux.fromArray(this)

fun <T : Any> Publisher<T>.toFlux(): Flux<T> = Flux.from(this)

fun <T : Any> Iterable<T>.toFlux(): Flux<T> = Flux.fromIterable(this)

fun <T : Any> Sequence<T>.toFlux(): Flux<T> = Flux.fromIterable(object : Iterable<T> {
    override operator fun iterator(): Iterator<T> = this@toFlux.iterator()
})

inline fun <reified T : Any> Flux<*>.cast(): Flux<T> {
    return cast(T::class.java)
}

inline fun <reified T : Any> Flux<*>.ofType(): Flux<T> {
    return ofType(T::class.java)
}

fun <T : Any> Flux<out Iterable<T>>.split(): Flux<T> = flatMapIterable { it }
