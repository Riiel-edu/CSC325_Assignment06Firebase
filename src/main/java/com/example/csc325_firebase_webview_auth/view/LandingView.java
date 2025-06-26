package com.example.csc325_firebase_webview_auth.view;

import com.example.csc325_firebase_webview_auth.model.CurrUser;
import com.example.csc325_firebase_webview_auth.model.Person;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class LandingView implements Initializable {

    @FXML
    TextField name, age, major;

    String currUser;

    @FXML
    private TableView<Person> tv;

    @FXML
    private TableColumn<Person, Integer> tv_age;

    @FXML
    private TableColumn<Person, String> tv_fn, tv_major;

    ObservableList<Person> data = FXCollections.observableList(new ArrayList<Person>());

    @FXML
    ImageView img_view;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        currUser = CurrUser.getUid();

        tv_fn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tv_age.setCellValueFactory(new PropertyValueFactory<>("age"));
        tv_major.setCellValueFactory(new PropertyValueFactory<>("major"));

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = App.fstore.collection("References").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();

            if (documents.size() > 0) {
                System.out.println("Outing....");

                for (QueryDocumentSnapshot document : documents) {

                    System.out.println(document.getData().get("Age"));
                    Person person = new Person(String.valueOf(document.getData().get("Name")), document.getData().get("Major").toString(), Integer.parseInt(document.getData().get("Age").toString()));

                    if(document.getData().get("User").equals(currUser)) {
                        data.add(person);
                    }
                }

            } else {
                System.out.println("No data");
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        tv.setItems(data);

    }


    @FXML
    protected void addNewRecord() {

        data.add(new Person(name.getText(), major.getText(), Integer.parseInt(age.getText())));

        DocumentReference docRef = App.fstore.collection("References").document(UUID.randomUUID().toString());

        Map<String, Object> datas = new HashMap<>();
        datas.put("Name", name.getText());
        datas.put("Major", major.getText());
        datas.put("Age", Integer.parseInt(age.getText()));
        datas.put("User", currUser);

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(datas);

    }

    @FXML
    protected void clearForm() {
        name.setText("");
        age.setText("");
        major.setText("");
    }

    @FXML
    protected void closeApplication() {
        System.exit(0);
    }


    @FXML
    protected void editRecord() {
        Person p = tv.getSelectionModel().getSelectedItem();

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = App.fstore.collection("References").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();

            if (documents.size() > 0) {
                System.out.println("Outing....");

                for (QueryDocumentSnapshot document : documents) {

                    if(document.getData().get("User").equals(currUser) && document.getData().get("Name").equals(p.getName()) && document.getData().get("Major").equals(p.getMajor()) && Integer.parseInt(document.getData().get("Age").toString()) == p.getAge()) {
                        DocumentReference docRef = App.fstore.collection("References").document(document.getId());
                        docRef.delete();
                    }
                }

            } else {
                System.out.println("No data");
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        int c = data.indexOf(p);
        Person p2 = new Person(name.getText(), major.getText(), Integer.parseInt(age.getText()));
        data.remove(c);
        data.add(c,p2);

        DocumentReference docRef = App.fstore.collection("References").document(UUID.randomUUID().toString());

        Map<String, Object> datas = new HashMap<>();
        datas.put("Name", name.getText());
        datas.put("Major", major.getText());
        datas.put("Age", Integer.parseInt(age.getText()));
        datas.put("User", currUser);

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(datas);

        tv.getSelectionModel().select(c);
    }

    @FXML
    protected void deleteRecord() {

        Person p = tv.getSelectionModel().getSelectedItem();

        //asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = App.fstore.collection("References").get();
        // future.get() blocks on response
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();

            if (documents.size() > 0) {
                System.out.println("Outing....");

                for (QueryDocumentSnapshot document : documents) {

                    if(document.getData().get("User").equals(currUser) && document.getData().get("Name").equals(p.getName()) && document.getData().get("Major").equals(p.getMajor()) && Integer.parseInt(document.getData().get("Age").toString()) == p.getAge()) {
                        DocumentReference docRef = App.fstore.collection("References").document(document.getId());
                        docRef.delete();
                    }
                }

            } else {
                System.out.println("No data");
            }

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        data.remove(p);
    }

    @FXML
    protected void selectedItemTV(MouseEvent mouseEvent) {
        Person p = tv.getSelectionModel().getSelectedItem();
        name.setText(p.getName());
        age.setText(String.valueOf(p.getAge()));
        major.setText(p.getMajor());
    }

    @FXML
    protected void close() {
        System.exit(0);
    }

    @FXML
    protected void showImage() {
        File file= (new FileChooser()).showOpenDialog(img_view.getScene().getWindow());
        if(file!=null){
            img_view.setImage(new Image(file.toURI().toString()));
        }
    }
}