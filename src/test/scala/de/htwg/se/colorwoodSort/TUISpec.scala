package de.htwg.se.colorwoodSort

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import de.htwg.se.colorwoodSort.model.{Tube, ColorBlock, GameState}
import de.htwg.se.colorwoodSort.AView.TUI

class TUITest extends AnyWordSpec with Matchers {
  "The TUI" should {
    "exit immediately on 'q'" in {
      val input = new ByteArrayInputStream("q\n".getBytes)
      val output = new ByteArrayOutputStream()
      Console.withIn(input) {
        Console.withOut(output) {
          // Create a game with mixed colors
          val tube1 = new Tube(3)
          val tube2 = new Tube(3)
          val red = ColorBlock("Red")
          val blue = ColorBlock("Blue")
          tube1.push(red)
          tube1.push(blue)
          val gameState = new GameState(Vector(tube1, tube2))
          new TUI(gameState).start()
        }
      }
      val str = output.toString
      str should include("Willkommen zum Colorwood Sort Spiel!")
      str should include("Verwende 'q' zum Beenden")
      str should include("Format für Züge")
      str should include("Tube 0:")
      str should include("Tube 1:")
      str should not include "Glückwunsch!"
    }

    "print error message on invalid input" in {
      val input = new ByteArrayInputStream("invalid\nq\n".getBytes)
      val output = new ByteArrayOutputStream()
      Console.withIn(input) {
        Console.withOut(output) {
          val tube1 = new Tube(3)
          val tube2 = new Tube(3)
          val red = ColorBlock("Red")
          tube1.push(red)
          val gameState = new GameState(Vector(tube1, tube2))
          new TUI(gameState).start()
        }
      }
      val str = output.toString
      str should include("Ungültige Eingabe! Bitte verwende das Format 'from to'")
    }

    "perform a successful move" in {
      val tube1 = new Tube(3)
      val tube2 = new Tube(3)
      val red = ColorBlock("Red")
      tube1.push(red)
      val gameState = new GameState(Vector(tube1, tube2))

      val input = new ByteArrayInputStream("0 1\nq\n".getBytes)
      val output = new ByteArrayOutputStream()
      Console.withIn(input) {
        Console.withOut(output) {
          new TUI(gameState).start()
        }
      }
      val str = output.toString
      str should include("Zug erfolgreich!")
    }

    "show error message for invalid move" in {
      val tube1 = new Tube(3)
      val tube2 = new Tube(3)
      val red = ColorBlock("Red")
      val blue = ColorBlock("Blue")
      
      // Setup: Red in tube1, Blue in tube2
      tube1.push(red)
      tube2.push(blue)
      val gameState = new GameState(Vector(tube1, tube2))

      val input = new ByteArrayInputStream("0 1\nq\n".getBytes)
      val output = new ByteArrayOutputStream()
      Console.withIn(input) {
        Console.withOut(output) {
          new TUI(gameState).start()
        }
      }
      val str = output.toString
      str should include("Ungültiger Zug! Bitte versuche es erneut.")
    }

    "detect win condition" in {
      val tube1 = new Tube(3)
      val tube2 = new Tube(3)
      val red = ColorBlock("Red")
      
      // Fill tube1 with 3 red blocks
      tube1.push(red)
      tube1.push(red)
      tube1.push(red)
      val gameState = new GameState(Vector(tube1, tube2))

      val input = new ByteArrayInputStream("q\n".getBytes)
      val output = new ByteArrayOutputStream()
      Console.withIn(input) {
        Console.withOut(output) {
          new TUI(gameState).start()
        }
      }
      val str = output.toString
      str should include("Glückwunsch! Du hast das Spiel gelöst!")
    }
  }
} 