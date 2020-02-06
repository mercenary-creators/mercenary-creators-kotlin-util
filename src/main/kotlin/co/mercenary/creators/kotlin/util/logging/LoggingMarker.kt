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

package co.mercenary.creators.kotlin.util.logging

import org.slf4j.Marker
import kotlin.reflect.KClass

class LoggingMarker(private val marker: Marker) : IMarker {

    constructor(name: String) : this(LoggingFactory.markerOf(name))

    constructor(type: Class<*>) : this(type.name)

    constructor(type: KClass<*>) : this(type.java)

    constructor(data: Any) : this(data.javaClass.name)

    constructor(func: () -> Unit) : this(LoggingFactory.markerOf(func))

    override val name: String
        get() = marker.name

    override fun markerOf() = marker

    override operator fun plus(value: IMarker): IMarker {
        marker.add(value.markerOf())
        return this
    }

    override operator fun minus(value: IMarker): IMarker {
        marker.remove(value.markerOf())
        return this
    }

    override operator fun contains(value: String): Boolean {
        return marker.contains(value)
    }

    override operator fun contains(value: IMarker): Boolean {
        return marker.contains(value.markerOf())
    }

    override fun toString() = marker.toString()

    override fun hashCode() = marker.hashCode()

    override fun equals(other: Any?) = when (other) {
        is LoggingMarker -> this === other || marker == other.marker
        else -> false
    }
}