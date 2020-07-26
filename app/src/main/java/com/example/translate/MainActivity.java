package com.example.translate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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

    public void setStatusBarColor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(id, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(id));
        }
    }

    public static void insertContentData(DatabaseHelper myDb, Activity activity) {
        myDb.insertContentData("What is Cyber?", 1, "Introduction", "Would you be comfortable living in a house that some stranger had access to? What if the door couldn't be locked or there was a hidden underground tunnel leading to the basement? Would you feel as though your safety and privacy is upheld? Using the internet and digital services is that house. If you need to store your personal belongings there, how do you go about protecting it?");
        myDb.insertContentData("What is Cyber?", 1, "Introduction", "The revolution of applications, IoT, computer and mobile devices has paved the way for cyberattacks leaving both individuals and companies vulnerable. With the vast number of ways in which attacks can occur, the question is \"when\" not \"if\".");
        myDb.insertContentData("What is Cyber?", 2, "A Brief History", "Early on, we had old legacy systems such as mainframes with limited access. They were centrally managed without easy access to the mainframe and instead access was usually through command-line terminals. Hence attack routes and methods to access data or compromise the data was difficult. ");
        myDb.insertContentData("What is Cyber?", 2, "A Brief History", "As we digitally progressed, the need to connect became more effervescent beginning with wired local access networks (LAN) that soon transitioned to Wireless Local Area Networks (WLAN) or better known as Wi-Fi. This provided ease of connection for both honest individuals as well as users with malicious intent. ");
        myDb.insertContentData("What is Cyber?", 2, "A Brief History", "Instead of all data being stored on centralised servers, we now have distributed devices such as computers with their own computational power, servers with their own processing power and even servers in the Cloud. With services spread across networks and devices, many more vulnerabilities exist.");
        myDb.insertContentData("What is Cyber?", 2, "A Brief History", "What is cybersecurity? Cybersecurity is how data, computer systems and networks are protected from digital attacks or compromises such as hackers or viruses. It is hence extremely important for organisations, governments and individuals to place a high focus on cybersecurity.");


        myDb.insertContentData("Cyber 101", 1, "Data Breaches", "If you keep up with the news, you will notice that every couple months, another large global company is hit by a data breach. This means customer payment information, logins and passwords are stolen. You may be left wondering, \"Now that this has occurred, what can I do to protect myself?\" Below are a few major steps to take as a precaution.");
        myDb.insertContentData("Cyber 101", 1, "Data Breaches", "1.\tReset your password now. Change your password for any and all compromised accounts. Multi-factor authentication should also be enabled for services that allow this extra layer of security. With this enabled, if attackers steal login credentials, they will not be able to access your account without the other authentication mechanism. It is extremely difficult to brute-force through a multi-factor authentication system provided that most companies lock accounts when too many incorrect attempts are made.");
        myDb.insertContentData("Cyber 101", 1, "Data Breaches", "2.\tMonitor your credit accounts. Observe any suspicious activity that arises. This is not just transactions that are to be looked out for but also where logins are from. Most banks and other digital service providers will notify when logins from unusual locations occur.");
        myDb.insertContentData("Cyber 101", 1, "Data Breaches", "3.\tConsider freezing your account. Freezing your bank account makes it harder to access the credit and access reports. Contacting the financial institutions, notifying the team and requesting help and advice is the most suitable course of action.");
        myDb.insertContentData("Cyber 101", 1, "Data Breaches", "4.\tCarefully monitor your inbox. Avoid phishing emails at all costs as there will be numerous attempts to get you to provide personal information. Cybercriminals are opportunistic and know that many victims will expect to receive some kind of communication or statement regarding the breach or accounts that were hacked.");
        myDb.insertContentData("Cyber 101", 2, "Phishing", "Phishing is the crime of deceiving individuals into exposing sensitive information such as passwords or payment information. There are many methods to reel a victim in but the most common is in the form of a malicious email or text message. These usually imitate an organisation that is trusted or a government office. Victims often fall for the message that appears from clouding better judgment with fear.");
        myDb.insertContentData("Cyber 101", 2, "Phishing", "“Phishing is the simplest kind of cyberattack and, at the same time, the most dangerous and effective.” - Adam Kujawa, Director of Malwarebytes Labs Phishing attacks the most vulnerable and powerful computer on the planet: the human mind.” Attackers here are not exploiting a technical vulnerability on a device or system. They are using “social engineering. Why waste time trying to penetrate through multiple layers of security when you can trick someone into handing over the key to the lock.");
        myDb.insertContentData("Cyber 101", 2, "Phishing", "Types of phishing attacks: \n" + "-\tSpear phishing \n" + "-\tEmail phishing \n" + "-\tWhaling \n" + "-\tSmishing and Vishing \n" + "-\tWatering hole phishing \n" + "-\tClone phishing");
        myDb.insertContentData("Cyber 101", 2, "Phishing", "How to identify a phishing attack: \n" + "Knowing whether something is a phishing attempt isn't always the easiest, but with some discipline and common sense, you can avoid a lot of issues down the road. Look for something abnormal or suspicious. You should ask yourself if the message passes the \"smell test\". Try to trust your intuition and do not get too swayed by the fear the message could generate. Phishing attacks more often than not, use fear to cloud judgement.");
        myDb.insertContentData("Cyber 101", 2, "Phishing", "A few more signs of a phishing attempt:\n" + "-\tYou recognise the sender but it's not someone that usually sends you messages.");
        myDb.insertContentData("Cyber 101", 2, "Phishing", "-\tThe message incites fear. Be careful when alarmist language or tone is used to create a sense of panic or urgency. Responsible organisations will not ask for personal details over the phone or internet.");
        myDb.insertContentData("Cyber 101", 2, "Phishing", "-\tThe message contains some unusual attachments that don't seem quite right to be attached.");
        myDb.insertContentData("Cyber 101", 2, "Phishing", "-\tThe message contains links that look suspicious. Be on the lookout for small spelling errors in the URL. You should always type in the URL yourself into your browser instead of clicking on the email link. To further this, don't click embedded hyperlinks without first hovering over it to see the destination URL.");
        myDb.insertContentData("Cyber 101", 2, "Phishing", "How do I protect myself against phishing? Many browsers have automatic checks on links to ensure they are safe before allowing you to enter. This is not a fool-proof method and ultimately it is still up to you to be the first line of defence. Important practices to stay safe:");
        myDb.insertContentData("Cyber 101", 2, "Phishing", "-\tDon't open e-mails from senders you are not familiar with.");
        myDb.insertContentData("Cyber 101", 2, "Phishing", "-\tDon't click on a link inside of an e-mail unless you know exactly where it is going.");
        myDb.insertContentData("Cyber 101", 2, "Phishing", "-\tTo add extra security, if you receive an e-mail from a source you are unsure of, navigate to the provided link manually by entering the legitimate website address into your browser.");
        myDb.insertContentData("Cyber 101", 2, "Phishing", "-\tLookout for the digital certificate of a website.");
        myDb.insertContentData("Cyber 101", 2, "Phishing", "-\tIf you are asked to provide sensitive information, check that the URL starts with “HTTPS” instead of just “HTTP.” The “S” stands for “secure.”It doesn't guarantee that the site is legitimate, but most legitimate sites use HTTPS because it's more secure. HTTP sites, even legitimate ones, are vulnerable to hackers.");
        myDb.insertContentData("Cyber 101", 2, "Phishing", "-\tHover the mouse over the link to see if it's legitimate.");


        myDb.insertContentData("Social Engineering", 1, "Introduction", "Social engineering is the act of manipulating individuals into disclosing confidential information. Social engineering aims to exploit human psychology rather than relying on technical hacking techniques, in order to gain access to sensitive data. ");
        myDb.insertContentData("Social Engineering", 1, "Introduction", "It is one of the most prolific and effective means of access securing systems and obtaining sensitive data however it is one that requires that least technical skills.");
        myDb.insertContentData("Social Engineering", 1, "Introduction", "Social engineering takes advantage of the weakest link in any organisation’s security defences – people. ");
        myDb.insertContentData("Social Engineering", 2, "Types of Social Engineering Attacks", "Phishing – an attempt to fraudulently obtain confidential information by sending an email disguised as a trustworthy entity");
        myDb.insertContentData("Social Engineering", 2, "Types of Social Engineering Attacks", "Whaling – use of phishing methods at high profile targets");
        myDb.insertContentData("Social Engineering", 2, "Types of Social Engineering Attacks", "Vishing – phishing conducted over the phone");
        myDb.insertContentData("Social Engineering", 2, "Types of Social Engineering Attacks", "Smishing – sending infected malware links via SMS");
        myDb.insertContentData("Social Engineering", 2, "Types of Social Engineering Attacks", "Pretexting – when an individual lies to obtain privileged data ");
        myDb.insertContentData("Social Engineering", 2, "Types of Social Engineering Attacks", "Baiting – use of physical media to disperse malware by appealing to victim’s curiosity or greed ");
        myDb.insertContentData("Social Engineering", 2, "Types of Social Engineering Attacks", "Watering hole – sabotaging a specific group of users by infecting websites associated with the group ");
        myDb.insertContentData("Social Engineering", 2, "Types of Social Engineering Attacks", "Tailgating – when an unauthorized person follows an authorized person into a secured premise");
        myDb.insertContentData("Social Engineering", 2, "Types of Social Engineering Attacks", "Quid pro quo – attacker promises to perform a service in exchange for sensitive data from the victim ");
        myDb.insertContentData("Social Engineering", 2, "Types of Social Engineering Attacks", "Dumpster diving – attacker steals sensitive data from trash ");
        myDb.insertContentData("Social Engineering", 2, "Types of Social Engineering Attacks", "Shoulder surfing – attack steals sensitive data by reading over the victim’s shoulder ");
        myDb.insertContentData("Social Engineering", 3, "Famous Social Engineering Attacks: Offer Something Sweet", "As any con artist will tell you, the easiest way to scam a mark is to exploit their own greed. This is the foundation of the classic Nigerian 419 scam, in which the scammer tries to convince the victim to help get supposedly ill-gotten cash out of their own country into a safe bank, offering a portion of the funds in exchange. ");
        myDb.insertContentData("Social Engineering", 3, "Famous Social Engineering Attacks: Offer Something Sweet", "These \"Nigerian prince\" emails have been a running joke for decades, but they're still an effective social engineering technique that people fall for: in 2007 the treasurer of a sparsely populated Michigan county gave $1.2 million in public funds to such a scammer in the hopes of personally cashing in. ");
        myDb.insertContentData("Social Engineering", 3, "Famous Social Engineering Attacks: Offer Something Sweet", "Another common lure is the prospect of a new, better job, which apparently is something far too many of us want: in a hugely embarrassing 2011 breach, the security company RSA was compromised when at least two low-level employees opened a malware file attached to a phishing email with the filename \"2011 recruitment plan.xls.\"");
        myDb.insertContentData("Social Engineering", 3, "Famous Social Engineering Attacks: Act Like You're in Charge", "Sometimes it is external authorities whose demands we comply with without giving it much thought. ");
        myDb.insertContentData("Social Engineering", 3, "Famous Social Engineering Attacks: Act Like You're in Charge", "Hillary Clinton campaign honcho John Podesta had his email hacked by Russian spies in 2016 when they sent him a phishing email disguised as a note from Google asking him to reset his password. ");
        myDb.insertContentData("Social Engineering", 3, "Famous Social Engineering Attacks: Act Like You're in Charge", "By taking action that he thought would secure his account, he actually gave his login credentials away.");
        myDb.insertContentData("Social Engineering", 4, "Importance of Guarding against Social Engineering? ", "Effective information security begins and ends with the user. Basic human interactions and communications have a significant effect on the security of an organisation. ");
        myDb.insertContentData("Social Engineering", 4, "Importance of Guarding against Social Engineering? ", "Employees pose the greatest security risk to organisations.");
        myDb.insertContentData("Social Engineering", 5, "Phishing", "Phishing is the most common social engineering attack. This is because most attacks can be launched through emails as individuals and companies commonly rely on emails as a means of communication and sharing of information. Malicious emails require two triggers to be effective: ");
        myDb.insertContentData("Social Engineering", 5, "Phishing", "A cleverly worded subject line that catches the attention of curiosity of the recipient.");
        myDb.insertContentData("Social Engineering", 5, "Phishing", "Compelling message that causes the recipient to click a link or open an attachment. This opens the doorway for the attack to be launched.");
        myDb.insertContentData("Social Engineering", 5, "Phishing", "Common indicators of phishing attempts:");
        myDb.insertContentData("Social Engineering", 5, "Phishing", "Suspicious sender address – often emails attempt to imitate a legitimate company’s email address. However, such emails often only closely resemble the reputable company’s by altering or omitting certain characters.");
        myDb.insertContentData("Social Engineering", 5, "Phishing", "Sent from an organisation in which the receiver has had no previous dealings with ");
        myDb.insertContentData("Social Engineering", 5, "Phishing", "Spelling and layout – phishing emails often contain poor spelling and grammar, utilise unusual phases, or have inconsistent layouts. ");
        myDb.insertContentData("Social Engineering", 5, "Phishing", "Spoofed hyperlinks and websites – by hovering the mouse over a link in an email, the text that appears when hovering may not match the link. ");
        myDb.insertContentData("Social Engineering", 5, "Phishing", "Suspicious attachments – often a common method to deliver malware, an attacker may create a false sense of urgency that encouragers victims to open attachments without examining them first. ");
        myDb.insertContentData("Social Engineering", 5, "Phishing", "Are unrealistic - unrealistic email headers or message may suggest that the email is malicious. ");
        myDb.insertContentData("Social Engineering", 5, "Phishing", "Imitations of a company’s logo – often attackers utilise a poor imitation of a reputable company’s logo. ");
        myDb.insertContentData("Social Engineering", 6, "Ransomware", "Ransomware has increasingly been utilised along with phishing mails. They often appear as attachments with file extensions such as “.PDF.zip” or “PDF.rar”. Ways to guard against ransomware include:");
        myDb.insertContentData("Social Engineering", 6, "Ransomware", "Avoid opening emails in the spam folder or emails with an unknown recipient");
        myDb.insertContentData("Social Engineering", 6, "Ransomware", "Avoid opening attachments in emails of unknown origin ");
        myDb.insertContentData("Social Engineering", 6, "Ransomware", "Use a reputable antivirus software");
        myDb.insertContentData("Social Engineering", 6, "Ransomware", "Perform regular backups to an external medium");
        myDb.insertContentData("Social Engineering", 6, "Ransomware", "Do not pay the ransom and seek help from a professional");


        myDb.insertContentData("Protecting Yourself", 1, "Protecting an Organisation ", "1.\tPatch – updating internet devices with fixes or improvements to software. This should be done as soon as possible to ensure that the device is utilising the latest security software. ");
        myDb.insertContentData("Protecting Yourself", 1, "Protecting an Organisation ", "2.\tMulti-factor authentication – is an authentication method that requires individuals to provide two or more credentials to authenticate their identity. Multi-factor authentication should be used to secure your internet access, infrastructure, and cloud-based platforms.");
        myDb.insertContentData("Protecting Yourself", 1, "Protecting an Organisation ", "3.\tSecure your hardware – safeguarding your hardware from loss or theft of devices. Protect all devices with a complicated password that is only shared with the devices’ user. Password should be committed to memory and not recorded down. Using more computers attached to desk is also effective in preventing intruders from walking away with your devices containing sensitive data. ");
        myDb.insertContentData("Protecting Yourself", 1, "Protecting an Organisation ", "4.\tBack up data – regularly backing up sensitive business data helps protect against the risk of damage or loss due to viruses or hacking. Ideally, data should be updated frequently such as every week. ");
        myDb.insertContentData("Protecting Yourself", 1, "Protecting an Organisation ", "5.\tCyber security insurance – is a type of business insurance product that protects businesses against the legal cost and expenses associated with cybercrime incidents. This minimizes the financial damage to organisations. ");
        myDb.insertContentData("Protecting Yourself", 1, "Protecting an Organisation ", "6.\tMaintain a security-focused culture – employees are the most common cause of security breaches. Ways to adhere to a security-focused workplace culture: \n" + "\tCommunicate and follow implemented cyber policy and procedures \n" + "\tUndertake staff education training dangers of cyber attacks\n" + "\tUnderstand the dangers of unsecured websites and avoid them\n" + "\tPrevent password sharing\n" + "\tUndertake employee educations programs\n");
        myDb.insertContentData("Protecting Yourself", 1, "Protecting an Organisation ", "7.\tUse robust anti-malware and firewall software – anti-malware tools help catch and isolate software viruses when attacked, while firewall is critical to preventing the system in the first place. ");
        myDb.insertContentData("Protecting Yourself", 1, "Protecting an Organisation ", "8.\tAustralian Cyber Security Center (ACSC) – organisations can be an ACSC partner to get the latest cyber threat advice to protect themselves online. ACSC recently released technical guidance outlining more than 50 types of attacks that have become increasingly used over the past year. ");
        myDb.insertContentData("Protecting Yourself", 2, "Protecting Yourself", "1.\tCheck up on data breaches – Frequent checks of updates on whether any accounts have been involved in a data breach");
        myDb.insertContentData("Protecting Yourself", 2, "Protecting Yourself", "2.\tStrengthen passwords – Testing the strength of your password using online tools such as https://howsecureismypassword.net/ and ensuring the use of longer strings with characters, symbols and upper case letters can make it harder to guess or crack.");
        myDb.insertContentData("Protecting Yourself", 2, "Protecting Yourself", "3.\tAvoid the extremely common passwords – Do not use passwords that are contained in password dictionaries for attackers looking to gain access through a simple crack. These are:\n" + "123456 (or any chronologically-ordered numbers) \n" + "987654321 \n" + "123123 \n" + "QWERTY \n" + "111111 \n" + "password\n");
        myDb.insertContentData("Protecting Yourself", 2, "Protecting Yourself", "4.\tKeep your guard up with emails – Trusting noone as the source of emails is an effective method to mitigate spam and phishing attempts. Be on the lookout for deceitful emails that may compromise your information once interacted with.");
        myDb.insertContentData("Protecting Yourself", 2, "Protecting Yourself", "5.\tSecure your device– If your personal devices are stolen, lost or unsecured, it could reveal a large amount of sensitive information and provide access to your money or identity. Some simple steps that are not to be overlooked are:\n" + "- install antivirus software\n" + "- setting a password, gesture or fingerprint in order to unlock the device\n" + "- using permissions to require additional settings or a password to be entered before any applications are installed\n" + "- do not allow installations from third party sources (this should be on by default)\n" + "- leave bluetooth off or hidden when not in use and disable automatic connection\n" + "- enable find my device services or remote locking if the device allows it\n");
        myDb.insertContentData("Protecting Yourself", 2, "Protecting Yourself", "6.\tKeep software updated – This is vital for operating systems and internet security software. Attackers frequently will use known exploits and flaws as a vulnerability to target and gain access to the system.");
        myDb.insertContentData("Protecting Yourself", 2, "Protecting Yourself", "7.\tManage social media settings – Keep your personal information private as this can be an easy target for social engineering attacks as hackers will only need a few data points to create a profile for an attack that can quickly allow them to answer security question answers. ");
        myDb.insertContentData("Protecting Yourself", 2, "Protecting Yourself", "8.\tStrengthen your home network –Using a Virtual Private Network(VPN) to access the internet encrypts all traffic that leaves your device. This makes it harder for data interception by cybercriminals listening in. This is extremely important when online in a public place using a public or unknown Wifi network such as a café.\n");
        myDb.insertContentData("Protecting Yourself", 3, "Staff Awareness Training", "Defences against vulnerabilities and risk: ");
        myDb.insertContentData("Protecting Yourself", 3, "Staff Awareness Training", "- Ensure staff training modules and sessions are completed by all staff a per business requirements ");
        myDb.insertContentData("Protecting Yourself", 3, "Staff Awareness Training", "- Use examples to illustrate risks and consequences");
        myDb.insertContentData("Protecting Yourself", 3, "Staff Awareness Training", "- Align training to best practices and industry standards");
        myDb.insertContentData("Protecting Yourself", 3, "Staff Awareness Training", "Employee Check-list Sample: ");
        myDb.insertContentData("Protecting Yourself", 3, "Staff Awareness Training", "- Password etiquette");
        myDb.insertContentData("Protecting Yourself", 3, "Staff Awareness Training", "- Social media and being safe online ");
        myDb.insertContentData("Protecting Yourself", 3, "Staff Awareness Training", "- Personal data and compliance");
        myDb.insertContentData("Protecting Yourself", 3, "Staff Awareness Training", "- Social media and being safe online ");
        myDb.insertContentData("Protecting Yourself", 3, "Staff Awareness Training", "- Mobile device use and BYOD ");
        myDb.insertContentData("Protecting Yourself", 3, "Staff Awareness Training", "- Clean desk policies");
        myDb.insertContentData("Protecting Yourself", 3, "Staff Awareness Training", "- Flash drives, removable media etc. ");
        myDb.insertContentData("Protecting Yourself", 4, "Technical Controls", "- Email filtering/spam protection – the organisation can organise and segment incoming emails to a specified criteria, based on business rules.");
        myDb.insertContentData("Protecting Yourself", 4, "Technical Controls", "- Real-time awareness alerts – help both operations and security staff to increase information security awareness, to detect unusual activities ");
        myDb.insertContentData("Protecting Yourself", 4, "Technical Controls", "- Device hardening – process of eliminating means of attack vectors by patching vulnerabilities, turning off non-essential services and configuring systems with security protocols ");
        myDb.insertContentData("Protecting Yourself", 4, "Technical Controls", "- Phishing simulation tests – process in which organisations purposely send deceptive, malicious emails in a controlled environment. In order to gauge the reactions of employees.");
        myDb.insertContentData("Protecting Yourself", 4, "Technical Controls", "- DLP solutions – type of strategy in which the organisation makes sure end users do not send sensitive or critical information outside the corporate network.");
        myDb.insertContentData("Protecting Yourself", 4, "Technical Controls", "- Physical security – protection of hardware, software, networks and data from physical actions and events that may potentially lead to loss or damage to an enterprise’s network.");
        myDb.insertContentData("Protecting Yourself", 4, "Technical Controls", "- Policies/standards – organisations have formalised written documents outlining how the business can protect itself from threats.");
        myDb.insertContentData("Protecting Yourself", 4, "Technical Controls", "- Aggressive authentication – process in which an organisation implements authentication protocols, enhancing the difficulty of accessing the organisation’s mission critical assets. ");





    }

    public static void insertWordData(DatabaseHelper myDb, Activity activity) {
        myDb.insertData("Threat", "An event or an action that can compromise a system or violate security", "What is Cyber?", false, false);
        myDb.insertData("Exploit", "A way to breach security measures put in place for a machine through a loophole or vulnerability", "What is Cyber?", false, false);
        myDb.insertData("Vulnerability", "Existence of a weakness in the design or implementation that can lead to undesirable compromising events", "What is Cyber?", false, false);
        myDb.insertData("Risk", "An expectation of loss if a particular threat exploits a particular vulnerability with a particular harmful result ", "What is Cyber?", false, false);
        myDb.insertData("Attacker", "Individual who compromises machine security through vulnerabilities for the purpose of stealing, manipulating of destroying data", "What is Cyber?", false, false);
        myDb.insertData("Attack", "Action performed by attackers that have the potential to harm the system of stored information", "What is Cyber?", false, false);
        myDb.insertData("Device Security", "The protection of the system from theft or damage to either the software, hardware or data present on the system. It will generally involve processes to safeguard against attackers from gaining access to the system and the data for malicious purposes", "What is Cyber?", false, false);
        myDb.insertData("Confidentiality", "Information is only available to those who are authorised and should have access", "What is Cyber?", false, false);
        myDb.insertData("Integrity", "Information is accurate, reliable and complete in its original intended form", "What is Cyber?", false, false);
        myDb.insertData("Availability", "Information is accessible to those authorised whenever required", "What is Cyber?", false, false);
        myDb.insertData("Authenticity", "Identification and assurance of the information's origin", "What is Cyber?", false, false);
        myDb.insertData("Non-Repudiation", "Assurance that authenticity cannot be denied", "What is Cyber?", false, false);
        myDb.insertData("", "", "What is Cyber?", false, false);

        myDb.insertData("Antivirus Software", "Computer programs that can detect, block and remove viruses and malware", "Cyber 101", false, false);
        myDb.insertData("Backup", "Extra copies of files and data that can be used to restore data when lost or damaged", "Cyber 101", false, false);
        myDb.insertData("Data Breach", "A result of a cyberattack that allows cybercriminals to gain unauthorised access to a system or network to steal the private, sensitive or personal and financial data of users within", "Cyber 101", false, false);
        myDb.insertData("DDos", "Distributed Denial of service attack that attempts to cause an online service unavailable by overwhelming it with a flood of traffic in packets", "Cyber 101", false, false);
        myDb.insertData("Encryption", "Process of using codes to make readable text unreadable. Encrypted information cannot be read until it is decrypted", "Cyber 101", false, false);
        myDb.insertData("Firewall", "Software designed to block malware from entering into the network", "Cyber 101", false, false);
        myDb.insertData("Hacker", "Attacker that seeks to compromise digital devices such as computers, smartphones, tablets or networks. They can have many motivations such as personal gain, make a statement, political reasons or simply because they can", "Cyber 101", false, false);
        myDb.insertData("Identity Theft", "Occurs when a criminal obtains or uses personal information that they have gained of another individual to assume their identity or access their online accounts", "Cyber 101", false, false);
        myDb.insertData("Keylogger", "Software that secretly records what you see, say or do on your device", "Cyber 101", false, false);
        myDb.insertData("Malware", "Software that harms devices, networks or individuals. It includes viruses, worms, ransomware and other programs", "Cyber 101", false, false);
        myDb.insertData("Network", "Two or more interconnected devices that can exchange data", "Cyber 101", false, false);
        myDb.insertData("Phishing", "An attempt to trick people into revealing sensitive information. The main method is by the use of emails or fake websites", "Cyber 101", false, false);
        myDb.insertData("Ransomware", "New form of malware that locks users out of their files or devices then demands payment to restore access", "Cyber 101", false, false);
        myDb.insertData("Server", "Computer or program that provides services on a network. E.g. Email server or web server", "Cyber 101", false, false);
        myDb.insertData("Social Engineering", "Methods cybercriminals use to get their victim to undertake some questionable action that usually involves sending money or providing confidential private information", "Cyber 101", false, false);
        myDb.insertData("Software", "Package of code written in a programming language or languages instructing devices to perform certain tasks", "Cyber 101", false, false);
        myDb.insertData("Spam", "Unsolicited emails sent to many destination addresses", "Cyber 101", false, false);
        myDb.insertData("Spyware", "Form of malware that hides on your device, monitoring activity and has the potential to steal sensitive information such as passwords or banking information", "Cyber 101", false, false);
        myDb.insertData("Vulnerability", "Flaw or weakness that attackers can exploit to gain access to information or damage", "Cyber 101", false, false);
        myDb.insertData("Virus", "A piece of code which is capable of copying itself and typically has a detrimental effect, such as corrupting the system or destroying data", "Cyber 101", false, false);
        myDb.insertData("", "", "Cyber 101", false, false);

        myDb.insertData("Social Engineering", "The act of manipulating individuals into disclosing confidential information. Social engineering aims to exploit human psychology rather than relying on technical hacking techniques, in order to gain access to sensitive data", "Social Engineering", false, false);
        myDb.insertData("Phishing", "An attempt to fraudulently obtain confidential information by sending an email disguised as a trustworthy entity", "Social Engineering", false, false);
        myDb.insertData("Spear Phishing", "Attack through the use of a fraudulent email at a specific target", "Social Engineering", false, false);
        myDb.insertData("Whaling", "Use of phishing methods at high profile targets", "Social Engineering", false, false);
        myDb.insertData("Vishing", "Phishing conducted over the phone", "Social Engineering", false, false);
        myDb.insertData("Smishing", "Sending infected malware links via SMS ", "Social Engineering", false, false);
        myDb.insertData("Pretexting", "When an individual lies to obtain privileged data ", "Social Engineering", false, false);
        myDb.insertData("Baiting", "Use of physical media to disperse malware by appealing to victim’s curiosity or greed", "Social Engineering", false, false);
        myDb.insertData("Watering Hole", "Sabotaging a specific group of users by infecting websites associated with the group", "Social Engineering", false, false);
        myDb.insertData("Tailgating", "When an unauthorized person follows an authorized person into a secured premise", "Social Engineering", false, false);
        myDb.insertData("Quid Pro Quo", "Attacker promises to perform a service in exchange for sensitive data from the victim", "Social Engineering", false, false);
        myDb.insertData("Dumpster Diving", "Attacker steals sensitive data from trash", "Social Engineering", false, false);
        myDb.insertData("Shoulder Surfing", "Attack steals sensitive data by reading over the victim’s shoulder", "Social Engineering", false, false);
        myDb.insertData("", "", "Social Engineering", false, false);


    }

    public static void insertStoryData(DatabaseHelper myDb, Activity activity) {
        // storyId, pageId, pageContent, one, two, three, four
        myDb.insertStoryData(0, 0, "You are a software engineer who works for Google. The team lead has piled on 3 projects for you to work on and you are having trouble keeping up. One of the projects is however quite simple and you are thinking about offloading to a friend who is also at Google. Your friend however recommends that you should get a third party engineer to do this for you. Do you decide to let the third party engineer take this on for you?", "Yes", "No", "", "");

        myDb.insertStoryData(0, 1, "You tell your friend you don’t want to risk that. He doesn’t understand though, this is an easy task that any new engineer can complete and it won’t cost much. Select two risks that you will tell him.", "Customer personal details are given away and used for third party promotions", "Servers can be made vulnerable and hacked from unauthorised access", "The engineer may get hired and take your position", "The data you give him may be incorrect");
        myDb.insertStoryData(0, 2, "You want to give the task to the engineer however you don’t want to give him sensitive company data. Consider the risks of giving information to the third party engineer.  Select an option.", "How risky can it be? I’m sure the engineer is trust-worthy.", "Tell your friend that it’s too high of a risk. ", "", "");

        myDb.insertStoryData(0, 3, "Your friend understands your concerns. He suggests you talk to your team lead to rethink the task allocation. Do you want to?", "Yes", "No", "", "");
        myDb.insertStoryData(0, 4, "You argue with your friend to no avail as he does not understand your points. He leaves you to make the decision on your own. How do you plan to complete this?", "Talk to the team lead and ask for an extension", "Use the third party engineer anyway", "Just do it on your own", "");
        myDb.insertStoryData(0, 5, "You decide to go ahead and give the third party engineer the task. The engineer finishes the task for you. However, when you go to test the application you notice a virus has installed itself and your servers are compromised. Do you choose to report this to your team lead?", "Yes", "No", "", "");

        myDb.insertStoryData(0, 6, "You decide to complete the task on your own and avoided all risk. You have understood the confidentiality of company data. In future, discussing with the team lead can reveal safer ways to complete work. ", "", "", "", "");
        myDb.insertStoryData(0, 7, "After your talk with the team lead, he extends the deadline on your tasks by a week. You take this time to continue discussing with the team lead and he organises reliable employees to help you complete the tasks. You understood the risks of sharing company data and followed up appropriately. ", "", "", "", "");
        myDb.insertStoryData(0, 8, "Your team lead is very worried about this situation and must not have company data leaked. He needs you and your friend to report your interactions with the third party engineer to ensure no security threats. Please reflect and select the options which would have prevented this.", "", "", "", "");
        myDb.insertStoryData(0, 9, "Failing to report this, a security breach occurs and the engineer steals sensitive user data. The team lead finds out and is very worried as this will have huge ramifications on Google’s reputation and users. He needs you to log your interactions with the third party engineer to attempt to fix this issue. You will be held responsible for this. Please reflect and select the options which would have prevented this. ", "", "", "", "");
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

    public static void insertAchievementData(DatabaseHelper myDb, Activity activity) {
        myDb.insertAchievementData("Cyber Novice I", "Complete the What is Cyber? Learning module", 0, 1, false);
        myDb.insertAchievementData("Cyber Novice II", "Complete the What is Cyber? Flashcards module", 0, 1, false);
        myDb.insertAchievementData("Cyber Novice III", "Complete the What is Cyber? Quiz module", 0, 1, false);
        myDb.insertAchievementData("Certified Cyber Novice", "Complete Level One: What is Cyber?", 0, 3, false);

        myDb.insertAchievementData("Cyber Skilled I", "Complete the Cyber 101 Learning module", 0, 1, false);
        myDb.insertAchievementData("Cyber Skilled II", "Complete the Cyber 101 Flashcards module", 0, 1, false);
        myDb.insertAchievementData("Cyber Skilled III", "Complete the Cyber 101 Quiz module", 0, 1, false);
        myDb.insertAchievementData("Cyber Skilled IV", "Complete the Cyber 101 Story module", 0, 1, false);
        myDb.insertAchievementData("Certified Cyber Skilled", "Complete Level Two: Cyber 101", 0, 4, false);

        myDb.insertAchievementData("Anti-Social Engineer I", "Complete the Social Engineering Learning module", 0, 1, false);
        myDb.insertAchievementData("Anti-Social Engineer II", "Complete the Social Engineering FLashcards module", 0, 1, false);
        myDb.insertAchievementData("Anti-Social Engineer III", "Complete the Social Engineering Quiz module", 0, 1, false);
        myDb.insertAchievementData("Anti-Social Engineer IV", "Complete the Social Engineering Story module", 0, 1, false);
        myDb.insertAchievementData("Certified Anti-Social Engineer", "Complete Level Three: Social Engineering", 0, 4, false);

        myDb.insertAchievementData("Cyber Defender I", "Complete the Protecting Yourself Learning module", 0, 1, false);
        myDb.insertAchievementData("Cyber Defender II", "Complete the Protecting Yourself FLashcards module", 0, 1, false);
        myDb.insertAchievementData("Cyber Defender III", "Complete the Protecting Yourself Quiz module", 0, 1, false);
        myDb.insertAchievementData("Cyber Defender IV", "Complete the Protecting Yourself Story module", 0, 1, false);
        myDb.insertAchievementData("Certified Cyber Defender", "Complete Level Four: Protecting Yourself", 0, 4, false);

        myDb.insertAchievementData("Dedicated", "Revise your saved words", 0, 1, false);
        myDb.insertAchievementData("Pursuing Perfection", "Revise your mastered words", 0, 1, false);

        myDb.insertAchievementData("Cyber Scholar", "Complete all Learning modules", 0, 4, false);
        myDb.insertAchievementData("Cyber Specialist", "Complete all Quiz modules", 0, 4, false);

        myDb.insertAchievementData("Cyber Savvy", "Complete a Quiz without any mistakes", 0, 1, false);
        myDb.insertAchievementData("Nice Nine", "Achieve over 90% for any Quiz", 0, 1, false);
        myDb.insertAchievementData("Excellent Eight", "Achieve over 80% for any Quiz", 0, 1, false);
        myDb.insertAchievementData("Super Seven", "Achieve over 70% for any Quiz", 0, 1, false);
        myDb.insertAchievementData("Sexy Six", "Achieve over 60% for any Quiz", 0, 1, false);
        myDb.insertAchievementData("Did you even try?", "Achieve under 30% for any Quiz", 0, 1, false);
        myDb.insertAchievementData("Off to a Great Start", "Get the first answer wrong", 0, 1, false);
        myDb.insertAchievementData("Abort Mission?", "Get 3 answers wrong in a row_achievement", 0, 1, false);
        myDb.insertAchievementData("Abandon Ship!", "Get 5 answers wrong in a row_achievement", 0, 1, false);
        myDb.insertAchievementData("Oh Baby a Triple!", "Get 3 answers correct in a row_achievement", 0, 1, false);
        myDb.insertAchievementData("Pentakill!", "Get 5 answers correct in a row_achievement", 0, 1, false);

        myDb.insertAchievementData("Instant Noodles!", "Get an answer correct within 1 second", 0, 1, false);
        myDb.insertAchievementData("Slick Speedster!", "Get an answer correct within 2 seconds", 0, 1, false);

        myDb.insertAchievementData("Smart Saver", "Save 5 words", 0, 5, false);
        myDb.insertAchievementData("Sophisticated Saver", "Save 20 words", 0, 20, false);
        myDb.insertAchievementData("Terrific Tester", "Take 10 Quizzes", 0, 10, false);
        myDb.insertAchievementData("Talented Tester", "Take 20 Quizzes", 0, 20, false);
        myDb.insertAchievementData("Tenacious Tester", "Take 30 Quizzes", 0, 30, false);

        //open a youtube link
        //


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
            insertStoryData(myDb, activity);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            myDb.close();
        }

    }

}
