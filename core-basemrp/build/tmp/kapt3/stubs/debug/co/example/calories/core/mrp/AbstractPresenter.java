package co.example.calories.core.mrp;

import java.lang.System;

/**
 * Abstract base class for all [Presenter]. For more details see:
 * https://docs.google.com/document/d/13x3eBzXVm8SzV2Z8W1GHOKDDVPhe9XWehR3fEwLazMM/edit#heading=h.nrnw03t7conb
 */
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00030\u0002*\u0004\b\u0001\u0010\u00032\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\u0001\u00a2\u0006\u0002\u0010\u0006J\u0015\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00028\u0000H\u0017\u00a2\u0006\u0002\u0010\u0012J\u001a\u0010\u0013\u001a\u00020\u00102\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00010\u0015J\u0013\u0010\u0013\u001a\u00020\u00102\u0006\u0010\r\u001a\u00028\u0001\u00a2\u0006\u0002\u0010\u0006J\u000b\u0010\r\u001a\u00028\u0001\u00a2\u0006\u0002\u0010\u0016J\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00010\u0018R\u0010\u0010\u0005\u001a\u00028\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0007R \u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t8D@BX\u0084\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00010\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lco/example/calories/core/mrp/AbstractPresenter;", "R", "Lco/example/calories/core/mrp/Renderer;", "S", "Lco/example/calories/core/mrp/Presenter;", "initial", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "<set-?>", "Lcom/uber/autodispose/ScopeProvider;", "scope", "getScope", "()Lcom/uber/autodispose/ScopeProvider;", "state", "Lio/reactivex/subjects/BehaviorSubject;", "consume", "", "renderer", "(Lco/example/calories/core/mrp/Renderer;)V", "emit", "reducer", "Lkotlin/Function1;", "()Ljava/lang/Object;", "states", "Lio/reactivex/Observable;", "core-basemrp_debug"})
public abstract class AbstractPresenter<R extends co.example.calories.core.mrp.Renderer<? super S>, S extends java.lang.Object> implements co.example.calories.core.mrp.Presenter<R, S> {
    private final S initial = null;
    private final io.reactivex.subjects.BehaviorSubject<S> state = null;
    @org.jetbrains.annotations.NotNull()
    private com.uber.autodispose.ScopeProvider scope;
    
    public AbstractPresenter(S initial) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    protected final com.uber.autodispose.ScopeProvider getScope() {
        return null;
    }
    
    @androidx.annotation.CallSuper()
    @java.lang.Override()
    public void consume(@org.jetbrains.annotations.NotNull()
    R renderer) {
    }
    
    @java.lang.Override()
    public final void emit(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super S, ? extends S> reducer) {
    }
    
    @java.lang.Override()
    public final void emit(S state) {
    }
    
    @java.lang.Override()
    public final S state() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public final io.reactivex.Observable<S> states() {
        return null;
    }
}