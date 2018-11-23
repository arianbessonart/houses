package smt.ort.houses.ui.housedetail;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import smt.ort.houses.R;
import smt.ort.houses.model.House;
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

    public HouseDetailFragment() {
        // Required empty public constructor
    }

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

        viewModel.getHouse().observe(this, resourceHouse -> {
            if (resourceHouse.getData() != null) {
                itemLoaded = true;
                getActivity().invalidateOptionsMenu();
                House newHouse = resourceHouse.getData();
                if (newHouse.getPhotos().size() > 0) {
                    viewPager.setAdapter(new HousePhotosAdapter(getActivity(), newHouse.getPhotos()));
                }
                house = resourceHouse.getData();
                subtitleTextView.setText(buildSubtitle(house));
                titleTextView.setText(house.getTitle());
                priceTextView.setText(StringUtil.formatCurrency(house.getPrice()));

                if (house.getFavorite()) {
                    favoriteBtn.setImageResource(R.drawable.baseline_favorite_24);
                } else {
                    favoriteBtn.setImageResource(R.drawable.baseline_favorite_border_24);
                }
            }
        });

        favoriteBtn.setOnClickListener(viewBtn -> {
            viewModel.toggleFavorite(house, !house.getFavorite());
        });

        shareBtn.setOnClickListener(viewBtn -> {
            initShareAction();
        });

        callBtn.setOnClickListener(viewBtn -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + "+59899000000"));
            startActivity(intent);
        });

        return view;
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
