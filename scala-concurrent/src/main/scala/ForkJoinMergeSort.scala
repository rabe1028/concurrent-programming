// Challenge 6-3
import java.util.concurrent.RecursiveTask
import scala.concurrent.forkjoin.ForkJoinPool
import scala.util.Random

object ForkJoinMergeSort extends App {
  val length = 100
  val randomList = (for (i <- 1 to length) yield Random.nextInt(100)).toList
  println(randomList)

  val pool = new ForkJoinPool()

  class AggregateTask(list: List[Int]) extends RecursiveTask[List[Int]] {

    override def compute(): List[Int] = {
      val n = list.length / 2
      if (n == 0) list match {
        case List() => Nil
        case List(n) => List(n)
      } else {
        val (left, right) = list.splitAt(n)
        val leftTask = new AggregateTask(left)
        val rightTask = new AggregateTask(right)
        leftTask.fork()
        rightTask.fork()
        // merge
        def mergeRec(left: List[Int], right: List[Int]) : List[Int] = {
          (left, right) match {
            case (List(), r) => r
            case (l, List()) => l
            case (l :: ls, r :: rs) =>
              if (l < r) l :: mergeRec(ls, r :: rs)
              else r :: mergeRec(l :: ls, rs)
          }
        }
        mergeRec(leftTask.join(), rightTask.join())

      }
    }
  }


  val sortedList = pool.invoke(new AggregateTask(randomList))
  println(sortedList)
}