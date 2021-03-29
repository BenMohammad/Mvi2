package com.benmohammad.mvi2.screens.addedittodo

import com.benmohammad.mvi2.TodoEntity

sealed class TodoEditorState {
    object Closed : TodoEditorState() {
        fun addTodo(todo: TodoEntity) = Editing(todo)
        fun editTodo(todo: TodoEntity) = Editing(todo)
    }

    data class Editing(val todo: TodoEntity, val adding: Boolean = false): TodoEditorState() {
        fun edit(block: TodoEntity.() -> TodoEntity) = copy(todo = todo.block())
        fun save() = Saving(todo)
        fun delete() = Deleting(todo.id)
        fun cancel() = Closed
    }

    data class Saving(val todo: TodoEntity): TodoEditorState() {
        fun saved() = Closed
    }

    data class Deleting(val uuid: String): TodoEditorState() {
        fun deleted() = Closed
    }
}
