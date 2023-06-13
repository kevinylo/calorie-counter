package co.example.calories;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\u001e\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\f0\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J\u001e\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\f0\u00102\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J\u0010\u0010\u0011\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\u001c\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\f0\u00102\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lco/example/calories/EntryRepositoryImpl;", "Lcom/example/repository/EntryRepository;", "foodEntryDao", "Lcom/example/persistencecalories/api/FoodEntryDao;", "(Lcom/example/persistencecalories/api/FoodEntryDao;)V", "clear", "Lio/reactivex/Completable;", "deleteFoodEntry", "entry", "Lcom/example/calories/FoodEntry;", "foodEntriesBefore", "Lio/reactivex/Observable;", "", "dateTime", "Lorg/joda/time/DateTime;", "foodEntriesTodayOr", "Lio/reactivex/Single;", "insertFoodEntry", "search", "key", "", "updateFoodEntry", "Companion", "repository_debug"})
public final class EntryRepositoryImpl implements com.example.repository.EntryRepository {
    private final com.example.persistencecalories.api.FoodEntryDao foodEntryDao = null;
    @org.jetbrains.annotations.NotNull()
    public static final co.example.calories.EntryRepositoryImpl.Companion Companion = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SQL_PARTIAL_STRING_MATCHING_FORMAT = "%%%s%%";
    
    public EntryRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.example.persistencecalories.api.FoodEntryDao foodEntryDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public io.reactivex.Observable<java.util.List<com.example.calories.FoodEntry>> foodEntriesBefore(@org.jetbrains.annotations.Nullable()
    org.joda.time.DateTime dateTime) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public io.reactivex.Single<java.util.List<com.example.calories.FoodEntry>> foodEntriesTodayOr(@org.jetbrains.annotations.Nullable()
    org.joda.time.DateTime dateTime) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public io.reactivex.Completable insertFoodEntry(@org.jetbrains.annotations.NotNull()
    com.example.calories.FoodEntry entry) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public io.reactivex.Completable deleteFoodEntry(@org.jetbrains.annotations.NotNull()
    com.example.calories.FoodEntry entry) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public io.reactivex.Completable updateFoodEntry(@org.jetbrains.annotations.NotNull()
    com.example.calories.FoodEntry entry) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public io.reactivex.Single<java.util.List<com.example.calories.FoodEntry>> search(@org.jetbrains.annotations.NotNull()
    java.lang.String key) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public io.reactivex.Completable clear() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lco/example/calories/EntryRepositoryImpl$Companion;", "", "()V", "SQL_PARTIAL_STRING_MATCHING_FORMAT", "", "repository_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}