package com.savdev.fp.monad.composition.option.scala;

import scala.Option;

/**
 * Fake implementtion for tests. In tests we mock this abstract method
 */
public class UserServiceSOTest implements UserServiceSO {
  @Override
  public Option<UserSO> loadUser(String name) {
    return Option.empty();
  }
}
