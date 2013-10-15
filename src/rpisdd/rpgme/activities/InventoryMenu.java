package rpisdd.rpgme.activities;

import java.io.File;

import com.squareup.picasso.Picasso;

import rpisdd.rpgme.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class InventoryMenu extends Fragment {
	
	ImageView test;
	
	public InventoryMenu(){}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    	
    	View v = inflater.inflate(R.layout.inventory_menu, container, false);
    	
    	test =  (ImageView) v.findViewById(R.id.itemImage);
    	
    	//Sample line of code to load an image from assets into an ImageView with Picasso.
    	Picasso.with(getActivity()).load("file:///android_asset/Items/potion.gif").into(test);
    	
        // Inflate the layout for this fragment
    	return v;
    }
    
    
    
}
