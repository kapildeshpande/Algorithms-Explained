package com.kapil.kapil.algosexplained.Sorting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kapil.kapil.algosexplained.Home.HomePageAdapter;
import com.kapil.kapil.algosexplained.Home.LoginActivity;
import com.kapil.kapil.algosexplained.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.FALSE;

public class SortActivity extends AppCompatActivity {

    private static final String TAG = "SortActivity";
    public static String fragmentName;
    private SlidingUpPanelLayout slidingPaneLayout;
    private LinearLayout addComments;
    private RecyclerView recyclerView;
    private TextView textViewError;
    private ImageButton commentButton;
    private AppCompatEditText editText;
    ArrayList<JSONData> arrayList;
    CommentPageAdapter commentPageAdapter;
    private String url = "https://algorithms-explained-backend.herokuapp.com/";
    ArrayList <String> nameList = new ArrayList<>(Arrays.asList("More are coming soon..", "Selection Sort","Bubble Sort","Insertion Sort","Merge Sort","Heap Sort"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sorting_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        slidingPaneLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slidingPaneLayout.setDragView(R.id.sliding_area);
        addComments = findViewById(R.id.comment_layout);
        textViewError = findViewById(R.id.textview_error);
        commentButton = findViewById(R.id.comment_button);
        editText = findViewById(R.id.add_comment);

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = editText.getText().toString();
                if (value == null || value.isEmpty()) {
                    Toast.makeText(SortActivity.this, "Enter Comment",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                editText.setText("");

                String addCommentUrl = url + "comment";
                JSONObject postData = new JSONObject();
                try {
                    postData.put("pageNum", String.valueOf(nameList.indexOf(fragmentName)));
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                    String username = sharedPreferences.getString("username", "");
                    postData.put("username", username);
                    postData.put("comment", value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                volleyPost(addCommentUrl, postData, "Adding Comment",0,0);
            }
        });

        arrayList = new ArrayList<>();

        slidingPaneLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (slidingPaneLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    addComments.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                }
                else {
                    addComments.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        fragmentName = getIntent().getStringExtra("fragmentName");
        getSupportActionBar().setTitle(fragmentName);

        SectionPagerAdapter pagerAdapter = new
                SectionPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        initRecyclerView();
        volleyGet(url + "comment/","Loading Comments");
    }

    private void initRecyclerView () {
        recyclerView = (RecyclerView) findViewById(R.id.comments_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        commentPageAdapter = new CommentPageAdapter(this);
        recyclerView.setAdapter(commentPageAdapter);
    }

    String commentId;
    JSONData jsonData;

    public void onCalledReply(String reply,String commentId,int position) {
        String addCommentUrl = url + "comment/" + commentId;
        this.commentId = commentId;
        JSONObject postData = new JSONObject();
        jsonData = new JSONData();
        String username;
        try {
            postData.put("pageNum", String.valueOf(nameList.indexOf(fragmentName)));
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
            username = sharedPreferences.getString("username", "");
            postData.put("username", username);
            postData.put("comment", reply);
            jsonData.setType(1);
            jsonData.setComment(reply);
            jsonData.setUsername(username);
            jsonData.setId(commentId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        volleyPost(addCommentUrl, postData, "Replying",1,position);
    }



    public void volleyPost(String postUrl, JSONObject postData, final String operation,final int type,final int pos){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                if (type == 1) {
                    try {
                        arrayList.add(pos, jsonData);
                        commentPageAdapter.setItems(arrayList);
                        commentPageAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        JSONData jsonData = new JSONData();
                        jsonData.setType(0);
                        jsonData.setComment(response.getString("comment"));
                        jsonData.setUsername(response.getString("username"));
                        jsonData.setId(response.getString("_id"));
                        arrayList.add(pos, jsonData);
                        commentPageAdapter.setItems(arrayList);
                        commentPageAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(SortActivity.this, operation + " Failed",
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("token", token);

                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }


    private void volleyGet(String getUrl, final String operation) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String pageNum = String.valueOf(nameList.indexOf(fragmentName));
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, getUrl + pageNum,null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                    parseJSON(response, operation);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SortActivity.this, operation + " Failed",
                            Toast.LENGTH_LONG).show();
                }
                textViewError.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(SortActivity.this, operation + " Failed",
                        Toast.LENGTH_LONG).show();
                textViewError.setVisibility(View.VISIBLE);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("token", token);

                return params;
            }
        };
        requestQueue.add(jsonArrayRequest);
    }

    private void parseJSON(JSONObject response,String operation) {
        try {

            JSONArray arrResponse = response.getJSONArray("comments");
            for (int i=0;i<arrResponse.length();i++) {
                JSONObject jsonObject = arrResponse.getJSONObject(i);
                JSONData jsonData = new JSONData();
                jsonData.setType(0);
                jsonData.setComment(jsonObject.getString("comment"));
                jsonData.setUsername(jsonObject.getString("username"));
                String id = jsonObject.getString("_id");
                jsonData.setId(id);
                arrayList.add(jsonData);
                JSONArray arr = jsonObject.getJSONArray("replies");
                for (int j=0;j<arr.length();j++) {
                    JSONObject obj = arr.getJSONObject(j);
                    JSONData jsonDataReply = new JSONData();
                    jsonDataReply.setType(1);
                    jsonDataReply.setUsername(obj.getString("username"));
                    jsonDataReply.setComment(obj.getString("reply"));
                    jsonDataReply.setId(id);
                    arrayList.add(jsonDataReply);
                }
            }
            Log.d(TAG, "parseJSON: " + arrResponse);
            commentPageAdapter.setItems(arrayList);
            commentPageAdapter.notifyDataSetChanged();

            Toast.makeText(SortActivity.this, operation + " Successfully!!",
                    Toast.LENGTH_LONG).show();
            textViewError.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(SortActivity.this, operation + " Failed",
                    Toast.LENGTH_LONG).show();
        }
    }

    private class SectionPagerAdapter extends FragmentPagerAdapter {

        public SectionPagerAdapter (FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount () {
            return 2;
        }

        @Override
        public Fragment getItem (int position) {
            if (position == 0) {

                switch (fragmentName) {
                    case "Selection Sort":
                        return new SelectionSortFragment();

                    case "Bubble Sort":
                        return new BubbleSortFragment();

                    case "Insertion Sort":
                        return new InsertionSortFragment();

                    case "Merge Sort":
                        return new MergeSortFragment();
                    case "Heap Sort":
                        return new HeapSortFragment();
                }
            } else if (position == 1) {
                return new CodeFragment();
            }
            return  null;
        }

        //for name of each tab
        @Override
        public CharSequence getPageTitle (int position) {
            switch (position) {
                case 0:
                    return "Animation";
                case 1:
                    return "Code";
            }
            return null;
        }
    }


}