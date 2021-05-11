// Challenge 3-3
import java.text.SimpleDateFormat
import java.util.Date

object ThreadSafeFormatter {

  def format(date: Date): String = {
    val formatter = new SimpleDateFormat("yyyy'年'MM'月'dd'日'E'曜日'H'時'mm'分'")
    formatter format(date)
  }

}