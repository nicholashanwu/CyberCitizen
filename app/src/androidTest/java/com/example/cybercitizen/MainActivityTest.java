package com.example.cybercitizen;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

	@Rule
	public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
	private DatabaseHelper myDb;

	private MainActivity mActivity = null;

	@Before
	public void setUp() throws Exception {
		mActivity = mActivityTestRule.getActivity();
		myDb = DatabaseHelper.getInstance(mActivityTestRule.getActivity().getApplicationContext());

	}

	@Test
	public void testAchievements(){
		assertTrue(myDb.getCountAchievements() > 1);
	}

	@Test
	public void testContent(){
		assertTrue(myDb.getCountContent() > 1);
	}

	@Test
	public void testScores(){
		assertTrue(myDb.getCountScores() > 1);
	}

	@Test
	public void testStory(){
		assertTrue(myDb.getCountStory() > 1);
	}

	@Test
	public void testWords(){
		assertTrue(myDb.getCountWords() > 1);
	}

	@After
	public void tearDown() throws Exception {
		mActivity = null;
		myDb = null;
	}
}