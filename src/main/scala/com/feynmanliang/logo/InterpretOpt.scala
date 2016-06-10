package com.feynmanliang.logo

import cats.~>

import Logo._

object InterpretOpt extends (Instruction ~> Option) {
  import Computations._
  val nonNegative: (Position) => Option[Position] = (p: Position) => {
    if (p.x >= 0 && p.y >= 0) Some(p) else None
  }

  override def apply[A](fa: Instruction[A]): Option[A] = fa match {
    case Forward(p, length) => nonNegative(forward(p, length))
    case Backward(p, length) => nonNegative(backward(p, length))
    case RotateLeft(p, degree) => Some(left(p, degree))
    case RotateRight(p, degree) => Some(right(p, degree))
    case ShowPosition(p) => Some(println(s"showing position $p"))
  }
}
