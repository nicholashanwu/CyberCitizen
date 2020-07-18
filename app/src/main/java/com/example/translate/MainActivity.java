package com.example.translate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomBar;
    boolean isTest;

    public static void insertAchievementData(DatabaseHelper myDb, Activity activity) {
        myDb.insertAchievementData("Number Novice", "Complete the Numbers learning module", 0, 1, false);
        myDb.insertAchievementData("Great Greeter", "Complete the Essentials learning module", 0, 1, false);
        myDb.insertAchievementData("Food Fight", "Complete the Food learning module", 0, 1, false);
        myDb.insertAchievementData("Helping Hand", "Complete the Help learning module", 0, 1, false);
        myDb.insertAchievementData("Dedicated", "Revise your saved words", 0, 1, false);
        myDb.insertAchievementData("Pursuing Perfection", "Revise your mastered words", 0, 1, false);
        myDb.insertAchievementData("Self-Improver", "Check out all components in your profile", 0, 3, false);
        myDb.insertAchievementData("Lingo Learner", "Complete all learning modules", 0, 4, false);
        myDb.insertAchievementData("Lingo Legend", "Complete a test without any mistakes", 0, 1, false);
        myDb.insertAchievementData("Number Cruncher", "Complete the Numbers test module", 0, 1, false);
        myDb.insertAchievementData("The Nice Guy", "Complete the Essentials test module", 0, 1, false);
        myDb.insertAchievementData("Shef", "Complete the Food test module", 0, 1, false);
        myDb.insertAchievementData("Public Service", "Complete the Help test module", 0, 1, false);
        myDb.insertAchievementData("Lingo Lord", "Complete all test modules", 0, 4, false);
        myDb.insertAchievementData("Nice Nine", "Achieve over 90% for any test", 0, 1, false);
        myDb.insertAchievementData("Excellent Eight", "Achieve over 80% for any test", 0, 1, false);
        myDb.insertAchievementData("Sensational Seven", "Achieve over 70% for any test", 0, 1, false);
        myDb.insertAchievementData("Sexy Six", "Achieve over 60% for any test", 0, 1, false);
        myDb.insertAchievementData("Did you even try?", "Achieve under 30% for any test", 0, 1, false);
        myDb.insertAchievementData("Off to a Great Start", "Get the first answer wrong", 0, 1, false);
        myDb.insertAchievementData("Abort?", "Get 3 answers wrong in a row", 0, 1, false);
        myDb.insertAchievementData("Abandon Ship!", "Get 5 answers wrong in a row", 0, 1, false);
        myDb.insertAchievementData("Oh Baby a Triple!", "Get 3 answers correct in a row", 0, 1, false);
        myDb.insertAchievementData("Pentakill!", "Get 5 answers correct in a row", 0, 1, false);
        myDb.insertAchievementData("Average Addition", "Visit your My Words list", 0, 1, false);
        myDb.insertAchievementData("Avid Addition", "Add 10 words to the My Words section", 0, 10, false);
        myDb.insertAchievementData("Awesome Addition", "Add 50 words to the My Words section", 0, 50, false);
        myDb.insertAchievementData("Ambitious Addition", "Add 500 words to the My Words section", 0, 500, false);
        myDb.insertAchievementData("Smart Saver", "Save 5 words", 0, 5, false);
        myDb.insertAchievementData("Sophisticated Saver", "Save 20 words", 0, 20, false);
        myDb.insertAchievementData("Terrific Tester", "Take 10 tests", 0, 10, false);
        myDb.insertAchievementData("Talented Tester", "Save 20 tests", 0, 20, false);
        myDb.insertAchievementData("Tenacious Tester", "Save 30 tests words", 0, 30, false);


    }

    private void setColors() {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled}, // enabled
                new int[]{-android.R.attr.state_enabled}, // disabled
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[]{
                Color.WHITE,
                Color.GRAY,
                Color.WHITE,
                Color.GRAY
        };

        final ColorStateList colorList = new ColorStateList(states, colors);
        bottomBar.setItemIconTintList(colorList);
        bottomBar.setItemTextColor(colorList);
    }

    private void setAnimations(final NavController navController) {
        final NavOptions slideLeftNavOptions = new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_right)
                .setPopExitAnim(R.anim.slide_out_left)
                .setPopUpTo(navController.getGraph().getStartDestination(), false)
                .build();

        final NavOptions slideRightNavOptions = new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.slide_in_left)
                .setExitAnim(R.anim.slide_out_right)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .setPopUpTo(navController.getGraph().getStartDestination(), false)
                .build();

        /*bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean handled = false;
                if (navController.getCurrentDestination().getId() == R.id.navigation_home && item.getItemId() != R.id.navigation_home) {                //if learn is selected
                    navController.navigate(item.getItemId(), null, slideLeftNavOptions);
                    handled = true;
                } else if (navController.getCurrentDestination().getId() == R.id.navigation_profile && item.getItemId() != R.id.navigation_profile) {      //if profile is selected
                    navController.navigate(item.getItemId(), null, slideRightNavOptions);
                    handled = true;
                } else if (navController.getCurrentDestination().getId() == R.id.navigation_test_home && (item.getItemId() == R.id.navigation_dashboard || item.getItemId() == R.id.navigation_profile) && item.getItemId() != R.id.navigation_test_home) {
                    navController.navigate(item.getItemId(), null, slideLeftNavOptions);
                    handled = true;
                } else if (navController.getCurrentDestination().getId() == R.id.navigation_test_home && item.getItemId() == R.id.navigation_home && item.getItemId() != R.id.navigation_test_home) {
                    navController.navigate(item.getItemId(), null, slideRightNavOptions);
                    handled = true;
                } else if (navController.getCurrentDestination().getId() == R.id.navigation_dashboard && (item.getItemId() == R.id.navigation_profile) && item.getItemId() != R.id.navigation_dashboard) {
                    navController.navigate(item.getItemId(), null, slideLeftNavOptions);
                    handled = true;
                } else if (navController.getCurrentDestination().getId() == R.id.navigation_dashboard && (item.getItemId() == R.id.navigation_home || item.getItemId() == R.id.navigation_test_home) && item.getItemId() != R.id.navigation_dashboard) {
                    navController.navigate(item.getItemId(), null, slideRightNavOptions);
                    handled = true;
                } else if (navController.getCurrentDestination().getId() == R.id.navigation_test) {
                    enableBottomBar(false);
                    isTest = true;
                    handled = true;
                } else if (navController.getCurrentDestination().getId() == R.id.navigation_learning) {
                    enableBottomBar(false);
                    isTest = true;
                    handled = true;
                } else if (navController.getCurrentDestination().getId() == R.id.navigation_my_list_fragment) {
                    enableBottomBar(false);
                    isTest = true;
                    handled = true;
                } else if (navController.getCurrentDestination().getId() == R.id.navigation_achievement) {
//                    enableBottomBar(false);
//                    isTest = true;
//                    handled = true;
                } else if (navController.getCurrentDestination().getId() == R.id.navigation_dashboard && item.getItemId() == R.id.navigation_achievement) {
                    enableBottomBar(false);
                    isTest = true;
                    handled = true;
                } else if (item.getItemId() != R.id.navigation_home) {
                    navController.navigate(item.getItemId(), null, slideRightNavOptions);
                } else {
                    navController.navigate(item.getItemId(), null);
                    handled = true;
                }
                return handled;

            }
        });*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (firstStart) {
            initDatabase task = new initDatabase(this);
            task.execute();
            updateSharedPreferences();
        }


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_main);

        bottomBar = findViewById(R.id.nav_view);


        setColors();


        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_test_home, R.id.navigation_compass, R.id.navigation_dashboard)
                .build();

        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomBar, navController);

        setAnimations(navController);


        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                enableBottomBar(true);
                isTest = false;
                if (destination.getId() == R.id.navigation_home) {
                    bottomBar.setBackgroundColor(getResources().getColor(R.color.colorPurpleDark));
                    setStatusBarColor(R.color.colorPurpleDark);
                } else if (destination.getId() == R.id.navigation_test_home) {
                    bottomBar.setBackgroundColor(getResources().getColor(R.color.colorRedDark));
                    setStatusBarColor(R.color.colorRedDark);
                } else if (destination.getId() == R.id.navigation_compass) {
                    bottomBar.setBackgroundColor(getResources().getColor(R.color.colorBlueDark));
                    setStatusBarColor(R.color.colorBlueDark);
                } else if (destination.getId() == R.id.navigation_learning) {
                    isTest = true;
                    bottomBar.setBackgroundColor(Color.parseColor("#444444"));
                    setStatusBarColor(R.color.colorPurpleDark);
                    enableBottomBar(false);
                } else if (destination.getId() == R.id.navigation_test) {
                    isTest = true;
                    bottomBar.setBackgroundColor(Color.parseColor("#444444"));
                    enableBottomBar(false);
                }  else if (destination.getId() == R.id.navigation_dashboard) {
                    bottomBar.setBackgroundColor(getResources().getColor(R.color.colorYellowDark));
                    setStatusBarColor(R.color.colorYellowDark);
                } else if (destination.getId() == R.id.navigation_achievement) {
                    isTest = true;
                    bottomBar.setBackgroundColor(Color.parseColor("#444444"));
                    enableBottomBar(false);
                }

            }
        });


    }



    public void updateSharedPreferences() {

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    public static void insertWordData(DatabaseHelper myDb, Activity activity) {
        myDb.insertData("One", "一", "numbers", false, false);
        myDb.insertData("Two", "二", "numbers", false, false);
        myDb.insertData("Three", "三", "numbers", false, false);
        myDb.insertData("Four", "四", "numbers", false, false);
        myDb.insertData("Five", "五", "numbers", false, false);
        myDb.insertData("Six", "六", "numbers", false, false);
        myDb.insertData("Seven", "七", "numbers", false, false);
        myDb.insertData("Eight", "八", "numbers", false, false);
        myDb.insertData("Nine", "九", "numbers", false, false);
        myDb.insertData("Ten", "十", "numbers", false, false);
        myDb.insertData("Twenty", "二十", "numbers", false, false);
        myDb.insertData("Fifty", "五十", "numbers", false, false);
        myDb.insertData("One Hundred", "一百", "numbers", false, false);
        myDb.insertData("One Thousand", "一千", "numbers", false, false);

        myDb.insertData("Hello", "你好", "essentials", false, false);
        myDb.insertData("How are you?", "你好吗", "essentials", false, false);
        myDb.insertData("Thank you", "谢谢", "essentials", false, false);
        myDb.insertData("Good", "好", "essentials", false, false);
        myDb.insertData("Not good", "不好", "essentials", false, false);
        myDb.insertData("Sorry", "对不起", "essentials", false, false);
        myDb.insertData("Ok!", "好的", "essentials", false, false);
        myDb.insertData("Good Morning", "早上好", "essentials", false, false);
        myDb.insertData("Goodnight", "晚安", "essentials", false, false);
        myDb.insertData("Good Evening", "晚上好", "essentials", false, false);
        myDb.insertData("I am-", "我是", "essentials", false, false);
        myDb.insertData("Bye", "再见", "essentials", false, false);

        myDb.insertData("Apple", "苹果", "food", false, false);
        myDb.insertData("Banana", "香蕉", "food", false, false);
        myDb.insertData("Orange", "橙子", "food", false, false);
        myDb.insertData("Hamburger", "汉堡包", "food", false, false);
        myDb.insertData("Dumpling", "饺子", "food", false, false);
        myDb.insertData("Rice", "白饭", "food", false, false);
        myDb.insertData("Noodles", "面条", "food", false, false);
        myDb.insertData("Orange Juice", "橙汁", "food", false, false);
        myDb.insertData("Apple Juice", "苹果汁", "food", false, false);
        myDb.insertData("Coffee", "咖啡", "food", false, false);
        myDb.insertData("Tea", "茶", "food", false, false);
        myDb.insertData("Pizza", "比萨", "food", false, false);
        myDb.insertData("Sushi", "寿司", "food", false, false);

        myDb.insertData("Police", "警察", "help", false, false);
        myDb.insertData("Police Station", "警察局", "help", false, false);
        myDb.insertData("Ambulance", "救护车", "help", false, false);
        myDb.insertData("Hospital", "医院", "help", false, false);
        myDb.insertData("Fire", "火", "help", false, false);
        myDb.insertData("Drugstore", "药店", "help", false, false);
        myDb.insertData("Help", "救命", "help", false, false);
        myDb.insertData("Stay Away", "远离", "help", false, false);
        myDb.insertData("Headache", "头痛", "help", false, false);
        myDb.insertData("Hot Water", "热水", "help", false, false);
        myDb.insertData("Go Away!", "走开", "help", false, false);

    }

    public void setStatusBarColor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(id, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(id));
        }
    }

    public static void insertScoreData(DatabaseHelper myDb, Activity activity) {
        myDb.insertScoreData("Achievements", 0);            //
        myDb.insertScoreData("Tests Taken", 0);             //
        myDb.insertScoreData("Mastered Words", 0);          //
        myDb.insertScoreData("Level", 0);                   //
        myDb.insertScoreData("Words Added", 0);
        myDb.insertScoreData("Experience", 0);
        myDb.insertScoreData("HD", 0);
        myDb.insertScoreData("D", 0);
        myDb.insertScoreData("C", 0);
        myDb.insertScoreData("P", 0);
        myDb.insertScoreData("F", 0);
    }

    public static void insertContentData(DatabaseHelper myDb, Activity activity) {
        myDb.insertContentData("What is Cyber?", 1, "Introduction", "Would you be comfortable living in a house that some stranger had access to? What if the door couldn't be locked or there was a hidden underground tunnel leading to the basement? Would you feel as though your safety and privacy is upheld? Using the internet and digital services is that house. If you need to store your personal belongings there, how do you go about protecting it?");
        myDb.insertContentData("What is Cyber?", 1, "Introduction", "The revolution of applications, IoT, computer and mobile devices has paved the way for cyberattacks leaving both individuals and companies vulnerable. With the vast number of ways in which attacks can occur, the question is \"when\" not \"if\".");
        myDb.insertContentData("What is Cyber?", 2, "A Brief History", "Early on, we had old legacy systems such as mainframes with limited access. They were centrally managed without easy access to the mainframe and instead access was usually through command-line terminals. Hence attack routes and methods to access data or compromise the data was difficult. ");
        myDb.insertContentData("What is Cyber?", 2, "A Brief History", "As we digitally progressed, the need to connect became more effervescent beginning with wired local access networks (LAN) that soon transitioned to Wireless Local Area Networks (WLAN) or better known as Wi-Fi. This provided ease of connection for both honest individuals as well as users with malicious intent. ");
        myDb.insertContentData("What is Cyber?", 2, "A Brief History", "Instead of all data being stored on centralised servers, we now have distributed devices such as computers with their own computational power, servers with their own processing power and even servers in the Cloud. With services spread across networks and devices, many more vulnerabilities exist.");
        myDb.insertContentData("What is Cyber?", 2, "A Brief History", "What is cybersecurity? Cybersecurity is how data, computer systems and networks are protected from digital attacks or compromises such as hackers or viruses. It is hence extremely important for organisations, governments and individuals to place a high focus on cybersecurity.");
        myDb.insertContentData("What is Cyber?", 3, "A Brief History", "content");


    }

    private void enableBottomBar(boolean enable) {
        for (int i = 0; i < bottomBar.getMenu().size(); i++) {
            bottomBar.getMenu().getItem(i).setEnabled(enable);
        }
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        if (isTest) {
            showMessage("", "Please go back by using the button in the top left");
        } else {
            super.onBackPressed();
        }
    }

    private static class initDatabase extends AsyncTask<Void, Void, Void> {

        WeakReference<MainActivity> activityWeakReference;

        DatabaseHelper myDb;
        initDatabase(MainActivity activity) {
            activityWeakReference = new WeakReference<MainActivity>(activity);
            myDb = new DatabaseHelper(activity);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return null;
            }

            insertWordData(myDb, activity);
            insertAchievementData(myDb, activity);
            insertScoreData(myDb, activity);
            insertContentData(myDb, activity);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            myDb.close();
        }

    }

}
