package com.savdev.fp.monad.composition.option.java;


import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class JavaOptionalTest {

  UserServiceJO userServiceJOTest = spy(new UserServiceJOTest());

  @Test
  public void testGrandChildExistsViaFlatMap() {
    when(userServiceJOTest.loadUser("u")).thenReturn(Optional.of(
      UsersJO.userWithGrandChild("u", "c", "gc")));
    Optional<UserJO> result = userServiceJOTest.getGrandChildViaFlatMap("u");
    Assert.assertNotNull(result);
    Assert.assertTrue(result.isPresent());
    Assert.assertEquals("gc", result.get().name);
  }

  @Test
  public void testOnlyChildExistsViaFlatMap() {
    when(userServiceJOTest.loadUser("u")).thenReturn(Optional.of(
      UsersJO.userWithChild("u", "c")));
    Optional<UserJO> result = userServiceJOTest.getGrandChildViaFlatMap("u");
    Assert.assertNotNull(result);
    Assert.assertFalse(result.isPresent());
  }

  @Test
  public void testUserWithoutChildViaFlatMap() {
    when(userServiceJOTest.loadUser("u")).thenReturn(Optional.of(
      UsersJO.userNoChild("u")));
    Optional<UserJO> result = userServiceJOTest.getGrandChildViaFlatMap("u");
    Assert.assertNotNull(result);
    Assert.assertFalse(result.isPresent());
  }
}