package HotelReservation;

// This code is intended for use for receptions to book rooms and manage rooms 
// receptionists can create there on account by signing up then they can login everytime 

import java.math.BigDecimal;
import javafx.application.Application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class HotelReservation extends Application {
    
    public Scene createLoginScene(Stage PrimaryStage) {
        GridPane gp1 = new GridPane();
        gp1.setHgap(10);
        gp1.setVgap(10);
        gp1.setAlignment(Pos.CENTER);
        gp1.setMaxSize(500, 400);

        Text loginText = new Text("Login");
        loginText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        GridPane.setHalignment(loginText, HPos.CENTER);
        GridPane.setValignment(loginText, VPos.CENTER);
        Label usernameLabel = new Label("Email");
        TextField usernameTextField = new TextField();
        Label passwordLabel = new Label("Password");
        PasswordField passwordTextField = new PasswordField();
        Button loginBtn = new Button("Login");

        loginBtn.setStyle("-fx-font-size: 18px; -fx-background-color: #3498db; -fx-text-fill: white;");
        loginBtn.addEventHandler(ActionEvent.ACTION, e -> {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();

            if (authenticateUser(username, password)) {
                PrimaryStage.setScene(createHomeScene(PrimaryStage));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText("Invalid Credentials");
                alert.setContentText("Please enter the correct username and password.");
                alert.showAndWait();
            }
        });

        gp1.add(loginText, 0, 0);
        gp1.add(usernameLabel, 0, 1);
        gp1.add(usernameTextField, 0, 2);
        gp1.add(passwordLabel, 0, 3);
        gp1.add(passwordTextField, 0, 4);
        gp1.add(loginBtn, 0, 5);

        GridPane bottomgp = new GridPane();
        Text noAccountText = new Text("Don't have an account?");
        Hyperlink signUpLink = new Hyperlink("Sign up");

        signUpLink.addEventHandler(ActionEvent.ACTION, e -> {
            PrimaryStage.setScene(createSignUpScene(PrimaryStage));
        });

        bottomgp.add(noAccountText, 0, 0);
        bottomgp.add(signUpLink, 1, 0);
        bottomgp.setAlignment(Pos.CENTER);
        bottomgp.setHgap(5);

        gp1.add(bottomgp, 0, 6);

        StackPane root = new StackPane(gp1);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #F5F5DC;");
        gp1.setStyle("-fx-border-color: black; " + "-fx-border-width: 1; " + "-fx-border-radius: 5; " + "-fx-padding: 10;" + "-fx-background-color: white;");

        return new Scene(root, 700, 700);
    }
    
    private boolean authenticateUser(String email, String password) {
        String url = "jdbc:mysql://localhost:3306/hotel_booking";
        String user = "root";
        String pass = "";

        String query = "SELECT COUNT(*) FROM users WHERE email = '" + email + "' AND password = '" + password + "'";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            showAlert("Database Error", "There was an issue with the database: " + e.getMessage());
        }

        return false;
    }
   
    public Scene createSignUpScene(Stage primaryStage) {
        GridPane gp2 = new GridPane();
        gp2.setHgap(10);
        gp2.setVgap(10);
        gp2.setAlignment(Pos.CENTER);
        gp2.setMaxSize(500, 400);

        Text signUpText = new Text("Sign Up");
        signUpText.setFont(Font.font("Arial", FontWeight.BOLD, 40)); 
        GridPane.setHalignment(signUpText, HPos.CENTER); 
        GridPane.setValignment(signUpText, VPos.CENTER);

        Label firstnameLabel = new Label("First Name");
        TextField firstnameTextField = new TextField();
        Label lastnameLabel = new Label("Last Name");
        TextField lastnameTextField = new TextField();
        Label emailLabel = new Label("Email");
        TextField emailTextField = new TextField();
        Label phoneNoLabel = new Label("Phone Number");
        TextField phoneNoTextField = new TextField();
        Label passwordLabel = new Label("Password");
        PasswordField passwordTextField = new PasswordField();
        Label confirmPasswordLabel = new Label("Confirm Password");
        PasswordField confirmPasswordTextField = new PasswordField();
        Button signUpBtn = new Button("Sign Up");

        gp2.add(signUpText, 0, 0);
        gp2.add(firstnameLabel, 0, 1);
        gp2.add(firstnameTextField, 0, 2);
        gp2.add(lastnameLabel, 0, 3);
        gp2.add(lastnameTextField, 0, 4);
        gp2.add(emailLabel, 0, 5);
        gp2.add(emailTextField, 0, 6);
        gp2.add(phoneNoLabel, 0, 7);
        gp2.add(phoneNoTextField, 0, 8);
        gp2.add(passwordLabel, 0, 9);
        gp2.add(passwordTextField, 0, 10);
        gp2.add(confirmPasswordLabel, 0, 11);
        gp2.add(confirmPasswordTextField, 0, 12);
        gp2.add(signUpBtn, 0, 13);

        GridPane bottomgp = new GridPane();
        Text haveAccountText = new Text("Already have an account?");
        Hyperlink backToLoginLink = new Hyperlink("Log in");
        backToLoginLink.addEventHandler(ActionEvent.ACTION, (ActionEvent e) -> {
            primaryStage.setScene(createLoginScene(primaryStage));
        });

        bottomgp.add(haveAccountText, 0, 0);
        bottomgp.add(backToLoginLink, 1, 0);
        bottomgp.setAlignment(Pos.CENTER);
        bottomgp.setHgap(5);

        gp2.add(bottomgp, 0, 14);

        signUpBtn.setStyle("-fx-font-size: 18px; -fx-background-color: #3498db; -fx-text-fill: white;");
        signUpBtn.setOnAction(e -> {
            String firstname = firstnameTextField.getText();
            String lastname = lastnameTextField.getText();
            String email = emailTextField.getText();
            String phoneNo = phoneNoTextField.getText();
            String password = passwordTextField.getText();
            String confirmPassword = confirmPasswordTextField.getText();

            if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || phoneNo.isEmpty() || password.isEmpty()) {
                showAlert("Error", "All fields are required.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showAlert("Error", "Passwords do not match.");
                return;
            }

            addUserToDatabase(firstname, lastname, email, phoneNo, password, primaryStage);
        });

        StackPane root = new StackPane(gp2);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #F5F5DC;");
        gp2.setStyle("-fx-border-color: black; " + "-fx-border-width: 1; " + "-fx-border-radius: 5; " + "-fx-padding: 10;" + "-fx-background-color: white;");

        return new Scene(root, 700, 700);
    }

     
    private void addUserToDatabase(String firstname, String lastname, String email, String phoneNo, String password, Stage PrimaryStage) {
        String url = "jdbc:mysql://localhost:3306/hotel_booking"; 
        String user = "root";  
        String pass = "";      

        String query = "INSERT INTO users (firstname, lastname, email, phone_no, password) VALUES ('" 
                       + firstname + "', '" 
                       + lastname + "', '" 
                       + email + "', '" 
                       + phoneNo + "', '" 
                       + password + "')";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement()) {

            int result = statement.executeUpdate(query);

            if (result > 0) {
                showAlert("Success", "User signed up successfully!");
                PrimaryStage.setScene(createHomeScene(PrimaryStage));
            } else {
                showAlert("Error", "Failed to sign up user.");
            }
        } catch (SQLException e) {
            showAlert("Database Error", "There was an issue with the database: " + e.getMessage());
        }
    }

    public Scene createHomeScene(Stage primaryStage) {
        MenuBar menuBar = createMenu(primaryStage);

        Text welcomeText = new Text("Welcome to the Hotel Booking System!");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        welcomeText.setFill(Color.web("#2c3e50"));

        Label availableRoomsLabel = new Label("Available Rooms:");
        availableRoomsLabel.setTextFill(Color.web("#34495e"));

        ListView<String> availableRoomsListView = new ListView<>();
        fetchAvailableRooms(availableRoomsListView);

        Button bookRoomButton = new Button("Book Selected Room");
        bookRoomButton.setStyle("-fx-font-size: 18px; -fx-background-color: #3498db; -fx-text-fill: white;");
        bookRoomButton.setOnAction(e -> {
            String selectedRoom = availableRoomsListView.getSelectionModel().getSelectedItem();
            if (selectedRoom != null) {
                String roomNumber = selectedRoom.split(" ")[1];
                primaryStage.setScene(createRoomBookingPage(primaryStage, roomNumber));
            } else {
                showAlert("Error", "Please select a room to book.");
            }
        });

        Label bookedRoomsLabel = new Label("Booked Rooms:");
        bookedRoomsLabel.setTextFill(Color.web("#34495e"));

        ListView<String> bookedRoomsListView = new ListView<>();
        fetchBookedRooms(bookedRoomsListView);

        Button freeRoomButton = new Button("Free Selected Room");
        freeRoomButton.setStyle("-fx-font-size: 18px; -fx-background-color: #e74c3c; -fx-text-fill: white;");
        freeRoomButton.setOnAction(e -> {
            String selectedRoom = bookedRoomsListView.getSelectionModel().getSelectedItem();
            if (selectedRoom != null) {
                String roomNumber = selectedRoom.split(" ")[1];

                freeUpRoom(roomNumber);

                bookedRoomsListView.getItems().remove(selectedRoom);

                fetchBookedRooms(bookedRoomsListView);

                fetchAvailableRooms(availableRoomsListView);

                showAlert("Room Freed", "Room " + roomNumber + " has been freed and is now available.");
            } else {
                showAlert("Error", "Please select a room to free up.");
            }
        });

        VBox availableRoomsBox = new VBox(10, availableRoomsLabel, availableRoomsListView, bookRoomButton);

        VBox bookedRoomsBox = new VBox(10, bookedRoomsLabel, bookedRoomsListView, freeRoomButton);

        VBox centerBox = new VBox(20, welcomeText, availableRoomsBox, bookedRoomsBox);
        centerBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(centerBox);
        root.setStyle("-fx-background-color: #ecf0f1;");

        return new Scene(root, 700, 700);
    }

    public Scene createRoomBookingPage(Stage primaryStage, String selectedRoom) {
        MenuBar menuBar = createMenu(primaryStage);

        Label roomLabel = new Label("Room: " + selectedRoom);
        roomLabel.setTextFill(Color.web("#34495e"));

        Label checkInLabel = new Label("Check-in Date:");
        checkInLabel.setTextFill(Color.web("#34495e"));
        DatePicker checkInDatePicker = new DatePicker();

        Label checkOutLabel = new Label("Check-out Date:");
        checkOutLabel.setTextFill(Color.web("#34495e"));
        DatePicker checkOutDatePicker = new DatePicker();

        Label guestsLabel = new Label("Number of Guests:");
        guestsLabel.setTextFill(Color.web("#34495e"));
        Spinner<Integer> guestsSpinner = new Spinner<>(1, 10, 1);

        Button confirmBookingButton = new Button("Confirm Booking");
        confirmBookingButton.setStyle("-fx-font-size: 18px; -fx-background-color: #3498db; -fx-text-fill: white;");
        confirmBookingButton.setOnAction(e -> {
            String checkInDate = checkInDatePicker.getValue().toString();
            String checkOutDate = checkOutDatePicker.getValue().toString();
            int numGuests = guestsSpinner.getValue();

            bookRoom(selectedRoom, checkInDate, checkOutDate, numGuests);

            showAlert("Booking Confirmation", "You have booked the room from " + checkInDate + " to " + checkOutDate + " for " + numGuests + " guests.");

            primaryStage.setScene(createHomeScene(primaryStage));
        });

        VBox bookingForm = new VBox(10, roomLabel, checkInLabel, checkInDatePicker, checkOutLabel, checkOutDatePicker, guestsLabel, guestsSpinner, confirmBookingButton);
        bookingForm.setAlignment(Pos.CENTER);
        bookingForm.setStyle("-fx-padding: 20;");

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(bookingForm);
        root.setStyle("-fx-background-color: #ecf0f1;");

        return new Scene(root, 700, 700);
    }

    public void fetchAvailableRooms(ListView<String> availableRoomsListView) {
        String url = "jdbc:mysql://localhost:3306/hotel_booking";
        String user = "root";
        String pass = "";

        String query = "SELECT room_number, room_type, price FROM rooms WHERE availability = TRUE";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            availableRoomsListView.getItems().clear();

            while (resultSet.next()) {
                String roomNumber = resultSet.getString("room_number");
                String roomType = resultSet.getString("room_type");
                double price = resultSet.getDouble("price");
                availableRoomsListView.getItems().add("Room " + roomNumber + " (" + roomType + ") - " + price + " USD");
            }

        } catch (SQLException e) {
            showAlert("Database Error", "There was an issue with fetching the available rooms: " + e.getMessage());
        }
    }

    public void fetchBookedRooms(ListView<String> bookedRoomsListView) {
        String url = "jdbc:mysql://localhost:3306/hotel_booking";
        String user = "root";
        String pass = "";

        String query = "SELECT room_number, room_type FROM rooms WHERE availability = FALSE";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            bookedRoomsListView.getItems().clear();

            while (resultSet.next()) {
                String roomNumber = resultSet.getString("room_number");
                String roomType = resultSet.getString("room_type");

                String roomDetails = "Room " + roomNumber + " (" + roomType + ") - Booked";

                bookedRoomsListView.getItems().add(roomDetails);
            }

            if (bookedRoomsListView.getItems().isEmpty()) {
                bookedRoomsListView.getItems().add("No booked rooms found.");
            }

        } catch (SQLException e) {
            showAlert("Database Error", "There was an issue with fetching the booked rooms: " + e.getMessage());
        }
    }

    private void bookRoom(String roomNumber, String checkInDate, String checkOutDate, int numGuests) {
        String url = "jdbc:mysql://localhost:3306/hotel_booking";
        String user = "root";
        String pass = "";

        String query = "UPDATE rooms SET availability = FALSE WHERE room_number = '" + roomNumber + "'";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement()) {

            int result = statement.executeUpdate(query);

            if (result > 0) {
                String bookingQuery = "INSERT INTO bookings (room_number, check_in_date, check_out_date, num_guests) " +
                                      "VALUES ('" + roomNumber + "', '" + checkInDate + "', '" + checkOutDate + "', " + numGuests + ")";
                statement.executeUpdate(bookingQuery);
            }

        } catch (SQLException e) {
            showAlert("Database Error", "There was an issue with the database: " + e.getMessage());
        }
    }

    public void freeUpRoom(String roomNumber) {
        String url = "jdbc:mysql://localhost:3306/hotel_booking";
        String user = "root";
        String pass = "";

        String query = "UPDATE rooms SET availability = TRUE WHERE room_number = '" + roomNumber + "'";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement()) {

            int rowsAffected = statement.executeUpdate(query);

            if (rowsAffected > 0) {
                System.out.println("Room " + roomNumber + " has been freed.");
            } else {
                showAlert("Error", "No room found with number " + roomNumber + ".");
            }

        } catch (SQLException e) {
            showAlert("Database Error", "There was an issue with freeing up the room: " + e.getMessage());
        }
    }

    public Scene createAddRoomScene(Stage primaryStage) {
        MenuBar menuBar = createMenu(primaryStage);

        Label roomNumberLabel = new Label("Room Number:");
        TextField roomNumberField = new TextField();
        roomNumberField.setPromptText("e.g., R011");

        Label roomTypeLabel = new Label("Room Type:");
        TextField roomTypeField = new TextField();
        roomTypeField.setPromptText("e.g., Single");

        Label bedCountLabel = new Label("Bed Count:");
        TextField bedCountField = new TextField();
        bedCountField.setPromptText("e.g., 1");

        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();
        priceField.setPromptText("e.g., 100.00");

        Label availabilityLabel = new Label("Availability:");
        ToggleGroup availabilityGroup = new ToggleGroup();
        RadioButton availableRadioButton = new RadioButton("Available");
        availableRadioButton.setToggleGroup(availabilityGroup);
        availableRadioButton.setSelected(true);
        RadioButton unavailableRadioButton = new RadioButton("Unavailable");
        unavailableRadioButton.setToggleGroup(availabilityGroup);

        Button addRoomButton = new Button("Add Room");
        addRoomButton.setStyle("-fx-font-size: 18px; -fx-background-color: #3498db; -fx-text-fill: white;");
        addRoomButton.setOnAction(e -> {
            String roomNumber = roomNumberField.getText();
            String roomType = roomTypeField.getText();
            String bedCountText = bedCountField.getText();
            String priceText = priceField.getText();
            boolean availability = availableRadioButton.isSelected();

            if (roomNumber.isEmpty() || roomType.isEmpty() || bedCountText.isEmpty() || priceText.isEmpty()) {
                showAlert("Error", "Please fill in all fields.");
            } else {
                try {
                    int bedCount = Integer.parseInt(bedCountText);
                    BigDecimal price = new BigDecimal(priceText);

                    addRoomToDatabase(roomNumber, roomType, bedCount, price, availability);

                    roomNumberField.clear();
                    roomTypeField.clear();
                    bedCountField.clear();
                    priceField.clear();
                    availabilityGroup.selectToggle(availableRadioButton);

                    showAlert("Success", "Room added successfully.");
                } catch (NumberFormatException ex) {
                    showAlert("Error", "Please enter valid numbers for bed count and price.");
                }
            }
        });
        VBox formBox = new VBox(10, roomNumberLabel, roomNumberField, roomTypeLabel, roomTypeField, bedCountLabel, bedCountField, 
                                priceLabel, priceField, availabilityLabel, availableRadioButton, unavailableRadioButton, addRoomButton);
        formBox.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(formBox);
        root.setStyle("-fx-background-color: #ecf0f1;");

        return new Scene(root, 700, 700);
    }

    public void addRoomToDatabase(String roomNumber, String roomType, int bedCount, BigDecimal price, boolean availability) {
        String url = "jdbc:mysql://localhost:3306/hotel_booking";
        String user = "root";
        String pass = "";

        String query = "INSERT INTO rooms (room_number, room_type, bed_count, price, availability) " +
                       "VALUES ('" + roomNumber + "', '" + roomType + "', " + bedCount + ", " + price + ", " + availability + ")";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Room added successfully.");
        } catch (SQLException e) {
            showAlert("Database Error", "Error while adding room to the database: " + e.getMessage());
        }
    }
    
    public Scene createUpdateRoomScene(Stage primaryStage) {
        MenuBar menuBar = createMenu(primaryStage);

        Label roomNumberLabel = new Label("Room Number:");
        TextField roomNumberField = new TextField();
        roomNumberField.setPromptText("e.g., R001");

        Label roomTypeLabel = new Label("Room Type:");
        TextField roomTypeField = new TextField();

        Label bedCountLabel = new Label("Bed Count:");
        TextField bedCountField = new TextField();

        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();

        Label availabilityLabel = new Label("Availability:");
        ToggleGroup availabilityGroup = new ToggleGroup();
        RadioButton availableRadioButton = new RadioButton("Available");
        availableRadioButton.setToggleGroup(availabilityGroup);
        RadioButton unavailableRadioButton = new RadioButton("Unavailable");
        unavailableRadioButton.setToggleGroup(availabilityGroup);
        availableRadioButton.setSelected(true);

        Button updateRoomButton = new Button("Update Room");
        updateRoomButton.setStyle("-fx-font-size: 18px; -fx-background-color: #3498db; -fx-text-fill: white;");
        updateRoomButton.setOnAction(e -> {
            String roomNumber = roomNumberField.getText();
            String roomType = roomTypeField.getText();
            String bedCountText = bedCountField.getText();
            String priceText = priceField.getText();
            boolean availability = availableRadioButton.isSelected();

            try {
                int bedCount = Integer.parseInt(bedCountText);
                BigDecimal price = new BigDecimal(priceText);

                updateRoomInDatabase(roomNumber, roomType, bedCount, price, availability);
                showAlert("Success", "Room updated successfully.");

                roomNumberField.clear();
                roomTypeField.clear();
                bedCountField.clear();
                priceField.clear();
                availabilityGroup.selectToggle(availableRadioButton);
            } catch (NumberFormatException ex) {
                showAlert("Error", "Please enter valid numbers for bed count and price.");
            }
        });

        VBox formBox = new VBox(10, roomNumberLabel, roomNumberField, roomTypeLabel, roomTypeField, bedCountLabel, bedCountField, 
                                priceLabel, priceField, availabilityLabel, availableRadioButton, unavailableRadioButton, updateRoomButton);
        formBox.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(formBox);
        root.setStyle("-fx-background-color: #ecf0f1;");

        return new Scene(root, 700, 700);
    }
    
    public void updateRoomInDatabase(String roomNumber, String roomType, int bedCount, BigDecimal price, boolean availability) {
        String url = "jdbc:mysql://localhost:3306/hotel_booking";
        String user = "root";
        String pass = "";

        String query = "UPDATE rooms SET room_type = ?, bed_count = ?, price = ?, availability = ? WHERE room_number = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, roomType);
            pstmt.setInt(2, bedCount);
            pstmt.setBigDecimal(3, price);
            pstmt.setBoolean(4, availability);
            pstmt.setString(5, roomNumber);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Room updated successfully!");
            } else {
                System.out.println("No room found with the given room number.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to update room: " + e.getMessage());
        }
    }
    
    public Scene createDeleteRoomScene(Stage primaryStage) {
        MenuBar menuBar = createMenu(primaryStage);

        Label roomNumberLabel = new Label("Room Number to Delete:");
        TextField roomNumberField = new TextField();
        roomNumberField.setPromptText("e.g., R011");

        Button deleteRoomButton = new Button("Delete Room");
        deleteRoomButton.setStyle("-fx-font-size: 18px; -fx-background-color: #3498db; -fx-text-fill: white;");
        deleteRoomButton.setOnAction(e -> {
            String roomNumber = roomNumberField.getText();

            if (roomNumber.isEmpty()) {
                showAlert("Error", "Please enter the room number.");
            } else {
                deleteRoomFromDatabase(roomNumber);

                roomNumberField.clear();

                showAlert("Success", "Room " + roomNumber + " deleted successfully.");
            }
        });

        VBox formBox = new VBox(10, roomNumberLabel, roomNumberField, deleteRoomButton);
        formBox.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(formBox);
        root.setStyle("-fx-background-color: #ecf0f1;");

        return new Scene(root, 700, 700);
    }

    private void deleteRoomFromDatabase(String roomNumber) {
        String url = "jdbc:mysql://localhost:3306/hotel_booking";
        String user = "root";
        String pass = "";

        String query = "DELETE FROM rooms WHERE room_number = ?";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, roomNumber);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Room " + roomNumber + " deleted successfully.");
            } else {
                showAlert("Error", "Room not found.");
            }

        } catch (SQLException e) {
            showAlert("Database Error", "There was an issue deleting the room: " + e.getMessage());
        }
    }
    
    public Scene createProjectMembersScene(Stage primaryStage) {
        MenuBar menuBar = createMenu(primaryStage);

        Label membersTitle = new Label("Project Members:");
        membersTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 10;");

        VBox memberList = new VBox(10);
        memberList.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20;");

        Label member1 = new Label("Natnael Samuel - 1125/15");
        Label member2 = new Label("Amanuel Wondimu - 0158/15");
        Label member3 = new Label("Munira Jemal - 1081/15");
        Label member4 = new Label("Senay Abreha - 1286/15");
        Label member5 = new Label("Kasahun Bekele - 0817/15");
        Label member6 = new Label("Firdos Mohammed - 0593/15");

        member1.setStyle("-fx-font-size: 16px; -fx-padding: 5;");
        member2.setStyle("-fx-font-size: 16px; -fx-padding: 5;");
        member3.setStyle("-fx-font-size: 16px; -fx-padding: 5;");
        member4.setStyle("-fx-font-size: 16px; -fx-padding: 5;");
        member5.setStyle("-fx-font-size: 16px; -fx-padding: 5;");
        member6.setStyle("-fx-font-size: 16px; -fx-padding: 5;");
        memberList.getChildren().addAll(member1, member2, member3, member4, member5, member6);

        VBox root = new VBox(20, menuBar, membersTitle, memberList);
        root.setStyle("-fx-background-color: #ecf0f1;");

        return new Scene(root, 700, 700);
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public MenuBar createMenu(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();

        Menu homeMenu = new Menu("Home");
        Menu aboutMenu = new Menu("About Us");
        Menu roomManagementMenu = new Menu("Room Management");
        Menu exitMenu = new Menu("Exit");

        MenuItem homeItem = new MenuItem("Home");
        homeItem.setOnAction(e -> primaryStage.setScene(createHomeScene(primaryStage)));
        homeMenu.getItems().add(homeItem);

        MenuItem aboutItem = new MenuItem("Project members");
        aboutItem.setOnAction(e -> {
            primaryStage.setScene(createProjectMembersScene(primaryStage));
        });
        aboutMenu.getItems().add(aboutItem);

        MenuItem addRoomItem = new MenuItem("Add Room");
        addRoomItem.setOnAction(e -> {
            primaryStage.setScene(createAddRoomScene(primaryStage));
        });

        MenuItem removeRoomItem = new MenuItem("Remove Room");
        removeRoomItem.setOnAction(e -> {
            primaryStage.setScene(createDeleteRoomScene(primaryStage));
        });

        MenuItem updateRoomItem = new MenuItem("Update Room");
        updateRoomItem.setOnAction(e -> {
            primaryStage.setScene(createUpdateRoomScene(primaryStage));
        });

        roomManagementMenu.getItems().addAll(addRoomItem, removeRoomItem, updateRoomItem);

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> primaryStage.setScene(createLoginScene(primaryStage)));
        exitMenu.getItems().add(exitItem);

        menuBar.getMenus().addAll(homeMenu, roomManagementMenu, aboutMenu, exitMenu);

        return menuBar;
    }
     
    @Override
    public void start (Stage PrimaryStage) {
        PrimaryStage.setScene(createLoginScene(PrimaryStage));
        PrimaryStage.setTitle("Hotel Booking System");
        PrimaryStage.show();
    }
    public static void main (String[] args) {
       Application.launch(args);
    } 
}
