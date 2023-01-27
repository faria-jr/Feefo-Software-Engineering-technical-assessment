package com.feefo.technicaltest.entrypoint;

import com.feefo.technicaltest.application.usecase.NormaliserUseCase;
import com.feefo.technicaltest.application.usecase.impl.NormaliserUseCaseImpl;
import com.feefo.technicaltest.entrypoint.data.converter.JobTitleConverter;
import com.feefo.technicaltest.entrypoint.data.converter.impl.JobTitleConverterImpl;
import com.feefo.technicaltest.entrypoint.data.input.JobTitleInput;
import com.feefo.technicaltest.entrypoint.data.output.JobTitleOutput;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/normalize")
public class NormalizeController {
    @Autowired
    private final NormaliserUseCase usecase = new NormaliserUseCaseImpl();
    @Autowired
    private final JobTitleConverter converter = new JobTitleConverterImpl();

    @PostMapping()
    public ResponseEntity<Object> normalizeJobTitle(@RequestBody JobTitleInput input) throws ParseException, IOException {
        try {
            JobTitleOutput output = this.converter.toJobTitleOutput(
                    this.usecase.normalise(
                            this.converter.toBusinessInput(input)
                    )
            );
            return new ResponseEntity<>(output, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
