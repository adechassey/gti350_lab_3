package gti350.lab.meetsports;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Antoine on 04/11/2016.
 */

public class EventFragment extends Fragment {
    private static final String TAG = "EventFragment";

    private TextView text_event_place;
    private TextView text_event_address;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event, container, false);
        Bundle args = getArguments();

        text_event_place = (TextView) v.findViewById(R.id.event_place);
        text_event_address = (TextView) v.findViewById(R.id.event_address);

        if (args != null) {
            String place = getArguments().getString("place");
            text_event_place.setText(place);
            String address = getArguments().getString("address");
            text_event_address.setText(address);
        } else {
            Log.d(TAG, "Bundle arguments are null");
        }

        return v;
    }
}
