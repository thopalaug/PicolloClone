package Controller;

import Model.Player;
import Model.Question;
import Model.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.sql.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class GameWindowController implements Initializable {

    public static final String DATABASE_NAME = "questions.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\ThomasPalme\\Desktop\\JavaSpringMasterClass\\PicolloClone\\" + DATABASE_NAME;
    public static String QUERY;

    private static ArrayList<Question> listOfQuestions = new ArrayList<>();
    private final ArrayList<Player> PlayerList = new ArrayList<>();

    @FXML
    TextArea questionTextArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PlayerList.addAll(SetupWindowController.getPlayerArrayList());

        QUERY = "SELECT * FROM Questions WHERE categoryChooser = " + CategoryWindowController.DBchooser;
        getQuestionsFromDB();

        questionTextArea.setEditable(false);
        questionTextArea.setWrapText(true);
        questionTextArea.setText(nextQuestion());

    }

    @FXML
    public void nextQuestionButtonPressed(){
        questionTextArea.setText(nextQuestion());
    }

    private String nextQuestion(){
        Random random = new Random();
        int randomQuestion = random.nextInt(listOfQuestions.size());
        int randomPlayer = random.nextInt(PlayerList.size());

        Question currentQuestion = listOfQuestions.get(randomQuestion);
        Player currentPlayer = PlayerList.get(randomPlayer);

        return String.format(currentQuestion.getQuestionToAsk(),currentPlayer.getName());
    }

    @FXML
    public void quitGameButtonPressed(ActionEvent event) throws IOException {
        questionTextArea.clear();
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScenes(event, "/View/setupWindow.fxml", "");
    }

    private void getQuestionsFromDB(){
        listOfQuestions.clear();
        try(Connection conn = DriverManager.getConnection(CONNECTION_STRING); Statement statement = conn.createStatement(); ResultSet resultSet = statement.executeQuery(QUERY)){
            while (resultSet.next()){
                Question question = new Question(resultSet.getString("question"));
                listOfQuestions.add(question);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


}
