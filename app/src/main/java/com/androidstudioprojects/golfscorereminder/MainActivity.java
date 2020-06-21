package com.androidstudioprojects.golfscorereminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ListActivity {
	private static final String PREFS_FILE = "com.androidstudioprojects" +
					".golfscorereminder.preferences";
	private static final String KEY_STROKE_COUNT = "key_stroke_count";
	private SharedPreferences mSharedPreferences;
	private SharedPreferences.Editor mEditor;
	private Hole[] mHoles = new Hole[18];
	private ListAdapter mListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
		mEditor = mSharedPreferences.edit();
		
		// Initialize holes
		int strokes = 0;
		for (int i = 0; i < mHoles.length; i++){
			strokes = mSharedPreferences.getInt(KEY_STROKE_COUNT + i, 0);
			mHoles[i] = new Hole("Hole " + (i + 1) + " :", strokes);
			
		}
		
		mListAdapter = new ListAdapter(this, mHoles);
		setListAdapter(mListAdapter);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		for (int i = 0; i < mHoles.length; i++) {
			mEditor.putInt(KEY_STROKE_COUNT + i, mHoles[i].getmStrokeCount());
		}
		mEditor.apply();
	}
	
	// Inflate Menu file
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		int id = item.getItemId();
		
		// noinspection SimplifiableIfStream
		if (id == R.id.action_clear_strokes){
			mEditor.clear();
			mEditor.apply();
			
			for (Hole hole: mHoles) {
				hole.setmStrokeCount(0);
			}
			// Update List View after hit ClearStroke button
			mListAdapter.notifyDataSetChanged();
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
