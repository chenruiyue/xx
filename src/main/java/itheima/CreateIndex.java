package itheima;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class CreateIndex{
    //创建索引库 (模拟文档对象)
    @Test
    public void addIndex() throws IOException {
        //指定存储索引库磁盘路径
        String path = "F:\\indexs";
        //关联磁盘索引库存储位置
        Directory directory = FSDirectory.open(new File(path));

        //创建分词器对象
        //把文档对象中文本进行分词 变成索引单词
        //1.基本分词器(StandardAnalyzer) 特点:一个汉字一个词语.
        //2.cik分词器(CJKAnalyzer) 特点:每两个汉字一个词语,不管这两个汉字组合是不是词语
        //3.聪明的中国人分词器(SmartChineseAnalyzer)特点:满足中文一些搜索习惯,对英文支持不是很好

        //4.使用IK分词器创建索引库
        Analyzer analyzer = new IKAnalyzer();

        //创建核心对象的配置对象:Lucene版本,使用分词器
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);

        //创建索引库核心对象 indexWriter
        IndexWriter indexWriter = new IndexWriter(directory, iwc);

        //文档对象来源
        //1.网页   2.文件   3.数据库
        //文档数据结构是以域字段方法组成的 一个字段对应一些值
        //id,title.desc,url,content

        Document doc = new Document();
        //添加域字段:id
        //StringField:域字段类型
        //特点:不分词,有索引,Store.NO/YES
        //搜索结果需不需要进行结果展示,如果不展示,数据就不存储
        doc.add(new StringField("id","t5556789", Field.Store.NO));

        //添加域字段:title
        //TextField:域字段类型
        //特点:必须分词,索引,存储
        doc.add(new TextField("title","大家都在传智播客好好学习java,Lucene经典教程,希望可以学有所成", Field.Store.YES));

        //添加域字段:desc
        //TextField:域字段类型
        //特点:必须分词,索引,存储
        doc.add(new TextField("desc","这个夏天好多小孩玩王者荣耀,坑爹!", Field.Store.YES));

        //添加域字段：content
        //TextField:域字段类型
        //特点：
        //必须分词，索引，存储
        doc.add(new TextField("concent","努力学习,这样才能找到好工作啊,目标是月入10K,然后再慢慢过自己想过的生活!", Field.Store.YES));

        //写索引库
        indexWriter.addDocument(doc);

        //提交
        indexWriter.commit();





    }
}
