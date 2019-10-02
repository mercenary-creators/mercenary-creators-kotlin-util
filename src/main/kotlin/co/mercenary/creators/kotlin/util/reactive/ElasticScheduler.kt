/*
 * Copyright (c) 2019, Mercenary Creators Company. All rights reserved.
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

package co.mercenary.creators.kotlin.util.reactive

import reactor.core.scheduler.*

class ElasticScheduler private constructor(private val proxy: Scheduler) : Scheduler by proxy {
    @JvmOverloads
    constructor(name: String, live: Int = DEFAULT_TIME_TO_LIVE_SECONDS, daemon: Boolean = false) : this(Schedulers.newElastic(name, live, daemon))

    companion object {
        const val DEFAULT_TIME_TO_LIVE_SECONDS = 60
    }
}