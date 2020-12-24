package com.oim.icepouring.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.oim.icepouring.R;

public class SpeedPanelFragment  extends Fragment {
    private ArcProgress arcProgress;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.speed_fragment, null);
        arcProgress =   v.findViewById(R.id.speedPanel);
        setSpeedPanelProperties(arcProgress);
        return v;
    }
    public ArcProgress getArcProgress()
    {
        return  arcProgress;
    }
    private void setSpeedPanelProperties(ArcProgress arcProgress)
    {
        arcProgress.setSuffixText(" ");
        arcProgress.setBottomText("km/h");
        arcProgress.setBottomTextSize(30);
        arcProgress.setTextSize(30);
        arcProgress.setProgress(0);
        arcProgress.setTextColor(Color.parseColor("#F4F0F0"));
        arcProgress.setUnfinishedStrokeColor(Color.parseColor("#FFFFFF"));
        arcProgress.setFinishedStrokeColor(Color.parseColor("#FFFF00"));
    }
}
