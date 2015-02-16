package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("yolo2"))
  }

  def admin = Action {
    Ok(views.html.admin("Admin Panel"))
  }

  def jsRoutes = Action { implicit request =>
    Ok(Routes.javascriptRouter("jsRoutes")(
      // jsRoutes
    )).as("text/javascript")
  }
}
