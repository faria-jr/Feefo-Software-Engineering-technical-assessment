package com.feefo.technicaltest.application.usecase;

import com.feefo.technicaltest.application.usecase.data.input.NormalizedJobTitleBusinessInput;
import com.feefo.technicaltest.application.usecase.data.output.NormalizedJobTitleBusinessOutput;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;

public interface NormaliserUseCase {
    NormalizedJobTitleBusinessOutput normalise(NormalizedJobTitleBusinessInput input);
}
