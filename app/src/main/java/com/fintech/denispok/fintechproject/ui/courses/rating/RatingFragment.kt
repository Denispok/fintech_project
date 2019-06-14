package com.fintech.denispok.fintechproject.ui.courses.rating

import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fintech.denispok.fintechproject.App
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.ui.lectures.LecturesActivity
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class RatingFragment : Fragment() {

    @Inject
    lateinit var ratingViewModelFactory: RatingViewModelFactory
    private lateinit var ratingViewModel: RatingViewModel
    private var studentDisposable: Disposable? = null

    private lateinit var pointsView: TextView
    private lateinit var pointsTitleView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.applicationComponent.inject(this)
        ratingViewModel = ViewModelProvider(this, ratingViewModelFactory).get(RatingViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_rating, container, false)

        view.apply {
            pointsView = findViewById(R.id.points)
            pointsTitleView = findViewById(R.id.points_title)
        }

        view.findViewById<ConstraintLayout>(R.id.rating_details).setOnClickListener {
            startActivity(Intent(context, LecturesActivity::class.java))
        }

        studentDisposable = ratingViewModel.getCurrentStudent().subscribe {
            val mark = it.mark.toInt()
            pointsView.text = mark.toString()
            pointsTitleView.text = resources.getQuantityText(R.plurals.plurals_points, mark)
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        studentDisposable?.dispose()
        studentDisposable = null
    }
}
