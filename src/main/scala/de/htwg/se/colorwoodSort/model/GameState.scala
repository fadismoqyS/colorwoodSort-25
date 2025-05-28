package de.htwg.se.colorwoodSort.model

class GameState(val tubes: Vector[Tube]) {
  private var moveHistory: List[(Int, Int)] = List()
  // Number of blocks moved in the last move
  var lastMoveCount: Int = 0

  def move(from: Int, to: Int): Boolean = {
    if (from < 0 || to < 0 || from >= tubes.length || to >= tubes.length) return false
    
    val newMove = (from, to)
    val source = tubes(from)
    val target = tubes(to)

    // Check if target tube is full
    if (target.isFull) return false

    // Check if source tube is empty
    if (source.isEmpty) return false

    // Get the color of the top block in source tube
    val sourceColor = source.topColor.getOrElse(return false)

    // Determine how many contiguous blocks of the same color to move
    val contiguous = source.getBlocks.takeWhile(_.color == sourceColor)
    val freeSpace = target.capacity - target.getBlocks.size
    
    // Check if there is enough space for all contiguous blocks
    if (contiguous.isEmpty || freeSpace < contiguous.size) return false

    // Move the blocks
    for (_ <- 1 to contiguous.size) {
      source.pop().foreach(target.push)
    }
    lastMoveCount = contiguous.size
    moveHistory = newMove :: moveHistory
    true
  }

  def isSolved: Boolean = {
    // A game is solved if every tube is either empty or full and contains only one color
    tubes.forall(tube => tube.isEmpty || (tube.isFull && tube.getBlocks.map(_.color).distinct.size == 1))
  }

  override def toString: String = tubes.zipWithIndex.map {
    case (tube, idx) => s"Tube $idx: $tube"
  }.mkString("\n")
}
