package com.espiandev.redux.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.espiandev.redux.R;

public class RealAnimationFactory implements AnimationFactory {

    private static final Interpolator interpolator = new AccelerateDecelerateInterpolator();
    public static final float PARTIAL_ALPHA = 0.7f;

    @Override
    public void fadeIn(final View view) {
        ObjectAnimator animator = createFadeInAnimator(view, null);
        animator.setInterpolator(interpolator);
        animator.start();
    }

    @Override
    public void refadeIn(View view, Runnable runnable) {
        Animator animator;
        if (view.getVisibility() == View.VISIBLE) {
            AnimatorSet animatorSet = createSet();
            animatorSet.playSequentially(createFadeOutAnimator(view), createFadeInAnimator(view,
                    runnable));
            animator = animatorSet;
        } else {
            animator = createFadeInAnimator(view, runnable);
        }
        animator.setInterpolator(interpolator);
        animator.start();
    }

    @Override
    public void fadeAndChangeText(final TextView textView, final CharSequence text) {
        Animator animator;
        Runnable changeText = new Runnable() {
            @Override
            public void run() {
                textView.setText(text);
            }
        };
        if (textView.getVisibility() == View.VISIBLE) {
            AnimatorSet animatorSet = createSet();
            animatorSet.playSequentially(createFadeOutAnimator(textView), createFadeInAnimator(textView,
                    changeText));
            animator = animatorSet;
        } else {
            animator = createFadeInAnimator(textView, changeText);
        }
        animator.setInterpolator(interpolator);
        animator.start();
    }

    @Override
    public void fadeOut(final View view) {
        if (view.getVisibility() == View.INVISIBLE) {
            return;
        }

        ObjectAnimator animator = createFadeOutAnimator(view);
        animator.setInterpolator(interpolator);
        animator.start();
    }

    @Override
    public void upAndOut(View view) {
        float translation = view.getContext().getResources().getDimension(R.dimen.translation_delta);
        AnimatorSet set = createSet();
        set.playTogether(createFadeOutAnimator(view), ObjectAnimator.ofFloat(view, "translationY", 0, -translation));
        set.start();
    }

    @Override
    public void upAndIn(View view) {
        float translation = view.getContext().getResources().getDimension(R.dimen.translation_delta);
        AnimatorSet set = createSet();
        set.playTogether(createFadeInAnimator(view, null), ObjectAnimator.ofFloat(view, "translationY", translation, 0));
        set.setInterpolator(interpolator);
        set.start();
    }

    @Override
    public void downAndOut(View view) {
        float translation = view.getContext().getResources().getDimension(R.dimen.translation_delta);
        AnimatorSet set = createSet();
        set.setInterpolator(interpolator);
        set.playTogether(createFadeOutAnimator(view), ObjectAnimator.ofFloat(view, "translationY", 0, translation));
        set.start();
    }

    @Override
    public void downAndIn(View view) {
        float translation = view.getContext().getResources().getDimension(R.dimen.translation_delta);
        AnimatorSet set = createSet();
        set.playTogether(createFadeInAnimator(view, null), ObjectAnimator.ofFloat(view, "translationY", -translation, 0));
        set.start();
    }

    @Override
    public void cancelAnimations(View... views) {
        for (View view : views) {
            if (view.getTag() != null && view.getTag() instanceof Animator) {
                ((Animator) view.getTag()).cancel();
            }
        }
    }

    private static ObjectAnimator createFadeInAnimator(final View view, final Runnable runnable) {
        return createFadeInAnimator(view, runnable, 0f);
    }

    private static ObjectAnimator createFadeInAnimator(final View view, final Runnable runnable, float startingValue) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", startingValue, 1f);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                view.setVisibility(View.VISIBLE);
                if (runnable != null) {
                    runnable.run();
                }
                view.setTag(animator);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setTag(animator);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        return animator;
    }

    private static ObjectAnimator createFadeOutAnimator(final View view) {
        return createFadeOutAnimator(view, 0f);
    }

    private static ObjectAnimator createFadeOutAnimator(final View view, float endingValue) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, endingValue);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                view.setVisibility(View.VISIBLE);
                view.setTag(animator);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.INVISIBLE);
                view.setTag(null);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        return animator;
    }

    private static AnimatorSet createSet() {
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(interpolator);
        return set;
    }

}
