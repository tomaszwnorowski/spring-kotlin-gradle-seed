package project.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import project.core.ResourceId
import project.module.api.EphemeralModuleResource
import project.module.api.ModuleApi
import project.module.api.ModuleResource

data class CreateRestResource(
    val name: String,
) {
    fun toEphemeralResource() = EphemeralModuleResource(name)
}

data class RestResource(
    val id: String,
    val name: String,
)

fun ModuleResource.toRest(): RestResource =
    RestResource(id.value, name)

@RestController
@RequestMapping("/public/api/v1/resources")
class ResourcesRestController(private val api: ModuleApi) {

    @PostMapping
    fun create(@RequestBody request: CreateRestResource): ResponseEntity<RestResource> =
        request.toEphemeralResource()
            .let { api.save(it) }
            .let { ResponseEntity.ok(it.toRest()) }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): ResponseEntity<RestResource> =
        id.takeIf { ResourceId.isValid(it) }
            ?.let { api.findById(ResourceId(it)) }
            ?.let { ResponseEntity.ok(it.toRest()) }
            ?: ResponseEntity.notFound().build()
}
