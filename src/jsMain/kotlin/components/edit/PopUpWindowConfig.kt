package components.edit

class PopUpWindowConfig(
    val show: Boolean = false,
    val message: String = "",
    val showAddIngredientComponents: Boolean = false
) {
    fun newWithField(
        newShow: Boolean = show,
        newMessage: String = message,
        newShowAddIngredientTextBox: Boolean = showAddIngredientComponents
    ): PopUpWindowConfig {
        return PopUpWindowConfig(
            show = newShow,
            message = newMessage,
            showAddIngredientComponents = newShowAddIngredientTextBox
        )
    }
}