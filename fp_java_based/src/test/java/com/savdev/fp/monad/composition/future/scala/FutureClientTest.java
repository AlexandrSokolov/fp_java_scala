package com.savdev.fp.monad.composition.future.scala;

import org.junit.Test;
import scala.concurrent.Future;

import java.util.concurrent.TimeUnit;

public class FutureClientTest {

  @Test
  public void testF() throws InterruptedException {
    Future<String> r = new FutureClient().f("source");
    while (!r.isCompleted()) {
      TimeUnit.SECONDS.sleep(2);
    }
  }
}
