package dev.osrscleaner.app

import dev.osrscleaner.asm.ClassGroup
import java.io.File

/**
 * Copyright (c) 2020 Travis Welles
 * Refer to LICENSE.txt for licensing details.
 **/

class ProjectModel(val projectName: String) {

    /**
     * the source JAR file for the project.
     */
    lateinit var sourceJar: File

    /**
     * The reference JAR file for the project.
     */
    lateinit var referenceJar: File


    /**
     * The type of project.
     */
    var projectType: ProjectType = ProjectType.DEOB

    /**
     * The source JAR ASM group
     */
    val sourceGroup = ClassGroup()

    /**
     * The reference JAR ASM group.
     */
    val referenceGroup = ClassGroup()

    enum class ProjectType {
        DEOB,
        REMAP;
    }
}