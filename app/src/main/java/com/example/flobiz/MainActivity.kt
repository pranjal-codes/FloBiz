package com.example.flobiz

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flobiz.adapter.OnItemClickListener
import com.example.flobiz.adapter.QuestionListAdapter
import com.example.flobiz.databinding.ActivityMainBinding
import com.example.flobiz.models.Item
import com.example.flobiz.viewModels.FeedViewModel
import java.util.*


class MainActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var mBinding: ActivityMainBinding
    private var mFeedViewModel: FeedViewModel? = null
    private var mQuestionListAdapter: QuestionListAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Used viewBinding to bind this' activity layout
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        //Setting up the viewModel
        mFeedViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(FeedViewModel::class.java)

        setupRecyclerView()
        setRepositoryObserver()


    }


    private fun setupRecyclerView() {
        if (mQuestionListAdapter == null) { //Initially we set the layoutManager
            with(mBinding.questionRecyclerView) {
                layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            }
        } else { // if already set, we refresh the recycler view
            mQuestionListAdapter!!.refreshRecyclerView()
        }
    }

    private fun setRepositoryObserver() {
        mFeedViewModel!!.getFeedRepository()?.observe(this, {

            val vidList: List<Item> = it?.items ?: arrayListOf()

            mQuestionListAdapter = QuestionListAdapter(vidList, this)
            mBinding.questionRecyclerView.adapter = mQuestionListAdapter
            mQuestionListAdapter!!.refreshRecyclerView()
        })
    }

    /*
    * Opening the clicked question
    * in the web browser
    * */
    override fun onClick(item: Item) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
        startActivity(browserIntent)
    }
}