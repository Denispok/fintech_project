package com.fintech.denispok.fintechproject.ui.courses

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.ui.lectures.LecturesActivity

class RatingFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_rating, container, false)

        view.findViewById<ConstraintLayout>(R.id.rating_details).setOnClickListener {
            startActivity(Intent(context, LecturesActivity::class.java))
        }

        return view
    }
}