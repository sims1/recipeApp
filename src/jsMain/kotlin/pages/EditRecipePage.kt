package pages

import addRecipe
import atomics.*
import components.shared.Footer
import components.shared.Header
import components.common.*
import csstype.*
import csstype.LineStyle.Companion.solid
import csstype.Position.Companion.absolute
import csstype.Position.Companion.fixed
import csstype.Position.Companion.relative
import csstype.TextAlign.Companion.center
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
// https://stackoverflow.com/questions/65043370/type-mismatch-when-serializing-data-class
import react.FC
import react.Props
import react.css.css
import react.dom.html.ButtonType
import react.dom.html.InputType
import react.dom.html.ReactHTML.body
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.select
import react.dom.html.ReactHTML.textarea
import react.useState

private val scope = MainScope()

val EditRecipePage = FC<Props> {
    var recipeNameState: String? by useState(null)
    var recipeTypeState: RecipeType? by useState(null)

    var vegetableAndMeatTypeState: VegetableAndMeatType? by useState(null)
    var vegetableAndMeatDescriptionState: String? by useState(null)
    var vegetableAndMeatQuantityState: Int? by useState(null)
    var vegetableAndMeatIngredientsState: List<Ingredient<VegetableAndMeatType>> by useState(emptyList())

    var spiceAndSauceTypeState: SpiceAndSauceType? by useState(null)
    var spiceAndSauceDescriptionState: String? by useState(null)
    var spiceAndSauceQuantityState: Int? by useState(null)
    var spiceAndSauceUnitState: CookingUnit? by useState(null)
    var spiceAndSauceIngredientsState: List<Ingredient<SpiceAndSauceType>> by useState(emptyList())

    var descriptionState: String by useState("")

    var showPopUpWindow: Boolean by useState(false)
    var popUpWindowMessage: String by useState("")

    Header { }

    div {
        css {
            margin = Auto.auto
            width = 60.pc

            fontFamily = h2FontFamilyAlias
            fontSize = h2FontSizeAlias
        }

        input {
            css {
                fontSize = h2FontSizeAlias
            }
            type = InputType.text
            placeholder = "Recipe Name"
            onChange = { event -> recipeNameState = event.target.value }
        }

        br { }

        RecipeType.values().toList().map { recipeType ->
            label {
                css {
                    fontFamily = textFontFamilyAlias
                    fontSize = textFontSizeAlias
                }
                input {
                    type = InputType.radio
                    value = recipeType.value
                    name = "recipeType"
                    onChange = { recipeTypeState = recipeType }
                }
            }
            +"${recipeType.value}   "
        }

        div {
            css {
                display = Display.grid
                gridTemplateAreas = GridTemplateAreas(
                    GridArea("VegetableAndMeatSelection Selected"),
                    GridArea("SpiceAndSauceSelection Selected"),
                )

                backgroundColor = recipeColorAlias

                padding = 2.pc
                borderRadius = commonBorderRadiusAlias

                whiteSpace = WhiteSpace.preWrap
                fontFamily = textFontFamilyAlias
                fontSize = textFontSizeAlias
            }

            div {
                css {
                    gridArea = GridArea("VegetableAndMeatSelection")
                    backgroundColor = recipeColorAlias
                    width = 30.pc

                    padding = 1.pc
                    borderRadius = commonBorderRadiusAlias

                    whiteSpace = WhiteSpace.preWrap
                    fontFamily = textFontFamilyAlias
                    fontSize = unimportantFontSizeAlias
                }

                button {
                    css {
                        fontSize = unimportantFontSizeAlias
                        backgroundColor = recipeNameColorAlias
                        color = buttonFontColor
                        borderStyle = None.none
                        borderRadius = commonButtonBorderRadiusAlias
                        paddingLeft = 1.pc
                        paddingRight = 1.pc
                        paddingTop = 0.5.pc
                        paddingBottom = 0.5.pc
                        cursor = Cursor.pointer
                    }
                    type = ButtonType.button
                    onClick = {
                        if (vegetableAndMeatTypeState != null) {
                            vegetableAndMeatIngredientsState = vegetableAndMeatIngredientsState +
                                Ingredient(
                                    vegetableAndMeatTypeState!!,
                                    vegetableAndMeatDescriptionState,
                                    vegetableAndMeatQuantityState ?: 1
                                )
                        }
                    }
                    +"Add vegetable or meat"
                }

                br { }

                select {
                    css {
                        fontFamily = textFontFamilyAlias
                        fontSize = unimportantFontSizeAlias
                        marginTop = 1.pc
                    }
                    name = "VegetableAndMeatType"
                    id = "VegetableAndMeatType"
                    onChange = { event ->
                        vegetableAndMeatTypeState = TypeStringConverter.getVegetableAndMeatType(event.target.value)
                    }
                    option { +"Select vegetable or meat" }
                    sortedVegetableAndMeatType.map { ingredient ->
                        option {
                            value = ingredient.getValue()
                            +ingredient.getValue()
                        }
                    }
                }

                p {
                    +"Quantity "
                    input {
                        css {
                            width = 5.pc
                        }
                        type = InputType.number
                        onChange = { event -> vegetableAndMeatQuantityState = event.target.value.toIntOrNull() }
                    }
                }

                p {
                    +"Description"
                    br { }
                    input {
                        css {
                            width = 20.pc
                        }
                        type = InputType.text
                        onChange = { event -> vegetableAndMeatDescriptionState = event.target.value }
                    }
                }

                br { }
            }

            div {
                css {
                    gridArea = GridArea("SpiceAndSauceSelection")
                    backgroundColor = recipeColorAlias
                    width = 30.pc

                    padding = 1.pc
                    borderRadius = commonBorderRadiusAlias

                    whiteSpace = WhiteSpace.preWrap
                    fontFamily = textFontFamilyAlias
                    fontSize = unimportantFontSizeAlias
                }

                button {
                    css {
                        fontSize = unimportantFontSizeAlias
                        backgroundColor = recipeNameColorAlias
                        color = buttonFontColor
                        borderStyle = None.none
                        borderRadius = commonButtonBorderRadiusAlias
                        paddingLeft = 1.pc
                        paddingRight = 1.pc
                        paddingTop = 0.5.pc
                        paddingBottom = 0.5.pc
                        cursor = Cursor.pointer
                    }
                    type = ButtonType.button
                    onClick = {
                        spiceAndSauceIngredientsState = spiceAndSauceIngredientsState +
                            Ingredient(
                                spiceAndSauceTypeState!!,
                                spiceAndSauceDescriptionState,
                                spiceAndSauceQuantityState ?: 1,
                                spiceAndSauceUnitState ?: CookingUnit.DEFAULT
                            )
                    }
                    +"Add spice or sauce"
                }

                br { }
                select {
                    css {
                        fontFamily = textFontFamilyAlias
                        fontSize = unimportantFontSizeAlias
                        marginTop = 1.pc
                    }
                    name = "SpiceAndSauceType"
                    id = "SpiceAndSauceType"
                    onChange = { event ->
                        spiceAndSauceTypeState = TypeStringConverter.getSpiceAndSauceType(event.target.value)
                    }
                    option { +"Select spice or sauce" }
                    sortedSpiceAndSauceType.map { ingredient ->
                        option {
                            value = ingredient.getValue()
                            +ingredient.getValue()
                        }
                    }
                }

                p {
                    +"Quantity "
                    input {
                        css {
                            width = 5.pc
                        }
                        type = InputType.number
                        onChange = { event -> spiceAndSauceQuantityState = event.target.value.toIntOrNull() }
                    }
                }

                p {
                    +"Unit   "
                    CookingUnit.values().map { ingredientUnit ->
                        label {
                            css {
                                fontFamily = textFontFamilyAlias
                                fontSize = textFontSizeAlias
                            }
                            input {
                                type = InputType.radio
                                value = ingredientUnit.value
                                name = "ingredientUnit"
                                onChange = { spiceAndSauceUnitState = ingredientUnit }
                            }
                        }
                        +if (ingredientUnit == CookingUnit.DEFAULT) "None" else ingredientUnit.value
                    }
                }

                p {
                    +"Description"
                    br { }
                    input {
                        css {
                            width = 20.pc
                        }
                        type = InputType.text
                        onChange = { event -> spiceAndSauceDescriptionState = event.target.value }
                    }
                }

                br { }
            }

            div {
                css {
                    gridArea = GridArea("Selected")
                    width = 30.pc
                }

                p {
                    css {
                        fontFamily = h2FontFamilyAlias
                        fontSize = unimportantFontSizeAlias
                    }
                    +"Vegetable and Meat"
                }
                vegetableAndMeatIngredientsState.map { ingredient ->
                    p {
                        css {
                            fontFamily = textFontFamilyAlias
                            fontSize = unimportantFontSizeAlias
                        }
                        button {
                            css {
                                fontSize = unimportantFontSizeAlias
                                backgroundColor = recipeNameColorAlias
                                color = buttonFontColor
                                borderStyle = None.none
                                borderRadius = commonButtonBorderRadiusAlias
                                cursor = Cursor.pointer
                            }
                            type = ButtonType.button
                            onClick = {
                                vegetableAndMeatIngredientsState = vegetableAndMeatIngredientsState - ingredient
                            }
                            +"x"
                        }
                        +"  ${ingredient.getString()}"
                    }
                }

                p {
                    css {
                        fontFamily = h2FontFamilyAlias
                        fontSize = unimportantFontSizeAlias
                    }
                    +"Spice and Sauce"
                }
                spiceAndSauceIngredientsState.map { ingredient ->
                    p {
                        css {
                            fontFamily = textFontFamilyAlias
                            fontSize = unimportantFontSizeAlias
                        }
                        button {
                            css {
                                fontSize = unimportantFontSizeAlias
                                backgroundColor = recipeNameColorAlias
                                color = buttonFontColor
                                borderStyle = None.none
                                borderRadius = commonButtonBorderRadiusAlias
                                cursor = Cursor.pointer
                            }
                            type = ButtonType.button
                            onClick = {
                                spiceAndSauceIngredientsState = spiceAndSauceIngredientsState - ingredient
                            }
                            +"x"
                        }
                        +"  ${ingredient.getString()}"
                    }
                }
            }
        }


        p {
            +"Cooking Instructions"
            br { }
            textarea {
                rows = 4
                cols = 50
                onChange = { event -> descriptionState = event.target.value }
            }
        }

        br { }

        button {
            css {
                fontSize = unimportantFontSizeAlias
                backgroundColor = recipeNameColorAlias
                color = buttonFontColor
                borderStyle = None.none
                borderRadius = commonButtonBorderRadiusAlias
                paddingLeft = 1.pc
                paddingRight = 1.pc
                paddingTop = 0.5.pc
                paddingBottom = 0.5.pc
                cursor = Cursor.pointer
            }
            type = ButtonType.button
            onClick = {
                if (recipeNameState == null) {
                    println("Recipe name is not set")
                    popUpWindowMessage = "Recipe name is not set!"
                } else if (recipeTypeState == null) {
                    println("Recipe type is not selected")
                    popUpWindowMessage = "Recipe type is not selected!"
                } else {
                    val ingredient = Recipe(
                        recipeTypeState!!,
                        recipeNameState!!,
                        vegetableAndMeatIngredientsState,
                        spiceAndSauceIngredientsState,
                        listOf(descriptionState)
                    )
                    popUpWindowMessage = "Congratulations! Recipe $recipeNameState is added!"
                    scope.launch {
                        addRecipe(ingredient)
                    }
                }
                showPopUpWindow = true
            }
            +"I'm Ling, and I want to add this recipe"
        }

        if (showPopUpWindow) {
            div {
                css {
                    textAlign = center
                    position = fixed
                    zIndex = ZIndex(99)
                    left = 35.pc
                    top = 10.pc
                    padding = 2.pc

                    fontFamily = textFontFamilyAlias
                    fontSize = textFontSizeAlias

                    borderStyle = solid
                    borderWidth = 0.2.pc
                    borderRadius = commonBorderRadiusAlias
                    borderColor = recipeNameColorAlias
                    backgroundColor = hoverColorAlias
                }
                p {
                    +popUpWindowMessage
                }

                button {
                    css {
                        onMouseDown = { showPopUpWindow = false }

                        color = NamedColor.white
                        backgroundColor = recipeNameColorAlias
                        borderColor = recipeNameColorAlias
                        borderRadius = commonButtonBorderRadiusAlias
                        height = 2.pc
                        width = 6.pc
                        cursor = Cursor.pointer
                    }
                    +"OK"
                }
            }
        }
    }

    Footer { }
}