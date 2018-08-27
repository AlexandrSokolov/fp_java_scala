package com.savdev.fp.monad.composition.future.scala;

import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContext$;
import scala.concurrent.Future;
import scala.concurrent.Future$;
import scala.util.Random;

import java.util.concurrent.TimeUnit;

public class FutureClient {
  ExecutionContext scalaGlobalEc = ExecutionContext$.MODULE$.global();

  public Future<String> f(String s){
    return Future$.MODULE$.apply(() -> {
      System.out.println("thread: " + Thread.currentThread().getName());
      try {
        TimeUnit.SECONDS.sleep(new Random().nextInt(3));
      } catch (InterruptedException e) {
        throw new IllegalStateException(e);
      }
      return s;
    }, scalaGlobalEc);
  }
}
