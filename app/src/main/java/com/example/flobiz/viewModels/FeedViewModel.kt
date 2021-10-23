package com.example.flobiz.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flobiz.models.QuestionFeed
import com.example.flobiz.repository.FeedRepository


class FeedViewModel(application: Application) : AndroidViewModel(application) {
    private var mFeedData: MutableLiveData<QuestionFeed?>? = null
    private var mFeedRepository: FeedRepository? = null


    init {
        /*
        * Requesting the data
        * if feed is empty
        * */
        if (mFeedData == null) {
            mFeedRepository = FeedRepository.getInstance()
            mFeedData = mFeedRepository?.getQuestionFeed()
        }
    }

    fun getFeedRepository(): LiveData<QuestionFeed?>? {
        return mFeedData
    }

}