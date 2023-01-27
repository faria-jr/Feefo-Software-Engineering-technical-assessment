package com.feefo.technicaltest.entrypoint.data.converter.impl;

import com.feefo.technicaltest.application.usecase.data.input.NormalizedJobTitleBusinessInput;
import com.feefo.technicaltest.application.usecase.data.output.NormalizedJobTitleBusinessOutput;
import com.feefo.technicaltest.entrypoint.data.converter.JobTitleConverter;
import com.feefo.technicaltest.entrypoint.data.input.JobTitleInput;
import com.feefo.technicaltest.entrypoint.data.output.JobTitleOutput;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class JobTitleConverterImpl implements JobTitleConverter {

    @Override
    public JobTitleOutput toJobTitleOutput(NormalizedJobTitleBusinessOutput input) {
        JobTitleOutput output = new JobTitleOutput();
        List<JobTitleOutput.NormalizedOutput> results = new ArrayList<>();
        for (HashMap<String, String> result : input.getResults()) {
            for (String key : result.keySet()) {
                JobTitleOutput.NormalizedOutput normalizedOutput = new JobTitleOutput.NormalizedOutput();
                normalizedOutput.setJobTitle(key);
                normalizedOutput.setNormalized(result.get(key));
                results.add(normalizedOutput);
            }
        }
        output.setResults(results);
        return output;
    }

    @Override
    public NormalizedJobTitleBusinessInput toBusinessInput(JobTitleInput input) {
        NormalizedJobTitleBusinessInput businessInput = NormalizedJobTitleBusinessInput.builder().build();

        if (Objects.nonNull(input)) {
            businessInput.setJobTitles(input.getJobTitles());
            return businessInput;
        }
        return businessInput;
    }
}
