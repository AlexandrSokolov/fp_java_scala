package com.savdev.fp.monad.composition.option.scala

import org.junit.{Assert, Test}
import org.mockito.Mockito._

//Fake implementation for tests
class UserServiceTest extends UserServiceSO {
  override def loadUser(name: String): Option[User] = None
}

class ScalaOptionTest {
  import com.savdev.fp.monad.composition.option.scala.User._
  val userServiceMock = spy(new UserServiceTest)

  @Test def testGrandChildExistsViaFlatMap(): Unit = {
    when(userServiceMock.loadUser("u")).thenReturn(Some(
      userWithGrandChild(user="u", child="c", grandChild="gc")))
    val result = userServiceMock.getGrandChildViaFlatMapChain("u")
    Assert.assertNotNull(result)
    Assert.assertFalse(result.isEmpty)
    Assert.assertEquals("gc", result.get.name)
  }

  @Test def testGrandChildExistsViaForComprehension(): Unit = {
    when(userServiceMock.loadUser("u")).thenReturn(Some(
      userWithGrandChild(user="u", child="c", grandChild="gc")))
    val result = userServiceMock.getGrandChildViaForComprehension("u")
    Assert.assertNotNull(result)
    Assert.assertFalse(result.isEmpty)
    Assert.assertEquals("gc", result.get.name)
  }

  @Test def testGrandChildExistsViaFlatMapWithoutChain(): Unit = {
    when(userServiceMock.loadUser("u")).thenReturn(Some(
      userWithGrandChild(user="u", child="c", grandChild="gc")))
    val result = userServiceMock.getGrandChildViaFlatMapWithoutChain("u")
    Assert.assertNotNull(result)
    Assert.assertFalse(result.isEmpty)
    Assert.assertEquals("gc", result.get.name)
  }

  @Test def testOnlyChildExistsViaFlatMap(): Unit = {
    when(userServiceMock.loadUser("u"))
      .thenReturn(Some(userWithChild(user="u", child="c")))
    val result = userServiceMock.getGrandChildViaFlatMapChain("u")
    Assert.assertNotNull(result)
    Assert.assertTrue(result.isEmpty)
  }

  @Test def testOnlyChildExistsViaForComprehension(): Unit = {
    when(userServiceMock.loadUser("u"))
      .thenReturn(Some(userWithChild(user="u", child="c")))
    val result = userServiceMock.getGrandChildViaForComprehension("u")
    Assert.assertTrue(result.isEmpty)
  }

  @Test def testUserWithoutChildViaFlatMap(): Unit = {
    when(userServiceMock.loadUser("u"))
      .thenReturn(Some(userNoChild(user="u")))
    val result = userServiceMock.getGrandChildViaFlatMapChain("u")
    Assert.assertTrue(result.isEmpty)
  }

  @Test def testUserWithoutChildViaForComprehension(): Unit = {
    when(userServiceMock.loadUser("u"))
      .thenReturn(Some(userNoChild(user="u")))
    val result = userServiceMock.getGrandChildViaForComprehension("u")
    Assert.assertTrue(result.isEmpty)
  }
}
