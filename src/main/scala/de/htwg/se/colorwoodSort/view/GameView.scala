package de.htwg.se.colorwoodSort.view

import de.htwg.se.colorwoodSort.model.GameState

object GameView {
  def apply(): GameView = new GameView()
}

class GameView {
  def displayWelcome(): Unit = {
    println("Willkommen zum Colorwood Sort Spiel!")
    println("Verwende 'q' zum Beenden")
    println("Format für Züge: 'from to' (z.B. '0 1' um von Tube 0 nach Tube 1 zu bewegen)")
  }

  def displayGameState(gameState: GameState): Unit = {
    println("\nAktueller Spielstand:")
    println(gameState)
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