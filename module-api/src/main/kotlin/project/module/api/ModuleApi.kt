package project.module.api

import project.core.ResourceId

data class EphemeralModuleResource(
    val name: String,
)

data class ModuleResource(
    val id: ResourceId,
    val name: String,
)

interface ModuleApi {
    fun save(resource: EphemeralModuleResource): ModuleResource
    fun findById(id: ResourceId): ModuleResource?
}
