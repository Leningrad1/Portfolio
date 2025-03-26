package com.example.headhunterapps.presentation.homefragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.headhunterapp.data.repository.Repository;
import com.example.headhunterapp.model.Vacancy;
import com.example.headhunterapps.R;
import com.example.headhunterapps.ViewModel;
import com.example.headhunterapps.databinding.FragmentHomeBinding;
import com.example.headhunterapps.presentation.recyclerviewadapter.JobTitleRecyclerViewAdapter;
import com.example.headhunterapps.presentation.recyclerviewadapter.VacancyAdapter;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 %2\u00020\u0001:\u0001%B\u0005\u00a2\u0006\u0002\u0010\u0002J&\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\u001a\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u00142\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0017J\b\u0010\u001f\u001a\u00020\u001cH\u0002J\u0016\u0010 \u001a\u00020\u001c2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"H\u0003J\b\u0010$\u001a\u00020\u001cH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006&"}, d2 = {"Lcom/example/headhunterapps/presentation/homefragment/HomeFragment;", "Landroidx/fragment/app/Fragment;", "()V", "binding", "Lcom/example/headhunterapps/databinding/FragmentHomeBinding;", "repository", "Lcom/example/headhunterapp/data/repository/Repository;", "getRepository", "()Lcom/example/headhunterapp/data/repository/Repository;", "setRepository", "(Lcom/example/headhunterapp/data/repository/Repository;)V", "vacancyCount", "", "viewModel", "Lcom/example/headhunterapps/ViewModel;", "getViewModel", "()Lcom/example/headhunterapps/ViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onItemClick", "", "onViewCreated", "view", "recyclerVacancy", "showMoreButtonIfNeeded", "vacancies", "", "Lcom/example/headhunterapp/model/Vacancy;", "tabButton", "Companion", "app_debug"})
public final class HomeFragment extends androidx.fragment.app.Fragment {
    @javax.inject.Inject
    public com.example.headhunterapp.data.repository.Repository repository;
    private com.example.headhunterapps.databinding.FragmentHomeBinding binding;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.NotNull
    private java.lang.String vacancyCount = "";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String VACANCY_ALL = "vac_all";
    @org.jetbrains.annotations.NotNull
    public static final com.example.headhunterapps.presentation.homefragment.HomeFragment.Companion Companion = null;
    
    public HomeFragment() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.headhunterapp.data.repository.Repository getRepository() {
        return null;
    }
    
    public final void setRepository(@org.jetbrains.annotations.NotNull
    com.example.headhunterapp.data.repository.Repository p0) {
    }
    
    private final com.example.headhunterapps.ViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override
    @android.annotation.SuppressLint(value = {"NotifyDataSetChanged"})
    public void onViewCreated(@org.jetbrains.annotations.NotNull
    android.view.View view, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void recyclerVacancy() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void showMoreButtonIfNeeded(java.util.List<com.example.headhunterapp.model.Vacancy> vacancies) {
    }
    
    private final void onItemClick() {
    }
    
    private final void tabButton() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/headhunterapps/presentation/homefragment/HomeFragment$Companion;", "", "()V", "VACANCY_ALL", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}