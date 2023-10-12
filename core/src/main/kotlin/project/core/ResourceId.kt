package project.core

import io.hypersistence.tsid.TSID

@JvmInline
value class ResourceId(private val value: String) {
    companion object {
        fun isValid(value: String) = TSID.isValid(value)

        fun generate(): ResourceId = ResourceId(TSID.Factory.getTsid().toLowerCase())
    }

    init {
        require(TSID.isValid(value)) { "Invalid resource identifier '$value'" }
    }

    constructor(value: Long) : this(TSID.from(value).toLowerCase())

    // external representation of the identifier that can be safely shared via an API
    val external: String
        get() = value

    // internal representation of the identifier that must not be shared via an API
    val internal: Long
        get() = TSID.from(value).toLong()
}
