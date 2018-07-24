package com.savdev.fp.monad.composition.option.scala;

import org.junit.Assert;
import org.junit.Test;
import scala.Option;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class ScalaOptionTest {

  UserServiceSO userServiceSOMock = spy(new UserServiceSOTest());

  @Test
  public void testGrandChildExistsViaFlatMap() {
    when(userServiceSOMock.loadUser("u")).thenReturn(Option.apply(
      UsersSO.userWithGrandChild("u", "c", "gc")));
    Option<UserSO> result = userServiceSOMock.getGrandChildViaFlatMap("u");
    Assert.assertNotNull(result);
    Assert.assertFalse(result.isEmpty());
    Assert.assertEquals("gc", result.get().name);
  }

  @Test
  public void testOnlyChildExistsViaFlatMap() {
    when(userServiceSOMock.loadUser("u")).thenReturn(Option.apply(
      UsersSO.userWithChild("u", "c")));
    Option<UserSO> result = userServiceSOMock.getGrandChildViaFlatMap("u");
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isEmpty());
  }

  @Test
  public void testUserWithoutChildViaFlatMap() {
    when(userServiceSOMock.loadUser("u")).thenReturn(Option.apply(
      UsersSO.userNoChild("u")));
    Option<UserSO> result = userServiceSOMock.getGrandChildViaFlatMap("u");
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isEmpty());
  }
}