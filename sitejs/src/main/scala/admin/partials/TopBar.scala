package admin.partials

import org.scalajs.dom
import rx._
import admin._
import common.Framework._
import common.Common._
import admin.Helpers._
import scalatags.JsDom.all._
import scalatags.JsDom.tags2.{nav, section}

object TopBar {

  private val mainBar: HtmlTag = {
    nav(cls:="top-bar", "data-topbar".attr:="", "role".attr:="navigation")(
      ul(cls:="title-area")(
        li(cls:="name")(h1(
          linkTo(DashboardScreen)(span("Spark Admin"))
        )),
        li(cls:="toggle-topbar menu-icon")(v(span))
      )
    )
  }

  private val leftNav: Rx[HtmlTag] = Rx {
    ul(cls:="left alt-bg nav-list")(
      rxNavItem(DashboardScreen)(span(span(cls:="fa fa-bar-chart fa-lg fa-fw icon")," Dashboard"))
    )
  }

  val rightNav: HtmlTag = {
    ul(cls:="right")(
      li(cls:="has-dropdown")(
        v(img(cls:="avatar x-small", src:=common.Common.userImg), " admin"),
        ul(cls:="dropdown")(
          li(v(
            span(cls:="fa fa-key fa-lg fa-fw"),
            "Sign Out"
          ))
        )
      )
    )
  }

  val rendered: dom.html.Element = {
    mainBar(
      section(cls:="top-bar-section")(
        leftNav,
        rightNav
      )
    ).render
  }
}
