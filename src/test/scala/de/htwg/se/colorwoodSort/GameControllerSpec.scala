package de.htwg.se.colorwoodSort

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.colorwoodSort.controller.GameController
import de.htwg.se.colorwoodSort.model.{GameState, Tube, ColorBlock}
import de.htwg.se.colorwoodSort.util.Observer

class GameControllerSpec extends AnyWordSpec with Matchers {
  
  // Helper class to test observer pattern
  class TestObserver extends Observer {
    var updateCount: Int = 0
    var lastUpdateGameState: Option[GameState] = None
    
    def update(): Unit = {
      updateCount += 1
    }
  }
  
  def createTestGameState(): GameState = {
    val tube1 = new Tube(3)
    val tube2 = new Tube(3)
    val tube3 = new Tube(3)
    val tube4 = new Tube(3)
    
    val red = ColorBlock("Red")
    val blue = ColorBlock("Blue")
    val green = ColorBlock("Green")
    
    // Setup mixed colors for testing
    tube1.push(green)
    tube1.push(blue)
    tube1.push(red)
    
    tube2.push(red)
    tube2.push(green)
    tube2.push(blue)
    
    tube3.push(blue)
    tube3.push(red)
    tube3.push(green)
    
    // tube4 remains empty
    
    new GameState(Vector(tube1, tube2, tube3, tube4))
  }
  
  def createSolvedGameState(): GameState = {
    val tube1 = new Tube(3)
    val tube2 = new Tube(3)
    val tube3 = new Tube(3)
    val tube4 = new Tube(3)
    
    val red = ColorBlock("Red")
    val blue = ColorBlock("Blue")
    val green = ColorBlock("Green")
    
    // All same colors in each tube
    tube1.push(red)
    tube1.push(red)
    tube1.push(red)
    
    tube2.push(blue)
    tube2.push(blue)
    tube2.push(blue)
    
    tube3.push(green)
    tube3.push(green)
    tube3.push(green)
    
    // tube4 remains empty
    
    new GameState(Vector(tube1, tube2, tube3, tube4))
  }

