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
  lazy val df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS")
  lazy val valuekey = "value"

  def index = Action {
    Ok {
      if (file.exists) Source.fromFile(file).getLines.mkString("\n")
      else "おきのどくですが ふぁいる1ばんは きえてしまいました"
    }
  }

  def reset = Action {
    file.delete
    Ok("Reset!")
  }

  def write = Action { implicit request =>
    val value = request.getQueryString(valuekey).getOrElse("")
    val pw = new PrintWriter(new FileWriter(file, true))
    val str = df.format(Calendar.getInstance(timeZone).getTime) + " " + value
    pw.println(str)
    pw.close
    Ok("Write : " + str)
  }
}





