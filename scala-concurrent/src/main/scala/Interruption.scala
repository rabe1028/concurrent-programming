// Challenge 4-1
object Interruption extends App {
  val th = new Thread(() => {
    try {
      while (true) {
        println("Sleeping...")
        Thread.sleep(1000)
      }
    } catch {
      case _: InterruptedException =>
    }
  })

  th.start()
  Thread.sleep(1500)
  th.interrupt()
}