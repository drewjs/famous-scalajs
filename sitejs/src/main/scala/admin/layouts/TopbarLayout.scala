package admin.layouts

import scalatags.JsDom.all._
import scalatags.JsDom.tags2.main

object TopbarLayout {

  val wrapper = div(cls:="layout-topbar")

  def topbarLayout(inner: Seq[Modifier]): HtmlTag = {
    wrapper(
      admin.partials.TopBar.rendered,
      inner
    )
  }
}
