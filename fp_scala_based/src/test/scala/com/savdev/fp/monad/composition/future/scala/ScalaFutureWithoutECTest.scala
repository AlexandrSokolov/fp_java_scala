package com.savdev.fp.monad.composition.future.scala

import java.util.concurrent.TimeUnit

import org.junit.{Assert, Test}

import scala.util.{Failure, Success}

class ScalaFutureWithoutECTest {

  object TestCappuchino extends CappuccinoWithoutExecutionContext

  import scala.concurrent.ExecutionContext.Implicits.global

  @Test def testAsynchroniously(): Unit = {
    val result = TestCappuchino.prepareCappuccinoAsynchroniously
    result onComplete {
      case Success(cappuchino) =>
        println("onComplete successfully, thread: " + Thread.currentThread().getName)
        Assert.assertEquals("cappuccino", cappuchino)
      case Failure(err) => Assert.fail("Cannot be failed")
    }
    while (!result.isCompleted) {
      TimeUnit.SECONDS.sleep(2)
    }
  }
}
