package com.savdev.fp.monad.di.partial

import scala.language.higherKinds

trait Functor[F[_]] {
  def map[A, B](a: F[A])(f: A => B): F[B]
}

object Functor {
  def apply[F[_] : Functor]: Functor[F] =
    implicitly[Functor[F]]

  implicit def ListFunctor: Functor[List] = new Functor[List] {
    def map[A, B](a: List[A])(f: A => B): List[B] = a map f
  }

  implicit def OptionFunctor: Functor[Option] = new Functor[Option] {
    def map[A, B](a: Option[A])(f: A => B): Option[B] = a map f
  }

  implicit def Tuple2Functor[A1]: Functor[({type f[x] = (A1, x)})#f] = new Functor[({type f[x] = (A1, x)})#f] {
    def map[A, B](a: (A1, A))(f: A => B): (A1, B) = (a._1, f(a._2))
  }

  implicit def Function1Functor[A1]: Functor[({type f[x] = Function1[A1, x]})#f] = new Functor[({type f[x] = Function1[A1, x]})#f] {
    def map[A, B](fa: A1 => A)(f: A => B) = fa andThen f
  }

}


