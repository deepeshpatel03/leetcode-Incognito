package com.example.leetincoginto

import LoadingScreen
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leetincoginto.Apisetup.search
import com.example.leetincoginto.friendsearch.FriendsScreen
import com.example.leetincoginto.friendsearch.HomeScreenProfilefriend
import com.example.leetincoginto.friendsearch.SearchScaffold
import com.example.leetincoginto.googleauth.AuthRepository
import com.example.leetincoginto.homescreen.AIScreen
import com.example.leetincoginto.homescreen.BottomNavItem
import com.example.leetincoginto.homescreen.HomeScreen
import com.example.leetincoginto.loginandsginupscreen.LoginScreen
import com.example.leetincoginto.loginandsginupscreen.SignUpScreen
import com.example.leetincoginto.loginandsginupscreen.WelcomeScreen
import com.example.leetincoginto.loginandsginupscreen.addhandle
import com.example.leetincoginto.profile.HomeScreenProfile
import com.example.leetincoginto.profile.LeetCodeContestScreen
import com.example.leetincoginto.profile.LeetCodeProfileScreen
import com.example.leetincoginto.ui.theme.LeetincogintoTheme
import com.example.leetincoginto.viewModel.LeetCodeViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            LeetincogintoTheme {

                val viewModel: LeetCodeViewModel = viewModel()

                // Hold execution until userProfile is not null

                    Appnav(
                        authRepository = authRepository,
                        loadpotd = {
                            viewModel.fetchuppotd()
                        },
                        loadsearch = { viewModel.getsearch(it) },
                        homeload = { viewModel.fectchthree() },
                        viewModel = viewModel
                    )


            }
        }
    }
}



@Composable
fun Appnav(authRepository: AuthRepository, loadpotd:()->Unit,loadsearch:(search)->Unit,homeload:()->Unit, viewModel: LeetCodeViewModel ){
    val problemOfTheDay by viewModel.problemOfTheDay.collectAsState()
    val upcomingContests by viewModel.upcomingContests.collectAsState()
    val apiResponse by viewModel.apiResponse.collectAsState()
    val leetcodehomeprofile by viewModel.leetCodeUserProfile.collectAsState()
    val contestData by viewModel.contestData.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val leetcodehomeprofilefriend by viewModel.leetCodeUserProfilefriend.collectAsState()
    val contestDatafriend  by viewModel.contestDatafriend.collectAsState()
    val userProfilefriend by viewModel.userProfilefriend.collectAsState()
    var start:String=""
    val user1 = FirebaseAuth.getInstance().currentUser
    val checkpotd by viewModel.checkpotd.collectAsState()
    val checkuserprofile by viewModel.checkuserprofile.collectAsState()
    val checkfriend by viewModel.checkfriend.collectAsState()
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val user  by viewModel.user.collectAsState()
    if(user1==null){
        start="welcome"
    }else{
        start="home"
    }

    val navController=rememberNavController()
    NavHost(navController, startDestination = start) {
        composable(route="welcome"){
            WelcomeScreen({navController.navigate("login")},{navController.navigate("signup")})
        }
        composable("login"){
            LoginScreen(navController,authRepository)
        }
        composable("signup") {
            SignUpScreen(authRepository,{navController.navigate("login")},{navController.popBackStack()})
        }
         composable("adduser"){
      addhandle(navController,viewModel)
      }

        composable(BottomNavItem.Home.route) {

            LaunchedEffect(userId) {

                viewModel.getUser(userId.toString())
                while(user==null){
                    delay(1000)
                }
            }
            Log.d("API_show","${user.toString()} ${userId.toString()}")
            loadpotd()
            if (upcomingContests != null&&problemOfTheDay!=null) {
                HomeScreen(navController, upcomingContests!!,problemOfTheDay!!)
            } else {
                LoadingScreen()
                if(checkpotd>0&&problemOfTheDay==null){
                    LoadingScreen("To many API fetch try after 1 hr")
                }
            }
        }
        composable(BottomNavItem.Friends.route) {
             FriendsScreen(navController,viewModel,{loadsearch(it)})
        }
        composable(BottomNavItem.Profile.route) {
            homeload()
            if(leetcodehomeprofile!=null){
                HomeScreenProfile(navController,leetcodehomeprofile!!)
            }else{
                LoadingScreen("Loading")
                if(checkuserprofile>0&&leetcodehomeprofile==null){
                    LoadingScreen("To many API fetch try after 1 hr")
                }
            }

        }
        composable(BottomNavItem.AI.route) {
           viewModel.aistart()
            AIScreen(viewModel)
        }

        composable("searchfriend"){
            SearchScaffold({loadsearch(it)},apiResponse = apiResponse,({viewModel.addFriend(viewModel.userId.toString(),it)}))
        }

        composable("userprofile"){

            if(userProfile!=null){
                LeetCodeProfileScreen(userProfile)
            }else {
                LoadingScreen("Loading")
                if(checkuserprofile>0){
                    LoadingScreen("To many API fetch try after 1 hr")
                }
            }
        }
        composable("usercontest"){
            if(contestData!=null){
                LeetCodeContestScreen(contestData)
            }else{
                LoadingScreen("Loading")
                if(checkuserprofile>0){
                    LoadingScreen("To many API fetch try after 1 hr")
                }
            }

        }
        composable("profilefriend"){
            if(leetcodehomeprofilefriend!=null){
                HomeScreenProfilefriend(navController,leetcodehomeprofilefriend!!)
            }else{
                LoadingScreen("Loading")
                if(checkfriend>0&&leetcodehomeprofilefriend==null){
                    LoadingScreen("To many API fetch try after 1 hr")
                }
            }
        }
        composable("userprofilefriend"){

            if(userProfilefriend!=null){
                LeetCodeProfileScreen(userProfilefriend)
            }else{
                LoadingScreen("Loading")
                if(checkfriend>0){
                    LoadingScreen("To many API fetch try after 1 hr")
                }
            }

        }
        composable("usercontestfriend"){
            if(contestDatafriend!=null){
                LeetCodeContestScreen(contestDatafriend)
            }else{
                LoadingScreen("Loading")
                if(checkfriend>0 ){
                    LoadingScreen("To many API fetch try after 1 hr")
                }
            }

        }
    }

}
