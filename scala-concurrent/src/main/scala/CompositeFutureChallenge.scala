import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Random, Success}

object CompositeFutureChallenge extends App {

  val random = new Random()
  val f1 = Future {
    random.nextInt(10)
  }
  val f2 = Future {
    random.nextInt(10)
  }
  val f3 = Future {
    random.nextInt(10)
  }
  val f4 = Future {
    random.nextInt(10)
  }

  // Challenge 7-1
  val compositeFuture: Future[Int] = List(f1, f2, f3, f4).fold(Future { 1 }) {(acc, item) =>
    acc.zipWith(item) { (t1, t2) => t1 * t2 }
  }

  compositeFuture onComplete {
    case Success(value) => println(value)
    case Failure(throwable) => throwable.printStackTrace()
  }
  Await.result(compositeFuture, Duration.Inf)
}