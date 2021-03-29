package com.benmohammad.mvi2.screens.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.benmohammad.mvi2.*
import com.benmohammad.mvi2.mvi.StateSubscriber
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class TodoAdapter @Inject constructor(
    private val taskIntentFactory: TodoIntentFactory,
    private val store: TodoListStore
): RecyclerView.Adapter<TodoVH>(), StateSubscriber<TodoListState> {

    private val disposables = CompositeDisposable()
    private lateinit var filteredTodoList: List<TodoEntity>

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoVH {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoVH(rootView).apply {
            events().subscribe(taskIntentFactory::process)
        }
    }

    override fun onBindViewHolder(holder: TodoVH, position: Int) {
        holder.bind(filteredTodoList[position])
    }

    override fun getItemCount(): Int {
        return filteredTodoList.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        disposables += store.modelState().subscribeToState()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        disposables.clear()
    }



    override fun Observable<TodoListState>.subscribeToState(): Disposable {
        return map {
            (todoList, filter) ->
            todoList.filter { task -> filter.filter(task) }
        }.distinctUntilChanged().subscribe(::updateList)
    }

    private fun updateList(todoList: List<TodoEntity>) {
        filteredTodoList = todoList
        notifyDataSetChanged()
    }
}