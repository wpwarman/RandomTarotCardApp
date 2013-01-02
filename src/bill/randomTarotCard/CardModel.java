package bill.randomTarotCard;

public class CardModel{
	private String displayName;
	private String suit;
	private String faceValue;
	private int id;
	private String imagePath;
	
	public static String[] DBColumns = {"_id", "facevalue", "suit", "displayname", "imagefile"};
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getFaceValue() {
		return faceValue;
	}
	
	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public void setSuit(String suit) {
		this.suit = suit;
	}
	
	public String getResourcePath() {
		String resourcePath = "placeholder";
		int indexOfFileExt = imagePath.indexOf('.');
		if (indexOfFileExt > 0)
		{
			String temp = imagePath.substring(0, indexOfFileExt);
			resourcePath = String.format("drawable/%s", temp);
		}
		return resourcePath;
	}
	
	@Override
	public String toString()
	{
		return displayName;
	}
}