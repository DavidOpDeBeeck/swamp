package de.daxu.swamp.deploy.notifier;

import de.daxu.swamp.deploy.notifier.DeployNotifier.ProgressNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class BufferedProgressNotifier implements ProgressNotifier<String> {

    private final Logger logger = LoggerFactory.getLogger(BufferedProgressNotifier.class);

    private final int bufferLimit;
    private final int bufferWaitTime;
    private final StringBuilder buffer;
    private final ProgressNotifier<String> delegate;

    private int count;
    private Timer bufferTimer;

    private BufferedProgressNotifier(int bufferLimit, int bufferWaitTime, ProgressNotifier<String> delegate) {
        this.bufferLimit = bufferLimit;
        this.bufferWaitTime = bufferWaitTime;
        this.delegate = delegate;
        this.count = 0;
        this.buffer = new StringBuilder();
        this.bufferTimer = new Timer();
    }

    @Override
    public void onProgress(String payload) {
        startWaitTimer();
        addToBuffer(payload);
        if (count >= bufferLimit) {
            callDelegate();
        }
    }

    private void callDelegate() {
        logger.trace("Buffered {} lines", count);
        delegate.onProgress(String.join("", buffer.toString()));
        resetBuffer();
    }

    private void addToBuffer(String payload) {
        this.count++;
        this.buffer.append(payload);
    }

    private void resetBuffer() {
        this.count = 0;
        this.buffer.setLength(0);
    }

    private void startWaitTimer() {
        resetWaitTimer();
        bufferTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                callDelegate();
            }
        }, bufferWaitTime);
    }

    private void resetWaitTimer() {
        bufferTimer.cancel();
        bufferTimer = new Timer();
    }

    public static class Builder {

        private int bufferLimit = 5;
        private int bufferWaitTime = 2000;
        private ProgressNotifier<String> delegate;

        public static Builder aBufferedProgressNotifier() {
            return new Builder();
        }

        public Builder withBufferLimit(int bufferLimit) {
            this.bufferLimit = bufferLimit;
            return this;
        }

        public Builder withBufferWaitTime(int bufferWaitTime) {
            this.bufferWaitTime = bufferWaitTime;
            return this;
        }

        public Builder withDelegate(ProgressNotifier<String> delegate) {
            this.delegate = delegate;
            return this;
        }

        public BufferedProgressNotifier build() {
            return new BufferedProgressNotifier(bufferLimit, bufferWaitTime, delegate);
        }
    }
}
