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

import co.mercenary.creators.kotlin.util.*
import kotlin.reflect.KClass

@IgnoreForSerialize
class LoggingMarker @CreatorsDsl constructor(private val marker: mu.Marker) : IMarker {

    @CreatorsDsl
    constructor(name: String) : this(LoggingFactory.markerOf(name))

    @CreatorsDsl
    constructor(type: Class<*>) : this(type.name)

    @CreatorsDsl
    constructor(data: Any) : this(data.javaClass)

    @CreatorsDsl
    constructor(type: KClass<*>) : this(type.java)

    @CreatorsDsl
    @IgnoreForSerialize
    override fun getName(): String = marker.name

    @CreatorsDsl
    override fun markerOf(): mu.Marker = marker

    @CreatorsDsl
    override operator fun plusAssign(value: IMarker) {
        marker.add(value.markerOf())
    }

    @CreatorsDsl
    override operator fun minusAssign(value: IMarker) {
        marker.remove(value.markerOf())
    }

    @CreatorsDsl
    override operator fun contains(value: CharSequence): Boolean {
        return marker.contains(value.toString())
    }

    @CreatorsDsl
    @IgnoreForSerialize
    override fun isEmpty(): Boolean {
        return marker.hasReferences().isNotTrue()
    }

    @CreatorsDsl
    override operator fun contains(value: IMarker): Boolean {
        return marker.contains(value.markerOf())
    }

    @CreatorsDsl
    override fun toString() = markerOf().toString()

    @CreatorsDsl
    override fun toMapNames() = dictOf("name" to getName(), "empty" to isEmpty())

    @CreatorsDsl
    override fun hashCode() = toString().hashCode()

    @CreatorsDsl
    override fun equals(other: Any?) = when (other) {
        is LoggingMarker -> this === other || marker == other.marker
        else -> false
    }
}