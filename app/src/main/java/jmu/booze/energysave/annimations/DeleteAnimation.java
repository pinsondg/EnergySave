package jmu.booze.energysave.annimations;

import android.animation.Animator;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import jmu.booze.energysave.model.Device;
import jmu.booze.energysave.model.DeviceCollection;

public class DeleteAnimation implements View.OnClickListener, Animator.AnimatorListener {


    private ListView view;
    private List<Device> list;
    private int position;

    public DeleteAnimation(ListView view, List<Device> list, int position ) {
        this.list = list;
        this.view = view;
        this.position = position;
    }
    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        ((ArrayAdapter<Device>)view.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    @Override
    public void onClick(View view) {
        if ( list instanceof DeviceCollection) {
            animate(view);
            list.remove(position);
        } else {
            animate(view);
            DeviceCollection.getInstance().remove(this.view.getAdapter().getItem(position));
            list.remove(position);
        }
    }

    private void animate(View view) {
        if ( position == list.size() - 1 ) {
            ((View)view.getParent().getParent()).animate().alpha(0).setDuration(200).setListener(this);
        } else {
            ((View)view.getParent().getParent()).animate().alpha(0).setDuration(200);
            for ( int i = position + 1; i < list.size(); i++ ) {
                getViewByPosition(i).animate().translationYBy(-view.getHeight()).setDuration(300).setListener(this);
            }
        }
    }

    private View getViewByPosition(int pos) {
        final int firstListItemPosition = view.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + view.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return view.getAdapter().getView(pos, null, view);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return view.getChildAt(childIndex);
        }
    }
}
