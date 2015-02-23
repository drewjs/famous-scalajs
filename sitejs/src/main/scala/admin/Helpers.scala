package admin

import rx._
import common.Common._
import common.Framework._
import scalatags.JsDom.all._
import admin.{AdminScreen, Admin}

object Helpers {

  def linkTo(screen: AdminScreen)(content: HtmlTag): HtmlTag = {
    v(onclick:={ () => Admin.router.linkTo(screen)})(content)
  }

  def rxNavItem(screen: admin.AdminScreen)(content: HtmlTag): HtmlTag = {
    li(cls := Rx {s"${if(Admin.router.current() == screen) "active" else ""}"})(Helpers.linkTo(screen)(content))
  }

  def zoomcrop(src: String, divCls: String = ""): HtmlTag = {
    div(cls:=s"zoomcrop $divCls", style:=s"background-image: url('$src');")
  }

  def limitChars(str: String, limit: Int): String = {
    if(str.length > limit) str.take(limit) + "..."
    else str
  }

  def disabledNavItem(content: HtmlTag): HtmlTag = {
    li(v(cls:="disable")(content))
  }
}
