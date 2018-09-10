package com.omottec.demoapp.reuse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.omottec.demoapp.R;
import com.omottec.demoapp.Tag;
import com.omottec.demoapp.fresco.Frescos;
import com.omottec.demoapp.utils.Logger;

/**
 * Created by qinbingbing on 09/09/2018.
 */

public class ReuseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.d(Tag.REUSE, "ReuseFragment onCreateView");
        View view = inflater.inflate(R.layout.f_reuse, container, false);
        LinearLayout ll = view.findViewById(R.id.ll);
        ll.setOrientation(LinearLayout.VERTICAL);
        /*ReuseImageView iv;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(360, 360);
        for (int i = 0; i < 100; i++) {
            iv = new ReuseImageView(container.getContext());
            iv.setImageResource(i % 2 == 0 ? R.drawable.sea_food : R.drawable.vegetable);
            lp.topMargin = 10;
            ll.addView(iv, lp);
        }*/

        SimpleDraweeView iv;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(360, 360);
        lp.topMargin = 10;
        String[] urls = new String[] {
                "http://p1.meituan.net/waimaidpoipicmining/04a2d08e2a4aa6f5e390e530c2d8897782752.jpg",
                "http://p1.meituan.net/mallimages/658a98f89fe9073594ec1dd0366cdea0130609.jpg",
                "http://p0.meituan.net/mallimages/13958385667b2fd524256190e4fb8321178229.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/80f1e892ae857e4caf346494e804ae3d102999.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/27102b1547e82ab8b1903c83256902af120719.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/4707597a1585576414b502dcb14e0ee4164530.jpg",
                "http://p0.meituan.net/mallimages/da6b1948499f75cf91794d75217465c0204814.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/61d947c5e327084bede4f6795d9185f295527.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/32f8abd30e9202a5f58c9487dc44d8e356221.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/c1fad060c113ed562dddc318d19e82b387329.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/4fd9b63bba6528184b095e1586bcfc1991051.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/e3d835de2aa568a2d7eefb7cefdd44be48971.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/04b0b4e9847ee59929e8008cd4927bd837943.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/cf2f39787a26b458e473485bcfd4532389467.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/9264aeec8bfacf9579dd602a337a474a83774.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/d4d4092b76944fe588feb7e9c562195d61094.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/5dc0646a1eeeed6f79f99bc28806500c75025.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/3272bc44297c782d902559edd837a9eb84783.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/3db008a5eaa88e61aab3d8557c0d00f0114760.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/52327b85c82a86ef70cacebcdd6f122889451.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/26ff2b987d1b4a9bec3b524ce67798d071311.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/ec3278d08325fce6edcbce21ab773ec566836.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/05e9c11a271cd121bb92a4e29d1432e2151362.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/9ce39c207b2ee0e421b4c8764303075991497.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/e30e2af351bbb350b3d80e4f9068de42133938.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/ac6903776a4bf799acb7939acdd107a459092.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/af8895ee6eafa27353f2781b14e4bb8665458.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/af8a1082eb500fde7594780ab8505d99103130.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/541e22305f13cc49fc0bcb2ffc3158b5106457.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/21fcb4b70c84dc87920e91f7a55a284c68905.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/10cb6e7fb57abb09aea5c85e6c50a61a73618.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/d43fe16b164ec0f88cf15e6cb80071f984204.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/6c2850dfbef9c31b8767c5c6f519993a76368.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/fadca13bf8354e3a6730a6f5aab7121762332.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/70edb1527b764a4fbc694da7f66c8b4494982.jpg",
                "http://p1.meituan.net/mallimages/bc60d8e62fc270e133927b231a4958fd120271.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/d7410fed7520e146b94b5618e4c6b23478308.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/3f22b1a0f283fdf36854854588282d0049034.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/da22567865db742c3b1000fd9b9b33fb64434.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/1c0ac22d0339f073c39e0da416c6a6a378144.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/f9842a21e31097e562ca602b9186980564621.jpg",
                "http://p0.meituan.net/mallimages/1a9e311be595c15a3158ee7031e418aa157003.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/d4d25880daf9eeaa0b4224073d80826c95120.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/188757cd3944d7cb6f40f57b73f10542105959.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/d102dbb7032fc37a76a92641ba18e17285222.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/db2b89fa630c2941ade1c1f862099b2798277.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/5a14adb2dc6f60bec64df1b58dd54f7484208.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/c0d3205d8324953a0de1dddad1791f84101476.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/7eda60b83e03b76418fde02a605dc58969461.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/456faf294c3b21fb6be3c60e0f7e862d52191.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/d0c0b1ebdb4d1df2b9f49c2b6b596dca64327.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/46f5951bc553f997e73dc6cd0f3f612863231.jpg",
                "http://p1.meituan.net/waimaidpoipicmining/5e6ed1e0eb7b3b99e729549bdec09b1066902.jpg",
                "http://p0.meituan.net/mallimages/2d8d7483f495b686836ecd4832156cda135905.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/1f9be2cf2859d5c29b1f55fa28d14dcc97867.jpg",
                "http://p0.meituan.net/waimaidpoipicmining/6a3b3e4bf90f524e560e6d31d95cea8b64975.jpg",
        };
        for (int i = 0; i < urls.length; i++) {
            iv = new SimpleDraweeView(container.getContext());
            Frescos.rawLoad(iv, urls[i], null);
            ll.addView(iv, lp);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.d(Tag.REUSE, "ReuseFragment onViewCreated");
    }
}
