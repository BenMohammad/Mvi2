package com.benmohammad.mvi2.screens.addedittodo

import com.benmohammad.mvi2.mvi.ModelStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoEditorModelStore @Inject constructor(): ModelStore<TodoEditorState>(TodoEditorState.Closed)