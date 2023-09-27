package project.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import project.module.api.CreateResourceCommand
import project.module.api.ModuleApi
import project.module.api.Resource

data class RestCreateResource(
    val name: String,
) {
    fun toCommand() = CreateResourceCommand(name)
}

data class RestResource(
    val id: String,
    val name: String,
)

fun Resource.toRest(): RestResource =
    RestResource(id, name)

@RestController
@RequestMapping("/public/api/v1/resources")
class ResourcesRestController(private val api: ModuleApi) {

    @PostMapping
    fun create(@RequestBody request: RestCreateResource): ResponseEntity<RestResource> =
        ResponseEntity.ok(api.save(request.toCommand()).toRest())

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): ResponseEntity<RestResource> =
        ResponseEntity.ofNullable(api.findById(id)?.toRest())
}
