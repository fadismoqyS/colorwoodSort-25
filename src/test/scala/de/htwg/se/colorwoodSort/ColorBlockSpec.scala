package de.htwg.se.colorwoodSort

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class ColorBlockTest extends AnyWordSpec with Matchers {
  "A ColorBlock" should {
    "store the given color" in {
      val block = ColorBlock("Green")
      block.color shouldBe "Green"
    }
  }
}