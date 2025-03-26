package com.example.leetincoginto.Apisetup

import kotlinx.serialization.Serializable

data class Message(
    val role:String,
    val content: String
)

data class search(
    val str:String,
    val min:Int,
    val max:Int,
    val limit:Int
)
//leetcodepoted data class
data class LeetCodeProblem(
    val questionLink: String?,
    val questionFrontendId: String?,
    val questionTitle: String?,
    val titleSlug: String?,
    val difficulty: String?,
    val topicTags: List<TopicTag>?,  // Nullable to handle missing values
    val likes: Int?,
    val dislikes: Int?
)

data class Solution(
    val id: String,
    val canSeeDetail: Boolean,
    val paidOnly: Boolean,
    val hasVideoSolution: Boolean,
    val paidOnlyVideo: Boolean
)

data class SimilarQuestion(
    val title: String,
    val titleSlug: String,
    val difficulty: String
)

data class TopicTag(
    val name: String,
    val slug: String
)





//upcoming contest clist data class
data class ContestResponse(
    val meta: Meta?,
    val objects: List<Contest>?
)
@Serializable
data class Contest(
    val duration: Int?,
    val end: String?,
    val event: String?,
    val host: String?,
    val href: String?,
    val id: Int?,
    val nProblems: Int?,
    val nStatistics: Int?,
    val parsedAt: String?,
    val problems: List<String>?,
    val resource: String?,
    val resourceId: Int?,
    val start: String?
)
//clist data class

@Serializable
data class ApiResponse(
    val meta: Meta?,
    val objects: List<LeetCodeUser>?
)

@Serializable
data class Meta(
    val estimated_count: Int?,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: String?,
    val total_count: Int?
)
@Serializable
data class LeetCodeUser(
    val handle: String,
    val id: Int,
    val last_activity: String,
    val n_contests: Int,
    val name: String,
    val rating: Int,
    val resource: String,
    val resource_id: Int,
    val resource_rank: Int
)



// 🟢 Submission Data Class
@Serializable
data class Submission(
    val title: String,
    val titleSlug: String,
    val timestamp: String,  // Changed from String to Long
    val statusDisplay: String,
    val lang: String
)

// 🟢 Submission Statistics
@Serializable
data class SubmissionStat(
    val difficulty: String,
    val count: Int,
    val submissions: Int
)

// 🟢 User Submission Stats
@Serializable
data class UserStats(
    val acSubmissionNum: List<SubmissionStat>,
    val totalSubmissionNum: List<SubmissionStat>
)

// 🟢 LeetCode Profile
@Serializable
data class LeetCodeProfile(
    val totalSolved: Int?,
    val totalSubmissions: List<SubmissionStat>?,
    val totalQuestions: Int?,
    val easySolved: Int?,
    val totalEasy: Int?,
    val mediumSolved: Int?,
    val totalMedium: Int?,
    val hardSolved: Int?,
    val totalHard: Int?,
    val ranking: Int?,
    val contributionPoint: Int?,
    val reputation: Int?,
    val submissionCalendar: Map<String, Int>?,
    val recentSubmissions: List<Submission>?,
    val matchedUserStats: UserStats?
)


//this represent the no of contest he participated
@Serializable
data class ContestData(
    val contestAttend: Int?,
    val contestRating: Double?,
    val contestGlobalRanking: Int?,
    val totalParticipants: Int?,
    val contestTopPercentage: Double?,

    val contestParticipation: List<ContestParticipation>?
)

@Serializable
data class ContestParticipation(
    val attended: Boolean,
    val rating: Double,
    val ranking: Int,
    val trendDirection: String,
    val problemsSolved: Int,
    val totalProblems: Int,
    val finishTimeInSeconds: Int,
    val contest: ContestDetails
)

@Serializable
data class ContestDetails(
    val title: String,
    val startTime: Long
)

//leetcode profile in which image contain
@Serializable
data class LeetCodeUserProfile(
    val username: String?,
    val name: String?,
    val birthday: String?,
    val avatar: String?,
    val ranking: Int?,
    val reputation: Int?,
    val gitHub: String?,
    val twitter: String?,
    val linkedIN: String?,
    val website: List<String>?,
    val country: String?,
    val company: String?,
    val school: String?,
    val skillTags: List<String>?,
    val about: String?
)



//user states for ai
data class UserStatsResponse(
     val data: ResponseData
)

data class ResponseData(
     val matchedUser: MatchedUser
)

data class MatchedUser(
     val tagProblemCounts: TagProblemCounts
)

data class TagProblemCounts(
     val advanced: List<ProblemTag>,
     val intermediate: List<ProblemTag>,
     val fundamental: List<ProblemTag>
)

data class ProblemTag(
   val tagName: String,
    val tagSlug: String,
    val problemsSolved: Int
)