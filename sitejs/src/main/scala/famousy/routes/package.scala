package famousy

import locallink._
import scala.concurrent.{Future,ExecutionContext}
import scalajs.concurrent.JSExecutionContext.Implicits.queue

package object routes {

  sealed trait Screen
  case object TestScreen extends Screen

  lazy val router: Router[Screen] = Router.generate[Screen](TestScreen)
}
