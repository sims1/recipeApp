package pages

import addIngredient
import addRecipe
import addSeasoning
import atomics.*
import atomics.ingredient.*
import components.shared.Footer
import components.shared.Header
import components.common.*
import components.edit.CreateCustomIngredientConfig
import components.edit.PopUpWindowConfig
import components.edit.SelectedSeasoningConfig
import components.edit.SelectedIngredientConfig
import csstype.*
import csstype.LineStyle.Companion.solid
import csstype.Position.Companion.fixed
import csstype.TextAlign.Companion.center
import getListOfIngredients
import getListOfSeasonings
import io.ktor.http.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.files.File
import org.w3c.files.get
// https://stackoverflow.com/questions/65043370/type-mismatch-when-serializing-data-class
import react.FC
import react.Props
import react.css.css
import react.dom.html.ButtonType
import react.dom.html.InputType
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.select
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.textarea
import react.useEffectOnce
import react.useState
import uploadRecipePicture

private val scope = MainScope()

val EditRecipePage = FC<Props> {
    var recipeNameState: String? by useState(null)
    var recipeTagsState: List<Tag> by useState(emptyList())

    var selectedIngredientConfigState: SelectedIngredientConfig by useState(SelectedIngredientConfig())
    var selectedIngredientsState: List<IngredientDetails<Ingredient>> by useState(emptyList())

    var selectedSeasoningConfigState: SelectedSeasoningConfig by useState(SelectedSeasoningConfig())
    var selectedSeasoningsState: List<IngredientDetails<Seasoning>> by useState(emptyList())

    var recipeImageState: File? by useState(null)
    var recipeDescriptionState: String by useState("")

    var popUpWindowConfigState: PopUpWindowConfig by useState(PopUpWindowConfig())
    var createCustomIngredientConfigState: CreateCustomIngredientConfig by useState(CreateCustomIngredientConfig())

    var showAddIngredientDetails: Boolean by useState(false)

    var allIngredientsState: List<Ingredient> by useState(emptyList())
    var allSeasoningState: List<Seasoning> by useState(emptyList())

    useEffectOnce {
        scope.launch {
            allIngredientsState = getListOfIngredients()
            allSeasoningState = getListOfSeasonings()
        }
    }

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
                textAlign = center
                margin = Auto.auto
            }
            type = InputType.text
            placeholder = "Recipe Name"
            onChange = { event -> recipeNameState = event.target.value }
        }

        br { }

        Tag.values().toList().map { tag ->
            button {
                css {
                    fontFamily = textFontFamilyAlias
                    fontSize = unimportantFontSizeAlias
                    backgroundColor = recipeNameColorAlias
                    color = buttonFontColor
                    borderStyle = None.none
                    borderRadius = commonButtonBorderRadiusAlias
                    paddingLeft = 1.pc
                    paddingRight = 1.pc
                    paddingTop = 0.5.pc
                    paddingBottom = 0.5.pc
                    marginRight = 0.3.pc
                    marginBottom = 0.5.pc
                    cursor = Cursor.pointer
                }
                type = ButtonType.button
                onClick = {
                    recipeTagsState = recipeTagsState + tag
                }
                +tag.value
            }
        }

        div {
            css {
                display = Display.grid
                gridTemplateAreas = GridTemplateAreas(
                    GridArea("IngredientSelection Selected"),
                    GridArea("SeasoningSelection Selected"),
                    GridArea("UploadPictures UploadPictures"),
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
                    gridArea = GridArea("IngredientSelection")
                    backgroundColor = recipeColorAlias
                    width = 30.pc

                    padding = 1.pc
                    borderRadius = commonBorderRadiusAlias

                    whiteSpace = WhiteSpace.preWrap
                    fontFamily = textFontFamilyAlias
                    fontSize = unimportantFontSizeAlias
                }

                br { }

                select {
                    css {
                        fontFamily = textFontFamilyAlias
                        fontSize = unimportantFontSizeAlias
                        marginTop = 1.pc
                    }
                    name = "Ingredient"
                    id = "Ingredient"
                    onChange = { event ->
                        selectedIngredientConfigState = selectedIngredientConfigState.newWithField(
                            newSelected = Ingredient(event.target.value)
                        )
                    }
                    option { +"Select vegetable or meat" }
                    allIngredientsState.map { ingredient ->
                        option {
                            value = ingredient.getValue()
                            +ingredient.getValue()
                        }
                    }
                }
                span { +" " }
                button {
                    css {
                        fontFamily = textFontFamilyAlias
                        fontSize = unimportantFontSizeAlias
                        backgroundColor = recipeNameColorAlias
                        color = buttonFontColor
                        borderRadius = roundBorderRadius
                        borderWidth = 0.pc
                        cursor = Cursor.pointer
                    }
                    type = ButtonType.button
                    onClick = {
                        popUpWindowConfigState = popUpWindowConfigState.newWithField(
                            newShow = true,
                            newMessage = "",
                            newShowAddIngredientTextBox = true,
                        )
                    }
                    onMouseEnter = {
                        showAddIngredientDetails = true
                    }
                    onMouseLeave = {
                        showAddIngredientDetails = false
                    }
                    +"+"
                }
                if (showAddIngredientDetails) {
                    span {
                        css {
                            fontSize = unimportantFontSizeAlias
                            backgroundColor = hoverColorAlias
                            borderColor = NamedColor.lightgrey
                            borderStyle = solid
                        }
                        +"Missing ingredient? Add it!"
                    }
                }
                p {
                    +"Quantity "
                    input {
                        css {
                            width = 5.pc
                        }
                        type = InputType.number
                        onChange = { event ->
                            selectedIngredientConfigState = selectedIngredientConfigState.newWithField(
                                newQuantity = event.target.value.toIntOrNull()
                            )
                        }
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
                        onChange = { event ->
                            selectedIngredientConfigState = selectedIngredientConfigState.newWithField(
                                newDescription = event.target.value
                            )
                        }
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
                        if (selectedIngredientConfigState.isValid()) {
                            selectedIngredientsState = selectedIngredientsState +
                                    selectedIngredientConfigState.getIngredient()
                        }
                    }
                    +"Add vegetable or meat"
                }
            }

            div {
                css {
                    gridArea = GridArea("SeasoningSelection")
                    backgroundColor = recipeColorAlias
                    width = 30.pc

                    padding = 1.pc
                    borderRadius = commonBorderRadiusAlias

                    whiteSpace = WhiteSpace.preWrap
                    fontFamily = textFontFamilyAlias
                    fontSize = unimportantFontSizeAlias
                }

                br { }
                select {
                    css {
                        fontFamily = textFontFamilyAlias
                        fontSize = unimportantFontSizeAlias
                        marginTop = 1.pc
                    }
                    name = "SeasoningType"
                    id = "SeasoningType"
                    onChange = { event ->
                        selectedSeasoningConfigState = selectedSeasoningConfigState.newWithField(
                            newSelected = Seasoning(event.target.value)
                        )
                    }
                    option { +"Select seasoning" }
                    allSeasoningState.map { ingredient ->
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
                        onChange = { event ->
                            selectedSeasoningConfigState = selectedSeasoningConfigState.newWithField(
                                newQuantity = event.target.value.toIntOrNull()
                            )
                        }
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
                                onChange = {
                                    selectedSeasoningConfigState = selectedSeasoningConfigState.newWithField(
                                        newUnit = ingredientUnit
                                    )
                                }
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
                        onChange = { event ->
                            selectedSeasoningConfigState = selectedSeasoningConfigState.newWithField(
                                newDescription = event.target.value
                            )
                        }
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
                        selectedSeasoningsState =selectedSeasoningsState +
                                selectedSeasoningConfigState.getIngredient()
                    }
                    +"Add seasoning"
                }
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
                selectedIngredientsState.map { ingredient ->
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
                                selectedIngredientsState = selectedIngredientsState - ingredient
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
                    +"Seasoning"
                }
                selectedSeasoningsState.map { ingredient ->
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
                                selectedSeasoningsState = selectedSeasoningsState - ingredient
                            }
                            +"x"
                        }
                        +"  ${ingredient.getString()}"
                    }
                }
            }
            div {
                css {
                    gridArea = GridArea("UploadPictures")
                    zIndex = ZIndex(99)
                }

                input {
                    type = InputType.file
                    accept = "image/png"
                    onChange = { event ->
                        val file = event.target.files!![0]!!
                        recipeImageState = file
                        println("file.name: ${file.name}, file.size: ${file.size}")
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
                onChange = { event -> recipeDescriptionState = event.target.value }
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
                when {
                    recipeNameState == null -> {
                        popUpWindowConfigState = popUpWindowConfigState.newWithField(
                            newMessage = "Recipe name is not set!"
                        )
                    }
                    recipeTagsState.isEmpty() -> {
                        popUpWindowConfigState = popUpWindowConfigState.newWithField(
                            newMessage = "Please choose at least 1 tag"
                        )
                    }
                    else -> {
                        val recipe = Recipe(
                            recipeNameState!!,
                            selectedIngredientsState,
                            selectedSeasoningsState,
                            recipeTagsState,
                            recipeDescriptionState
                        )
                        scope.launch {
                            val message = when (addRecipe(recipe).status) {
                                HttpStatusCode.OK -> {
                                    val response = recipeImageState?.let {
                                        println("Creating recipe in progress...")
                                        popUpWindowConfigState = popUpWindowConfigState.newWithField(
                                            newMessage = "Creating recipe in progress..."
                                        )
                                        uploadRecipePicture(recipe.id, it)
                                    }
                                    when(response?.status) {
                                        HttpStatusCode.OK, null -> "Congratulations! Recipe $recipeNameState is added!"
                                        else -> "Error while uploading recipe picture"
                                    }
                                }
                                HttpStatusCode.Conflict -> "Error since a recipe with the same name already exists"
                                HttpStatusCode.Unauthorized -> "Please log in first"
                                else -> "Unknown error occurred, please contact Ling"
                            }
                            popUpWindowConfigState = popUpWindowConfigState.newWithField(
                                newMessage = message
                            )
                        }
                    }
                }
                popUpWindowConfigState = popUpWindowConfigState.newWithField(
                    newShow = true,
                    newShowAddIngredientTextBox = false
                )
            }
            +"Add recipe"
        }

        if (popUpWindowConfigState.show) {
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
                    +popUpWindowConfigState.message
                }
                if (popUpWindowConfigState.showAddIngredientComponents) {
                    input {
                        css {
                            fontSize = textFontSizeAlias
                            textAlign = center
                            margin = Auto.auto
                        }
                        type = InputType.text
                        placeholder = "Ingredient Name"
                        onChange = { event ->
                            createCustomIngredientConfigState = createCustomIngredientConfigState.newWithField(newName = event.target.value)
                        }
                    }
                    p { +"" }

                    CreateCustomIngredientConfig.CreateIngredientType.values().map { createIngredientType ->
                        label {
                            css {
                                fontFamily = textFontFamilyAlias
                                fontSize = textFontSizeAlias
                            }
                            input {
                                type = InputType.radio
                                value = createIngredientType.value
                                name = "ingredientUnit"
                                onChange = {
                                    createCustomIngredientConfigState = createCustomIngredientConfigState.newWithField(newCreateIngredientType = createIngredientType)
                                }
                            }
                        }
                        +createIngredientType.value
                        p { +"" }
                    }
                    button {
                        css {
                            color = NamedColor.white
                            backgroundColor = recipeNameColorAlias
                            borderColor = recipeNameColorAlias
                            borderRadius = commonButtonBorderRadiusAlias
                            height = 2.pc
                            width = 6.pc
                            cursor = Cursor.pointer

                            onClick = {
                                scope.launch {
                                    var popUpMessage: String
                                    if (createCustomIngredientConfigState.isValid()) {
                                        val result = when (createCustomIngredientConfigState.ingredient) {
                                            CreateCustomIngredientConfig.CreateIngredientType.MAIN_INGREDIENT -> {
                                                addIngredient(
                                                    Ingredient(createCustomIngredientConfigState.name!!, true)
                                                )
                                            }
                                            CreateCustomIngredientConfig.CreateIngredientType.OTHER_INGREDIENT -> {
                                                addIngredient(
                                                    Ingredient(createCustomIngredientConfigState.name!!, false)
                                                )
                                            }
                                            CreateCustomIngredientConfig.CreateIngredientType.SEASONING -> {
                                                addSeasoning(Seasoning(createCustomIngredientConfigState.name!!))
                                            }
                                        }
                                        popUpMessage = when (result.status) {
                                            HttpStatusCode.OK -> "Congratulations! Ingredient ${createCustomIngredientConfigState.name} is added!"
                                            HttpStatusCode.Conflict -> "Error since an ingredient with the same name already exists"
                                            HttpStatusCode.Unauthorized -> "Please log in first"
                                            else -> "Unknown error occurred, please contact Ling"
                                        }
                                    } else {
                                        popUpMessage = "Please enter the ingredient name first"
                                    }
                                    popUpWindowConfigState = PopUpWindowConfig(
                                        show = true,
                                        message = popUpMessage
                                    )
                                }
                            }
                        }
                        +"Create"
                    }
                }
                else {
                    button {
                        css {
                            color = NamedColor.white
                            backgroundColor = recipeNameColorAlias
                            borderColor = recipeNameColorAlias
                            borderRadius = commonButtonBorderRadiusAlias
                            height = 2.pc
                            width = 6.pc
                            cursor = Cursor.pointer

                            onClick = {
                                popUpWindowConfigState = popUpWindowConfigState.newWithField(
                                    newShow = false
                                )
                            }
                        }
                        +"OK"
                    }
                }
            }
        }
    }

    Footer { }
}