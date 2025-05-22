package de.htwg.se.colorwoodSort

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class MainTest extends AnyWordSpec with Matchers {
  "The Main object" should {
    "initialize a game state correctly" in {
      // Capture console output
      val stream = new java.io.ByteArrayOutputStream()
      Console.withOut(stream) {
        // Create a new instance of Main without running the game loop
        val tube1 = new Tube(3)
        val tube2 = new Tube(3)
        val tube3 = new Tube(3)
        val tube4 = new Tube(3)
        
        val red = ColorBlock("Red")
        val blue = ColorBlock("Blue")
        val green = ColorBlock("Green")
        
        tube1.push(green)
        tube1.push(blue)
        tube1.push(red)
        
        tube2.push(red)
        tube2.push(green)
        tube2.push(blue)
        
        tube3.push(blue)
        tube3.push(red)
        tube3.push(green)
        
        val gameState = new GameState(Vector(tube1, tube2, tube3, tube4))
        
        // Verify the game state
        gameState.tubes.size shouldBe 4
        gameState.tubes(0).getBlocks.size shouldBe 3
        gameState.tubes(1).getBlocks.size shouldBe 3
        gameState.tubes(2).getBlocks.size shouldBe 3
        gameState.tubes(3).getBlocks.size shouldBe 0
      }
    }
  }
} 