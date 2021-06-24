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

package co.mercenary.creators.kotlin.util.collection

import co.mercenary.creators.kotlin.util.*

@FrameworkDsl
@IgnoreForSerialize
object EmptyIterator : ListIterator<Nothing> {

    @FrameworkDsl
    override fun hasNext(): Boolean {
        return false
    }

    @FrameworkDsl
    override fun next(): Nothing {
        fail(nameOf())
    }

    @FrameworkDsl
    override fun hasPrevious(): Boolean {
        return false
    }

    @FrameworkDsl
    override fun nextIndex(): Int {
        return 0
    }

    @FrameworkDsl
    override fun previous(): Nothing {
        fail(nameOf())
    }

    @FrameworkDsl
    override fun previousIndex(): Int {
        return IS_NOT_FOUND
    }
}