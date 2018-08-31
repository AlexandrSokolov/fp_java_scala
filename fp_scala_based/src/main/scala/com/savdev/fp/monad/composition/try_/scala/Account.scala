package com.savdev.fp.monad.composition.try_.scala

import java.util.{Calendar}

object common {
  type Amount = BigDecimal

  def today = Calendar.getInstance.getTime
}


import common._

case class Balance(amount: Amount = 0)


import java.util.{Date}

case class Account(no: String,
                   name: String,
                   dateOfOpening: Date = today,
                   dateOfClosing: Option[Date] = None,
                   balance: Balance = Balance(0))
