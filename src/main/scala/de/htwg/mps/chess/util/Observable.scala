package de.htwg.mps.chess.util

trait Observer {
  def update()
}

class Observable {
  var subscribers: Vector[Observer] = Vector()

  def add(s: Observer) = subscribers = subscribers :+ s

  def remove(s: Observer) = subscribers = subscribers.filterNot(_ == s)

  def notifyObservers() = subscribers.foreach(_.update())
}

