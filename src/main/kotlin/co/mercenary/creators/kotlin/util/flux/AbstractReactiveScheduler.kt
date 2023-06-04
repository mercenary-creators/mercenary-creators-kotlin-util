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
import reactor.core.scheduler.*

@IgnoreForSerialize
abstract class AbstractReactiveScheduler(scheduler: Scheduler, nameof: CharSequence, daemon: Boolean, kindof: ReactiveSchedulerKind, sizeof: Int) : ReactiveScheduler, Scheduler by scheduler {

    @FrameworkDsl
    private val text = nameof.copyOf()

    @FrameworkDsl
    private val flag = daemon.copyOf()

    @FrameworkDsl
    private val kind = kindof.copyOf()

    @FrameworkDsl
    private val size = sizeof.copyOf()

    @FrameworkDsl
    override fun sizeOf(): Int {
        return size.copyOf()
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isOpen(): Boolean {
        return isDisposed.isNotTrue()
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getName(): String {
        return text.copyOf()
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isDaemon(): Boolean {
        return flag.copyOf()
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getSchedulerKind(): ReactiveSchedulerKind {
        return kind.copyOf()
    }

    @FrameworkDsl
    override fun close() {
        if (isOpen()) {
            reset()
        }
    }

    @FrameworkDsl
    override fun reset() {
        dispose()
    }

    @FrameworkDsl
    override fun hashCode() = idenOf()

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is AbstractReactiveScheduler -> other === this
        else -> false
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getSchedulerProperties(): Dictionary<Maybe> {
        return dictOf("size" to sizeOf())
    }

    @FrameworkDsl
    override fun toMapNames(): Dictionary<Maybe> {
        return BasicDictionaryMap<Maybe>("type" to nameOf(), "name" to getName(), "kind" to getSchedulerKind().toUpperCaseEnglish(), "open" to isOpen(), "daemon" to isDaemon()).append(getSchedulerProperties()).toReadOnly()
    }

    companion object {

        @FrameworkDsl
        private const val DEFAULT_SECOND_TTL = 60

        @FrameworkDsl
        private const val DEFAULT_QUEUED_MIN = 1

        @FrameworkDsl
        private const val DEFAULT_QUEUED_MAX = Int.MAX_VALUE

        @FrameworkDsl
        private val DEFAULT_PARALLELISM: Int by lazy {
            getProcessors()
        }

        @JvmStatic
        @FrameworkDsl
        fun getDefaultParalleism(): Int = DEFAULT_PARALLELISM.copyOf()

        @JvmStatic
        @FrameworkDsl
        fun getDefaultBoundedThread(): Int = (((getDefaultParalleism().maxOf(2) + 1) * 2) / 2)

        @JvmStatic
        @FrameworkDsl
        fun getDefaultBoundedQueued(): Int = getDefaultBoundedThread().boxIn(DEFAULT_QUEUED_MIN, DEFAULT_QUEUED_MAX)

        @JvmStatic
        @FrameworkDsl
        fun getDefaultBoundedSeconds(): Int = DEFAULT_SECOND_TTL

        @JvmStatic
        @FrameworkDsl
        fun immediate(): Scheduler = Schedulers.immediate()

        @JvmStatic
        @FrameworkDsl
        @JvmOverloads
        fun bounded(name: CharSequence, threads: Int = getDefaultBoundedThread(), queued: Int = getDefaultBoundedQueued(), seconds: Int = getDefaultBoundedSeconds(), daemon: Boolean = false): Scheduler = Schedulers.newBoundedElastic(threads.maxOf(1), queued.boxIn(DEFAULT_QUEUED_MIN, DEFAULT_QUEUED_MAX), name.copyOf(), seconds.maxOf(1), daemon.isTrue())

        @JvmStatic
        @FrameworkDsl
        @JvmOverloads
        fun single(name: CharSequence, daemon: Boolean = false): Scheduler = Schedulers.newSingle(name.copyOf(), daemon.isTrue())

        @JvmStatic
        @FrameworkDsl
        @JvmOverloads
        fun parallel(name: CharSequence, parallelism: Int = getDefaultParalleism(), daemon: Boolean = false): Scheduler = Schedulers.newParallel(name.copyOf(), parallelism.maxOf(1), daemon.isTrue())
    }
}