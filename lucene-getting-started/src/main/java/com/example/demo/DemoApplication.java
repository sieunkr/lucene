package com.example.demo;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        //New index
        StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
        Directory directory = new RAMDirectory();
        IndexWriterConfig config = new IndexWriterConfig(standardAnalyzer);

        //Create a writer
        IndexWriter writer = new IndexWriter(directory, config);
        Document document = new Document();

        document.add(new TextField("content", "Hello World", Field.Store.YES));
        writer.addDocument(document);
        document.add(new TextField("content", "Hello people", Field.Store.YES));
        writer.addDocument(document);
        writer.close();

        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher (reader);

        QueryParser parser = new QueryParser ("content", standardAnalyzer);
        Query query = parser.parse("Hello");
        TopDocs results = searcher.search(query, 5);
        System.out.println("Hits for Hello -->" + results.totalHits);

        query = parser.parse("hello");
        results = searcher.search(query, 5);
        System.out.println("Hits for hello -->" + results.totalHits);

        query = parser.parse("Hi there");
        results = searcher.search(query, 5);
        System.out.println("Hits for Hi there -->" + results.totalHits);

        

    }
}
