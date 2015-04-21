package famousy.views

import org.scalajs.dom
import scala.scalajs.js
import scalajs.js.Dynamic.{literal => lit}
import common.famous.core._
import common.famous.surfaces.{ImageSurface, ImageSurfaceOptions}

object AppView {

  private val view = new View()

  val rootMod = new Modifier()

  val mainNode = view.add(rootMod)

  val logo = new ImageSurface({
    val o = lit().asInstanceOf[ImageSurfaceOptions]
    o.size = js.Array("200", "200")
    o.content = "http://code.famo.us/assets/famous_logo.png"
    o.classes = js.Array("double-sided")
    o
  })

  private val initialTime = js.Date.now()

  val spinMod = new Modifier({
    val o = lit().asInstanceOf[ModifierOptions]
    o.origin = js.Array(0.5, 0.5)
    o.align = js.Array(0.5, 0.5)
    o.transform = () => Transform.rotateY(0.002 * (js.Date.now() - initialTime))
    o
  })

  mainNode.add(spinMod).add(logo)
}
