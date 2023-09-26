package project.module.embedded

import io.hypersistence.tsid.TSID
import org.jooq.DSLContext
import project.module.api.CreateResourceCommand
import project.module.api.ModuleApi
import project.module.api.Resource
import project.module.embedded.jooq.codegen.tables.records.ModuleResourceRecord
import project.module.embedded.jooq.codegen.tables.references.MODULE_RESOURCE

private val NOT_FOUND = null

class ModuleEmbedded(private val jooq: DSLContext) : ModuleApi {
    override fun save(command: CreateResourceCommand): Resource =
        jooq.insertInto(MODULE_RESOURCE)
            .set(command.toRecord())
            .returning()
            .fetchSingle { it.toResource() }

    override fun findById(id: String): Resource? =
        if (TSID.isValid(id)) {
            findByTsid(TSID.from(id))
        } else {
            NOT_FOUND
        }

    private fun findByTsid(id: TSID): Resource? =
        jooq.selectFrom(MODULE_RESOURCE)
            .where(MODULE_RESOURCE.ID.eq(id.toLong()))
            .fetchOne { it!!.toResource() }

    private fun CreateResourceCommand.toRecord(): ModuleResourceRecord =
        ModuleResourceRecord(TSID.fast().toLong(), name)

    private fun ModuleResourceRecord.toResource(): Resource =
        Resource(TSID.from(this.id!!).toLowerCase(), name!!)
}
