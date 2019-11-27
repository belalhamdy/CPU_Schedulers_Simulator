package com.company;

import java.io.InputStream;
import java.util.List;

public class AGScheduling extends ProcessScheduling {
    AGScheduling(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    protected List<Process> EnterData() {
        return null;
    }

    @Override
    protected List<Process> Simulate(List<Process> Queue) {
        return null;
    }
}
