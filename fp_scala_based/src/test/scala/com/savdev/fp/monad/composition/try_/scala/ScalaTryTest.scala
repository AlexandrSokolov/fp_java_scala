package com.savdev.fp.monad.composition.try_.scala

import com.savdev.fp.monad.composition.try_.scala.common.Amount
import org.junit.{Assert, Test}

object ScalaTryTest {
  final val transfer: Amount = 50
}

class ScalaTryTest {

  import ScalaTryTest._

  @Test def testTransferSuccess(): Unit = {
    val from = Account(no = "1", name = "R", balance = Balance(100))
    val to = Account(no = "2", name = "A")
    Assert.assertEquals(BigDecimal(0), to.balance.amount)
    val r = AccountService.transfer(from, to, transfer)
    Assert.assertEquals(transfer, r.get._2.balance.amount)
  }

  @Test def testTransferFailed(): Unit = {
    val from = Account(no = "1", name = "R", balance = Balance(10))
    val to = Account(no = "2", name = "A")
    val r = AccountService.transfer(from, to, transfer)
    Assert.assertTrue(r.isFailure)
    Assert.assertEquals("Insufficient balance", r.failed.get.getMessage)
  }
}
