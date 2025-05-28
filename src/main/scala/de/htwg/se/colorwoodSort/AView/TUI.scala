package de.htwg.se.colorwoodSort.AView

import de.htwg.se.colorwoodSort.model.GameState
import de.htwg.se.colorwoodSort.controller.GameController

class TUI(gameState: GameState) {
  private val view = new GameView()
  private val controller = new GameController(gameState)
  
  // Registriere die View als Observer beim Controller
  controller.addObserver(view)
  // Setze den Controller in der View
  view.setController(controller)

  def start(): Unit = {
    view.displayWelcome()
    controller.start()
  }
} 