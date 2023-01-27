package com.feefo.technicaltest.application.usecase.impl;

import com.feefo.technicaltest.application.usecase.NormaliserUseCase;
import com.feefo.technicaltest.application.usecase.data.converter.NormalizedJobTitleBusinessConverter;
import com.feefo.technicaltest.application.usecase.data.converter.impl.NormalizedJobTitleBusinessConverterImpl;
import com.feefo.technicaltest.application.usecase.data.input.NormalizedJobTitleBusinessInput;
import com.feefo.technicaltest.application.usecase.data.output.NormalizedJobTitleBusinessOutput;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class NormaliserUseCaseImpl implements NormaliserUseCase {
    private static final List<String> NORMALIZED_TITLES = Arrays.asList(
            "Architect",
            "Software engineer",
            "Quantity surveyor",
            "Accountant");
    private IndexReader indexReader;
    private IndexSearcher indexSearcher;
    private QueryParser queryParser;
    private StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
    private List<String> normalizedList = new ArrayList<String>();
    private static final Float minParam = 0.0f;
    private static final Float maxParam = 1.0f;

    @Autowired
    private final NormalizedJobTitleBusinessConverter converter = new NormalizedJobTitleBusinessConverterImpl();


    private void initUseCase() throws IOException {
        try {

            indexReader = DirectoryReader.open(generateIndex(NORMALIZED_TITLES));
            queryParser = new QueryParser("title", standardAnalyzer);
            indexSearcher = new IndexSearcher(indexReader);
        } catch (IOException e) {
            throw new IOException();
        }

    }

    @Override
    public NormalizedJobTitleBusinessOutput normalise(NormalizedJobTitleBusinessInput input) throws IOException {
        if (Objects.isNull(input.getJobTitles())) {
            throw new RuntimeException("Invalid Input");
        }

        this.initUseCase();

        List<HashMap<String, String>> results = new ArrayList<>();
        for (String jobTitle : input.getJobTitles()) {
            String normalizedTitle = null;
            try {
                HashMap<String, String> result = new HashMap<>();
                normalizedTitle = this.normalize(jobTitle);
                result.put(jobTitle, normalizedTitle);
                results.add(result);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        ;
        this.close();
        return this.setOutput(results);
    }

    private Directory generateIndex(List<String> titles) throws IOException {
        Directory index = new ByteBuffersDirectory();
        IndexWriter idxWriter = new IndexWriter(index, new IndexWriterConfig(standardAnalyzer));
        for (String title : titles) {
            Document doc = new Document();
            doc.add(new TextField("title", title, Field.Store.YES));
            idxWriter.addDocument(doc);
        }
        idxWriter.close();

        return index;
    }

    private void normalizeList(String input, Integer max) throws ParseException, IOException {
        normalizedList.clear();

        if (input.trim().isEmpty()) {
            return;
        }

        TopDocs topDocs = indexSearcher.search(queryParser.parse(input), max);
        ScoreDoc[] hits = topDocs.scoreDocs;

        if (hits.length == 0) {
            return;
        }

        for (ScoreDoc hit : hits) {
            if (minParam <= hit.score && hit.score <= maxParam) {
                normalizedList.add(String.format("%s", indexSearcher.doc(hit.doc).get("title"), hit.score, hits.length));
            }
        }
    }

    private String normalize(String input) throws ParseException, IOException {
        if (Objects.isNull(input) || input.isEmpty()) {
            return "";
        }
        normalizeList(input, 20);
        if (normalizedList.isEmpty()) {
            return "";
        }

        return normalizedList.get(0);
    }

    private NormalizedJobTitleBusinessOutput setOutput(List<HashMap<String, String>> results) {
        NormalizedJobTitleBusinessOutput result = NormalizedJobTitleBusinessOutput.builder().build();
        if (results.isEmpty()) {
            result.setResults(Arrays.asList(this.converter.prepareToBusinessOutput("empty Input", "invalid Input")));
            return result;
        }
        result = this.converter.toBusinessOutput(results);
        System.out.format(result.toString());
        return result;
    }

    private void close() throws IOException {
        indexReader.close();
    }

}
