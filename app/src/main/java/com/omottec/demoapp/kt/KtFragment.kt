package com.omottec.demoapp.kt

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.omottec.demoapp.R

class KtFragment : Fragment() {
    private lateinit var mTv: TextView
    var name: String = "qbb"
    val TAG = "KtFragment";

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mTv = view.findViewById(R.id.tv)!!;
        mTv.setText(name)
        Log.i(TAG, name)
        mTv.setOnClickListener {
            name = "omottec";
            mTv.setText(name)
            Log.i(TAG, name)
        }
    }
}