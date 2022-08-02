
package co.mercenary.creators.kotlin.util.io

import co.mercenary.creators.kotlin.util.*

@IgnoreForSerialize
abstract class AbstractCachedContentResource @JvmOverloads constructor(data: ByteArray, path: String, type: String = DEFAULT_CONTENT_TYPE, time: Long = getTimeStamp(), sort: String = EMPTY_STRING) : AbstractContentResourceBase(path, type, time), CachedContentResource {

    @FrameworkDsl
    private val kind = sort.copyOf()

    @FrameworkDsl
    private val save = data.toByteArray()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentKind() = kind.copyOf()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentData() = save.toByteArray()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getInputStream() = save.toInputStream()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun getContentSize() = save.toContentSize()

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isContentThere() = true

    @FrameworkDsl
    @IgnoreForSerialize
    override fun isContentCache() = true

    @FrameworkDsl
    override fun toContentCache() = this

    @FrameworkDsl
    override fun hashCode() = save.hashOf() * HASH_NEXT_VALUE + super.hashCode()

    @FrameworkDsl
    override fun equals(other: Any?) = when (other) {
        is AbstractCachedContentResource -> this === other || getContentPath() == other.getContentPath() && save isSameArrayAs other.save
        else -> false
    }
}