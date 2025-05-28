package de.htwg.se.colorwoodSort.controller

import scala.io.StdIn.readLine
import de.htwg.se.colorwoodSort.model.GameState
import de.htwg.se.colorwoodSort.util.Subject
//import de.htwg.se.colorwoodSort.view.GameView

class GameController(private val gameState: GameState) extends Subject {
  private var lastMoveValid: Boolean = true
  private var lastMoveSuccessful: Boolean = false
  private var lastInputValid: Boolean = true
  private var lastNumberValid: Boolean = true

  def start(): Unit = {
    notifyObservers()
    gameLoop()
  }

  private def gameLoop(): Unit = {
    var running = true
    while (running) {
      if (gameState.isSolved) {
        notifyObservers()
        running = false
      } else {
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
    val parts = input.trim.split("\\s+")
    if (parts.length == 2) {
      try {
        val from = parts(0).toInt
        val to = parts(1).toInt
        lastMoveValid = true
        lastMoveSuccessful = gameState.move(from, to)
        lastInputValid = true
        lastNumberValid = true
      } catch {
        case _: NumberFormatException => 
          lastMoveValid = false
          lastNumberValid = false
      }
    } else {
      lastMoveValid = false
      lastInputValid = false
    }
    notifyObservers()
  }

  def getGameState: GameState = gameState
  def isLastMoveValid: Boolean = lastMoveValid
  def isLastMoveSuccessful: Boolean = lastMoveSuccessful
  def isLastInputValid: Boolean = lastInputValid
  def isLastNumberValid: Boolean = lastNumberValid
  def isGameSolved: Boolean = gameState.isSolved
} 