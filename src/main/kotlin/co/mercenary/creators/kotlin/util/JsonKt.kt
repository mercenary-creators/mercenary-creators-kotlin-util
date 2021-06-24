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

@file:kotlin.jvm.JvmName("JsonKt")
@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST", "HttpUrlsUsage")

package co.mercenary.creators.kotlin.util

import co.mercenary.creators.kotlin.util.io.InputStreamSupplier
import co.mercenary.creators.kotlin.util.json.base.*

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JavaType
import com.jayway.jsonpath.TypeRef
import java.io.*
import java.net.*
import java.nio.channels.ReadableByteChannel
import java.nio.file.Path

typealias JSONArray = co.mercenary.creators.kotlin.util.json.base.JSONArray

typealias JSONObject = co.mercenary.creators.kotlin.util.json.base.JSONObject

typealias JSONStatic = co.mercenary.creators.kotlin.util.json.base.JSONStatic

typealias JSONPath = co.mercenary.creators.kotlin.util.json.path.JSONPath

typealias CompiledPath = co.mercenary.creators.kotlin.util.json.path.CompiledPath

typealias PathEvaluationContext = co.mercenary.creators.kotlin.util.json.path.PathEvaluationContext

typealias TypicodePostData = co.mercenary.creators.kotlin.util.json.typicode.TypicodePostData

typealias TypicodeTodoData = co.mercenary.creators.kotlin.util.json.typicode.TypicodeTodoData

typealias TypicodeUserData = co.mercenary.creators.kotlin.util.json.typicode.TypicodeUserData

typealias TypicodePhotoData = co.mercenary.creators.kotlin.util.json.typicode.TypicodePhotoData

typealias TypicodeAlbumData = co.mercenary.creators.kotlin.util.json.typicode.TypicodeAlbumData

typealias TypicodeCommentData = co.mercenary.creators.kotlin.util.json.typicode.TypicodeCommentData

@FrameworkDsl
inline fun <reified T : Any> PathEvaluationContext.eval(path: CharSequence): T = eval(path.copyOf(), object : TypeRef<T>() {})

@FrameworkDsl
inline fun <reified T : Any> PathEvaluationContext.eval(path: CompiledPath): T = eval(path, object : TypeRef<T>() {})

@FrameworkDsl
inline fun <reified T : Any> PathEvaluationContext.read(path: CharSequence): T? = read(path.copyOf(), object : TypeRef<T>() {})

@FrameworkDsl
inline fun <reified T : Any> PathEvaluationContext.read(path: CompiledPath): T? = read(path, object : TypeRef<T>() {})

@FrameworkDsl
inline fun <reified T : Any> toTypeReference(): TypeReference<T> = object : TypeReference<T>() {}

@FrameworkDsl
inline fun <reified T : Any> T.toDeepCopy(): T = JSONStatic.toDeepCopy(this, T::class.java)

@FrameworkDsl
inline fun <reified T : Any, A> JSONAccess<A>.asDataTypeOf(look: A): T? = JSONStatic.asDataTypeOf(accessOf(look), toTypeReference())

@FrameworkDsl
inline fun <reified T : Any> Any.toDataType(): T = JSONStatic.toDataType(this, toTypeReference())

@FrameworkDsl
inline fun <reified T : Any> URI.readOf(): T = JSONStatic.jsonReadOf(this, toTypeReference())

@FrameworkDsl
inline fun <reified T : Any> URL.readOf(): T = JSONStatic.jsonReadOf(this, toTypeReference())

@FrameworkDsl
inline fun <reified T : Any> File.readOf(): T = JSONStatic.jsonReadOf(this, toTypeReference())

@FrameworkDsl
inline fun <reified T : Any> Path.readOf(): T = JSONStatic.jsonReadOf(this, toTypeReference())

@FrameworkDsl
inline fun <reified T : Any> CharSequence.readOf(): T = JSONStatic.jsonReadOf(copyOf(), toTypeReference())

@FrameworkDsl
inline fun <reified T : Any> ByteArray.readOf(): T = JSONStatic.jsonReadOf(toByteArray(true), toTypeReference())

@FrameworkDsl
inline fun <reified T : Any> InputStreamSupplier.readOf(): T = JSONStatic.jsonReadOf(this, toTypeReference())

@FrameworkDsl
inline fun <reified T : Any> ReadableByteChannel.readOf(): T = JSONStatic.jsonReadOf(this, toTypeReference())

@FrameworkDsl
inline fun <reified T : Any> Reader.readOf(done: Boolean = true): T = JSONStatic.jsonReadOf(this, toTypeReference(), done)

@FrameworkDsl
inline fun <reified T : Any> InputStream.readOf(done: Boolean = true): T = JSONStatic.jsonReadOf(this, toTypeReference(), done)

