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

@file:JvmName("FluxKt")
@file:Suppress("NOTHING_TO_INLINE", "FunctionName", "HttpUrlsUsage")

package co.mercenary.creators.kotlin.util

import org.reactivestreams.Publisher
import reactor.core.publisher.*
import java.util.concurrent.*
import java.util.function.Supplier
import java.util.stream.*

typealias SingleReactiveScheduler = co.mercenary.creators.kotlin.util.flux.SingleReactiveScheduler

typealias ParallelReactiveScheduler = co.mercenary.creators.kotlin.util.flux.ParallelReactiveScheduler

typealias ImmediateReactiveScheduler = co.mercenary.creators.kotlin.util.flux.ImmediateReactiveScheduler

typealias BoundedElasticReactiveScheduler = co.mercenary.creators.kotlin.util.flux.BoundedElasticReactiveScheduler

@FrameworkDsl
fun <T : Any> Flux<T>.toList(): List<T> = collect(Collectors.toList()).block().otherwise { toListOf() }

@FrameworkDsl
fun <T : Any> Flux<T>.limit(size: Int): Flux<T> = limit(size.longOf())

@FrameworkDsl
fun <T : Any> Flux<T>.limit(size: Long): Flux<T> = take(size.maxOf(0L), true)

@FrameworkDsl
fun <T : Any> Flux<T>.rated(size: Int): Flux<T> = limitRate(size.copyOf())

@FrameworkDsl
fun <T : Any> Flux<T>.rated(size: Int, lows: Int): Flux<T> = limitRate(size.copyOf(), lows.copyOf())

@FrameworkDsl
fun <T : Any> Flux<T>.cache(time: CreatorsTimeDuration): Flux<T> = cache(time.duration())

@FrameworkDsl
fun <T : Any> Flux<T>.cache(time: KotlinTimeDuration): Flux<T> = cache(time.toCreatorsTimeDuration())

@FrameworkDsl
fun <T : Any> Flux<T>.cache(size: Int, time: CreatorsTimeDuration): Flux<T> = cache(size.maxOf(0), time.duration())

@FrameworkDsl
fun <T : Any> Flux<T>.cache(size: Int, time: KotlinTimeDuration): Flux<T> = cache(size, time.toCreatorsTimeDuration())

@FrameworkDsl
fun <T : Any> Mono<T>.cache(time: CreatorsTimeDuration): Mono<T> = cache(time.duration())

@FrameworkDsl
fun <T : Any> Mono<T>.cache(time: KotlinTimeDuration): Mono<T> = cache(time.toCreatorsTimeDuration())

@FrameworkDsl
fun <T : Any> T.toMono(): Mono<T> = Mono.just(this)

@FrameworkDsl
fun <T> Throwable.toMono(): Mono<T> = Mono.error(this)

@FrameworkDsl
fun <T> Supplier<Throwable>.toMono(): Mono<T> = Mono.error(this)

@FrameworkDsl
fun <T> Publisher<T>.toMono(): Mono<T> = Mono.from(this)

@FrameworkDsl
fun <T> (() -> T?).toMono(): Mono<T> = Mono.fromSupplier(this)

@FrameworkDsl
fun <T> Callable<T?>.toMono(): Mono<T> = Mono.fromCallable(this::call)

@FrameworkDsl
fun <T> CompletableFuture<out T?>.toMono(): Mono<T> = Mono.fromFuture(this)

@FrameworkDsl
inline fun <reified T : Any> Mono<*>.cast(): Mono<T> {
    return this.cast(T::class.java)
}

@FrameworkDsl
inline fun <reified T : Any> Mono<*>.ofType(): Mono<T> {
    return ofType(T::class.java)
}

@FrameworkDsl
fun <T : Any> Mono<T>.block(time: CreatorsTimeDuration): T? = block(time.duration())

@FrameworkDsl
fun <T : Any> Mono<T>.block(time: KotlinTimeDuration): T? = block(time.toCreatorsTimeDuration())

@FrameworkDsl
fun <T : Any> Mono<T>.blocked(): T = block() ?: throw MercenaryFatalExceptiion("null Mono.blocked()")

@FrameworkDsl
fun <T : Any> Mono<T>.blocked(time: CreatorsTimeDuration): T = block(time) ?: throw MercenaryFatalExceptiion("null Mono.blocked($time)")

@FrameworkDsl
fun <T : Any> Mono<T>.blocked(time: KotlinTimeDuration): T = blocked(time.toCreatorsTimeDuration())

@FrameworkDsl
fun <T> Throwable.toFlux(requested: Boolean = false): Flux<T> = Flux.error(this, requested.isTrue())

@FrameworkDsl
inline fun <T> Array<out T>.toFlux(): Flux<T> = Flux.fromArray(this)

@FrameworkDsl
inline fun <T : Any> Stream<T>.toFlux(): Flux<T> = Flux.fromStream(this)

@FrameworkDsl
inline fun <T : Any> Iterable<T>.toFlux(): Flux<T> = Flux.fromIterable(this)

@FrameworkDsl
inline fun <T : Any> Sequence<T>.toFlux(): Flux<T> = toIterable().toFlux()

@FrameworkDsl
inline fun <T : Any> Iterator<T>.toFlux(): Flux<T> = toIterable().toFlux()

@FrameworkDsl
inline fun <T : Any> Publisher<T>.toFlux(): Flux<T> = Flux.from(this)

@FrameworkDsl
fun IntStream.toFlux(): Flux<Int> = boxed().toFlux()

@FrameworkDsl
fun LongStream.toFlux(): Flux<Long> = boxed().toFlux()

@FrameworkDsl
fun DoubleStream.toFlux(): Flux<Double> = boxed().toFlux()

@FrameworkDsl
fun IntArray.toFlux(): Flux<Int> = getArray().toFlux()

@FrameworkDsl
fun ByteArray.toFlux(): Flux<Byte> = getArray().toFlux()

@FrameworkDsl
fun CharArray.toFlux(): Flux<Char> = getArray().toFlux()

@FrameworkDsl
fun LongArray.toFlux(): Flux<Long> = getArray().toFlux()

@FrameworkDsl
fun ShortArray.toFlux(): Flux<Short> = getArray().toFlux()

@FrameworkDsl
fun FloatArray.toFlux(): Flux<Float> = getArray().toFlux()

@FrameworkDsl
fun DoubleArray.toFlux(): Flux<Double> = getArray().toFlux()

@FrameworkDsl
fun BooleanArray.toFlux(): Flux<Boolean> = getArray().toFlux()

@FrameworkDsl
inline fun <reified T : Any> Flux<*>.cast(): Flux<T> {
    return cast(T::class.java)
}

@FrameworkDsl
inline fun <reified T : Any> Flux<*>.ofType(): Flux<T> {
    return ofType(T::class.java)
}

@FrameworkDsl
fun <T : Any> Flux<out Iterable<T>>.split(): Flux<T> = flatMapIterable { it }



