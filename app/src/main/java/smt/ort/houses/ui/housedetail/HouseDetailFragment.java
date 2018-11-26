package smt.ort.houses.ui.housedetail;


import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import smt.ort.houses.R;
import smt.ort.houses.model.House;
import smt.ort.houses.model.LocationAddress;
import smt.ort.houses.services.GeocodingLocation;
import smt.ort.houses.ui.adapter.HousePhotosAdapter;
import smt.ort.houses.util.StringUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class HouseDetailFragment extends Fragment {

    private final String DIVIDER = " | ";
    private House house;
    private HouseDetailViewModel viewModel;
    private Menu menu;
    private Boolean itemLoaded = false;

    private GoogleMap googleMap;
    private MapView mMapView;

    public HouseDetailFragment() {
        // Required empty public constructor
    }

    @SuppressLint("HandlerLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_house_detail, container, false);

        setHasOptionsMenu(true);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            house = bundle.getParcelable("selected");
            Log.d("HOUSES", house.getTitle());
        }
        final ViewPager viewPager = view.findViewById(R.id.viewPagerHouseDetailPhotos);

        HouseDetailViewModel.Factory factory = new HouseDetailViewModel.Factory(
                getActivity().getApplication(), house.getId());

        viewModel = ViewModelProviders.of(this, factory).get(HouseDetailViewModel.class);

        TextView subtitleTextView = view.findViewById(R.id.subtitle);
        TextView titleTextView = view.findViewById(R.id.title);
        TextView priceTextView = view.findViewById(R.id.price);
        ImageView favoriteBtn = view.findViewById(R.id.favorite_button);
        ImageView shareBtn = view.findViewById(R.id.share_button);

        ImageView callBtn = view.findViewById(R.id.call_button);

        TextView totalArea = view.findViewById(R.id.total_area_value);
        TextView bedrooms = view.findViewById(R.id.bedrooms_value);
        TextView bathrooms = view.findViewById(R.id.bathrooms_value);

        viewModel.getHouse().observe(this, resourceHouse -> {
            if (resourceHouse.getData() != null) {
                itemLoaded = true;
                getActivity().invalidateOptionsMenu();
                House newHouse = resourceHouse.getData();
                viewPager.setAdapter(new HousePhotosAdapter(getActivity(), newHouse.getPhotos()));
                house = resourceHouse.getData();
                subtitleTextView.setText(buildSubtitle(house));
                titleTextView.setText(house.getTitle());
                priceTextView.setText(StringUtil.formatCurrency(house.getPrice()));

                if (house.getFavorite()) {
                    favoriteBtn.setImageResource(R.drawable.baseline_favorite_24);
                } else {
                    favoriteBtn.setImageResource(R.drawable.baseline_favorite_border_24);
                }

                // Sections
                totalArea.setText(house.getSquareMeters() != null && !house.getSquareMeters().isEmpty() ? StringUtil.formatSquareMeters(house.getSquareMeters()) : "-");
//                bedrooms.setText(setFeaturesText(house.getSquareMeters()));
//                bathrooms.setText(setFeaturesText(house.getSquareMeters()));

                GeocodingLocation.getAddressFromLocation(house.getNeighborhood() + ", Uruguay", getContext(), new Handler() {
                    @Override
                    public void handleMessage(Message message) {
                        LocationAddress locationAddress;
                        if (message.what == 1) {
                            Bundle bundle = message.getData();
                            locationAddress = bundle.getParcelable(GeocodingLocation.BUNDLE_LOCATION_ADDRESS_KEY);
                            if (locationAddress != null) {
                                setCoordsAndMoveMap(locationAddress);
                            }
                        }
                    }
                });
            }
        });

        favoriteBtn.setOnClickListener(viewBtn -> viewModel.toggleFavorite(house, !house.getFavorite()));

        shareBtn.setOnClickListener(viewBtn -> initShareAction());

        callBtn.setOnClickListener(viewBtn -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + "+59899000000"));
            startActivity(intent);
        });

        mMapView = view.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(mMap -> googleMap = mMap);

        return view;
    }

    private String setFeaturesText(String value) {
        return value != null && value.isEmpty() ? value : "-";
    }

    private void setCoordsAndMoveMap(LocationAddress locationAddress) {
        LatLng houseLocation;
        if (locationAddress.getLat() == null || locationAddress.getLng() == null) {
            houseLocation = new LatLng(GeocodingLocation.DEFAULT_LAT, GeocodingLocation.DEFAULT_LNG);
        } else {
            houseLocation = new LatLng(locationAddress.getLat(), locationAddress.getLng());
        }

        googleMap.addMarker(new MarkerOptions().position(houseLocation).title(locationAddress.getLocation()));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(houseLocation).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private String buildSubtitle(House house) {
        return StringUtil.formatSquareMeters(house.getSquareMeters()) + " " + getResources().getString(R.string.total) + DIVIDER + house.getRooms() + " " + getResources().getString(R.string.rooms);
    }

    private void initShareAction() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, house.getTitle());
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "http://www.houses.com/houses/" + house.getId());
        startActivity(Intent.createChooser(shareIntent, "Share link!"));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_items_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (itemLoaded) {
            MenuItem item = menu.findItem(R.id.favorite_action_item);
            if (house.getFavorite()) {
                item.setIcon(R.drawable.baseline_favorite_24);
            } else {
                item.setIcon(R.drawable.baseline_favorite_border_24);
            }
            menu.setGroupVisible(R.id.main_menu_group, true);
        } else {
            menu.setGroupVisible(R.id.main_menu_group, false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite_action_item:
                viewModel.toggleFavorite(house, !house.getFavorite());
                return true;
            case R.id.share_action_item:
                initShareAction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
