package com.savdev.fp.monad.composition.future;

import com.savdev.fp.monad.composition.future.scala.TestCappuchinoOverridableEc;
import com.savdev.fp.monad.composition.future.scala.TestUtils;
import org.junit.Test;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContext$;
import scala.concurrent.Future;

import java.util.concurrent.TimeUnit;

public class ScalaFutureOverridableEcTestFromJava {

  ExecutionContext scalaGlobalEc = ExecutionContext$.MODULE$.global();

  @Test
  public void testAsynchroniously() throws InterruptedException {
    Future<String> result = TestCappuchinoOverridableEc
      .prepareCappuccinoAsynchroniously(scalaGlobalEc);
    result.onComplete(TestUtils.onCompleteHandler(), scalaGlobalEc);
    while (!result.isCompleted()) {
      TimeUnit.SECONDS.sleep(2);
    }
  }
}
