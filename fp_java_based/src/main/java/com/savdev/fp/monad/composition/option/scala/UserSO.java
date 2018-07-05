package com.savdev.fp.monad.composition.option.scala;

import scala.Option;

public class UserSO {
    public final String name;
    public final Option<UserSO> child;

    public UserSO(String name) {
        this.name = name;
        child = Option.empty();
    }

    public UserSO(String name, Option<UserSO> child) {
        this.name = name;
        this.child = child;
    }
}
