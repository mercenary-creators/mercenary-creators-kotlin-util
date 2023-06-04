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

package co.mercenary.creators.kotlin.util.system

import co.mercenary.creators.kotlin.util.*
import java.lang.management.ManagementFactory

@FrameworkDsl
@IgnoreForSerialize
object Manager : HasMapNames {

    @FrameworkDsl
    override fun toMapNames() = dictOf("process" to process(), "uptime" to uptime(), "started" to started().toDate(), "memory" to memory(), "system" to system())

    @JvmStatic
    @FrameworkDsl
    fun process(): Int {
        return try {
            ManagementFactory.getRuntimeMXBean().name.split("@")[0].toTrimOr("0").toIntOrElse()
        } catch (cause: Throwable) {
            IS_NOT_FOUND
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun uptime(): CreatorsTimeDuration {
        return try {
            ManagementFactory.getRuntimeMXBean().uptime.Milliseconds
        } catch (cause: Throwable) {
            0L.Milliseconds
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun started(): Long {
        return try {
            ManagementFactory.getRuntimeMXBean().startTime
        } catch (cause: Throwable) {
            0L
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun memory(): List<MemoryUsageData> = listOf(heapMemuryUsage(), otherMemoryUsage())

    @JvmStatic
    @FrameworkDsl
    fun load(): Double {
        return try {
            ManagementFactory.getOperatingSystemMXBean().systemLoadAverage
        } catch (cause: Throwable) {
            -1.0
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun system(): AnyDictionary {
        return try {
            ManagementFactory.getOperatingSystemMXBean().let { data ->
                dictOf("name" to data.name, "arch" to data.arch, "version" to data.version, "load" to data.systemLoadAverage)
            }
        } catch (cause: Throwable) {
            dictOf()
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun heapMemuryUsage(): MemoryUsageData {
        return try {
            ManagementFactory.getMemoryMXBean().heapMemoryUsage.let { data ->
                MemoryUsageData(MemoryType.HEAP, data.used, data.init, data.max, data.committed)
            }
        } catch (cause: Throwable) {
            MemoryUsageData(MemoryType.HEAP, 0L, 0L, 0L, 0L)
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun otherMemoryUsage(): MemoryUsageData {
        return try {
            ManagementFactory.getMemoryMXBean().nonHeapMemoryUsage.let { data ->
                MemoryUsageData(MemoryType.OTHER, data.used, data.init, data.max, data.committed)
            }
        } catch (cause: Throwable) {
            MemoryUsageData(MemoryType.OTHER, 0L, 0L, 0L, 0L)
        }
    }

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()
}