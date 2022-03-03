package recipes

import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.pre
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr
import recipes.atomics.Recipe

external interface RecipeTableProps : Props {
    var recipes: List<Recipe>
}

val RecipeTable = FC<RecipeTableProps> { props ->
    table {
        tr {
            ReactHTML.th { +"Type" }
            ReactHTML.th { +"Name" }
            ReactHTML.th { +"Ingredients" }
            ReactHTML.th { +"Spices and Sauces" }
            ReactHTML.th { +"Cooking Instructions" }
        }
        props.recipes.map { recipe ->
            tr {
                td { +recipe.getTypeString() }
                td { +recipe.getNameString() }
                td { pre { +recipe.getIngredientsString() } }
                td { pre { +recipe.getSpicesAndSaucesString() } }
                td { pre { +recipe.getCookingInstructionsString() } }
            }
        }
    }
}