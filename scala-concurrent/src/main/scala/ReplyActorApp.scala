// Challenge 8-2
import akka.actor.{Actor, ActorSystem, Props}
import akka.event.Logging

class Alice extends Actor {
  var log = Logging(context.system, this)
  def receive = {
    case msg: Int =>
      log.info("Alice : " + (msg + 1))
      Thread.sleep(100)
      sender ! msg + 1
  }
}

class Bob extends Actor {
  var log = Logging(context.system, this)
  def receive = {
    case msg: Int =>
      log.info("Bob : " + msg)
      Thread.sleep(100)
      sender ! msg
  }
}

object ReplyActorApp extends App {
  val system = ActorSystem("MessageCount")

  val alice = system.actorOf(Props[Alice], "alice")
  val bob   = system.actorOf(Props[Bob], "bob")

  alice.tell(0, bob)
}


