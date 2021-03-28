package com.benmohammad.mvi2

data class TodoListState(
    val todos: List<TodoEntity>,
    val filterType: FilterType,
    val syncState: SyncState
)

enum class FilterType {
    ANY,
    COMPLETED,
    NOT_COMPLETED
}

fun FilterType.filter(item: TodoEntity): Boolean {
    return when (this) {
        FilterType.ANY -> true
        FilterType.COMPLETED -> item.completed
        FilterType.NOT_COMPLETED -> !item.completed
    }
}

enum class SyncState {
    IDLE,
    REFRESH,

}
