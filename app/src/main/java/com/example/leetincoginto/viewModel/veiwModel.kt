package com.example.leetincoginto.viewModel

import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leetincoginto.Apisetup.ApiResponse
import com.example.leetincoginto.Apisetup.ContestData
import com.example.leetincoginto.Apisetup.ContestResponse
import com.example.leetincoginto.Apisetup.LeetCodeProblem
import com.example.leetincoginto.Apisetup.LeetCodeProfile
import com.example.leetincoginto.Apisetup.LeetCodeRepository
import com.example.leetincoginto.Apisetup.LeetCodeUserProfile
import com.example.leetincoginto.Apisetup.Message
import com.example.leetincoginto.Apisetup.ProblemTag
import com.example.leetincoginto.Apisetup.UserStatsResponse
import com.example.leetincoginto.Apisetup.search
import com.example.leetincoginto.googleauth.User

import com.example.leetincoginto.googleauth.firestoreUserRepository
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeetCodeViewModel @Inject constructor(
    private val repository: LeetCodeRepository,
    private val repositorystore: firestoreUserRepository
) : ViewModel() {

    val userId= FirebaseAuth.getInstance().currentUser?.uid
    var state= MutableStateFlow(0)
    fun addUser(username: String, leetcodeId: String) {
        viewModelScope.launch {
            try{
            repositorystore.addUser(username, leetcodeId)
            state.value++;}catch (e:Exception){
                state.value--
            }
        }
    }

    private val _user = MutableStateFlow<String?>(null)
    val user: StateFlow<String?> get() = _user



    fun getUser(userUID: String) {
        viewModelScope.launch {
            try{
            _user.value = repositorystore.getUser(userUID)?.leetcodeId
//            Log.d("API_SHOW get user",user.value.toString())
            }catch (e:Exception){

            }finally {
                if(user!=null){
                    fetchUserStats(user.value.toString())
                }
            }
        }
    }

    fun updateLeetCodeId(userUID: String, newLeetcodeId: String) {
        viewModelScope.launch {
            repositorystore.updateLeetCodeId(userUID, newLeetcodeId)
        }
    }

    fun deleteUser(userUID: String) {
        viewModelScope.launch {
            repositorystore.deleteUser(userUID)
        }
    }

    fun addFriend(userUID: String, friendUsername: String,) {
          var a= friendUsername.substringBefore("@")
        viewModelScope.launch {

            repositorystore.addFriend(userUID, a)
        }
    }

    fun removeFriend(userUID: String, friendUsername: String) {
        viewModelScope.launch {
//            Log.d("API_SHOW remove friend",userUID)
            repositorystore.removeFriend(userUID, friendUsername)
            getFriends(userUID)
        }
    }

    private val _friendsList = MutableStateFlow<List<String>>(emptyList())
    val friendsList: StateFlow<List<String>> get() = _friendsList

    fun getFriends(userUID: String) {
        viewModelScope.launch {
//            Log.d("API_SHOW fetch friends",userUID)
            _friendsList.value = repositorystore.getFriends(userUID)
        }
    }

    ///////Leetcode api??????????//
    var cntpr=0;
    fun fectchthree(){
        var username=user.value

        if (username != null) {
            if(cntpr==0){
//                Log.d("API_SHOW fetchthree","$username")
                cntpr++;
                fetchUserProfile(username)
                fetchContestHistory(username)
                fetchLeetCodeUserProfile(username)
            }
        }
    }
    var getuserfriend=MutableStateFlow("")
    fun fectchthreefriend(username:String){
        Log.d("API_SHOW fetchthree","$username")
        if(getuserfriend.value!=username){
            fetchUserProfilefriend(username)
            fetchContestHistoryfriend(username)
            fetchLeetCodeUserProfilefriend(username)
        }
    }
    fun getsearch(user : search) {
        fetchAccountDetails(user.str,user.min,user.max,user.limit)
    }

    private val _userProfile = MutableStateFlow<LeetCodeProfile?>(null)
    val userProfile: StateFlow<LeetCodeProfile?> get() = _userProfile

    private val _contestData = MutableStateFlow<ContestData?>(null)
    val contestData: StateFlow<ContestData?> get() = _contestData

    private val _leetCodeUserProfile = MutableStateFlow<LeetCodeUserProfile?>(null)
    val leetCodeUserProfile: StateFlow<LeetCodeUserProfile?> get() = _leetCodeUserProfile

    private val _userStats = MutableStateFlow<UserStatsResponse?>(null)
    val userStats: StateFlow<UserStatsResponse?> get() = _userStats

    private val _userProfilefriend = MutableStateFlow<LeetCodeProfile?>(null)
    val userProfilefriend: StateFlow<LeetCodeProfile?> get() = _userProfilefriend

    private val _contestDatafriend = MutableStateFlow<ContestData?>(null)
    val contestDatafriend: StateFlow<ContestData?> get() = _contestDatafriend

    private val _leetCodeUserProfilefriend = MutableStateFlow<LeetCodeUserProfile?>(null)
    val leetCodeUserProfilefriend: StateFlow<LeetCodeUserProfile?> get() = _leetCodeUserProfilefriend
val checkfriend=MutableStateFlow(0)
    fun fetchUserProfilefriend(username: String) {
        viewModelScope.launch {
            try {
//                Log.d("API_SHOW fetch user profile", "Fetching data for: $username")
                val response = repository.fetchUserProfile(username)
                _userProfilefriend.value = response
                getuserfriend.value=username
                checkfriend.value
//                Log.d("API_SHOW user profile respose", "Fetching data for: $response")
            } catch (e: Exception) {

                _userProfilefriend.value = null
//                Log.d("API_SHOW fetchUserProfile error ", "Fetching data for: $e")
            }
        }
    }

    fun fetchContestHistoryfriend(username: String) {
        viewModelScope.launch {
            try {
//                Log.d("API_SHOW fetchContestHistory ", "Fetching data for: $username")
                val response = repository.fetchContestHistory(username)
                _contestDatafriend.value = response
//                Log.d("API_SHOW fetchContestHistory", "Fetching data for: $response")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchLeetCodeUserProfilefriend(username: String) {
        viewModelScope.launch {
            try {
//                Log.d("API_SHOW  fetchLeetCodeUserProfile", "Fetching data for: $username")
                val response = repository.fetchLeetCodeUserProfile(username)
                _leetCodeUserProfilefriend.value = response
//                Log.d("API_SHOW  fetchLeetCodeUserProfile", "Fetching data for: $response")
            } catch (e: Exception) {
//                Log.d("API_SHOW", "Error: $e")
            }
        }
    }

    val checkuserprofile=MutableStateFlow(0)
    fun fetchUserProfile(username: String) {
        viewModelScope.launch {
            try {
//                Log.d("API_SHOW fetch user profile", "Fetching data for: $username")
                val response = repository.fetchUserProfile(username)
                _userProfile.value = response
//                Log.d("API_SHOW user profile respose", "Fetching data for: $response")
                checkuserprofile.value++;
            } catch (e: Exception) {

                _userProfile.value = null
//                Log.d("API_SHOW fetchUserProfile error ", "Fetching data for: $e")
            }
        }
    }

    fun fetchContestHistory(username: String) {
        viewModelScope.launch {
            try {
//                Log.d("API_SHOW fetchContestHistory ", "Fetching data for: $username")
                val response = repository.fetchContestHistory(username)
                _contestData.value = response
//                Log.d("API_SHOW fetchContestHistory", "Fetching data for: $response")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchLeetCodeUserProfile(username: String) {
        viewModelScope.launch {
            try {
//                Log.d("API_SHOW  fetchLeetCodeUserProfile", "Fetching data for: $username")
                val response = repository.fetchLeetCodeUserProfile(username)
                _leetCodeUserProfile.value = response
//                Log.d("API_SHOW  fetchLeetCodeUserProfile", "Fetching data for: $response")
            } catch (e: Exception) {
//                Log.d("API_SHOW", "Error: $e")
            }
        }
    }
    val a = MutableStateFlow("")
    var usersate=0
    fun fetchUserStats(username: String) {
        if(usersate==0){
            usersate++;
        viewModelScope.launch {
            try {
//                Log.e("API_SHOW fetchUserStats", "Error:$username")
                val response = repository.fetchUserStats(username)
                _userStats.value = response
                a.value = response!!.toFormattedString()
//                Log.e("API_SHOW fetchUserStats", "Error:$response")
            } catch (e: Exception) {
//                Log.e("API_SHOW fetchUserStats", "Error: ${e.message}")
            }
        }
        }
    }
    val checkpotd=MutableStateFlow(0)
    private val _problemOfTheDay = MutableStateFlow<LeetCodeProblem?>(null)
    val problemOfTheDay: StateFlow<LeetCodeProblem?> get() = _problemOfTheDay
    var cnt=0;
    fun fetchProblemOfTheDay() {
        if(cnt==0){
            cnt++;
        viewModelScope.launch {
            try {

                val response = repository.fetchProblemOfTheDay()
                _problemOfTheDay.value = response
                checkpotd.value++;
//                Log.e("API_SHOW fetchProblemOfTheDay", "Error: ${response.toString()} ")
            } catch (e: Exception) {
                cnt--;
//                Log.e("API_SHOW fetchProblemOfTheDay", "Error: ${e.message}")
            }
        }}
    }

    fun fetchuppotd(){
        fetchUpcomingContests()
        fetchProblemOfTheDay()
    }



    private val _upcomingContests = MutableStateFlow<ContestResponse?>(null)
    val upcomingContests: StateFlow<ContestResponse?> get() = _upcomingContests
    var cnt1=0;
    fun fetchUpcomingContests() {
        viewModelScope.launch {
            if(cnt1==0){
                cnt1++;
            try {
                val response = repository.fetchUpcomingContests()
                _upcomingContests.value = response
            } catch (e: Exception) {
                cnt1--;
//                Log.e("API_SHOW fetchUpcomingContests", "Error: ${e.message}")
            }}
        }
    }
    private val _apiResponse = MutableStateFlow<ApiResponse?>(null)
    val apiResponse: StateFlow<ApiResponse?> get() = _apiResponse
    fun fetchAccountDetails(username: String, a: Int, b: Int, c: Int) {
        viewModelScope.launch {
            try {
//                Log.d("API_SHOW fetchAccountDetails", "Fetching data for: $username")
                val response = repository.getAccountDetail(username, a, b, c)
                _apiResponse.value = response
//                Log.d("API_SHOW fetchAccountDetails", "Fetching data for: ${response.toString()}")
            } catch (e: Exception) {
//                Log.e("API_ERROR", "Error: ${e.message}")
            }
        }
    }



    fun UserStatsResponse.toFormattedString(): String {
        val sb = StringBuilder()
        sb.append("🔹 User Leetcode Stats 🔹\n\n")

        fun appendSection(title: String, tags: List<ProblemTag>) {
            if (tags.isNotEmpty()) {
                sb.append("📌 $title:\n")
                tags.forEach { sb.append("   ➤ ${it.tagName}: ${it.problemsSolved} solved\n") }
                sb.append("\n")
            }
        }

        val tagCounts = data.matchedUser.tagProblemCounts
        appendSection("Advanced Topics", tagCounts.advanced)
        appendSection("Intermediate Topics", tagCounts.intermediate)
        appendSection("Fundamental Topics", tagCounts.fundamental)

        return sb.toString()
    }

    private val _story = MutableStateFlow ("")
    val story = _story.asStateFlow()
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    val generativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = "AIzaSyCeopdJ7M46ulAHxVUHyFLwpx-OmoAfhAA"
    )
    private val _isLoading = MutableStateFlow(false) //   Track AI response state
    val isLoading = _isLoading.asStateFlow()
    lateinit var chat  : Chat
     var start=0;
    fun aistart(){

        if(start==0){
            start++;
//        Log.d("API_ERROR","Model")
        chat = generativeModel.startChat()

        if(a.value!=null){
                _isLoading.value=true
        viewModelScope.launch {
            try {
                _story.value = chat.sendMessage((a.value)).text.toString()
                _messages.value+= Message("ai",_story.value)
                _messages.value+= Message("ai","Ask anything about progress")
            } catch (e: Exception) {
                _story.value = "Error: ${e.message}"
                _messages.value+= Message("ai",_story.value)
                Log.d("start","${e.message}")
            }finally{
                _isLoading.value=false

            }
        }}
        }
    }

    fun generateStory(prompt:String) { // ✅ Function to handle API call



        if (_isLoading.value) return // ✅ Prevent multiple requests
        _isLoading.value = true
        _messages.value+=Message("user",prompt)

        viewModelScope.launch {
            try {

                _story.value = chat.sendMessage((" Answer code in (text format) follow instruction strictly  " +
                        ""
                        +prompt+" Answer code in {algorithm} follow instruction strictly ")).text.toString()
                _messages.value+= Message("ai",_story.value)
            } catch (e: Exception) {
                _story.value = "Error: ${e.message}"
                _messages.value+= Message("ai",_story.value)
            }finally{
                _isLoading.value=false
            }

        }
    }
}
