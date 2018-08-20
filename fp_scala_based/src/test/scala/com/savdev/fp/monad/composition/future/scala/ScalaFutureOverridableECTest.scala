package com.savdev.fp.monad.composition.future.scala

import java.util.concurrent.TimeUnit

import org.junit.{Test}

/**
  * Default ExecutionContext chosen by API is used
  */
class ScalaFutureOverridableECTest {

  object TestCappuchino extends CappuccinoWithOverridableExecutionContext
  import com.savdev.fp.monad.composition.future.scala.TestUtils.onCompleteHandler
  //used for: Future.onComplete
  import scala.concurrent.ExecutionContext.Implicits.global

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
