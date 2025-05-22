package de.htwg.se.colorwoodSort

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class TubeTest extends AnyWordSpec with Matchers {
  "A Tube" should {
    val red = ColorBlock("Red")
    val blue = ColorBlock("Blue")

    "be empty initially" in {
      val tube = new Tube(3)
      tube.isEmpty shouldBe true
      tube.isFull shouldBe false
      tube.getBlocks shouldBe List()
      tube.topColor shouldBe None
    }

    "allow pushing same color blocks" in {
      val tube = new Tube(3)
      tube.push(red) shouldBe true
      tube.push(red) shouldBe true
      tube.push(red) shouldBe true
      tube.push(red) shouldBe false // Tube is full
    }

    "allow pushing different color blocks" in {
      val tube = new Tube(3)
      tube.push(red) shouldBe true
      tube.push(blue) shouldBe true
      tube.push(red) shouldBe true
    }

    "show the correct top color" in {
      val tube = new Tube(3)
      tube.push(red)
      tube.topColor should contain("Red")
    }

    "allow popping the top block" in {
      val tube = new Tube(3)
      tube.push(red)
      tube.isEmpty shouldBe false
      tube.pop() should contain(red)
      tube.isEmpty shouldBe true
    }

    "not allow pushing when full" in {
      val tube = new Tube(3)
      tube.push(red) shouldBe true
      tube.push(red) shouldBe true
      tube.push(red) shouldBe true
      tube.push(red) shouldBe false
      tube.isFull shouldBe true
    }

    "return None when popping from empty tube" in {
      val emptyTube = new Tube(3)
      emptyTube.pop() shouldBe None
      emptyTube.topColor shouldBe None
    }

    "have correct string representation" in {
      val testTube = new Tube(3)
      testTube.toString shouldBe "[]"
      testTube.push(red)
      testTube.toString shouldBe "[Red]"
      testTube.push(red)
      testTube.toString shouldBe "[Red, Red]"
    }

    "have correct string representation with multiple blocks" in {
      val testTube = new Tube(3)
      testTube.push(red)
      testTube.push(blue)
      testTube.toString shouldBe "[Red, Blue]"
    }

    "return all blocks correctly" in {
      val testTube = new Tube(3)
      testTube.push(red)
      testTube.push(red)
      val blocks = testTube.getBlocks
      blocks.size shouldBe 2
      blocks.head.color shouldBe "Red"
      blocks(1).color shouldBe "Red"
    }

    "handle topColor for empty and non-empty states" in {
      val tube = new Tube(3)
      tube.topColor shouldBe None  // Test empty state
      tube.push(red)
      tube.topColor should contain("Red")  // Test with one block
      tube.push(red)
      tube.topColor should contain("Red")  // Test with multiple blocks
      tube.pop()
      tube.topColor should contain("Red")  // Test after pop
      tube.pop()
      tube.topColor shouldBe None  // Test back to empty
    }

    "maintain block order correctly" in {
      val tube = new Tube(3)
      tube.push(red)
      tube.getBlocks.map(_.color) shouldBe List("Red")
      tube.push(red)
      tube.getBlocks.map(_.color) shouldBe List("Red", "Red")
      tube.pop()
      tube.getBlocks.map(_.color) shouldBe List("Red")
    }

    "test tube initialization" in {
      val tube = new Tube(4)
      tube.capacity shouldBe 4
      tube.isEmpty shouldBe true
      tube.getBlocks shouldBe empty
    }

    "test toString with various states" in {
      val tube = new Tube(4)
      val red = ColorBlock("Red")
      val blue = ColorBlock("Blue")
      
      // Test empty tube
      tube.toString shouldBe "[]"
      
      // Test single block
      tube.push(red)
      tube.toString shouldBe "[Red]"
      
      // Test multiple blocks of same color
      tube.push(red)
      tube.toString shouldBe "[Red, Red]"
      
      // Test after removing blocks
      tube.pop()
      tube.toString shouldBe "[Red]"
      
      tube.pop()
      tube.toString shouldBe "[]"
    }

    "test topColor with various states" in {
      val tube = new Tube(4)
      val red = ColorBlock("Red")
      
      tube.topColor shouldBe None  // Empty tube
      
      tube.push(red)
      tube.topColor should contain("Red")  // With block
      
      tube.pop()
      tube.topColor shouldBe None  // After removing block
    }
  }
}
