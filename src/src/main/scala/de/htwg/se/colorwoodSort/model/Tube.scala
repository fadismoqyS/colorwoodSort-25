package de.htwg.se.colorwoodSort.model

class Tube(val capacity: Int) {
  private var blocks: List[ColorBlock] = List()

  def getBlocks: List[ColorBlock] = blocks

  def isFull: Boolean = blocks.size >= capacity
  def isEmpty: Boolean = blocks.isEmpty
  def topColor: Option[String] = blocks.headOption.map(_.color)

  def push(block: ColorBlock): Boolean = {
    if (isFull) {
      false
    } else {
      blocks = block :: blocks
      true
    }
  }

  def pop(): Option[ColorBlock] = {
    blocks match {
      case head :: tail =>
        blocks = tail
        Some(head)
      case Nil => None
    }
  }

  override def toString: String = {
    if (blocks.isEmpty) "[]"
    else blocks.reverse.map(_.color).mkString("[", ", ", "]")
  }
}
