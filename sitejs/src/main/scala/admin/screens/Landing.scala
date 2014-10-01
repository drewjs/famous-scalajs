package admin.screens

import scalatags.JsDom.all._
import Common._

object Landing {

  val screen: HtmlTag = {
    page(
      row()(column()(
        h1("Landing Screen")
      ))
    )
  }
}
