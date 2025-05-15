package de.htwg.se.colorwoodSort

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.colorwoodSort.model.{ColorBlock, Tube, GameState}

class GameStateTest extends AnyWordSpec with Matchers {
  "A GameState" should {
    "detect that the game is not solved" in {
      val red = ColorBlock("Red")
      val blue = ColorBlock("Blue")
      val t1 = new Tube(3)
      val t2 = new Tube(3)

      t1.push(red)
      t1.push(blue) // mixed colors in the same tube

      val game = new GameState(Vector(t1, t2))
      game.isSolved shouldBe false
    }

    "detect that the game is solved when all tubes are empty" in {
      val t1 = new Tube(3)
      val t2 = new Tube(3)
      val game = new GameState(Vector(t1, t2))
      game.isSolved shouldBe true
    }

    "detect that the game is solved when all tubes are full with same colors" in {
      val red = ColorBlock("Red")
      val t1 = new Tube(3)
      val t2 = new Tube(3)
      
      t1.push(red)
      t1.push(red)
      t1.push(red)
      t2.push(red)
      t2.push(red)
      t2.push(red)
      
      val game = new GameState(Vector(t1, t2))
      game.isSolved shouldBe true
    }

    "allow a valid move" in {
      val red = ColorBlock("Red")
      val t1 = new Tube(3)
      val t2 = new Tube(3)
      t1.push(red)
      val game = new GameState(Vector(t1, t2))
      game.move(0, 1) shouldBe true
      t1.isEmpty shouldBe true
      t2.topColor should contain("Red")
    }

    "allow a valid move to tube with same color" in {
      val red = ColorBlock("Red")
      val t1 = new Tube(3)
      val t2 = new Tube(3)
      t1.push(red)
      t2.push(red)
      val game = new GameState(Vector(t1, t2))
      game.move(0, 1) shouldBe true
      t1.isEmpty shouldBe true
      t2.getBlocks.size shouldBe 2
      t2.topColor should contain("Red")
    }

    "disallow an invalid move (wrong color)" in {
      val red = ColorBlock("Red")
      val blue = ColorBlock("Blue")
      val t1 = new Tube(3)
      val t2 = new Tube(3)
      t1.push(red)
      t2.push(blue)
      val game = new GameState(Vector(t1, t2))
      game.move(0, 1) shouldBe false
      t1.topColor should contain("Red")
      t2.topColor should contain("Blue")
    }

    "disallow moving from empty tube" in {
      val t1 = new Tube(3)
      val t2 = new Tube(3)
      val game = new GameState(Vector(t1, t2))
      game.move(0, 1) shouldBe false
    }

    "disallow moving to full tube" in {
      val red = ColorBlock("Red")
      val t1 = new Tube(3)
      val t2 = new Tube(3)
      t1.push(red)
      t2.push(red)
      t2.push(red)
      t2.push(red)
      val game = new GameState(Vector(t1, t2))
      game.move(0, 1) shouldBe false
      t1.topColor should contain("Red")
      t2.getBlocks.size shouldBe 3
    }

    "have correct string representation" in {
      val red = ColorBlock("Red")
      val blue = ColorBlock("Blue")
      val t1 = new Tube(3)
      val t2 = new Tube(3)
      t1.push(red)
      t2.push(blue)
      val game = new GameState(Vector(t1, t2))
      val expected = "Tube 0: [Red]\nTube 1: [Blue]"
      game.toString shouldBe expected
    }

    "have correct string representation for empty tubes" in {
      val t1 = new Tube(3)
      val t2 = new Tube(3)
      val game = new GameState(Vector(t1, t2))
      val expected = "Tube 0: []\nTube 1: []"
      game.toString shouldBe expected
    }

    "verify toString with multiple tubes and indices" in {
      val red = ColorBlock("Red")
      val blue = ColorBlock("Blue")
      val t1 = new Tube(4)
      val t2 = new Tube(4)
      val t3 = new Tube(4)
      t1.push(red)
      t2.push(blue)
      val game = new GameState(Vector(t1, t2, t3))
      val expected = "Tube 0: [Red]\nTube 1: [Blue]\nTube 2: []"
      game.toString shouldBe expected
    }

    "handle invalid tube indices for move" in {
      val red = ColorBlock("Red")
      val t1 = new Tube(4)
      val t2 = new Tube(4)
      t1.push(red)
      val game = new GameState(Vector(t1, t2))
      
      // Test moving from invalid index
      game.move(-1, 1) shouldBe false
      game.move(2, 1) shouldBe false
      
      // Test moving to invalid index
      game.move(0, -1) shouldBe false
      game.move(0, 2) shouldBe false
      
      // Verify original state remains unchanged
      t1.topColor should contain("Red")
      t2.isEmpty shouldBe true
    }

    "handle complex move scenarios" in {
      val blue = ColorBlock("Blue")
      val t1 = new Tube(4)
      val t2 = new Tube(4)
      
      // Setup: Blue in t1
      t1.push(blue)
      
      val game = new GameState(Vector(t1, t2))
      
      // Move blue to empty tube
      game.move(0, 1) shouldBe true
      
      // Verify final state
      t1.isEmpty shouldBe true
      t2.topColor should contain("Blue")
    }

    "verify game solved with partially filled tubes" in {
      val red = ColorBlock("Red")
      val t1 = new Tube(2)
      val t2 = new Tube(2)
      val t3 = new Tube(2)
      
      // Setup: Two reds in t1, empty t2 and t3
      t1.push(red)
      t1.push(red)
      
      val game = new GameState(Vector(t1, t2, t3))
      game.isSolved shouldBe true
    }

    "verify game is not solved with mixed colors in tube" in {
      val red = ColorBlock("Red")
      val blue = ColorBlock("Blue")
      val t1 = new Tube(2)
      
      t1.push(red)
      t1.push(blue)
      
      val game = new GameState(Vector(t1))
      game.isSolved shouldBe false
    }

    "verify game is not solved with partially filled tube" in {
      val red = ColorBlock("Red")
      val t1 = new Tube(2)
      
      t1.push(red) // Only one red in a tube with capacity 2
      
      val game = new GameState(Vector(t1))
      game.isSolved shouldBe false
    }

    "handle edge cases in move operation" in {
      val red = ColorBlock("Red")
      val t1 = new Tube(2)
      val t2 = new Tube(2)
      
      t1.push(red)
      
      val game = new GameState(Vector(t1, t2))
      
      // Test invalid indices
      game.move(-1, 0) shouldBe false
      game.move(0, -1) shouldBe false
      game.move(2, 0) shouldBe false
      game.move(0, 2) shouldBe false
      
      // Verify state unchanged
      t1.topColor should contain("Red")
      t2.isEmpty shouldBe true
    }

    "test toString with various states" in {
      val tube1 = new Tube(4)
      val tube2 = new Tube(4)
      val gameState = new GameState(Vector(tube1, tube2))
      val red = ColorBlock("Red")
      val blue = ColorBlock("Blue")
      
      // Test empty game state
      gameState.toString shouldBe "Tube 0: []\nTube 1: []"
      
      // Test with one block in first tube
      tube1.push(red)
      gameState.toString shouldBe "Tube 0: [Red]\nTube 1: []"
      
      // Test with blocks in both tubes
      tube2.push(blue)
      gameState.toString shouldBe "Tube 0: [Red]\nTube 1: [Blue]"
    }

    "test move with all possible conditions" in {
      val red = ColorBlock("Red")
      val blue = ColorBlock("Blue")
      val t1 = new Tube(2)
      val t2 = new Tube(2)
      
      t1.push(red)
      val game = new GameState(Vector(t1, t2))
      
      // Move to empty tube
      game.move(0, 1) shouldBe true
      t1.isEmpty shouldBe true
      t2.topColor should contain("Red")
      
      // Move from empty tube
      game.move(0, 1) shouldBe false
      
      // Move to full tube
      t2.push(blue)
      game.move(0, 1) shouldBe false
      
      // Move to tube with different color
      t1.push(blue)
      game.move(1, 0) shouldBe false
    }

    "test source.pop() operation" in {
      val red = ColorBlock("Red")
      val t1 = new Tube(4)
      val t2 = new Tube(4)
      
      // Setup: Ein roter Block in t1
      t1.push(red)
      val game = new GameState(Vector(t1, t2))
      
      // Überprüfe den Zustand vor dem Pop
      t1.topColor should contain("Red")
      t2.isEmpty shouldBe true
      
      // Führe move aus, was intern source.pop() verwendet
      game.move(0, 1) shouldBe true
      
      // Überprüfe den Zustand nach dem Pop
      t1.isEmpty shouldBe true
      t2.topColor should contain("Red")
    }

    "prevent move when no blocks can be moved" in {
      val red = ColorBlock("Red")
      val t1 = new Tube(3)
      val t2 = new Tube(3)
      
      // Fill t2 completely
      t2.push(red)
      t2.push(red)
      t2.push(red)
      
      // Try to add one more block to full tube
      t1.push(red)
      val game = new GameState(Vector(t1, t2))
      
      // Should fail because target tube is full (toMove would be 0)
      game.move(0, 1) shouldBe false
      
      // Verify state unchanged
      t1.getBlocks.size shouldBe 1
      t2.getBlocks.size shouldBe 3
    }

    "not move when target has no free space (coverage for toMove <= 0)" in {
      val sourceTube = Tube(2)
      val targetTube = Tube(2)
      val red = ColorBlock("Red")
      val blue = ColorBlock("Blue")

      sourceTube.push(red)
      targetTube.push(blue)
      targetTube.push(blue) // Voll und andere Farbe

      val gameState = new GameState(Vector(sourceTube, targetTube))
      val toMove = 99

      gameState.move(0, 1) should be(false)
    }


  }
}
