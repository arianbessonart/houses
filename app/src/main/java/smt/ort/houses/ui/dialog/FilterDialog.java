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
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import smt.ort.houses.R;
import smt.ort.houses.model.HouseFilters;


public class FilterDialog extends DialogFragment {

    private NoticeDialogListener listener;
    private HouseFilters houseFilters = new HouseFilters();

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
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            TextView minTextView = d.findViewById(R.id.min_price_text);
            TextView maxTextView = d.findViewById(R.id.max_price_text);
            minTextView.setText("0");
            maxTextView.setText("100000000");
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
            ImageView imageView = d.findViewById(R.id.clear_filter_dialog);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    listener.onDialogNegativeClick();
                }
            });

            TextView textView = d.findViewById(R.id.apply_button_dialog);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    listener.onDialogPositiveClick(houseFilters);
                }
            });

            SeekBar priceItem = d.findViewById(R.id.price_item_dialog);
            priceItem.setMin(10);
            priceItem.setMax(Integer.MAX_VALUE);
            priceItem.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    houseFilters.setPrice(String.valueOf(i));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

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
