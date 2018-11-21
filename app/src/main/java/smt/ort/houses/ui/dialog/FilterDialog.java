package smt.ort.houses.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import smt.ort.houses.R;
import smt.ort.houses.model.HouseFilters;


public class FilterDialog extends DialogFragment {

    private NoticeDialogListener listener;
    private HouseFilters filters;
    private Map<Integer, Button> buttons;


    public static FilterDialog newInstance(HouseFilters houseFilters) {

        Bundle args = new Bundle();
        args.putParcelable("filters", houseFilters);
        FilterDialog fragment = new FilterDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        try {
            this.listener = (NoticeDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            Log.w("FilterDialog", "Set listener to dialog, " + e);
        }
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_theme);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            filters = bundle.getParcelable("filters");
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            TextView minTextView = d.findViewById(R.id.min_price_text);
            TextView maxTextView = d.findViewById(R.id.max_price_text);
            minTextView.setText(HouseFilters.MIN_PRICE.toString());
            maxTextView.setText(HouseFilters.MAX_PRICE.toString());
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
            ImageView imageView = d.findViewById(R.id.clear_filter_dialog);
            imageView.setOnClickListener(view -> {
                dismiss();
                listener.onDialogNegativeClick();
            });

            TextView textView = d.findViewById(R.id.apply_button_dialog);
            textView.setOnClickListener(view -> {
                dismiss();
                listener.onDialogPositiveClick(filters);
            });

            SeekBar priceItem = d.findViewById(R.id.price_item_dialog);
            priceItem.setMin(HouseFilters.MIN_PRICE);
            priceItem.setMax(HouseFilters.MAX_PRICE);
            priceItem.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    filters.setPrice(String.valueOf(i));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            priceItem.setProgress(Integer.parseInt(filters.getPrice()), true);

            ////////////////////////////////////////////////////////////////////////////////////////////////

//            DiscreteSeekBar discreteSeekBar = d.findViewById(R.id.price_item_dialog_seekbar);
//            discreteSeekBar.setMin(HouseFilters.MIN_PRICE);
//            discreteSeekBar.setMax(HouseFilters.MAX_PRICE);
//            discreteSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
//                @Override
//                public void onProgressChanged(DiscreteSeekBar seekBar, int i, boolean fromUser) {
//                    filters.setPrice(String.valueOf(i));
//                }
//
//                @Override
//                public void onStartTrackingTouch(DiscreteSeekBar seekBar) {
//                }
//
//                @Override
//                public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
//                }
//            });
//            discreteSeekBar.setProgress(Integer.parseInt(filters.getPrice()));

            ////////////////////////////////////////////////////////////////////////////////////////////////

            Switch filterGarage = d.findViewById(R.id.filter_garage);
            Switch filterBarbecue = d.findViewById(R.id.filter_barbecue);

            filterGarage.setOnCheckedChangeListener((compoundButton, b) -> filters.setHasGarage(b ? true : null));
            filterGarage.setChecked(filters.getHasGarage() != null && filters.getHasGarage());

            filterBarbecue.setOnCheckedChangeListener((compoundButton, b) -> filters.setHasBarbecue(b ? true : null));
            filterBarbecue.setChecked(filters.getHasBarbecue() != null && filters.getHasBarbecue());

            buttons = new HashMap<Integer, Button>() {{
                put(1, d.findViewById(R.id.rooms_cant_1));
                put(2, d.findViewById(R.id.rooms_cant_2));
                put(3, d.findViewById(R.id.rooms_cant_3));
                put(4, d.findViewById(R.id.rooms_cant_4));
            }};

            if (filters.getRooms() != null) {
                buttons.get(filters.getRooms()).setSelected(true);
            }

            for (int i = 1; i <= 4; i++) {
                Button btn = buttons.get(i);
                int finalI = i;
                btn.setOnClickListener(view -> changeButtonState(btn, finalI));
            }
        }
    }

    private void changeButtonState(Button btn, int quantity) {
        if (filters.getRooms() == null) {
            btn.setSelected(true);
            filters.setRooms(quantity);
        } else if (filters.getRooms() == quantity) {
            btn.setSelected(false);
            filters.setRooms(null);
        } else {
            Button btnSelected = buttons.get(filters.getRooms());
            btnSelected.setSelected(false);
            btn.setSelected(true);
            filters.setRooms(quantity);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.filter_dialog, container);
    }

    public interface NoticeDialogListener {
        void onDialogPositiveClick(HouseFilters filters);

        void onDialogNegativeClick();
    }
}
