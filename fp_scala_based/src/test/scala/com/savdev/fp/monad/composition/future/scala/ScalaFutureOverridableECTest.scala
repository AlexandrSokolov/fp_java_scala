package com.savdev.fp.monad.composition.future.scala

import java.util.concurrent.TimeUnit

import org.junit.{Test}

object TestCappuchinoOverridableEc extends CappuccinoWithOverridableExecutionContext

/**
  * Used implicit, not default ExecutionContext for both Future.onComplete AND
  * CappuccinoWithOverridableExecutionContext.prepareCappuccinoSequentially or
  * CappuccinoWithOverridableExecutionContext.prepareCappuccinoAsynchroniously()
  */
class ScalaFutureOverridableECTest {

  //  object TestCappuchino extends CappuccinoWithOverridableExecutionContext
  import com.savdev.fp.monad.composition.future.scala.TestUtils.onCompleteHandler
  //used implicit ExecutionContext for both Future.onComplete AND
  // TestCappuchinoOverridableEc.prepareCappuccinoSequentially or
  // TestCappuchinoOverridableEc.prepareCappuccinoAsynchroniously()
  import scala.concurrent.ExecutionContext.Implicits.global

  @Test def testSequential(): Unit = {
    val result = TestCappuchinoOverridableEc.prepareCappuccinoSequentially

    result onComplete onCompleteHandler
    while (!result.isCompleted) {
      TimeUnit.SECONDS.sleep(2)
    }
  }

  @Test def testAsynchroniously(): Unit = {
    val result = TestCappuchinoOverridableEc.prepareCappuccinoAsynchroniously
    result onComplete onCompleteHandler
    while (!result.isCompleted) {
      TimeUnit.SECONDS.sleep(2)
    }
  }

}
