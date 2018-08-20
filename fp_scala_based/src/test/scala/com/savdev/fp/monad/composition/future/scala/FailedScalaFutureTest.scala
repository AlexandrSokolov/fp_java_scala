package com.savdev.fp.monad.composition.future.scala

import java.util.concurrent.TimeUnit

import com.savdev.fp.monad.composition.future.scala.Cappuccino.{FrothedMilk, Milk}
import org.junit.{Test}

import scala.concurrent.Future

object FailedCappuchino extends Cappuccino {
  override def frothMilk(milk: Milk): Future[FrothedMilk] =
    Future.failed(new IllegalStateException("Cannot froth"))
}

class FailedScalaFutureTest {

  import scala.concurrent.ExecutionContext.Implicits.global

  @Test def testFailedCappuchino(): Unit = {
    val result = FailedCappuchino.prepareCappuccinoAsynchroniously()
    import TestUtils.onCompleteErrorHandler
    result onComplete onCompleteErrorHandler
    while (!result.isCompleted) {
      TimeUnit.SECONDS.sleep(2)
    }
  }


}
