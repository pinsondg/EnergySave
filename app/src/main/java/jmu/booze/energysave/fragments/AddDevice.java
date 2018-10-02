package jmu.booze.energysave.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jmu.booze.energysave.DeviceListAdapter;
import jmu.booze.energysave.R;
import jmu.booze.energysave.model.Device;
import jmu.booze.energysave.model.DeviceCollection;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddDevice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDevice extends Fragment implements View.OnClickListener {


    private DeviceListFragment deviceListFragment;
    private View rootView;
    private Button buttonCnacel;
    private Button buttonDone;
    private EditText nameField;
    private EditText serialNumberField;


    public AddDevice() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public AddDevice(DeviceListFragment deviceListFragment) {
        this.deviceListFragment = deviceListFragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddDevice.
     */
    // TODO: Rename and change types and number of parameters
    public static AddDevice newInstance(DeviceListFragment parentFragment) {
        AddDevice fragment = new AddDevice();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_device, container, false);
        buttonCnacel = rootView.findViewById(R.id.cancel_add_button);
        buttonDone = rootView.findViewById(R.id.done_add_button);
        buttonCnacel.setOnClickListener(this);
        buttonDone.setOnClickListener(this);
        nameField = rootView.findViewById(R.id.edit_name);
        serialNumberField = rootView.findViewById(R.id.edit_serial_num);
        return rootView;
    }

    @Override
    public void onClick(View v) {

        if (v.equals(buttonDone)) {
            if ( nameField.getText().toString().isEmpty()
                    || serialNumberField.getText().toString().isEmpty() ) {
                printErrorMessage("Please enter a valid name and serial number.");
                return;
            } else {
                try {
                    Device device = new Device(nameField.getText().toString(),
                            Long.parseLong(serialNumberField.getText().toString()), 0, 4);
                    DeviceCollection.getInstance().add(device);
                } catch (NumberFormatException e) {
                    printErrorMessage("Please enter valid serial number.");
                    return;
                }
            }

        }
        changeFragment();
    }

    private void printErrorMessage( String message ) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void changeFragment() {
        FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_holder, new DeviceListFragment()).commitNow();
    }
}
