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

package co.mercenary.creators.kotlin.util.logging

import co.mercenary.creators.kotlin.util.*
import kotlin.reflect.KClass

@IgnoreForSerialize
class LoggingMarker @FrameworkDsl constructor(musoft: mu.Marker) : IMarker {

    @FrameworkDsl
    constructor(name: String) : this(LoggingFactory.markerOf(name))

    @FrameworkDsl
    constructor(type: Class<*>) : this(type.nameOf())

    @FrameworkDsl
    constructor(data: Any) : this(data.nameOf())

    @FrameworkDsl
    constructor(type: KClass<*>) : this(type.nameOf())

    @FrameworkDsl
    private val marker = musoft

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getName(): String = marker.name

    @FrameworkDsl
    override fun markerOf() = marker

    @FrameworkDsl
    override operator fun plusAssign(value: IMarker) {
        markerOf().add(value.markerOf())
    }

    @FrameworkDsl
    override operator fun minusAssign(value: IMarker) {
        markerOf().remove(value.markerOf())
    }

    @FrameworkDsl
    override operator fun contains(value: CharSequence): Boolean {
        return markerOf().contains(value.toString())
    }

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isEmpty(): Boolean {
        return markerOf().hasReferences().isNotTrue()
    }

    @FrameworkDsl
    override operator fun contains(value: IMarker): Boolean {
        return markerOf().contains(value.markerOf())
    }

    @FrameworkDsl
    override fun toString() = markerOf().toString()

    @FrameworkDsl
    override fun toMapNames() = dictOf("name" to getName(), "empty" to isEmpty())

    @FrameworkDsl
    override fun hashCode() = markerOf().hashCode()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is LoggingMarker -> this === other || markerOf() == other.markerOf()
        else -> false
    }
}