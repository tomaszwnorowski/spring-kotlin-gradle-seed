package project.module.api

import io.hypersistence.tsid.TSID

data class Resource(
    val id: TSID,
)

interface ModuleApi {
    fun save(resource: Resource): Resource
    fun findById(id: TSID): Resource?
}
