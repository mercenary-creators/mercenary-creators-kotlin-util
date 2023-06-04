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

package co.mercenary.creators.kotlin.util.flux

import co.mercenary.creators.kotlin.util.*

@IgnoreForSerialize
class BoundedElasticReactiveScheduler @JvmOverloads @FrameworkDsl constructor(name: CharSequence, thread: Int = getDefaultBoundedThread(), queued: Int = getDefaultBoundedQueued(), seconds: Int = getDefaultBoundedSeconds(), daemon: Boolean = false) : AbstractReactiveScheduler(bounded(name.copyOf(), thread, queued, seconds, daemon), name.copyOf(), daemon.isTrue(), ReactiveSchedulerKind.BOUNDED, seconds.maxOf(1)) {

    @FrameworkDsl
    private val threads = thread.copyOf()

    @FrameworkDsl
    private val queueds = queued.copyOf()

    @FrameworkDsl
    @IgnoreForSerialize
    fun getsBoundedQueued(): Int = queueds

    @FrameworkDsl
    @IgnoreForSerialize
    fun getsBoundedThreads(): Int = threads

    @FrameworkDsl
    @IgnoreForSerialize
    fun getsBoundedSeconds(): Int = sizeOf()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getSchedulerProperties() = dictOf("threads" to getsBoundedThreads(), "queued" to getsBoundedQueued(), "seconds" to getsBoundedSeconds())
}