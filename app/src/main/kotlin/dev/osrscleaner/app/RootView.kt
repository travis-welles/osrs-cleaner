package dev.osrscleaner.app

import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.scene.text.Font
import tornadofx.*
import kotlin.system.exitProcess

/**
 * Copyright (c) 2020 Travis Welles
 * Refer to LICENSE.txt for licensing details.
 **/

/**
 * Represents the root window view.
 */
class RootView : View() {

    /**
     * The root content view
     */
    override val root = BorderPane()

    init {
        title = "OSRS Cleaner"

        with(root) {
            setPrefSize(1200.0, 900.0)
            top = menubar {
                menu("File") {
                    item("New Deob Project")
                    item("New Remap Project")
                    item("Open Project")
                    item("Close Project") { isDisable = true }
                    separator()
                    item("Save Project") { isDisable = true }
                    item("Save Project As") { isDisable = true }
                    item("Export") { isDisable = true }
                    separator()
                    item("Exit").action { exitProcess(0) }
                }
            }

            center = vbox {
                alignment = Pos.CENTER
                label("Create / Open a Project") {
                    font = Font(16.0)
                }

                label("File > New Project") {
                    font = Font(12.0)
                }
            }
        }
    }
}