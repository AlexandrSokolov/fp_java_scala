package com.savdev.fp.monad.composition.option.java;


import java.util.Optional;

/**
 * Fake implementtion for tests. In tests we mock this abstract method
 */
public class UserServiceJOTest implements UserServiceJO {
  @Override
  public Optional<UserJO> loadUser(String name) {
    return Optional.empty();
  }
}
