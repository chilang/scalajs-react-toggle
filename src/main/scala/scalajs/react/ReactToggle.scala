package scalajs.react

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom.raw.HTMLInputElement

import scala.scalajs.js.UndefOr
import scalacss.Defaults._
import scalacss.ScalaCssReact._

object ReactToggle {
  case class State(checked:Boolean, hasFocus:Boolean)
  case class Props(checked:Boolean, disabled:Boolean, defaultChecked:Boolean, style:Style, onChange:ReactEventI => Callback)

  val input: RefSimple[HTMLInputElement] = Ref[HTMLInputElement]("input")

  class Backend($: BackendScope[Props, State]) {
    def handleClick(event:ReactMouseEvent):Callback = {
      val checkbox: UndefOr[HTMLInputElement] = input($)
      if (event.target != checkbox.get) {
        event.preventDefault()
        checkbox.map{ element =>
          element.focus()
          element.click()
        }
      }
      $.modState(_.copy(checked = checkbox.get.checked))
    }


    def handleFocus(event: ReactEvent) = $.modState(_.copy(hasFocus = true))
    def handleBlur(event: ReactEvent) = $.modState(_.copy(hasFocus = false))

    def render(props:Props, state:State) = {

      val style = props.style
      <.div(style.reactToggle
        ,state.checked ?= style.reactToggleChecked
        ,state.hasFocus ?= style.reactToggleFocus
        ,props.disabled ?= style.reactToggleDisabled
        , ^.onClick ==> handleClick
        , <.div(style.reactToggleTrack
          , <.div(style.reactToggleTrackCheck
            , Check(())
          )
          , <.div(style.reactToggleTrackX
            , X(())
          )
        )
        , <.div(style.reactToggleThumb)
        , <.input(^.ref := input
          ,^.onFocus ==> handleFocus
          ,^.onBlur ==> handleBlur
          ,style.reactToggleScreenreaderOnly
          ,^.`type` := "checkbox"
          ,^.onChange ==> props.onChange
        )
      )
    }
  }
  lazy val component = ReactComponentB[Props]("ReactToggle")
    .getInitialState{scope:CompScope.DuringCallbackU[Props, State,Any] => State(checked = scope.props.checked || scope.props.defaultChecked, hasFocus = false)}
    .renderBackend[Backend]
    .componentWillReceiveProps($ => if ($.nextProps.checked) $.$.modState(_.copy(checked = true)) else Callback{})
    .build

  def apply(checked:Boolean, onChange:ReactEventI => Callback) =
    component(Props(checked, disabled = false, defaultChecked = checked, style = DefaultStyle, onChange))

  lazy val X = FunctionalComponent[Unit](_ => {
    <.svg.svg(^.width := "10", ^.height :="10", ^.svg.viewBox :="0 0 10 10", ^.xmlns:="http://www.w3.org/2000/svg",
      <.titleTag("switch-x"),
      <.svg.path(^.svg.d := "M9.9 2.12L7.78 0 4.95 2.828 2.12 0 0 2.12l2.83 2.83L0 7.776 2.123 9.9 4.95 7.07 7.78 9.9 9.9 7.776 7.072 4.95 9.9 2.12", ^.svg.fill:="#fff", ^.svg.fillRule:="evenodd")
    )
  })

  lazy val Check = FunctionalComponent[Unit](_ => {
    <.svg.svg(^.width:="14", ^.height:="11", ^.svg.viewBox:="0 0 14 11", ^.xmlns:="http://www.w3.org/2000/svg",
      <.titleTag("switch-check"),
      <.svg.path(^.svg.d:="M11.264 0L5.26 6.004 2.103 2.847 0 4.95l5.26 5.26 8.108-8.107L11.264 0", ^.svg.fill:="#fff", ^.svg.fillRule:="evenodd")
    )
  })

  class Style extends StyleSheet.Inline{
    import dsl._

    val reactToggleThumb = style(
      position.absolute
      ,top(1.px)
      ,left(1.px)
      ,width(22.px)
      ,height(22.px)
      ,border:=! "1px solid #4D4D4D"
      ,borderRadius(50 %%)
      ,backgroundColor(c"#FAFAFA")
      ,boxSizing.borderBox
      ,transition:= "all 0.25s ease"
    )

    val reactToggleTrack = style(
      width(50.px)
      ,height(24.px)
      ,padding.`0`
      ,borderRadius(30.px)
      ,backgroundColor(c"#4D4D4D")
      ,transition:= "all 0.2s ease"
    )

    val reactToggleDisabled = style(
      opacity(0.5)
      , transition := "opacity 0.25s"
    )



    val reactToggleScreenreaderOnly = style(
      border.`0`
      ,clip.rect(0.px, 0.px, 0.px, 0.px)
      ,height(1.px)
      ,margin(-1.px)
      ,overflow.hidden
      ,padding.`0`
      ,position.absolute
      ,width(1.px)
    )


    val reactToggleTrackCheck = style(
      position.absolute
      ,width(14.px)
      ,height(10.px)
      ,top.`0`
      ,bottom.`0`
      ,marginTop.auto
      ,marginBottom.auto
      ,lineHeight.`0`
      ,left(8.px)
      ,opacity(0)
      ,transition := "opacity 0.25s ease"
    )
    val reactToggleTrackX = style(
      position.absolute
      , width(10.px)
      , height(10.px)
      , top.`0`
      , bottom.`0`
      , marginTop.auto
      , marginBottom.auto
      , lineHeight.`0`
      , right(10.px)
      , opacity(1)
      , transition := "opacity 0.25s ease"
    )


    val reactToggleChecked = style(
      unsafeChild("."+reactToggleTrack.className.value)(
        backgroundColor(c"#19AB27")
      )
      ,unsafeChild("."+reactToggleTrackCheck.className.value)(
        opacity(1)
        ,transition := "opacity 0.25s ease"
      )
      ,unsafeChild("."+reactToggleTrackX.className.value)(
        opacity(0)
      )
      ,unsafeChild("."+reactToggleThumb.className.value)(
        left(27.px)
        ,borderColor(c"#19AB27")
      )
    )

    val reactToggleFocus = style(
      unsafeChild("."+reactToggleThumb.className.value)(
        boxShadow := "0px 0px 2px 3px #0099E0"
      )
    )

    val reactToggle = style(
      display.inlineBlock
      ,position.relative
      ,cursor.pointer
      ,backgroundColor.transparent
      ,border.`0`
      ,padding.`0`
      ,userSelect := "none"
      ,&.hover(
        unsafeChild("."+reactToggleTrack.className.value)(
          backgroundColor(c"#000000")
        )
      )
      ,unsafeExt(_ + "."+reactToggleChecked.className.value)(
        &.hover(
          unsafeChild("."+reactToggleTrack.className.value)(
            backgroundColor(c"#128D15")
          )
        )
      )
      ,&.active(
        unsafeChild("."+reactToggleThumb.className.value)(
          boxShadow:= "0px 0px 5px 5px #0099E0"
        )
      )
    )
  }

  object DefaultStyle extends Style
}


