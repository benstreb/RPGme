package rpisdd.rpgme.gamelogic.items;

public class Item {
	
	String name;
	int price;
	String imagePath;	//Path to image in assets folder
	
	public static Item createItemFromName(String aname){
		Item newItem = null;
		if(aname.equals("Energy Potion")){
			newItem = (Item)(new EnergyPotion());
		}
		return newItem;
	}
	
	public Item(String aname,int aprice,String aimagePath){
		name = aname;
		price = aprice;
		imagePath = aimagePath;
	}
	
	public int getRefundPrice(){
		return (int)(price * 0.5f);
	}
	
	public String getName(){ return name; }
	public int getPrice(){ return price; }
	public String getImagePath(){ return imagePath; }
}
