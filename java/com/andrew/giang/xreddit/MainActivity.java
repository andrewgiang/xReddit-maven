package com.andrew.giang.xreddit;

/*
 * Copyright 2013 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.andrew.giang.xreddit.adapter.NavigationAdapter;
import com.andrew.giang.xreddit.fragments.CasualDialogFragment;
import com.andrew.giang.xreddit.fragments.LoginDialogFragment;
import com.andrew.giang.xreddit.fragments.SubredditListFragment;
import com.andrew.giang.xreddit.network.LoginRequest;
import com.andrew.giang.xreddit.network.RequestManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;


public class MainActivity extends ActionBarActivity implements LoginDialogFragment.LoginDialogListener {


    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationAdapter mNavigationAdapter;
    private CharSequence mTitle;

    @Override
    public void onFinishLoginDialog(String username, String password) {
        LoginRequest request = new LoginRequest(username, password, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject array) {
                Toast.makeText(MainActivity.this, array.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.w("COOKIE", volleyError.getMessage());
            }
        }
        );
        RequestManager.getInstance(MainActivity.this).makeRequest(request);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            NavigationItem item = (NavigationItem) mNavigationAdapter.getItem(position);
            final FragmentManager fragmentManager = getSupportFragmentManager();
            switch (item.mAction) {
                case FRONTPAGE:
                    fragmentManager.beginTransaction().replace(R.id.content_frame,
                            SubredditListFragment.getInstance(null, "")).commit();
                    break;
                case ALL:
                    break;
                case CASUAL:
                    CasualDialogFragment dialogFragment = new CasualDialogFragment();
                    dialogFragment.show(fragmentManager, "fragment_casual");
                    break;
                case MULTI_REDDIT:
                    break;
                case PROFILE:
                case LOGIN:
                    LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
                    loginDialogFragment.show(fragmentManager, "fragment_login");


                    break;
                case SUBREDDIT:
                    fragmentManager.beginTransaction().replace(R.id.content_frame,
                            SubredditListFragment.getInstance(item.title, "")).commit();
                    break;
            }
            mDrawerList.setItemChecked(position, true);
            setTitle(item.title);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setContentView(R.layout.activity_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /**
             * Called when a drawer has settled in a completely closed state.
             */
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /**
             * Called when a drawer has settled in a completely open state.
             */
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mNavigationAdapter = new NavigationAdapter(this.getApplicationContext());
        mDrawerList.setAdapter(mNavigationAdapter);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new SubredditListFragment()).commit();
    }


}