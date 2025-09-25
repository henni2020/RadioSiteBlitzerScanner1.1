package com.example.radiositeblitzerscanner11.ui.settings;

import static com.example.radiositeblitzerscanner11.Constants.LOG_TAG;
import static com.example.radiositeblitzerscanner11.Constants.SERACHTERMS_COUNT;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.example.radiositeblitzerscanner11.R;
import com.example.radiositeblitzerscanner11.tools.AppTools;

import java.util.HashMap;


public class SettingsSub1FragSearchTerms extends Fragment {
    private View thisContextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_sub1_frag_search_terms, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.buttonSaveST).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        //Log.d(LOG_TAG, "OnClickListener  buttonSaveST " + "\n" );

                        HashMap<String, String> configs = readConfigsFromFormular( view );
                        AppTools.saveSearchTermsConfigs( configs, requireContext() );

                    }
                });

        HashMap<String, String> configs = AppTools.readSearchTermsConfigs( requireContext() );
        writeConfigsToForm(configs, view);                      // init formular with data


    }

    private void writeConfigsToForm(HashMap<String, String> configs, View view) {
        // Edittext auslesen
        EditText st00 = (EditText)view.findViewById(R.id.editSearchTerm00);
        EditText st01 = (EditText)view.findViewById(R.id.editSearchTerm01);
        EditText st02 = (EditText)view.findViewById(R.id.editSearchTerm02);
        EditText st03 = (EditText)view.findViewById(R.id.editSearchTerm03);
        EditText st04 = (EditText)view.findViewById(R.id.editSearchTerm04);
        EditText st05 = (EditText)view.findViewById(R.id.editSearchTerm05);
        EditText st06 = (EditText)view.findViewById(R.id.editSearchTerm06);
        EditText st07 = (EditText)view.findViewById(R.id.editSearchTerm07);
        EditText st08 = (EditText)view.findViewById(R.id.editSearchTerm08);
        EditText st09 = (EditText)view.findViewById(R.id.editSearchTerm09);

        st00.setText( configs.get("searchTerm00") );
        st01.setText( configs.get("searchTerm01") );
        st02.setText( configs.get("searchTerm02") );
        st03.setText( configs.get("searchTerm03") );
        st04.setText( configs.get("searchTerm04") );
        st05.setText( configs.get("searchTerm05") );
        st06.setText( configs.get("searchTerm06") );
        st07.setText( configs.get("searchTerm07") );
        st08.setText( configs.get("searchTerm08") );
        st09.setText( configs.get("searchTerm09") );
    }

    /**
     * Lese Werte aus Formular und bringe in HashMap
     * @param view
     * @return Config-HashMap
     */
    private HashMap<String, String> readConfigsFromFormular(View view) {

        // Edittext auslesen
        EditText st00 = (EditText)view.findViewById(R.id.editSearchTerm00);
        EditText st01 = (EditText)view.findViewById(R.id.editSearchTerm01);
        EditText st02 = (EditText)view.findViewById(R.id.editSearchTerm02);
        EditText st03 = (EditText)view.findViewById(R.id.editSearchTerm03);
        EditText st04 = (EditText)view.findViewById(R.id.editSearchTerm04);
        EditText st05 = (EditText)view.findViewById(R.id.editSearchTerm05);
        EditText st06 = (EditText)view.findViewById(R.id.editSearchTerm06);
        EditText st07 = (EditText)view.findViewById(R.id.editSearchTerm07);
        EditText st08 = (EditText)view.findViewById(R.id.editSearchTerm08);
        EditText st09 = (EditText)view.findViewById(R.id.editSearchTerm09);

        // Werte in Array
        String[] txt = new String[SERACHTERMS_COUNT];
        int i=0;
        txt[i++] = st00.getText().toString().trim();
        txt[i++] = st01.getText().toString().trim();
        txt[i++] = st02.getText().toString().trim();
        txt[i++] = st03.getText().toString().trim();
        txt[i++] = st04.getText().toString().trim();
        txt[i++] = st05.getText().toString().trim();
        txt[i++] = st06.getText().toString().trim();
        txt[i++] = st07.getText().toString().trim();
        txt[i++] = st08.getText().toString().trim();
        txt[i++] = st09.getText().toString().trim();

        /*
        String t01 = st01.getText().toString().trim();
        String t02 = st02.getText().toString().trim();
        String t03 = st03.getText().toString().trim();
        String t04 = st04.getText().toString().trim();
        String t05 = st05.getText().toString().trim();
        String t06 = st06.getText().toString().trim();
        String t07 = st07.getText().toString().trim();
        String t08 = st08.getText().toString().trim();
        String t09 = st09.getText().toString().trim();
        String t10 = st10.getText().toString().trim();
        */

        String key="";
        int keyCounter = 0;
        HashMap<String, String> configs = new HashMap<String, String>();

//        for( i=0; i<textCount; i++ ) {
//            Log.d(LOG_TAG, "txt[i]=" + txt[i] + "\n");
//        }

        // Werte bereinigt in config Hashmap
        for( i=0; i<SERACHTERMS_COUNT; i++ ) {
            if( !txt[i].isEmpty() ) {
                key = String.format("searchTerm%02d", keyCounter++ );
                configs.put(key, txt[i]);

                //Log.d(LOG_TAG, "a-key=" + key + " txt[i]="+ txt[i] + "\n");
            }
        }

        // freie Plätze mit Leerstrings auffüllen
        for( i=keyCounter; i<SERACHTERMS_COUNT; i++ ) {
            key = String.format("searchTerm%02d", keyCounter++);
            configs.put(key, "");
            // Log.d(LOG_TAG, "b-key=" + key + " #empty#"+ "\n");
        }

        /*
            So könnte config-HashMap im Log aussehen.

            a-key=searchTerm00 txt[i]=aa
            a-key=searchTerm01 txt[i]=bbb
            a-key=searchTerm02 txt[i]=cccc

            b-key=searchTerm03 #empty#
            b-key=searchTerm04 #empty#
            b-key=searchTerm05 #empty#
            b-key=searchTerm06 #empty#
            b-key=searchTerm07 #empty#
            b-key=searchTerm08 #empty#
            b-key=searchTerm09 #empty#
         */

        return configs;
    }

}

