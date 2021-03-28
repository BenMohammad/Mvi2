package com.benmohammad.mvi2.di

import com.benmohammad.mvi2.screens.addedittodo.AddEditTodoActivity
import com.benmohammad.mvi2.screens.todolist.TodoListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(addEditTodoActivity: AddEditTodoActivity)
    fun inject(todoListActivity: TodoListActivity)
}