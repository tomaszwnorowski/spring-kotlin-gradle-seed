package project.module.embedded

import io.hypersistence.tsid.TSID
import org.jooq.DSLContext
import project.module.api.ModuleApi
import project.module.api.Resource
import project.module.embedded.jooq.codegen.tables.records.ResourceRecord
import project.module.embedded.jooq.codegen.tables.references.RESOURCE

class ModuleEmbedded(private val jooq: DSLContext) : ModuleApi {
    override fun save(resource: Resource): Resource =
        jooq.insertInto(RESOURCE)
            .set(resource.toRecord())
            .returning()
            .fetchSingle { it.toApi() }

    override fun findById(id: TSID): Resource? =
        jooq.selectFrom(RESOURCE)
            .where(RESOURCE.ID.eq(id.toLong()))
            .fetchOne { it.toApi() }

    private fun Resource.toRecord(): ResourceRecord =
        ResourceRecord(id.toLong())

    private fun ResourceRecord.toApi(): Resource =
        Resource(TSID.from(this.id!!))
}
