package scalajs.react

import japgolly.scalajs.react.Callback
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

import org.scalajs.dom
import scala.scalajs.js.JSApp
import scalacss.mutable.GlobalRegistry

object DemoApp extends JSApp {

  def loadCss() = {
    import scalacss.Defaults._
    import scalacss.ScalaCssReact._

    GlobalRegistry.register(ReactToggle.DefaultStyle)
    GlobalRegistry.addToDocumentOnRegistration()
  }

  def main(): Unit = {
    loadCss()
    val container = dom.document.getElementById("container")
    ReactDOM.render(Demo(), container)
  }
}

object Demo{
  lazy val component = ReactComponentB[Unit]("Demo")
    .initialState(true).renderS(($,state) =>{

    def onChange(e:ReactEventI):Callback = $.modState(_ => e.target.checked)

    <.div(
      <.h4("Checked ", state.toString),
      ReactToggle(checked = state, onChange = onChange)
    )
  }).buildU

  def apply() = component()
}