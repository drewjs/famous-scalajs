package admin.screens

import scalatags.JsDom.all._
import rx._
import admin._

object Common {

  // Define common tags
  val v: HtmlTag = a(href:="javascript:void(0)")
  def row(rowCls: String = ""): HtmlTag = div(cls:=s"row $rowCls")
  def column(columnCls: String = ""): HtmlTag = div(cls:=s"column $columnCls")
  val page: HtmlTag = div(id:="content")

  val hiddenSubmit: HtmlTag = input(`type`:="submit",value:="",style:="display:none")

  // link to change screen
  def linkTo(screen: Screen)(content: HtmlTag): HtmlTag = {
    v(onclick:={ () => Admin.screen() = screen})(content)
  }

  // images
  val userImg: String = "http://placehold.it/500&text=USER"

  def getActiveScreenCls(screen: Screen): String = {
    if(Admin.screen() == screen) "active" else ""
  }
}
