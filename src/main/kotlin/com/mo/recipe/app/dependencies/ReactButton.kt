@file:JsModule("react-awesome-button")
@file:JsNonModule

import react.*

@JsName("AwesomeButton")
external val ReactButton: ComponentClass<ReactButtonProps>

external interface ReactButtonProps : Props {
    var type: String
    var onPress: () -> Unit
}