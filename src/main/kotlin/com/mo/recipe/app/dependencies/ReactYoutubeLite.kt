@file:JsModule("react-awesome-button")
@file:JsNonModule

import react.*

@JsName("AwesomeButton")
external val ReactButton: ComponentClass<ReactYouTubeProps>

external interface ReactYouTubeProps : Props {
    var type: String
    var onPress: () -> Unit
}