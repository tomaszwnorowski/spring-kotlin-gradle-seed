package project.module.embedded

import io.hypersistence.tsid.TSID
import org.jooq.DSLContext
import project.core.ResourceId
import project.module.api.EphemeralModuleResource
import project.module.api.ModuleApi
import project.module.api.ModuleResource
import project.module.embedded.jooq.codegen.tables.records.ModuleResourceRecord
import project.module.embedded.jooq.codegen.tables.references.MODULE_RESOURCE

class ModuleEmbedded(private val jooq: DSLContext) : ModuleApi {
    override fun save(resource: EphemeralModuleResource): ModuleResource =
        jooq.insertInto(MODULE_RESOURCE)
            .set(resource.toRecord())
            .returning()
            .fetchSingle { it.toResource() }

    override fun findById(id: ResourceId): ModuleResource? =
        jooq.selectFrom(MODULE_RESOURCE)
            .where(MODULE_RESOURCE.ID.eq(id.toDatabaseId()))
            .fetchOne { it!!.toResource() }

    private fun ResourceId.toDatabaseId(): Long =
        TSID.from(value).toLong()

    private fun EphemeralModuleResource.toRecord(): ModuleResourceRecord =
        ModuleResourceRecord(ResourceId.generate().toDatabaseId(), name)

    private fun ModuleResourceRecord.toResource(): ModuleResource =
        ModuleResource(ResourceId(TSID.from(id!!).toLowerCase()), name!!)
}
