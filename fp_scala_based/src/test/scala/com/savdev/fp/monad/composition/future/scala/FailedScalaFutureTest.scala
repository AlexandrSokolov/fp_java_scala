package com.savdev.fp.monad.composition.future.scala

import java.util.concurrent.TimeUnit

import com.savdev.fp.monad.composition.future.scala.Cappuccino.{FrothedMilk, Milk}
import org.junit.{Assert, Test}

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

object FailedCappuchino extends Cappuccino {
  override def frothMilk(milk: Milk): Future[FrothedMilk] =
    Future.failed(new IllegalStateException("Cannot froth"))
}

class FailedScalaFutureTest {

  import scala.concurrent.ExecutionContext.Implicits.global

  @Test def testFailedCappuchino(): Unit = {
    val result = FailedCappuchino.prepareCappuccinoAsynchroniously()
    result onComplete onCompleteErrorHandler
    while (!result.isCompleted) {
      TimeUnit.SECONDS.sleep(2)
    }
  }

  //val onCompleteHandler: scala.Function1[Try[Cappuccino.Cappuccino], Unit] =
  val onCompleteErrorHandler = (_: Try[Cappuccino.Cappuccino]) match {
    case Success(txt) => Assert.fail("Cannot be successful")
    case Failure(err) => Assert.assertEquals("Cannot froth", err.getMessage)
  }
}
