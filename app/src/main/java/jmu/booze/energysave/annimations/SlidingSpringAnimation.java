package jmu.booze.energysave.annimations;

import android.database.DataSetObserver;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.v4.app.Fragment;
import android.util.AndroidRuntimeException;
import android.view.MotionEvent;
import android.view.View;

import jmu.booze.energysave.R;
import jmu.booze.energysave.fragments.DeviceStatus;
import jmu.booze.energysave.model.Device;

/**
 * A spring animation that reveals a hidden button when the user swipes over a view.
 *
 * @author Daniel Pinson
 * @version 1.0
 */
public class SlidingSpringAnimation extends DataSetObserver implements View.OnTouchListener {

    public static final int RIGHT_TO_LEFT = -1;
    public static final int LEFT_TO_RIGHT = 1;
    private int direction;
    private boolean isRevealed;
    private View button;
    private View view;
    private float x1;
    private Fragment fragment;
    private Device device;

    /**
     * Constructor for a sliding spring animation
     * @param button the hidden button to show
     * @param view the view to slide over
     * @param direction the direction of the slide - use the static finals
     * @param fragment the fragment the view is a part of
     */
    public SlidingSpringAnimation(View button, View view, int direction, Fragment fragment, Device device) {
        this.x1 = 0;
        this.button = button;
        this.direction = direction;
        isRevealed = false;
        this.fragment = fragment;
        this.view = view;
        this.device = device;
    }

    /**
     * Determines where to slide when the user touches or swipes the screen.
     *
     * @param view the view to slide
     * @param motionEvent the motion even the user inputs
     * @return true if executed, false if otherwise
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.view = view;
        if ( !isRevealed ) {
            SpringAnimation animation = new SpringAnimation(view, DynamicAnimation.TRANSLATION_X,
                    direction*(this.button.getMeasuredWidth()));
            switch(motionEvent.getAction())
            {
                case  MotionEvent.ACTION_DOWN:
                    x1 = motionEvent.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float x = motionEvent.getX();
                    if ( view.getX() >= -button.getWidth() && x < x1 ) {
                        //System.out.println(x);
                        view.animate().xBy(x - x1).setDuration(0);
                    } else if ( view.getX() <= 0 && x > x1 ) {
                        view.animate().xBy(x - x1).setDuration(0);
                    }
                    x1 = x;
                    break;
                case MotionEvent.ACTION_UP:
                    if ( view.getX() > -button.getWidth() && view.getX() != 0 ) {
                        sendBack(view);
                    } else if( view.getX() == 0 ) {
                        fragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, new DeviceStatus(device)).commitNow();
                        //TO_DO: what happens on a click
                    } else if( view.getX() <= -button.getWidth() ) {
                        animation.start();
                        isRevealed = true;
                    }
                    break;
                default:
                    if ( view.getX() < -button.getWidth() / 3 ) {
                        animation.start();
                        isRevealed = true;
                    } else {
                        sendBack(view);
                    }
                    break;
            }

        }
        else {
            switch (motionEvent.getAction())
            {
                case  MotionEvent.ACTION_DOWN:
                    x1 = motionEvent.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float x = motionEvent.getX();
                    if ( view.getX() >= -button.getWidth() && x < x1 ) {
                        //System.out.println(x);
                        view.animate().xBy(x - x1).setDuration(0);
                    } else if ( view.getX() <= 0 && x > x1 ) {
                        view.animate().xBy(x - x1).setDuration(0);
                    }
                    x1 = x;
                    break;
                case MotionEvent.ACTION_UP:
                    if ( view.getX() > -button.getWidth() ) {
                        sendBack(view);
                    }
                default:
                    sendBack(view);
            }
            if ( motionEvent.getAction() == MotionEvent.ACTION_UP )
            sendBack( view );
        }
        if ( view.getX() == 0 ) {
            isRevealed = false;
        }

        return true;
    }

    /**
     * Sends the view back to its starting point.
     * @param view the view to send back
     */
    public void sendBack( View view ) {
        SpringAnimation animation = new SpringAnimation(view, DynamicAnimation.TRANSLATION_X,
                0);
        try {
            animation.start();
        } catch (AndroidRuntimeException e) {

        }
        isRevealed = false;
        x1 = 0;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    @Override
    public void onChanged() {
        sendBack(view);
    }
}
