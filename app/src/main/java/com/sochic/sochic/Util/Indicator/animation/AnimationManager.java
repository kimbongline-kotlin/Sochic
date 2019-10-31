package com.sochic.sochic.Util.Indicator.animation;


import androidx.annotation.NonNull;

import com.sochic.sochic.Util.Indicator.animation.controller.AnimationController;
import com.sochic.sochic.Util.Indicator.animation.controller.ValueController;
import com.sochic.sochic.Util.Indicator.draw.data.Indicator;

public class AnimationManager {

    private AnimationController animationController;

    public AnimationManager(@NonNull Indicator indicator, @NonNull ValueController.UpdateListener listener) {
        this.animationController = new AnimationController(indicator, listener);
    }

    public void basic() {
        if (animationController != null) {
            animationController.end();
            animationController.basic();
        }
    }

    public void interactive(float progress) {
        if (animationController != null) {
            animationController.interactive(progress);
        }
    }

    public void end() {
        if (animationController != null) {
            animationController.end();
        }
    }
}
