package com.feefo.technicaltest.entrypoint.data.converter;

import com.feefo.technicaltest.application.usecase.data.input.NormalizedJobTitleBusinessInput;
import com.feefo.technicaltest.application.usecase.data.output.NormalizedJobTitleBusinessOutput;
import com.feefo.technicaltest.entrypoint.data.input.JobTitleInput;
import com.feefo.technicaltest.entrypoint.data.output.JobTitleOutput;

public interface JobTitleConverter {
    JobTitleOutput toJobTitleOutput(NormalizedJobTitleBusinessOutput input);

    NormalizedJobTitleBusinessInput toBusinessInput(JobTitleInput input);

}
