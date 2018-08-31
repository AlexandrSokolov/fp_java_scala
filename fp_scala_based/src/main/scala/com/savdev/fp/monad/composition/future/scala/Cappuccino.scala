package com.savdev.fp.monad.composition.future.scala

import java.util.concurrent.TimeUnit

import scalaz.Monad

object Cappuccino {
  type CoffeeBeans = String
  type GroundCoffee = String

  case class Water(temperature: Int)

  type Milk = String
  type FrothedMilk = String
  type Espresso = String
  type Cappuccino = String
}

// some exceptions for things that might go wrong in the individual steps
// (we'll need some of them later, use the others when experimenting
// with the code):
case class GrindingException(msg: String) extends Exception(msg)

case class FrothingException(msg: String) extends Exception(msg)

case class WaterBoilingException(msg: String) extends Exception(msg)

case class BrewingException(msg: String) extends Exception(msg)

trait Cappuccino {
  //define non-overridable implicit
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.Future
  import scala.util.Random
  import com.savdev.fp.monad.composition.future.scala.Cappuccino._

  def grind(beans: CoffeeBeans): Future[GroundCoffee] = Future {
    println("01.Start start grinding..., " +
      "thread: " + Thread.currentThread().getName)
    TimeUnit.SECONDS.sleep(Random.nextInt(3))
    if (beans == "baked beans") throw GrindingException("are you joking?")
    println("01.End finished grinding...")
    s"ground coffee of $beans"
  }

  def heatWater(water: Water): Future[Water] = Future {
    println("02.Start heating the water now, " +
      "thread: " + Thread.currentThread().getName)
    TimeUnit.SECONDS.sleep(Random.nextInt(3))
    println("02.End hot, it's hot!")
    water.copy(temperature = 85)
  }

  def frothMilk(milk: Milk): Future[FrothedMilk] = Future {
    println("03.Start milk frothing system engaged!, " +
      "thread: " + Thread.currentThread().getName)
    TimeUnit.SECONDS.sleep(Random.nextInt(3))
    println("03.End shutting down milk frothing system")
    s"frothed $milk"
  }

  def brew(coffee: GroundCoffee, heatedWater: Water)
  : Future[Espresso] = Future {
    println("04.Start happy brewing :), " +
      "thread: " + Thread.currentThread().getName)
    TimeUnit.SECONDS.sleep(Random.nextInt(3))
    println("04.End it's brewed!")
    "espresso"
  }

  def combine(espresso: Espresso, frothedMilk: FrothedMilk)
  : Future[Cappuccino.Cappuccino] = Future {
    println("05.Start happy combining :), " +
      "thread: " + Thread.currentThread().getName)
    TimeUnit.SECONDS.sleep(Random.nextInt(3))
    println("05.End it's combined!")
    "cappuccino"
  }

  // going through these steps synchroniously, wrong way:
  def prepareCappuccinoSequentially(): Future[Cappuccino.Cappuccino] = {
    for {
      ground <- grind("arabica beans")
      water <- heatWater(Water(20))
      foam <- frothMilk("milk")
      espresso <- brew(ground, water)
      cappuchino <- combine(espresso, foam)
    } yield cappuchino
  }

  // going through these steps asynchroniously:
  def prepareCappuccinoAsynchroniously(): Future[Cappuccino.Cappuccino] = {
    val groundCoffee = grind("arabica beans")
    val heatedWater = heatWater(Water(20))
    val frothedMilk = frothMilk("milk")
    for {
      ground <- groundCoffee
      water <- heatedWater
      foam <- frothedMilk
      espresso <- brew(ground, water)
      cappuchino <- combine(espresso, foam)
    } yield cappuchino
  }

  // going through these steps asynchroniously:
  def prepareCappuccinoAsynchroniouslyViaFlatMap(): Future[Cappuccino.Cappuccino] = {
    val groundCoffee = grind("arabica beans")
    val heatedWater = heatWater(Water(20))
    val frothedMilk = frothMilk("milk")
    groundCoffee.flatMap(ground => {
      heatedWater.flatMap(water => {
        brew(ground, water)
          .flatMap(espresso => {
            frothedMilk
              .flatMap(foam => {
                combine(espresso, foam)
              })
          })
      })
    })
  }
}

