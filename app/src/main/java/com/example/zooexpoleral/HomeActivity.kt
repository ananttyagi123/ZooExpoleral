package com.example.zooexpoleral

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging

class HomeActivity : BaseActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var startAnimalSoundsGameButton: Button
    private lateinit var welcomeText: TextView
    private lateinit var eventNameTextView: TextView
    private lateinit var eventTimeTextView: TextView
    private lateinit var eventImageView: ImageView
    private lateinit var viewPager: ViewPager2
    private lateinit var planYourVisitButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        try {
            auth = FirebaseAuth.getInstance()
            db = FirebaseFirestore.getInstance()

            val toolbar = findViewById<Toolbar>(R.id.toolbar)
            setSupportActionBar(toolbar)

            drawerLayout = findViewById(R.id.drawer_layout)
            navigationView = findViewById(R.id.navigation_view)

            if (drawerLayout == null || navigationView == null) {
                Toast.makeText(this, "Navigation components not found", Toast.LENGTH_SHORT).show()
                return
            }

            toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            val headerView = navigationView.getHeaderView(0)
            val navUsername = headerView.findViewById<TextView>(R.id.navUsername)
            val navEmail = headerView.findViewById<TextView>(R.id.navEmail)



            viewPager = findViewById(R.id.homeViewPager)
            val images = listOf(
                R.drawable.img,
                R.drawable.img_3,
                R.drawable.img_1,
                R.drawable.img_2,
                R.drawable.img_4,
                R.drawable.img_5,
                R.drawable.img_6,
                R.drawable.img_7,
                R.drawable.img_8,
                R.drawable.img_9
            )

            val adapter = SliderAdapter(images)
            viewPager.adapter = adapter
            val handler = Handler(Looper.getMainLooper())
            var slidePosition = 0

            val runnable = object : Runnable {
                override fun run() {
                    if (slidePosition == images.size) slidePosition = 0
                    viewPager.currentItem = slidePosition++
                    handler.postDelayed(this, 3000)
                }
            }

            handler.post(runnable)

            checkUnreadNotifications()

            startAnimalSoundsGameButton = findViewById(R.id.btnAnimalSounds)
            startAnimalSoundsGameButton.setOnClickListener {
                val intent = Intent(this, AnimalSoundsGameActivity::class.java)
                startActivity(intent)
            }

            welcomeText = findViewById(R.id.welcomeText)


            val guessAnimalButton: LinearLayout = findViewById(R.id.guessAnimalButton)

            guessAnimalButton.setOnClickListener {
                val intent = Intent(this, GuessAnimalActivity::class.java)
                startActivity(intent)
            }

            val zooChatBotButton: LinearLayout = findViewById(R.id.zooChatBotButton)
            zooChatBotButton.setOnClickListener {
                val intent = Intent(this, ChatActivity::class.java)
                startActivity(intent)
            }


            val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> {
                            Toast.makeText(this@HomeActivity, "You're at Home", Toast.LENGTH_SHORT).show()
                        }
                        1 -> {
                            val intent = Intent(this@HomeActivity, MapActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        }
                        2 -> {
                            val intent = Intent(this@HomeActivity, AnimalInfoActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        }
                        3 -> {
                            val intent = Intent(this@HomeActivity, EventActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        }
                        4 -> {
                            val intent = Intent(this@HomeActivity, RealTimeUpdatesActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> {
                            Toast.makeText(this@HomeActivity, "Home refreshed", Toast.LENGTH_SHORT).show()
                        }
                        1 -> {
                            Toast.makeText(this@HomeActivity, "Map refreshed", Toast.LENGTH_SHORT).show()
                        }
                        2 -> {
                            Toast.makeText(this@HomeActivity, "Animal info refreshed", Toast.LENGTH_SHORT).show()
                        }
                        3 -> {
                            Toast.makeText(this@HomeActivity, "Events refreshed", Toast.LENGTH_SHORT).show()
                        }
                        4 -> {
                            Toast.makeText(this@HomeActivity, "Directions refreshed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })



            planYourVisitButton=findViewById(R.id.planTitle)
            planYourVisitButton.setOnClickListener {
                val intent = Intent(this, PlanYourVisitActivity::class.java)
                startActivity(intent)
            }


            eventNameTextView = findViewById(R.id.eventName)
            eventTimeTextView = findViewById(R.id.eventTime)
            eventImageView = findViewById(R.id.eventImage)

            navigationView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_profile -> startActivity(Intent(this, ProfileActivity::class.java))
                    R.id.nav_settings -> startActivity(Intent(this, Setting::class.java))
                    R.id.nav_about -> startActivity(Intent(this, AboutActivity::class.java))
                    R.id.nav_feedback -> startActivity(Intent(this, FeedbackActivity::class.java))
                    R.id.nav_logout -> logoutUser()
                }
                drawerLayout.closeDrawers()
                true
            }



            val user = auth.currentUser
            if (user != null) {
                navEmail?.text = user.email
                welcomeText?.text = "Welcome to ZooExplorer, Guest!"

                db.collection("users").document(user.uid).get()
                    .addOnSuccessListener { document ->
                        val name = document.getString("username")
                        if (!name.isNullOrEmpty()) {
                            navUsername?.text = name
                            welcomeText?.text = "Welcome, $name!"
                        } else {
                            navUsername?.text = "Guest"
                        }
                    }
                    .addOnFailureListener {
                        navUsername?.text = "Guest"
                    }
            } else {
                navUsername?.text = "Guest"
                navEmail?.text = "Not logged in"
                welcomeText?.text = "Welcome to ZooExplorer, Guest!"
            }

            registerFCMToken()

        } catch (e: Exception) {
            Log.e("HomeActivity", "Error in onCreate: ${e.message}")
            e.printStackTrace()
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun logoutUser() {
        auth.signOut()
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        finish()
    }

    private fun registerFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result?.let { saveTokenToFirestore(it) }
            } else {
                Log.e("FCM", "Token fetch failed", task.exception)
            }
        }
    }

    private fun saveTokenToFirestore(token: String) {
        auth.currentUser?.let { user ->
            db.collection("users").document(user.uid).update("fcmToken", token)
                .addOnSuccessListener { Log.d("FCM", "Token saved") }
                .addOnFailureListener { Log.e("FCM", "Token save failed", it) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val notificationItem = menu?.findItem(R.id.notification_background)
        val actionView = notificationItem?.actionView

        actionView?.setOnClickListener {
            onOptionsItemSelected(notificationItem)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.notification_background -> {
                startActivity(Intent(this, NotificationsActivity::class.java))
                true
            }
            else -> toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
        }
    }


    private fun checkUnreadNotifications() {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()
        val twoDaysAgo = System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000

        db.collection("users").document(user.uid)
            .collection("notifications")
            .whereGreaterThan("timestamp", twoDaysAgo)
            .whereEqualTo("read", false)
            .get()
            .addOnSuccessListener { docs ->
                val badge = findViewById<TextView>(R.id.badgeCount)
                val count = docs.size()

                if (count > 0) {
                    badge.text = count.toString()
                    badge.visibility = View.VISIBLE
                } else {
                    badge.visibility = View.GONE
                }
            }
    }

}
