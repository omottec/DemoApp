package com.omottec.demoapp.kt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.omottec.demoapp.R

class KtFragment : Fragment() {
    private lateinit var mTv: TextView;

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.full_screen_text, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view ?: return;
        mTv = view.findViewById(R.id.tv)!!;
        mTv.setText("KtFragment")
    }
}