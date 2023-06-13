package com.example.calories.details

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.example.calories.R
import co.example.calories.databinding.ActivityDetailsBinding
import com.example.BaseActivity
import com.example.calories.FoodEntry
import com.example.calories.dagger.module.Injector
import com.example.calories.details.adpater.DetailsAdapter
import com.example.calories.entry.EntryActivity
import com.example.calories.entry.EntryActivity.Companion.ENTRY
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class DetailsActivity : BaseActivity(), DetailsRenderer {

    companion object {
        const val ADD_ENTRY_REQUEST_CODE = 1000
    }

    @Inject
    lateinit var presenter: DetailsPresenter

    @Inject
    lateinit var adapter: DetailsAdapter

    private lateinit var binding: ActivityDetailsBinding
    private val updatedSubject = PublishSubject.create<Unit>()

    override val addEntryClicked: Observable<Unit> by lazy { binding.add.clicks() }

    override val entrySelected: Observable<FoodEntry> by lazy { adapter.entrySelected() }

    override val entriesUpdated: Observable<Unit> by lazy { updatedSubject }

    override val searchUpdated: Observable<CharSequence> by lazy {
        binding.search.textChanges().map { it }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ADD_ENTRY_REQUEST_CODE -> if (resultCode == RESULT_OK) {
                updatedSubject.onNext(Unit)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerDetailsComponent.factory()
            .create(
                mainComponent = Injector,
                activity = this,
            )
            .inject(this)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        setSupportActionBar(binding.toolbar)

        setContentView(binding.root)

        presenter.consume(this)
    }

    override fun render(state: DetailsState) {
        when (state) {
            is Initialize -> {
                adapter.setEntries(state.rows)
                binding.recyclerView.adapter = adapter
            }
            is Loading -> binding.progressBar.isVisible = state.showLoading
            is Start -> {}
            is NewEntry -> this.startActivityForResult(
                Intent(this, EntryActivity::class.java),
                ADD_ENTRY_REQUEST_CODE)
            is EditEntry -> this.startActivityForResult(
                Intent(this, EntryActivity::class.java).also {
                    it.putExtra(ENTRY, state.entry)
                },
                ADD_ENTRY_REQUEST_CODE)
        }
    }

}