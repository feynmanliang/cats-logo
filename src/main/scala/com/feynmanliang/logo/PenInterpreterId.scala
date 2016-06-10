package com.feynmanliang.logo

import cats.{Id,~>}

import Logo._

object PenInterpreterId extends (PencilInstruction ~> Id) {
  def apply[A](fa: PencilInstruction[A]): Id[A] = fa match {
    case PencilDown(p) => println(s"start drawing at $p")
    case PencilUp(p) => println(s"stop drawing at $p")
  }
}
