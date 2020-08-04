package com.example.cybercitizen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.slider.Slider;

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

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
		boolean firstStart = prefs.getBoolean("firstStart", true);

		setContentView(R.layout.activity_main);
		final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
				R.id.navigation_home, R.id.navigation_compass, R.id.navigation_dashboard)
				.build();

		ActionBar actionBar = getSupportActionBar();
		actionBar.hide();

		bottomBar = findViewById(R.id.nav_view);

		setColors();

		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(bottomBar, navController);


		NavGraph graph = navController.getNavInflater().inflate(R.navigation.mobile_navigation);


		if (firstStart) {
			graph.setStartDestination(R.id.onboardingFragment);
			navController.setGraph(graph);
			bottomBar.setVisibility(View.GONE);

			initDatabase task = new initDatabase(this);
			task.execute();
			updateSharedPreferences();
		} else {
			graph.setStartDestination(R.id.navigation_home);
			navController.setGraph(graph);
		}

		setAnimations(navController);

		navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
			@Override
			public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
				enableBottomBar(true);
				isTest = false;
				if (destination.getId() == R.id.navigation_home) {
					bottomBar.setBackgroundColor(getResources().getColor(R.color.colorPurpleDark));
					setStatusBarColor(R.color.colorPurple);
				} else if (destination.getId() == R.id.navigation_compass) {
					bottomBar.setBackgroundColor(getResources().getColor(R.color.colorBlueDark));
					setStatusBarColor(R.color.colorBlueDark);
				} else if (destination.getId() == R.id.navigation_learning) {
					isTest = true;
					bottomBar.setBackgroundColor(Color.parseColor("#444444"));
					setStatusBarColor(R.color.colorGreenDark);
					enableBottomBar(false);
				} else if (destination.getId() == R.id.navigation_test) {
					isTest = true;
					setStatusBarColor(R.color.colorRedDark);
					bottomBar.setBackgroundColor(Color.parseColor("#444444"));
					enableBottomBar(false);
				} else if (destination.getId() == R.id.navigation_dashboard) {
					bottomBar.setBackgroundColor(getResources().getColor(R.color.colorYellowDark));
					setStatusBarColor(R.color.colorYellowDark);
				} else if (destination.getId() == R.id.navigation_achievement) {
					isTest = true;
					bottomBar.setBackgroundColor(Color.parseColor("#444444"));
					enableBottomBar(false);
				} else if (destination.getId() == R.id.onboardingFragment) {
					enableBottomBar(false);
					setStatusBarColor(R.color.secondaryBlack);
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
		myDb.getWritableDatabase().beginTransaction();

		myDb.insertContentData("What is Cyber?", 1, "Introduction", "Would you be comfortable living in a house that some stranger had access to? What if the door couldn't be locked or there was a hidden underground tunnel leading to the basement? Would you feel as though your safety and privacy is upheld?");
		myDb.insertContentData("What is Cyber?", 1, "Introduction", "Using the internet and digital services is that house. If you need to store your personal belongings there, how do you go about protecting it?");
		myDb.insertContentData("What is Cyber?", 1, "Introduction", "The revolution of applications, IoT, computer and mobile devices has paved the way for cyberattacks leaving both individuals and companies vulnerable. With the vast number of ways in which attacks can occur, the question is \"when\" not \"if\".");
		myDb.insertContentData("What is Cyber?", 2, "A Brief History", "Early on, we had old legacy systems such as mainframes with limited access. They were centrally managed without easy access to the mainframe and instead access was usually through command-line terminals. Hence attack routes and methods to access data or compromise the data was difficult. ");
		myDb.insertContentData("What is Cyber?", 2, "A Brief History", "As we digitally progressed, the need to connect became more effervescent beginning with wired local access networks (LAN) that soon transitioned to Wireless Local Area Networks (WLAN) or better known as Wi-Fi. This provided ease of connection for both honest individuals as well as users with malicious intent. ");
		myDb.insertContentData("What is Cyber?", 2, "A Brief History", "Instead of all data being stored on centralised servers, we now have distributed devices such as computers with their own computational power, servers with their own processing power and even servers in the Cloud. With services spread across networks and devices, many more vulnerabilities exist.");
		myDb.insertContentData("What is Cyber?", 2, "A Brief History", "What is cybersecurity? Cybersecurity is how data, computer systems and networks are protected from digital attacks or compromises such as hackers or viruses. It is hence extremely important for organisations, governments and individuals to place a high focus on cybersecurity.");

		myDb.insertContentData("Cyber 101", 1, "Cyber Risk", "Cybersecurity, at its heart, is the management of risk related to internet-connected businesses. That means almost every business in the world. ");
		myDb.insertContentData("Cyber 101", 1, "Cyber Risk", "Familiar risks are hackers and viruses shared over the internet, malicious software, vulnerabilities in older systems, and the various methods used to counteract and prevent these incidents");
		myDb.insertContentData("Cyber 101", 1, "Cyber Risk", "So why does an attacker decide to attack? They can attack for financial gain, obtaining confidential intelligence, disrupting competitors or services, or for political intent. An attacker will only attack if the value of attacking outweighs the costs of attacking");
		myDb.insertContentData("Cyber 101", 1, "Cyber Risk", "What parts of an organisation or an individual are at risk? Digital information stored in databases, servers, and networks are all at risk. Even physical documents can be vulnerable as we will discuss later on.");
		myDb.insertContentData("Cyber 101", 2, "Cyber Attacks", "In the early days of computing, frauds occurred without any computer involvement. For example, having a nonexistent employee listed on a payroll where money is sent to someone else's account. ");
		myDb.insertContentData("Cyber 101", 2, "Cyber Attacks", "Another is called the salami fraud, so-called because it slices off the fractions in rounding calculations and pocketing those small amounts. Over millions of transactions, this can add up to a huge number");
		myDb.insertContentData("Cyber 101", 2, "Cyber Attacks", "As the use of computers grew, so did the sophistication of frauds. Youngsters were enthralled by the opportunity to break into other computers, beginning the age of the teen hacker. Their motivations were mostly to get recognised by their peers. ");
		myDb.insertContentData("Cyber 101", 2, "Cyber Attacks", "Kevin Mitnick, a hacker of the 1990s, hacked others for over a decade just to show how good he was before he was sentenced to jail for three years. ");
		myDb.insertContentData("Cyber 101", 2, "Cyber Attacks", "Hackers began to hack to send messages like 'I got you' and for political slander. A more sophisticated attack called Denial of Service emerged in which a remote attacker compromises IT systems by exploiting vulnerabilities or by overwhelming their ability to handle information flows.");
		myDb.insertContentData("Cyber 101", 2, "Cyber Attacks", "Adversaries were later able to take control of many computers at a time, called a botnet, and then direct an attack on a single target, amplifying the impact of the denial of service. This is called a distributed denial of service (DDoS).");
		myDb.insertContentData("Cyber 101", 3, "2014 DDoS Attacks", "A global example of DDoS occurred in February 2014 when an unknown attacker launched a rolling wave of distributed denial of service attacks on a variety of targets, country by country around the world. The scale of the attack was enormous. ");
		myDb.insertContentData("Cyber 101", 3, "2014 DDoS Attacks", "The attack used a special feature of the network time protocol to amplify the data. By the time the packets reached the target, they had been amplified 50 times, resulting in the equivalent of about 250,000 individual denial of service attacks. ");
		myDb.insertContentData("Cyber 101", 3, "2014 DDoS Attacks", "One of the businesses targeted in this campaign was attacked by 4,278 individual IP addresses from over 80 countries, delivering a continuous stream of over one million packets per minute for about an hour. ");
		myDb.insertContentData("Cyber 101", 4, "Automation and Botnets", "The cyber crime industry has flourished largely in part due to the possibility to automate cyber crimes. To make serious money from cybercrimes, automation is needed to carry out attacks at huge scales");
		myDb.insertContentData("Cyber 101", 4, "Automation and Botnets", "In the early days of computers, viruses hopped from computer to computer through floppy disks. They began to appear in file downloads as internet speeds got faster. Then USBs became the norm as a prime vector for infection. Compromised websites became hosts for malware. ");
		myDb.insertContentData("Cyber 101", 4, "Automation and Botnets", "The impact of viruses shifted from minor nuisances to financial gain. The popularity of email exploded the cyber crime industry. ");
		myDb.insertContentData("Cyber 101", 4, "Automation and Botnets", "Cybercrime scales by what's known as a botnet, which consists of the botmaster who runs a number of command and control systems. They infect legitimate websites and used for command and control but only for a short period of time before it leaves the host and infects a new one. ");
		myDb.insertContentData("Cyber 101", 4, "Automation and Botnets", "The botmaster runs the command and control servers, and each command and control system controls a large number of 'zombie' computers that have been infected. A large botnet may have over a million computers under its control");
		myDb.insertContentData("Cyber 101", 4, "Automation and Botnets", "A typical task for a zombie would be to extract files from a target, spam the target with emails, or send out packets as part of a DDoS attack");
		myDb.insertContentData("Cyber 101", 5, "Rootkits", "Sophisticated hackers have developed strong malware that is able to look deep into the heart of an operating system. This is known as a rootkit. ");
		myDb.insertContentData("Cyber 101", 5, "Rootkits", "A rootkit penetrates a host and installs itself into the system, making sure it can restart after a system reboot. A rookit does not exploit vulnerabilities, rather it is designed to hide and carry out its mission using normal system functions. ");
		myDb.insertContentData("Cyber 101", 5, "Rootkits", "Important to note is that rootkits are not viruses - they do not propagate on their own. However, they can be combined with viruses to propagate. ");
		myDb.insertContentData("Cyber 101", 5, "Rootkits", "Rootkits can hide inside the operating system kernel which bypasses the detection systems of traditional antivirus software. They operate on what is called 'ring zero' which is the deepest part of the kernela and lets them carry out system functions with elevated privileges. There is a very little a rootkit can't do at this level. ");
		myDb.insertContentData("Cyber 101", 6, "Advanced Persistence Threats", "Over the years there has been an increasing participation of nation states in cyber warfare. They use highly sophisticated malware, known as APTs or Advanced Persistence Threats. This is malware directed at political and military targets");
		myDb.insertContentData("Cyber 101", 6, "Advanced Persistence Threats", "There are five key characteristics that APTs have that set them apart from rootkits");
		myDb.insertContentData("Cyber 101", 6, "Advanced Persistence Threats", "1. They are highly customized to suit the specific intended attack. This means that they were designed to target the systems of their specific target");
		myDb.insertContentData("Cyber 101", 6, "Advanced Persistence Threats", "2. They usually have multiple 'zero-day' exploits through which to exploit their target. A zero-day vulnerability is unknown to or not addressed by the developers of that software. ");
		myDb.insertContentData("Cyber 101", 6, "Advanced Persistence Threats", "3. An APT may have more than one objective and these may change over time");
		myDb.insertContentData("Cyber 101", 6, "Advanced Persistence Threats", "4. Their deployment can be controlled by humans to some extent rather than being fully automated");
		myDb.insertContentData("Cyber 101", 6, "Advanced Persistence Threats", "5. They can stay hidden for far longer than rootkits as they act very slowly");
		myDb.insertContentData("Cyber 101", 7, "APT: Stuxnet", "The most notorious military-grade APT to date has been Stuxnet, which was detected in 2010. It was designed to target centrifuges in the Iranian nuclear program, targeting the Siemens industrial plant equipment used in nuclear fuel enrichment. ");
		myDb.insertContentData("Cyber 101", 7, "APT: Stuxnet", "A key feature of Stuxnet was that it was designed to be delivered through email or USB sticks, meaning it can reach target systems without the target being connected to the internet.");
		myDb.insertContentData("Cyber 101", 7, "APT: Stuxnet", "Stuxnet had four zero-day vulnerabilities which it used to propagate and deliver the payload to the Iranian nuclear systems. ");
		myDb.insertContentData("Cyber 101", 7, "APT: Stuxnet", "Once on the system, it lay dormant for many months, simply collecting the centrifuge data logs. ");
		myDb.insertContentData("Cyber 101", 7, "APT: Stuxnet", "After thirteen days, it took control of the software and then repeatedly sped up and slowed down the centrifuges. In the meantime, the collected logs were played back to the system, completely masking that there was any problem at all.");
		myDb.insertContentData("Cyber 101", 7, "APT: Stuxnet", "The changes in rotation speed caused the aluminium tubes to expand and contract, eventually destroying 900 to 1000 centrifuges");
		myDb.insertContentData("Cyber 101", 7, "APT: Stuxnet", "The US admitted in 2012 that it was responsible with Israel for developing Stuxnet. ");

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
		myDb.insertContentData("Social Engineering", 5, "Phishing", "Phishing is the crime of deceiving individuals into exposing sensitive information such as passwords or payment information. There are many methods to reel a victim in but the most common is in the form of a malicious email or text message. These usually imitate an organisation that is trusted or a government office. Victims often fall for the message that appears from clouding better judgment with fear.");
		myDb.insertContentData("Social Engineering", 5, "Phishing", "“Phishing is the simplest kind of cyberattack and, at the same time, the most dangerous and effective.” - Adam Kujawa, Director of Malwarebytes Labs.");
		myDb.insertContentData("Social Engineering", 5, "Phishing", "Phishing attacks the most vulnerable and powerful computer on the planet: the human mind. Attackers here are not exploiting a technical vulnerability on a device or system. They are using “social engineering”. Why waste time trying to penetrate through multiple layers of security when you can trick someone into handing over the key to the lock?");
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
		myDb.insertContentData("Protecting Yourself", 2, "Data Breaches", "If you keep up with the news, you will notice that every couple months, another large global company is hit by a data breach. This means customer payment information, logins and passwords are stolen. You may be left wondering, \"Now that this has occurred, what can I do to protect myself?\" Below are a few major steps to take as a precaution.");
		myDb.insertContentData("Protecting Yourself", 2, "Data Breaches", "1.\tReset your password now. Change your password for all compromised accounts. Multi-factor authentication should also be enabled for services that allow this extra layer of security. With this enabled, if attackers steal login credentials, they cannot access your account without the other authentication mechanism. ");
		myDb.insertContentData("Protecting Yourself", 2, "Data Breaches", "2.\tMonitor your credit accounts. Observe any suspicious activity that arises. This is not just transactions that are to be looked out for but also where logins are from. Most banks and other digital service providers will notify when logins from unusual locations occur.");
		myDb.insertContentData("Protecting Yourself", 2, "Data Breaches", "3.\tConsider freezing your account. Freezing your bank account makes it harder to access the credit and access reports. Contacting the financial institutions, notifying the team and requesting help and advice is the most suitable course of action.");
		myDb.insertContentData("Protecting Yourself", 2, "Data Breaches", "4.\tCarefully monitor your inbox. Avoid phishing emails at all costs as there will be numerous attempts to get you to provide personal information. Cybercriminals are opportunistic and know that many victims will expect to receive some kind of communication or statement regarding the breach or accounts that were hacked.");
		myDb.insertContentData("Protecting Yourself", 3, "Protecting Yourself", "1.\tCheck up on data breaches – Frequent checks of updates on whether any accounts have been involved in a data breach");
		myDb.insertContentData("Protecting Yourself", 3, "Protecting Yourself", "2.\tStrengthen passwords – Testing the strength of your password using online tools such as https://howsecureismypassword.net/ and ensuring the use of longer strings with characters, symbols and upper case letters can make it harder to guess or crack.");
		myDb.insertContentData("Protecting Yourself", 3, "Protecting Yourself", "3.\tAvoid the extremely common passwords – Do not use passwords that are contained in password dictionaries for attackers looking to gain access through a simple crack. These are:\n" + "123456 (or any chronologically-ordered numbers) \n" + "987654321 \n" + "123123 \n" + "QWERTY \n" + "111111 \n" + "password\n");
		myDb.insertContentData("Protecting Yourself", 3, "Protecting Yourself", "4.\tKeep your guard up with emails – Trusting noone as the source of emails is an effective method to mitigate spam and phishing attempts. Be on the lookout for deceitful emails that may compromise your information once interacted with.");
		myDb.insertContentData("Protecting Yourself", 3, "Protecting Yourself", "5.\tSecure your device– If your personal devices are stolen, lost or unsecured, it could reveal a large amount of sensitive information and provide access to your money or identity. Some simple steps that are not to be overlooked are:\n" + "- install antivirus software\n" + "- setting a password, gesture or fingerprint in order to unlock the device\n" + "- using permissions to require additional settings or a password to be entered before any applications are installed\n" + "- do not allow installations from third party sources (this should be on by default)\n" + "- leave bluetooth off or hidden when not in use and disable automatic connection\n" + "- enable find my device services or remote locking if the device allows it\n");
		myDb.insertContentData("Protecting Yourself", 3, "Protecting Yourself", "6.\tKeep software updated – This is vital for operating systems and internet security software. Attackers frequently will use known exploits and flaws as a vulnerability to target and gain access to the system.");
		myDb.insertContentData("Protecting Yourself", 3, "Protecting Yourself", "7.\tManage social media settings – Keep your personal information private as this can be an easy target for social engineering attacks as hackers will only need a few data points to create a profile for an attack that can quickly allow them to answer security question answers. ");
		myDb.insertContentData("Protecting Yourself", 3, "Protecting Yourself", "8.\tStrengthen your home network –Using a Virtual Private Network(VPN) to access the internet encrypts all traffic that leaves your device. This makes it harder for data interception by cybercriminals listening in. This is extremely important when online in a public place using a public or unknown Wifi network such as a café.\n");
		myDb.insertContentData("Protecting Yourself", 4, "Protecting Against Phishing", "Types of phishing attacks: \n" + "-\tSpear phishing \n" + "-\tEmail phishing \n" + "-\tWhaling \n" + "-\tSmishing and Vishing \n" + "-\tWatering hole phishing \n" + "-\tClone phishing");
		myDb.insertContentData("Protecting Yourself", 4, "Protecting Against Phishing", "How to identify a phishing attack: \n" + "Knowing whether something is a phishing attempt isn't always the easiest, but with some discipline and common sense, you can avoid a lot of issues down the road. Look for something abnormal or suspicious. You should ask yourself if the message passes the \"smell test\". Try to trust your intuition and do not get too swayed by the fear the message could generate.");
		myDb.insertContentData("Protecting Yourself", 4, "Protecting Against Phishing", "A few more signs of a phishing attempt:\n" + "-\tYou recognise the sender but it's not someone that usually sends you messages.");
		myDb.insertContentData("Protecting Yourself", 4, "Protecting Against Phishing", "-\tThe message incites fear. Be careful when alarmist language or tone is used to create a sense of panic or urgency. Responsible organisations will not ask for personal details over the phone or internet.");
		myDb.insertContentData("Protecting Yourself", 4, "Protecting Against Phishing", "-\tThe message contains some unusual attachments that don't seem quite right to be attached.");
		myDb.insertContentData("Protecting Yourself", 4, "Protecting Against Phishing", "-\tThe message contains links that look suspicious. Be on the lookout for small spelling errors in the URL. You should always type in the URL yourself into your browser instead of clicking on the email link. To further this, don't click embedded hyperlinks without first hovering over it to see the destination URL.");
		myDb.insertContentData("Protecting Yourself", 4, "Protecting Against Phishing", "How do I protect myself against phishing? Many browsers have automatic checks on links to ensure they are safe before allowing you to enter. This is not a fool-proof method and ultimately it is still up to you to be the first line of defence. Important practices to stay safe:");
		myDb.insertContentData("Protecting Yourself", 4, "Protecting Against Phishing", "-\tDon't open e-mails from senders you are not familiar with.");
		myDb.insertContentData("Protecting Yourself", 4, "Protecting Against Phishing", "-\tDon't click on a link inside of an e-mail unless you know exactly where it is going.");
		myDb.insertContentData("Protecting Yourself", 4, "Protecting Against Phishing", "-\tTo add extra security, if you receive an e-mail from a source you are unsure of, navigate to the provided link manually by entering the legitimate website address into your browser.");
		myDb.insertContentData("Protecting Yourself", 4, "Protecting Against Phishing", "-\tLookout for the digital certificate of a website.");
		myDb.insertContentData("Protecting Yourself", 4, "Protecting Against Phishing", "-\tIf you are asked to provide sensitive information, check that the URL starts with “HTTPS” instead of just “HTTP.” The “S” stands for “secure.”It doesn't guarantee that the site is legitimate, but most legitimate sites use HTTPS because it's more secure. HTTP sites, even legitimate ones, are vulnerable to hackers.");
		myDb.insertContentData("Protecting Yourself", 4, "Protecting Against Phishing", "-\tHover the mouse over the link to see if it's legitimate.");
		myDb.insertContentData("Protecting Yourself", 5, "Staff Awareness Training", "Defences against vulnerabilities and risk: ");
		myDb.insertContentData("Protecting Yourself", 5, "Staff Awareness Training", "- Ensure staff training modules and sessions are completed by all staff a per business requirements ");
		myDb.insertContentData("Protecting Yourself", 5, "Staff Awareness Training", "- Use examples to illustrate risks and consequences");
		myDb.insertContentData("Protecting Yourself", 5, "Staff Awareness Training", "- Align training to best practices and industry standards");
		myDb.insertContentData("Protecting Yourself", 5, "Staff Awareness Training", "Employee Check-list Sample: ");
		myDb.insertContentData("Protecting Yourself", 5, "Staff Awareness Training", "- Password etiquette");
		myDb.insertContentData("Protecting Yourself", 5, "Staff Awareness Training", "- Social media and being safe online ");
		myDb.insertContentData("Protecting Yourself", 5, "Staff Awareness Training", "- Personal data and compliance");
		myDb.insertContentData("Protecting Yourself", 5, "Staff Awareness Training", "- Social media and being safe online ");
		myDb.insertContentData("Protecting Yourself", 5, "Staff Awareness Training", "- Mobile device use and BYOD ");
		myDb.insertContentData("Protecting Yourself", 5, "Staff Awareness Training", "- Clean desk policies");
		myDb.insertContentData("Protecting Yourself", 5, "Staff Awareness Training", "- Flash drives, removable media etc. ");
		myDb.insertContentData("Protecting Yourself", 6, "Technical Controls", "- Email filtering/spam protection – the organisation can organise and segment incoming emails to a specified criteria, based on business rules.");
		myDb.insertContentData("Protecting Yourself", 6, "Technical Controls", "- Real-time awareness alerts – help both operations and security staff to increase information security awareness, to detect unusual activities ");
		myDb.insertContentData("Protecting Yourself", 6, "Technical Controls", "- Device hardening – process of eliminating means of attack vectors by patching vulnerabilities, turning off non-essential services and configuring systems with security protocols ");
		myDb.insertContentData("Protecting Yourself", 6, "Technical Controls", "- Phishing simulation tests – process in which organisations purposely send deceptive, malicious emails in a controlled environment. In order to gauge the reactions of employees.");
		myDb.insertContentData("Protecting Yourself", 6, "Technical Controls", "- DLP solutions – type of strategy in which the organisation makes sure end users do not send sensitive or critical information outside the corporate network.");
		myDb.insertContentData("Protecting Yourself", 6, "Technical Controls", "- Physical security – protection of hardware, software, networks and data from physical actions and events that may potentially lead to loss or damage to an enterprise’s network.");
		myDb.insertContentData("Protecting Yourself", 6, "Technical Controls", "- Policies/standards – organisations have formalised written documents outlining how the business can protect itself from threats.");
		myDb.insertContentData("Protecting Yourself", 6, "Technical Controls", "- Aggressive authentication – process in which an organisation implements authentication protocols, enhancing the difficulty of accessing the organisation’s mission critical assets. ");

		myDb.getWritableDatabase().setTransactionSuccessful();
		myDb.getWritableDatabase().endTransaction();
	}

	public static void insertWordData(DatabaseHelper myDb, Activity activity) {
		myDb.getWritableDatabase().beginTransaction();

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

		myDb.getWritableDatabase().setTransactionSuccessful();
		myDb.getWritableDatabase().endTransaction();


	}

	public static void insertStoryData(DatabaseHelper myDb, Activity activity) {

		myDb.getWritableDatabase().beginTransaction();

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

		//story 2
		myDb.insertStoryData(1, 0, "You are browsing Instagram and see an Influencer’s post about a raffle for a pair of sneakers. They are partnering with a website and all you need to do is register on their website and share a post tagging the company. What looks suspicious to you?", "The link looks suspicious", "The shoes look fake", "The name sounds wrong", "There's no likes or comments");
		myDb.insertStoryData(1, 1, "Congratulations you’ve identified the red flags that this scheme is presenting. By avoiding this scam, your personal information remains safe.", "", "", "", "");
		myDb.insertStoryData(1, 2, "You’ve decided to join the raffle and you apply. A few weeks pass and it seems like you have not won the raffle. The raffle company however send you promotional spam to purchase their products. You’ve been added to their advertising list.", "", "", "", "");

		myDb.insertStoryData(1, 3, "You continue to browse and your friend messages you asking for your Netflix password so that they can watch a movie. Is there any inherent risk in you sharing this?", "I won't share my password. It's risky.", "Sure, I trust my friend so I'll give him my password.", "", "");
		myDb.insertStoryData(1, 4, "You decide to share your password. By doing this, there is a risk of your friend sharing your password to others. And thus your Netflix account has been compromised. ", "", "", "", "");
		myDb.insertStoryData(1, 5, "Your Netflix account is safe and you keep browsing with peace of mind.", "", "", "", "");

		myDb.insertStoryData(1, 6, "You continue to browse Instagram and you decide to post a photo. Somebody comments on your photo saying they really like your photo and would like to collaborate with you. They message you privately and go into details about the collaboration. They want you to download an application, and have you post your content there to become a “creator”. Do you want to download the app?", "Yes", "No", "", "");
		myDb.insertStoryData(1, 7, "The app you download takes your information and shares it with third party companies. Companies will now send you constant spam emails and your identity is at risk of fraud. Be wary of what applications you download.", "", "", "", "");
		myDb.insertStoryData(1, 8, "Researching further into the application you see that the application is only new. They want to gain revenue and are attracting people to use their app whilst also sharing their private information to third party companies.", "", "", "", "");

		myDb.insertStoryData(1, 9, "You continue to browse. There is an advertisement for a sale going on a website. You do not want to see this as this website seems unsafe. Do you know how to prevent these advertisements from showing on your feed?", "Yes, click on the post and select Why am I seeing this ad and then Hide all ads from this advertiser.", "Nope, I'll be fine.", "", "");
		myDb.insertStoryData(1, 10, "Congratulations you are cyber secure and you are aware of the risks of using apps such as Instagram.", "", "", "", "");
		myDb.insertStoryData(1, 11, "You could change the privacy settings on your social media accounts or request to not see ads like this. Take a look at the learning module to find out about how you can browse social media with low risk.", "", "", "", "");

		//story 3

		myDb.insertStoryData(2, 0, "Background: You are the IT director finalising production for the Cyber Citizen game you are about to release. The purpose of this app is to revolutionise the cybersecurity mobile market with interactive games and information targeting younger audiences with negligent digital practises. Additionally industrial awards and certificates can be gained whilst progressing through the app e.g. LinkedIn badges, thus security and integrity of the app must be maintained. However prior to the game launch you are informed of some security issues that have been replicated by system engineers.\n" +
				"Instructions: The success of this app launch will be in your hands with every decision affecting the security outcomes of both this game and our company. ", "Outsource auditing of the app code externally to a third-party org to identify security issues", "Work on fixing the security issues internally and postpone the launch of the app", "Finalise the release of the app and address security issues with future patches/updates", "");
		myDb.insertStoryData(2, 1, "By outsourcing code to external parties, our own IT team can focus on other priorities without wasting our own cycles. In selecting which party to perform the audit you are given three options:", "A relatively new, small and unproven company (1 cost)", "ITsa, recommended by an IT team member, a solid team with a proven track record (2 cost)", "TechFAST, popular market choice with a full team of professional programmers and experience (3)", "");
		myDb.insertStoryData(2, 2, "You’ve been informed of a potential security attack on some of your employees in the company. The IT team has discovered evidence pointing to some targeted social engineering. Attackers have sent emails pretending to be administrative and HR personnel asking the employees to renew their passwords. Fortunately the company’s security detection systems have blocked the attack after scanning for malicious URLs. However there is still a risk of social engineering performed to third-party auditors who have access to our internal systems. You have been advised to invest in security measures for external companies. You decide to:", "Implement multi-factor authentication to external parties (1)", "Extend and enforce internal cybersecurity policies to external teams (0)", "Segment the network (2)", "");
		myDb.insertStoryData(2, 3, "Your IT team expresses concern on the cloud data security of the company. Although your company follows general information security regulatory requirements, they are worried data leakage would jeopardise customer and business information. They strongly suggest encrypting your Intellectual Property against hackers. You decide to:", "Encrypt the Intellectual Property (2)", "Save costs as you’re compliant with information security standards", "", "");
		myDb.insertStoryData(2, 4, "Before the event launch of the app you meet with your marketing team to discuss how to promote the game. As you express security concerns you suggest delaying its launch to secure the game before it is released publicly. However your marketing director pressures you in releasing it as they’ve agreed to let some journalists explore the game. You compromise and decide to:", "Allow them to test the app in a controlled environment", "Give them a beta version of the app with limited functionality", "", "");
		myDb.insertStoryData(2, 5, "After agreeing with your marketing team in promoting the game, you invite a few groups of journalists over to your office where special devices are provided and monitored for preliminarytesting. After spending an hour testing and playing around with the app, a journalist from a top tier news company approaches you, praising the benefits of the app. However they would like to receive a take-home or beta version of the app where they can really trial the app in the real world. They suggest that this will allow them to write a proper review where they can explain all the benefits to people demographic. After some brief thought, you:", "Give them a beta version of the app with limited functionality", "State that nothing is able to leave the premises as the security of customers and the product is at risk, much to her disapproval", "", "");
		myDb.insertStoryData(2, 6, "Before the event launch of the app you meet with your marketing team to discuss how to promote the game. As you express security concerns you suggest delaying its launch to secure the game before it is released publicly. However your marketing director pressures you in releasing it as they’ve agreed to let some journalists explore the game. You compromise and decide to:", "Allow them to test the app in a controlled environment", "Give them a beta version of the app with limited functionality", "", "");
		myDb.insertStoryData(2, 7, "Whilst eating dinner during your day-off at home, your PR director calls you about an article circulating around the web. This document claims to have information on the source code and the repository including generic information on the cybersecurity app. Additionally the anonymous source claims there are numerous security faults with the app in which consumer data and privacy are at risk. You are also contacted by your IT team manager and ensures that the team is secure however there may still be internal leaks. You decide to:", "Call in a forensic investigation team while keeping the project on track for release", "Dismiss the false claims and let the PR team handle it", "Run a routine system check and continue the schedule for release", "");
		myDb.insertStoryData(2, 8, "On the day of the app launch you are woken up by a call in the middle of the night from your IT team manager. There has been a massive leak of consumer data with hundreds of posts circulating social media claiming their money and information have been stolen. Your team manager urges you to act fast as you:", "Tell him to shut all the servers to mitigate any more company data leakage", "Tell him to lock all accounts and start damage control.", "", "");
		myDb.insertStoryData(2, 9, "Congratulations! You have successfully prevented a cybersecurity attack from your competitors by prioritising the security of your customers and ensuring all safety procedures are followed. By securing the beta version of your Cyber Citizen app within your premises, risk of your technology falling into wrong hands is mitigated. Well done for being cyber safe!", "", "", "", "");
		myDb.insertStoryData(2, 10, "Your team manager attempts to manually pull shut down company servers by cutting the power to the systems. However this is already too late as hackers have accessed the internal databases stealing credentials of innocent people. Ultimately you have failed in successfully launching your Cyber Citizen app by not taking the proper cybersecurity precautions in previous scenarios. Have another go by starting another playthrough and see if you can get a positive ending!\n", "", "", "", "");
		myDb.insertStoryData(2, 11, "Your team manager attempts to lock all accounts and start damage control through PR tactics. However this is already too late as company information has been compromised and consumer trust has been lost. Ultimately you have failed in successfully launching your Cyber Citizen app by not taking the proper cybersecurity precautions in previous scenarios. Have another go by starting another playthrough and see if you can get a positive ending!\n", "", "", "", "");





		myDb.getWritableDatabase().setTransactionSuccessful();
		myDb.getWritableDatabase().endTransaction();


	}

	public static void insertScoreData(DatabaseHelper myDb, Activity activity) {
		myDb.getWritableDatabase().beginTransaction();

		myDb.insertScoreData("Achievements", 0);
		myDb.insertScoreData("Tests Taken", 0);
		myDb.insertScoreData("Coupon Progress", 0);
		myDb.insertScoreData("Badge Progress", 0);
		myDb.insertScoreData("Tokens", 0);
		myDb.insertScoreData("Amazon", 0);
		myDb.insertScoreData("KFC", 0);
		myDb.insertScoreData("Jb", 0);
		myDb.insertScoreData("Myer", 0);
		myDb.insertScoreData("Steam", 0);
		myDb.insertScoreData("Bunnings", 0);

		myDb.getWritableDatabase().setTransactionSuccessful();
		myDb.getWritableDatabase().endTransaction();

	}

	public static void insertAchievementData(DatabaseHelper myDb, Activity activity) {

		myDb.getWritableDatabase().beginTransaction();

		myDb.insertAchievementData("Cyber Novice I", "Complete the What is Cyber? Learning module", 0, 1, false);
		myDb.insertAchievementData("Cyber Novice II", "Complete the What is Cyber? Flashcards module", 0, 1, false);
		myDb.insertAchievementData("Cyber Novice III", "Complete the What is Cyber? Quiz module", 0, 1, false);
		myDb.insertAchievementData("Certified Cyber Novice", "Complete Level One: What is Cyber?", 0, 3, false);

		myDb.insertAchievementData("Cyber Skilled I", "Complete the Cyber 101 Learning module", 0, 1, false);
		myDb.insertAchievementData("Cyber Skilled II", "Complete the Cyber 101 Flashcards module", 0, 1, false);
		myDb.insertAchievementData("Cyber Skilled III", "Complete the Cyber 101 Quiz module", 0, 1, false);
		myDb.insertAchievementData("Cyber Skilled IV", "Complete the Cyber 101 Story 1 module", 0, 1, false);
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
		myDb.insertAchievementData("Abort Mission?", "Get 3 answers wrong in a row", 0, 1, false);
		myDb.insertAchievementData("Abandon Ship!", "Get 5 answers wrong in a row", 0, 1, false);
		myDb.insertAchievementData("Oh Baby a Triple!", "Get 3 answers correct in a row", 0, 1, false);
		myDb.insertAchievementData("Pentakill!", "Get 5 answers correct in a row", 0, 1, false);

		myDb.insertAchievementData("Instant Noodles!", "Get an answer correct within 1 second", 0, 1, false);
		myDb.insertAchievementData("Slick Speedster!", "Get an answer correct within 2 seconds", 0, 1, false);

		myDb.insertAchievementData("Smart Saver", "Save 5 words", 0, 5, false);
		myDb.insertAchievementData("Sophisticated Saver", "Save 20 words", 0, 20, false);
		myDb.insertAchievementData("Terrific Tester", "Take 10 Quizzes", 0, 10, false);
		myDb.insertAchievementData("Talented Tester", "Take 20 Quizzes", 0, 20, false);
		myDb.insertAchievementData("Tenacious Tester", "Take 30 Quizzes", 0, 30, false);

		myDb.getWritableDatabase().setTransactionSuccessful();
		myDb.getWritableDatabase().endTransaction();


	}

	private void enableBottomBar(boolean enable) {
		for (int i = 0; i < bottomBar.getMenu().size(); i++) {
			bottomBar.getMenu().getItem(i).setEnabled(enable);
		}
	}

	private static class initDatabase extends AsyncTask<Void, Void, Void> {

		WeakReference<MainActivity> activityWeakReference;

		DatabaseHelper myDb;
		long startTime;
		long finishTime;

		initDatabase(MainActivity activity) {
			activityWeakReference = new WeakReference<MainActivity>(activity);
			myDb = DatabaseHelper.getInstance(activity);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			startTime = System.currentTimeMillis();
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

			finishTime = System.currentTimeMillis();
			System.out.println(finishTime - startTime);

			super.onPostExecute(aVoid);
		}

	}

}
