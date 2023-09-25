# :seedling: Spring Boot Kotlin Gradle Seed

Repository demonstrates a production grade setup of Spring Boot application written
in Kotlin and built by Gradle.

# :european_castle: Architecture
The following module setup visualizes the approach one can start with before gradually
transitioning into more distributed setup (e.g. microservices).

![Alt text](modules.png?raw=true "Modules")

## :office: Modularization
The benefits of using the modularized monolith approach include:
* :recycle: faster feedback loop by recompiling only the modules impacted by the introduced changes
* :construction: allows to better understand boundaries and access pattern of each module
* :anger: reduces the likelihood of dealing with a lot of merge conflicts
* :ant: better development experience when working with multiple engineers on the same project
* :mortar_board: benefiting from simplicity of having a single unit of deployment
* :checkered_flag: possibility of compiling, building and testing multiple modules at the same time

## :repeat: Dependencies
The split between compile time and runtime dependencies has the following benefits:
* :sweat_drops: avoids accidentally leaking dependencies between modules
* :rocket: speeds up the compilation by reducing the classpath size

## :tv: Module API
Most of the modules should be shipped in two parts. First part is the API module that will become compile time dependency
of any other module that would like to consume it. Second part is the implementation (e.g. embedded) that will become the
runtime dependency when the application is started. This separation achieves following benefits:
* :shower: clean separation between part of the module that is intended to be used by the client code and internal implementation
* :rocket: faster built time of the whole project when changes impact only the implementation of the module
* :electric_plug: allows to easily swap in/out different implementation (e.g. replace embedded implementation with a call to http service)

## :computer: REST API
Although it may be feasible to divide each module into three distinct components, namely API, implementation,
and protocol, instead of the [recommended](#tv-module-api) two components, this approach may result in an unnecessary
burden for small  to medium-sized projects. Some cross-cutting concerns, such as security, tracing, and monitoring,
benefit from looking  at the REST API as a whole. Moreover, from an API client perspective, it's desirable to interact
with an API that is consistent in terms of naming conventions, versioning and error handling rather than be surprised
that separate parts of the API behave differently. Furthermore, by running tests of multiple modules in parallel, it
is possible to encounter issues with starting servers on the same port, sharing or excessively utilizing system
resources. Therefore, it is recommended to begin with a single REST API module that aggregates and exposes other modules.

## :floppy_disk: Database
There are multiple ways of achieving the separation of database access in the context of multiple modules. On one end of
the spectrum, we have the possibility of connecting to a physically separate database, which eliminates the risk of
accidental coupling (e.g., joins between tables owned by separate modules, transactions spanning across multiple modules).
On the other end of the spectrum, we have solutions more in the spirit of multiplexing, which boils down to
using separate logical databases or schemas. The latter also ensures accidental coupling on the database layer is less
likely, but it's not as strict as the first approach. Since setting up a separate physical database for each module
introduces a lot of overhead, the logical separation seems like a reasonable compromise. However, even this approach
faces plenty of challenges:
* :truck: schema migration and code generation (e.g. Jooq) tools need to support multiple schemas
* :collision: failure to migrate a subset of schemas leads to a lot of issues and needs to be handled with care
* :slot_machine: application needs to handle connecting to, and switching between multiple schemas (in the case of spring multiple schemas
means either using multiple data sources with separate connection pools or even a lower-level approach of setting the
schema on connections acquired from the pool)
* :ticket: application has to specify in context of which schema given part of the request should happen (by either transferring
this information explicitly in method signatures or implicitly storing it in thread local)
* :passport_control: in case of specifying multiple data sources as beans, spring requires the one of them is primary and referencing rest
of them requires qualifier
* :warning: in the case of specifying multiple data sources as beans, spring boot starters that depend on the data source will configure
themselves with just one of them (primary)
* :ocean: in the case of using multiple data sources a care needs to be taken not to overwhelm database with the number of connections

Considering that the primary goal of the separation is the ability to promote modules to become services, it seems like
a huge cost to pay for this flexibility. This leads to the third alternative of using a table (or database object in
general) naming convention (e.g., by adding a prefix to each table) that makes it clear to which domain they belong.
However, the policy of not joining tables belonging to multiple domains or avoiding transactions spanning across
multiple modules won't be easily enforceable. It might require using tools like ArchUnit to discover unwanted
dependencies during build time or SQL execution listeners that would be able to tell which tables are being accessed
and throw an exception if necessary.
