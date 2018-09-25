package annotators;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLPClient;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Properties;

public class Segmenter {
    public static void main(String[] args) {

        Segmenter example = new Segmenter();

        example.runChineseAnnotators();

    }

    public void runChineseAnnotators(){

        Properties props = new Properties();
        props.setProperty("annotators","tokenize, ssplit");
        StanfordCoreNLPClient pipeline = new StanfordCoreNLPClient(props, "http://IP", PORT, 2);

        String text = "克林顿说，华盛顿将逐步落实对韩国的经济援助。"
                + "金大中对克林顿的讲话报以掌声：克林顿总统在会谈中重申，他坚定地支持韩国摆脱经济危机。";
        Annotation document = new Annotation(text);
        pipeline.annotate(document);
        parserOutput(document);
    }

    public void parserOutput(Annotation document){
        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        for(CoreMap sentence: sentences) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                System.out.println(word+"\t");
            }
        }
    }
}
