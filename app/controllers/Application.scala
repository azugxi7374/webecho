package controllers

import java.io.{FileWriter, FileOutputStream, PrintWriter, File}
import java.text.SimpleDateFormat
import java.util.{Calendar, TimeZone}

import play.api._
import play.api.mvc._

import scala.io.Source

object Application extends Controller {

  lazy val file = new File("log")
  lazy val timeZone = TimeZone.getTimeZone("Asia/Tokyo")
  lazy val df = {
    val df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS")
    df.setTimeZone(timeZone)
    df
  }
  lazy val valuekey = "value"

  val usage = """/write?value=hogeで書き込み,
                |/resetでリセット
                |=================================
              """.stripMargin


  def index = Action {
    Ok {
      val log: String = if (file.exists) {
        val lines = Source.fromFile(file).getLines().toList.reverse
        lines.mkString(lines.size + "\n", "\n", "")
      } else ""

      usage + log
    }
  }

  def reset = Action {
    file.delete
    Ok("reset!")
    // Redirect(routes.Application.index)
  }

  def write = Action { implicit request =>
    val value = request.getQueryString(valuekey).getOrElse("")
    val pw = new PrintWriter(new FileWriter(file, true))
    val str = df.format(Calendar.getInstance().getTime) + " " + value
    pw.println(str)
    pw.close()
    Ok("write!")
    // Redirect(routes.Application.index)
  }
}





