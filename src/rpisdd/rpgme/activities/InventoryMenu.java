package rpisdd.rpgme.activities;

import java.io.File;
import java.util.ArrayList;

import com.squareup.picasso.Picasso;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.items.Item;
import rpisdd.rpgme.gamelogic.player.Player;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class InventoryMenu extends ListFragment implements OnClickListener {

	Button sell;
	Button use;
	View selectedItem;
	int selectedItemIndex;

	public InventoryMenu() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		
		selectedItem = null;
		selectedItemIndex = -1;
		
		View v = inflater.inflate(R.layout.inventory_menu, container, false);

		sell = (Button) v.findViewById(R.id.sellItem);
		sell.setOnClickListener(this);

		use = (Button) v.findViewById(R.id.useItem);
		use.setOnClickListener(this);

		
		updateButtons();
		
		return v;
	}
	
	//Will enable or disable buttons, depending on the situation.
	public void updateButtons(){
		Item i = Player.getPlayer().getInventory().getItems().get(selectedItemIndex);
		if (selectedItem == null){
			sell.setEnabled(false);
			use.setEnabled(false);
		} else {
			sell.setEnabled(true);
			if (i.isUsable()) {
				use.setEnabled(true);
			} else {
				use.setEnabled(false);
			}
		}
	}
	
	@Override
	public void onResume() {
		fillListView(getView());
		super.onResume();
	}

	// Take a list of items, and fill up the list view with those entries
	public void fillListView(View v) {
		
		Player p = Player.getPlayer();
		ItemAdapter adapter = new ItemAdapter(getActivity(),
				R.layout.shop_item,p.getInventory().getItems());
		
		setListAdapter(adapter);
		
		selectedItem = null;
		selectedItemIndex = -1;
		
		updateButtons();
	}
	
	private class ItemAdapter extends ArrayAdapter<Item> {

		ArrayList<Item> items;
		
        public ItemAdapter(Context context, int textViewResourceId, ArrayList<Item> items) {
                super(context, textViewResourceId, items);
                this.items = items;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if(v == null){
                    v = LayoutInflater.from(getActivity()).inflate(R.layout.inventory_item, null);
                }
                Item i = items.get(position);
                if (i != null) {
                    TextView name = (TextView) v.findViewById(R.id.inventoryItemName);
                    name.setText(i.getName());  
                    
                    ImageView image = (ImageView) v.findViewById(R.id.inventoryItemImage);
                    name.setText("Name: " + i.getName());     
                    Picasso.with(getActivity())
                    	.load(i.getImagePath())
                    	.into(image);
                }
                return v;
        }
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);

		selectedItemIndex = position;
		
		if (selectedItem != null)
			selectedItem.setBackgroundColor(Color.TRANSPARENT);

		v.setBackgroundColor(Color.GRAY);

		v.setSelected(true);
		selectedItem = v;
		
		updateButtons();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.useItem: {
			
			AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
           builder1.setMessage("Use this item?");
           builder1.setCancelable(true);
           builder1.setPositiveButton("Use",
                   new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                   dialog.cancel();
                   useItem();
               }
           });
           
           builder1.setNegativeButton("Don't Use",
                   new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                   dialog.cancel();
               }
           });

           AlertDialog alert11 = builder1.create();
           alert11.show();
           
			break;
		}
		case R.id.sellItem: {
			
		   AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
           builder1.setMessage("Sell this item?");
           builder1.setCancelable(true);
           builder1.setPositiveButton("Sell",
           new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                   dialog.cancel();
                   sellItemToShop();
               }
           });
           
           builder1.setNegativeButton("Don't Sell",
                   new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                   dialog.cancel();
               }
           });

           AlertDialog alert11 = builder1.create();
           alert11.show();
           
			break;
		}
		default:
			break;
		}
	}
	
	public void sellItemToShop(){
		Player p = Player.getPlayer();
		p.addGold(p.getInventory().getItems().get(selectedItemIndex).getRefundPrice());
		p.getInventory().removeAt(selectedItemIndex);
		fillListView(getView());
	}
	
	public void useItem(){
		Player p = Player.getPlayer();
		p.getInventory().getItems().get(selectedItemIndex).useMe(p, selectedItemIndex);
		
		fillListView(getView());
	}
	
}