package com.example.radiositeblitzerscanner11.ui.blitzer;

import static com.example.radiositeblitzerscanner11.Constants.LOG_TAG;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.radiositeblitzerscanner11.MainActivity;
import com.example.radiositeblitzerscanner11.NotificationHelper;
import com.example.radiositeblitzerscanner11.NotificationSender;
import com.example.radiositeblitzerscanner11.R;
import com.example.radiositeblitzerscanner11.databinding.FragmentBlitzerBinding;
import com.example.radiositeblitzerscanner11.tools.AppTools;
import com.example.radiositeblitzerscanner11.tools.BlitzerParser;
import com.example.radiositeblitzerscanner11.tools.BlitzerParserFilter;

public class BlitzerFragment extends Fragment {

    private FragmentBlitzerBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BlitzerViewModel homeViewModel =
                new ViewModelProvider(this).get(BlitzerViewModel.class);

        binding = FragmentBlitzerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        TextView tw = (TextView)root.findViewById(R.id.textViewResults);
        tw.setMovementMethod(new ScrollingMovementMethod());


        root.findViewById(R.id.buttonScanNow).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        String toastMsg = getString(R.string.txtScanNow)+" "+AppTools.getServiceURLBlitzer();
                        Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();

                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                BlitzerParser bp = new BlitzerParser(AppTools.getServiceURLBlitzer());
                                String resultText = bp.parseCode();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //  Log.d(LOG_TAG, Thread.currentThread().getStackTrace()[2].getClassName()+" ("+Thread.currentThread().getStackTrace()[2].getLineNumber()+")"+"\n" );
                                        TextView tw = root.findViewById(R.id.textViewResults);
                                        tw.setText(resultText);
                                    }
                                });
                            }
                        });
                        t.start();



                    }
                });

        root.findViewById(R.id.buttonScanNowFiltered).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        String toastMsg = getString(R.string.txtScanNow)+" "+AppTools.getServiceURLBlitzer();
                        Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();

                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                BlitzerParserFilter bp = new BlitzerParserFilter( AppTools.getServiceURLBlitzer() );

                                bp.addSearchTerm( AppTools.readConfigurationString( requireContext(),"searchTerm00" ) );
                                bp.addSearchTerm( AppTools.readConfigurationString( requireContext(),"searchTerm01" ) );
                                bp.addSearchTerm( AppTools.readConfigurationString( requireContext(),"searchTerm02" ) );
                                bp.addSearchTerm( AppTools.readConfigurationString( requireContext(),"searchTerm03" ) );
                                bp.addSearchTerm( AppTools.readConfigurationString( requireContext(),"searchTerm04" ) );
                                bp.addSearchTerm( AppTools.readConfigurationString( requireContext(),"searchTerm05" ) );
                                bp.addSearchTerm( AppTools.readConfigurationString( requireContext(),"searchTerm06" ) );
                                bp.addSearchTerm( AppTools.readConfigurationString( requireContext(),"searchTerm07" ) );
                                bp.addSearchTerm( AppTools.readConfigurationString( requireContext(),"searchTerm08" ) );
                                bp.addSearchTerm( AppTools.readConfigurationString( requireContext(),"searchTerm09" ) );

                                String resultText = bp.parseCode();

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Log.d(LOG_TAG, Thread.currentThread().getStackTrace()[2].getClassName()+" ("+Thread.currentThread().getStackTrace()[2].getLineNumber()+")"+"\n" );

                                        TextView tw = root.findViewById(R.id.textViewResults);
                                        tw.setText(resultText);
                                    }
                                });
                            }
                        });
                        t.start();





                    }
                });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}