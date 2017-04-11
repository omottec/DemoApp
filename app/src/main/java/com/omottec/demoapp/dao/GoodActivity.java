package com.omottec.demoapp.dao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.omottec.demoapp.R;
import com.omottec.demoapp.activity.BaseActivity;
import com.omottec.demoapp.db.DaoSessionHolder;
import com.omottec.demoapp.view.recycler.OnItemClickListener;

import org.greenrobot.greendao.query.Query;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by qinbingbing on 07/04/2017.
 */

public class GoodActivity extends BaseActivity {
    private View mContentView;
    private EditText editText;
    private View addNoteButton;

    private GoodDao goodDao;
    private Query<Good> goodQuery;
//    private GoodsAdapter goodsAdapter;
    private GoodsAdapter goodsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpViews();

        // get the note DAO
        DaoSession daoSession = DaoSessionHolder.getInstance().getGoodDaoSession();
        goodDao = daoSession.getGoodDao();

        // query all notes, sorted a-z by their text
        goodQuery = goodDao.queryBuilder().orderAsc(GoodDao.Properties.Name).build();
        updateUsers();
    }

    @Override
    protected View createContentView() {
        mContentView = View.inflate(this, R.layout.a_good, null);
        return mContentView;
    }

    private void updateUsers() {
        List<Good> goods = goodQuery.list();
        goodsAdapter.setGoods(goods);
    }

    protected void setUpViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        //noinspection ConstantConditions
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        goodsAdapter = new GoodsAdapter(goodClickListener);
        goodsAdapter = new GoodsAdapter();
        goodsAdapter.setOnItemClickListener(goodClickListener);
        recyclerView.setAdapter(goodsAdapter);

        addNoteButton = findViewById(R.id.buttonAdd);
        //noinspection ConstantConditions
        addNoteButton.setEnabled(false);

        editText = (EditText) findViewById(R.id.editTextGood);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addNote();
                    return true;
                }
                return false;
            }
        });
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enable = s.length() != 0;
                addNoteButton.setEnabled(enable);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void onAddButtonClick(View view) {
        addNote();
    }

    private void addNote() {
        String nameText = editText.getText().toString();
        editText.setText("");

        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String desc = "Added on " + df.format(new Date());

        Good good = new Good();
        good.setName(nameText);
        good.setDesc(desc);
        goodDao.insert(good);
        Log.d("DaoExample", "Inserted new good, ID: " + good.getId());

        updateUsers();
    }

    /*GoodsAdapter.GoodClickListener goodClickListener = new GoodsAdapter.GoodClickListener() {
        @Override
        public void onUserClick(int position) {
            Good good = goodsAdapter.getGood(position);
            Long goodId = good.getId();

            goodDao.deleteByKey(goodId);
            Log.d("DaoExample", "Deleted good, ID: " + goodId);

            updateUsers();
        }
    };*/
    OnItemClickListener goodClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(ViewParent parent, View view, int adapterPosition, int layoutPosition) {
            Good good = goodsAdapter.getGood(adapterPosition);
            Long goodId = good.getId();

            goodDao.deleteByKey(goodId);
            Log.d("DaoExample", "Deleted good, ID: " + goodId);

            updateUsers();
        }
    };
}
