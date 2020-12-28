package com.oim.icepouring.module.batteryModule;

import java.util.List;

public interface ErrorChecker<T> {
    void registerError(T object);
    boolean checkErrorExistence();
    List <String> getErrorList();
}
