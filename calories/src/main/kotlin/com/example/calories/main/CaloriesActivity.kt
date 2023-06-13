package com.example.calories.main

import android.content.Intent
import android.database.Observable
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.example.calories.databinding.ActivityMainBinding
import com.example.BaseActivity
import com.example.calories.details.DetailsActivity
import com.example.calories.details.DetailsFragment
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject

class CaloriesActivity : BaseActivity() {

    companion object {
        const val ADD_ENTRY_REQUEST_CODE = 1000
    }

    private lateinit var binding: ActivityMainBinding
    val updatedSubject = PublishSubject.create<Unit>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val pagerAdapter = MainPagerAdapter(this)
        binding.pager.adapter = pagerAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ADD_ENTRY_REQUEST_CODE -> if (resultCode == RESULT_OK) {
                updatedSubject.onNext(Unit)
            }
        }
    }

    private inner class MainPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 2

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> CaloriesFragment()
                else -> DetailsFragment()
            }
        }
    }
}