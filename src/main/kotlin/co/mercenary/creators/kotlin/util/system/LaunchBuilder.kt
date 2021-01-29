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

package co.mercenary.creators.kotlin.util.system

import co.mercenary.creators.kotlin.util.*
import co.mercenary.creators.kotlin.util.collection.StringArrayList
import co.mercenary.creators.kotlin.util.io.*
import co.mercenary.creators.kotlin.util.io.EmptyOutputStream
import co.mercenary.creators.kotlin.util.time.TimeDuration
import java.io.*
import java.nio.file.*
import java.util.*
import java.util.concurrent.TimeUnit

@IgnoreForSerialize
class LaunchBuilder @FrameworkDsl constructor(args: Iterator<String>) : Builder<LaunchRunner> {

    @FrameworkDsl
    constructor(args: String) : this(iteratorOf(args))

    @FrameworkDsl
    constructor(vararg args: String) : this(args.toIterator())

    @FrameworkDsl
    private val envp = dict()

    @FrameworkDsl
    private var file = file()

    @FrameworkDsl
    private val argv = argv().append(args)

    @FrameworkDsl
    fun directory(home: File): LaunchBuilder {
        if (home.isDirectory) {
            file = home
        }
        return this
    }

    @FrameworkDsl
    fun directory(home: Path): LaunchBuilder {
        return directory(home.fileOf())
    }

    @FrameworkDsl
    fun directory(home: String): LaunchBuilder {
        if (home.isNotEmptyOrBlank()) {
            return directory(home.pathOf())
        }
        return this
    }

    @FrameworkDsl
    fun environment(args: Properties): LaunchBuilder {
        if (args.isNotExhausted()) {
            return environment(args.toStringDictionary())
        }
        return this
    }

    @FrameworkDsl
    fun environment(args: Map<String, String>): LaunchBuilder {
        envp.append(args)
        return this
    }

    @FrameworkDsl
    fun environment(args: Iterator<Pair<String, String>>): LaunchBuilder {
        envp.append(args)
        return this
    }

    @FrameworkDsl
    fun environment(args: Iterable<Pair<String, String>>): LaunchBuilder {
        envp.append(args)
        return this
    }

    @FrameworkDsl
    fun environment(args: Sequence<Pair<String, String>>): LaunchBuilder {
        envp.append(args)
        return this
    }

    @FrameworkDsl
    fun environment(pair: Pair<String, String>, vararg args: Pair<String, String>): LaunchBuilder {
        envp.append(pair).append(args.toIterator())
        return this
    }

    @FrameworkDsl
    fun arguments(head: String, vararg args: String): LaunchBuilder {
        argv.append(head).append(args.toIterator())
        return this
    }

    @FrameworkDsl
    fun arguments(args: Iterator<String>): LaunchBuilder {
        argv.append(args)
        return this
    }

    @FrameworkDsl
    fun arguments(args: Iterable<String>): LaunchBuilder {
        argv.append(args)
        return this
    }

    @FrameworkDsl
    fun arguments(args: Sequence<String>): LaunchBuilder {
        argv.append(args)
        return this
    }

    @FrameworkDsl
    override fun build(): LaunchRunner {
        val dict = dict()
        val make = proc(argv.toIterator(), file)
        val maps = make.environment()
        for ((k, v) in envp) {
            maps[k] = v
        }
        for ((k, v) in maps) {
            dict[k] = v
        }
        if (dict.isEmpty()) {
            dict.append(envp)
        }
        return ProcessLaunchRunner(make, dict.copyOf())
    }

