package co.example.calories.core.mrp;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\bf\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00030\u0002*\u0004\b\u0001\u0010\u00032\u00020\u0004J\u0015\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0000H&\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\t\u001a\u00020\u00062\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00010\u000bH&J\u0015\u0010\t\u001a\u00020\u00062\u0006\u0010\f\u001a\u00028\u0001H&\u00a2\u0006\u0002\u0010\rJ\r\u0010\f\u001a\u00028\u0001H&\u00a2\u0006\u0002\u0010\u000eJ\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\u0010H&\u00a8\u0006\u0011"}, d2 = {"Lco/example/calories/core/mrp/Presenter;", "R", "Lco/example/calories/core/mrp/Renderer;", "S", "", "consume", "", "renderer", "(Lco/example/calories/core/mrp/Renderer;)V", "emit", "reducer", "Lkotlin/Function1;", "state", "(Ljava/lang/Object;)V", "()Ljava/lang/Object;", "states", "Lio/reactivex/Observable;", "core-basemrp_debug"})
public abstract interface Presenter<R extends co.example.calories.core.mrp.Renderer<? super S>, S extends java.lang.Object> {
    
    /**
     * Consume [renderer]'s input streams. This is where we configure a
     * all core logic of presenter
     *
     * @param renderer A renderer which provides user input events
     */
    public abstract void consume(@org.jetbrains.annotations.NotNull()
    R renderer);
    
    /**
     * Emit state by applying [reducer] function on the latest state
     *
     * @param reducer The reducer functor to transform state
     */
    public abstract void emit(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super S, ? extends S> reducer);
    
    /**
     * Emit a single state
     *
     * @param state A state to be emitted
     */
    public abstract void emit(S state);
    
    /**
     * Return the latest state
     */
    public abstract S state();
    
    /**
     * Return state stream as [Observable]
     */
    @org.jetbrains.annotations.NotNull()
    public abstract io.reactivex.Observable<S> states();
}