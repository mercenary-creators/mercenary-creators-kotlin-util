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

@file:Suppress("FunctionName")

package co.mercenary.creators.kotlin.util.json.module

import co.mercenary.creators.kotlin.util.*
import com.fasterxml.jackson.databind.introspect.*

@FrameworkDsl
@IgnoreForSerialize
object MercenaryKotlinAnnotationIntrospector : JacksonAnnotationIntrospector() {

    override fun _isIgnorable(annotated: Annotated): Boolean {
        val look = _findAnnotation(annotated, IgnoreForSerialize::class.java)
        if (look != null) {
            return look.value.isTrue()
        }
        return super._isIgnorable(annotated)
    }

    override fun isIgnorableType(annotated: AnnotatedClass): Boolean? {
        val look = _findAnnotation(annotated, IgnoreForSerialize::class.java)
        if (look != null) {
            return look.value.isTrue()
        }
        return super.isIgnorableType(annotated)
    }

    @FrameworkDsl
    override fun version() = MercenaryPackageVersion.version()
}