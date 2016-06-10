package com.feynmanliang.logo

import cats.implicits._
import cats.free.Free

import Logo._
import Logo.dsl._

object App {
  val program: (Position => Free[Instruction, Position]) = (start: Position) => {
    for {
      p1 <- forward(start, 10)
      p2 <- right(p1, Degree(90))
      p3 <- forward(p2, 10)
      _ <- showPosition(p3)
    } yield p3
  }

  val program2: (Position => Free[Instruction, Unit]) = (start: Position) => {
    for {
      p1 <- forward(start, 10)
      p2 <- left(p1, Degree(90))
      p3 <- forward(p2, 10)
      p4 <- backward(p3, 20) // InterpretOpt computation stops here because goes out 1st quadrant
      _ <- showPosition(p4)
    } yield ()
  }

  def main(args: Array[String]):Unit = {
    val startPosition = Position(0.0, 0.0, Degree(0))

    program(startPosition).foldMap(InterpreterId)

    program2(startPosition).foldMap(InterpretOpt) // shouldn't print
  }
}
