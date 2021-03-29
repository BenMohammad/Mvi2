package com.benmohammad.mvi2.screens.addedittodo

import com.benmohammad.mvi2.TodoEntity
import com.benmohammad.mvi2.mvi.Intent
import com.benmohammad.mvi2.mvi.intent
import com.benmohammad.mvi2.screens.todolist.TodoIntentFactory
import io.reactivex.internal.subscriptions.SubscriptionHelper.cancel
import java.lang.IllegalStateException
import java.nio.file.Files.delete
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddEditTodoIntentFactory @Inject constructor(
        private val todoEditorModelStore: TodoEditorModelStore,
        private val todoIntentFactory: TodoIntentFactory
) {

    fun process(viewEvent: AddEditTodoViewEvent){
        todoEditorModelStore.process(toIntent(viewEvent))
    }

    private fun toIntent(viewEvent: AddEditTodoViewEvent): Intent<TodoEditorState> {
        return when (viewEvent) {
            is AddEditTodoViewEvent.TitleChange -> buildTitleChangeIntent(viewEvent)
            is AddEditTodoViewEvent.DescriptionChange -> buildEditDescriptionChangeIntent(viewEvent)
            AddEditTodoViewEvent.SaveTaskClick -> buildSaveTaskIntent()
            AddEditTodoViewEvent.DeleteTaskClick -> buildDeleteTaskIntent()
            AddEditTodoViewEvent.CancelTaskClick -> buildCancelIntent()
        }
    }

    private fun buildCancelIntent(): Intent<TodoEditorState> {
        return editorIntent<TodoEditorState.Editing> {
            cancel()
        }
    }

    private fun buildDeleteTaskIntent(): Intent<TodoEditorState> {
        return editorIntent<TodoEditorState.Editing> {
            delete()
        }
    }



    private fun buildEditDescriptionChangeIntent(descriptionChange: AddEditTodoViewEvent.DescriptionChange): Intent<TodoEditorState> {
        return editorIntent<TodoEditorState.Editing> {
            edit {
                copy(description = descriptionChange.description)
            }
        }
    }

    companion object {
        inline fun <reified S : TodoEditorState> editorIntent(
                crossinline block: S.() -> TodoEditorState
        ): Intent<TodoEditorState> {
            return intent {
                (this as? S)?.block()
                        ?: throw IllegalStateException("Bad guy")
            }
        }




        private fun buildTitleChangeIntent(titleChange: AddEditTodoViewEvent.TitleChange): Intent<TodoEditorState> {
            return editorIntent<TodoEditorState.Editing> {
                edit {
                    copy(title = titleChange.title)
                }
            }
        }


        fun buildAddTaskIntent(todo: TodoEntity): Intent<TodoEditorState> {
            return editorIntent<TodoEditorState.Closed> {
                addTodo(todo)
            }
        }

        private fun buildSaveTaskIntent(): Intent<TodoEditorState> {
            return editorIntent<TodoEditorState.Editing> {
                save()
            }
        }
    }
}