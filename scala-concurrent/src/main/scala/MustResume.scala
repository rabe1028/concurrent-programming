// Challenge 9-1
import akka.actor.SupervisorStrategy.Resume
import akka.actor.{Actor, ActorRef, ActorSystem, Inbox, OneForOneStrategy, PoisonPill, Props}

import scala.concurrent.duration._
import scala.language.postfixOps

class ParentActor extends Actor {

  val child = context.actorOf(Props[ChildActor])

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 100, withinTimeRange = 1 minute) {
      case _ => {
        print("Child Resume")
        Resume
      }
    }

  def receive = {
    case p: Props => sender() ! context.actorOf(p)
    case msg => child ! msg
  }
}

class ChildActor extends Actor {
  def receive = {
    case msg : String => println(msg)
    case e: Exception => throw e
  }
}

object MustResume extends App {
  val system = ActorSystem("MustResume")

  val parent = system.actorOf(Props[ParentActor], "parent")

  while (true) {
    parent ! new Exception
    Thread.sleep(1000)
  }

}

// Challenge 9-2
object PoisonPillTest extends App {
  val system = ActorSystem("PoisonPill")

  val inbox = Inbox.create(system)
  implicit val sender = inbox.getRef()

  val parent = system.actorOf(Props[ParentActor], "parent")
  parent ! Props[ChildActor]
  val child = inbox.receive(5 seconds).asInstanceOf[ActorRef]

  child ! "HELLO"
  println("Send PoisonPill")
  child ! PoisonPill
  child ! "HELLO"

  Thread.currentThread().join()
}