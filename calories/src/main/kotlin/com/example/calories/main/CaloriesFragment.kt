package com.example.calories.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.example.calories.databinding.FragmentCaloriesBinding
import com.example.BaseActivity
import com.example.calories.dagger.module.Injector
import com.example.calories.entry.EntryActivity
import com.example.calories.main.CaloriesActivity.Companion.ADD_ENTRY_REQUEST_CODE
import com.example.calories.main.view.CategoryView
import com.example.calories.modelconstant.MealType
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.uber.autodispose.lifecycle.LifecycleScopes
import io.reactivex.CompletableSource
import io.reactivex.Observable
import javax.inject.Inject

class CaloriesFragment: Fragment(), CaloriesRenderer, OnChartValueSelectedListener {

    @Inject
    lateinit var presenter: CaloriesPresenter

    private lateinit var binding: FragmentCaloriesBinding

    private lateinit var bottomSheet: BottomSheetBehavior<View>

    private var selectedCategory: CategoryView? = null

    companion object {
        const val BOTTOM_SHEET_MINIMUM_HEIGHT_DP = 250
        val COLORS = listOf(
            Color.parseColor("#3ca567"), Color.parseColor("#890567"), Color.parseColor("#a35567"), Color.parseColor("#ff5f67")
        )
    }

    override val addEntryClicks: Observable<MealType> by lazy {
        Observable.merge(
            binding.breakfast.addClicks(),
            binding.lunch.addClicks(),
            binding.dinner.addClicks(),
            binding.snack.addClicks(),
        )
    }

    override val entriesUpdated: Observable<Unit> by lazy { (this.activity!! as CaloriesActivity).updatedSubject }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCaloriesBinding.inflate(layoutInflater)
        bottomSheet = BottomSheetBehavior.from(binding.bottom)
        bottomSheet.peekHeight = binding.bottom.dpToPx(BOTTOM_SHEET_MINIMUM_HEIGHT_DP)
        bottomSheet.isHideable = false
        bottomSheet.isFitToContents = true

        binding.pieChart.setOnChartValueSelectedListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DaggerCaloriesComponent.factory()
            .create(
                mainComponent = Injector,
                activity = this.activity!!,
            )
            .inject(this)
        presenter.consume(this)
    }

    override fun render(state: CaloriesState) {
        when (state) {
            is AddEntry -> this.activity!!.startActivityForResult(
                Intent(this.activity, EntryActivity::class.java).also {
                    it.putExtra(EntryActivity.CATEGORY, state.type.name)
                },
                ADD_ENTRY_REQUEST_CODE
            )
            is UpdateCategories -> {
                binding.breakfast.initialize(state.data[MealType.BREAKFAST]!!)
                binding.lunch.initialize(state.data[MealType.LUNCH]!!)
                binding.dinner.initialize(state.data[MealType.DINNER]!!)
                binding.snack.initialize(state.data[MealType.SNACK]!!)
            }
            is SetPieEntries -> {
                val pieDataSet = PieDataSet(state.pieEntries, "label")
                pieDataSet.colors = COLORS
                val pieData = PieData(pieDataSet).also { it.setDrawValues(true) }
                pieData.setValueFormatter(PercentFormatter())
                binding.pieChart.data = pieData
                binding.pieChart.invalidate()
                binding.caloriesToday.text = String.format("%s kcal", state.pieEntries.map { it.value }.reduce { total, current ->
                    total + current
                }.toString())
            }
            else -> {}
        }
    }

    override fun requestScope(): CompletableSource {
        return LifecycleScopes.resolveScopeFromLifecycle(this.activity as BaseActivity)
    }

    fun View.dpToPx(dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        e?.let {
            selectedCategory?.disableHighlight()

            when ((it as PieEntry).label) {
                MealType.BREAKFAST.name -> {
                    binding.breakfast.highlight(Color.GRAY)
                    selectedCategory = binding.breakfast
                }
                MealType.LUNCH.name -> {
                    binding.lunch.highlight(Color.GRAY)
                    selectedCategory = binding.lunch
                }
                MealType.DINNER.name -> {
                    binding.dinner.highlight(Color.GRAY)
                    selectedCategory = binding.dinner
                }
                MealType.SNACK.name -> {
                    binding.snack.highlight(Color.GRAY)
                    selectedCategory = binding.snack
                }
            }
            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onNothingSelected() {
        selectedCategory?.disableHighlight()
    }

}