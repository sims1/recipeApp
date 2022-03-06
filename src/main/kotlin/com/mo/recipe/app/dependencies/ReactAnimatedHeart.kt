@file:JsModule("react-animated-heart")
@file:JsNonModule

import react.*

@JsName("Heart")
external val HeartButton: ComponentClass<HeartButtonProps>

external interface HeartButtonProps : Props {
    var isClick: Boolean
    var onClick: () -> Unit
}
