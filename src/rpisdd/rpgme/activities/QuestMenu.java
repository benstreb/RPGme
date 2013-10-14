package rpisdd.rpgme.activities;

import java.util.ArrayList;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.player.Player;
import rpisdd.rpgme.gamelogic.player.StatType;
import rpisdd.rpgme.gamelogic.quests.Quest;
import rpisdd.rpgme.gamelogic.quests.QuestDifficulty;
import rpisdd.rpgme.gamelogic.quests.QuestManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

//The quest menu is the "hub" for all quest-related activities.
//Players can create, update, delete or view quests here.
//CreateQuest and UpdateQuest (for later iteration) are their own menus,
//but viewing, deleting and completing can all be done in this menu.
public class QuestMenu extends ListFragment implements OnClickListener {
	
	public QuestMenu(){}
	
	Button createQuest;
	Button deleteQuest;
	Button completeQuest;
	
	View selectedQuest;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	
    	selectedQuest = null;
    	
        View v = inflater.inflate(R.layout.quest_menu, container, false);
        
        createQuest = (Button) v.findViewById(R.id.createQuestButton);
        createQuest.setOnClickListener(this);
        deleteQuest = (Button) v.findViewById(R.id.deleteQuestButton);
        deleteQuest.setOnClickListener(this);
        completeQuest = (Button) v.findViewById(R.id.completeQuestButton);
        completeQuest.setOnClickListener(this);
        
        showOrHideButtons(false);
        
        return v;
    }
    
    @Override
    public void onResume() {
    	
    	fillListView(Player.getPlayer(this).questManager,getView());
    	
    	//Disable create button if the number of quests is greater than the max allowed.
    	if(Player.getPlayer(this).questManager.atMaxNumQuests()){
    		createQuest.setEnabled(false);
    	}
    	else{
    		createQuest.setEnabled(true);
    	}
    	
    	super.onResume() ;
    }
    
    //Take a list of quests, and fill up the list view with those entries
    public void fillListView(QuestManager quests, View v){
    	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
    	   android.R.layout.simple_list_item_1, quests.getQuestNames());
    	
    	setListAdapter(adapter);
    }
    
    //Set show to true to show the delete and complete buttons, false to hide them
    public void showOrHideButtons(boolean show){
    	deleteQuest.setEnabled(show);
    	completeQuest.setEnabled(show);
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	
    	super.onListItemClick(l, v, position, id);
    	
    	if(selectedQuest != null)
    		selectedQuest.setBackgroundColor(Color.TRANSPARENT);
    	
    	v.setBackgroundColor(Color.GRAY);
    	
    	v.setSelected(true);
    	selectedQuest = v;
    	
    	if(selectedQuest != null)
    		showOrHideButtons(true);
    	else
    		showOrHideButtons(false);
    }
    
    public String getSelectedQuestName(){
    	return ((TextView)selectedQuest).getText().toString();
    }
    
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
	        case R.id.createQuestButton: {
	        	Log.i("Debug:", "created");
	        	//Make the parent activity change the fragment
	        	((MainActivity)getActivity()).changeFragment(new CreateQuestMenu());
	        	break;
	        }
	        case R.id.deleteQuestButton: {
	        	Log.i("Debug:", "destroy");
	        	Player player = Player.getPlayer(this);
	        	Quest quest = player.questManager.getQuestFromName(getSelectedQuestName());
	        	player.questManager.removeQuest(quest);
	        	fillListView(player.questManager,getView());
	        	break;
	        }
	        case R.id.completeQuestButton: {
	        	Log.i("Debug:", "complete");
	        	Player player = Player.getPlayer(this);
	        	Quest quest = player.questManager.getQuestFromName(getSelectedQuestName());
	        	player.questManager.completeQuest(player,quest);
	        	fillListView(player.questManager,getView());
	        	break;
	        }
            default: break;
        }
	}
    
	
}
