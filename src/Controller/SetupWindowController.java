package Controller;

import Model.Player;
import Model.SceneChanger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SetupWindowController implements Initializable {

    private static final ArrayList<Player> playerArrayList = new ArrayList<>();

    public static ArrayList<Player> getPlayerArrayList() {
        return playerArrayList;
    }

    @FXML
    Button addPlayer;
    @FXML
    private TextField nameOfPlayer;
    @FXML
    private ListView<Player> playersListView = new ListView<>();
    @FXML
    private Label errorMessageLabel;
    @FXML
    private ContextMenu listOfPlayersContextMenu;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        listOfPlayersContextMenu = new ContextMenu();
        MenuItem deletePlayer = new MenuItem("Fjern spiller");
        deletePlayer.setOnAction(event -> {
            Player player = playersListView.getSelectionModel().getSelectedItem();
            playerArrayList.remove(player);
            updateListOfPlayers();
        });

        playersListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Player> call(ListView<Player> playerListView) {
                ListCell<Player> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(Player player, boolean b) {
                        super.updateItem(player, b);
                        if(b){
                            setText(null);
                        }else{
                            setText(player.getName());
                        }
                    }
                };
                cell.emptyProperty().addListener((observableValue, aBoolean, t1) -> {
                    if(t1){
                        cell.setContextMenu(null);
                    }else{
                        cell.setContextMenu(listOfPlayersContextMenu);
                    }
                });
                return cell;
            }
        });

        listOfPlayersContextMenu.getItems().addAll(deletePlayer);
        updateListOfPlayers();
        playersListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void addPlayerButtonPressed(){
        errorMessageLabel.setText("");
        if(!nameOfPlayer.getText().isEmpty()) {
            String name = nameOfPlayer.getText().trim();
            Player player = new Player(name);
            playerArrayList.add(player);
            updateListOfPlayers();
            nameOfPlayer.clear();
        }else{
            errorMessageLabel.setText("Navnfeltet kan ikke være tomt");
        }
    }

    public void updateListOfPlayers(){
        ObservableList<Player> playersObservableList = FXCollections.observableArrayList(getPlayerArrayList());
        playersListView.setItems(playersObservableList);
    }

    @FXML
    public void nextButtonPressed(ActionEvent event) throws IOException {
        errorMessageLabel.setText("");
        if(playerArrayList.isEmpty()){
            errorMessageLabel.setText("Spillet krever minst en spiller");
        }else{
            SceneChanger sceneChanger = new SceneChanger();
            sceneChanger.changeScenes(event, "/View/categoryWindow.fxml", "Choose Category");
        }
    }

}
