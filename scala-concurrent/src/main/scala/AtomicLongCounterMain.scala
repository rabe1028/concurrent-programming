// Challenge 2-1
import java.util.concurrent.atomic.AtomicLong

object AtomicLongCounterMain extends App {

  for (i <- 1 to 100) {
    new Thread(() => println(AtomicLongCounter.next)).start()
  }

}

object AtomicLongCounter {
  val count = new AtomicLong()

  def next: Long = count.addAndGet(1)

}