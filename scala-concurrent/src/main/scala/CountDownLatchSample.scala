
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise, promise}
import scala.util.{Random, Success}

object CountDownLatchSample extends App {
  // Challenge 7-3
  val random = new Random()
  var promises = for (i <- 1 to 3) yield Promise[Int]
  val futures = for ( i <- 1 to 10)  yield Future[Int] {
    val waitMillis = random.nextInt(1000)
    Thread.sleep(waitMillis)
    promises.takeWhile { item =>
      if (item.isCompleted) {
        true
      } else {
        item.success(waitMillis)
        false
      }
    }
    waitMillis
  }

  promises.map(_.future).map(_.map(println(_)))

  Thread.sleep(5000)
}