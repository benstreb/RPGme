package rpisdd.rpgme.activities;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.player.Player;
import rpisdd.rpgme.gamelogic.player.StatType;
import rpisdd.rpgme.gamelogic.quests.Quest;
import rpisdd.rpgme.gamelogic.quests.QuestDifficulty;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateQuestMenu extends Fragment implements OnClickListener {
	
	Button confirmCreate;
	Button cancel;

	public CreateQuestMenu(){}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.create_quest_menu, container, false);
        
        confirmCreate = (Button) v.findViewById(R.id.confirmCreateQuest);
        confirmCreate.setOnClickListener(this);
        cancel = (Button) v.findViewById(R.id.cancelCreateQuest);
        cancel.setOnClickListener(this);
        
        return v;
    }
    
    @Override
	public void onClick(View v) {
		switch (v.getId()) {
	        case R.id.confirmCreateQuest: {
	        	//Verified quest: Switch back to Quest Menu
	        	if(verifyAndCreate()){
		        	((MainActivity)getActivity()).changeFragment(new QuestMenu());
	        	}
	        	break;
	        }
	        case R.id.cancelCreateQuest: {
	        	((MainActivity)getActivity()).changeFragment(new QuestMenu());
	        	break;
	        }
            default: break;
        }
	}

    //Return true if the quest was verified, false otherwise.
	public boolean verifyAndCreate(){

		Player player = Player.getPlayer(this);
		
    	EditText name = (EditText) getView().findViewById(R.id.enterQuestName);
    	EditText desc = (EditText) getView().findViewById(R.id.enterQuestDesc);
    	Spinner tag = (Spinner) getView().findViewById(R.id.statDropDown);
    	Spinner diff = (Spinner) getView().findViewById(R.id.difficultyDropDown);
    	
    	if(name.getText().toString().equals("") ){
    		TextView error = (TextView) getView().findViewById(R.id.createQuestError);
    		error.setText("Error: Enter in a name");
    		error.setVisibility(View.VISIBLE);
    		return false;
		}
    	else if(player.questManager.isNameUsed(name.getText().toString())){
    		TextView error = (TextView) getView().findViewById(R.id.createQuestError);
    		error.setVisibility(View.VISIBLE);
    		error.setText("Error: That name is used by another quest");
    		return false;
    	}
    	
    	StatType type = StatType.stringToType(tag.getSelectedItem().toString());
    	QuestDifficulty quest_diff = QuestDifficulty.stringToDifficulty(diff.getSelectedItem().toString());
    	
    	Quest newQuest = new Quest(name.getText().toString(),desc.getText().toString(),
    			quest_diff,type);
    	
    	player.questManager.addQuest(newQuest);
    	
    	return true;
    	
	}
}
