package dev.osrscleaner.app

import com.github.thomasnield.rxkotlinfx.actionEvents
import dev.osrscleaner.app.deob.DeobView
import dev.osrscleaner.app.dialog.NewDeobProjectView
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

    private val randomProjectWord = arrayOf("Deob", "Remap").random()

    /**
     * The root controller
     */
    private val controller: RootController by inject()

    /**
     * The root content view
     */
    override val root = BorderPane()

    init {
        title = "OSRS Cleaner"

        with(root) {
            setPrefSize(1400.0, 900.0)
            top = menubar {
                menu("File") {
                    menu("New Project") {
                        item("New Deob Project").apply { actionEvents().map { Unit }.subscribe(controller.newDeobProject) }
                        item("New Remap Project")
                    }
                    item("Open Project")
                    item("Project Settings") { isDisable = true }
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

                label("File > New $randomProjectWord Project") {
                    font = Font(12.0)
                }
            }
        }

        /**
         * New Deob Project Subscriber
         */
        controller.newDeobProject.subscribe {
            NewDeobProjectView().openModal()
        }

        /**
         * Subscribe to deob view switch observable
         */
        controller.deobViewSwitch.subscribe {
            this.replaceWith<DeobView>()
            println("boom")
        }
    }
}