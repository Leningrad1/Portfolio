package com.example.headhunterapps.presentation.recyclerviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import com.example.headhunterapp.model.Vacancy;
import com.example.headhunterapps.R;
import com.example.headhunterapps.ViewModel;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.inject.Inject;
import kotlin.reflect.KFunction0;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001 B3\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u00a2\u0006\u0002\u0010\rJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000fH\u0002J\u0010\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0011H\u0002J\u0018\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u000fH\u0016J\u0018\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u000fH\u0016J\u0018\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0007H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/example/headhunterapps/presentation/recyclerviewadapter/VacancyAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/example/headhunterapps/presentation/recyclerviewadapter/VacancyAdapter$VacancyViewHolder;", "context", "Landroid/content/Context;", "vacancies", "", "Lcom/example/headhunterapp/model/Vacancy;", "onClickListener", "Lkotlin/reflect/KFunction0;", "", "viewModel", "Lcom/example/headhunterapps/ViewModel;", "(Landroid/content/Context;Ljava/util/List;Lkotlin/reflect/KFunction;Lcom/example/headhunterapps/ViewModel;)V", "getItemCount", "", "getLookingString", "", "count", "getPublishedDateString", "inputDate", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "toggleFavorite", "imageView", "Landroid/widget/ImageView;", "vacancy", "VacancyViewHolder", "app_debug"})
public final class VacancyAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.headhunterapps.presentation.recyclerviewadapter.VacancyAdapter.VacancyViewHolder> {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private java.util.List<com.example.headhunterapp.model.Vacancy> vacancies;
    @org.jetbrains.annotations.NotNull
    private final kotlin.reflect.KFunction<kotlin.Unit> onClickListener = null;
    @org.jetbrains.annotations.NotNull
    private final com.example.headhunterapps.ViewModel viewModel = null;
    
    @javax.inject.Inject
    public VacancyAdapter(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.util.List<com.example.headhunterapp.model.Vacancy> vacancies, @org.jetbrains.annotations.NotNull
    kotlin.reflect.KFunction<kotlin.Unit> onClickListener, @org.jetbrains.annotations.NotNull
    com.example.headhunterapps.ViewModel viewModel) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.example.headhunterapps.presentation.recyclerviewadapter.VacancyAdapter.VacancyViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.example.headhunterapps.presentation.recyclerviewadapter.VacancyAdapter.VacancyViewHolder holder, int position) {
    }
    
    private final java.lang.String getLookingString(int count) {
        return null;
    }
    
    private final java.lang.String getPublishedDateString(java.lang.String inputDate) {
        return null;
    }
    
    private final void toggleFavorite(android.widget.ImageView imageView, com.example.headhunterapp.model.Vacancy vacancy) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u0011\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\bR\u0011\u0010\u0013\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\bR\u0011\u0010\u0015\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\bR\u0011\u0010\u0017\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\bR\u0011\u0010\u001d\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\b\u00a8\u0006\u001f"}, d2 = {"Lcom/example/headhunterapps/presentation/recyclerviewadapter/VacancyAdapter$VacancyViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "cityJob", "Landroid/widget/TextView;", "getCityJob", "()Landroid/widget/TextView;", "experienceWork", "getExperienceWork", "imageWork", "Landroid/widget/ImageView;", "getImageWork", "()Landroid/widget/ImageView;", "likeFavourites", "getLikeFavourites", "moneyJob", "getMoneyJob", "nameJob", "getNameJob", "nameWork", "getNameWork", "nextButton", "Landroidx/appcompat/widget/AppCompatButton;", "getNextButton", "()Landroidx/appcompat/widget/AppCompatButton;", "peopleLookTitle", "getPeopleLookTitle", "publicWork", "getPublicWork", "app_debug"})
    public static final class VacancyViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView peopleLookTitle = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.ImageView likeFavourites = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView nameJob = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView cityJob = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView moneyJob = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView nameWork = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.ImageView imageWork = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView experienceWork = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView publicWork = null;
        @org.jetbrains.annotations.NotNull
        private final androidx.appcompat.widget.AppCompatButton nextButton = null;
        
        public VacancyViewHolder(@org.jetbrains.annotations.NotNull
        android.view.View itemView) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getPeopleLookTitle() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.ImageView getLikeFavourites() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getNameJob() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getCityJob() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getMoneyJob() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getNameWork() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.ImageView getImageWork() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getExperienceWork() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getPublicWork() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final androidx.appcompat.widget.AppCompatButton getNextButton() {
            return null;
        }
    }
}