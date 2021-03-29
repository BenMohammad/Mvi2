package com.benmohammad.mvi2.screens.todolist

import com.benmohammad.mvi2.FilterType
import com.benmohammad.mvi2.TodoEntity
import com.benmohammad.mvi2.TodoListState
import com.benmohammad.mvi2.TodoListStore
import com.benmohammad.mvi2.mvi.Intent
import com.benmohammad.mvi2.mvi.intent
import com.benmohammad.mvi2.mvi.sideEffect
import com.benmohammad.mvi2.screens.addedittodo.AddEditTodoActivity
import com.benmohammad.mvi2.screens.addedittodo.AddEditTodoIntentFactory
import com.benmohammad.mvi2.screens.addedittodo.TodoEditorModelStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoIntentFactory @Inject constructor(
        private val store: TodoListStore,
        private val editorModelStore: TodoEditorModelStore
) {

    fun process(viewEvents: TodoListViewEvent) {
        store.process(toIntent(viewEvents))
    }

    private fun toIntent(viewEvent: TodoListViewEvent): Intent<TodoListState> {
        return when (viewEvent) {
            TodoListViewEvent.NewTodoClick -> buildNewTodoIntent()
            TodoListViewEvent.FilterTodoClick -> buildCycleFilterIntent()
            TodoListViewEvent.ClearCompletedTask -> buildClearCompletedIntent()
            TodoListViewEvent.RefreshTodoClick -> TODO()
            TodoListViewEvent.PullRefreshSwipe -> TODO()
            is TodoListViewEvent.CompleteTodoClick -> buildCompleteTaskClick(viewEvent.task, viewEvent.checked)
            is TodoListViewEvent.EditTaskClicked -> buildEditTaskClick(viewEvent.task)
        }
    }

    private fun buildEditTaskClick(task: TodoEntity): Intent<TodoListState> {
        return sideEffect {
            
        }

    }

    private fun buildCompleteTaskClick(todo: TodoEntity, checked: Boolean): Intent<TodoListState> {
        return intent {
            val mutableTodos = todos.toMutableList()
            mutableTodos[mutableTodos.indexOf(todo)] = todo.copy(completed = checked)
            copy(todos = mutableTodos)
        }
    }



    private fun buildNewTodoIntent(): Intent<TodoListState> {
        return sideEffect {
            val addIntent = AddEditTodoIntentFactory.buildAddTaskIntent(TodoEntity())
            editorModelStore.process(addIntent)
        }
    }

    private fun buildClearCompletedIntent(): Intent<TodoListState> {
        return intent {
            copy(todos = todos.filter { it.completed } )
        }
    }

    private fun buildCycleFilterIntent(): Intent<TodoListState> {
        return intent {
            copy(
                    filterType = when (this.filterType) {
                        FilterType.ANY -> FilterType.COMPLETED
                        FilterType.COMPLETED -> FilterType.NOT_COMPLETED
                        FilterType.NOT_COMPLETED -> FilterType.ANY
                    }
            )
        }
    }
}