    companion object {

        @JvmStatic
        @FrameworkDsl
        internal fun argv(): StringArrayList = StringArrayList()

        @JvmStatic
        @FrameworkDsl
        internal fun dict(): StringDictionary = StringDictionary()

        @JvmStatic
        @FrameworkDsl
        internal fun file(): File? = null

        @JvmStatic
        @FrameworkDsl
        internal fun proc(list: Iterator<String>, file: File?) = ProcessFactory(list, file).build()

        @IgnoreForSerialize
        internal class ProcessFactory @FrameworkDsl constructor(private val args: Iterator<String>, private val file: File?) : Builder<ProcessBuilder> {

            @FrameworkDsl
            override fun build(): ProcessBuilder {
                return ProcessBuilder(args.toNoEmptyChars()).also { if (file != null) it.directory(file) }
            }
        }

        @IgnoreForSerialize
        internal class ProcessLaunchRunner @FrameworkDsl constructor(private val process: Process, private val envmap: StringDictionary, private val command: Iterator<String>) : LaunchRunner {

            @FrameworkDsl
            constructor(make: ProcessBuilder, envmap: StringDictionary) : this(make.start(), envmap, make.command().toIterator().toNoEmptyChars().toIterator())

            @FrameworkDsl
            private val open = getAtomicTrue()

            @FrameworkDsl
            private val code = LaunchRunner.UNKNOWN_CODE.toAtomic()

            override fun toMapNames() = dictOf("open" to isOpen(), "alive" to isAlive())

            @FrameworkDsl
            @IgnoreForSerialize
            override fun isOpen(): Boolean {
                return open.isTrue()
            }

            @FrameworkDsl
            override fun close() {
                if (open.isTrueToFalse()) {
                    if (isAlive()) {
                        destroy(true)
                    }
                }
            }

            @FrameworkDsl
            @IgnoreForSerialize
            override fun getInputStream(): InputStream {
                return try {
                    process.inputStream.otherwise {
                        EmptyInputStream
                    }
                } catch (cause: Throwable) {
                    Throwables.fatal(cause) {
                        EmptyInputStream
                    }
                }
            }

            @FrameworkDsl
            @IgnoreForSerialize
            override fun getOutputStream(): OutputStream {
                return try {
                    process.outputStream.otherwise {
                        EmptyOutputStream
                    }
                } catch (cause: Throwable) {
                    Throwables.fatal(cause) {
                        EmptyOutputStream
                    }
                }
            }

            @FrameworkDsl
            @IgnoreForSerialize
            override fun getErrorStream(): InputStream {
                return try {
                    process.errorStream.otherwise {
                        EmptyInputStream
                    }
                } catch (cause: Throwable) {
                    Throwables.fatal(cause) {
                        EmptyInputStream
                    }
                }
            }

            @FrameworkDsl
            override fun codeOf(): Int {
                if (isUnknownCode(code.getValue())) {
                    try {
                        code.setValue(process.exitValue())
                    } catch (cause: Throwable) {
                        Throwables.check(cause)
                    }
                }
                return code.getValue()
            }

            @FrameworkDsl
            override fun waitOn(): Int {
                if (isUnknownCode(code.getValue())) {
                    try {
                        code.setValue(process.waitFor())
                    } catch (cause: Throwable) {
                        Throwables.check(cause)
                    }
                }
                return code.getValue()
            }

            @FrameworkDsl
            override fun waitOn(time: TimeDuration): Boolean {
                if (time.isEmpty()) {
                    return true
                }
                return try {
                    process.waitFor(time.getNanoSeconds(), TimeUnit.NANOSECONDS)
                } catch (cause: Throwable) {
                    Throwables.fatal(cause, false)
                }
            }

            @FrameworkDsl
            override fun destroy(force: Boolean): Boolean {
                try {
                    process.destroy()
                    if (force.isTrue() && isAlive()) {
                        process.destroyForcibly()
                    }
                } catch (cause: Throwable) {
                    Throwables.check(cause)
                }
                return isNotAlive()
            }

            @FrameworkDsl
            @IgnoreForSerialize
            override fun getCommandLine(): List<String> {
                return command.toNoEmptyChars()
            }

            @FrameworkDsl
            @IgnoreForSerialize
            override fun getEnvironment(): Map<String, String> {
                return envmap.copyOf()
            }

            @FrameworkDsl
            @IgnoreForSerialize
            override fun isAlive(): Boolean {
                return process.isAlive.isTrue()
            }
        }
    }
}