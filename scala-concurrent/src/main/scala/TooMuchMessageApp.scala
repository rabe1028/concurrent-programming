// Challenge 8-3
import akka.actor.{Actor, ActorSystem, Props}
import akka.event.Logging

class SlowMessageResponseActor extends Actor {
  var log = Logging(context.system, this)
  def receive = {
    case msg: Int =>
      log.info(msg.toString)
      Thread.sleep(1000)
  }
}

object TooMuchMessageApp extends App {
  val system = ActorSystem("TooMuchMessage")

  val slowResponse = system.actorOf(Props[SlowMessageResponseActor], "slowRes")

  for ( i <- 1 to 100000) {
    slowResponse ! i
  }
  println("Message send fin")

  Thread.currentThread().join()
}

