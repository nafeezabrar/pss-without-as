package pss.runner.cycle;

import pss.exception.PssException;
import pss.report.common.Report;
import pss.result.Result;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface CycleRunnable<TReport extends Report, TResult extends Result> {
    List<TResult> runCycle(List<TReport> reports) throws PssException, IOException, URISyntaxException;

    TResult runCycle(TReport report) throws PssException;
}
