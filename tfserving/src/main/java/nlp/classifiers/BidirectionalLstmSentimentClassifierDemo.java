package nlp.classifiers;

import nlp.classifiers.utils.ResourceUtils;
import nlp.classifiers.models.BidirectionalLstmSentimentClassifier;

import java.io.IOException;
import java.util.List;

public class BidirectionalLstmSentimentClassifierDemo {
    public static void main(String[] args) throws IOException {

        BidirectionalLstmSentimentClassifier classifier = new BidirectionalLstmSentimentClassifier();
        classifier.load_model(ResourceUtils.getInputStream("tf_models/saved_model.pb"));
        classifier.load_vocab(ResourceUtils.getInputStream("tf_models/bidirectional_lstm_softmax.csv"));

        List<String> lines = ResourceUtils.getLines("data/umich-sentiment-train.txt");

        for(String line : lines){
            String label = line.split("\t")[0];
            String text = line.split("\t")[1];
            float[] predicted = classifier.predict(text);
            String predicted_label = classifier.predict_label(text);
            System.out.println(text);
            System.out.println("Outcome: " + predicted[0] + ", " + predicted[1]);
            System.out.println("Predicted: " + predicted_label + " Actual: " + label);
        }
    }
}
