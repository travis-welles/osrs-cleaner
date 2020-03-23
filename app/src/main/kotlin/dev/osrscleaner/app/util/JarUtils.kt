package dev.osrscleaner.app.util

import dev.osrscleaner.asm.ClassGroup
import tornadofx.FXTask
import java.io.File
import java.util.jar.JarFile
import kotlin.streams.asSequence

/**
 * Copyright (c) 2020 Travis Welles
 * Refer to LICENSE.txt for licensing details.
 **/

/**
 * Provides useful utilites for loading JAR files and extracting
 * the bytecode from individual classes.
 */
object JarUtils {

    /**
     * Loads a JAR file into an ASM class group.
     * @param file The input JAR file to load.
     * @param group The [ClassGroup] to load the classes into.
     * @param task The ASYC [FXTask] which has a progress bar to be updated.
     */
    fun loadJar(file: File, group: ClassGroup, task: FXTask<*>? = null, message: String? = null) {
        JarFile(file).use { jar ->
            val entries = jar.entries()

            var step = 0L
            val maxSteps = jar.stream().iterator().asSequence()
                .filter { it.name.endsWith(".class") }
                .count()
                .toLong()

            task?.updateMessage(message ?: "Please Wait...")
            task?.updateProgress(step, maxSteps)

            while(entries.hasMoreElements()) {
                val entry = entries.nextElement()

                if(entry.name.endsWith(".class")) {
                    val inputStream = jar.getInputStream(entry)
                    group.loadClass(inputStream)
                    task?.updateProgress(++step, maxSteps)
                }
            }

            jar.close()
        }
    }
}