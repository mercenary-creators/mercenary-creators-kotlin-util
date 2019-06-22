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

package co.mercenary.creators.kotlin.util.test

import mu.KLogging

abstract class AbstractLogging : KLogging() {

    fun info(block: () -> Any?) {
        logger.info(block)
    }

    fun info(cause: Throwable, block: () -> Any?) {
        logger.info(cause, block)
    }

    fun warn(block: () -> Any?) {
        logger.warn(block)
    }

    fun warn(cause: Throwable, block: () -> Any?) {
        logger.warn(cause, block)
    }

    fun trace(block: () -> Any?) {
        logger.trace(block)
    }

    fun trace(cause: Throwable, block: () -> Any?) {
        logger.trace(cause, block)
    }

    fun debug(block: () -> Any?) {
        logger.debug(block)
    }

    fun debug(cause: Throwable, block: () -> Any?) {
        logger.debug(cause, block)
    }

    fun error(block: () -> Any?) {
        logger.error(block)
    }

    fun error(cause: Throwable, block: () -> Any?) {
        logger.error(cause, block)
    }
}