package com.example.headhunterapps;

import android.util.Log;
import com.example.headhunterapp.data.db.UserDao;
import com.example.headhunterapp.data.repository.Repository;
import com.example.headhunterapp.model.Vacancy;
import com.example.headhunterapp.model.dbdata.LikeVacancy;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 J\u0016\u0010!\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020$J\u0016\u0010%\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020$R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R \u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00130\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0015\"\u0004\b\u001a\u0010\u0017\u00a8\u0006&"}, d2 = {"Lcom/example/headhunterapps/ViewModel;", "Landroidx/lifecycle/ViewModel;", "myDao", "Lcom/example/headhunterapp/data/db/UserDao;", "repository", "Lcom/example/headhunterapp/data/repository/Repository;", "(Lcom/example/headhunterapp/data/db/UserDao;Lcom/example/headhunterapp/data/repository/Repository;)V", "_favoriteVacancies", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/example/headhunterapp/model/dbdata/LikeVacancy;", "allLikeVacancy", "Lkotlinx/coroutines/flow/StateFlow;", "getAllLikeVacancy", "()Lkotlinx/coroutines/flow/StateFlow;", "favoriteVacancies", "getFavoriteVacancies", "vacanciesList", "", "Lcom/example/headhunterapp/model/Vacancy;", "getVacanciesList", "()Ljava/util/List;", "setVacanciesList", "(Ljava/util/List;)V", "vacancyFilter", "getVacancyFilter", "setVacancyFilter", "addFavouriteVacancy", "", "getVacancyPluralForm", "", "count", "", "onAddLikeVacancy", "idVacancy", "likeVacancy", "", "onRemoveLikeVacancy", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class ViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.example.headhunterapp.data.db.UserDao myDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.example.headhunterapp.data.repository.Repository repository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.headhunterapp.model.dbdata.LikeVacancy>> _favoriteVacancies = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.headhunterapp.model.dbdata.LikeVacancy>> favoriteVacancies = null;
    @org.jetbrains.annotations.NotNull
    private java.util.List<com.example.headhunterapp.model.Vacancy> vacanciesList;
    @org.jetbrains.annotations.NotNull
    private java.util.List<com.example.headhunterapp.model.Vacancy> vacancyFilter;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.headhunterapp.model.dbdata.LikeVacancy>> allLikeVacancy = null;
    
    @javax.inject.Inject
    public ViewModel(@org.jetbrains.annotations.NotNull
    com.example.headhunterapp.data.db.UserDao myDao, @org.jetbrains.annotations.NotNull
    com.example.headhunterapp.data.repository.Repository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.headhunterapp.model.dbdata.LikeVacancy>> getFavoriteVacancies() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.example.headhunterapp.model.Vacancy> getVacanciesList() {
        return null;
    }
    
    public final void setVacanciesList(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.headhunterapp.model.Vacancy> p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.example.headhunterapp.model.Vacancy> getVacancyFilter() {
        return null;
    }
    
    public final void setVacancyFilter(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.headhunterapp.model.Vacancy> p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.headhunterapp.model.dbdata.LikeVacancy>> getAllLikeVacancy() {
        return null;
    }
    
    public final void onAddLikeVacancy(@org.jetbrains.annotations.NotNull
    java.lang.String idVacancy, boolean likeVacancy) {
    }
    
    public final void onRemoveLikeVacancy(@org.jetbrains.annotations.NotNull
    java.lang.String idVacancy, boolean likeVacancy) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getVacancyPluralForm(int count) {
        return null;
    }
    
    public final void addFavouriteVacancy() {
    }
}