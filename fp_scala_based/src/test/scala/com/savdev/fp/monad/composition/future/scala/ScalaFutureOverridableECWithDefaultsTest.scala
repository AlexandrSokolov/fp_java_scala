package com.savdev.fp.monad.composition.future.scala

import java.util.concurrent.TimeUnit

import org.junit.Test

/**
  * In prepareCappuccinoSequentially the default ExecutionContext
  * chosen by API definition is used
  * In Future.onComplete ExecutionContext passed explicitely is used
  */
class ScalaFutureOverridableECWithDefaultsTest {

  object TestCappuchino extends CappuccinoWithOverridableExecutionContext

  import com.savdev.fp.monad.composition.future.scala.TestUtils.onCompleteHandler

  @Test def testSequential(): Unit = {
    val result = TestCappuchino.prepareCappuccinoSequentially()

    result.onComplete(onCompleteHandler)(scala.concurrent.ExecutionContext.Implicits.global)
    while (!result.isCompleted) {
      TimeUnit.SECONDS.sleep(2)
    }
  }

  @Test def testAsynchroniously(): Unit = {
    val result = TestCappuchino.prepareCappuccinoAsynchroniously()
    result.onComplete(onCompleteHandler)(scala.concurrent.ExecutionContext.Implicits.global)
    while (!result.isCompleted) {
      TimeUnit.SECONDS.sleep(2)
    }
  }

}
