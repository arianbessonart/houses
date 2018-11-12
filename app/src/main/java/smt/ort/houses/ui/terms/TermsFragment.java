package smt.ort.houses.ui.terms;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import smt.ort.houses.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermsFragment extends Fragment {


    public TermsFragment() {
        // Required empty public constructor
    }

    public static TermsFragment newInstance() {
        return new TermsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_terms, container, false);
        WebView mWebView = v.findViewById(R.id.terms_webview);
        mWebView.loadUrl("http://173.233.86.183:8080/CursoAndroidWebApp/condiciones.html");

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());

        return v;
    }

}
