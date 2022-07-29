package components.shared

import reAuthenticateWithAuthToken
import authenticateWithPassword
import components.common.*
import csstype.*
import csstype.FontWeight.Companion.bolder
import csstype.TextAlign.Companion.center
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.css.css
import react.dom.html.InputType
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.header
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.p
import react.useState

private val scope = MainScope()

val Header = FC<Props> {
    var loginState: LoginState by useState(LoginState.GUEST)
    var showLoginPopUp: Boolean by useState(false)

    var loginId: String by useState("")
    var loginPassword: String by useState("")

    header {
        css {
            textAlign = center
            fontSize = h1FontSizeAlias
            fontFamily = fancyH1FontFamilyAlias
            fontWeight = bolder
            color = recipeNameColorAlias
        }
        p {
            +"Ling's favourite recipes"
        }

        div {
            css {
                //textAlign = TextAlign.right
                position = Position.fixed
                right = 1.pc
                top = 0.pc

                fontFamily = textFontFamilyAlias
                fontSize = unimportantFontSizeAlias
            }

            if (loginState == LoginState.GUEST) {
                scope.launch {
                    loginState = reAuthenticateWithAuthToken()
                }
            }

            if (loginState == LoginState.GUEST) {
                button {
                    css {
                        onMouseDown = {
                            showLoginPopUp = true
                        }

                        color = recipeNameColorAlias
                        borderStyle = LineStyle.hidden
                        backgroundColor = NamedColor.white
                        fontSize = headerLoginFontSizeAlias
                        fontFamily = unimportantFontFamilyAlias
                        height = 2.pc
                        cursor = Cursor.pointer
                    }
                    +loginState.message
                }
            } else {
                p {
                    css {
                        fontSize = headerLoginFontSizeAlias
                        fontFamily = unimportantFontFamilyAlias
                        fontWeight = FontWeight.normal
                    }
                    +loginState.message
                }
            }
        }

        if (showLoginPopUp) {
            div {
                css {
                    textAlign = center
                    position = Position.fixed
                    zIndex = ZIndex(99)
                    left = 33.pc
                    top = 10.pc
                    padding = 2.pc

                    fontFamily = textFontFamilyAlias
                    fontSize = textFontSizeAlias

                    borderStyle = LineStyle.solid
                    borderWidth = 0.2.pc
                    borderRadius = commonBorderRadiusAlias
                    borderColor = recipeNameColorAlias
                    backgroundColor = hoverColorAlias
                }
                input {
                    css {
                        width = 10.pc
                    }
                    type = InputType.text
                    onChange = { event -> loginId = event.target.value }
                    placeholder = "ID"
                }
                br { }
                input {
                    css {
                        width = 10.pc
                    }
                    type = InputType.text
                    onChange = { event -> loginPassword = event.target.value }
                    placeholder = "Password"
                }
                br { }
                button {
                    onMouseDown = {
                        scope.launch {
                            loginState = authenticateWithPassword(loginId, loginPassword)
                            showLoginPopUp = false
                        }
                    }
                    css {
                        color = NamedColor.white
                        backgroundColor = recipeNameColorAlias
                        borderColor = recipeNameColorAlias
                        borderRadius = commonButtonBorderRadiusAlias
                        height = 2.pc
                        width = 6.pc
                        cursor = Cursor.pointer
                    }
                    +"Login"
                }
            }
        }
    }
}