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

@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST", "FunctionName", "HttpUrlsUsage")

package co.mercenary.creators.kotlin.util.type

import co.mercenary.creators.kotlin.util.*
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JavaType
import com.jayway.jsonpath.TypeRef
import java.lang.reflect.*
import kotlin.reflect.*
import kotlin.reflect.jvm.*

@FrameworkDsl
@IgnoreForSerialize
object TypeTools : HasMapNames {

    @JvmStatic
    @FrameworkDsl
    fun clazOf(value: Any): Class<*> {
        return when (value) {
            is Class<*> -> value
            is KClass<*> -> value.java
            is JavaType -> value.rawClass
            is KType -> clazOf(value.javaType)
            is Type -> clazOf(value.toJavaTypeOf())
            is TypeRef<*> -> clazOf(value.type)
            is TypeReference<*> -> clazOf(value.type)
            is ParameterizedTypeReference<*> -> clazOf(value.getType())
            else -> value.javaClass
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun nameOf(value: Any): String {
        return when (value) {
            is KType -> value.descriptionOf()
            is KClass<*> -> value.qualifiedName.otherwise("...")
            is Class<*> -> when (value.isKotlinClass()) {
                true -> nameOf(value.kotlin)
                else -> value.name.copyOf()
            }
            is Type -> value.typeName.copyOf()
            is TypeRef<*> -> nameOf(value.type)
            is TypeReference<*> -> nameOf(value.type)
            is ParameterizedTypeReference<*> -> nameOf(value.getType())
            else -> nameOf(value.javaClass)
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun simpleNameOf(value: Any): String {
        return when (value) {
            is KType -> value.descriptionOf()
            is Class<*> -> if (value.isKotlinClass()) "kotlin." + value.simpleName else value.simpleName
            is KClass<*> -> value.java.simpleName
            else -> simpleNameOf(value.javaClass)
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun isAssignable(base: Any, from: Any): Boolean {
        return when (base) {
            is Class<*> -> {
                when (from) {
                    is Class<*> -> isAssignableClass(base, from)
                    is KClass<*> -> isAssignableClass(base, from.java)
                    else -> isAssignableClass(base, from.javaClass)
                }
            }
            is KClass<*> -> {
                when (from) {
                    is Class<*> -> isAssignableClass(base.java, from)
                    is KClass<*> -> isAssignableClass(base.java, from.java)
                    else -> isAssignableClass(base.java, from.javaClass)
                }
            }
            else -> isAssignableClass(base.javaClass, from.javaClass)
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun isAssignableClass(base: Class<*>, from: Class<*>): Boolean {
        return base.isAssignableFrom(from)
    }

    @JvmStatic
    @FrameworkDsl
    fun getParameterizedType(kind: KClass<*>): Type {
        return getParameterizedType(kind.java)
    }

    @JvmStatic
    @FrameworkDsl
    fun getParameterizedType(kind: Class<*>): Type {
        return kind.genericSuperclass.let { type ->
            when (type) {
                null -> fail(kind.nameOf())
                else -> getParameterizedType(type)
            }
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun getParameterizedType(kind: Type): Type {
        return when (kind) {
            is Class<*> -> fail(kind.nameOf())
            is ParameterizedType -> {
                kind.actualTypeArguments.let { list ->
                    if (list.isExhausted()) {
                        fail(kind.nameOf())
                    }
                    list[0]
                }
            }
            else -> fail(kind.nameOf())
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun getParameterizedTypes(kind: Class<*>): List<Type> {
        return kind.genericSuperclass.let { type ->
            when (type) {
                null -> toListOf()
                else -> getParameterizedTypes(type)
            }
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun getParameterizedTypes(kind: Type): List<Type> {
        return when (kind) {
            is Class<*> -> toListOf()
            is ParameterizedType -> kind.actualTypeArguments.toList()
            else -> toListOf()
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun getErasedType(kind: Type): Class<*> {
        return when (kind) {
            is Class<*> -> kind
            is ParameterizedType -> getErasedType(kind.rawType)
            is WildcardType -> getErasedType(kind.lowerBounds.firstOrNull().otherwise(kind.upperBounds.first()))
            is GenericArrayType -> getArrayType(getErasedType(kind.genericComponentType))
            is TypeVariable<*> -> getErasedType(kind.bounds.firstOrNull().otherwise(Unit::class.java))
            else -> Unit::class.java
        }
    }

    @JvmStatic
    @FrameworkDsl
    fun getErasedJavaClass(kind: KType): Class<*> {
        return getErasedKotlinClass(kind).java
    }

    @JvmStatic
    @FrameworkDsl
    fun getErasedKotlinClass(kind: KType): KClass<*> {
        return kind.jvmErasure
    }

    @JvmStatic
    @FrameworkDsl
    private fun getArrayType(kind: Class<*>): Class<*> {
        return newArray(kind, 0).javaClass
    }

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> getArrayOfNull(kind: KClass<T>, size: Int): Array<T?> {
        return getArrayOfNull(kind.java, size)
    }

    @JvmStatic
    @FrameworkDsl
    fun <T : Any> getArrayOfNull(kind: Class<T>, size: Int): Array<T?> {
        return newArray(kind, size) as Array<T?>
    }

    @JvmStatic
    @FrameworkDsl
    private fun newArray(kind: Class<*>, size: Int): Any {
        return java.lang.reflect.Array.newInstance(kind, size.maxOf(0))
    }

    @FrameworkDsl
    override fun toString() = toMapNames().toSafeString()

    @FrameworkDsl
    override fun toMapNames() = dictOf("type" to nameOf())
}