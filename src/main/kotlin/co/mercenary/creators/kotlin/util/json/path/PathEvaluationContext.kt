/*
 * Copyright (c) 2021, Mercenary Creators Company. All rights reserved.
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

package co.mercenary.creators.kotlin.util.json.path

import co.mercenary.creators.kotlin.util.*

import com.jayway.jsonpath.TypeRef
import kotlin.reflect.KClass

interface PathEvaluationContext : Copyable<PathEvaluationContext>, Cloneable {

    @FrameworkDsl
    fun <T : Any> root(): T

    @FrameworkDsl
    fun <T : Any> eval(path: String, type: Class<T>): T

    @FrameworkDsl
    fun <T : Any> eval(path: String, type: KClass<T>): T

    @FrameworkDsl
    fun <T : Any> eval(path: String, type: TypeRef<T>): T

    @FrameworkDsl
    fun <T : Any> read(path: String, type: Class<T>): T?

    @FrameworkDsl
    fun <T : Any> read(path: String, type: KClass<T>): T?

    @FrameworkDsl
    fun <T : Any> read(path: String, type: TypeRef<T>): T?

    @FrameworkDsl
    fun <T : Any> eval(path: CompiledPath, type: Class<T>): T

    @FrameworkDsl
    fun <T : Any> eval(path: CompiledPath, type: KClass<T>): T

    @FrameworkDsl
    fun <T : Any> eval(path: CompiledPath, type: TypeRef<T>): T

    @FrameworkDsl
    fun <T : Any> read(path: CompiledPath, type: Class<T>): T?

    @FrameworkDsl
    fun <T : Any> read(path: CompiledPath, type: KClass<T>): T?

    @FrameworkDsl
    fun <T : Any> read(path: CompiledPath, type: TypeRef<T>): T?

    @FrameworkDsl
    fun delete(path: String): PathEvaluationContext

    @FrameworkDsl
    fun delete(path: CompiledPath): PathEvaluationContext

    @FrameworkDsl
    fun rename(path: String, last: String, name: String): PathEvaluationContext

    @FrameworkDsl
    fun rename(path: CompiledPath, last: String, name: String): PathEvaluationContext

    @FrameworkDsl
    fun add(path: String, data: Maybe): PathEvaluationContext

    @FrameworkDsl
    fun add(path: CompiledPath, data: Maybe): PathEvaluationContext

    @FrameworkDsl
    fun set(path: String, data: Maybe): PathEvaluationContext

    @FrameworkDsl
    fun set(path: CompiledPath, data: Maybe): PathEvaluationContext

    @FrameworkDsl
    fun put(path: String, name: String, data: Maybe): PathEvaluationContext

    @FrameworkDsl
    fun put(path: CompiledPath, name: String, data: Maybe): PathEvaluationContext

    @FrameworkDsl
    fun map(path: String, func: (Any, PathEvaluationContext) -> Any): PathEvaluationContext

    @FrameworkDsl
    fun map(path: CompiledPath, func: (Any, PathEvaluationContext) -> Any): PathEvaluationContext
}