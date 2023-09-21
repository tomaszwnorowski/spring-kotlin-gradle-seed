package project.module.api

data class CreateResourceCommand(
    val name: String,
)

data class Resource(
    val id: String,
    val name: String,
)

interface ModuleApi {
    fun save(command: CreateResourceCommand): Resource
    fun findById(id: String): Resource?
}
