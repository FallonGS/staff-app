import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.*;

public class StaffApp extends Application {

    private TextField tfID = new TextField();
    private TextField tfLastName = new TextField();
    private TextField tfFirstName = new TextField();
    private TextField tfMI = new TextField();
    private TextField tfAddress = new TextField();
    private TextField tfCity = new TextField();
    private TextField tfState = new TextField();
    private TextField tfTelephone = new TextField();
    private TextField tfEmail = new TextField();

    private PreparedStatement psView;
    private PreparedStatement psInsert;
    private PreparedStatement psUpdate;

    @Override
    public void start(Stage primaryStage) {
        initializeDB();

        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10));
        pane.setHgap(5);
        pane.setVgap(5);

        pane.add(new Label("ID"), 0, 0); pane.add(tfID, 1, 0);
        pane.add(new Label("Last Name"), 0, 1); pane.add(tfLastName, 1, 1);
        pane.add(new Label("First Name"), 0, 2); pane.add(tfFirstName, 1, 2);
        pane.add(new Label("MI"), 0, 3); pane.add(tfMI, 1, 3);
        pane.add(new Label("Address"), 0, 4); pane.add(tfAddress, 1, 4);
        pane.add(new Label("City"), 0, 5); pane.add(tfCity, 1, 5);
        pane.add(new Label("State"), 0, 6); pane.add(tfState, 1, 6);
        pane.add(new Label("Telephone"), 0, 7); pane.add(tfTelephone, 1, 7);
        pane.add(new Label("Email"), 0, 8); pane.add(tfEmail, 1, 8);

        Button btView = new Button("View");
        Button btInsert = new Button("Insert");
        Button btUpdate = new Button("Update");

        pane.add(btView, 0, 9);
        pane.add(btInsert, 1, 9);
        pane.add(btUpdate, 2, 9);

        btView.setOnAction(e -> viewRecord());
        btInsert.setOnAction(e -> insertRecord());
        btUpdate.setOnAction(e -> updateRecord());

        Scene scene = new Scene(pane, 450, 400);
        primaryStage.setTitle("Staff Database App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/javabook", "scott", "tiger");

            psView = connection.prepareStatement(
                    "SELECT * FROM Staff WHERE id = ?");

            psInsert = connection.prepareStatement(
                    "INSERT INTO Staff VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            psUpdate = connection.prepareStatement(
                    "UPDATE Staff SET lastName=?, firstName=?, mi=?, address=?, " +
                    "city=?, state=?, telephone=?, email=? WHERE id=?");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void viewRecord() {
        try {
            psView.setString(1, tfID.getText());
            ResultSet rs = psView.executeQuery();

            if (rs.next()) {
                tfLastName.setText(rs.getString("lastName"));
                tfFirstName.setText(rs.getString("firstName"));
                tfMI.setText(rs.getString("mi"));
                tfAddress.setText(rs.getString("address"));
                tfCity.setText(rs.getString("city"));
                tfState.setText(rs.getString("state"));
                tfTelephone.setText(rs.getString("telephone"));
                tfEmail.setText(rs.getString("email"));
            } else {
                clearFields();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insertRecord() {
        try {
            psInsert.setString(1, tfID.getText());
            psInsert.setString(2, tfLastName.getText());
            psInsert.setString(3, tfFirstName.getText());
            psInsert.setString(4, tfMI.getText());
            psInsert.setString(5, tfAddress.getText());
            psInsert.setString(6, tfCity.getText());
            psInsert.setString(7, tfState.getText());
            psInsert.setString(8, tfTelephone.getText());
            psInsert.setString(9, tfEmail.getText());

            psInsert.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateRecord() {
        try {
            psUpdate.setString(1, tfLastName.getText());
            psUpdate.setString(2, tfFirstName.getText());
            psUpdate.setString(3, tfMI.getText());
            psUpdate.setString(4, tfAddress.getText());
            psUpdate.setString(5, tfCity.getText());
            psUpdate.setString(6, tfState.getText());
            psUpdate.setString(7, tfTelephone.getText());
            psUpdate.setString(8, tfEmail.getText());
            psUpdate.setString(9, tfID.getText());

            psUpdate.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        tfLastName.clear();
        tfFirstName.clear();
        tfMI.clear();
        tfAddress.clear();
        tfCity.clear();
        tfState.clear();
        tfTelephone.clear();
        tfEmail.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
