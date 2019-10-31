package com.sochic.sochic.Util.Indicator;


import androidx.annotation.Nullable;

import com.sochic.sochic.Util.Indicator.animation.AnimationManager;
import com.sochic.sochic.Util.Indicator.animation.controller.ValueController;
import com.sochic.sochic.Util.Indicator.animation.data.Value;
import com.sochic.sochic.Util.Indicator.draw.DrawManager;
import com.sochic.sochic.Util.Indicator.draw.data.Indicator;

public class IndicatorManager implements ValueController.UpdateListener {

    private DrawManager drawManager;
    private AnimationManager animationManager;
    private Listener listener;

    interface Listener {
        void onIndicatorUpdated();
    }

    IndicatorManager(@Nullable Listener listener) {
        this.listener = listener;
        this.drawManager = new DrawManager();
        this.animationManager = new AnimationManager(drawManager.indicator(), this);
    }

    public AnimationManager animate() {
        return animationManager;
    }

    public Indicator indicator() {
        return drawManager.indicator();
    }

    public DrawManager drawer() {
        return drawManager;
    }

    @Override
    public void onValueUpdated(@Nullable Value value) {
        drawManager.updateValue(value);
        if (listener != null) {
            listener.onIndicatorUpdated();
        }
    }
}
