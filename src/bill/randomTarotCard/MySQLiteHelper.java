package bill.randomTarotCard;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper
{
	private Context context;
	private static String DATABASE_PATH = "/data/data/bill.randomTarotCard/databases/";
	private static String DATABASE_NAME = "tarotdeck";
	private static int DATABASE_VERSION = 1;
	private SQLiteDatabase tarotCardDB;
	
	
	// Constructor
	public MySQLiteHelper(Context context)
	{		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	public void createDataBase() throws IOException
	{
		boolean dbExists = checkDataBase();
		if(!dbExists)
		{
			this.getReadableDatabase();
			try{
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying tarot card database");
			}
		}
	}
		
	private boolean checkDataBase(){
		SQLiteDatabase checkDB = null;
		try{
			String path = DATABASE_PATH + DATABASE_NAME;
			checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		}catch(SQLiteException ex){
			throw new Error("Tarot card database does not exist yet");
		}
		
		if(checkDB != null){
			checkDB.close();
		}
		
		return (checkDB != null);
	}
	
	private void copyDataBase() throws IOException{
		InputStream input = context.getAssets().open(DATABASE_NAME);
		String outputFile = DATABASE_PATH + DATABASE_NAME;
		OutputStream output = new FileOutputStream(outputFile);
		
		byte[] buffer = new byte[1024];
		int length;
		while((length = input.read(buffer)) > 0){
			output.write(buffer, 0, length);
		}
		
		output.flush();
		output.close();
		input.close();
	}
	
	public void openDataBase() throws SQLException{
		String path = DATABASE_PATH + DATABASE_NAME;
		tarotCardDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
	}
	
	@Override
	public synchronized void close(){
		if(tarotCardDB != null){
			tarotCardDB.close();
		}
		super.close();
	}
	
	@Override
	public void onCreate(SQLiteDatabase database){
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
	}
	
	public List<CardModel> getAllCards()
	{
		List<CardModel> cards = new ArrayList<CardModel>();
		Cursor cursor = tarotCardDB.query("cards", CardModel.DBColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			CardModel card = cursorToComment(cursor); 
			cards.add(card);
			cursor.moveToNext();
		}
		cursor.close();
		return cards;
	}
	
	private CardModel cursorToComment(Cursor cursor)
	{
		CardModel card = new CardModel();
		card.setId(cursor.getInt(0));
		card.setFaceValue(cursor.getString(1));
		card.setSuit(cursor.getString(2));
		card.setDisplayName(cursor.getString(3));
		card.setImagePath(cursor.getString(4));
		return card;
	}
}