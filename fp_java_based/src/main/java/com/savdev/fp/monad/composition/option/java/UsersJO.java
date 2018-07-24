package com.savdev.fp.monad.composition.option.java;


import java.util.Optional;

/**
 * Factory for users
 */
public class UsersJO {
  public static UserJO userWithGrandChild(
    final String user,
    final String child,
    final String grandChild) {
    UserJO grChild = new UserJO(grandChild);
    UserJO ch = new UserJO(child, Optional.of(grChild));
    return new UserJO(user, Optional.of(ch));
  }

  public static UserJO userWithChild(
    final String user,
    final String child) {
    UserJO ch = new UserJO(child);
    return new UserJO(user, Optional.of(ch));
  }

  public static UserJO userNoChild(final String user) {
    return new UserJO(user);
  }
}
