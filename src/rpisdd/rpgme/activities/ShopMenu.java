package rpisdd.rpgme.activities;

import java.util.ArrayList;
import java.util.List;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.items.Item;
import rpisdd.rpgme.gamelogic.player.Player;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
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

import com.squareup.picasso.Picasso;

public class ShopMenu extends ListFragment implements OnClickListener {

	private List<Item> itemsInStock;

	Button buy;
	View selectedItem;
	int selectedItemIndex;

	TextView goldWidget;

	// Load in the items that the shop stocks from external XML
	public void setItems() {

		itemsInStock = new ArrayList<Item>();
		itemsInStock.add(Item.createItemFromName("Energy Potion"));
		itemsInStock.add(Item.createItemFromName("Small Sword"));
		itemsInStock.add(Item.createItemFromName("Steel Plate"));
	}

	public ShopMenu() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		selectedItem = null;
		selectedItemIndex = -1;

		setItems();

		View v = inflater.inflate(R.layout.shop_menu, container, false);

		buy = (Button) v.findViewById(R.id.shopBuyButton);
		buy.setOnClickListener(this);

		Player p = Player.getPlayer();
		goldWidget = (TextView) v.findViewById(R.id.shopGoldDisplay);
		goldWidget.setText("Gold: " + p.getGold());

		updateButtons();

		return v;
	}

	// Will enable or disable buttons, depending on the situation.
	public void updateButtons() {
		if (selectedItem == null) {
			buy.setEnabled(false);
		} else {
			buy.setEnabled(true);
		}
	}

	@Override
	public void onResume() {
		fillListView(getView());
		super.onResume();
	}

	// Take a list of items, and fill up the list view with those entries
	public void fillListView(View v) {

		ItemAdapter adapter = new ItemAdapter(getActivity(),
				R.layout.shop_item, itemsInStock);

		setListAdapter(adapter);

		updateButtons();
	}

	private class ItemAdapter extends ArrayAdapter<Item> {

		List<Item> items;

		public ItemAdapter(Context context, int textViewResourceId,
				List<Item> itemsInStock) {
			super(context, textViewResourceId, itemsInStock);
			this.items = itemsInStock;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				v = LayoutInflater.from(getActivity()).inflate(
						R.layout.shop_item, null);
			}
			Item i = items.get(position);
			if (i != null) {
				TextView name = (TextView) v.findViewById(R.id.shopItemName);
				TextView price = (TextView) v.findViewById(R.id.shopItemPrice);
				ImageView image = (ImageView) v
						.findViewById(R.id.shopItemImage);
				name.setText("Name: " + i.getName());
				price.setText("Price: " + Integer.toString(i.getPrice()));
				Picasso.with(getActivity())
						.load(items.get(position).getImagePath()).into(image);

			}
			return v;
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);

		selectedItemIndex = position;

		if (selectedItem != null) {
			selectedItem.setBackgroundColor(Color.TRANSPARENT);
		}

		v.setBackgroundColor(Color.GRAY);

		v.setSelected(true);
		selectedItem = v;

		updateButtons();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.shopBuyButton: {

			AlertDialog.Builder builder1 = new AlertDialog.Builder(
					getActivity());
			sellItemToPlayer();

			final Item selected = itemsInStock.get(selectedItemIndex);
			if (selected.isEquipment()) {
				builder1.setMessage("Equip this item?");
				builder1.setCancelable(true);
				builder1.setPositiveButton("Equip",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								selected.useMe(Player.getPlayer(), Player
										.getPlayer().getInventory().getItems()
										.size() - 1);
							}
						});

				builder1.setNegativeButton("Don't Equip",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

				AlertDialog alert11 = builder1.create();
				alert11.show();
			}

			break;
		}
		default:
			break;
		}
	}

	public void sellItemToPlayer() {
		Player p = Player.getPlayer();

		if (p.getGold() < itemsInStock.get(selectedItemIndex).getPrice()) {

			AlertDialog.Builder builder1 = new AlertDialog.Builder(
					getActivity());
			builder1.setMessage("You don't have enough gold!");
			builder1.setCancelable(true);
			builder1.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});

			AlertDialog alert11 = builder1.create();
			alert11.show();

			return;
		} else if (p.getInventory().isInventoryFull()) {

			AlertDialog.Builder builder1 = new AlertDialog.Builder(
					getActivity());
			builder1.setMessage("Your inventory is full!");
			builder1.setCancelable(true);
			builder1.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});

			AlertDialog alert11 = builder1.create();
			alert11.show();

			return;
		}

		p.getInventory().addItem(itemsInStock.get(selectedItemIndex));
		p.deductGold(itemsInStock.get(selectedItemIndex).getPrice());
		goldWidget.setText("Gold: " + p.getGold());
	}

}