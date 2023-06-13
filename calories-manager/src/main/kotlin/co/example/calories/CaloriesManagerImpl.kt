package co.example.calories

import com.example.calories.FoodEntry
import com.example.manager.CaloriesManager
import com.example.repository.EntryRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.DateTime

class CaloriesManagerImpl(
    private val repo: EntryRepository
): CaloriesManager {
    override fun loadEntries(dateTime: DateTime?): Observable<List<FoodEntry>> {
        return repo.foodEntriesBefore(dateTime)
    }

    override fun add(entry: FoodEntry): Completable {
        return repo.insertFoodEntry(entry)
    }

    override fun update(entry: FoodEntry): Completable {
        return repo.updateFoodEntry(entry)
    }

    override fun delete(entry: FoodEntry): Completable {
        return repo.deleteFoodEntry(entry)
    }

    override fun search(key: String): Single<List<FoodEntry>> {
        return repo.search(key)
    }

}