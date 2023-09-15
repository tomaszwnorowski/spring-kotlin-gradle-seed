package project.rest

import io.hypersistence.tsid.TSID
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import project.module.api.ModuleApi
import project.module.api.Resource

data class RestResource(
    val id: String,
)

@RestController()
class ResourcesRestController(private val api: ModuleApi) {

    @PostMapping("/public/api/v1/resources")
    fun create(): ResponseEntity<RestResource> =
        ResponseEntity.ok(api.save(Resource(TSID.fast())).toRest())

    @GetMapping("/public/api/v1/resources/{id}")
    fun get(@PathVariable id: String): ResponseEntity<RestResource> =
        if (TSID.isValid(id)) {
            ResponseEntity.ofNullable(api.findById(TSID.from(id))?.toRest())
        } else {
            ResponseEntity.notFound().build()
        }

    private fun Resource.toRest(): RestResource =
        RestResource(id.toLowerCase())

    private fun RestResource.toApi(): Resource =
        Resource(TSID.from(id))
}
