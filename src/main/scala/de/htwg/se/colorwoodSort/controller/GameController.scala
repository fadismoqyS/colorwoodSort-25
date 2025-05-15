package de.htwg.se.colorwoodSort.controller

import scala.io.StdIn.readLine
import de.htwg.se.colorwoodSort.model.GameState
import de.htwg.se.colorwoodSort.view.GameView

class GameController(private val gameState: GameState, private val view: GameView) {
  def start(): Unit = {
    view.displayWelcome()
    gameLoop()
  }

  private def gameLoop(): Unit = {
    var running = true
    while (running) {
      view.displayGameState(gameState)
      
      if (gameState.isSolved) {
        view.displayVictory()
        running = false
      } else {
        view.displayMovePrompt()
        val input = readLine()
        
        if (input.toLowerCase == "q") {
          running = false
        } else {
          processMove(input)
        }
      }
    }
  }

  private def processMove(input: String): Unit = {
    val parts = input.trim.split("\\s+")  // Split on any whitespace
    if (parts.length == 2) {
      try {
        val from = parts(0).toInt
        val to = parts(1).toInt
        if (gameState.move(from, to)) {
          view.displaySuccessfulMove()
        } else {
          view.displayInvalidMove()
        }
      } catch {
        case _: NumberFormatException => view.displayInvalidNumber()
      }
    } else {
      view.displayInvalidInput()
    }
  }
} 