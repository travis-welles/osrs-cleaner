package dev.osrscleaner.app.dialog

import com.github.thomasnield.rxkotlinfx.actionEvents
import dev.osrscleaner.app.Main
import dev.osrscleaner.app.ProjectModel
import dev.osrscleaner.app.util.JarUtils
import dev.osrscleaner.app.util.ProgressView
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.Label
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
     * The error message label
     */
    private var errorLabel: Label by singleAssign()

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
    private val finish = BehaviorSubject.create<Unit>()

    private val progressView: ProgressView by inject()

    /**
     * The root form.
     */
    override val root = form {
        setPrefSize(500.0, 150.0)

        label {
            isVisible = false
            errorLabel = this
        }

        fieldset("Project Name") {
            textfield(model.projectName)
        }

        fieldset("Source Jar") {
            hbox(0) {
                textfield {
                    prefWidth = 400.0
                    isEditable = false

                    sourceJarFile.subscribeBy(
                        onNext = {
                            text = it.absolutePath
                            model.sourceJar.value = it
                        },

                        onError =  { alert(Alert.AlertType.ERROR, "OH SNAP!", it.message ?: "") }
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
            button("Finish").apply { actionEvents().map { Unit }.subscribe(finish) }
        }
    }

    init {

        /**
         * Finish button subscriber.
         */
        finish.subscribe {
            val projectModel = ProjectModel(model.projectName.value)
            projectModel.projectType = ProjectModel.ProjectType.DEOB
            projectModel.sourceJar = model.sourceJar.value

            /**
             * Set the loaded project as the new [ProjectModel]
             */
            Main.loadedProject = projectModel

            close()

            /**
             * Invoke the loading of the source JAR into the sourceGroup within the project model.
             */
            progressView.openModal()
            progressView.runAsync {
                JarUtils.loadJar(
                    projectModel.sourceJar,
                    projectModel.sourceGroup,
                    this,
                    "Loading Jar file classes..."
                )
            }
        }

    }
}