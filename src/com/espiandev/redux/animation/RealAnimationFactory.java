package com.espiandev.redux.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

public class RealAnimationFactory implements AnimationFactory {

    @Override
    public void fadeIn(final View view) {
        ObjectAnimator animator = createFadeInAnimator(view, null);
        animator.start();
    }

    @Override
    public void refadeIn(View view, Runnable runnable) {
        Animator animator;
        if (view.getVisibility() == View.VISIBLE) {
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(createFadeOutAnimator(view), createFadeInAnimator(view,
                    runnable));
            animator = animatorSet;
        } else {
            animator = createFadeInAnimator(view, runnable);
        }
        animator.start();
    }

    @Override
    public void fadeOut(final View view) {
        ObjectAnimator animator = createFadeOutAnimator(view);
        animator.start();
    }

    private static ObjectAnimator createFadeInAnimator(final View view, final Runnable runnable) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
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
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
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
}
