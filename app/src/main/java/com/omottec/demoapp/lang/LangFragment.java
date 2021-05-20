package com.omottec.demoapp.lang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.omottec.demoapp.R;
import com.omottec.demoapp.utils.ReflectUtils;
import com.omottec.logger.Logger;
import java.lang.reflect.Field;

public class LangFragment extends Fragment {
    public static final String TAG = "LangFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_screen_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.setOnClickListener(v -> {
            Computer computer = new Computer("qbb", "mac");
            //MyComputer computer = new MyComputer("qbb", "qbb");
            testReflect(computer);
        });
    }

    private void testReflect(Computer computer) {
        Logger.i(TAG, computer.toString());
        try {
            Field name = ReflectUtils.getField(computer.getClass(), "name");
            name.set(computer, "omottec");
            Logger.i(TAG, computer.toString());
        } catch (IllegalAccessException e) {
            Logger.e(TAG, e);
        } catch (NoSuchFieldException e) {
            Logger.e(TAG, e);
        }

        try {
            Field model = ReflectUtils.getField(computer.getClass(), "model");
            //model.setAccessible(true);
            model.set(computer, "windows");
            Logger.i(TAG, computer.toString());
        } catch (IllegalAccessException e) {
            Logger.e(TAG, e);
        } catch (NoSuchFieldException e) {
            Logger.e(TAG, e);
        }
    }

    public static class MyComputer extends Computer {

        public MyComputer(String name, String model) {
            super(name, model);
        }
    }
}
