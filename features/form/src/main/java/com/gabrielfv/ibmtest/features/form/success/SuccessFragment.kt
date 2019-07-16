package com.gabrielfv.ibmtest.features.form.success

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gabrielfv.ibmtest.features.form.R
import com.gabrielfv.ibmtest.libraries.core.ViewPagerStackController
import kotlinx.android.synthetic.main.fragment_success.*

class SuccessFragment(
    private val stackController: ViewPagerStackController
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_success, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newMessageLink.setOnClickListener {
            stackController.popFragment()
        }
    }
}