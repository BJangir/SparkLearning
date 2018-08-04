package org.apache.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 * Created by Administrator on 3/4/2018.
 */
public class LuceneTest
{

  public static void main(String[] args) throws Exception, ParseException {

    StandardAnalyzer analyzer = new StandardAnalyzer();
    Directory index = new RAMDirectory();
//    Directory index = FSDirectory.open(Paths.get("D:/data/mynewindex1"));
    IndexWriterConfig config = new IndexWriterConfig(analyzer);
    IndexWriterConfig config2 = new IndexWriterConfig(analyzer);

    IndexWriter w = new IndexWriter(index, config);
    addDoc(w, "This is the right way to go", "akajda");
    addDoc(w, "This is the right way to accomplish", "asdas");
    addDoc(w, "This is not the right way and don't go this way", "eqdasa");
//    addDoc(w, "The Art of Computer Science", "9900333X");
//    addDoc(w, "foo", "9900333Y");
//    addDoc(w, "bar", "9900333Z");
//    addDoc(w, "foo bar", "9900333AF");
//    addDoc(w, "zfoo barzy", "9900333AF");

    System.out.println("RAM Path:"+index);
    w.close();

    Path path = FileSystems.getDefault().getPath("D:/data", "latest");
    Directory dir = FSDirectory.open(path) ;
    IndexWriter second = new IndexWriter(dir, config2);
    second.addIndexes(new Directory[] {index});
    second.close();


    // 2. query
    //String querystr = args.length > 0 ? args[0] : "lucene";
    String querystr = args.length > 0 ? args[0] : "(title:right)";

    // the "title" arg specifies the default field to use
    // when no field is explicitly specified in the query.
   //    Query q = new QueryParser("title", analyzer).parse(querystr);
    QueryParser qp = new QueryParser("title", analyzer);
    qp.setAllowLeadingWildcard(true);
    Query q = qp.parse(querystr);

    // 3. search
    int hitsPerPage = 10;
    IndexReader reader = DirectoryReader.open(index);
    IndexSearcher searcher = new IndexSearcher(reader);
    TopDocs docs = searcher.search(q, hitsPerPage);
    ScoreDoc[] hits = docs.scoreDocs;

    // 4. display results
    System.out.println("Found " + hits.length + " hits.");
    for(int i=0;i<hits.length;++i) {
      int docId = hits[i].doc;
      Document d = searcher.doc(docId);
      System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
    }

    // reader can only be closed when there
    // is no need to access the documents any more.
    reader.close();
    index.close();

  }

  private static void addDoc(IndexWriter w, String title, String isbn) throws IOException {
    Document doc = new Document();
    doc.add(new TextField("title", title, Field.Store.YES));
    doc.add(new StringField("isbn", isbn, Field.Store.YES));
    try {
      w.addDocument(doc);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
