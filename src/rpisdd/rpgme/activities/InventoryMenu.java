package rpisdd.rpgme.activities;

import java.util.ArrayList;

import rpisdd.rpgme.R;
import rpisdd.rpgme.gamelogic.items.Item;
import rpisdd.rpgme.gamelogic.player.Player;
import rpisdd.rpgme.popups.AnnoyingPopup;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
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

public class InventoryMenu extends ListFragment implements OnClickListener {

	Button sell;
	Button use;
	Button details;
	Item selectedItem;
	View selectedItemSlot;
	int selectedItemIndex;

	ItemAdapter itemAdapter;

	public InventoryMenu() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		selectedItem = null;
		selectedItemSlot = null;
		selectedItemIndex = -1;

		View v = inflater.inflate(R.layout.inventory_menu, container, false);

		sell = (Button) v.findViewById(R.id.sellItem);
		sell.setOnClickListener(this);

		use = (Button) v.findViewById(R.id.useItem);
		use.setOnClickListener(this);

		details = (Button) v.findViewById(R.id.detailsItem);
		details.setOnClickListener(this);

		updateButtons();

		return v;
	}

	// Will enable or disable buttons, depending on the situation.
	public void updateButtons() {
		if (selectedItemSlot == null) {
			sell.setEnabled(false);
			use.setEnabled(false);
			details.setEnabled(false);
		} else {
			sell.setEnabled(true);
			details.setEnabled(true);
			if (selectedItem != null
					&& selectedItem.isUsable(Player.getPlayer())) {
				use.setEnabled(true);
			} else {
				use.setEnabled(false);
			}
		}

		Item item = Player.getPlayer().getInventory()
				.getItemAt(selectedItemIndex);

		if (item != null) {
			if (item.isEquipment()) {
				if (item == Player.getPlayer().getInventory().getWeapon()
						|| item == Player.getPlayer().getInventory().getArmor()) {
					use.setText("Unequip");
				} else {
					use.setText("Equip");
				}
			} else {
				use.setText("Use");
			}
		}
	}

	@Override
	public void onResume() {
		updateListView(false);
		super.onResume();
	}

	// Update the list view adapter. Also can choose to unselect current
	// selection
	public void updateListView(boolean unselectAll) {

		Player p = Player.getPlayer();

		if (unselectAll) {
			selectedItem = null;
			selectedItemSlot = null;
			selectedItemIndex = -1;
			itemAdapter = new ItemAdapter(getActivity(), R.layout.shop_item, p
					.getInventory().getItems());
			setListAdapter(itemAdapter);
		} else {
			if (itemAdapter == null) {
				itemAdapter = new ItemAdapter(getActivity(),
						R.layout.shop_item, p.getInventory().getItems());
				setListAdapter(itemAdapter);
			} else {
				itemAdapter.notifyDataSetChanged();
			}

		}

		updateButtons();
	}

	private class ItemAdapter extends ArrayAdapter<Item> {

		ArrayList<Item> items;

		public ItemAdapter(Context context, int textViewResourceId,
				ArrayList<Item> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				v = LayoutInflater.from(getActivity()).inflate(
						R.layout.inventory_item, null);
			}
			if (position == selectedItemIndex) {
				v.setSelected(true);
			}
			Item i = items.get(position);
			if (i != null) {
				TextView name = (TextView) v
						.findViewById(R.id.inventoryItemName);
				name.setText(i.getName());

				ImageView image = (ImageView) v
						.findViewById(R.id.inventoryItemImage);
				name.setText(i.getName());

				name.setTextColor(Color.WHITE);

				if (i == Player.getPlayer().getInventory().getArmor()
						|| i == Player.getPlayer().getInventory().getWeapon()) {
					name.setTextColor(Color.GREEN);
					name.append(" (Equipped)");
				}
				Picasso.with(getActivity()).load(i.getImagePath()).into(image);
			}
			return v;
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		selectedItemIndex = position;

		if (selectedItemSlot != null) {
			selectedItemSlot.setBackgroundColor(Color.TRANSPARENT);
		}

		v.setBackgroundColor(Color.GRAY);

		v.setSelected(true);
		selectedItemSlot = v;
		selectedItem = Player.getPlayer().getInventory().getItems()
				.get(selectedItemIndex);

		updateButtons();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.useItem:
			useItem();
			break;
		case R.id.sellItem:
			AnnoyingPopup.doDont(getActivity(), "Sell this item?", "Sell",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
							sellItemToShop();
						}
					});
			break;
		case R.id.detailsItem:
			AnnoyingPopup.notice(getActivity(), selectedItem.getDescription());
			break;
		default:
			break;
		}
	}

	public void sellItemToShop() {
		Player p = Player.getPlayer();
		p.addGold(selectedItem.getRefundPrice());
		p.getInventory().removeItemAt(selectedItemIndex);
		updateListView(true);
	}

	public void useItem() {

		Player p = Player.getPlayer();
		selectedItem.useMe(p, selectedItemIndex);
		Log.i("items", String.format("Player has %s and %s equipped", p
				.getInventory().getWeapon(), p.getInventory().getArmor()));

		if (!selectedItem.isEquipment()) {
			updateListView(true);
		} else {
			updateListView(false);
		}
	}
}