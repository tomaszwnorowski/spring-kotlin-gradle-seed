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
