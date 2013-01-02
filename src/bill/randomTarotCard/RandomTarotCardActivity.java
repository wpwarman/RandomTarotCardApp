package bill.randomTarotCard;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class RandomTarotCardActivity extends Activity {
	
	private List<CardModel> allCards;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        MySQLiteHelper dbHelper = new MySQLiteHelper(getBaseContext());
        
        try{
        	dbHelper.createDataBase();
        	dbHelper.openDataBase();
			allCards = dbHelper.getAllCards();
			CardModel randomCard = GetRandomCard();
			UpdateDisplay(randomCard);
			dbHelper.close();
        }
        catch (IOException ex){
        	System.out.println("Failed to read from tarot card database");
        } 
    }
    
    public void OnClick(View view)
    {	
    	CardModel randomCard = GetRandomCard();
    	UpdateDisplay(randomCard);
    }
     
    private CardModel GetRandomCard()
    {
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(allCards.size());
		CardModel randomCard = allCards.get(index);
		return randomCard;
    }
	
    private void UpdateDisplay(CardModel randomCard)
    {
    	ImageView cardImage = (ImageView) findViewById(R.id.image1);
		int imageId = getResources().getIdentifier(randomCard.getResourcePath(), null, this.getPackageName());
		cardImage.setImageResource(imageId);
		cardImage.setContentDescription(randomCard.getDisplayName());
    }  
}