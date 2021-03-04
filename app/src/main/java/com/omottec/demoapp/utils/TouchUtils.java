package com.omottec.demoapp.utils;

import androidx.recyclerview.widget.RecyclerView;
import android.view.MotionEvent;

/**
 * Created by qinbingbing on 8/26/16.
 */
public final class TouchUtils {
    public static String getTouchEventAction(MotionEvent event) {
        StringBuilder sb = new StringBuilder();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                sb.append("{ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                sb.append("{ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                sb.append("{ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                sb.append("{ACTION_CANCEL");
                break;
            default:
                sb.append("{ACTION_").append(event.getAction());
        }
        sb.append(" hashCode:").append(event.hashCode())
                .append(" eventTime:").append(event.getEventTime()).append('}');
        return sb.toString();
    }


    public static String getRecyclerScrollState(int state) {
        switch (state) {
            case RecyclerView.SCROLL_STATE_SETTLING:
                return "SCROLL_STATE_SETTLING";
            case RecyclerView.SCROLL_STATE_DRAGGING:
                return "SCROLL_STATE_DRAGGING";
            case RecyclerView.SCROLL_STATE_IDLE:
                return "SCROLL_STATE_IDLE";
            default:
                return "SCROLL_STATE_UNKNOWN";
        }
    }

}
