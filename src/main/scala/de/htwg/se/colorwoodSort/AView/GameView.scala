package de.htwg.se.colorwoodSort.AView

import de.htwg.se.colorwoodSort.util.Observer
import de.htwg.se.colorwoodSort.controller.GameController

class GameView extends Observer {
  private var controller: GameController = _

  def setController(controller: GameController): Unit = {
    this.controller = controller
  }

  def update(): Unit = {
    if (controller != null) {
      displayGameState()
      
      if (!controller.isLastInputValid) {
        displayInvalidInput()
      } else if (!controller.isLastNumberValid) {
        displayInvalidNumber()
      } else if (!controller.isLastMoveValid || !controller.isLastMoveSuccessful) {
        displayInvalidMove()
      } else if (controller.isLastMoveSuccessful) {
        displaySuccessfulMove()
      }
      
      if (controller.isGameSolved) {
        displayVictory()
      } else {
        displayMovePrompt()
      }
    }
  }

  def displayWelcome(): Unit = {
    println("Willkommen zum Colorwood Sort Spiel!")
    println("Verwende 'q' zum Beenden")
    println("Format für Züge: 'from to' (z.B. '0 1' um von Tube 0 nach Tube 1 zu bewegen)")
  }

  private def displayGameState(): Unit = {
    println("\nAktueller Spielstand:")
    println(controller.getGameState)
  }

  def displayVictory(): Unit = {
    println("\nGlückwunsch! Du hast das Spiel gelöst!")
  }

  def displayMovePrompt(): Unit = {
    print("\nDein Zug (from to): ")
  }

  def displayInvalidMove(): Unit = {
    println("Ungültiger Zug! Bitte versuche es erneut.")
  }

  def displayInvalidInput(): Unit = {
    println("Ungültige Eingabe! Bitte verwende das Format 'from to'")
  }

  def displayInvalidNumber(): Unit = {
    println("Ungültige Eingabe! Zahlen erwartet.")
  }

  def displaySuccessfulMove(): Unit = {
    println("Zug erfolgreich!")
  }
} 