package dev.osrscleaner.app

import org.tinylog.kotlin.Logger
import tornadofx.launch

/**
 * Copyright (c) 2020 Travis Welles
 * Refer to LICENSE.txt for licensing details.
 **/

/**
 * The main static object
 */
object Main {

    /**
     * Main method
     */
    @JvmStatic
    fun main(args: Array<String>) {
        Logger.info("Starting osrs-cleaner application...")
        launch<RootApp>(args)
    }
}