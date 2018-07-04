package com.savdev.fp.monad.composition.option.scala;

import scala.Option;

public class User {
    public final String name;
    public final Option<User> child;

    public User(String name) {
        this.name = name;
        child = Option.empty();
    }

    public User(String name, Option<User> child) {
        this.name = name;
        this.child = child;
    }
}