@FrameworkDsl
inline fun <reified T : Any> URI.toJSONReader(): JSONReader<T> = JSONStatic.toJSONReader(this, toTypeReference())

@FrameworkDsl
inline fun <reified T : Any> URL.toJSONReader(): JSONReader<T> = JSONStatic.toJSONReader(this, toTypeReference())

@FrameworkDsl
inline fun <reified T : Any> File.toJSONReader(): JSONReader<T> = JSONStatic.toJSONReader(this, toTypeReference())

@FrameworkDsl
inline fun <reified T : Any> Path.toJSONReader(): JSONReader<T> = JSONStatic.toJSONReader(this, toTypeReference())

@FrameworkDsl
inline fun <reified T : Any> CharSequence.toJSONReader(): JSONReader<T> = JSONStatic.toJSONReader(copyOf(), toTypeReference())

@FrameworkDsl
inline fun <reified T : Any> ByteArray.toJSONReader(): JSONReader<T> = JSONStatic.toJSONReader(toByteArray(true), toTypeReference())

@FrameworkDsl
inline fun <reified T : Any> InputStreamSupplier.toJSONReader(): JSONReader<T> = JSONStatic.toJSONReader(this, toTypeReference())

@FrameworkDsl
inline fun <reified T : Any> ReadableByteChannel.toJSONReader(): JSONReader<T> = JSONStatic.toJSONReader(this, toTypeReference())

@FrameworkDsl
inline fun <reified T : Any> Reader.toJSONReader(done: Boolean = true): JSONReader<T> = JSONStatic.toJSONReader(this, toTypeReference(), done)

@FrameworkDsl
inline fun <reified T : Any> InputStream.toJSONReader(done: Boolean = true): JSONReader<T> = JSONStatic.toJSONReader(this, toTypeReference(), done)

@FrameworkDsl
fun getJSONFormatter(pretty: Boolean = true): JSONFormatter {
    return JSONStatic.getFormatter(pretty)
}

@FrameworkDsl
fun CharSequence.toTypicodePath(secure: Boolean): String = when (secure.isNotTrue()) {
    true -> "http://jsonplaceholder.typicode.com/${removePrefix("/")}"
    else -> "https://jsonplaceholder.typicode.com/${removePrefix("/")}"
}

@FrameworkDsl
fun Any.toJavaTypeOf(): JavaType = JSONStatic.toJavaType(this)

@FrameworkDsl
fun json() = JSONObject()

@FrameworkDsl
fun json(args: Map<String, Maybe>) = JSONObject(args)

@FrameworkDsl
fun json(args: Pair<String, Maybe>) = JSONObject(args)

@FrameworkDsl
fun json(vararg args: Pair<String, Maybe>) = JSONObject(args.mapTo())

@FrameworkDsl
fun json(args: Iterable<Pair<String, Maybe>>) = JSONObject(args.mapTo())

@FrameworkDsl
fun json(args: Iterator<Pair<String, Maybe>>) = JSONObject(args.mapTo())

@FrameworkDsl
fun json(args: Sequence<Pair<String, Maybe>>) = JSONObject(args.mapTo())

@FrameworkDsl
operator fun JSONObject.plus(args: Map<String, Maybe>): JSONObject {
    return if (isEmpty()) JSONObject(args) else JSONObject(this).append(args)
}

@FrameworkDsl
operator fun JSONObject.plus(args: Pair<String, Maybe>): JSONObject {
    return if (isEmpty()) JSONObject(args) else JSONObject(this).append(args)
}

@FrameworkDsl
operator fun JSONObject.plus(args: Iterator<Pair<String, Maybe>>): JSONObject {
    return if (isEmpty()) JSONObject(args) else JSONObject(this).append(args)
}

@FrameworkDsl
operator fun JSONObject.plus(args: Iterable<Pair<String, Maybe>>): JSONObject {
    return if (isEmpty()) JSONObject(args) else JSONObject(this).append(args)
}

@FrameworkDsl
operator fun JSONObject.plus(args: Sequence<Pair<String, Maybe>>): JSONObject {
    return if (isEmpty()) JSONObject(args) else JSONObject(this).append(args)
}

@FrameworkDsl
operator fun JSONObject.plus(args: Array<out Pair<String, Maybe>>): JSONObject {
    return if (isEmpty()) JSONObject(args.mapTo()) else JSONObject(this).append(args.mapTo())
}

@FrameworkDsl
inline operator fun JSONObject.plusAssign(args: Map<String, Maybe>) {
    if (args.isNotExhausted()) {
        append(args)
    }
}

@FrameworkDsl
inline operator fun JSONObject.plusAssign(args: Pair<String, Maybe>) {
    append(args)
}

@FrameworkDsl
inline operator fun JSONObject.plusAssign(args: Iterator<Pair<String, Maybe>>) {
    if (args.isNotExhausted()) {
        append(args)
    }
}

