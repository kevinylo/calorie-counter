package co.example.calories

import com.example.calories.FoodEntry
import com.example.persistencecalories.api.FoodEntryDao
import com.example.repository.EntryRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

class EntryRepositoryImpl(
    private val foodEntryDao: FoodEntryDao
): EntryRepository {
    companion object {
        const val SQL_PARTIAL_STRING_MATCHING_FORMAT = "%%%s%%"
    }

    override fun foodEntriesBefore(dateTime: DateTime?): Observable<List<FoodEntry>> {
        return foodEntryDao.foodEntriesBeforeDateTime(dateTime ?: DateTime.now(DateTimeZone.forOffsetHours(-8)))
            .observeOn(Schedulers.io())
    }

    override fun foodEntriesTodayOr(dateTime: DateTime?): Single<List<FoodEntry>> {
        return foodEntryDao.foodEntriesTodayOr(dateTime ?: DateTime.now(DateTimeZone.forOffsetHours(-8)).toLocalDate().toDateTimeAtStartOfDay())
            .observeOn(Schedulers.io())
    }

    override fun insertFoodEntry(entry: FoodEntry): Completable {
        return foodEntryDao.insertFoodEntry(entry)
            .observeOn(Schedulers.io())
    }

    override fun deleteFoodEntry(entry: FoodEntry): Completable {
        return foodEntryDao.deleteFoodEntry(entry)
            .observeOn(Schedulers.io())
    }

    override fun updateFoodEntry(entry: FoodEntry): Completable {
        return foodEntryDao.updateFoodEntry(entry)
            .observeOn(Schedulers.io())
    }

    override fun search(key: String): Single<List<FoodEntry>> {
        return foodEntryDao.foodEntriesNameMatching(String.format(SQL_PARTIAL_STRING_MATCHING_FORMAT, key))
            .observeOn(Schedulers.io())
    }

    override fun clear(): Completable {
        return foodEntryDao.clearFoodEntries()
            .observeOn(Schedulers.io())
    }

}