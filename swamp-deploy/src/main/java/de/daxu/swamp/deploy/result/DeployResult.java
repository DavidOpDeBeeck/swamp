package de.daxu.swamp.deploy.result;

import com.google.common.collect.Sets;
import de.daxu.swamp.common.time.Dates;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.function.Consumer;

import static com.google.common.collect.Sets.newHashSet;

public class DeployResult<RESULT> {

    public static <RESULT> DeployResult<RESULT> empty() {
        return new DeployResult.Builder<RESULT>()
                .withWarnings(Sets.newHashSet())
                .withTimestamp(Dates.now())
                .build();
    }

    public static <RESULT> DeployResult<RESULT> warnings(Set<String> warnings) {
        return new DeployResult.Builder<RESULT>()
                .withWarnings(warnings)
                .withTimestamp(Dates.now())
                .build();
    }

    public static <RESULT> DeployResult<RESULT> result(RESULT result) {
        return new DeployResult.Builder<RESULT>()
                .withResult(result)
                .withTimestamp(Dates.now())
                .build();
    }

    public static <RESULT> DeployResult<RESULT> result(RESULT result, Set<String> warnings) {
        return new DeployResult.Builder<RESULT>()
                .withResult(result)
                .withWarnings(warnings)
                .withTimestamp(Dates.now())
                .build();
    }

    private RESULT result;
    private Set<String> warnings;
    private LocalDateTime timestamp;

    DeployResult(RESULT result, Set<String> warnings, LocalDateTime timestamp) {
        this.result = result;
        this.warnings = warnings == null ? newHashSet() : warnings;
        this.timestamp = timestamp;
    }

    public RESULT get() {
        return result;
    }

    public Set<String> warnings() {
        return warnings;
    }

    public LocalDateTime timestamp() {
        return timestamp;
    }

    public boolean success() {
        return warnings.isEmpty();
    }

    public void onSuccess(Consumer<RESULT> onSuccess) {
        if (success()) {
            onSuccess.accept(get());
        }
    }

    public void onFailed(Runnable onFailed) {
        if (!success()) {
            onFailed.run();
        }
    }

    public static class Builder<RESULT> {

        private RESULT result;
        private Set<String> warnings;
        private LocalDateTime timestamp;

        public Builder<RESULT> withResult(RESULT result) {
            this.result = result;
            return this;
        }

        public Builder<RESULT> withWarnings(Set<String> warnings) {
            this.warnings = warnings;
            return this;
        }

        public Builder<RESULT> withTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public DeployResult<RESULT> build() {
            return new DeployResult<>(result, warnings, timestamp);
        }
    }
}
