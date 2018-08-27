package com.savdev.fp.monad.composition.future;

import com.savdev.fp.monad.composition.future.scala.FailedCappuchino;
import com.savdev.fp.monad.composition.future.scala.TestCappuchino;
import org.junit.Assert;
import org.junit.Test;
import scala.concurrent.ExecutionContext$;
import scala.concurrent.Future;
import scala.util.Try;

import java.util.concurrent.TimeUnit;

public class ScalaFutureTestFromJava {
  @Test
  public void testSequential() throws InterruptedException {
    Future<String> result = TestCappuchino.prepareCappuccinoSequentially();
    result.onComplete(onCompleteHandler, ExecutionContext$.MODULE$.global());
    while (!result.isCompleted()) {
      TimeUnit.SECONDS.sleep(2);
    }
  }

  @Test
  public void testAsynchroniously() throws InterruptedException {
    Future<String> result = TestCappuchino.prepareCappuccinoAsynchroniously();
    result.onComplete(onCompleteHandler, ExecutionContext$.MODULE$.global());
    while (!result.isCompleted()) {
      TimeUnit.SECONDS.sleep(2);
    }
  }

  @Test
  public void testFailedCappuchino() throws InterruptedException {
    Future<String> result = FailedCappuchino.prepareCappuccinoAsynchroniously();
    result.onComplete(r-> {
      if (r.isFailure()){
        Assert.assertEquals("Cannot froth", r.failed().get().getMessage());
      } else if (r.isSuccess()){
        Assert.fail("Cannot be successul");
      } else {
        Assert.fail("Not expected status");
      }
      return null;
    }, ExecutionContext$.MODULE$.global());
    while (!result.isCompleted()) {
      TimeUnit.SECONDS.sleep(2);
    }
  }

  private scala.Function1<Try<String>, String> onCompleteHandler = r -> {
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
//
//  ExecutionContext ec = ExecutionContext$.MODULE$.global();
//
//  @Test
//  public void testSequential() throws InterruptedException {
//    Future<String> result = TestCappuchino.prepareCappuccinoSequentially(ec);
//    result.onComplete(onCompleteHandler, ec);
//    while (!result.isCompleted()) {
//      TimeUnit.SECONDS.sleep(2);
//    }
//  }
//
//  @Test
//  public void testAsynchroniously() throws InterruptedException {
//    Future<String> result = TestCappuchino.prepareCappuccinoAsynchroniously(ec);
//    result.onComplete(onCompleteHandler, ec);
//    while (!result.isCompleted()) {
//      TimeUnit.SECONDS.sleep(2);
//    }
//  }
//
//  @Test
//  public void testFailedCappuchino() throws InterruptedException {
//    Future<String> result = FailedCappuchino.prepareCappuccinoAsynchroniously(ec);
//    result.onComplete(r-> {
//      if (r.isFailure()){
//        Assert.assertEquals("Cannot froth", r.failed().get().getMessage());
//      } else if (r.isSuccess()){
//        Assert.fail("Cannot be successul");
//      } else {
//        Assert.fail("Not expected status");
//      }
//      return null;
//    }, ec);
//    while (!result.isCompleted()) {
//      TimeUnit.SECONDS.sleep(2);
//    }
//  }
//
//  private scala.Function1<Try<String>, String> onCompleteHandler = r -> {
//    if (r.isSuccess()){
//      System.out.println("onComplete successfully, thread: "
//        + Thread.currentThread().getName());
//      Assert.assertEquals("cappuccino", r.get());
//      return r.get();
//    } else if (r.isFailure()){
//      Assert.fail("Cannot be failed");
//    } else {
//      Assert.fail("Not expected status");
//    }
//    return null;
//  };
}
