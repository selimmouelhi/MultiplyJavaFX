package tn.esprit.multiply.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import tn.esprit.multiply.Utils.Utils;
import tn.esprit.multiply.entities.Entry;
import tn.esprit.multiply.entities.ReturnedObject;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MultiplyController implements Initializable {


    public void initialize(URL location, ResourceBundle resources) {

        multiply.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                //testing whatever the values given  are correct

               if( !value1.getText().matches("-?([1-9][0-9]*)?") || value1.getText().trim().equals("")){
                   invalid1.setVisible(true);
                   invalid2.setVisible(false);
               }
               else if(!value2.getText().matches("-?([1-9][0-9]*)?")|| value2.getText().trim().equals("")){
                   invalid1.setVisible(false);
                   invalid2.setVisible(true);
               }
               else{
                   invalid1.setVisible(false);
                   invalid2.setVisible(false);
                   new Thread(new Runnable() {
                    public void run() {


                        System.out.println(new Date());
                        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(3 * 1000).build();
                        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
                        HttpPost postRequest = new HttpPost(Utils.globalUrl);
                        try {
                            //Set various attributes
                            Entry entry = new Entry("details", Integer.parseInt(value1.getText()), Integer.parseInt(value2.getText()));
                            Gson gson = new Gson();
                            StringEntity postingString = new StringEntity(gson.toJson(entry));//gson.tojson() converts your entity to json
                            //Prepare payload

                            //Set to request body
                            postRequest.setEntity(postingString);
                            postRequest.setHeader("Content-type", "application/json");


                            //Send request
                            HttpResponse response = client.execute(postRequest);

                            //Verify response if any
                            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                                System.out.println(response.getStatusLine().getStatusCode());
                                String jsonString = EntityUtils.toString(response.getEntity());
                                System.out.println(jsonString + "response");
                                ReturnedObject object = new Gson().fromJson(jsonString,new TypeToken<ReturnedObject>(){
                                }.getType());

                                //printing the object given after consuming web service
                                System.out.println(object.toString());
                                //then show the result
                                result.setText(Integer.toString(object.getResult()));
                                } else {
                                System.out.println("Request failed");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();

                        }
                    }
                }).start();

            }}
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
    }




    @FXML
    private Label invalid1;
    @FXML
    private Label invalid2;
    @FXML
    private TextField result;

    @FXML
    private Button cancel;

    @FXML
    private TextField value2;

    @FXML
    private TextField value1;

    @FXML
    private Button multiply;




    }

