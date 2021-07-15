package com.example.kulakov_p3_wallpapers_app.di

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kulakov_p3_wallpapers_app.navigators.*
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/*@Module
class NavigatorModule(private val navController: NavController) {
    @Provides
    fun providesFavoriteFragmentNavigator() = FavoriteFragmentNavigator(navController)

    @Provides
    fun providesHistoryFragmentNavigator() = HistoryFragmentNavigator(navController)

    @Provides
    fun providesPhotoDetailFragmentNavigator() = PhotoDetailFragmentNavigator(navController)

    @Provides
    fun providesPhotoInfoFragmentNavigator() = PhotoInfoFragmentNavigator(navController)

    @Provides
    fun providesSearchFragmentNavigator() = SearchFragmentNavigator(navController)
}

@Singleton
@Component(modules = [NavigatorModule::class])
interface NavigatorComponent {
    fun inject(adapter: RecyclerView.Adapter<*>)
    fun inject(fragment: Fragment)

    val favoriteFragmentNavigator: FavoriteFragmentNavigator
    val historyFragmentNavigator: HistoryFragmentNavigator
    val photoDetailFragmentNavigator: PhotoDetailFragmentNavigator
    val photoInfoFragmentNavigator: PhotoInfoFragmentNavigator
    val searchFragmentNavigator: SearchFragmentNavigator
}*/