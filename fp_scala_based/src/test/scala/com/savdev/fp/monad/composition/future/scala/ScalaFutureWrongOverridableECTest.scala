package com.savdev.fp.monad.composition.future.scala

import java.util.concurrent.TimeUnit

import org.junit.Test

/**
  * Here client tries to override default implicit, but it does not work
  *   due to the way how function is invoked:
  *   TestCappuchino.prepareCappuccinoSequentially()
  *
  *   to fix it he must it invoke without () as:
  *   TestCappuchino.prepareCappuccinoSequentially
  */
class ScalaFutureWrongOverridableECTest {

  object TestCappuchino extends CappuccinoWithOverridableExecutionContext
  import com.savdev.fp.monad.composition.future.scala.TestUtils.onCompleteHandler
  //used implicit ExecutionContext ONLY for Future.onComplete
  // BUT not for:
  // TestCappuchino.prepareCappuccinoSequentially or
  // TestCappuchino.prepareCappuccinoAsynchroniously()
  import scala.concurrent.ExecutionContext.Implicits.global

  @Test def testSequential(): Unit = {
    //to fix it, you must invoke it without ()
    val result = TestCappuchino.prepareCappuccinoSequentially()

    result onComplete onCompleteHandler
    while (!result.isCompleted) {
      TimeUnit.SECONDS.sleep(2)
    }
  }

  @Test def testAsynchroniously(): Unit = {
    //to fix it, you must invoke it without ()
    val result = TestCappuchino.prepareCappuccinoAsynchroniously()
    result onComplete onCompleteHandler
    while (!result.isCompleted) {
      TimeUnit.SECONDS.sleep(2)
    }
  }

}
