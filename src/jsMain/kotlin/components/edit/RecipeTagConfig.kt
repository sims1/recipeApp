package components.edit

import atomics.Tag

class RecipeTagConfig(
    private val tagToIsSelected: MutableMap<Tag, Boolean> = Tag.values().associateWith { false }.toMutableMap()
) {

    fun newWithClick(tag: Tag): RecipeTagConfig {
        when (this.tagToIsSelected[tag] == true) {
            true -> this.tagToIsSelected[tag] = false
            else -> this.tagToIsSelected[tag] = true
        }
        return RecipeTagConfig(this.tagToIsSelected)
    }

    fun isSelected(tag: Tag): Boolean {
        return this.tagToIsSelected[tag]!!
    }

    fun isNoneSelected(): Boolean {
        return this.tagToIsSelected.values.none { it }
    }

    fun getSelectedTags(): List<Tag> {
        return tagToIsSelected.filter { it.value }.keys.toList()
    }
}