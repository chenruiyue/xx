package itheima;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class QueryIndex {
    //根据关键词检索索引库 搜索文档对象

    @Test
    public void searchIndex() throws IOException, ParseException {
        //指定索引磁盘
        String path = "F:\\indexs";
        DirectoryReader reader = DirectoryReader.open(FSDirectory.open(new File(path)));

        //创建搜索索引库的核心对象
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        //指定索引关键词
        String qName = "夏天";
        //此关键词大于最小分词单元 必须分词后才能进行搜索IK
        //创建查询解析器
        QueryParser queryParser = new QueryParser(Version.LUCENE_4_10_2, "desc", new IKAnalyzer());

        Query query = queryParser.parse(qName);

        TopDocs docs = indexSearcher.search(query,10);

        int num = docs.totalHits;
        System.out.println("总命中数"+num);

        //获取文档得分,文档id
        ScoreDoc[] scoreDocs = docs.scoreDocs;
        //循环数组 获取文档得分 文档id
        for(ScoreDoc doc:scoreDocs){
            double score = doc.score;
            System.out.println("文档的得分是"+score);
            int docId = doc.doc;
            System.out.println("文档id"+docId);

            //依靠文档id来获取对应的文档对象
            Document mydoc = indexSearcher.doc(docId);

            //获取文档对象内容
            //根据域字段名称获取域字段对应值
            String id = mydoc.get("id");
            System.out.println("ID"+id);

            String title = mydoc.get("title");
            System.out.println("title"+title);

            String desc = mydoc.get("desc");
            System.out.println("desc"+desc);

            String concent = mydoc.get("concent");
            System.out.println("concent："+concent);



        }


    }

   //Term 最小单位词条查询  不用分词器
    @Test
    public void termQuery() throws Exception {
        String qName = "目标";
        TermQuery query = new TermQuery(new Term("concent",qName));
        executeAndPrintResult(query);

    }
    //Wildcard 模糊词条查询
    @Test
    public void wildcardQuery() throws Exception {
        String qName = "目标";
        WildcardQuery query = new WildcardQuery(new Term("concent","*"+qName));
        executeAndPrintResult(query);

    }

    //Fuzzy 相似词条查询 仅适用于英文单词
    @Test
    public void fuzzyQuery() throws Exception {
        String qName = "lucene";
        FuzzyQuery query = new FuzzyQuery(new Term("concent",qName));
        executeAndPrintResult(query);

    }

    private void executeAndPrintResult(Query query) throws Exception {
        //指定索引磁盘
        String path = "F:\\indexs";
        DirectoryReader reader = DirectoryReader.open(FSDirectory.open(new File(path)));

        //创建搜索索引库的核心对象
        IndexSearcher indexSearcher = new IndexSearcher(reader);

        TopDocs docs = indexSearcher.search(query,10);

        int num = docs.totalHits;
        System.out.println("总命中数"+num);

        //获取文档得分,文档id
        ScoreDoc[] scoreDocs = docs.scoreDocs;
        //循环数组 获取文档得分 文档id
        for(ScoreDoc doc:scoreDocs){
            double score = doc.score;
            System.out.println("文档的得分是"+score);
            int docId = doc.doc;
            System.out.println("文档id"+docId);

            //依靠文档id来获取对应的文档对象
            Document mydoc = indexSearcher.doc(docId);

            //获取文档对象内容
            //根据域字段名称获取域字段对应值
            String id = mydoc.get("id");
            System.out.println("ID"+id);

            String title = mydoc.get("title");
            System.out.println("title"+title);

            String desc = mydoc.get("desc");
            System.out.println("desc"+desc);

            String concent = mydoc.get("concent");
            System.out.println("concent："+concent);



        }
    }
}
