// Challenge 9-3
import akka.actor.ActorRef.noSender
import akka.actor.{Actor, ActorSystem, Inbox, Props}
import akka.event.Logging
import akka.event.jul.Logger
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, DurationInt}
import scala.language.postfixOps

case class AnalyzeNumber(num: Int)
case class PrimeNumber(num: Int)
case class NonPrimeNumber(num: Int)
case class AnalyzeRange(start: Int, end: Int)
case class AnalyzeAnswer(num: Int)

class PrimeSearcher extends Actor {
  var log = Logging(context.system, this)

  def isPrime(n: Int): Boolean = if (n < 2) false else ! ((2 until n-1) exists (n % _ == 0))

  def receive = {
    case AnalyzeNumber(num) =>
      log.info("search : " + num)
      if (isPrime(num)) sender() ! PrimeNumber(num)
      else sender() ! NonPrimeNumber(num)
  }
}

class PrimeAggregator extends Actor {
  private var sum = 0
  private var searchedCount = 0
  private var receivedCount = 0
  private var master = noSender

  val router = {
    val routee = Vector.fill(16) {
      ActorRefRoutee(context.actorOf(Props[PrimeSearcher]))
    }
    Router(RoundRobinRoutingLogic(), routee)
  }

  def receive = {
    case AnalyzeRange(start, end) =>
      master = sender()
      searchedCount = end - start + 1
      for (i <- start to end) {
        router.route(AnalyzeNumber(i), self)
      }
    case PrimeNumber(num) =>
      receivedCount += 1
      sum +=  1
      if (receivedCount == searchedCount) {
        master ! AnalyzeAnswer(sum)
      }
    case _: NonPrimeNumber =>
      receivedCount += 1
      if (receivedCount == searchedCount) {
        master ! AnalyzeAnswer(sum)
      }
  }
}

object PrimeNumberSearch extends App {
  val system = ActorSystem("PrimeNumberSearch")

  val inbox = Inbox.create(system)
  implicit val sender = inbox.getRef()

  val aggregator = system.actorOf(Props[PrimeAggregator], "primeAggregator")
  aggregator ! AnalyzeRange(1010000, 1040000)
  val AnalyzeAnswer(result) = inbox.receive(1000 seconds)

  println(s"Result: ${result}")

  Await.ready(system.terminate(), Duration.Inf)
}