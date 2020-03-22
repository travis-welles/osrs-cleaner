package dev.osrscleaner.app.util

import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import tornadofx.*

/**
 * Copyright (c) 2020 Travis Welles
 * Refer to LICENSE.txt for licensing details.
 **/

class ProgressTaskWindow : View("Operation progress") {

    private var statusMessage: Label by singleAssign()
    private var progressBar: ProgressBar by singleAssign()

    private val task: TaskStatus by inject()

    override val root = vbox {
        paddingAll = 10

        label("Please Wait...") {
            statusMessage = this
        }
        progressbar {
            prefWidth = 400.0
            progress = 0.0
            progressBar = this
        }

        task.completed.onChange {
            if(it) {
                close()
            }
        }
    }
}