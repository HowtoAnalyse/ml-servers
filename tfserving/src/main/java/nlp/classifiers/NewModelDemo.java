package nlp.classifiers;

import org.tensorflow.SavedModelBundle;
import org.tensorflow.Tensor;

import java.io.IOException;

public class NewModelDemo {
    SavedModelBundle bundle = null;
    public void init() {
        bundle = loadmodel("C:\\Users\\sara.zeng\\OneDrive - The University Of Hong Kong\\csrp\\ml-servers\\tfserving\\src\\main\\resources\\tf_models");
    }

    public  static SavedModelBundle loadmodel(String modelpath){
        SavedModelBundle bundle=SavedModelBundle.load(modelpath,"serve");
        return bundle;
    }

//    public Tensor getResult(float[][] arr) {
//        Tensor tensor = Tensor.create(arr);
//        Tensor<?> result = bundle.session().runner().feed("sms",tensor).feed("predict").run().get(0);
//        return result;
//    }

    public static void main(String[] args) throws IOException {
        NewModelDemo model = new NewModelDemo();
        model.init();
//        float[][] text = new float[20][200];
//        text[0] = "克林顿说，华盛顿将逐步落实对韩国的经济援助。";
//        System.out.println(model.getResult(text));





    }



}
