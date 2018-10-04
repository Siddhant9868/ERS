package singh.siddhant.project.ers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Jauhar xlr on 4/18/2016.
 * mycreativecodes.in
 */

public class MainActivity extends AppCompatActivity implements PassbookActivity.OnListFragmentInteractionListener{
    DrawerLayout myDrawerLayout;
    NavigationView myNavigationView;
    FragmentManager myFragmentManager;
    FragmentTransaction myFragmentTransaction;
    Toolbar toolbar;
    /*private TextView username;
    private TextView userid;*/
    public GoogleApiClient mGoogleApiClient;
    public String UserEmail;
    public String UserName;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         *Setup the DrawerLayout and NavigationView
         */

        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        myNavigationView = (NavigationView) findViewById(R.id.nav_drawer) ;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        UserEmail = intent.getStringExtra("Email");
        UserName = intent.getStringExtra("UserName");

        View hView =  myNavigationView.getHeaderView(0);
        TextView username = (TextView) hView.findViewById(R.id.username);
        TextView userid = (TextView) hView.findViewById(R.id.userid);
        username.setText(UserName);
        userid.setText(UserEmail);

        /*userid = findViewById(R.id.userid);
        username = findViewById(R.id.username);
        Intent intent = getIntent();
        String UserEmail = intent.getStringExtra("Email");
        String UserName = intent.getStringExtra("UserName");
        userid.setText(UserEmail);
        username.setText(UserName);*/

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the HomeFragment as the first Fragment
         */
             myFragmentManager = getSupportFragmentManager();
             myFragmentTransaction = myFragmentManager.beginTransaction();
             myFragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

             myNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                 @Override
                 public boolean onNavigationItemSelected(MenuItem selectedMenuItem) {
                     myDrawerLayout.closeDrawers();

                     if (selectedMenuItem.getItemId() == R.id.nav_item_home) {
                         FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
                         fragmentTransaction.replace(R.id.containerView, new HomeFragment(),"Home").addToBackStack("Home").commit();
                     }

                     if (selectedMenuItem.getItemId() == R.id.nav_item_developer) {
                         FragmentTransaction xfragmentTransaction = myFragmentManager.beginTransaction();
                         xfragmentTransaction.replace(R.id.containerView, new DeveloperFragment(),"Developer").addToBackStack("Developer").commit();
                     }

                     if (selectedMenuItem.getItemId() == R.id.nav_item_joinus) {
                         FragmentTransaction yfragmentTransaction = myFragmentManager.beginTransaction();
                         yfragmentTransaction.replace(R.id.containerView, new JoinusFragment(),"Join Us").addToBackStack("Join Us").commit();
                     }

                     if (selectedMenuItem.getItemId() == R.id.monthly) {
                         FragmentTransaction zfragmentTransaction = myFragmentManager.beginTransaction();
                         zfragmentTransaction.replace(R.id.containerView, new MonthlyFragment(),"Expenditure Cap").addToBackStack("Expenditure Cap").commit();
                     }

                     if (selectedMenuItem.getItemId() == R.id.logout) {
                         Log.d("MainActivity00","logout pressed");

                         mAuth = FirebaseAuth.getInstance();
                         mAuth.signOut();
                         Intent i = new Intent(getApplicationContext(),MainActivity0.class);
                         startActivity(i);
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                 new ResultCallback<Status>() {
                                     @Override
                                     public void onResult(Status status) {
                                         // ...
                                         Toast.makeText(getApplicationContext(),"Thank You for Using ERS", Toast.LENGTH_SHORT).show();
                                         Intent i = new Intent(getApplicationContext(),MainActivity0.class);
                                         startActivity(i);
                                     }
                                 });
                     }
                     return false;
                 }
             });

        /*myFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            final int newBackStackLength = myFragmentManager.getBackStackEntryCount() + 1;

            @Override
            public void onBackStackChanged() {
                int nowCount = myFragmentManager.getBackStackEntryCount();
                if (newBackStackLength != nowCount) {
                    // we don't really care if going back or forward. we already performed the logic here.
                    myFragmentManager.removeOnBackStackChangedListener(this);
                    if ( newBackStackLength > nowCount ) { // user pressed back
                        myFragmentManager.popBackStackImmediate();
                    }
                }
            }
        });*/


        /**
         * Setup Drawer Toggle of the Toolbar
         */

                android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
                ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

                myDrawerLayout.setDrawerListener(mDrawerToggle);

                mDrawerToggle.syncState();
    }

    @Override
    public void onListFragmentInteraction(Contact item) {

    }

    private boolean exit = false;

    @Override
    public void onBackPressed() {

        if (myDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            myDrawerLayout.closeDrawer(GravityCompat.START);

        } else if(myFragmentManager.getBackStackEntryCount() == 0){
            if (exit) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);// finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);
            }
        }
        else{
            Handler uiHandler = new Handler();
            uiHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    myFragmentManager.popBackStackImmediate();
                    myFragmentManager.executePendingTransactions();
                }
            });


            //FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
            //fragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();
        }
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }

    public void join_us(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String[] recipients={"expensesreviewsystem@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,"ERS  |  Connect");
        intent.putExtra(Intent.EXTRA_TEXT,"Hey Developers!\n" +
                "This is " + UserName + " from " + UserEmail.substring(1,5) +  " batch.\n" +
                "I wanted to convey the following to you - ");
        intent.setType("text/html");
        intent.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(intent, "Send mail"));
    }
}