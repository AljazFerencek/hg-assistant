package com.hivegarden.assistant.MyPlant;



import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.hivegarden.assistant.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PlantOverview extends Fragment {
    ListView logList;




    public PlantOverview() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_plant_overview, container, false);
        // Get ListView object from xml
        logList = (ListView) rootView.findViewById(R.id.listViewLogEntries);

        // Defined Array values to show in ListView
        String[] values = new String[] { "Š: 2 V: 3",
                "Š: 3 V: 5",
                "Š: 4 V: 8",
                "Š: 6 V: 12",
                "Š: 7 V: 15",
                "Š: 9 V: 20"
        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        logList.setAdapter(adapter);

        // ListView Item Click Listener

        logList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView <?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item value
                String  itemValue    = (String) logList.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getActivity(),
                        "Position :" + position + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();

            }

        });


        return rootView;
    }


}
