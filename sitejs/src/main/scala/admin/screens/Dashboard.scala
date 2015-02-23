package admin.screens

import scalatags.JsDom.all._
import admin.layouts.TopbarLayout._
import common.Common._

object Dashboard {

  val screen: HtmlTag = {
    topbarLayout{Seq(
      page(
        row()(column()(
          div(cls:="panel")(
            h1("Dashboard")
          )
        ))
      )
    )}
  }
}
