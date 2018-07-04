package com.savdev.fp.monad.composition.option.scala;

import scala.Option;

/**
 *  Factory for users
 *
 */
public class Users {
    public static User userWithGrandChild(
        final String user,
        final String child,
        final String grandChild){
        User grChild = new User(grandChild);
        User ch = new User(child, Option.apply(grChild));
        return new User(user, Option.apply(ch));
    }

    public static User userWithChild(
        final String user,
        final String child) {
        User ch = new User(child);
        return new User(user, Option.apply(ch));
    }

    public static User userNoChild(final String user){
        return new User(user);
    }
}
