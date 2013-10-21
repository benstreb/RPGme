package rpisdd.rpgme.activities;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.player.Player;
import rpisdd.rpgme.gamelogic.player.StatType;
import rpisdd.rpgme.gamelogic.quests.Quest;
import rpisdd.rpgme.gamelogic.quests.QuestDifficulty;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import java.util.Locale;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

//This is the main activity of the game, that will host the various menus and such.
public class MainActivity extends FragmentActivity 
{
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mMenuTitles = {"Quests", "Inventory", "Stats", "Shop", "Dungeon"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_layout);
        
        setupPlayer();
        
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mMenuTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  //host Activity 
                mDrawerLayout,         //DrawerLayout object 
                R.drawable.ic_drawer,  //nav drawer image to replace 'Up' caret 
                R.string.drawer_open,  //"open drawer" description for accessibility 
                R.string.drawer_close  //"close drawer" description for accessibility
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        //Select the first menu, Quests
        if (savedInstanceState == null) {
            selectItem(0);
        }
        
    }

    public void setupPlayer(){
    	
    	//If we didn't set up the player for the first time: Load player data from database here
    	//Note: Current code is only a placeholder, creates a new test player for now.
    	if(Player.getPlayer() == null){
    		Player.createPlayer("Anon", "Player", R.id.avatar2);
    	}
    	
    	Player.getPlayer().questManager.loadQuestsFromDatabase(this);
    	Player.getPlayer().inventory.loadItemsFromDatabase(this);
    	
    	/*
    	player.getQuestManager().addQuest(new Quest("Do HW","Description",QuestDifficulty.EASY,StatType.INTELLIGENCE));
    	player.getQuestManager().addQuest(new Quest("Eat","Description",QuestDifficulty.EASY,StatType.INTELLIGENCE));
    	player.getQuestManager().addQuest(new Quest("Sleep","Description",QuestDifficulty.EASY,StatType.INTELLIGENCE));
    	*/
    	
    	//Log.i("Debug:", "player 2 reached");
    }
    
    //Save all data here
    @Override
    public void onPause(){
    	super.onPause();
    	Player.getPlayer().questManager.saveQuestsToDatabase(this);
    	Player.getPlayer().inventory.saveItemsToDatabase(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Called whenever we call invalidateOptionsMenu()
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }

    // The click listner for ListView in the navigation drawer 
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    //Change the current fragment, or menu.
    public void changeFragment(Fragment fragment){
    	FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }
    
    //Called when an item was selected in the navigation drawer.
    private void selectItem(int position) {
        
    	Fragment fragment;
    	
    	if(mMenuTitles[position].equals("Quests"))
    	{
    		fragment = new QuestMenu();
    	}
    	else if(mMenuTitles[position].equals("Inventory"))
    	{
    		fragment = new InventoryMenu();
    	}
    	else if(mMenuTitles[position].equals("Stats"))
    	{
    		fragment = new StatsMenu();
    	}
    	else if(mMenuTitles[position].equals("Shop"))
    	{
    		fragment = new ShopMenu();
    	}
    	else if(mMenuTitles[position].equals("Dungeon"))
    	{
    		fragment = new DungeonMenu();
    	}
    	else
    	{
    		// update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            setTitle(mMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
    		return;
    	}
    	
    	changeFragment(fragment);

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mMenuTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
        
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    
     //
     // When using the ActionBarDrawerToggle, you must call it during
     // onPostCreate() and onConfigurationChanged()...
     //
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    
}

