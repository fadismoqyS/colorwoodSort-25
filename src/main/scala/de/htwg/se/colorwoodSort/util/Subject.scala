package de.htwg.se.colorwoodSort.util

trait Subject {
  private var observers: List[Observer] = List()

  def addObserver(observer: Observer): Unit = {
    observers = observer :: observers
  }

  def removeObserver(observer: Observer): Unit = {
    observers = observers.filterNot(_ == observer)
  }

  def notifyObservers(): Unit = {
    observers.foreach(_.update())
  }
}
