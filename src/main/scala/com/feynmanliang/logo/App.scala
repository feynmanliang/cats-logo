package com.feynmanliang.logo

import cats.free.Free

import Logo._

object App {
  val program: (Position => Free[Instruction, Position]) = (start: Position) => {
    for {
      _ <- showPosition(start)
      p1 <- forward(start, 10)
      _ <- showPosition(p1)
      p2 <- right(p1, Degree(90))
      _ <- showPosition(p2)
      p3 <- forward(p2, 10)
      _ <- showPosition(p3)
    } yield p3
  }


  def main(args: Array[String]):Unit = {
    val startPosition = Position(0.0, 0.0, Degree(0))
    program(startPosition).foldMap(InterpreterId)
  }
}
