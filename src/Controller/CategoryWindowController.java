package Controller;

import Model.Question;
import Model.QuestionCategory;
import Model.SceneChanger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CategoryWindowController implements Initializable {

    public static final String DATABASE_NAME = "questions.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\ThomasPalme\\Desktop\\JavaSpringMasterClass\\PicolloClone\\" + DATABASE_NAME;
    public static final String QUERY_CATEGORIES = "SELECT * FROM Categories";

    protected static int DBchooser = 0;

    @FXML
    TextArea categoryInfoTextArea;

    @FXML
    ChoiceBox<QuestionCategory> categoryChoiceBox;

    ObservableList<QuestionCategory> categories = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCategoriesFromDB();
        categoryInfoTextArea.setWrapText(true);
        categoryInfoTextArea.setEditable(false);
        categoryChoiceBox.setItems(categories);
        categoryChoiceBox.getSelectionModel().select(0);
        categoryInfoTextArea.setText(categories.get(0).getShortDescription());

        categoryChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) ->
                categoryInfoTextArea.setText(categories.get(categoryChoiceBox.getSelectionModel().getSelectedIndex()).getShortDescription())
        );
        categoryChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) ->
            DBchooser = categoryChoiceBox.getSelectionModel().getSelectedIndex()
        );
    }

    @FXML
    public void backButtonPressed(ActionEvent event) throws IOException {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScenes(event, "/View/setupWindow.fxml", "");
    }

    @FXML
    public void playButtonPressed(ActionEvent event) throws IOException{
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScenes(event, "/View/gameWindow.fxml", "");
    }

    private void getCategoriesFromDB(){
        try(Connection conn = DriverManager.getConnection(CONNECTION_STRING); Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(QUERY_CATEGORIES)){
            while (resultSet.next()){
                QuestionCategory questionCategory = new QuestionCategory(resultSet.getString("CategoryName"), resultSet.getString("ShortDescription") );
                categories.add(questionCategory);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }




}
