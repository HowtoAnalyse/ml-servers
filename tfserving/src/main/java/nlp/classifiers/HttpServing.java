package nlp.classifiers;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpServing {
    public static void main(String[] args) throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://52.163.191.2:8501/v1/models/textclf:predict");
        StringEntity params =new StringEntity("{\"instances\": [\"ok, I will be with you in 5 min. see you then\"]}");
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        HttpResponse response = httpClient.execute(request);
        String json = EntityUtils.toString(response.getEntity());
        System.out.println(json);
    }
}
