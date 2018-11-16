package smt.ort.houses;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.facebook.FacebookException;

import smt.ort.houses.model.House;
import smt.ort.houses.ui.HelpFragment;
import smt.ort.houses.ui.HomeFragment;
import smt.ort.houses.ui.LoginFragment;
import smt.ort.houses.ui.housedetail.HouseDetailFragment;
import smt.ort.houses.ui.terms.TermsFragment;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnHouseSelectedListener, LoginFragment.LoginListener {

    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        setupDrawer(navigationView);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, HomeFragment.newInstance()).commit();
    }

    private void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass;

        switch (item.getItemId()) {
            case R.id.home_item:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.terms_item:
                fragmentClass = TermsFragment.class;
                break;
            case R.id.help_video_item:
                fragmentClass = HelpFragment.class;
                break;
            case R.id.settings_item:
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            case R.id.login_item:
            case R.id.logout_item:
                fragmentClass = LoginFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        item.setChecked(true);
        setTitle(item.getTitle());
        drawer.closeDrawers();
    }

    private void setupDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            selectDrawerItem(menuItem);
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onHouseSelected(House house) {
        HouseDetailFragment fragment = new HouseDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("selected", house);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();

    }

    @Override
    public void onLoginSuccess() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.login_item).setVisible(false);
        navigationView.getMenu().findItem(R.id.logout_item).setVisible(true);
        invalidateOptionsMenu();
    }

    @Override
    public void onLoginCancel() {

    }

    @Override
    public void onLoginError(FacebookException e) {

    }

    @Override
    public void onLogoutSuccess() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.login_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.logout_item).setVisible(false);
        invalidateOptionsMenu();
    }
}
