package famousy

import routes._

import rx._
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom

import scalajs.js.Dynamic.{literal => lit}
import common.famous.core.{Engine, RenderNode}
import common.famous.views.{RenderController, RenderControllerOptions}

object Famousy extends js.JSApp{

  private val controller = new RenderController({
    val o = lit().asInstanceOf[RenderControllerOptions]
    o
  })

  private val setCurrent: Rx[Unit] = Rx {
    val current = router.current() match {
      case TestScreen => views.AppView.mainNode
    }
    println("SET CURRENT: ")
    dom.console.log(current)

    controller.show(current)
  }

  def main(): Unit = {
    val mainCtx = Engine.createContext()
    mainCtx.add(controller)
  }
}
