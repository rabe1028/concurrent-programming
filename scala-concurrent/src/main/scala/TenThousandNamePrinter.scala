import java.util.concurrent.{Callable, Executors}
// Challenge 1-2
//object TenThousandNamePrinter extends App {
//  for (i <- 1 to 10000) {
//    new Thread(() => {
//      Thread.sleep(1000)
//      println(Thread.currentThread().getName)
//    }).start()
//  }
//}

// Challenge 5-1
object TenThousandNamePrinter extends App {
  val es = Executors.newFixedThreadPool(100)

  (1 to 10000).map { _ =>
    es.submit(new Callable[Unit] {
      override def call(): Unit = {
        Thread.sleep(1000)
        println(Thread.currentThread().getName)
      }
    })
  }.foreach(_.get())

  es.shutdownNow()

}