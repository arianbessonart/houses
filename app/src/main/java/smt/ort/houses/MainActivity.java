package smt.ort.houses;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import smt.ort.houses.model.House;
import smt.ort.houses.model.User;
import smt.ort.houses.ui.HelpFragment;
import smt.ort.houses.ui.HomeFragment;
import smt.ort.houses.ui.LoginFragment;
import smt.ort.houses.ui.favorite.FavoritesFragment;
import smt.ort.houses.ui.housedetail.HouseDetailFragment;
import smt.ort.houses.ui.terms.TermsFragment;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnHouseSelectedListener, LoginFragment.LoginListener {

    DrawerLayout drawer;
    NavigationView navigationView;
    private Boolean isLandscape = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

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

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("login", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("userId", null) != null) {
            navigationView.getMenu().findItem(R.id.login_item).setVisible(false);
            navigationView.getMenu().findItem(R.id.logout_item).setVisible(true);
            navigationView.getMenu().findItem(R.id.favorite_item).setVisible(true);
        }
    }

    private void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass;

        switch (item.getItemId()) {
            case R.id.home_item:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.favorite_item:
                fragmentClass = FavoritesFragment.class;
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

        item.setChecked(true);
        goToFragment(fragment, item.getTitle().toString(), null);
        drawer.closeDrawers();
    }

    private void goToFragment(Fragment fragment, String title, String addToBackStack) {
        setTitle(title);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction;

        if (isLandscape) {
            transaction = fragmentManager.beginTransaction().replace(R.id.flContentDetail, fragment);
        } else {
            transaction = fragmentManager.beginTransaction().replace(R.id.flContent, fragment);
        }

        if (addToBackStack != null && !addToBackStack.isEmpty()) {
            transaction.addToBackStack(addToBackStack);
        } else {
            transaction.addToBackStack(null);
        }
        transaction.commit();
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
        if (isLandscape) {
            fragmentManager.beginTransaction().replace(R.id.flContentDetail, fragment).addToBackStack(null).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
        }
    }

    @Override
    public void onLoginSuccess(User user) {
        navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.login_item).setVisible(false);
        navigationView.getMenu().findItem(R.id.logout_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.favorite_item).setVisible(true);
        invalidateOptionsMenu();
        goToFragment(HomeFragment.newInstance(), getResources().getString(R.string.home), null);
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, HomeFragment.newInstance()).addToBackStack(null).commit();
        TextView textView = findViewById(R.id.nav_header_textView);
        textView.setText(user.getName());
        ImageView imageView = findViewById(R.id.nav_header_image);

        String profileUrl = "https://graph.facebook.com/" + user.getId() + "/picture?type=large";
        Picasso.get().load(profileUrl).placeholder(R.mipmap.ic_launcher).into(imageView);
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("login", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("userId", user.getId()).apply();
    }

    @Override
    public void onLoginCancel() {

    }

    @Override
    public void onLoginError(Exception e) {

    }

    @Override
    public void onLogoutSuccess() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.login_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.logout_item).setVisible(false);
        navigationView.getMenu().findItem(R.id.favorite_item).setVisible(false);
        invalidateOptionsMenu();
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("login", Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("userId").apply();
    }
}
