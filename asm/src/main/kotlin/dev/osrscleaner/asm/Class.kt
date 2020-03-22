package dev.osrscleaner.asm

import org.objectweb.asm.tree.ClassNode

/**
 * Copyright (c) 2020 Travis Welles
 * Refer to LICENSE.txt for licensing details.
 **/

class Class(val group: ClassGroup, private val node: ClassNode) {

    /**
     * The class name
     */
    val name: String get() = node.name
}