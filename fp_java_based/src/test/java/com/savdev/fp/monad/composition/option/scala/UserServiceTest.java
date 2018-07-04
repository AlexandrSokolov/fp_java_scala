package com.savdev.fp.monad.composition.option.scala;

import scala.Option;

/**
 * Fake implementtion for tests. In tests we mock this abstract method
 */
public class UserServiceTest implements UserService {
    @Override
    public Option<User> loadUser(String name) {
        return Option.empty();
    }
}
