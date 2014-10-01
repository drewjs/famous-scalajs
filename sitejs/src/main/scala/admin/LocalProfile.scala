package admin

import rx._

object LocalProfile {

  case class UserId(id: Long)
  case class User(uid: UserId, name: String)

  val fakeUser: User = User(UserId(-1), "Fake Guy")

  val user: Var[Option[User]] = Var(None)

  val userObs = Obs(user, skipInitial = true) {
    user() match {
      case Some(user) => {
        // fetch any user data
        Admin.screen() = LandingScreen
      }
      case _ => Admin.screen() = LoginScreen
    }
  }

  def clearUserData() = {
    user() = None
  }
}
