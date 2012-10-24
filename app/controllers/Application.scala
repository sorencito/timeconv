package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import org.joda.time._
import org.joda.time.format._

object Application extends Controller {

  def index = Action {
    Redirect(routes.Application.conversions)
  }

  def conversions = Action {
    Ok(views.html.index("no time computed", "", conversionForm))
  }

  def conversion = Action {
    implicit request =>
      conversionForm.bindFromRequest.fold(
        errors => BadRequest(views.html.index("bad user input", "", errors)),
        millis => {
          // get dateTime object from user request
          val dateTime = new DateTime(millis)

          // do the conversion
          val europeTime = dateTime.withZone(DateTimeZone.forID("Europe/Berlin"))
          val utcTime = dateTime.withZone(DateTimeZone.forID("UTC"))

          // init result display pattern
          val pattern = "dd. MMMM, yyyy, hh:mm:ss.SS"

          Ok(views.html.index(
            europeTime.toString(DateTimeFormat.forPattern(pattern)) + " (Europe/Hamburg)",
            utcTime.toString(DateTimeFormat.forPattern(pattern)) + " (UTC)",
            conversionForm))

        }
      )
  }

  val conversionForm = Form(
    "millis" -> longNumber
  )

}