package com.example.radiositeblitzerscanner11.ui.settings;

import static com.example.radiositeblitzerscanner11.Constants.LOG_TAG;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.radiositeblitzerscanner11.databinding.FragmentSettingsBinding;

import com.example.radiositeblitzerscanner11.R;


public class SettingsFragment extends Fragment {
    Button buttonSearchTerms, buttonAutoScan;

    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textNotifications;
        //notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // ----------------------------------

        buttonSearchTerms = (Button)  root.findViewById(R.id.buttonSearchTerms);
        buttonAutoScan = (Button)  root.findViewById(R.id.buttonAutoScan);

        buttonSearchTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // load First Fragment
                loadFragment(new SettingsSub1FragSearchTerms());
                //Log.d(LOG_TAG, Thread.currentThread().getStackTrace()[2].getClassName()+" ("+Thread.currentThread().getStackTrace()[2].getLineNumber()+")"
                //        +"\n--- 1 firstFragmentButton onClick"+"\n" );

            }
        });

        buttonAutoScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // load First Fragment
                loadFragment(new SettingsSub2FragAutoScan());
//                Log.d(LOG_TAG, Thread.currentThread().getStackTrace()[2].getClassName()+" ("+Thread.currentThread().getStackTrace()[2].getLineNumber()+")"
//                        +"\n--- 2 secondFragmentButton onClick"+"\n" );

            }
        });


        // ----------------------------------
        return root;
    }

    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        // FragmentManager fm = getFragmentManager();
        // FragmentManager fm = getSupportFragmentManager();
        FragmentManager fm = getChildFragmentManager();

// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}