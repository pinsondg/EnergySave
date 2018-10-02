package jmu.booze.energysave.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import jmu.booze.energysave.DeviceListeners;
import jmu.booze.energysave.R;
import jmu.booze.energysave.model.Device;
import jmu.booze.energysave.model.DeviceCollection;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceStatus extends Fragment implements DeviceListeners, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private Device device;
    private View rootView;

    public DeviceStatus() {
        device = new Device("Default", 100, 0, 4);
    }

    @SuppressLint("ValidFragment")
    public DeviceStatus(Device device) {
        this.device = device;
    }

    @SuppressLint("ValidFragment")
    public DeviceStatus(int position ) {
        device = DeviceCollection.getInstance().get(position);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_device_status, container, false);
        TextView title = rootView.findViewById(R.id.device_status_title);
        TextView usage = rootView.findViewById(R.id.device_status_usage);
        title.setText(device.getName());
        usage.setText(String.format("Live Usage:\t%.2f mW", device.getUsage()));
        TextView view2 = rootView.findViewById(R.id.device_status_usage_total);
        view2.setText(String.format("Total Usage:\t%.2f mW", device.getTotalUsage()));
        Switch onOff = rootView.findViewById(R.id.on_off_switch);
        onOff.setChecked(device.isDeviceOn());
        onOff.setOnCheckedChangeListener(this);
        Button button = rootView.findViewById(R.id.status_done_button);
        button.setOnClickListener(this);
        device.addListener(this);
        return rootView;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        device.removeListener(this);
    }

    @Override
    public void changeStatus() {
        rootView.post(new Runnable() {
            @Override
            public void run() {
                TextView view = rootView.findViewById(R.id.device_status_usage);
                view.setText(String.format("Live Usage:\t%.2f mW", device.getUsage()));
                TextView view2 = rootView.findViewById(R.id.device_status_usage_total);
                view2.setText(String.format("Total Usage:\t%.2f mW", device.getTotalUsage()));
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        device.toggle();
    }

    @Override
    public void onClick(View v) {
        this.getActivity().getSupportFragmentManager().beginTransaction().replace(
                R.id.fragment_holder, new DeviceListFragment()).commitNow();
    }
}
