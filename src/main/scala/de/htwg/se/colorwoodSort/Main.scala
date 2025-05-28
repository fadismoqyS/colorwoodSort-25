package de.htwg.se.colorwoodSort

import de.htwg.se.colorwoodSort.model.{Tube, ColorBlock, GameState}
import de.htwg.se.colorwoodSort.AView.TUI

object Main extends App {
  // Erstelle ein initiales Spiel mit 4 Tubes (3 mit Blöcken, 1 leer)
  val tube1 = new Tube(3)
  val tube2 = new Tube(3)
  val tube3 = new Tube(3)
  val tube4 = new Tube(3)  // .Leere Tube zum Verschieben
  
  // Fülle die Tubes mit einigen Blöcken
  val red = ColorBlock("Red")
  val blue = ColorBlock("Blue")
  val green = ColorBlock("Green")
  
  // Tube 1: Rot, Blau, Grün
  tube1.push(green)
  tube1.push(blue)
  tube1.push(red)
  
  // Tube 2: Blau, Grün, Rot
  tube2.push(red)
  tube2.push(green)
  tube2.push(blue)
  
  // Tube 3: Grün, Rot, Blau
  tube3.push(blue)
  tube3.push(red)
  tube3.push(green)
  
  // Tube 4 bleibt leer
  
  // Erstelle den Spielzustand
  val gameState = new GameState(Vector(tube1, tube2, tube3, tube4))
  
  // Starte die TUI
  val tui = new TUI(gameState)
  tui.start()
}
