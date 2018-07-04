package com.savdev.fp.monad.composition.option.scala;

import scala.Option;

public interface UserService {
    Option<User> loadUser(final String name);
    default Option<User> getGrandChildViaFlatMap(final String name) {
        return loadUser(name)
            .flatMap(u -> u.child)
            .flatMap(c -> c.child);
    }
}
