package com.savdev.fp.monad.composition.future.scala

import java.util.concurrent.TimeUnit

import org.junit.{Test}


class ScalaFutureWithoutECTest {

  object TestCappuchino extends CappuccinoWithoutExecutionContext

  import scala.concurrent.ExecutionContext.Implicits.global

  @Test def testAsynchroniously(): Unit = {
    val result = TestCappuchino.prepareCappuccinoAsynchroniously
    import TestUtils.onCompleteHandler
    result onComplete onCompleteHandler
    while (!result.isCompleted) {
      TimeUnit.SECONDS.sleep(2)
    }
  }
}
