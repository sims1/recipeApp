package pages

import addIngredientType
import addRecipe
import addSpiceAndSauceType
import atomics.*
import atomics.ingredient.*
import components.shared.Footer
import components.shared.Header
import components.common.*
import components.edit.AddCustomIngredientConfig
import components.edit.PopUpWindowConfig
import components.edit.SelectedSpiceAndSauceConfig
import components.edit.SelectedVegetableAndMeatConfig
import csstype.*
import csstype.LineStyle.Companion.solid
import csstype.Position.Companion.fixed
import csstype.TextAlign.Companion.center
import getListOfIngredientTypes
import getListOfSpiceAndSauceTypes
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

    var selectedVegetableAndMeatConfigState: SelectedVegetableAndMeatConfig by useState(SelectedVegetableAndMeatConfig())
    var vegetableAndMeatIngredientsState: List<IngredientDetails<Ingredient>> by useState(emptyList())

    var selectedSpiceAndSauceConfigState: SelectedSpiceAndSauceConfig by useState(SelectedSpiceAndSauceConfig())
    var spiceAndSauceIngredientsState: List<IngredientDetails<SpiceAndSauceType>> by useState(emptyList())

    var recipeImageState: File? by useState(null)
    var descriptionState: String by useState("")

    var popUpWindowConfigState: PopUpWindowConfig by useState(PopUpWindowConfig())
    var addCustomIngredientConfigState: AddCustomIngredientConfig by useState(AddCustomIngredientConfig())

    var showAddIngredientDetails: Boolean by useState(false)

    var ingredientTypesState: List<Ingredient> by useState(emptyList())
    var spiceAndSauceTypesState: List<SpiceAndSauceType> by useState(emptyList())

    useEffectOnce {
        scope.launch {
            ingredientTypesState = getListOfIngredientTypes()
            spiceAndSauceTypesState = getListOfSpiceAndSauceTypes()
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
                    GridArea("VegetableAndMeatSelection Selected"),
                    GridArea("SpiceAndSauceSelection Selected"),
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
                    gridArea = GridArea("VegetableAndMeatSelection")
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
                    name = "VegetableAndMeatType"
                    id = "VegetableAndMeatType"
                    onChange = { event ->
                        selectedVegetableAndMeatConfigState = selectedVegetableAndMeatConfigState.newWithField(
                            newSelected = TypeStringConverter.getVegetableAndMeatType(event.target.value)
                        )
                    }
                    option { +"Select vegetable or meat" }
                    ingredientTypesState.map { ingredient ->
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
                            selectedVegetableAndMeatConfigState = selectedVegetableAndMeatConfigState.newWithField(
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
                            selectedVegetableAndMeatConfigState = selectedVegetableAndMeatConfigState.newWithField(
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
                        if (selectedVegetableAndMeatConfigState.isValid()) {
                            vegetableAndMeatIngredientsState = vegetableAndMeatIngredientsState +
                                    selectedVegetableAndMeatConfigState.getIngredient()
                        }
                    }
                    +"Add vegetable or meat"
                }
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
                        selectedSpiceAndSauceConfigState = selectedSpiceAndSauceConfigState.newWithField(
                            newSelected = TypeStringConverter.getSpiceAndSauceType(event.target.value)
                        )
                    }
                    option { +"Select spice or sauce" }
                    spiceAndSauceTypesState.map { ingredient ->
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
                            selectedSpiceAndSauceConfigState = selectedSpiceAndSauceConfigState.newWithField(
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
                                    selectedSpiceAndSauceConfigState = selectedSpiceAndSauceConfigState.newWithField(
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
                            selectedSpiceAndSauceConfigState = selectedSpiceAndSauceConfigState.newWithField(
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
                        spiceAndSauceIngredientsState =spiceAndSauceIngredientsState +
                                selectedSpiceAndSauceConfigState.getIngredient()
                    }
                    +"Add spice or sauce"
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
                            vegetableAndMeatIngredientsState,
                            spiceAndSauceIngredientsState,
                            recipeTagsState,
                            descriptionState
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
                            addCustomIngredientConfigState = addCustomIngredientConfigState.newWithField(newName = event.target.value)
                        }
                    }
                    p { +"" }

                    AddCustomIngredientConfig.AddIngredientType.values().map { addIngredientType ->
                        label {
                            css {
                                fontFamily = textFontFamilyAlias
                                fontSize = textFontSizeAlias
                            }
                            input {
                                type = InputType.radio
                                value = addIngredientType.value
                                name = "ingredientUnit"
                                onChange = {
                                    addCustomIngredientConfigState = addCustomIngredientConfigState.newWithField(newAddIngredientType = addIngredientType)
                                }
                            }
                        }
                        +addIngredientType.value
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
                                    if (addCustomIngredientConfigState.isValid()) {
                                        val result = when (addCustomIngredientConfigState.ingredientType) {
                                            AddCustomIngredientConfig.AddIngredientType.MAIN_INGREDIENT -> {
                                                addIngredientType(
                                                    Ingredient(addCustomIngredientConfigState.name!!, true)
                                                )
                                            }
                                            AddCustomIngredientConfig.AddIngredientType.OTHER_INGREDIENT -> {
                                                addIngredientType(
                                                    Ingredient(addCustomIngredientConfigState.name!!, false)
                                                )
                                            }
                                            AddCustomIngredientConfig.AddIngredientType.SPICES_AND_SAUCE -> {
                                                addSpiceAndSauceType(SpiceAndSauceType(addCustomIngredientConfigState.name!!))
                                            }
                                        }
                                        popUpMessage = when (result.status) {
                                            HttpStatusCode.OK -> "Congratulations! Ingredient ${addCustomIngredientConfigState.name} is added!"
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
                        +"Add"
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