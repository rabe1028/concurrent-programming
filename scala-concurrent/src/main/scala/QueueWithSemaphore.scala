// Challenge 4-3
import java.util.concurrent.{CopyOnWriteArrayList, Semaphore}

object QueueWithSemaphore extends App {

  val arrayList = new CopyOnWriteArrayList[Runnable]()

  for (i <- 1 to 100) {
    arrayList.add(() => {
      Thread.sleep(1000)
      println(s"Runnable: ${i} finished.")
    })
  }

  val semaphore = new Semaphore(10)

  for (i <- 1 to 20) {
    val t = new Thread(() => {
      while (!arrayList.isEmpty) {
        semaphore.acquire()
        if (!arrayList.isEmpty) {
          try {
            // semaphoreより後でないと、始めに20個リストから取られることがある。そのために番号の最大値は10個ごと進むけど、始めに20個進む。
            val runnable = arrayList.remove(0)
            runnable.run()
          } finally {
            semaphore.release()
          }
        }
      }
    })
    t.start()
  }
}