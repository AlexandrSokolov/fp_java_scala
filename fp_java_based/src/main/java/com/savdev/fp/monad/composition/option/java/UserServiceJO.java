package com.savdev.fp.monad.composition.option.java;


import java.util.Optional;

public interface UserServiceJO {
  Optional<UserJO> loadUser(final String name);

  default Optional<UserJO> getGrandChildViaFlatMap(final String name) {
    return loadUser(name)
      .flatMap(u -> u.child)
      .flatMap(c -> c.child);
  }
}
