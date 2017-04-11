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

public class UserActivity extends BaseActivity {
    private View mContentView;
    private EditText editText;
    private View addNoteButton;

    private UserDao userDao;
    private Query<User> notesQuery;
    private UsersAdapter usersAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpViews();

        // get the note DAO
        DaoSession daoSession = DaoSessionHolder.getInstance().getMyDaoSession();
        userDao = daoSession.getUserDao();

        // query all notes, sorted a-z by their text
        notesQuery = userDao.queryBuilder().orderAsc(UserDao.Properties.Name).build();
        updateUsers();
    }

    @Override
    protected View createContentView() {
        mContentView = View.inflate(this, R.layout.a_user, null);
        return mContentView;
    }

    private void updateUsers() {
        List<User> users = notesQuery.list();
        usersAdapter.setUsers(users);
    }

    protected void setUpViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewUsers);
        //noinspection ConstantConditions
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        usersAdapter = new UsersAdapter();
        usersAdapter.setOnItemClickListener(userClickListener);
        recyclerView.setAdapter(usersAdapter);

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
        String comment = "Added on " + df.format(new Date());

        User user = new User();
        user.setName(nameText);
//        user.setComment(desc);
        userDao.insert(user);
        Log.d("DaoExample", "Inserted new user, ID: " + user.getId());

        updateUsers();
    }

    OnItemClickListener userClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(ViewParent parent, View view, int adapterPosition, int layoutPosition) {
            User user = usersAdapter.getUser(adapterPosition);
            Long noteId = user.getId();

            userDao.deleteByKey(noteId);
            Log.d("DaoExample", "Deleted user, ID: " + noteId);

            updateUsers();
        }
    };
}
