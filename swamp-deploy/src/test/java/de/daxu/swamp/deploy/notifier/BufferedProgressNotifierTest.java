package de.daxu.swamp.deploy.notifier;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static de.daxu.swamp.deploy.notifier.BufferedProgressNotifier.Builder.aBufferedProgressNotifier;
import static java.lang.Thread.sleep;
import static org.mockito.Mockito.verify;

public class BufferedProgressNotifierTest {

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();
    @Mock
    private DeployNotifier.ProgressNotifier<String> progressNotifier;

    private BufferedProgressNotifier.Builder bufferedNotifier;

    @Before
    public void setUp() throws Exception {
        bufferedNotifier = aBufferedProgressNotifier()
                .withDelegate(progressNotifier);
    }

    @Test
    public void onProgress_WhenBufferLimitMet_CallsDelegate() throws Exception {
        BufferedProgressNotifier notifier = bufferedNotifier
                .withBufferLimit(3)
                .build();

        notifier.onProgress("1");
        notifier.onProgress("2");
        notifier.onProgress("3");

        verify(progressNotifier).onProgress("123");
    }

    @Test
    public void onProgress_WhenBufferLimitNotMet_CallsDelegateAfterWaitTime() throws Exception {
        BufferedProgressNotifier notifier = bufferedNotifier
                .withBufferLimit(3)
                .withBufferWaitTime(1000)
                .build();

        notifier.onProgress("1");
        notifier.onProgress("2");

        sleep(2000);

        verify(progressNotifier).onProgress("12");
    }

    @Test
    public void onProgress_WhenTimerStarted_CancelsWhenBufferLimitMet() throws Exception {
        BufferedProgressNotifier notifier = bufferedNotifier
                .withBufferLimit(3)
                .withBufferWaitTime(1000)
                .build();

        notifier.onProgress("1");
        notifier.onProgress("2");

        sleep(900);

        notifier.onProgress("3");

        sleep(500);

        verify(progressNotifier).onProgress("123");
    }
}