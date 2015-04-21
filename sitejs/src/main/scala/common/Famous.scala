package common

import scala.scalajs.js
import js.annotation._
import org.scalajs.dom
import scala.scalajs.js.UndefOr
import scala.scalajs.js.UndefOr._

package famous {

  trait jsObjectExt extends js.Object {
    def on(`type`: String, handler: js.Function1[dom.Event,_]): Unit = js.native
  }

package core {

trait Renderable extends jsObjectExt {
}

@JSName("famous.core.Context")
class Context extends jsObjectExt {
  def add(child: Renderable): RenderNode = js.native
  def add(child: views.RenderController): RenderNode = js.native
  def getSize(): js.Array[Double] = js.native
}

@JSName("famous.core.Engine")
object Engine extends jsObjectExt {
  def createContext(container: dom.html.Element): Context = js.native
  def createContext(): Context = js.native
}

@JSName("famous.core.EventHandler")
class EventHandler extends jsObjectExt {
  def emit(`type`: String, obj: UndefOr[js.Object] = js.undefined): EventHandler = js.native
  //def subscribe(source: EventEmitter): EventHandler = js.native
  def subscribe(source: EventHandler): EventHandler = js.native
  //def pipe(target: EventEmitter): EventHandler = js.native
  def pipe(target: EventHandler): EventHandler = js.native
}

trait Transform extends js.Array[Double] {
}

@JSName("famous.core.Transform")
object Transform extends js.Object {
  def behind: Transform = js.native
  def identity: Transform = js.native
  def rotateY(theta: Double): Transform = js.native
  def rotateX(theta: Double): Transform = js.native
  def rotateZ(theta: Double): Transform = js.native
  def translate(x: Double, y: Double, z: Double): Transform = js.native
  def scale(x: Double, y: Double, z: Double): Transform = js.native
  def thenMove(transform: Transform, move: js.Array[Double]): Transform = js.native
}

@JSName("famous.core.RenderNode")
class RenderNode(target: UndefOr[Renderable] = js.undefined) extends jsObjectExt {
  def add(child: Renderable): RenderNode = js.native
  def set(child: Renderable): RenderNode = js.native
  def getSize(): js.Array[Double] = js.native
}

// Modifier
trait ModifierOptions extends js.Object {
  var transform: js.Function0[Transform] = js.native
  var opacity: Double = js.native
  var align: js.Array[Double] = js.native
  var origin: js.Array[Double] = js.native
  var size: js.Array[js.Any] = js.native
  //var proportions = js.native
}

trait ModifierTrait extends Renderable {
  def halt(): Unit = js.native
  def transformFrom(transform: core.Transform): ModifierTrait = js.native
  def opacityFrom(opacity: js.Array[Double]): ModifierTrait = js.native
  def originFrom(origin: js.Array[Double]): ModifierTrait = js.native
  def alignFrom(align: js.Array[Double]): ModifierTrait = js.native
  def sizeFrom(size: js.Array[Double]): ModifierTrait = js.native
  //def proportionsFrom(proportions: js.Any) = js.native

  // todo test this
  def setTransform(transform: core.Transform, transition: UndefOr[js.Object] = js.undefined, callback: UndefOr[js.Function0[Unit]] = js.undefined): ModifierTrait = js.native
  def setOpacity(opacity: Double, transition: UndefOr[js.Object] = js.undefined, callback: UndefOr[js.Function0[Unit]] = js.undefined): ModifierTrait = js.native
  def setSize(size: js.Array[js.Any]): ModifierTrait = js.native
}

@JSName("famous.core.Modifier")
class Modifier(opts: UndefOr[ModifierOptions] = js.undefined) extends ModifierTrait {
}

// Surface
trait SurfaceTrait extends Renderable {
  def properties: js.Object = js.native
  def attributes: js.Object = js.native
  def content: String = js.native
  def size: js.Array[Double] = js.native

  def on(`type`: String, handler: js.Function0[_]): Unit = js.native
  def getSize(): js.Array[js.Any] = js.native
  def setSize(size: js.Array[js.Any]): SurfaceTrait = js.native
  def setProperties(props: js.Object): SurfaceTrait = js.native
  def setAttributes(attrs: js.Object): SurfaceTrait = js.native
  def setContent(content: String): SurfaceTrait = js.native
  def getContent(): String = js.native
  def addClass(cls: String): SurfaceTrait = js.native
  def removeClass(cls: String): SurfaceTrait = js.native
  def toggleClass(cls: String): SurfaceTrait = js.native
  def setClasses(cls: String): SurfaceTrait = js.native
}

trait SurfaceOptions extends js.Object {
  var classes: js.Array[String] = js.native
  var content: String = js.native
  var properties: js.Object = js.native
  var attributes: js.Object = js.native
  var size: js.Array[js.Any] = js.native
}

@JSName("famous.core.Surface")
class Surface(opts: UndefOr[SurfaceOptions] = js.undefined) extends SurfaceTrait {
}

// View
trait ViewOptions extends js.Object {
}

@JSName("famous.core.View")
class View(ops: UndefOr[ViewOptions] = js.undefined) extends Renderable {
  def add(child: Renderable): RenderNode = js.native
  def add(child: core.RenderNode): RenderNode = js.native
  def getSize(): js.Array[Double] = js.native
  //def getOptions(key: String): js.Dynamic = js.native
}
}

package surfaces {

import core._

// ImageSurface
trait ImageSurfaceOptions extends core.SurfaceOptions {
  var src: String = js.native
}

@JSName("famous.surfaces.ImageSurface")
class ImageSurface(opts: UndefOr[ImageSurfaceOptions] = js.undefined) extends SurfaceTrait {
}

// VideoSurface
trait VideoSurfaceOptions extends core.SurfaceOptions {
  var autoplay: Boolean = js.native
  var src: String = js.native
}

@JSName("famous.surfaces.VideoSurface")
class VideoSurface(opts: UndefOr[VideoSurfaceOptions] = js.undefined) extends SurfaceTrait {
}

// ContainerSurface
trait ContainerSurfaceOptions extends core.SurfaceOptions {
}

@JSName("famous.surfaces.ContainerSurface")
class ContainerSurface(opts: UndefOr[ContainerSurfaceOptions] = js.undefined) extends SurfaceTrait {
  def add(obj: Renderable): ContainerSurface = js.native // core.RenderNode
  def add(obj: core.RenderNode): ContainerSurface = js.native // core.RenderNode
}

// InputSurface
trait InputSurfaceOptions extends core.SurfaceOptions {
  var placeholder: String = js.native
  var `type`: String = js.native
  var value: String = js.native
}

@JSName("famous.surfaces.InputSurface")
class InputSurface(opts: UndefOr[InputSurfaceOptions] = js.undefined) extends SurfaceTrait {
  def setValue(value: String): InputSurface = js.native
  def getValue(): String = js.native
  def setName(name: String): InputSurface = js.native
  def getName(): String = js.native
  def setPlaceholder(str: String): InputSurface = js.native
  def focus(): Unit = js.native
  def blur(): Unit = js.native
}
}

package views {

// Scrollview
trait ScrollviewOptions extends js.Object {
  var direction: Double = js.native // X:0, Y:1, Z:2
  var rails: Boolean = js.native
  var friction: Double = js.native
  var drag: Double = js.native
  var edgeGrip: Double = js.native
  var edgePeriod: Double = js.native
  var edgeDamp: Double = js.native
  var margin: Double = js.native
  var paginated: Boolean = js.native
  var pagePeriod: Double = js.native
  var pageDamp: Double = js.native
  var pageStopSpeed: Double = js.native
  var pageSwitchSpeed: Double = js.native
  var speedLimit: Double = js.native
  var groupScroll: Boolean = js.native
  var syncScale: Double = js.native
}

@JSName("famous.views.Scrollview")
class Scrollview(opts: ScrollviewOptions) extends core.Renderable  {
  def sequenceFrom[A <: core.Renderable](node: js.Array[A]): Scrollview = js.native
  //def sequenceFrom(node: js.Array[ViewSequence]): Scrollview = js.native
}

// RenderController
trait RenderControllerOptions extends js.Object {
  var inTransition: Boolean = js.native
  var outTransition: Boolean = js.native
  var overlap: Boolean = js.native
}

trait RenderControllerTrait extends js.Object {
  def show(surface: core.RenderNode, transition: UndefOr[js.Dynamic] = js.undefined, cb: UndefOr[js.Function0[Unit]] = js.undefined): Unit = js.native
  def show(surface: core.Renderable): Unit = js.native
}

@JSName("famous.views.RenderController")
class RenderController(opts: UndefOr[RenderControllerOptions] = js.undefined) extends RenderControllerTrait {
}

// HeaderFooter
trait HeaderFooterOptions extends js.Object {
  var headerSize: js.Any = js.native
  var footerSize: js.Any = js.native
}

trait HeaderFooterLayoutTrait extends core.Renderable {
  def header: core.RenderNode = js.native
  def footer: core.RenderNode = js.native
  def content: core.RenderNode = js.native
}

@JSName("famous.views.HeaderFooterLayout")
class HeaderFooterLayout(opts: UndefOr[HeaderFooterOptions] = js.undefined) extends HeaderFooterLayoutTrait {

}

// Lightbox
trait LightboxOptions extends js.Object {
  var inTransform: core.Transform = js.native
  var inOpacity: Double = js.native
  var inOrigin: js.Array[Double] = js.native
  var showTransform: core.Transform = js.native
  var showOpacity: Double = js.native
  var showOrigin: js.Array[Double] = js.native
  var outTransform: core.Transform = js.native
  var outOpacity: Double = js.native
  var outOrigin: js.Array[Double] = js.native
  var inTransition: transitions.Transition = js.native
  var outTransition: transitions.Transition = js.native
  var overlap: Boolean = js.native
}

trait LightboxTrait extends core.Renderable {
  def show(thing: core.Renderable): Unit = js.native
}

@JSName("famous.views.Lightbox")
class Lightbox(opts: UndefOr[LightboxOptions]) extends LightboxTrait {
}

}

package modifiers {

// StateModifier
trait StateModifierOptions extends core.ModifierOptions {
}

@JSName("famous.modifiers.StateModifier")
class StateModifier(opts: UndefOr[StateModifierOptions] = js.undefined) extends core.ModifierTrait {
}
}

package transitions {

trait Transition extends js.Object {
  var duration: Double = js.native
  var curve: js.Any = js.native
}

// todo test this
@JSName("famous.transitions.Transitionable")
class Transitionable[T](initial: T) extends js.Object {
  def get(): T = js.native
  def set(value: T, duration: Transition): Unit = js.native
}

@JSName("famous.transitions.Easing")
object Easing extends js.Object {
  def inCubic: Unit = js.native
  def inQuad: Unit = js.native
  def outBack: Unit = js.native
}
}
}