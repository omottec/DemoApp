package com.omottec.demoapp.fresco;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omottec.demoapp.R;
import com.omottec.demoapp.memory.MemorySimulator;

import java.util.ArrayList;
import java.util.List;

import static com.omottec.demoapp.fresco.BatchLoadAdapter.*;

/**
 * Created by qinbingbing on 11/02/2018.
 */

public class BatchLoadFragment extends Fragment {
    private RecyclerView mRv;
    private BatchLoadAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_batch_load, container, false);
        mRv = view.findViewById(R.id.rv);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        List<ImageData> imageDataList = new ArrayList<>();
        ImageData imageData = null;
        int n = 60;
        String[] url = {"http://p1.meituan.net/mallimages/a921db9cc416a741bec264fa05069882162471.jpg",
                "http://p1.meituan.net/mallimages/9edd10326b332f8872e107c83282f69e243278.jpg",
                "http://p0.meituan.net/mallimages/00722d7e86e6d4d99b90afc59c940c65183040.jpg",
                "http://p0.meituan.net/mallimages/53b80ba7b4245d14dd97fbf8433b3d4449353.jpg",
                "http://p0.meituan.net/mallimages/9db9e7c054ca8391c63f92c15569d4e948097.jpg",
                "http://p1.meituan.net/mallimages/73dee704d61efe1f9a08a9c99a8bbe6859167.jpg",
                "http://p0.meituan.net/mallimages/64f959bfc0b68586b2a30edb8aa022fe113757.jpg",
                "http://p0.meituan.net/mallimages/7ebb38135b3bac6d2bd17fcc1a9e3139154561.jpg",
                "http://p0.meituan.net/mallimages/512fc42845f17c00234f24f8d813a9e3219856.jpg",
                "http://p1.meituan.net/mallimages/b7366c9358540541b3f8db9cae2b284948045.jpg",
                "http://p0.meituan.net/mallimages/d953aa8bc84ce4425a6e5b52603ec3c0153365.jpg",
                "http://p1.meituan.net/mallimages/3e8d100db90bf5984a8d8450536a5e8d265138.jpg",
                "http://p1.meituan.net/mallimages/5e6e9d6b32457f15872dfb51a2cd75c7218274.jpg",
                "http://p1.meituan.net/mallimages/c4a89e02a496afff4cdc67db2d3c6b0798680.jpg",
                "http://p1.meituan.net/mallimages/738f4791e74b4af387e70c5f0ab8b733152253.jpg",
                "http://p1.meituan.net/mallimages/e6226f40ac023a98abaad4be3c87d59a215266.jpg",
                "http://p0.meituan.net/mallimages/45e88e7f42624a5eba258ec6aa468091107200.jpg",
                "http://p0.meituan.net/mallimages/96386af87ae0152ff4c335887cd52eb4100310.jpg",
                "http://p0.meituan.net/mallimages/1bbf1ca34636e103308bb266a1bc7e4c272370.jpg",
                "http://p0.meituan.net/mallimages/67bd7391496b61d158ba6c33b82d622f132886.jpg",
                "http://p1.meituan.net/mallimages/fed6bde71eb2015ef6c0b007f5ba5a39184162.jpg",
                "http://p1.meituan.net/mallimages/23ba2b6f0a018662318943a63e43f486156093.jpg",
                "http://p0.meituan.net/mallimages/6cd34632d4e6e384d701db742e3dfb4c127733.jpg",
                "http://p0.meituan.net/mallimages/ddbf4b53683e3332cff9ca465d6f3da3138349.jpg",
                "http://p0.meituan.net/mallimages/5c62e1f4a9287bd3e7fc162d05de8770189423.jpg",
                "http://p0.meituan.net/mallimages/6dea1ab114197b81cecee0efaa3b2e11126691.jpg",
                "http://p0.meituan.net/mallimages/4b10e06a6203f8960736e39aac991afa118331.jpg",
                "http://p1.meituan.net/mallimages/17fbcc5b31ae43f79be2995d6601338578647.jpg",
                "http://p0.meituan.net/mallimages/328b481879e082337aaa45fd84e0fd8b184630.jpg",
                "http://p0.meituan.net/mallimages/d41d6b2005b2f78012b9ee703c2070b4292825.jpg",
                "http://p0.meituan.net/mallimages/a77f7034678c6616992426451e999530176605.jpg",
                "http://p0.meituan.net/mallimages/c39d2c302716d53eb3b423be97e1bc2b189270.jpg",
                "http://p0.meituan.net/mallimages/bd083c6b0f2489e6e72b08f1ade7b63e292109.jpg",
                "http://p0.meituan.net/mallimages/64f959bfc0b68586b2a30edb8aa022fe113757.jpg",
                "http://p0.meituan.net/mallimages/9db9e7c054ca8391c63f92c15569d4e948097.jpg",
                "http://p0.meituan.net/mallimages/9c69d13b69765c551bc15bb5e5151f8163279.jpg",
                "http://p0.meituan.net/mallimages/70de512a583404ec694be7d60a02f621163384.jpg",
                "http://p0.meituan.net/mallimages/f07fe6fb69ac9430f37ac306feb92645294520.jpg",
                "http://p0.meituan.net/mallimages/1329f81058a4e0bb93d6dd922060a6b697406.jpg",
                "http://p0.meituan.net/mallimages/fe78277ff37aec5c953ca9214574a36971822.jpg",
                "http://p0.meituan.net/mallimages/c1d7ef51f2aa6c3720838e8b204a96e1104816.jpg",
                "http://p0.meituan.net/mallimages/3275f1283b61e4d51a7aa142cd21492e207851.jpg",
                "http://p0.meituan.net/mallimages/d76561e75edd5ca55dea49bcb61f5df4165959.jpg",
                "http://p0.meituan.net/mallimages/f33c874c3b1456c67866843478033066118542.jpg",
                "http://p0.meituan.net/mallimages/61b357706a2b53c7e15dc5682770dabd100387.jpg",
                "http://p0.meituan.net/mallimages/76d2cbd74dd3e19361805099485e6e5b287165.jpg",
                "http://p0.meituan.net/mallimages/4ab2983ca4e59d11049946300fad60ff232132.jpg",
                "http://p0.meituan.net/mallimages/e6a1bca85daf6c30584cf1587d0ce5f3109467.jpg",
                "http://p0.meituan.net/mallimages/84a62ae902d290d509c38ed875389f24148204.jpg",
                "http://p0.meituan.net/mallimages/7b828a55ad9d5f5c36bd35117695ad10113434.jpg",
                "http://p0.meituan.net/mallimages/681a81f63f2999d411fd532d997becef189327.jpg",
                "http://p0.meituan.net/mallimages/dc91a23f617139304dcfa626335742b3114272.jpg",
                "http://p0.meituan.net/mallimages/bef935692eaa04f2850445ee84a6f0a7207856.jpg",
                "http://p0.meituan.net/mallimages/90ff67697597fa9612f56bd179257a8c119765.jpg",
                "http://p0.meituan.net/mallimages/f3cd665b947850ca61aa69ceda177369142358.jpg",
                "http://p0.meituan.net/mallimages/5c013b67cfd1d01ff81c0c0d52ffff94180898.jpg",
                "http://p0.meituan.net/mallimages/e5f1465bb08e6d4ea9c302646bdb97ec239740.jpg",
                "http://p0.meituan.net/mallimages/4a473bdc8c3e19b484c2547586a9098a118040.jpg",
                "http://p0.meituan.net/mallimages/05f22cdc407dafbfe3c21906084c4e93229075.jpg",
                "http://p0.meituan.net/mallimages/677d2d78f9aed71667cd5fae2707618f168587.jpg"};
        for (int i = 0; i < n; i++) {
            imageData = new ImageData();
            imageData.title = "item " + i;
            imageData.url = url[i];
            imageDataList.add(imageData);
        }
        mAdapter = new BatchLoadAdapter(imageDataList);
        mRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
//        MemorySimulator.drainMemoryDouble(3000);
//        MemorySimulator.asyncDrainMemoryDouble(3000);
//        MemorySimulator.asyncDrainMemorySlow(500);
    }
}
