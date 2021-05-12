// Challenge 8-1
import akka.actor.{Actor, ActorSystem, Props}

class Counter extends Actor {

  private[this] var count = 0

  def receive = {
    case _ : String =>
      count = count + 1
      println(count)
  }
}

object MessageCountActorApp extends App {
  val system = ActorSystem("MessageCount")

  val counter = system.actorOf(Props[Counter], "counter")

  for (_ <- 1 to 10000) {
    counter ! "send"
  }

  Thread.currentThread().join()
}

