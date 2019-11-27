package com.company;

import java.io.InputStream;
import java.util.List;

public class PriorityScheduling extends ProcessScheduling {

    PriorityScheduling(InputStream inputStream) {
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
