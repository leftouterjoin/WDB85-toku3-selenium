package jp.gihyo.selenium.sample.rule;

import org.junit.AssumptionViolatedException;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RetryRule implements TestRule {
    private int maxRetryCount;

    public RetryRule(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    @Override
    public Statement apply(final Statement statement, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Throwable caughtThrowable = null;

                for (int i = 0; i < maxRetryCount; i++) {
                    try {
                        statement.evaluate();
                        return;
                    } catch (AssumptionViolatedException e) {
                        // スキップされたテストはリトライしない
                        throw e;
                    } catch (Throwable t) {
                        caughtThrowable = t;
                        System.err.println(String.format("%s failed %d times", description.getDisplayName(), i + 1));
                    }
                }

                throw caughtThrowable;
            }
        };
    }
}
