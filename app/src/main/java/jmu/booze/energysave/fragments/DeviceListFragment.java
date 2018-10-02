package jmu.booze.energysave.fragments;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jmu.booze.energysave.DeviceListeners;
import jmu.booze.energysave.model.Device;
import jmu.booze.energysave.model.DeviceCollection;
import jmu.booze.energysave.DeviceListAdapter;
import jmu.booze.energysave.R;

public class DeviceListFragment extends Fragment implements TextWatcher,
        AbsListView.OnScrollListener, Animator.AnimatorListener, View.OnClickListener {

    private View rootView;
    private EditText editText;
    private int oldScrollY;
    private boolean isHidden = false;
    private boolean animationEnded = true;
    private List<Device> new_list = new ArrayList<Device>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_fragment, container, false);
        ImageButton addButton = (ImageButton) rootView.findViewById(R.id.add_button);
        addButton.setOnClickListener(this);
        try {
            editText = (EditText) rootView.findViewById(R.id.search_bar);

            //editText.setOnKeyListener(this);
            editText.addTextChangedListener(this);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        createList(DeviceCollection.getInstance());
        return rootView;
    }

    @Override
    public void onResume() {
        createList(DeviceCollection.getInstance());
        super.onResume();
    }

    @SuppressLint("NewApi")
    private void createList(List<Device> DeviceCollection) {
        ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        //origHeight = listView.getHeight();
        listView.setAdapter( new DeviceListAdapter(this.getActivity(), DeviceCollection, this));
        listView.setOnScrollListener(this);
        listView.setFastScrollEnabled(true);
    }


    public List<Device> getNew_list() {
        return new_list;
    }

    public List<Device> search_for_string(String str) {
        new_list = DeviceCollection.getInstance();
        str = str.toLowerCase();
        if (!str.isEmpty()) {
            new_list = new ArrayList<Device>();
            Iterator<Device> i = DeviceCollection.getInstance().iterator();
            while (i.hasNext()) {
                Device temp = i.next();
                if ( temp.getName().contains(str) ) {
                    new_list.add(temp);
                }

            }
        }
        return new_list;
    }


    //@Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        //Log.i("TAG","KEY PRESSED");
        //Log.i("TAG", new Integer(keyEvent.getKeyCode()).toString());
        if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            //Log.i("TAG","ENNNTEEERRR PRESSED!");
            List<Device> results = search_for_string(editText.getText().toString());
            createList(results);
            notifyAdapterOfChange();
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //Log.i("TAG","KEY PRESSED");

        //Log.i("TAG","ENNNTEEERRR PRESSED!");
        List<Device> results = search_for_string(editText.getText().toString());
        createList(results);
        notifyAdapterOfChange();
    }

    private void notifyAdapterOfChange() {
        ListView listView = rootView.findViewById(R.id.list_view);
        DeviceListAdapter adapter = (DeviceListAdapter) listView.getAdapter();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onPause() {
        editText.clearFocus();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        editText.clearFocus();
        super.onDestroy();
    }

    public EditText getEditText() {
        return editText;
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ((View)absListView.getParent()).getLayoutParams();
        if ( oldScrollY < i && !isHidden && animationEnded) {
            ((View)editText.getParent()).animate().translationYBy( -editText.getHeight()).setDuration(300).setListener(this);
            ((View)absListView.getParent()).animate().yBy(-editText.getHeight()).setDuration(300);
            params.bottomMargin = -editText.getMeasuredHeight();
            ((View)absListView.getParent()).setLayoutParams(params);
            isHidden = true;
        } else if ( oldScrollY > i && isHidden && animationEnded ){
            ((View)editText.getParent()).animate().translationYBy( editText.getHeight()).setDuration(300).setListener(this);
            ((View)absListView.getParent()).animate().yBy(editText.getHeight()).setDuration(300);
            ((View)absListView.getParent()).setLayoutParams(params);
            isHidden = false;
        }
        oldScrollY = i;
    }

    @Override
    public void onAnimationStart(Animator animator) {
        animationEnded = false;
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        animationEnded = true;
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    @Override
    public void onClick(View v) {
         FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
         transaction.add(R.id.fragment_holder, new AddDevice(
                 this));
         transaction.commitNow();
    }



    /* Experiment
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ((View)view.getParent()).getLayoutParams();
        if ( !isHidden ) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    y0 = motionEvent.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float y = motionEvent.getY();
                    if ( ((View)view.getParent()).getY() >= 0 && y < y0 ) {
                        System.out.println(y);
                        ((View)editText.getParent()).animate().translationYBy( y - y0 ).setDuration(0);
                        ((View)view.getParent()).animate().yBy( y - y0 ).setDuration(0);
                        //params.bottomMargin = -editText.getMeasuredHeight();
                        //((View)view.getParent()).setLayoutParams(params);
                    } else if ( view.getY() <= 0 && y > y0 ) {
                        ((View)editText.getParent()).animate().translationYBy(y - y0).setDuration(0);
                        ((View)view.getParent()).animate().yBy(y - y0).setDuration(0);
                    }
                    y0 = y;
                    break;
            }
        }
        return false;
    }
    */
}
