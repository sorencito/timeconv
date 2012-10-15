package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._


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
          val date : java.util.Date = new java.util.Date()
          date.setTime(java.lang.Long.valueOf(millis))
          Ok(views.html.index(date.toString(), conversionForm))
      }
    )
  }

  val conversionForm = Form(
    "millis" -> longNumber
  )

}