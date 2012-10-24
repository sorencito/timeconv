package controllers

import play.api._
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
    Ok(views.html.index("no time computed", conversionForm))
  }

  def conversion = Action {
    implicit request =>
    conversionForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index("nothing to compute", errors)),
      millis => {

          val timeInAnotherTimezone = new DateTime(millis)

          val marketCentreTime = timeInAnotherTimezone.withZone(DateTimeZone.forID("Europe/Berlin"))

          Ok(views.html.index(marketCentreTime.toString(DateTimeFormat.forPattern("dd. MMMM, yyyy, hh:mm:ss")) + " Europe/Berlin"+
            " <<<<>>>> " +
            timeInAnotherTimezone.toString(DateTimeFormat.forPattern("dd. MMMM, yyyy, hh:mm:ss")) + " server time"
            , conversionForm))

      }
    )
  }

  val conversionForm = Form(
    "millis" -> longNumber
  )

}