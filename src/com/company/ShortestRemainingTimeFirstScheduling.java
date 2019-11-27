package com.company;

import java.io.InputStream;
import java.util.List;

public class ShortestRemainingTimeFirstScheduling extends ProcessScheduling {

    ShortestRemainingTimeFirstScheduling(InputStream inputStream) {
        super(inputStream);
    }
    ShortestRemainingTimeFirstScheduling(List<Process> data) {
        super(data);
    }

    @Override
    protected List<Process> EnterData() {
        return null;
    }

    @Override
    protected List<Process> Simulate() {
        return null;
    }
}
