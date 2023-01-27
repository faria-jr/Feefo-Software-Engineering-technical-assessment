package com.feefo.technicaltest.entrypoint.data.converter.impl;

import com.feefo.technicaltest.application.usecase.data.input.NormalizedJobTitleBusinessInput;
import com.feefo.technicaltest.application.usecase.data.output.NormalizedJobTitleBusinessOutput;
import com.feefo.technicaltest.entrypoint.data.input.JobTitleInput;
import com.feefo.technicaltest.entrypoint.data.output.JobTitleOutput;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JobTitleConverterImplTest {

    private JobTitleConverterImpl converter = new JobTitleConverterImpl();

    @Test
    void toJobTitleOutput(){
        HashMap<String,String> values = new HashMap<>();
        values.put("jobTitle", "normalized");

        NormalizedJobTitleBusinessOutput input = NormalizedJobTitleBusinessOutput.builder()
                .results(Collections.singletonList(values)                )
                .build();

        JobTitleOutput output = converter.toJobTitleOutput(input);

        assertTrue(output.getResults().size() == 1);
        assertTrue(output.getResults().get(0).getJobTitle() == "jobTitle");
        assertTrue(output.getResults().get(0).getNormalized() == "normalized");
    }

    @Test
    void toBusinessInput(){

        HashMap<String,String> values = new HashMap<>();
        values.put("jobTitle", "normalized");

        JobTitleInput input = new JobTitleInput();
        input.setJobTitles(Collections.singletonList("jobTitle"));

        NormalizedJobTitleBusinessInput output = converter.toBusinessInput(input);

        assertTrue(output.getJobTitles().size() == 1);
        assertTrue(output.getJobTitles().get(0) == "jobTitle");
    }
}