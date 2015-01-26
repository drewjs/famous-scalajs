package admin

import scala.scalajs._
import scala.scalajs.js.annotation.JSExport
import js.Dynamic.{ global => g }
import org.scalajs.dom
import scalatags.JsDom.all._
import rx._

import screens._
import screens.Common._


trait Screen
case object LoginScreen extends Screen
case object LandingScreen extends Screen
case object UsersScreen extends Screen
case object GamesScreen extends Screen
case object PlatformsScreen extends Screen

@JSExport
object Admin {

  import Framework._

  // default screen
  lazy val screen: Var[Screen] = Var(LoginScreen)

  // get current screen
  private lazy val current: Rx[HtmlTag] = Rx {
    screen() match {
      case LoginScreen => Login.screen()
      case LandingScreen => Landing.screen()
      case UsersScreen => Users.screen()
      case GamesScreen => Games.screen()
      case PlatformsScreen => Platforms.screen()
      case _ => page("page missing")
    }
  }

  val screenObs = Obs(screen) {
    refreshFoundation()
  }

  // foundation helper
  def refreshFoundation() = {
    dom.setTimeout({ () =>
      g.$(g.document).foundation();
    },10)
  }


  // main view
  val view = {
    div(cls:="full-height")(
      partials.TopBar.topbar,
      current
    )
  }

  @JSExport
  def main(): Unit = {
    dom.document.body.appendChild(view.render)
  }

  @JSExport
  def shoutout(id: String, message: String): Unit = {
    val elem = dom.document.getElementById(id)
    elem.appendChild(span(message).render)
  }
}
