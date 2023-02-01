package com.feefo.technicaltest.application.usecase;

import com.feefo.technicaltest.application.usecase.data.input.NormalizedJobTitleBusinessInput;
import com.feefo.technicaltest.application.usecase.data.output.NormalizedJobTitleBusinessOutput;

public interface NormaliserUseCase {
    NormalizedJobTitleBusinessOutput normalise(NormalizedJobTitleBusinessInput input);
}
