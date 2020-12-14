package com.executor;

import com.docker.DockerExecutor;

public class ExecutorManager {
    private static Execute loacalExecute;
    private static DockerExecutor dockerExecutor;

    public ExecutorManager() {
        loacalExecute = new Execute();
        dockerExecutor = new DockerExecutor();
    }

    public static Executor getExecutor(boolean isDocker) {
        if (isDocker == true) {
            return dockerExecutor;
        } else {
            return loacalExecute;
        }
    }
}
