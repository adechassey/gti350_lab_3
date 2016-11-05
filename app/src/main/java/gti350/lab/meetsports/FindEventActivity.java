package gti350.lab.meetsports;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

/**
 * Created by Antoine on 02/10/2016.
 * <p>
 * A big thanks to this tutorial:
 * https://androidhub.intel.com/en/posts/nglauber/Android_Search.html
 */

public class FindEventActivity extends AppCompatActivity implements PlaceSelectionListener {
    private static final String TAG = "FindEventActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_event);

        // Retrieve the PlaceAutocompleteFragment.
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        autocompleteFragment.setOnPlaceSelectedListener(this);
    }

    /**
     * Callback invoked when a place has been selected from the PlaceAutocompleteFragment.
     */
    @Override
    public void onPlaceSelected(Place place) {
        Log.i(TAG, "Place Selected: " + place.getName());

        // Saving results
        Intent intent = new Intent();
        intent.putExtra("place", place.getName());
        intent.putExtra("address", place.getAddress());
        intent.putExtra("id", place.getId());
        if (place.getPhoneNumber() != null)
            intent.putExtra("phone", place.getPhoneNumber());
        if (place.getWebsiteUri() != null)
            intent.putExtra("website", place.getWebsiteUri().toString());
        setResult(MainActivity.RESULT_OK, intent);
    }

    public void selectEvent(View view) {
        TextView category = (TextView) findViewById(R.id.event_category);
        TextView place = (TextView) findViewById(R.id.event_place);
        TextView address = (TextView) findViewById(R.id.event_address);

        Intent intent = new Intent();
        intent.putExtra("category", category.getText());
        intent.putExtra("place", place.getText());
        intent.putExtra("address", address.getText());
        setResult(MainActivity.RESULT_OK, intent);
        finish();
    }

    /**
     * Callback invoked when PlaceAutocompleteFragment encounters an error.
     */
    @Override
    public void onError(Status status) {
        Log.e(TAG, "onError: Status = " + status.toString());

        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Helper method to format information about a place nicely.
     */
    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }
}
