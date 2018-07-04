package com.savdev.fp.monad.composition.option.scala;

import org.junit.Assert;
import org.junit.Test;
import scala.Option;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class ScalaOptionTest {

    UserService userServiceMock = spy(new UserServiceTest());

    @Test
    public void testGrandChildExistsViaFlatMap(){
        when(userServiceMock.loadUser("u")).thenReturn(Option.apply(
            Users.userWithGrandChild("u", "c","gc")));
        Option<User> result = userServiceMock.getGrandChildViaFlatMap("u");
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        Assert.assertEquals("gc", result.get().name);
    }
}