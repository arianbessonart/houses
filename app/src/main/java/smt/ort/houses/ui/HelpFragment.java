package smt.ort.houses.ui;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import smt.ort.houses.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {


    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help, container, false);
        VideoView videoView = v.findViewById(R.id.help_video_view);

        final MediaController mediacontroller = new MediaController(getActivity());
        mediacontroller.setAnchorView(videoView);

        videoView.setMediaController(mediacontroller);
        videoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.example_video));
        videoView.start();

        return v;
    }

}
