package com.savdev.fp.monad.di.partial

import com.savdev.fp.monad.di.{AccountRepositoryInMemory, Balance}
import org.junit.{Assert, Test}

class TestAccountRepository extends AccountRepositoryInMemory

class AccountServiceScalazMonadTest {

  import scalaz.Scalaz._ //contains Monad for Function1
  import AccountService._

  def op(no: String) = for {
    _ <- credit(no, BigDecimal(100))
    _ <- credit(no, BigDecimal(300))
    _ <- debit(no, BigDecimal(160))
    b <- balance(no)
  } yield b

  @Test def testOpComposition: Unit = {
    val newOp = for {
      _ <- open("a-123", "Alex", Option.empty)
      b <- op("a-123")
    } yield b

    val balance = newOp(new TestAccountRepository)
    Assert.assertTrue(balance.isSuccess)
    Assert.assertEquals(Balance(240), balance.get)
    println(balance)
  }

  @Test def testOpCompositionNotExistingAccount: Unit = {
    val balance = op("a-123")(new TestAccountRepository)
    Assert.assertTrue(balance.isFailure)
    Assert.assertEquals("No account exists with no a-123", balance.failed.get.getMessage)
    println(balance)
  }
}
