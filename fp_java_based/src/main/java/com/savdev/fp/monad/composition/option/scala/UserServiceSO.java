package com.savdev.fp.monad.composition.option.scala;

import scala.Option;

public interface UserServiceSO {
    Option<UserSO> loadUser(final String name);
    default Option<UserSO> getGrandChildViaFlatMap(final String name) {
        return loadUser(name)
            .flatMap(u -> u.child)
            .flatMap(c -> c.child);
    }
}
