package admin

import rx._
import org.scalajs.dom
import org.scalajs.dom.{PopStateEvent, Event}
import scalatags.JsDom.all._

trait LocalLink {
  def toFragment: String
}

object LocalLink {

  val url: Var[LocalLink] = Var(Index)

  trait Content extends LocalLink
  case object Index extends Content { def toFragment = "/admin/index"}
  case object Login extends Content { def toFragment = "/admin/login"}
  case class About(topic: String) extends Content { def toFragment = "/admin/about"}

  private def loadRoute(fragment: String) = {
    fragment match {
      case "/admin/login" => url() = Login
      case _ => url() = Index
    }
  }

  def linkTo(link: LocalLink, fixthis: String = "button"): HtmlTag = {
    a(cls:=fixthis,
      onclick := { () =>
        dom.console.log("LinkTo clicked...")
        dom.history.pushState(link.toFragment,"???",link.toFragment)
        LocalLink.url() = link
        false
      }
    )
  }

  dom.onpopstate = { (e:PopStateEvent) =>
    loadRoute(e.state.asInstanceOf[String])
  }

  dom.onload = { (e:Event) =>
    loadRoute(dom.window.location.pathname)
  }
}
