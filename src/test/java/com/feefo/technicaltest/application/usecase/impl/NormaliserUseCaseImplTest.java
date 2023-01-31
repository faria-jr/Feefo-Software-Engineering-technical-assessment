package com.feefo.technicaltest.application.usecase.impl;

import com.feefo.technicaltest.application.usecase.data.converter.NormalizedJobTitleBusinessConverter;
import com.feefo.technicaltest.application.usecase.data.converter.impl.NormalizedJobTitleBusinessConverterImpl;
import com.feefo.technicaltest.application.usecase.data.input.NormalizedJobTitleBusinessInput;
import com.feefo.technicaltest.application.usecase.data.output.NormalizedJobTitleBusinessOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class NormaliserUseCaseImplTest {
    @InjectMocks
    private NormaliserUseCaseImpl useCase = new NormaliserUseCaseImpl();
    @Spy
    private NormalizedJobTitleBusinessConverter converter = spy(new NormalizedJobTitleBusinessConverterImpl());

    @Test
    void shoudNormaliseWithSuccess() {
        NormalizedJobTitleBusinessOutput output = useCase.normalise(buildInput());

        assertTrue(output.getResults().size() > 0);
        assertTrue(output.getResults().get(0).containsValue("Software engineer"));
    }

    @Test
    void shoudNormaliseException() {
        NormalizedJobTitleBusinessInput input = buildInput();
        input.setJobTitles(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            useCase.normalise(input);
        });
    }

    private NormalizedJobTitleBusinessInput buildInput(){
        return NormalizedJobTitleBusinessInput.builder().jobTitles(Collections.singletonList("Java Engineer")).build();
    }
}