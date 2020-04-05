package com.example.translate.ui.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.translate.DatabaseHelper;
import com.example.translate.Phrase;
import com.example.translate.R;
import com.example.translate.Translater;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import name.pilgr.pipinyin.PiPinyin;

public class MyListFragment extends Fragment {

    private TextInputLayout mTextInputWord;
    private PhraseAdapter mAdapter;
    private ArrayList<Phrase> customPhraseList = new ArrayList<Phrase>();

    private TextView mTxtPlaceholder;

    private CardView mCvWords;

    public MyListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_my_list, container, false);

        final RecyclerView mRecyclerView = view.findViewById(R.id.rvMyWords);
        //mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new PhraseAdapter(customPhraseList);
        mRecyclerView.setAdapter(mAdapter);

        mTextInputWord = view.findViewById(R.id.text_input_phrase);
        FloatingActionButton mFabAddWord = view.findViewById(R.id.fabAddWord);
        FloatingActionButton mFabDeleteAll = view.findViewById(R.id.fabDeleteAll);
        FloatingActionButton mFabLearn = view.findViewById(R.id.fabLearn);
        Button mBtnDebug = view.findViewById(R.id.btnDebug);
        Button mBtnAdapter = view.findViewById(R.id.btnAdapter);
        CircleImageView mBtnProfileImageMyList = view.findViewById(R.id.btnProfileImageMyList);
        mTxtPlaceholder = view.findViewById(R.id.txtPlaceholder);

        Picasso.get().load(R.mipmap.tzuyu).resize(240, 240).into(mBtnProfileImageMyList);

        DatabaseHelper myDb = new DatabaseHelper(getActivity());

        rebuildArrayList();
        if (customPhraseList.isEmpty()) {
            mTxtPlaceholder.setVisibility(view.VISIBLE);
        } else {
            mTxtPlaceholder.setVisibility(view.GONE);
        }

        mAdapter.setOnItemClickListener(new PhraseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onDeleteClick(int position) {
                DatabaseHelper myDb = new DatabaseHelper(getActivity());
                myDb.deletePhrase(customPhraseList.get(position).getPhraseEn());
                mAdapter.deleteItem(position);

                if (customPhraseList.isEmpty()) {
                    mTxtPlaceholder.setVisibility(view.VISIBLE);
                } else {
                    mTxtPlaceholder.setVisibility(view.GONE);
                }
            }
        });

        mBtnAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder string = new StringBuilder();
                for (int i = 0; i < mAdapter.getPhraseList().size(); i++) {
                    string.append(mAdapter.getPhraseList().get(i).getPhraseEn() + "\n\n");
                }
                showMessage("title", string.toString());
            }
        });

        mBtnDebug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper myDb = new DatabaseHelper(getActivity());

                Cursor res = myDb.getCategory("custom");
                if (res.getCount() == 0) {
                    showMessage("No Data", "You haven't added any of your own words yet");
                } else {
                    StringBuilder string = new StringBuilder();
                    while (res.moveToNext()) {
                        string.append(res.getString(1) + "\n\n");
                    }
                    showMessage("title", string.toString());
                }
                res.close();
            }
        });

        mFabAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                closeKeyboard(view);

                if (mTextInputWord.getEditText().getText().toString().trim().isEmpty()) {
                    mTextInputWord.setError("field cannot be empty");
                } else if (mTextInputWord.getEditText().getText().toString().length() > 15) {
                    mTextInputWord.setError("too many characters");
                } else {
                    boolean exists = false;
                    for (int i = 0; i < customPhraseList.size(); i++) {
                        if (customPhraseList.get(i).getPhraseEn().equals(mTextInputWord.getEditText().getText().toString())) {
                            mTextInputWord.setError("phrase already exists");
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        mTextInputWord.setError(null);
                        translate(mTextInputWord.getEditText().getText().toString().trim(), view);
                    }
                }
            }
        });

        mFabLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseHelper myDb = new DatabaseHelper(getActivity());

                Cursor res = myDb.getCategory("custom");
                if (res.getCount() == 0) {
                    showMessage("No Data", "You haven't added any of your own words yet");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("learningType", "custom");
                    Navigation.findNavController(getView()).navigate(R.id.action_navigation_my_list_fragment_to_navigation_learning, bundle);
                }
                res.close();
            }
        });

        mFabDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customPhraseList.isEmpty()) {
                    showMessage("No Data", "You haven't added any of your own words yet");
                } else {
                    DatabaseHelper myDb = new DatabaseHelper(getActivity());
                    myDb.clearMyList();
                    rebuildArrayList();
//
                    mAdapter.deleteAll();
                    mTxtPlaceholder.setVisibility(view.VISIBLE);

                }

            }
        });

        return view;
    }

    public void addWord(String phraseEn, String phraseCn, View view) {
        DatabaseHelper myDb = new DatabaseHelper(getContext());
        PiPinyin piPinyin = new PiPinyin(getActivity());
        String phrasePinyin = piPinyin.toPinyin(phraseCn, " ");

        myDb.insertData(phraseEn, phraseCn, phrasePinyin, "custom", false, false);
        mAdapter.addItem("hi", phraseEn, phraseCn, phrasePinyin, "custom", false, false);

        if (customPhraseList.isEmpty()) {
            mTxtPlaceholder.setVisibility(view.VISIBLE);
        } else {
            mTxtPlaceholder.setVisibility(view.GONE);

        }
        // TODO: SHOW A REFRESH ICON
    }

    public void translate(final String phrase, final View view) {
        Translater translater = new Translater();
        translater.checkModelExists(translater.configure());
        translater.configure().translate(phrase)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                addWord(phrase, translatedText, view);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("ERROR");// Error.
                            }
                        });
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void rebuildArrayList() {
        DatabaseHelper myDb = new DatabaseHelper(getActivity());
        Cursor res = myDb.getCategory("custom");

        while (res.moveToNext()) {
            customPhraseList.add(new Phrase(
                    res.getString(0),
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5),
                    res.getString(6)
            ));
        }

        while (res.moveToNext()) {
            mAdapter.addItem(
                    res.getString(0),
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    false,
                    false
            );
        }

        res.close();
    }

    public void closeKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        }
    }

}
