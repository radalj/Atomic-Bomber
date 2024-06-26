package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.User;
import view.Main;

import java.net.URL;
import java.util.ArrayList;

public class RankingController {
    private int sortTypeInt = 0; // 0 for score, 1 for kills, 2 for difficultKills, 3 for accuracy
    public static Scene scene;
    @FXML
    private TableView<User> rankingTable;
    @FXML
    private ChoiceBox<String> sortType;

    public void initialize() {
        ApplicationController.playMusic();
        buildRanking(rankingTable);
        rankingTable.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>() {
                @Override
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);
                    if (getIndex() == 0 && !empty) {
                        setStyle("-fx-background-color: #FFD700;");
                    } else if (getIndex() == 1 && !empty) {
                        setStyle("-fx-background-color: #C0C0C0;");
                    } else if (getIndex() == 2 && !empty) {
                        setStyle("-fx-background-color: #CD7F32;");
                    } else {
                        setStyle("");
                    }
                }
            };
            row.setPrefHeight(50);
            return row;
        });
        sortType.getItems().addAll("Score", "Kills", "Difficulty Kills", "Accuracy");
        sortType.setValue("Score");
        sortType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Score" -> setSortType(0);
                case "Kills" -> setSortType(1);
                case "Difficulty Kills" -> setSortType(2);
                default -> setSortType(3);
            }
            buildRanking(rankingTable);
        });
    }

    public void start() {
        URL url = Main.class.getResource("/FXML/Ranking.fxml");
        assert url != null;
        Pane root;
        try {
            root = FXMLLoader.load(url);
        } catch (Exception e) {
            return;
        }
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/CSS/Styles.css").toExternalForm());
        ApplicationController.setScene(scene);
    }

    public ArrayList<User> findRanking() {
        ArrayList<User> users = switch (sortTypeInt) {
            case 0 -> User.sortUsersByScore();
            case 1 -> User.sortUsersByKills();
            case 2 -> User.sortUsersByDifficultKills();
            default -> User.sortUsersByAccuracy();
        };
        for (int i = 0; i < users.size(); i++)
            users.get(i).setRank(i + 1);
        while (users.size() > 10)
            users.removeLast();
        rankingTable.refresh();
        return users;
    }

    public void buildRanking(TableView<User> rankingTable) {
        rankingTable.getItems().clear();
        ArrayList<User> users = findRanking();
        ObservableList<User> playerRankings = FXCollections.observableArrayList(users);
        TableColumn<User, Integer> rankColumn = new TableColumn<>("Rank");
        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        TableColumn<User, Integer> scoreColumn = new TableColumn<>("Score");
        TableColumn<User, Integer> killsColumn = new TableColumn<>("Kills");
        TableColumn<User, Integer> difficultyKillsColumn = new TableColumn<>("Difficulty Kills");
        TableColumn<User, Double> accuracyColumn = new TableColumn<>("Accuracy");
        TableColumn<User, Integer> waveColumn = new TableColumn<>("Wave");

        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        killsColumn.setCellValueFactory(new PropertyValueFactory<>("kills"));
        difficultyKillsColumn.setCellValueFactory(new PropertyValueFactory<>("difficultyKills"));
        accuracyColumn.setCellValueFactory(new PropertyValueFactory<>("accuracy"));
        waveColumn.setCellValueFactory(new PropertyValueFactory<>("wave"));

        rankColumn.setPrefWidth(100);
        usernameColumn.setPrefWidth(200);
        scoreColumn.setPrefWidth(120);
        killsColumn.setPrefWidth(120);
        difficultyKillsColumn.setPrefWidth(120);
        accuracyColumn.setPrefWidth(120);
        waveColumn.setPrefWidth(120);

        rankingTable.getColumns().setAll(rankColumn, usernameColumn, scoreColumn, killsColumn, difficultyKillsColumn, accuracyColumn, waveColumn);
        rankingTable.setItems(playerRankings);
    }

    public void setSortType(int type) {
        sortTypeInt = type;
    }

    public void back() {
        ApplicationController.setScene(MainMenuController.scene);
    }
}
