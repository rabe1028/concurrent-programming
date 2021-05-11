// Challenge 3-2
object MemoryVisibilityProblem2 extends App {
  var runner = new AsyncRunner2("Runner 0", () => true)

  for (i <- 1 to 10) {
    runner.asyncRun((name) => println(s"${name} is finished."))
    runner = new AsyncRunner2(s"Runner ${i}", runner.canNextStart)
  }

  runner.asyncRun((name) => println(s"${name} is finished. Totally finished."))
}

class AsyncRunner2(private[this] val name: String, private[this] val canStart: () => Boolean) {
  private[this] var isFinished = false

  def asyncRun(f: String => Unit): Unit = {
    new Thread(() => {
      while (!canStart()) {
        Thread.`yield`()
      }
      f(name)
      synchronized {
        isFinished = true
      }

    }).start()
  }

  def canNextStart: () => Boolean = () => synchronized { this.isFinished }
}