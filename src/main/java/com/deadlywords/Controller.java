package com.deadlywords;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class Controller implements Initializable {


    @FXML
    private TextField textField;

    @FXML
    private TabPane tabPane;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label labelStatus;


    private String homepage = Settings.getInstance().getHomepage();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tabPane.getSelectionModel().selectedItemProperty().addListener(e -> {
            textField.setText(((TabClass)(tabPane.getSelectionModel().getSelectedItem())).getUrl());
            labelStatus.setText("");
            progressBar.setVisible(false);
        });
        addTab();
    }

    public void loadPage(){
        String url = checkUrl(textField.getText());
        activeTab().setUrlAndLoad(url);
        textField.setText(url);
    }

    public void addTab(){
        TabClass tabclass = new TabClass(homepage, progressBar, labelStatus);
        tabPane.getTabs().add(tabclass);
        tabPane.getSelectionModel().select(tabclass);
    }

    public void close(){
        Platform.exit();
    }

    public void newWindow() throws IOException{
        Stage stage = new Stage();
        Scene scene = new Scene(DeadlyBrowser.loadFXML("scene"), 1400, 1000);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }



    public void loadHomepage(){
        activeTab().setUrlAndLoad(homepage);
        textField.setText(homepage);
    }

    public void setHomepage(){
        Settings.getInstance().setHomepage(activeTab().getUrl());
        homepage = Settings.getInstance().getHomepage();
    }

    // Homepage Dialog
    public void openSettings() {
        TextInputDialog textInputDialog = new TextInputDialog();

        textInputDialog.setTitle("Set your homepage");

        textInputDialog.getDialogPane().setContentText("Homepage:");

        Optional<String> result = textInputDialog.showAndWait();
        TextField input = textInputDialog.getEditor();
        if (input.getText() != null && input.getText().toString().length() != 0){
            Settings.getInstance().setHomepage(checkUrl(input.getText()));
            homepage = Settings.getInstance().getHomepage();
        }
        else
            System.out.println("no entry");
    }

    public String checkUrl(String url){
        if(url.startsWith("http://")|| url.startsWith("https://")) {
            return url;
        }else{
            return "https://" + url;
        }
    }

    public TabClass activeTab(){
            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
            TabClass tabClass = (TabClass) selectionModel.getSelectedItem();
        return tabClass;
    }


    public void aboutPressed(){
        activeTab().setText("About");
        activeTab().getWebView().getEngine().loadContent(
                "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "    <title>About DeadlyBrowser</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "\n" +
                        "<h1>Assessment for the course 'Object oriented programming'</h1>\n" +
                        "<p>By Christoph Kreutzer MatrNr. 00136611</p>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>");
        activeTab().setGraphic(null);
    }


}

