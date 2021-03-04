package com.omottec.demoapp.anr;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Printer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.omottec.demoapp.R;

import com.omottec.logger.Logger;
import java.text.DecimalFormat;

/**
 * Created by omottec on 19/01/2019.
 */

public class AnrFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "AnrFragment";
    private int mMsgId;
    private EditText mMsgTimeEt;
    private TextView mSendMsgTv;
    private TextView mSendMsgDelayTv;
    private TextView mRemoveMesTv;
    private long mMsgDispatchTime = -1;
    private long mMsgFinishTime = -1;
    private Handler mMainHandler = new Handler();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detectAnr();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_anr, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mMsgTimeEt = view.findViewById(R.id.et_msg_time);
        mSendMsgTv = view.findViewById(R.id.tv_send_msg);
        mSendMsgDelayTv = view.findViewById(R.id.tv_send_msg_delay);
        mRemoveMesTv = view.findViewById(R.id.tv_remove_msg);
        mSendMsgTv.setOnClickListener(this);
        mSendMsgDelayTv.setOnClickListener(this);
        mRemoveMesTv.setOnClickListener(this);
        /*view.findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "click", Toast.LENGTH_SHORT).show();
                while (true) {
                    v.invalidate();
                }
            }
        });*/
    }

    private void detectAnr() {
        Looper.getMainLooper().setMessageLogging(new Printer() {
            @Override
            public void println(String x) {
                Logger.i(TAG, x);
                if (x != null && x.startsWith(">>>>> Dispatching to"))
                    mMsgDispatchTime = SystemClock.elapsedRealtimeNanos();
                if (x != null && x.startsWith("<<<<< Finished to"))
                    mMsgFinishTime = SystemClock.elapsedRealtimeNanos();
                if (mMsgFinishTime >= mMsgDispatchTime && mMsgDispatchTime > 0) {
                    DecimalFormat df = new DecimalFormat("#,###");
                    Logger.i(TAG, "handle msg cost " + df.format(mMsgFinishTime - mMsgDispatchTime) + "Nanos");
                    mMsgDispatchTime = -1;
                    mMsgFinishTime = -1;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send_msg:
                sendMsg();
                break;
            case R.id.tv_send_msg_delay:
                sendMsgDelay();
                break;
            case R.id.tv_remove_msg:
                removeMsg();
                break;
            default:
                break;
        }
    }

    private void sendMsgDelay() {
        int msgId = mMsgId++;
        Logger.i(TAG, "sendMsgDelay msgId:" + msgId);
        String str = mMsgTimeEt.getText().toString();
        if (TextUtils.isEmpty(str) || !TextUtils.isDigitsOnly(str)) {
            Toast.makeText(getContext(), "enter msg time first", Toast.LENGTH_SHORT).show();
            return;
        }
        mMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Logger.i(TAG, "msgId:" + msgId + " Runnable run begin");
                Integer timeSecond = Integer.valueOf(str);
                SystemClock.sleep(timeSecond * 1000);
                Logger.i(TAG, "msgId:" + msgId + " Runnable run end after " + timeSecond);
            }
        }, 5 * 000);
    }

    private void removeMsg() {
        mMainHandler.removeCallbacksAndMessages(null);
        Logger.i(TAG, "removeCallbacksAndMessages");
    }

    private void sendMsg() {
        int msgId = mMsgId++;
        Logger.i(TAG, "sendMsg msgId:" + msgId);
        String str = mMsgTimeEt.getText().toString();
        if (TextUtils.isEmpty(str) || !TextUtils.isDigitsOnly(str)) {
            Toast.makeText(getContext(), "enter msg time first", Toast.LENGTH_SHORT).show();
            return;
        }
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                Logger.i(TAG, "msgId:" + msgId + " Runnable run begin");
                Integer timeSecond = Integer.valueOf(str);
                SystemClock.sleep(timeSecond * 1000);
                Logger.i(TAG, "msgId:" + msgId + " Runnable run end after " + timeSecond);
            }
        });
    }
}
