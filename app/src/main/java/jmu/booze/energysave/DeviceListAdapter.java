package jmu.booze.energysave;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import jmu.booze.energysave.annimations.DeleteAnimation;
import jmu.booze.energysave.annimations.SlidingSpringAnimation;
import jmu.booze.energysave.fragments.DeviceListFragment;
import jmu.booze.energysave.model.Device;
import jmu.booze.energysave.model.DeviceCollection;

public class DeviceListAdapter extends ArrayAdapter<Device> {

    private DeviceListFragment fragment;
    private List<Device> list;
    private View rootView;

    public DeviceListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public DeviceListAdapter(Context contest, List<Device> deviceCollection, DeviceListFragment deviceListFragment) {
        super( contest, 0, deviceCollection);
        fragment = deviceListFragment;
        list = deviceCollection;
    }

    /**
     * Turns the items of a list into the view items of the pothole list.
     * @param position the position in the list
     * @param convertView the return view
     * @param parent the parent view group
     * @return the view to display
     */
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        if ( !list.isEmpty() ) {
            final Device device = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.device_list, parent, false);
            rootView = convertView;
            Button button = convertView.findViewById(R.id.del_button);
            LinearLayout linearLayout = convertView.findViewById(R.id.liner_layout);
            SlidingSpringAnimation animation = new SlidingSpringAnimation(button, linearLayout, SlidingSpringAnimation.RIGHT_TO_LEFT, fragment, DeviceCollection.getInstance().get(position));
            button.setOnClickListener(new DeleteAnimation((ListView) fragment.getView().findViewById(R.id.list_view), list , position));
            linearLayout.setOnTouchListener(animation);
            this.registerDataSetObserver(animation);
            TextView name = convertView.findViewById(R.id.device_name);
            TextView serialNumber = convertView.findViewById(R.id.device_serial_num);
            TextView status = convertView.findViewById(R.id.status);
            name.setText(device.getName());
            if ( device.isDeviceOn() ) {
                serialNumber.setText("ON");
            } else {
                serialNumber.setText("OFF");
            }
            status.setText(String.format("%.2f mW", device.getUsage()));
            // Return the completed view to render on screen
        }
        return convertView;
    }

}
