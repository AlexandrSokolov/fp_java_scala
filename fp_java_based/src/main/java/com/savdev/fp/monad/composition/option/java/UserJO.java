package com.savdev.fp.monad.composition.option.java;


import java.util.Optional;

public class UserJO {
    public final String name;
    public final Optional<UserJO> child;

    public UserJO(String name) {
        this.name = name;
        child = Optional.empty();
    }

    public UserJO(String name, Optional<UserJO> child) {
        this.name = name;
        this.child = child;
    }
}
