// Challenge 1-1
object QuadNumberPrinter extends App {
  private var counter = 0

  def next(): Int = synchronized {
    counter = counter + 1
    counter
  }

  for (i <- 1 to 4) {
    new Thread(() => {
      while(("Thread-" + counter.toString) != Thread.currentThread().getName) {
        Thread.`yield`()
      }
      for(j <- 1 to 100000)  println(Thread.currentThread().getName + ": " + j.toString)
      next()
    }).start()
  }
}

