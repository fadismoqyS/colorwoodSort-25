package de.htwg.se.colorwoodSort.view

import de.htwg.se.colorwoodSort.model.GameState
import de.htwg.se.colorwoodSort.controller.GameController

class TUI(gameState: GameState) {
  private val view = GameView()  // Use companion object to create instance
  private val controller = new GameController(gameState, view)

  def start(): Unit = {
    controller.start()
  }
} 