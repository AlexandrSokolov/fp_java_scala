package com.savdev.fp.monad.composition.try_.scala;

import org.junit.Assert;
import org.junit.Test;
import scala.Option;
import scala.Tuple3;
import scala.math.BigDecimal;
import scala.util.Try;

public class ScalaTryFJTest {

    public static final BigDecimal transfer = BigDecimal.decimal(50);

    @Test
    public void testTransferSuccess(){
        Account from = Account.apply("1", "R", common.today(),
            Option.empty(), new Balance(BigDecimal.decimal(100)));
        Account to = Account.apply("2", "A", common.today(),
            Option.empty(), Balance.apply(BigDecimal.decimal(0)));
        Assert.assertEquals(BigDecimal.decimal(0), to.balance().amount());
        Try<Tuple3> r = AccountService.transfer(from, to, transfer);
        Assert.assertTrue(r.isSuccess());
        Assert.assertEquals(transfer,
            ((Account) r.get()._2()).balance().amount());
    }

    @Test
    public void testTransferFailed(){
        Account from = Account.apply("1", "R", common.today(),
            Option.empty(), new Balance(BigDecimal.decimal(10)));
        Account to = Account.apply("2", "A", common.today(),
            Option.empty(), Balance.apply(BigDecimal.decimal(0)));
        Try<Tuple3> r = AccountService.transfer(from, to, transfer);
        Assert.assertTrue(r.isFailure());
        Assert.assertEquals("Insufficient balance",
            r.failed().get().getMessage());
    }
}
