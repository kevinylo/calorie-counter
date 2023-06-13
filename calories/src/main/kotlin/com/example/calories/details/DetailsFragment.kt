package com.example.calories.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import co.example.calories.databinding.ActivityDetailsBinding
import com.example.BaseActivity
import com.example.calories.FoodEntry
import com.example.calories.dagger.module.Injector
import com.example.calories.details.adpater.DetailsAdapter
import com.example.calories.entry.EntryActivity
import com.example.calories.main.CaloriesActivity
import com.example.calories.main.CaloriesActivity.Companion.ADD_ENTRY_REQUEST_CODE
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.uber.autodispose.lifecycle.LifecycleScopes
import io.reactivex.CompletableSource
import io.reactivex.Observable
import javax.inject.Inject

class DetailsFragment: Fragment(), DetailsRenderer  {

    @Inject
    lateinit var presenter: DetailsPresenter

    @Inject
    lateinit var adapter: DetailsAdapter

    private lateinit var binding: ActivityDetailsBinding

    override val addEntryClicked: Observable<Unit> by lazy { binding.add.clicks() }

    override val entrySelected: Observable<FoodEntry> by lazy { adapter.entrySelected() }

    override val entriesUpdated: Observable<Unit> by lazy { (this.activity!! as CaloriesActivity).updatedSubject }

    override val searchUpdated: Observable<CharSequence> by lazy {
        binding.search.textChanges().map { it }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DaggerDetailsComponent.factory()
            .create(
                mainComponent = Injector,
                activity = this.activity!!,
            )
            .inject(this)

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
            is NewEntry -> this.activity?.startActivityForResult(
                Intent(this.activity, EntryActivity::class.java),
                ADD_ENTRY_REQUEST_CODE
            )
            is EditEntry -> this.activity?.startActivityForResult(
                Intent(this.activity, EntryActivity::class.java).also {
                    it.putExtra(EntryActivity.ENTRY, state.entry)
                },
                ADD_ENTRY_REQUEST_CODE
            )
        }
    }

    override fun requestScope(): CompletableSource {
        return LifecycleScopes.resolveScopeFromLifecycle(this.activity as BaseActivity)
    }
}