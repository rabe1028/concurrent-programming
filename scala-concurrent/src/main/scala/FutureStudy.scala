import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object FutureStudy extends App {

//  val s = "Hello"
//  val f: Future[String] = Future {
//    Thread.sleep(1000)
//    s + " future!"
//  }
//
//  f.foreach { case s: String =>
//    println(s)
//  }
//
//  println(f.isCompleted) // false
//
//  Thread.sleep(5000) // Hello future!
//
//  println(f.isCompleted) // true
//
//  val f2: Future[String] = Future {
//    Thread.sleep(1000)
//    throw new RuntimeException("わざと失敗")
//  }
//
//  f2.failed.foreach { case e: Throwable =>
//    println(e.getMessage)
//  }
//
//  println(f2.isCompleted) // false
//
//  Thread.sleep(5000) // わざと失敗
//
//  println(f2.isCompleted) // true

  val s = "Hello"
  val f: Future[String] = Future {
    Thread.sleep(1000)
    println(s"[ThreadName] In Future: ${Thread.currentThread.getName}")
    s + " future!"

  }

  f.foreach { case s: String =>
    println(s"[ThreadName] In Success: ${Thread.currentThread.getName}")
    println(s)
  }

  println(f.isCompleted) // false

  Await.ready(f, 5000 millisecond) // Hello future!

  println(s"[ThreadName] In App: ${Thread.currentThread.getName}")
  println(f.isCompleted) // true

}