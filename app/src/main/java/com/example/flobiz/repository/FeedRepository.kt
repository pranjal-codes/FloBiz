package com.example.flobiz.repository

import androidx.lifecycle.MutableLiveData
import com.example.flobiz.models.QuestionFeed
import com.example.flobiz.repository.networking.FeedApi
import com.example.flobiz.repository.networking.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FeedRepository {
    private var mFeedApi: FeedApi? = null

    init {
        mFeedApi = RetrofitService.createService(FeedApi::class.java)
    }

    companion object {
        private var feedRepository: FeedRepository? = null
        fun getInstance(): FeedRepository? {
            if (feedRepository == null) {
                feedRepository = FeedRepository()
            }
            return feedRepository
        }
    }

    fun getQuestionFeed(): MutableLiveData<QuestionFeed?> {

        val feedData: MutableLiveData<QuestionFeed?> = MutableLiveData<QuestionFeed?>()
        mFeedApi?.getQuestionFeed()?.enqueue(object : Callback<QuestionFeed?> {
            override fun onResponse(
                call: Call<QuestionFeed?>?,
                response: Response<QuestionFeed?>
            ) {
                if (response.isSuccessful) {
                    feedData.value = response.body()
                } else {
                    feedData.value = null
                }
            }

            override fun onFailure(call: Call<QuestionFeed?>?, t: Throwable?) {
                feedData.value = null
            }
        })
        return feedData
    }
}