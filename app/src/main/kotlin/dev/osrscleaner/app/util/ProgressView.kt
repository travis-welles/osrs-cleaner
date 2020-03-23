package dev.osrscleaner.app.util

import tornadofx.*

/**
 * Copyright (c) 2020 Travis Welles
 * Refer to LICENSE.txt for licensing details.
 **/

class ProgressView : View("Operation Progress") {

    private val task: TaskStatus by inject()

    override val root = vbox {
        paddingAll = 5
        visibleWhen { task.running }

        label(task.message)
        progressbar(task.progress) {
            prefWidth = 400.0
        }
    }

    init {
        task.completed.onChange {
            if(it) close()
        }
    }
}