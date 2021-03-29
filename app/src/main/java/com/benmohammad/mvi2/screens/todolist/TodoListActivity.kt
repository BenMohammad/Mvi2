package com.benmohammad.mvi2.screens.todolist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.benmohammad.mvi2.R
import com.benmohammad.mvi2.appComponent
import com.benmohammad.mvi2.mvi.EventObservable
import com.benmohammad.mvi2.mvi.StateSubscriber
import com.benmohammad.mvi2.screens.addedittodo.AddEditTodoActivity
import com.benmohammad.mvi2.screens.addedittodo.TodoEditorModelStore
import com.benmohammad.mvi2.screens.addedittodo.TodoEditorState
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_add_edit_todo.*
import kotlinx.android.synthetic.main.activity_todo_list.*
import javax.inject.Inject


class TodoListActivity: AppCompatActivity(), EventObservable<TodoListViewEvent>, StateSubscriber<TodoEditorState> {

    @Inject
    lateinit var todoAdapter: TodoAdapter
    @Inject
    lateinit var editorStore: TodoEditorModelStore
    @Inject
    lateinit var todoIntentFactory: TodoIntentFactory

    private val disposbales = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)
        todo_list.layoutManager = LinearLayoutManager(this)
        todo_list.adapter = todoAdapter
    }

    override fun events(): Observable<TodoListViewEvent> {
        return Observable.merge(
                add_todo_main.clicks().map {TodoListViewEvent.NewTodoClick},
                filter_todo.clicks().map { TodoListViewEvent.FilterTodoClick }
        )
    }

    override fun Observable<TodoEditorState>.subscribeToState(): Disposable {
        return ofType<TodoEditorState.Editing>().subscribe() {
            val intent = Intent(this@TodoListActivity, AddEditTodoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        disposbales += editorStore.modelState().subscribeToState()
        disposbales += events().subscribe(todoIntentFactory::process)
    }

    override fun onPause() {
        super.onPause()
        disposbales.clear()
    }
}