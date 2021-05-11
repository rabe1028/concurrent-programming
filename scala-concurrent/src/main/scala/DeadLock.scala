// Challenge 1-3
object DeadLock extends App {
  var now: Long = 0L

  def update(next: Long): Unit = synchronized {
    now = next
  }

  val threadA = new Thread(() => {
    Thread.sleep(1000)
    update(System.currentTimeMillis())
  })
  val threadB = new Thread(() => {
    val first = now
    while(first == now) {
      Thread.`yield`()
    }
    println(now)
  })

  threadA.start()
  threadB.start()
}
