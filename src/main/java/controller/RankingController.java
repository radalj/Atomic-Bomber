package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.User;

import java.util.ArrayList;

public class RankingController {
    private int sortType = 0; // 0 for score, 1 for kills, 2 for difficultKills, 3 for accuracy
    public ArrayList<User> findRanking() {
        ArrayList<User> users;
        if (sortType == 0) {
            users = User.sortUsersByScore();
        } else if (sortType == 1) {
            users = User.sortUsersByKills();
        } else if (sortType == 2) {
            users = User.sortUsersByDifficultKills();
        } else {
            users = User.sortUsersByAccuracy();
        }
        while (users.size() > 10) {
            users.remove(users.size() - 1);
        }
        return users;
    }
    public void buildRanking(TableView<User> rankingTable) {
        rankingTable.getItems().clear();
        ArrayList<User> users = findRanking();
        for (int i = 0; i < users.size(); i++) {
            users.get(i).setRank(i + 1);
        }
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
        sortType = type;
    }
}
