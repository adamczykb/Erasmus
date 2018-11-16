package zsp.erasmus;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import zsp.erasmus.Fragments.Member;
import zsp.erasmus.Fragments.SettingsActivity;
import zsp.erasmus.Fragments.admin;
import zsp.erasmus.Fragments.fragment_twinspace;
import zsp.erasmus.Fragments.journal_scrj;

public class Menu_Updates extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String[] data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__updates);
        Toolbar toolbar = (Toolbar) findViewById(R.id.frgmntbar);
        setSupportActionBar(toolbar);



        Intent intent = getIntent();

        data=intent.getStringArrayExtra("dane");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        TextView team = (TextView)header.findViewById(R.id.TeamNav);
        TextView usnrn = (TextView)header.findViewById(R.id.Surnamenav);
        TextView email = (TextView)header.findViewById(R.id.EmailViewnav);
        email.setText(data[3]);
        usnrn.setText(data[1]+ " "+data[2]);
        team.setText(data[4]);
        displaySelectedScreen(R.id.nav_lastup);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        SharedPreferences.Editor sharedPreferences = preferences.edit();
        sharedPreferences.putString("example_text",data[1]);
        sharedPreferences.putString("edit_text_preference_1",data[2]);
        sharedPreferences.putString("edit_text_preference_3",data[3]);
        sharedPreferences.putString("edit_text_preference_5",data[4]);
        sharedPreferences.putString("edit_text_preference_2",data[8]);
        sharedPreferences.putString("edit_text_preference_4",data[9]);
        sharedPreferences.putString("edit_text_preference_6",data[0]);

        sharedPreferences.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }
    public static String[] datGet(){
        return data;
    }
    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_lastup:
                fragment = new journal_scrj();
                setTitle("Journal update");
                break;
            case R.id.nav_twin:
                Bundle bundle=new Bundle();
                bundle.putString("link","https://twinspace.etwinning.net/unauthorized");

                fragment = new fragment_twinspace();
                fragment.setArguments(bundle);
                break;
            case R.id.nav_download:
                    String urlString="http://www.serwer1727017.home.pl/2ti/erasmusapp/index.php";
                    Intent intents=new Intent(Intent.ACTION_VIEW,Uri.parse(urlString));
                    intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intents.setPackage("com.android.chrome");
                    try {
                        startActivity(intents);
                    } catch (ActivityNotFoundException ex) {
                        // Chrome browser presumably not installed so allow user to choose instead
                        intents.setPackage(null);
                        startActivity(intents);
                    }

                break;
            case R.id.nav_froum:
                fragment = new Forum();
                setTitle("Forum");
                break;
            case R.id.nav_web:
                Bundle bundled=new Bundle();
                bundled.putString("link","http://erasmus.zspwrzesnia.pl");

                fragment = new fragment_twinspace();
                fragment.setArguments(bundled);
                break;
            case R.id.nav_memb:
                fragment = new Member();

                break;
            case R.id.nav_school:
                fragment = new Info();

                break;
            case R.id.nav_supp:
                Intent testIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?subject=" + "Problem ID:"+ this.data[0] + "&body=" + "//Question/problem//" + "&to=" + "2ti@zspwrzesnia.pl");
                testIntent.setData(data);
                startActivity(testIntent);
                break;

            case R.id.nav_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_admin:
                if(Integer.valueOf(this.data[7])==1) {
                fragment = new admin();
                }else
                {
                    Toast.makeText(this,"This function is only for superuser",Toast.LENGTH_LONG);
                }

                break;
            default:
                Toast.makeText(this,"This function will be available soon",Toast.LENGTH_LONG).show();

                break;

        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

}
