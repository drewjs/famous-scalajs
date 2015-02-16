package admin.screens

import admin.LocalProfile
import org.scalajs.dom

import scalatags.JsDom.all._
import Common._

object Login {

  val loginHeader: HtmlTag = {
    h1("GameTime Login")
  }

  val EMAIL_ID = "login-email"
  val PASSWORD_ID = "login-password"

  val loginForm: dom.html.Form = {
    form(onsubmit:={ () => loginFormHandler()})(
      input(id:=EMAIL_ID,`type`:="text", placeholder:="email"),
      input(id:=PASSWORD_ID,`type`:="password", placeholder:="password"),
      hiddenSubmit,
      row()(column("text-center")(
        v(cls:="button", onclick:={ () => loginFormHandler() })(
          "Sign In"
        )
      ))
    ).render
  }

  def loginFormHandler(): Boolean = {
    val email = dom.document.getElementById(EMAIL_ID).asInstanceOf[dom.html.Input].value
    val pw = dom.document.getElementById(PASSWORD_ID).asInstanceOf[dom.html.Input].value

    LocalProfile.user() = Option(LocalProfile.fakeUser)
    loginForm.reset()
    false
  }

  val screen: HtmlTag = {
    page(
      row()(column("text-center")(
        loginHeader
      )),
      row()(column("medium-8 medium-centered large-6")(
        loginForm
      ))
    )
  }
}
