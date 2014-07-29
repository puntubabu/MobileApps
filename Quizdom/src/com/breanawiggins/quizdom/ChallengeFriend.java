package com.breanawiggins.quizdom;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ChallengeFriend extends ListActivity {
    // if certain friend clicked then create challenge
    private ArrayList<HashMap<String, String>> friends;

    // List Adapter
    private ListAdapter adapter;
    // Tags
    private static final String TAG_FRIEND = "friend_name";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_FRIENDS = "friends";
    private static final String TAG = "ChallengeFriend";

    // session
    public static UserSessionManager session;

    // URL's
    private static final String GET_FRIENDS_URL = "http://pradeepkeshary.com/webservice/getfriends.php";
    private static final String ADD_CHALLENGE_URL = "http://pradeepkeshary.com/webservice/addChallenge.php";

    // JSON Objects
    private final JSONParser jsonParser = new JSONParser();
    private JSONArray jarrayFriends = new JSONArray();

    private String friendToChallenge;
    private int positionToChallenge = -1;
    public String st = "";

    private String friendsname;
    private ListView listFriends;
    // ListItem it;

    private String strUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.select_friendchallenge);
        session = new UserSessionManager(this.getApplicationContext());
        // this.positionToChallenge = -1;
        // this.setCurrentUserText();
        // this.friends = new ArrayList<HashMap<String, String>>();
        if (savedInstanceState == null) {

        }
        // Get Requests
        // new GetFriends().execute();
        // new GetChallenges().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // loading the friends via AsyncTask
        new GetFriends().execute();
    }

    /**
     * Inserts the parsed data into the listview.
     */
    private void updateList() {
        // For a ListActivity we need to set the List Adapter, and in order to
        // do
        // that, we need to create a ListAdapter. This SimpleAdapter,
        // will utilize our updated Hashmapped ArrayList,
        // use our single_post xml template for each item in our list,
        // and place the appropriate info from the list to the
        // correct GUI id. Order is important here.
        this.adapter = new SimpleAdapter(this, this.friends,
                R.layout.single_challenge, new String[] { TAG_FRIEND },
                new int[] { R.id.friendname });

        // I shouldn't have to comment on this one:
        this.setListAdapter(this.adapter);

        this.listFriends = this.getListView();
        this.listFriends.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                String item = ChallengeFriend.this.listFriends
                        .getItemAtPosition(position).toString();
                ChallengeFriend.this.friendToChallenge = ChallengeFriend.this
                        .parseFriendname(item);
                ChallengeFriend.this.positionToChallenge = position;
                ChallengeFriend.this.showChallengeFriendDialog();
            }
        });
    }

    class ChooseFriend extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            HashMap<String, String> user = new HashMap<String, String>();
            user = session.getUserDetails();
            String strUser = user.get("name");
            // String friend = st.getText().toString();

            try {
                // Building Parameters
                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", strUser));
                params.add(new BasicNameValuePair("friendname",
                        ChallengeFriend.this.friendToChallenge));

                Log.d("request!", "starting");

                // Posting user data to script
                JSONObject json = ChallengeFriend.this.jsonParser
                        .makeHttpRequest(ADD_CHALLENGE_URL, "POST", params);

                // full json response
                Log.d("Add Challenge attempt", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Friend Challenge Sent!", json.toString());
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Friend Doesn't Exist!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String file_url) {
            if (file_url != null) {
                Toast.makeText(ChallengeFriend.this, file_url,
                        Toast.LENGTH_LONG).show();
            }
        }

    }

    /**
     * Retrieves recent post data from the server.
     */
    public void updateJSONdata() {

        // Instantiate the arraylist to contain all the JSON data.
        // we are going to use a bunch of key-value pairs, referring
        // to the json element name, and the content, for example,
        // message it the tag, and "I'm awesome" as the content..

        this.friends = new ArrayList<HashMap<String, String>>();

        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        HashMap<String, String> user = new HashMap<String, String>();
        user = session.getUserDetails();
        this.strUser = user.get("name");
        params.add(new BasicNameValuePair("username", this.strUser));

        JSONParser jParser = new JSONParser();
        JSONObject json = jParser.makeHttpRequest(GET_FRIENDS_URL, "POST",
                params);

        // when parsing JSON stuff, we should probably
        // try to catch any exceptions:
        try {

            // I know I said we would check if "Posts were Avail." (success==1)
            // before we tried to read the individual posts, but I lied...
            // mComments will tell us how many "posts" or comments are
            // available
            this.jarrayFriends = json.getJSONArray(TAG_FRIENDS);

            // looping through all posts according to the json object returned
            for (int i = 0; i < this.jarrayFriends.length(); i++) {
                JSONObject c = this.jarrayFriends.getJSONObject(i);

                // gets the content of each tag
                String friendName = c.getString(TAG_FRIEND);

                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();

                map.put(TAG_FRIEND, friendName);

                // adding HashList to ArrayList
                this.friends.add(map);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class RequestChallenge extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... arg0) {
            new ChooseFriend().execute();

            return null;
        }

    }

    private void challengeFriend() {
        int success;
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", this.strUser));
        params.add(new BasicNameValuePair("friendname", this.friendToChallenge));
        try {
            JSONObject json = this.jsonParser.makeHttpRequest(
                    ADD_CHALLENGE_URL, "POST", params);

            // json success tag
            success = json.getInt(TAG_SUCCESS);

            // Remove friend from arraylist
            // this.friends.remove(this.positionToDelete);

            // Reset positionToDelete
            // this.positionToDelete = -1;
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String parseFriendname(String name) {
        name = name.substring(name.indexOf("=") + 1, name.length() - 1);
        return name;
    }

    public class GetFriends extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... arg0) {
            ChallengeFriend.this.updateJSONdata();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            ChallengeFriend.this.updateList();
        }
    }

    public class Challenge extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... arg0) {
            ChallengeFriend.this.challengeFriend();
            return null;
        }
    }

    public void onClick(View v) {
        /*
         * switch (v.getId()) { case R.id.btnAddFriend: // new
         * AddFriend().execute(); break;
         * 
         * case R.id.btnDeleteFriend: // showDeleteFriendDialog(); break; }
         */

        switch (v.getId()) {
            case R.id.btnChallengeFriend:
                this.showChallengeFriendDialog();

                break;
        }
    }

    private void showChallengeFriendDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder
                .setTitle("Are You Sure You Want to challenge this friend?");

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Challenge!",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                new Challenge().execute();
                                Intent j = new Intent(ChallengeFriend.this,
                                        Categories.class);
                                ChallengeFriend.this.startActivity(j);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                ChallengeFriend.this.friendToChallenge = null;
                                ChallengeFriend.this.positionToChallenge = -1;
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(R.menu.friends_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_friends_list,
                    container, false);
            return rootView;
        }
    }

}