package com.benmohammad.mvi2.screens.addedittodo

sealed class AddEditTodoViewEvent {

    data class TitleChange(val title: String): AddEditTodoViewEvent()
    data class DescriptionChange(val description: String): AddEditTodoViewEvent()
    object SaveTaskClick: AddEditTodoViewEvent()
    object DeleteTaskClick: AddEditTodoViewEvent()
    object CancelTaskClick: AddEditTodoViewEvent()
}
