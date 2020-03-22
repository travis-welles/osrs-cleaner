package dev.osrscleaner.app.dialog

import com.github.thomasnield.rxkotlinfx.actionEvents
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.control.TextField
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File

/**
 * Copyright (c) 2020 Travis Welles
 * Refer to LICENSE.txt for licensing details.
 **/

/**
 * Represents the screen window for creating a new deob project.
 */
class NewDeobProjectView : View("New Project") {

    /**
     * The project model from field input.
     */
    private val model = object : ViewModel() {
        val projectName = bind { SimpleStringProperty() }
        val sourceJar = bind { SimpleObjectProperty<File>() }
    }

    /**
     * The source jar file observable
     */
    private val sourceJarFile = BehaviorSubject.create<File>()

    /**
     * The root form.
     */
    override val root = form {
        setPrefSize(500.0, 150.0)


        fieldset("Project Name") {
            textfield(model.projectName)
        }

        fieldset("Source Jar") {
            hbox(0) {
                textfield {
                    prefWidth = 400.0
                    isDisable = true

                    sourceJarFile.subscribeBy(
                        onNext = {
                            text = it.absolutePath
                            model.sourceJar.value = it
                        }
                    )
                }

                button("Choose").apply {
                    actionEvents()
                        .map { chooseFile("Choose Jar File", arrayOf(FileChooser.ExtensionFilter("Jar Files", "*.jar"))) }
                        .map { it.first() }
                        .subscribe(sourceJarFile)
                }
            }
        }

        hbox(10) {
            alignment = Pos.CENTER_RIGHT

            button("Cancel").action { close() }
            button("Finish")
        }
    }
}