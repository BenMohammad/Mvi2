package com.benmohammad.mvi2.screens.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benmohammad.mvi2.R
import com.benmohammad.mvi2.TodoListState
import com.benmohammad.mvi2.TodoListStore
import com.benmohammad.mvi2.mvi.StateSubscriber
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class TodoAdapter @Inject constructor(
    private val taskIntentFactory: TodoIntentFactory,
    private val store: TodoListStore
): RecyclerView.Adapter<TodoVH>(), StateSubscriber<TodoListState> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoVH {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoVH(rootView).apply {
            events().subscribe(taskIntentFactory::process)
        }
    }

    override fun onBindViewHolder(holder: TodoVH, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun Observable<TodoListState>.subscribeToState(): Disposable {
        TODO("Not yet implemented")
    }
}