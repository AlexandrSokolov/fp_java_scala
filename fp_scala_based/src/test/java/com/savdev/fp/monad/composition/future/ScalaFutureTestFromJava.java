package com.savdev.fp.monad.composition.future;

import com.savdev.fp.monad.composition.future.scala.FailedCappuchino;
import com.savdev.fp.monad.composition.future.scala.TestCappuchino;
import com.savdev.fp.monad.composition.future.scala.TestUtils;
import org.junit.Assert;
import org.junit.Test;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContext$;
import scala.concurrent.Future;
import scala.util.Try;

import java.util.concurrent.TimeUnit;

public class ScalaFutureTestFromJava {
  ExecutionContext scalaGlobalEc = ExecutionContext$.MODULE$.global();
  @Test
  public void testSequential() throws InterruptedException {
    Future<String> result = TestCappuchino.prepareCappuccinoSequentially();
    //result.onComplete(onCompleteHandler, );

    result.onComplete(TestUtils.onCompleteHandler(), scalaGlobalEc);
    while (!result.isCompleted()) {
      TimeUnit.SECONDS.sleep(2);
    }
  }

  @Test
  public void testSequentialViaOnCompleteJavaHandler() throws InterruptedException {
    Future<String> result = TestCappuchino.prepareCappuccinoSequentially();
    result.onComplete(onCompleteJavaHandler, scalaGlobalEc);
    while (!result.isCompleted()) {
      TimeUnit.SECONDS.sleep(2);
    }
  }

  @Test
  public void testAsynchroniously() throws InterruptedException {
    Future<String> result = TestCappuchino.prepareCappuccinoAsynchroniously();
    result.onComplete(TestUtils.onCompleteHandler(), scalaGlobalEc);
    while (!result.isCompleted()) {
      TimeUnit.SECONDS.sleep(2);
    }
  }

  @Test
  public void testFailedCappuchino() throws InterruptedException {
    Future<String> result = FailedCappuchino.prepareCappuccinoAsynchroniously();
    result.onComplete(TestUtils.onCompleteErrorHandler(), scalaGlobalEc);
    while (!result.isCompleted()) {
      TimeUnit.SECONDS.sleep(2);
    }
  }

  private scala.Function1<Try<String>, String> onCompleteJavaHandler = r -> {
    if (r.isSuccess()){
      System.out.println("onComplete successfully, thread: "
        + Thread.currentThread().getName());
      Assert.assertEquals("cappuccino", r.get());
      return r.get();
    } else if (r.isFailure()){
      Assert.fail("Cannot be failed");
    } else {
      Assert.fail("Not expected status");
    }
    return null;
  };
}
