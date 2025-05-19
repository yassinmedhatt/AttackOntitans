package game.gui;

import java.io.IOException;
import java.util.Optional;
import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartGameView extends Application {

    private Stage primaryStage;
    private Battle battle;
    private VBox ImageLane1;
    private VBox ImageLane2;
    private VBox ImageLane3;
    private VBox ImageLane4;
    private VBox ImageLane5;

    private HBox hBox1Lane1;
    private HBox hBox2Lane1;
    private HBox hBox3Lane1;
    private HBox hBox4Lane1;
    private HBox hBox1Lane2;
    private HBox hBox2Lane2;
    private HBox hBox3Lane2;
    private HBox hBox4Lane2;
    private HBox hBox1Lane3;
    private HBox hBox2Lane3;
    private HBox hBox3Lane3;
    private HBox hBox4Lane3;
    private HBox hBox1Lane4;
    private HBox hBox2Lane4;
    private HBox hBox3Lane4;
    private HBox hBox4Lane4;
    private HBox hBox1Lane5;
    private HBox hBox2Lane5;
    private HBox hBox3Lane5;
    private HBox hBox4Lane5;


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        Scene mainMenuScene = createMainMenuScene(primaryStage);
        primaryStage.setScene(mainMenuScene);
        primaryStage.setTitle("Attack on Titan - Main Menu");
        primaryStage.show();
        
    }

    private Scene createMainMenuScene(Stage primaryStage) {// Load the background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("mohsenbro.jpg"));

        // Create a background image view
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        // Create a background pane to hold the main menu components
        VBox backgroundPane = new VBox(20); // Spacing between components
        backgroundPane.setBackground(new Background(background));
        backgroundPane.setAlignment(Pos.CENTER); // Center alignment

        // Main menu components
        Text title = new Text("Attack on Titan");
        title.setFont(Font.font("Bookman Old Style", 34));
        title.setFill(Color.rgb(245, 245, 220));        
        Button hardButton = new Button("Hard");
        Button easyButton = new Button("Easy");
        Button instructionsButton = new Button("Game Instructions");
        backgroundPane.getChildren().addAll(title, hardButton, easyButton, instructionsButton);

        easyButton.setOnAction(e -> {
            try {
                Scene EasyBattleScene = createEasyScene(primaryStage);
                primaryStage.setScene(EasyBattleScene);
            } catch (IOException ex) {
                ex.printStackTrace(); // Handle the exception appropriately
            }
        });
        hardButton.setOnAction(e -> {
            try {
                Scene HardBattleScene = createHardScene(primaryStage);
                primaryStage.setScene(HardBattleScene);
            } catch (IOException ex) {
                ex.printStackTrace(); // Handle the exception appropriately
            }
        });

        	
       
        instructionsButton.setOnAction(e -> {
            Scene instructionsScene = createInstructionsScene(primaryStage);
            primaryStage.setScene(instructionsScene);
        });

        return new Scene(backgroundPane, 300, 300);
    }

    private Scene createInstructionsScene(Stage primaryStage) {


        Image backgroundImage = new Image(getClass().getResourceAsStream("mohsenbro.jpg"));
        BackgroundImage background = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        VBox backgroundPane = new VBox(20); 
        backgroundPane.setBackground(new Background(background));
        backgroundPane.setAlignment(Pos.CENTER); 
        backgroundPane.setPadding(new Insets(20)); 

        Text instructionsText = new Text();
        instructionsText.setText("Introduction:\n" +
                "Welcome to Attack on Titan: Utopia, a tower defense game where you defend the Utopia District Walls against waves of titans.\n" +
                "Objective:\n" +
                "Defend the Utopia District Walls by deploying anti-titan weapons and surviving.\n" +
                "Setup:\n" +
                "The battlefield has lanes, walls, and titans. Deploy weapons strategically.\n" +
                "Enemy Characters:\n" +
                "Titans come in types with varying HP, damage, speed, and abilities.\n" +
                "Friendly Pieces:\n" +
                "Deploy weapons like Piercing Cannon, Sniper Cannon, and Volley Spread Cannon.\n" +
                "Game Rules:\n" +
                "Survive and earn points. Titans and weapons perform actions each turn.\n" +
                "Game Flow:\n" +
                "Purchase and deploy weapons each turn, then titans and weapons act.\n" +
                "Can you defend humanity's last stronghold? Play Attack on Titan: Utopia and find out!");

        instructionsText.setFont(Font.font("Bookman Old Style",13));
        instructionsText.setFill(Color.rgb(245, 245, 220)); // Cream color

        backgroundPane.getChildren().add(instructionsText);

        // Add an exit button to return to the main menu
        Button exitButton = new Button("Back to Main Menu");
        exitButton.setOnAction(e -> primaryStage.setScene(createMainMenuScene(primaryStage)));
        backgroundPane.getChildren().add(exitButton);

        return new Scene(backgroundPane, 300 , 300);
    }
    
    private Scene createEasyScene(Stage primaryStage) throws IOException {
    	
        battle = new Battle(1, 0, 250, 3, 250);

        BorderPane root = new BorderPane();
        root.setPrefSize(500, 600);
     // Create the black pane (WallPane)
        Pane wallPane = new Pane();
        wallPane.setStyle("-fx-background-color: black;");
        wallPane.setPrefSize(100, 600); // Adjust width and height as needed
        root.setRight(wallPane); // Add it to the right side of the BorderPane

        // Move the wallPane 30 pixels to the right
        wallPane.setTranslateX(30);

        // Insert a label inside the wallPane
        Label label = new Label("Wall");
        label.setTextFill(Color.WHITE); // Set text color to white
        label.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Set font and size
        label.setLayoutX(10); // Adjust label position
        label.setLayoutY(10); 
        label.setRotate(90); 
        wallPane.getChildren().add(label); // Add label to the wallPane

        // Initialize VBox containers for weapons in each lane
        ImageLane1 = new VBox(2);
        ImageLane2 = new VBox(2);
        ImageLane3 = new VBox(2);
        double vBoxWidth = 50; // Adjust width value as needed
        ImageLane1.setPrefWidth(vBoxWidth);
        ImageLane2.setPrefWidth(vBoxWidth);
        ImageLane3.setPrefWidth(vBoxWidth);
        ScrollPane scrollPaneLane1 = new ScrollPane(ImageLane1);
        scrollPaneLane1.setPrefViewportHeight(30); // Set a fixed height
        scrollPaneLane1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Disable horizontal scrollbar
        scrollPaneLane1.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Enable vertical scrollbar

        ScrollPane scrollPaneLane2 = new ScrollPane(ImageLane2);
        scrollPaneLane2.setPrefViewportHeight(30); // Set a fixed height
        scrollPaneLane2.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Disable horizontal scrollbar
        scrollPaneLane2.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Enable vertical scrollbar
        ScrollPane scrollPaneLane3 = new ScrollPane(ImageLane3);
        scrollPaneLane3.setPrefViewportHeight(30); // Set a fixed height
        scrollPaneLane3.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Disable horizontal scrollbar
        scrollPaneLane3.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Enable vertical scrollbar

        // Create a GridPane for the lanes
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(600, 750);
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setTranslateX(150); // Move the GridPane 150 pixels to the right

        Pane pane1 = createPane();
        Pane pane2 = createPane2();
        Pane pane3 = createPane3();

        pane1.setPrefSize(580, 240);
        pane2.setPrefSize(750, 240);
        pane3.setPrefSize(750, 240);

        gridPane.add(pane1, 0, 0); // Add pane1 last
        gridPane.add(pane2, 0, 1); // Add pane2 second
        gridPane.add(pane3, 0, 2); // Add pane3 first

        // Wrap the gridPane in a ScrollPane
        ScrollPane lanesScrollPane = new ScrollPane(gridPane);
        lanesScrollPane.setFitToWidth(true);
        lanesScrollPane.setFitToHeight(true);
        root.setCenter(lanesScrollPane);

        VBox leftVBox = new VBox(100);
        leftVBox.setAlignment(Pos.CENTER_LEFT);
        leftVBox.setPadding(new Insets(10));
        leftVBox.setTranslateY(30); // Move the VBox down by 30
        Label Lane1 = new Label(updateValuesForLane1());
        Label Lane2 = new Label(updateValuesForLane2());
        Label Lane3 = new Label(updateValuesForLane3());
        Label BattleLabel = new Label(getValuesFromBattle());

        leftVBox.getChildren().addAll(Lane1, Lane2, Lane3, BattleLabel);

        Pane purchasedWeaponsPane = new Pane();
        purchasedWeaponsPane.setPrefSize(50, 800); // Set height and width
        purchasedWeaponsPane.setStyle("-fx-background-color: lightgray;"); // Set background color

        // Set the position of lane ScrollPanes inside purchasedWeaponsPane
        scrollPaneLane1.setLayoutX(0);
        scrollPaneLane1.setLayoutY(0);

        scrollPaneLane2.setLayoutX(0);
        scrollPaneLane2.setLayoutY(250); // Adjust Y position as needed

        scrollPaneLane3.setLayoutX(0);
        scrollPaneLane3.setLayoutY(500); // Adjust Y position as needed

        // Add the lane ScrollPanes to the purchased weapons pane
        purchasedWeaponsPane.getChildren().addAll(scrollPaneLane1, scrollPaneLane2, scrollPaneLane3);

        // Wrap the purchasedWeaponsPane in a ScrollPane
        ScrollPane weaponsScrollPane = new ScrollPane(purchasedWeaponsPane);
        weaponsScrollPane.setFitToWidth(true);
        weaponsScrollPane.setFitToHeight(true);

        HBox labelsAndRightVBox = new HBox(10);
        labelsAndRightVBox.getChildren().addAll(leftVBox, weaponsScrollPane);

        HBox bottomHBox = new HBox(10);
        bottomHBox.setAlignment(Pos.CENTER);
        bottomHBox.setPadding(new Insets(10));
        Button piercingCannonButton = new Button("Type: Piercing Cannon\nName: Anti-Titan Shell\nPrice: 25\nDamage: 10");
        Button sniperCannonButton = new Button("Type: Sniper Cannon\nName: Long-Rear Spear\nPrice: 25\nDamage: 35");
        Button volleySpreadCannonButton = new Button("Type: Volley-Spread Cannon\nName: Wall-Spread Cannon\nPrice: 100\nDamage: 5");
        Button wallTrapButton = new Button("Type: Wall-Trap\nName: Proximity Trap\nPrice: 75\nDamage: 100");
        Button passTurnButton = new Button("Pass Turn");
        Button MainMenuButton = new Button("Main Menu");
        bottomHBox.getChildren().addAll(piercingCannonButton, sniperCannonButton, volleySpreadCannonButton, wallTrapButton, passTurnButton,MainMenuButton);

        root.setLeft(labelsAndRightVBox);
        root.setBottom(bottomHBox);

        Scene scene = new Scene(root);

        setUpButtonActions(piercingCannonButton, 1, Lane1, Lane2, Lane3, BattleLabel, primaryStage);
        setUpButtonActions(sniperCannonButton, 2, Lane1, Lane2, Lane3, BattleLabel, primaryStage);
        setUpButtonActions(volleySpreadCannonButton, 3, Lane1, Lane2, Lane3, BattleLabel, primaryStage);
        setUpButtonActions(wallTrapButton, 4, Lane1, Lane2, Lane3, BattleLabel, primaryStage);

        MainMenuButton.setOnAction(event -> {               
        	primaryStage.setScene(createMainMenuScene(primaryStage)); // Switch to the main menu scene
});

        passTurnButton.setOnAction(event -> {
            try {
                battle.passTurn();
                Lane1.setText(updateValuesForLane1());
                Lane2.setText(updateValuesForLane2());
                Lane3.setText(updateValuesForLane3());
              
                getTitansToAddLane1();
                getTitansToAddLane2();
                getTitansToAddLane3();
              
                removeDefeatedTitansFromAllPanes();
                checkGameOver(primaryStage);
                BattleLabel.setText(getValuesFromBattle());
            } catch (NullPointerException e) {
            	displayGameOverAlert() ;
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setScene(scene);
        primaryStage.show();
		return scene;
    }

    private void setUpButtonActions(Button button, int weaponType, Label Lane1, Label Lane2, Label Lane3, Label BattleLabel, Stage primaryStage) {
        button.setOnAction(event -> {
            int laneNumber = askLaneNumber();
            try {
                if (laneNumber != -1) {
                    try {
                        battle.purchaseWeapon(weaponType, getLaneFromNumber(laneNumber));
                    } catch (NullPointerException e) {
                    	displayGameOverAlert() ;
                    	return;
                    }

                    getTitansToAddLane1();
                    getTitansToAddLane2();
                    getTitansToAddLane3();
                    removeDefeatedTitansFromAllPanes();
                    Lane1.setText(updateValuesForLane1());
                    Lane2.setText(updateValuesForLane2());
                    Lane3.setText(updateValuesForLane3());
                    BattleLabel.setText(getValuesFromBattle());
                    addWeaponImageToVBox(laneNumber, createWeaponButton(weaponType));
                } else {
                	checkGameOver( primaryStage);
                }
            } catch (NumberFormatException e) {
                displayAlert("Invalid Input", "Please enter a valid lane number.");
            } catch (InsufficientResourcesException e) {
                displayAlert("Resources Unavailable", "Not Enough Resources!");
            } catch (InvalidLaneException e) {
                displayAlert("Invalid Lane", "Invalid lane provided for weapon purchase.");
            }
        });
    }
    
    private Button createWeaponButton(int weaponType) {
        switch (weaponType) {
            case 1:
                return createPiercingCannonButton();
            case 2:
                return createSniperCannonButton();
            case 3:
                return createVolleySpreadCannonButton();
            case 4:
                return createWallTrapButton();
            default:
                return new Button("Unknown Weapon");
        }
    }
    private void addWeaponImageToVBox(int laneNumber, Button button) {
        VBox targetVBox = null;
        switch (laneNumber) {
            case 1:
                targetVBox = ImageLane1;
                break;
            case 2:
                targetVBox = ImageLane2;
                break;
            case 3:
                targetVBox = ImageLane3;
                break;
            case 4:
                targetVBox = ImageLane4;

                break;
            case 5:
                targetVBox = ImageLane5;

                break;
            default:
                return;
        }

        if (targetVBox != null) {
            targetVBox.getChildren().add(button);
        }
    }

    private int askLaneNumber() {

        while (true) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Select Lane Number");
            dialog.setHeaderText(null);
            dialog.setContentText("Please enter the lane number (1 to 3):");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    int laneNumber = Integer.parseInt(result.get());
                    if (laneNumber < 1 || laneNumber > 3) {
                        displayAlert("Invalid Input", "Please enter a valid lane number between 1 and 3.");
                        continue; // Ask for input again
                    }
                    return laneNumber;
                } catch (NumberFormatException e) {
                    displayAlert("Invalid Input", "Please enter a valid integer for the lane number.");
                    continue; // Ask for input again
                }
            } else {
                return -1; // User canceled the dialog
            }
        }
    }
    
    private int askLaneNumber2() {
        while (true) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Select Lane Number");
            dialog.setHeaderText(null);
            dialog.setContentText("Please enter the lane number (1 to 5):");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    int laneNumber = Integer.parseInt(result.get());
                    if (laneNumber < 1 || laneNumber > 5) {
                        displayAlert("Invalid Input", "Please enter a valid lane number between 1 and 3.");
                        continue; // Ask for input again
                    }
                    return laneNumber;
                } catch (NumberFormatException e) {
                    displayAlert("Invalid Input", "Please enter a valid integer for the lane number.");
                    continue; // Ask for input again
                }
            } else {
                return -1; // User canceled the dialog
            }
        }
    }
    private void checkGameOver(Stage primaryStage) {
        if (battle.isGameOver()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("The game is over!"+"\nScore:"+battle.getScore());
            alert.showAndWait();
            primaryStage.close();
        }}

    private void displayAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private String getValuesFromBattle() {
        
        return "Score: " + battle.getScore() + "\nTurn: " + battle.getNumberOfTurns() + "\nPhase: " + battle.getBattlePhase()  + "\nResources: " + battle.getResourcesGathered();
    }
    private Button createPiercingCannonButton() {
        Button button = new Button();
        button.setPrefSize(18, 18);
        button.setStyle("-fx-background-color: blue;");
        Tooltip tooltip = new Tooltip("Type: Piercing Cannon\nName: Anti-Titan Shell\nPrice: 25\nDamage: 10");
        button.setTooltip(tooltip);
        return button;
    }

    private Button createSniperCannonButton() {
        Button button = new Button();
        button.setPrefSize(18, 18);
        button.setStyle("-fx-background-color: yellow;");
        Tooltip tooltip = new Tooltip("Type: Sniper Cannon\nName: Long-Rear Spear\nPrice: 25\nDamage: 35");
        button.setTooltip(tooltip);
        return button;
    }

    private Button createVolleySpreadCannonButton() {
        Button button = new Button();
        button.setPrefSize(18, 18);
        button.setStyle("-fx-background-color: orange;");
        Tooltip tooltip = new Tooltip("Type: Volley-Spread Cannon\nName: Wall-Spread Cannon\nPrice: 100\nDamage: 5");
        button.setTooltip(tooltip);
        return button;
    }

    private Button createWallTrapButton() {
        Button button = new Button();
        button.setPrefSize(18, 18);
        button.setStyle("-fx-background-color: purple;");
        Tooltip tooltip = new Tooltip("Type: Wall-Trap\nName: Proximity Trap\nPrice: 75\nDamage: 100");
        button.setTooltip(tooltip);
        return button;
    }
    
    private Pane createPane() {
        VBox pane1 = new VBox();
        pane1.setPrefSize(200, 100);
        pane1.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        hBox1Lane1 = new HBox(10);
        hBox2Lane1 = new HBox(10);
        hBox3Lane1 = new HBox(10);
        hBox4Lane1 = new HBox(10);

        // Set maximum height for HBoxes
        hBox1Lane1.setMaxHeight(100);
        hBox2Lane1.setMaxHeight(100);
        hBox3Lane1.setMaxHeight(100);
        hBox4Lane1.setMaxHeight(100);

        hBox1Lane1.setPadding(new Insets(5));
        hBox2Lane1.setPadding(new Insets(5));
        hBox3Lane1.setPadding(new Insets(5));
        hBox4Lane1.setPadding(new Insets(5));

        hBox1Lane1.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
        hBox2Lane1.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
        hBox3Lane1.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
        hBox4Lane1.setStyle("-fx-border-color: gray; -fx-border-width: 1;");

       // Wrap HBoxes in ScrollPanes
        ScrollPane scrollPane1 = new ScrollPane(hBox1Lane1);
        ScrollPane scrollPane2 = new ScrollPane(hBox2Lane1);
        ScrollPane scrollPane3 = new ScrollPane(hBox3Lane1);
        ScrollPane scrollPane4 = new ScrollPane(hBox4Lane1);

        pane1.getChildren().addAll(scrollPane1, scrollPane2, scrollPane3, scrollPane4);
        pane1.setAlignment(Pos.CENTER);

        return pane1;
    }

    private Pane createPane2() {
         VBox pane2 = new VBox();
         pane2.setPrefSize(200, 100);
         pane2.setStyle("-fx-border-color: black; -fx-border-width: 1;");

         hBox1Lane2 = new HBox(10);
         hBox2Lane2 = new HBox(10);
         hBox3Lane2 = new HBox(10);
         hBox4Lane2 = new HBox(10);

         // Set maximum height for HBoxes
         hBox1Lane2.setMaxHeight(100);
         hBox2Lane2.setMaxHeight(100);
         hBox3Lane2.setMaxHeight(100);
         hBox4Lane2.setMaxHeight(100);

         hBox1Lane2.setPadding(new Insets(5));
         hBox2Lane2.setPadding(new Insets(5));
         hBox3Lane2.setPadding(new Insets(5));
         hBox4Lane2.setPadding(new Insets(5));

         hBox1Lane2.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
         hBox2Lane2.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
         hBox3Lane2.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
         hBox4Lane2.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
         
         ScrollPane scrollPane1 = new ScrollPane(hBox1Lane2);
         ScrollPane scrollPane2 = new ScrollPane(hBox2Lane2);
         ScrollPane scrollPane3 = new ScrollPane(hBox3Lane2);
         ScrollPane scrollPane4 = new ScrollPane(hBox4Lane2);

         pane2.getChildren().addAll(scrollPane1, scrollPane2, scrollPane3, scrollPane4);
         pane2.setAlignment(Pos.CENTER);

         return pane2;
     }

    private Pane createPane3() {
        VBox pane3 = new VBox();
        pane3.setPrefSize(200, 100);
        pane3.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        hBox1Lane3 = new HBox(10);
        hBox2Lane3 = new HBox(10);
        hBox3Lane3 = new HBox(10);
        hBox4Lane3 = new HBox(10);

        // Set maximum height for HBoxes
        hBox1Lane3.setMaxHeight(100);
        hBox2Lane3.setMaxHeight(100);
        hBox3Lane3.setMaxHeight(100);
        hBox4Lane3.setMaxHeight(100);

        hBox1Lane3.setPadding(new Insets(5));
        hBox2Lane3.setPadding(new Insets(5));
        hBox3Lane3.setPadding(new Insets(5));
        hBox4Lane3.setPadding(new Insets(5));

        hBox1Lane3.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
        hBox2Lane3.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
        hBox3Lane3.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
        hBox4Lane3.setStyle("-fx-border-color: gray; -fx-border-width: 1;");

        // Wrap HBoxes in ScrollPanes
        ScrollPane scrollPane1 = new ScrollPane(hBox1Lane3);
        ScrollPane scrollPane2 = new ScrollPane(hBox2Lane3);
        ScrollPane scrollPane3 = new ScrollPane(hBox3Lane3);
        ScrollPane scrollPane4 = new ScrollPane(hBox4Lane3);

        pane3.getChildren().addAll(scrollPane1, scrollPane2, scrollPane3, scrollPane4);
        pane3.setAlignment(Pos.CENTER);

        return pane3;
    }

    private String updateValuesForLane1() {
    	Lane lane1 = getLaneFromNumber(1);
    	return "Lane Number: " + 1 + "\n" +
        "Danger Level: " + lane1.getDangerLevel() + "\n" +
        "Health: " + lane1.getLaneWall().getCurrentHealth();}
    private String updateValuesForLane2() {
    	Lane lane2 = getLaneFromNumber(2);
    	return "Lane Number: " + 2 + "\n" +
        "Danger Level: " + lane2.getDangerLevel() + "\n" +
        "Health: " + lane2.getLaneWall().getCurrentHealth();}
    private String updateValuesForLane3() {
    	Lane lane3 = getLaneFromNumber(3);
    	return "Lane Number: " + 3 + "\n" +
        "Danger Level: " + lane3.getDangerLevel() + "\n" +
        "Health: " + lane3.getLaneWall().getCurrentHealth();}
 
    private void removeDefeatedTitansFromPane(HBox hBox) {
        hBox.getChildren().removeIf(node -> {
            if (node instanceof Button) {
                Titan titan = (Titan) ((Button) node).getUserData();
                return titan != null && titan.isDefeated();
            }
            return false;
        });
    }
    private void removeDefeatedTitansFromAllPanes() {
        removeDefeatedTitansFromPane(hBox1Lane1);
        removeDefeatedTitansFromPane(hBox2Lane1);
        removeDefeatedTitansFromPane(hBox3Lane1);
        removeDefeatedTitansFromPane(hBox4Lane1);

        removeDefeatedTitansFromPane(hBox1Lane2);
        removeDefeatedTitansFromPane(hBox2Lane2);
        removeDefeatedTitansFromPane(hBox3Lane2);
        removeDefeatedTitansFromPane(hBox4Lane2);

        removeDefeatedTitansFromPane(hBox1Lane3);
        removeDefeatedTitansFromPane(hBox2Lane3);
        removeDefeatedTitansFromPane(hBox3Lane3);
        removeDefeatedTitansFromPane(hBox4Lane3);
    }
    
    private Lane getLaneFromNumber(int laneNumber) {
        int laneIndex = 1;
        for (Lane lane : battle.getOriginalLanes()) {
            if (laneNumber == laneIndex) {
                return lane;
            }
            laneIndex++;
        }
        return null;
    }
    private void getTitansToAddLane1() {
        Lane lane1 = getLaneFromNumber(1);
        if (lane1 != null) {
            for (Titan titan : lane1.getTitans()) {
            	if (titan instanceof AbnormalTitan) {
                    hBox1Lane1.getChildren().add(createEmptyButton());
                    hBox1Lane1.getChildren().add(createEmptyButton());
                    hBox1Lane1.getChildren().add(createTitanButton(titan));
                } else if (titan instanceof ArmoredTitan) {
                    hBox2Lane1.getChildren().add(createEmptyButton());
                    hBox2Lane1.getChildren().add(createTitanButton(titan));                   
                } else if (titan instanceof ColossalTitan) {
                    hBox3Lane1.getChildren().add(createTitanButton(titan));
                } else if (titan instanceof PureTitan) {
                    hBox4Lane1.getChildren().add(createEmptyButton());
                    hBox4Lane1.getChildren().add(createTitanButton(titan));
                }}
        }
    }
    private void getTitansToAddLane2() {
        Lane lane2 = getLaneFromNumber(2);
        if (lane2 != null) {
            for (Titan titan : lane2.getTitans()) {
            	if (titan instanceof AbnormalTitan) {
                    hBox1Lane2.getChildren().add(createEmptyButton());
                    hBox1Lane2.getChildren().add(createEmptyButton());
                    hBox1Lane2.getChildren().add(createTitanButton(titan));
                } else if (titan instanceof ArmoredTitan) {
                    hBox2Lane2.getChildren().add(createEmptyButton());
                    hBox2Lane2.getChildren().add(createTitanButton(titan));                   
                } else if (titan instanceof ColossalTitan) {
                    hBox3Lane2.getChildren().add(createTitanButton(titan));
                } else if (titan instanceof PureTitan) {
                    hBox4Lane2.getChildren().add(createEmptyButton());
                    hBox4Lane2.getChildren().add(createTitanButton(titan));
                }
            }
        }
    }

    private void getTitansToAddLane3() {

        Lane lane3 = getLaneFromNumber(3);
        if (lane3 != null) {
            for (Titan titan : lane3.getTitans()) {
                if (titan instanceof AbnormalTitan) {
                    hBox1Lane3.getChildren().add(createEmptyButton());
                    hBox1Lane3.getChildren().add(createEmptyButton());
                    hBox1Lane3.getChildren().add(createTitanButton(titan));
                } else if (titan instanceof ArmoredTitan) {
                    hBox2Lane3.getChildren().add(createEmptyButton());
                    hBox2Lane3.getChildren().add(createTitanButton(titan));                   
                } else if (titan instanceof ColossalTitan) {
                    hBox3Lane3.getChildren().add(createTitanButton(titan));
                } else if (titan instanceof PureTitan) {
                    hBox4Lane3.getChildren().add(createEmptyButton());
                    hBox4Lane3.getChildren().add(createTitanButton(titan));
                }
            }
        }}
    private Button createEmptyButton() {

        Button button = new Button();
        button.setPrefSize(5, 5);
        button.setStyle("-fx-background-color: transparent;");
        return button;
    }
    private Button createTitanButton(Titan titan) {
        Button button = new Button();
        String backgroundColor;

        if (titan instanceof AbnormalTitan) {
            backgroundColor = "red";
            button.setPrefSize(25,25 ); // Example size for AbnormalTitan
            button.setMinSize(25,25);
            button.setMaxSize(25,25);
        } else if (titan instanceof ArmoredTitan) {
            backgroundColor = "gray";
            button.setPrefSize(25, 20); // Example size for ArmoredTitan
            button.setMinSize(25,20);
            button.setMaxSize(25,20);
        } else if (titan instanceof ColossalTitan) {
            backgroundColor = "black";
            button.setPrefSize(15, 40); // Example size for ColossalTitan
            button.setMinSize(25,40);
            button.setMaxSize(25,40);
        } else if (titan instanceof PureTitan) {
            backgroundColor = "green";
            button.setPrefSize(25, 25); // Example size for PureTitan
            button.setMinSize(25,25);
            button.setMaxSize(25,25);
        } else {
            backgroundColor = "transparent"; // Default color if titan type is unknown
        }

        button.setStyle("-fx-background-color: " + backgroundColor + ";");

        Tooltip tooltip = new Tooltip(
                titan.getClass().getSimpleName() + "\nHealth: " + titan.getCurrentHealth() + "\nAttack Damage: " + titan.getDamage() + "\nDistance From Base: " + titan.getDistance() + "\nSpeed: " + titan.getSpeed() + "\nHeight: " + titan.getHeightInMeters()
        );
        button.setTooltip(tooltip);

        return button;
    }

    private Scene createHardScene(Stage primaryStage) throws IOException {
        battle = new Battle(1, 0, 0, 5, 150);

        BorderPane root = new BorderPane();
        root.setPrefSize(200, 200);
     // Create the black pane (WallPane)
        Pane wallPane = new Pane();
        wallPane.setStyle("-fx-background-color: black;");
        wallPane.setPrefSize(100, 600); // Adjust width and height as needed
        root.setRight(wallPane); // Add it to the right side of the BorderPane

        // Move the wallPane 30 pixels to the right
        wallPane.setTranslateX(30);

        // Insert a label inside the wallPane
        Label label = new Label("Wall");
        label.setTextFill(Color.WHITE); // Set text color to white
        label.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Set font and size
        label.setLayoutX(10); // Adjust label position
        label.setLayoutY(10);
        label.setRotate(90); // Rotate the label 90 degrees clockwise to make the text horizontal
        wallPane.getChildren().add(label); // Add label to the wallPane
        
        // Initialize VBox containers for weapons in each lane
        ImageLane1 = new VBox(15);
        ImageLane2 = new VBox(15);
        ImageLane3 = new VBox(15);
        ImageLane4 = new VBox(15);
        ImageLane5 = new VBox(15);
        double vBoxWidth = 50; // Adjust width value as needed
        ImageLane1.setPrefWidth(vBoxWidth);
        ImageLane2.setPrefWidth(vBoxWidth);
        ImageLane3.setPrefWidth(vBoxWidth);
        ImageLane4.setPrefWidth(vBoxWidth);
        ImageLane5.setPrefWidth(vBoxWidth);

        // Create a GridPane for the lanes
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(600, 750);
        gridPane.setPadding(new Insets(2));
        gridPane.setHgap(10);
        gridPane.setTranslateX(150); // Move the GridPane 150 pixels to the right

        Pane pane1 = createPane(1);
        Pane pane2 = createPane(2);
        Pane pane3 = createPane(3);
        Pane pane4 = createPane(4);
        Pane pane5 = createPane(5);

        pane1.setPrefSize(580, 240);
        pane2.setPrefSize(580, 240); // Adjusted to match the width of pane1
        pane3.setPrefSize(580, 240); // Adjusted to match the width of pane1
        pane4.setPrefSize(580, 240); // Adjusted to match the width of pane1
        pane5.setPrefSize(580, 240); // Adjusted to match the width of pane1

        // Add panes to the GridPane in the correct order
        gridPane.add(pane1, 0, 0);
        gridPane.add(pane2, 0, 1);
        gridPane.add(pane3, 0, 2);
        gridPane.add(pane4, 0, 3);
        gridPane.add(pane5, 0, 4);

        // Wrap the gridPane in a ScrollPane
        ScrollPane lanesScrollPane = new ScrollPane(gridPane);
        lanesScrollPane.setFitToWidth(true);
        lanesScrollPane.setFitToHeight(true);
        root.setCenter(lanesScrollPane);

        VBox leftVBox = new VBox(20); // Increase spacing between labels
        leftVBox.setAlignment(Pos.CENTER_LEFT);
        leftVBox.setPadding(new Insets(10)); // Add padding
        leftVBox.setTranslateY(30); // Move the VBox down by 30
        leftVBox.setPrefWidth(200); // Set a wider width to accommodate labels

        Label Lane1 = new Label(updateValuesForLane12());
        Label Lane2 = new Label(updateValuesForLane22());
        Label Lane3 = new Label(updateValuesForLane32());
        Label Lane4 = new Label(updateValuesForLane42());
        Label Lane5 = new Label(updateValuesForLane52());

        Label BattleLabel = new Label(getValuesFromBattle());

        leftVBox.getChildren().addAll(Lane1, Lane2, Lane3, Lane4, Lane5, BattleLabel);

        Pane purchasedWeaponsPane = new Pane();
        purchasedWeaponsPane.setPrefSize(50, 800); // Set height and width
        purchasedWeaponsPane.setStyle("-fx-background-color: lightgray;"); // Set background color
        // Set the position of lane VBoxes inside purchasedWeaponsPane
        ImageLane1.setLayoutX(0);
        ImageLane1.setLayoutY(0);

        ImageLane2.setLayoutX(0);
        ImageLane2.setLayoutY(250); // Adjust Y position as needed

        ImageLane3.setLayoutX(0);
        ImageLane3.setLayoutY(500); // Adjust Y position as needed

        ImageLane4.setLayoutX(0);
        ImageLane4.setLayoutY(750); // Adjust Y position as needed

        ImageLane5.setLayoutX(0);
        ImageLane5.setLayoutY(1000); // Adjust Y position as needed
        
        ScrollPane scrollPaneLane1 = new ScrollPane(ImageLane1);
        scrollPaneLane1.setPrefViewportHeight(30); // Set a fixed height
        scrollPaneLane1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Disable horizontal scrollbar
        scrollPaneLane1.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Enable vertical scrollbar

        ScrollPane scrollPaneLane2 = new ScrollPane(ImageLane2);
        scrollPaneLane2.setPrefViewportHeight(30); // Set a fixed height
        scrollPaneLane2.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Disable horizontal scrollbar
        scrollPaneLane2.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Enable vertical scrollbar
        ScrollPane scrollPaneLane3 = new ScrollPane(ImageLane3);
        scrollPaneLane3.setPrefViewportHeight(30); // Set a fixed height
        scrollPaneLane3.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Disable horizontal scrollbar
        scrollPaneLane3.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Enable vertical scrollbar

        ScrollPane scrollPaneLane4 = new ScrollPane(ImageLane4);
        scrollPaneLane4.setPrefViewportHeight(30); // Set a fixed height
        scrollPaneLane4.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Disable horizontal scrollbar
        scrollPaneLane4.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Enable vertical scrollbar
      
        ScrollPane scrollPaneLane5 = new ScrollPane(ImageLane5);
        scrollPaneLane5.setPrefViewportHeight(30); // Set a fixed height
        scrollPaneLane5.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Disable horizontal scrollbar
        scrollPaneLane5.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Enable vertical scrollbar

        scrollPaneLane1.setLayoutX(0);
        scrollPaneLane1.setLayoutY(0);

        scrollPaneLane2.setLayoutX(0);
        scrollPaneLane2.setLayoutY(100); // Adjust Y position as needed

        scrollPaneLane3.setLayoutX(0);
        scrollPaneLane3.setLayoutY(200); // Adjust Y position as needed

        scrollPaneLane4.setLayoutX(0);
        scrollPaneLane4.setLayoutY(300); // Adjust Y position as needed

        scrollPaneLane5.setLayoutX(0);
        scrollPaneLane5.setLayoutY(400); // Adjust Y position as needed


        // Add the lane ScrollPanes to the purchased weapons pane
        purchasedWeaponsPane.getChildren().addAll(scrollPaneLane1, scrollPaneLane2, scrollPaneLane3,scrollPaneLane4,scrollPaneLane5);

        // Wrap the purchasedWeaponsPane in a ScrollPane
        ScrollPane weaponsScrollPane = new ScrollPane(purchasedWeaponsPane);
        weaponsScrollPane.setFitToWidth(true);
        weaponsScrollPane.setFitToHeight(true);

        HBox labelsAndRightVBox = new HBox(10);
        labelsAndRightVBox.getChildren().addAll(leftVBox, weaponsScrollPane);

        HBox bottomHBox = new HBox(10);
        bottomHBox.setAlignment(Pos.CENTER);
        bottomHBox.setPadding(new Insets(10));
        Button piercingCannonButton = new Button("Type: Piercing Cannon\nName: Anti-Titan Shell\nPrice: 25\nDamage: 10");
        Button sniperCannonButton = new Button("Type: Sniper Cannon\nName: Long-Rear Spear\nPrice: 25\nDamage: 35");
        Button volleySpreadCannonButton = new Button("Type: Volley-Spread Cannon\nName: Wall-Spread Cannon\nPrice: 100\nDamage: 5");
        Button wallTrapButton = new Button("Type: Wall-Trap\nName: Proximity Trap\nPrice: 75\nDamage: 100");
        Button passTurnButton = new Button("Pass Turn");
        Button MainMenuButton = new Button("Main Menu");

        bottomHBox.getChildren().addAll(piercingCannonButton, sniperCannonButton, volleySpreadCannonButton, wallTrapButton, passTurnButton);

        root.setLeft(labelsAndRightVBox);
        root.setBottom(bottomHBox);

        Scene scene = new Scene(root);

        // Setting up button actions
        setUpButtonActions2(piercingCannonButton, 1, Lane1, Lane2, Lane3, Lane4, Lane5, BattleLabel, primaryStage);
        setUpButtonActions2(sniperCannonButton, 2, Lane1, Lane2, Lane3, Lane4, Lane5, BattleLabel, primaryStage);
        setUpButtonActions2(volleySpreadCannonButton, 3, Lane1, Lane2, Lane3, Lane4, Lane5, BattleLabel, primaryStage);
        setUpButtonActions2(wallTrapButton, 4, Lane1, Lane2, Lane3, Lane4, Lane5, BattleLabel, primaryStage);

        MainMenuButton.setOnAction(event -> {               
        	primaryStage.setScene(createMainMenuScene(primaryStage)); // Switch to the main menu scene
});

        passTurnButton.setOnAction(event -> {
            try {
                battle.passTurn();
                Lane1.setText(updateValuesForLane12());
                Lane2.setText(updateValuesForLane22());
                Lane3.setText(updateValuesForLane32());  
                Lane4.setText(updateValuesForLane42());            
                Lane5.setText(updateValuesForLane52());            
                getTitansToAddLane1();
                getTitansToAddLane2();
                getTitansToAddLane3();
                getTitansToAddLane4();
                getTitansToAddLane5();            
                removeDefeatedTitansFromAllPanes2();
                checkGameOver(primaryStage);
                BattleLabel.setText(getValuesFromBattle());
            } catch (NullPointerException e) {
            	displayGameOverAlert() ;
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
		return scene;
    }

    private Pane createPane(int laneNumber) {
        VBox pane = new VBox();
        pane.setPrefSize(200, 100);
        pane.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        HBox hBox1 = new HBox(10);
        HBox hBox2 = new HBox(10);
        HBox hBox3 = new HBox(10);
        HBox hBox4 = new HBox(10);

        hBox1.setMaxHeight(100);
        hBox2.setMaxHeight(100);
        hBox3.setMaxHeight(100);
        hBox4.setMaxHeight(100);

        hBox1.setPadding(new Insets(20));
        hBox2.setPadding(new Insets(20));
        hBox3.setPadding(new Insets(20));
        hBox4.setPadding(new Insets(20));

        hBox1.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
        hBox2.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
        hBox3.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
        hBox4.setStyle("-fx-border-color: gray; -fx-border-width: 1;");

        ScrollPane scrollPane1 = new ScrollPane(hBox1);
        ScrollPane scrollPane2 = new ScrollPane(hBox2);
        ScrollPane scrollPane3 = new ScrollPane(hBox3);
        ScrollPane scrollPane4 = new ScrollPane(hBox4);

        pane.getChildren().addAll(scrollPane1, scrollPane2, scrollPane3, scrollPane4);
        pane.setAlignment(Pos.CENTER);

        switch (laneNumber) {
            case 1:
                hBox1Lane1 = hBox1;
                hBox2Lane1 = hBox2;
                hBox3Lane1 = hBox3;
                hBox4Lane1 = hBox4;
                break;
            case 2:
                hBox1Lane2 = hBox1;
                hBox2Lane2 = hBox2;
                hBox3Lane2 = hBox3;
                hBox4Lane2 = hBox4;
                break;
            case 3:
                hBox1Lane3 = hBox1;
                hBox2Lane3 = hBox2;
                hBox3Lane3 = hBox3;
                hBox4Lane3 = hBox4;
                break;
            case 4:
                hBox1Lane4 = hBox1;
                hBox2Lane4 = hBox2;
                hBox3Lane4 = hBox3;
                hBox4Lane4 = hBox4;
                break;
            case 5:
                hBox1Lane5 = hBox1;
                hBox2Lane5 = hBox2;
                hBox3Lane5 = hBox3;
                hBox4Lane5 = hBox4;
                break;
        }

        return pane;
    }

    private void setUpButtonActions2(Button button, int weaponCode, Label Lane1, Label Lane2, Label Lane3, Label Lane4,Label Lane5,Label BattleLabel, Stage primaryStage) {
        button.setOnAction(event -> {
            int laneNumber = askLaneNumber2();
            try {
                if (laneNumber != -1) {
                    try {
                        battle.purchaseWeapon(weaponCode, getLaneFromNumber(laneNumber));
                    } catch (NullPointerException e) {
                    	displayGameOverAlert() ;
                        return;
                    }

                    getTitansToAddLane1();
                    getTitansToAddLane2();
                    getTitansToAddLane3();
                    getTitansToAddLane4();
                    getTitansToAddLane5();
                    removeDefeatedTitansFromAllPanes2();
                    Lane1.setText(updateValuesForLane12());
                    Lane2.setText(updateValuesForLane22());
                    Lane3.setText(updateValuesForLane32());
                    Lane4.setText(updateValuesForLane42());
                    Lane5.setText(updateValuesForLane52());
                    BattleLabel.setText(getValuesFromBattle());
                    addWeaponImageToVBox(laneNumber, createWeaponButton(weaponCode));
                } else {
                	checkGameOver( primaryStage);
                }
            } catch (NumberFormatException e) {
                displayAlert("Invalid Input", "Please enter a valid lane number.");
            } catch (InsufficientResourcesException e) {
                displayAlert("Resources Unavailable", "Not Enough Resources!");
            } catch (InvalidLaneException e) {
                displayAlert("Invalid Lane", "Invalid lane provided for weapon purchase.");
            }
        });
    }

    private String updateValuesForLane12() {
        Lane lane1 = getLaneFromNumber(1);
        return "Lane Number: " + 1 + "\n" +
                "Danger Level: " + lane1.getDangerLevel() + "\n" +
                "Health: " + lane1.getLaneWall().getCurrentHealth();
    }

    private String updateValuesForLane22() {
        Lane lane2 = getLaneFromNumber(2);
        return "Lane Number: " + 2 + "\n" +
                "Danger Level: " + lane2.getDangerLevel() + "\n" +
                "Health: " + lane2.getLaneWall().getCurrentHealth();
    }

    private String updateValuesForLane32() {
        Lane lane3 = getLaneFromNumber(3);
        return "Lane Number: " + 3 + "\n" +
                "Danger Level: " + lane3.getDangerLevel() + "\n" +
                "Health: " + lane3.getLaneWall().getCurrentHealth();
    }

    private String updateValuesForLane42() {
        Lane lane4 = getLaneFromNumber(4);
        return "Lane Number: " + 4 + "\n" +
                "Danger Level: " + lane4.getDangerLevel() + "\n" +
                "Health: " + lane4.getLaneWall().getCurrentHealth();
    }

    private String updateValuesForLane52() {
        Lane lane5 = getLaneFromNumber(5);
        return "Lane Number: " + 5 + "\n" +
                "Danger Level: " + lane5.getDangerLevel() + "\n" +
                "Health: " + lane5.getLaneWall().getCurrentHealth();
    }

    private void removeDefeatedTitansFromAllPanes2() {
        removeDefeatedTitansFromPane(hBox1Lane1);
        removeDefeatedTitansFromPane(hBox2Lane1);
        removeDefeatedTitansFromPane(hBox3Lane1);
        removeDefeatedTitansFromPane(hBox4Lane1);

        removeDefeatedTitansFromPane(hBox1Lane2);
        removeDefeatedTitansFromPane(hBox2Lane2);
        removeDefeatedTitansFromPane(hBox3Lane2);
        removeDefeatedTitansFromPane(hBox4Lane2);

        removeDefeatedTitansFromPane(hBox1Lane3);
        removeDefeatedTitansFromPane(hBox2Lane3);
        removeDefeatedTitansFromPane(hBox3Lane3);
        removeDefeatedTitansFromPane(hBox4Lane3);

        removeDefeatedTitansFromPane(hBox1Lane4);
        removeDefeatedTitansFromPane(hBox2Lane4);
        removeDefeatedTitansFromPane(hBox3Lane4);
        removeDefeatedTitansFromPane(hBox4Lane4);

        removeDefeatedTitansFromPane(hBox1Lane5);
        removeDefeatedTitansFromPane(hBox2Lane5);
        removeDefeatedTitansFromPane(hBox3Lane5);
        removeDefeatedTitansFromPane(hBox4Lane5);
    }
  
    private void getTitansToAddLane4() {
        Lane lane4 = getLaneFromNumber(4);
        if (lane4 != null) {
            for (Titan titan : lane4.getTitans()) {
            	 if (titan instanceof AbnormalTitan) {
                     hBox1Lane4.getChildren().add(createEmptyButton());
                     hBox1Lane4.getChildren().add(createEmptyButton());
                     hBox1Lane4.getChildren().add(createTitanButton(titan));
                 } else if (titan instanceof ArmoredTitan) {
                     hBox2Lane4.getChildren().add(createEmptyButton());
                     hBox2Lane4.getChildren().add(createTitanButton(titan));                   
                 } else if (titan instanceof ColossalTitan) {
                     hBox3Lane4.getChildren().add(createTitanButton(titan));
                 } else if (titan instanceof PureTitan) {
                     hBox4Lane4.getChildren().add(createEmptyButton());
                     hBox4Lane4.getChildren().add(createTitanButton(titan));
                 }
             }
    }}
    private void getTitansToAddLane5() {
        Lane lane5 = getLaneFromNumber(5);
        if (lane5 != null) {
            for (Titan titan : lane5.getTitans()) {
            	 if (titan instanceof AbnormalTitan) {
                     hBox1Lane5.getChildren().add(createEmptyButton());
                     hBox1Lane5.getChildren().add(createEmptyButton());
                     hBox1Lane5.getChildren().add(createTitanButton(titan));
                 } else if (titan instanceof ArmoredTitan) {
                     hBox2Lane5.getChildren().add(createEmptyButton());
                     hBox2Lane5.getChildren().add(createTitanButton(titan));                   
                 } else if (titan instanceof ColossalTitan) {
                     hBox3Lane5.getChildren().add(createTitanButton(titan));
                 } else if (titan instanceof PureTitan) {
                     hBox4Lane5.getChildren().add(createEmptyButton());
                     hBox4Lane5.getChildren().add(createTitanButton(titan));
                 }
             }
        }
    }
    public void displayGameOverAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("Score:"+battle.getScore());
        alert.setContentText("The game is over.What would you like to do?");

        // Add buttons for user to choose
        ButtonType exitButton = new ButtonType("Exit");
        ButtonType mainMenuButton = new ButtonType("Return to Main Menu");

        // Set buttons
        alert.getButtonTypes().setAll(exitButton, mainMenuButton);

        // Show and wait for user interaction
        alert.showAndWait().ifPresent(response -> {
            if (response == exitButton) {
                // Handle exit action
                System.exit(0);
            } else if (response == mainMenuButton) {
                primaryStage.setScene(createMainMenuScene(primaryStage)); // Switch to the main menu scene
           }
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
