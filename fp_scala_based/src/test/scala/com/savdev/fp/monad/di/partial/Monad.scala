package com.savdev.fp.monad.di.partial

trait Monad[F[_]] extends Functor[F] {
  def unit[A](a: => A): F[A]

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B] =
    join(map(fa)(f))

  def map[A, B](ma: F[A])(f: A => B): F[B] =
    flatMap(ma)(a => unit(f(a)))

  def join[A](mma: F[F[A]]): F[A] = flatMap(mma)(ma => ma)
}

object Monad {
  def apply[F[_] : Monad]: Monad[F] =
    implicitly[Monad[F]]

  implicit def function1Monad[A1]: Monad[({type f[x] = Function1[A1, x]})#f] = new Monad[({type f[x] = Function1[A1, x]})#f] {
    def unit[A](a: => A) = (_: A1) => a

    override def flatMap[A, B](r: A1 => A)(f: A => A1 => B) = (t: A1) => f(r(t))(t)
  }
}
