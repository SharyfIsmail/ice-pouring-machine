package com.oim.icepouring.thread;

import com.oim.icepouring.errorModel.ErrorViewerModel;

import java.util.TimerTask;

public class ErrorViewer extends TimerTask  {
    private ErrorViewerModel errorViewerModel;

    public ErrorViewer( ErrorViewerModel errorViewerModel)
    {
        this.errorViewerModel = errorViewerModel;
    }
    @Override
    public void run()
    {
        errorViewerModel.updateErrorList();
        errorViewerModel.updateField();
    }
}
