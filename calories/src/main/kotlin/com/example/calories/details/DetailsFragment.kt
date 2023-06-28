package com.example.calories.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import co.example.calories.databinding.FragmentDetailsBinding
import com.example.BaseActivity
import com.example.calories.FoodEntry
import com.example.calories.dagger.module.Injector
import com.example.calories.details.adpater.*
import com.example.calories.entry.EntryActivity
import com.example.calories.main.CaloriesActivity
import com.example.calories.main.CaloriesActivity.Companion.ADD_ENTRY_REQUEST_CODE
import co.example.calories.R
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

    private lateinit var binding: FragmentDetailsBinding

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
        binding = FragmentDetailsBinding.inflate(layoutInflater)
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

    @Composable
    fun ListItem(data: EntryRowDate) {
        Text(
            text = data.date.toString("EEEE MMMM dd"),
            modifier = Modifier.padding(15.dp),
            fontWeight = FontWeight.Bold
        )
    }

    @Composable
    fun ListItem(data: EntryRowMealType) {
        Text(
            text = data.mealType.name,
            modifier = Modifier.padding(15.dp, 5.dp, 0.dp, 5.dp),
            fontWeight = FontWeight.Bold
        )
    }

    @Composable
    fun ListItem(data: EntryRowMealEntry) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.birdWhite),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                val (name, calories, fat, protein, carbs) = createRefs()

                Text(
                    data.entry.name ?: "",
                    Modifier
                        .width(100.dp)
                        .constrainAs(name) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )

                Text(
                    data.entry.calories.toString(),
                    Modifier.constrainAs(calories) {
                        start.linkTo(name.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                )

                data.entry.fat?.let {
                    Text(
                        String.format("%s (f)", it.toString()),
                        Modifier
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                            .constrainAs(fat) {
                                end.linkTo(protein.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                }

                data.entry.protein?.let {
                    Text(
                        String.format("%s (p)", it.toString()),
                        Modifier
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                            .constrainAs(protein) {
                                end.linkTo(carbs.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                }

                data.entry.carb?.let {
                    Text(
                        String.format("%s (c)", it.toString()),
                        Modifier
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                            .constrainAs(carbs) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                } ?: run {
                    Text(
                        "",
                        Modifier
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                            .constrainAs(carbs) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                }

            }
        }
    }

    @Composable
    fun ListItem(data: EntryRowCalories) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth().padding(15.dp)
        ) {
            val (caloriesDescription, calories) = createRefs()

            Text(
                stringResource(R.string.total_calories),
                Modifier.constrainAs(caloriesDescription) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                fontWeight = FontWeight.Bold
            )

            Text(
                data.caloriesTotal.toString(),
                Modifier.constrainAs(calories) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
                fontWeight = FontWeight.Bold
            )
        }
    }

    @Composable
    fun ListItem(data: EntryRowDivider) {
        Divider(thickness = 1.dp, color = Color.Gray)
    }

    override fun render(state: DetailsState) {
        when (state) {
            is Initialize -> {
                binding.composeView.setContent {
                    LazyColumn(Modifier.fillMaxSize()) {
                        this.items(
                            items = state.rows,
                            itemContent = {
                                when (it.type) {
                                    EntryRowType.DATE -> ListItem(data = it as EntryRowDate)
                                    EntryRowType.MEAL_ENTRY -> ListItem(data = it as EntryRowMealEntry)
                                    EntryRowType.MEAL_TYPE -> ListItem(data = it as EntryRowMealType)
                                    EntryRowType.CALORIES_TOTAL -> ListItem(data = it as EntryRowCalories)
                                    EntryRowType.DIVIDER -> ListItem(data = it as EntryRowDivider)
                                }
                            }
                        )
                    }
                }
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