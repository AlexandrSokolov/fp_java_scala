package com.savdev.fp.monad.composition.future.scala

import java.util.concurrent.TimeUnit

import org.junit.{Test}

/**
  * Used implicit, not default ExecutionContext for both Future.onComplete AND
  * TestCappuchino.prepareCappuccinoSequentially or
  * TestCappuchino.prepareCappuccinoAsynchroniously()
  */
class ScalaFutureOverridableECTest {

  object TestCappuchino extends CappuccinoWithOverridableExecutionContext
  import com.savdev.fp.monad.composition.future.scala.TestUtils.onCompleteHandler
  //used implicit ExecutionContext for both Future.onComplete AND
  // TestCappuchino.prepareCappuccinoSequentially or
  // TestCappuchino.prepareCappuccinoAsynchroniously()
  import scala.concurrent.ExecutionContext.Implicits.global

  @Test def testSequential(): Unit = {
    val result = TestCappuchino.prepareCappuccinoSequentially

    result onComplete onCompleteHandler
    while (!result.isCompleted) {
      TimeUnit.SECONDS.sleep(2)
    }
  }

  @Test def testAsynchroniously(): Unit = {
    val result = TestCappuchino.prepareCappuccinoAsynchroniously
    result onComplete onCompleteHandler
    while (!result.isCompleted) {
      TimeUnit.SECONDS.sleep(2)
    }
  }

}