@FrameworkDsl
inline operator fun JSONObject.plusAssign(args: Iterable<Pair<String, Maybe>>) {
    if (args.isNotExhausted()) {
        append(args)
    }
}

@FrameworkDsl
inline operator fun JSONObject.plusAssign(args: Sequence<Pair<String, Maybe>>) {
    if (args.isNotExhausted()) {
        append(args)
    }
}

@FrameworkDsl
inline operator fun JSONObject.plusAssign(args: Array<out Pair<String, Maybe>>) {
    if (args.isNotExhausted()) {
        append(args.mapTo())
    }
}

@FrameworkDsl
operator fun JSONObject.minus(args: String): JSONObject {
    return if (isEmpty()) JSONObject() else JSONObject(this).also { self ->
        self.undefine(args)
    }
}

@FrameworkDsl
operator fun JSONObject.minus(args: Iterator<String>): JSONObject {
    return if (isEmpty()) JSONObject() else JSONObject(this).also { self ->
        self.undefine(args)
    }
}

@FrameworkDsl
operator fun JSONObject.minus(args: Iterable<String>): JSONObject {
    return if (isEmpty()) JSONObject() else JSONObject(this).also { self ->
        self.undefine(args)
    }
}

@FrameworkDsl
operator fun JSONObject.minus(args: Sequence<String>): JSONObject {
    return if (isEmpty()) JSONObject() else JSONObject(this).also { self ->
        self.undefine(args)
    }
}

@FrameworkDsl
operator fun JSONObject.minus(args: Array<out String>): JSONObject {
    return if (isEmpty()) JSONObject() else JSONObject(this).also { self ->
        self.undefine(args)
    }
}

@FrameworkDsl
inline operator fun JSONObject.minusAssign(args: String) {
    undefine(args)
}

@FrameworkDsl
inline operator fun JSONObject.minusAssign(args: Iterator<String>) {
    if (args.isNotExhausted()) {
        undefine(args)
    }
}

@FrameworkDsl
inline operator fun JSONObject.minusAssign(args: Iterable<String>) {
    if (args.isNotExhausted()) {
        undefine(args)
    }
}

@FrameworkDsl
inline operator fun JSONObject.minusAssign(args: Sequence<String>) {
    if (args.isNotExhausted()) {
        undefine(args)
    }
}

@FrameworkDsl
inline operator fun JSONObject.minusAssign(args: Array<out String>) {
    if (args.isNotExhausted()) {
        undefine(args)
    }
}

@FrameworkDsl
fun <T> MutableKeysContainer<T>.undefine(args: T) {
    keysOf().remove(args)
}

@FrameworkDsl
fun <T> MutableKeysContainer<T>.undefine(args: Iterable<T>) {
    if (args.isNotExhausted()) {
        keysOf().removeAll(args)
    }
}

@FrameworkDsl
fun <T> MutableKeysContainer<T>.undefine(args: Iterator<T>) {
    if (args.isNotExhausted()) {
        keysOf().removeAll(args.toSequence())
    }
}

@FrameworkDsl
fun <T> MutableKeysContainer<T>.undefine(args: Sequence<T>) {
    if (args.isNotExhausted()) {
        keysOf().removeAll(args)
    }
}

@FrameworkDsl
fun <T> MutableKeysContainer<T>.undefine(args: Array<out T>) {
    if (args.isNotExhausted()) {
        keysOf().removeAll(args)
    }
}

typealias Emoji = co.mercenary.creators.kotlin.util.json.text.emoji.Emoji

typealias EmojiManager = co.mercenary.creators.kotlin.util.json.text.emoji.EmojiManager

typealias FitzpatrickExtensionType = co.mercenary.creators.kotlin.util.json.text.emoji.FitzpatrickExtensionType

typealias FitzpatrickExtensionCode = Int

@FrameworkDsl
inline fun FitzpatrickExtensionCode.isValid(): Boolean = isBetween(1, 6)

@FrameworkDsl
inline fun FitzpatrickExtensionCode.isNotValid(): Boolean = isValid().isNotTrue()

@FrameworkDsl
inline fun FitzpatrickExtensionCode.toFitzpatrickExtensionType(): FitzpatrickExtensionType = FitzpatrickExtensionType.fromCode(this)

@FrameworkDsl
inline fun getEmojiManager() = EmojiManager

@FrameworkDsl
fun CharSequence.isEmoji(): Boolean = getEmojiManager().isEmoji(this)

@FrameworkDsl
inline fun CharSequence.isNotEmoji(): Boolean = isEmoji().isNotTrue()

@FrameworkDsl
inline fun Emoji.isNotFitzpatrick(): Boolean = isFitzpatrick().isNotTrue()



