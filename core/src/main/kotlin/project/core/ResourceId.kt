package project.core

import io.hypersistence.tsid.TSID

@JvmInline
value class ResourceId(val value: String) {
    companion object {
        fun isValid(value: String) = TSID.isValid(value)

        fun generate(): ResourceId = ResourceId(TSID.Factory.getTsid().toLowerCase())
    }

    init {
        require(TSID.isValid(value)) { "Invalid resource identifier '$value'" }
    }
}
