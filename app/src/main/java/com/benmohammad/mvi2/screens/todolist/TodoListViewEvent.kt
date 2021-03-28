package com.benmohammad.mvi2.screens.todolist

import com.benmohammad.mvi2.TodoEntity

sealed class TodoListViewEvent {
    object NewTodoClick: TodoListViewEvent()
    object FilterTodoClick: TodoListViewEvent()
    object ClearCompletedTask: TodoListViewEvent()
    object RefreshTodoClick: TodoListViewEvent()
    object PullRefreshSwipe: TodoListViewEvent()
    data class CompleteTodoClick(val task: TodoEntity, val checked: Boolean): TodoListViewEvent()
    data class EditTaskClicked(val task: TodoEntity): TodoListViewEvent()
}
