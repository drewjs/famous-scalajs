package admin

import common.{Framework, Common}
import upickle._
import locallink._
import locallink.internal._ //TODO FIGURE OUT HOW TO NOT NEED THIS
import scala.concurrent.{Future,ExecutionContext} //TODO FIGURE OUT HOW TO NOT NEED THIS (if only for locallink)
import scalajs.concurrent.JSExecutionContext.Implicits.queue

import scala.scalajs._
import scala.scalajs.js.annotation.JSExport
import js.Dynamic.{ global => g }
import org.scalajs.dom
import scalatags.JsDom.all._
import rx._

import screens._
import Common._


sealed trait AdminScreen
case object DashboardScreen extends AdminScreen

object Admin extends js.JSApp {

  import Framework._

  val router: Router[AdminScreen] = Router.generate[AdminScreen](DashboardScreen)

  // get current screen
  private lazy val current: Rx[HtmlTag] = Rx {
    router.current() match {
      case DashboardScreen => Dashboard.screen()
      case _ => page("page missing")
    }
  }

  val screenObs = Obs(router.current) {
    refreshFoundation()
  }

  // foundation helper
  def refreshFoundation() = {
    dom.setTimeout({ () =>
      g.$(g.document).foundation();
    },10)
  }

  def main(): Unit = {
    dom.document.body.appendChild(div(cls:="fill-height")(current).render)
  }

  @JSExport
  def shoutout(id: String, message: String): Unit = {
    val elem = dom.document.getElementById(id)
    elem.appendChild(span(message).render)
  }
}