  "A GameController" should {
    
    "initialize with correct default state" in {
      val gameState = createTestGameState()
      val controller = new GameController(gameState)
      
      controller.getGameState shouldBe gameState
      controller.isLastMoveValid shouldBe true
      controller.isLastMoveSuccessful shouldBe false
      controller.isLastInputValid shouldBe true
      controller.isLastNumberValid shouldBe true
      controller.isGameSolved shouldBe false
    }
    
    "return the game state correctly" in {
      val gameState = createTestGameState()
      val controller = new GameController(gameState)
      
      controller.getGameState shouldBe gameState
    }
    
    "detect when game is solved" in {
      val solvedGameState = createSolvedGameState()
      val controller = new GameController(solvedGameState)
      
      controller.isGameSolved shouldBe true
    }
    
    "detect when game is not solved" in {
      val gameState = createTestGameState()
      val controller = new GameController(gameState)
      
      controller.isGameSolved shouldBe false
    }
    
    "handle valid move input format" in {
      val gameState = createTestGameState()
      val controller = new GameController(gameState)
      
      // Use reflection to test processMove private method
      val processMethod = controller.getClass.getDeclaredMethod("processMove", classOf[String])
      processMethod.setAccessible(true)
      
      processMethod.invoke(controller, "1 4")
      
      controller.isLastInputValid shouldBe true
      controller.isLastNumberValid shouldBe true
      controller.isLastMoveValid shouldBe true
    }
    
    "handle invalid input format - too few arguments" in {
      val gameState = createTestGameState()
      val controller = new GameController(gameState)
      
      val processMethod = controller.getClass.getDeclaredMethod("processMove", classOf[String])
      processMethod.setAccessible(true)
      
      processMethod.invoke(controller, "1")
      
      controller.isLastInputValid shouldBe false
      controller.isLastMoveValid shouldBe false
    }
    
    "handle invalid input format - too many arguments" in {
      val gameState = createTestGameState()
      val controller = new GameController(gameState)
      
      val processMethod = controller.getClass.getDeclaredMethod("processMove", classOf[String])
      processMethod.setAccessible(true)
      
      processMethod.invoke(controller, "1 2 3")
      
      controller.isLastInputValid shouldBe false
      controller.isLastMoveValid shouldBe false
    }
    
    "handle invalid input format - non-numeric arguments" in {
      val gameState = createTestGameState()
      val controller = new GameController(gameState)
      
      val processMethod = controller.getClass.getDeclaredMethod("processMove", classOf[String])
      processMethod.setAccessible(true)
      
      processMethod.invoke(controller, "a b")
      
      controller.isLastMoveValid shouldBe false
      controller.isLastNumberValid shouldBe false
    }
    
    "handle mixed valid and invalid numeric arguments" in {
      val gameState = createTestGameState()
      val controller = new GameController(gameState)
      
      val processMethod = controller.getClass.getDeclaredMethod("processMove", classOf[String])
      processMethod.setAccessible(true)
      
      processMethod.invoke(controller, "1 x")
      
      controller.isLastMoveValid shouldBe false
      controller.isLastNumberValid shouldBe false
    }
    
    "handle empty input" in {
      val gameState = createTestGameState()
      val controller = new GameController(gameState)
      
      val processMethod = controller.getClass.getDeclaredMethod("processMove", classOf[String])
      processMethod.setAccessible(true)
      
      processMethod.invoke(controller, "")
      
      controller.isLastInputValid shouldBe false
      controller.isLastMoveValid shouldBe false
    }
    
    "handle whitespace-only input" in {
      val gameState = createTestGameState()
      val controller = new GameController(gameState)
      
      val processMethod = controller.getClass.getDeclaredMethod("processMove", classOf[String])
      processMethod.setAccessible(true)
      
      processMethod.invoke(controller, "   ")
      
      controller.isLastInputValid shouldBe false
      controller.isLastMoveValid shouldBe false
    }
    
    "notify observers when processing moves" in {
      val gameState = createTestGameState()
      val controller = new GameController(gameState)
      val observer = new TestObserver()
      
      controller.addObserver(observer)
      
      val processMethod = controller.getClass.getDeclaredMethod("processMove", classOf[String])
      processMethod.setAccessible(true)
      
      val initialCount = observer.updateCount
      processMethod.invoke(controller, "1 4")
      
      observer.updateCount shouldBe (initialCount + 1)
    }
    
    //    "track successful moves correctly" in {
    //      val gameState = createTestGameState()
    //      val controller = new GameController(gameState)
    //      
    //      val processMethod = controller.getClass.getDeclaredMethod("processMove", classOf[String])
    //      processMethod.setAccessible(true)
    //      
    //      // This should be a valid move (move from tube 1 to tube 4)
    //      processMethod.invoke(controller, "1 4")
    //      
    //      controller.isLastMoveValid shouldBe true
    //      controller.isLastMoveSuccessful shouldBe true
    //    }
    
    "track unsuccessful moves correctly" in {
      val gameState = createTestGameState()
      val controller = new GameController(gameState)
      
      val processMethod = controller.getClass.getDeclaredMethod("processMove", classOf[String])
      processMethod.setAccessible(true)
      
      // This should be an invalid move (move from empty tube)
      processMethod.invoke(controller, "4 1")
      
      controller.isLastMoveValid shouldBe true
      controller.isLastMoveSuccessful shouldBe false
    }
    
    "support observer pattern correctly" in {
      val gameState = createTestGameState()
      val controller = new GameController(gameState)
      val observer1 = new TestObserver()
      val observer2 = new TestObserver()
      
      // Test adding observers
      controller.addObserver(observer1)
      controller.addObserver(observer2)
      
      // Test notification
      controller.notifyObservers()
      
      observer1.updateCount shouldBe 1
      observer2.updateCount shouldBe 1
      
      // Test removing observer
      controller.removeObserver(observer1)
      controller.notifyObservers()
      
      observer1.updateCount shouldBe 1  // Should not increase
      observer2.updateCount shouldBe 2  // Should increase
    }
  }
} 