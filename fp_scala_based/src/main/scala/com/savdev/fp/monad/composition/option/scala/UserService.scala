package com.savdev.fp.monad.composition.option.scala

object User {
  def userWithGrandChild(user:String, child:String,
                         grandChild:String):User = {
    val grChild = SimpleUser(grandChild)
    val ch = SimpleUser(child, Some(grChild))
    SimpleUser(user, Some(ch))
  }

  def userWithChild(user:String,
                    child:String):User = {
    val ch = SimpleUser(child)
    SimpleUser(user, Some(ch))
  }

  def userNoChild(user:String):User = {
    SimpleUser(user)
  }
}

trait User {
  val name:String
  val child: Option[User]
}

case class SimpleUser(name:String,
                      child:Option[User]=None)
  extends User

trait UserService {
  def loadUser(name: String): Option[User]

/**
  *   We try to get grand child.
  *   To do that we need to invoke these three functions:
  *   String
  **/

  def getGrandChildViaFlatMapChain(name:String):Option[User] =
    loadUser(name)
      .flatMap(x => x.child )
        .flatMap(x => x.child)

  def getGrandChildViaFlatMapWithoutChain(name:String):Option[User] =
    loadUser(name).flatMap {
      us =>
        us.child flatMap {
          ch => ch.child
        }
    }

  def getGrandChildViaForComprehension(name:String):Option[User] =
    for {
      u <- loadUser(name)
      c <- u.child
      gc <- c.child
    } yield gc
}
