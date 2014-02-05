package com.espiandev.redux.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.espiandev.redux.R;

public class RealAnimationFactory implements AnimationFactory {

    private static final Interpolator interpolator = new AccelerateDecelerateInterpolator();

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
            }

            @Override
            public void onAnimationEnd(Animator animator) {
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
        return createFadeOutAnimator(view, 1f);
    }

    private static ObjectAnimator createFadeOutAnimator(final View view, float startingValue) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", startingValue, 0f);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.INVISIBLE);
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
