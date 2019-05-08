package app.flipshop.android.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import app.flipshop.android.ui.auth.LoginViewModel;
import app.flipshop.android.viewmodel.FlipshopViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindUserViewModel(LoginViewModel userViewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(SearchViewModel.class)
//    abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(RepoViewModel.class)
//    abstract ViewModel bindRepoViewModel(RepoViewModel repoViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FlipshopViewModelFactory factory);
}
