package com.savdev.fp.monad.composition.future.scala

import java.util.concurrent.TimeUnit

import com.savdev.fp.monad.composition.future.scala.Cappuccino.{FrothedMilk, Milk}
import org.junit.{Assert, Test}

import scala.concurrent.Future
import scala.util.{Failure, Success}

class ScalaFutureTest {

  import scala.concurrent.ExecutionContext.Implicits.global

  object TestCappuchino extends Cappuccino

  @Test def testSequential(): Unit ={
    val result = TestCappuchino.prepareCappuccinoSequentially()
    result  onComplete {
      case Success(cappuchino) => {
        println("onComplete successfully, thread: " + Thread.currentThread().getName)
        Assert.assertEquals("cappuccino", cappuchino)
      }
      case Failure(err) => Assert.fail("Cannot be failed")
    }
    while (!result.isCompleted) {
      TimeUnit.SECONDS.sleep(2)
    }
  }

  @Test def testAsynchroniously(): Unit ={
    val result = TestCappuchino.prepareCappuccinoAsynchroniously()
    result  onComplete {
      case Success(cappuchino) => {
        println("onComplete successfully, thread: " + Thread.currentThread().getName)
        Assert.assertEquals("cappuccino", cappuchino)
      }
      case Failure(err) => Assert.fail("Cannot be failed")
    }
    while (!result.isCompleted) {
      TimeUnit.SECONDS.sleep(2)
    }
  }

  object FailedCappuchino extends Cappuccino {
    override def frothMilk(milk: Milk): Future[FrothedMilk] =
      Future.failed(new IllegalStateException("Cannot froth"))
  }

  @Test def testFailedCappuchino(): Unit ={
    val result = FailedCappuchino.prepareCappuccinoAsynchroniously()
    result  onComplete {
      case Success(txt) => Assert.fail("Cannot be successul")
      case Failure(err) => Assert.assertEquals("Cannot froth", err.getMessage)
    }
    while (!result.isCompleted) {
      TimeUnit.SECONDS.sleep(2)
    }
  }
}
