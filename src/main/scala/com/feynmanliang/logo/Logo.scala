package com.feynmanliang.logo

import cats.data.Coproduct
import cats.free.{Free, Inject}

object Logo {
  type LogoApp[A] = Coproduct[Instruction, PencilInstruction, A]

  sealed trait Instruction[A]
  case class Forward(position: Position, length: Int) extends Instruction[Position]
  case class Backward(position: Position, length: Int) extends Instruction[Position]
  case class RotateLeft(position: Position, length: Degree) extends Instruction[Position]
  case class RotateRight(position: Position, length: Degree) extends Instruction[Position]
  case class ShowPosition(position: Position) extends Instruction[Unit]

  sealed trait PencilInstruction[A]
  case class PencilUp(position: Position) extends PencilInstruction[Unit]
  case class PencilDown(position: Position) extends PencilInstruction[Unit]

  case class Position(x: Double, y: Double, heading: Degree)
  case class Degree(private val d: Int) {
    val value = d % 360
  }

  object dsl {
    class Moves[F[_]](implicit I: Inject[Instruction, F]) {
      def forward(pos: Position, l: Int): Free[F, Position] = Free.inject[Instruction, F](Forward(pos, l))
      def backward(pos: Position, l: Int): Free[F, Position] = Free.inject[Instruction, F](Backward(pos, l))
      def left(pos: Position, l: Degree): Free[F, Position] = Free.inject[Instruction, F](RotateLeft(pos, l))
      def right(pos: Position, l: Degree): Free[F, Position] = Free.inject[Instruction, F](RotateRight(pos, l))
      def showPosition(pos: Position): Free[F, Unit] = Free.inject[Instruction, F](ShowPosition(pos))
    }

    object Moves {
      implicit def moves[F[_]](implicit I: Inject[Instruction, F]): Moves[F] = new Moves[F]
    }

    class PencilActions[F[_]](implicit I: Inject[PencilInstruction, F]) {
      def pencilUp(pos: Position): Free[F, Unit] = Free.inject[PencilInstruction, F](PencilUp(pos))
      def pencilDown(pos: Position): Free[F, Unit] = Free.inject[PencilInstruction, F](PencilDown(pos))
    }

    object PencilActions {
      implicit def pencilActions[F[_]](implicit I: Inject[PencilInstruction, F]): PencilActions[F] = new PencilActions[F]
    }
  }
}
