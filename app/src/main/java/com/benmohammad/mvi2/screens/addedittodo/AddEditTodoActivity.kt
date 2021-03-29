package com.benmohammad.mvi2.screens.addedittodo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.benmohammad.mvi2.R
import com.benmohammad.mvi2.appComponent
import com.benmohammad.mvi2.mvi.EventObservable
import com.benmohammad.mvi2.mvi.StateSubscriber
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_add_edit_todo.*
import javax.inject.Inject

class AddEditTodoActivity: AppCompatActivity(), EventObservable<AddEditTodoViewEvent>, StateSubscriber<TodoEditorState> {

    @Inject lateinit var store: TodoEditorModelStore
    @Inject lateinit var intentFactory: AddEditTodoIntentFactory
    private val disposable = CompositeDisposable()

    private val backClickPublishRelay = PublishRelay.create<AddEditTodoViewEvent.CancelTaskClick>()

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_todo)
        add_todo.setOnClickListener {
            finish()
        }
    }



    override fun events(): Observable<AddEditTodoViewEvent> {
        return Observable.merge(
                listOf(
                        todo_title.textChanges().map { AddEditTodoViewEvent.TitleChange(it.toString()) },
                        todo_desc.textChanges().map {AddEditTodoViewEvent.DescriptionChange(it.toString())},
                        delete_todo.clicks().map { AddEditTodoViewEvent.DeleteTaskClick },
                        add_todo.clicks().map { AddEditTodoViewEvent.SaveTaskClick },
                        backClickPublishRelay
                )
        )
    }

    override fun Observable<TodoEditorState>.subscribeToState(): Disposable {
        return ofType<TodoEditorState.Editing>().firstElement().subscribe(::updateView, ::updateViewError)
    }

    private fun updateView(state: TodoEditorState.Editing) {
        todo_title.setText(state.todo.title)
        todo_desc.setText(state.todo.description)
    }

    private fun updateViewError(e: Throwable) {

    }

    override fun onResume() {
        super.onResume()
        disposable += store.modelState().subscribeToState()
        disposable += events().subscribe(intentFactory::process)
    }

    override fun onPause() {
        super.onPause()
        disposable.clear()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backClickPublishRelay.accept(AddEditTodoViewEvent.CancelTaskClick)
    }
}