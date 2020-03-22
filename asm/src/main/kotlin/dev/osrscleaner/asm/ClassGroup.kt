package dev.osrscleaner.asm

import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode
import java.io.InputStream

/**
 * Copyright (c) 2020 Travis Welles
 * Refer to LICENSE.txt for licensing details.
 **/

class ClassGroup {

    /**
     * The backing store of classes.
     */
    private val classes = hashSetOf<Class>()

    val size: Int get() = classes.size

    operator fun get(name: String): Class? = classes.find { it.name == name }

    fun first(predicate: (Class) -> Boolean) = classes.first(predicate)

    fun firstOrNull(predicate: (Class) -> Boolean) = classes.firstOrNull(predicate)

    fun forEach(action: (Class) -> Unit) = classes.forEach(action)

    fun <T> map(transform: (Class) -> T): List<T> = classes.map(transform)

    /**
     * Loads a class from the class's input stream.
     */
    fun loadClass(inputStream: InputStream) {
        val node = ClassNode()
        val reader = ClassReader(inputStream)
        reader.accept(node, ClassReader.SKIP_FRAMES or ClassReader.SKIP_DEBUG)
        classes.add(Class(this, node))
    }
}