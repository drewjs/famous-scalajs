package admin.screens.partials

import admin._
import admin.LocalProfile.User
import admin.Framework._
import rx._

import scalatags.JsDom.all._
import scalatags.JsDom.tags2.{nav, section}
import admin.screens.Common.{v, userImg, linkTo, getActiveScreenCls}

object TopBar {

  private val stickyWrapper: HtmlTag = div(id:="topbar")

  private val mainBar: HtmlTag = {
    nav(cls:="top-bar", "data-topbar".attr:="", "role".attr:="navigation")(
      ul(cls:="title-area")(
        li(cls:="name")(h1(
          linkTo(admin.LandingScreen)(span("GameTime"))
        )),
        li(cls:="toggle-topbar menu-icon")(v(span))
      )
    )
  }

  private val leftNav: Rx[HtmlTag] = Rx {
    ul(cls:="left")(
      navItem(span(span(cls:="fa fa-home fa-lg fa-fw"),"Dashboard"), LandingScreen),
      navItem(span(span(cls:="fa fa-user fa-lg fa-fw"),"Users"), UsersScreen),
      navItem(span(span(cls:="fa fa-gamepad fa-lg fa-fw"),"Games"), GamesScreen),
      navItem(span(span(cls:="fa fa-hdd-o fa-lg fa-fw"),"Platforms"), PlatformsScreen)
    )
  }

  private def rightNav(user: User): HtmlTag = {
    ul(cls:="right")(
      li(cls:="has-dropdown")(
        v(img(cls:="avatar", src:=userImg), "Andrew"),
        ul(cls:="dropdown")(
          li(v(onclick:={ () => LocalProfile.clearUserData() })(
            span(cls:="fa fa-key fa-lg fa-fw"),
            "Sign Out"
          ))
        )
      )
    )
  }

  private def navItem(title: HtmlTag, screen: admin.Screen): HtmlTag = {
    li(cls:=Rx{getActiveScreenCls(screen)})(
      linkTo(screen)(title)
    )
  }

  private def defaultBar(user: User): HtmlTag = {
    stickyWrapper(
      mainBar(
        section(cls:="top-bar-section")(
          leftNav,
          rightNav(user)
        )
      )
    )
  }

  val topbar: Rx[HtmlTag] = Rx {
    LocalProfile.user() match {
      case Some(user) => defaultBar(user)
      case _ => div
    }
  }
}
