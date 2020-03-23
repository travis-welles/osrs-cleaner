package dev.osrscleaner.app

import javafx.scene.image.Image
import tornadofx.App
import tornadofx.setStageIcon

/**
 * Copyright (c) 2020 Travis Welles
 * Refer to LICENSE.txt for licensing details.
 **/

/**
 * Represents the root application.
 */
class RootApp : App(RootView::class) {

    init {
        setStageIcon(Image("icon.png"))
    }
}