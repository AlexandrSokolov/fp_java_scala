package com.savdev.fp.monad.composition.future.scala

import java.util.concurrent.TimeUnit

import scala.concurrent.ExecutionContext

/**
  * Only client should decide, which execution context should be used
  * as a result methods cannot be defined as:
  * def grind(beans: CoffeeBeans): Future[GroundCoffee] = Future {...}
  * but ExecutionContext must be passed implicitly:
  * def grind(beans: CoffeeBeans)(implicit executor: ExecutionContext)
  * : Future[GroundCoffee] = Future {...}
  * only the client of component should specify execution context and pass it implicitly:
  * import scala.concurrent.ExecutionContext.Implicits.global
  * ...
  *   c.grind(...)
  */
trait CappuccinoWithoutExecutionContext {

  import scala.concurrent.Future
  import scala.util.Random
  import com.savdev.fp.monad.composition.future.scala.Cappuccino._

  def grind(beans: CoffeeBeans)(implicit executor: ExecutionContext)
  : Future[GroundCoffee] = Future {
    println("01.Start start grinding..., " +
      "thread: " + Thread.currentThread().getName)
    TimeUnit.SECONDS.sleep(Random.nextInt(3))
    if (beans == "baked beans") throw GrindingException("are you joking?")
    println("01.End finished grinding...")
    s"ground coffee of $beans"
  }

  def heatWater(water: Water)(implicit executor: ExecutionContext)
  : Future[Water] = Future {
    println("02.Start heating the water now, " +
      "thread: " + Thread.currentThread().getName)
    TimeUnit.SECONDS.sleep(Random.nextInt(3))
    println("02.End hot, it's hot!")
    water.copy(temperature = 85)
  }

  def frothMilk(milk: Milk)(implicit executor: ExecutionContext)
  : Future[FrothedMilk] = Future {
    println("03.Start milk frothing system engaged!, " +
      "thread: " + Thread.currentThread().getName)
    TimeUnit.SECONDS.sleep(Random.nextInt(3))
    println("03.End shutting down milk frothing system")
    s"frothed $milk"
  }

  def brew(coffee: GroundCoffee, heatedWater: Water)
          (implicit executor: ExecutionContext)
  : Future[Espresso] = Future {
    println("04.Start happy brewing :), " +
      "thread: " + Thread.currentThread().getName)
    TimeUnit.SECONDS.sleep(Random.nextInt(3))
    println("04.End it's brewed!")
    "espresso"
  }

  def combine(espresso: Espresso, frothedMilk: FrothedMilk)
             (implicit executor: ExecutionContext)
  : Future[Cappuccino.Cappuccino] = Future {
    println("05.Start happy combining :), " +
      "thread: " + Thread.currentThread().getName)
    TimeUnit.SECONDS.sleep(Random.nextInt(3))
    println("05.End it's combined!")
    "cappuccino"
  }

  // going through these steps synchroniously, wrong way:
  def prepareCappuccinoSequentially(implicit executor: ExecutionContext)
  : Future[Cappuccino.Cappuccino] = {
    for {
      ground <- grind("arabica beans")
      water <- heatWater(Water(20))
      foam <- frothMilk("milk")
      espresso <- brew(ground, water)
      cappuchino <- combine(espresso, foam)
    } yield cappuchino
  }

  // going through these steps asynchroniously:
  def prepareCappuccinoAsynchroniously(implicit executor: ExecutionContext)
  : Future[Cappuccino.Cappuccino] = {
    println("Preparing cappucchino without specifiying execution context in API")
    val groundCoffee = grind("arabica beans")
    val heatedWater = heatWater(Water(20))
    val frothedMilk = frothMilk("milk")
    for {
      ground <- groundCoffee
      water <- heatedWater
      foam <- frothedMilk
      espresso <- brew(ground, water)
      cappuchino <- combine(espresso, foam)
    } yield (cappuchino)
  }
}
