package com.benmohammad.mvi2

import com.benmohammad.mvi2.mvi.ModelStore
import javax.inject.Inject

class TodoListStore @Inject constructor(): ModelStore<TodoListState>(
    TodoListState(
        listOf(
            TodoEntity("jk1", "Hello"),
            TodoEntity("jk2", "Bonjour"),
            TodoEntity("jk3", "Hola")
        ),
        FilterType.ANY,
        SyncState.IDLE
    )
)