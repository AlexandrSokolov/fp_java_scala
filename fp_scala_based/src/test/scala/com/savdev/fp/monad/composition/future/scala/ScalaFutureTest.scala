package com.savdev.fp.monad.composition.future.scala

import java.util.concurrent.TimeUnit

import org.junit.{Assert, Test}

import scala.util.{Failure, Success, Try}

object TestCappuchino extends Cappuccino

object TestUtils {
  //val onCompleteHandler: scala.Function1[Try[Cappuccino.Cappuccino], Unit] =
  val onCompleteHandler = (_: Try[Cappuccino.Cappuccino]) match {
    case Success(cappuchino) => {
      println("onComplete successfully, thread: " + Thread.currentThread().getName)
      Assert.assertEquals("cappuccino", cappuchino)
    }
    case Failure(err) => Assert.fail("Cannot be failed")
  }
  //val onCompleteHandler: scala.Function1[Try[Cappuccino.Cappuccino], Unit] =
  val onCompleteErrorHandler = (_: Try[Cappuccino.Cappuccino]) match {
    case Success(txt) => Assert.fail("Cannot be successful")
    case Failure(err) => Assert.assertEquals("Cannot froth", err.getMessage)
  }
}

class ScalaFutureTest {

  import scala.concurrent.ExecutionContext.Implicits.global

  import TestUtils.onCompleteHandler

  @Test def testSequential(): Unit = {
    val result = TestCappuchino.prepareCappuccinoSequentially()
    result onComplete onCompleteHandler
    while (!result.isCompleted) {
      TimeUnit.SECONDS.sleep(2)
    }
  }

  @Test def testAsynchroniously(): Unit = {
    val result = TestCappuchino.prepareCappuccinoAsynchroniously()
    result onComplete onCompleteHandler
    while (!result.isCompleted) {
      TimeUnit.SECONDS.sleep(2)
    }
  }


}
