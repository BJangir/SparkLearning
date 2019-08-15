package org.apache.lucene;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.AlreadyClosedException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.Lock;
import org.apache.lucene.store.LockFactory;
import org.apache.lucene.store.RAMDirectory;

/**
 * Created by Administrator on 3/28/2018.
 */
public class LucenceMerge {

  public static void main(String[] args) throws IOException, ParseException {
    StandardAnalyzer analyzer = new StandardAnalyzer();
    /*IndexWriterConfig config2 = new IndexWriterConfig(analyzer);
    Directory[] alldir=new Directory[2];

    Path path1 = FileSystems.getDefault().getPath("D:/data", "access.log_babu");
    Path path2 = FileSystems.getDefault().getPath("D:/data", "access.log_babu2");
    Path out = FileSystems.getDefault().getPath("D:/data", "access.log_out");
String lock1="D:/data/mylocak1";
String lock2="D:/data/mylocak2";

    LockFactory lockFactory1 = getlockFacorty(lock1);

    alldir[0]=FSDirectory.open(path1) ;
    alldir[1]=FSDirectory.open(path2) ;

    Directory outdir= FSDirectory.open(out,lockFactory1) ;

    IndexWriter second = new IndexWriter(outdir, config2);
    second.addIndexes(alldir);
    second.close();

    */

    // 3. search
    Query q = new QueryParser("title", analyzer).parse("foo");
    Path out = FileSystems.getDefault().getPath("D:/data", "mynewindex");
    Directory outdir= FSDirectory.open(out) ;
    int hitsPerPage = 10;
    IndexReader reader = DirectoryReader.open(outdir);
    IndexSearcher searcher = new IndexSearcher(reader);
    TopDocs docs = searcher.search(q, 10);
    System.out.println("Total Doc1"+reader.numDocs());
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
    System.out.println(reader.getDocCount("isbn"));

    reader.close();

  }
  /*
  public static LockFactory getlockFacorty(String path){
    return new LockFactory() {
      @Override public Lock obtainLock(Directory directory, String s) throws IOException {
        return new SimpleLocal(path);
      }
    };
  }
  */
}
class SimpleLocal extends Lock{
 String path;
  SimpleLocal(String path){
    this.path=path;
  }

  @Override public void close() throws IOException {
  }

  @Override public void ensureValid() throws IOException {
    String s = new File(path) + "/" + System.currentTimeMillis();
    if(new File(s).exists()){
     throw new AlreadyClosedException("lock can not aquired "+path);
  }
  }
}