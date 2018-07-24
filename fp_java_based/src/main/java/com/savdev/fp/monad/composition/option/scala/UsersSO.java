package com.savdev.fp.monad.composition.option.scala;

import scala.Option;

/**
 * Factory for users
 */
public class UsersSO {
  public static UserSO userWithGrandChild(
    final String user,
    final String child,
    final String grandChild) {
    UserSO grChild = new UserSO(grandChild);
    UserSO ch = new UserSO(child, Option.apply(grChild));
    return new UserSO(user, Option.apply(ch));
  }

  public static UserSO userWithChild(
    final String user,
    final String child) {
    UserSO ch = new UserSO(child);
    return new UserSO(user, Option.apply(ch));
  }

  public static UserSO userNoChild(final String user) {
    return new UserSO(user);
  }
}
