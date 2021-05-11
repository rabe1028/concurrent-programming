import java.util.concurrent.{ForkJoinPool, RecursiveTask}

object ForkJoinSumStudy extends App {

  val length = 10000000
  val list = (for (i <- 1 to length) yield i.toLong).toList

  val pool = new ForkJoinPool()

  class AggregateTask(list: List[Long]) extends RecursiveTask[Long] {

    override def compute(): Long = {
      val n = list.length / 2
      if (n == 0) {
        list match {
          case List() => 0
          case List(n) => n
        }
      } else {
        val (left, right) = list.splitAt(n)
        val leftTask = new AggregateTask(left)
        val rightTask = new AggregateTask(right)
        leftTask.fork()
        rightTask.fork()
        leftTask.join() + rightTask.join()
      }
    }
  }

  val start = System.currentTimeMillis()
  val sum = pool.invoke(new AggregateTask(list))
  val time = System.currentTimeMillis() - start

  println(sum)
  println(s"time: ${time} msec")
}