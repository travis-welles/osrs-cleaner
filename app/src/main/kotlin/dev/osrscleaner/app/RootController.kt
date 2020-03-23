package dev.osrscleaner.app

import io.reactivex.subjects.BehaviorSubject
import tornadofx.Controller

/**
 * Copyright (c) 2020 Travis Welles
 * Refer to LICENSE.txt for licensing details.
 **/

/**
 * Responsible for holding the RX Event observables for the root
 * app.
 */
class RootController : Controller() {

    /**
     * The unit observable which the menu entry action subscribes to
     */
    val newDeobProject = BehaviorSubject.create<Unit>()
    val deobViewSwitch = BehaviorSubject.create<Unit>()
}