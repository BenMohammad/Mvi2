package com.benmohammad.mvi2.screens.todolist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.benmohammad.mvi2.TodoEntity
import com.benmohammad.mvi2.mvi.EventObservable
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.checkedChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.item_todo.view.*
import timber.log.Timber

class TodoVH(private val rootView: View):RecyclerView.ViewHolder(rootView), EventObservable<TodoListViewEvent> {

    private lateinit var todo: TodoEntity

    override fun events(): Observable<TodoListViewEvent> {
        return Observable.merge(
                rootView.completed.checkedChanges()
                        .skipInitialValue()
                        .doOnNext { Timber.d("Events changed to $it")}
                        .map {TodoListViewEvent.CompleteTodoClick(todo, it)},
                rootView.clicks().map { TodoListViewEvent.EditTaskClicked(todo) }
        )
    }

    fun bind(todo: TodoEntity) {
        this.todo = todo
        rootView.title.text = todo.title
        rootView.description.text = todo.description
        rootView.completed.isChecked = todo.completed
    }
}