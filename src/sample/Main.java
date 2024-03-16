package sample;

import animatefx.animation.BounceIn;
import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.algorithm.NotificationWorker;
import sample.algorithm.QueensProblemAlgorithm;
import sample.grid.Cell;
import sample.grid.CellType;
import sample.grid.Grid;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;

public class Main extends Application implements ActionListener {
    private Grid mGrid;
    private double xOffset,yOffset;
    private final Timer timer = new Timer(50, this);
    private Button btnStart,btnReSetup,btnExit;
    private QueensProblemAlgorithm queensProblemAlgorithm;
    private CheckBox showRoutes;
    private boolean isShowRoute=false;
    private BorderPane root;
    private StackPane gridContent;
    private Label speedLabel,infoText, lblEstimatedTimeOfExecution, lblActualTimeOfExecution;
    private Slider speedSlider;
    private long startTime,stopTime;
    private int minute,delayTime,totalDelayTime=0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/splash.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/sample/css/application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setFullScreen(false);
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void display(Stage primaryStage){
        root = new BorderPane();
        gridContent = new StackPane();
        root.setCenter(gridContent);
        VBox toolbar = new VBox();
        lblEstimatedTimeOfExecution =new Label();
        lblActualTimeOfExecution =new Label();

        toolbar.setPadding(new Insets(10, 10, 0, 10));
        toolbar.setSpacing(10);
        toolbar.setPrefWidth(1300);
        toolbar.setAlignment(Pos.CENTER);

        btnStart = new JFXButton("START");
        btnStart.setOnAction(eventEventHandler);
        btnStart.setPrefWidth(100);

        btnReSetup=new JFXButton("Re-Setup");
        btnReSetup.setOnAction(eventEventHandler);
        btnReSetup.setVisible(false);
        btnReSetup.setPrefWidth(100);

        btnExit=new JFXButton("EXIT");
        btnExit.setOnAction(eventEventHandler);
        btnExit.setAlignment(Pos.CENTER_LEFT);
        btnExit.setPrefWidth(100);

        showRoutes=new CheckBox();
        showRoutes.setText("Display Queen's route");
        showRoutes.setOnAction(eventEventHandler);

        speedSlider = new Slider();
        speedLabel=new Label();
        speedSlider.setMax(200);
        speedSlider.setMin(10);
        speedSlider.setPrefWidth(200);
        speedSlider.setValue(50);
        speedSlider.setOnMouseDragged(event -> setSpeed());
        speedSlider.setOnMouseClicked(event -> setSpeed());
        speedLabel.setText(" Delay time:  50  ms ");
        setSpeed();

        HBox topMenu = new HBox();
        topMenu.setSpacing(10);
        topMenu.setAlignment(Pos.CENTER);
        topMenu.getChildren().addAll(showRoutes,speedSlider,speedLabel, lblEstimatedTimeOfExecution, lblActualTimeOfExecution);
        HBox menu =new HBox();
        menu.setSpacing(10);
        menu.setAlignment(Pos.CENTER);
        menu.getChildren().addAll(btnStart,btnReSetup);

        HBox descriptionBox = new HBox();
        infoText = new Label();
        infoText.setMaxWidth(Long.MAX_VALUE);

        Label lblSplashMessage=new Label();
        HBox.setHgrow(infoText, Priority.ALWAYS);
        descriptionBox.getChildren().addAll(btnExit,infoText,lblSplashMessage);
        infoText.setAlignment(Pos.CENTER);

        toolbar.getChildren().addAll(topMenu,menu,descriptionBox);
        root.setTop(toolbar);

        Runnable runnable= () -> {
            try {
                while (!interrupted()){
                    String[] splashMessage={"Designed and Developed by DevThrone Inc.","Algorithms look awesome when visualized","v2021.1","Protected by DevThrone license","Strictly NOT FOR SELL","Feel free to contact DevThrone devthroneke@gmail.com"};
                    int steps=0;
                    while(steps<=150){
                        switch (steps){
                            case 0:
                                Platform.runLater(() -> {
                                    new BounceIn(lblSplashMessage).play();
                                    lblSplashMessage.setText(splashMessage[0]); });
                                break;
                            case 25:
                                Platform.runLater(() -> {
                                    new BounceIn(lblSplashMessage).play();
                                    lblSplashMessage.setText(splashMessage[1]);});
                                break;
                            case 50:
                                Platform.runLater(() -> {
                                    new BounceIn(lblSplashMessage).play();
                                    lblSplashMessage.setText(splashMessage[2]);});
                                break;
                            case 75:
                                Platform.runLater(() -> {
                                    new BounceIn(lblSplashMessage).play();
                                    lblSplashMessage.setText(splashMessage[3]);});
                                break;
                            case 100:
                                Platform.runLater(() -> {
                                    new BounceIn(lblSplashMessage).play();
                                    lblSplashMessage.setText(splashMessage[4]);});
                                break;
                            case 125:
                                Platform.runLater(() -> {
                                    new BounceIn(lblSplashMessage).play();
                                    lblSplashMessage.setText(splashMessage[5]);});
                                break;
                        }
                        sleep(100);
                        ++steps;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };
        new Thread(runnable).start();

        infoText.getStyleClass().add("footer-label");
        lblSplashMessage.getStyleClass().add("footer-label");
        lblEstimatedTimeOfExecution.getStyleClass().add("footer-label");
        lblActualTimeOfExecution.getStyleClass().add("footer-label");
        toolbar.getStyleClass().add("toolbar");
        btnStart.getStyleClass().add("btnStart");
        btnReSetup.getStyleClass().add("btnStart");
        btnExit.getStyleClass().add("btnStart");

        Scene scene = new Scene(root, 1300, 720);
        scene.getStylesheets().add(getClass().getResource("/sample/css/application.css").toExternalForm());
        primaryStage.getIcons().add(new Image(getClass().getResource("/sample/images/queen4.png").toExternalForm()));
        primaryStage.setScene(scene);
        scene.setOnMousePressed(event2 -> {
            xOffset = event2.getSceneX();
            yOffset = event2.getSceneY();
        });
        scene.setOnMouseDragged(event1 -> {
            primaryStage.setX(event1.getScreenX() - xOffset);
            primaryStage.setY(event1.getScreenY() - yOffset);
        });
        setUpGrid(gridContent,root );

    }

    private void setUpGrid(StackPane gridContent,BorderPane root ){
        int numberOfQueens=5;
        try {
            String userInput=getNumberOfQueens();
            if (userInput!=null){
                numberOfQueens=Integer.parseInt(userInput);
                if (numberOfQueens<4) {
                    new NotificationWorker().notify("Sorry only more than three but less than 10 queens  allowed. Default queens [5 Queens] are ready", gridContent)
                            .showError();
                    numberOfQueens = 5;
                }
//                }else if(numberOfQueens>10){
//                    new NotificationWorker().notify("Sorry only less than 10 queens allowed. Default queens [10 Queens] are ready",gridContent)
//                            .showError();
//                    numberOfQueens=10;
//                }
            }
        } catch (NumberFormatException e) {
            new NotificationWorker().notify("Sorry provided wrong data. Default queens [5 Queens] are ready",gridContent)
                    .showError();
            e.printStackTrace();
        }
        mGrid = new Grid(numberOfQueens, numberOfQueens, gridContent.getBoundsInParent().getWidth(), gridContent.getBoundsInParent().getHeight());

        for (int row = 0; row < numberOfQueens; row++) {
            boolean dark_chess=true;
            if (row%2==0){
                dark_chess=false;
            }
            for (int column = 0; column < numberOfQueens; column++) {
                String text = "";
                Cell cell;
                if (dark_chess){
                     cell = new Cell(text, column, row, CellType.DARK_CHESS);
                     dark_chess=false;
                }else{
                    cell = new Cell(text, column, row, CellType.BRIGHT_CHESS);
                    dark_chess=true;
                }

                mGrid.add(cell, column, row);

            }
        }
        root.setCenter(mGrid);


    }
    private String getNumberOfQueens() {
        TextInputDialog textInputDialog = new TextInputDialog(null);
        textInputDialog.setTitle("QPVisualizer");
        textInputDialog.setHeaderText("Set the number of queens");
        textInputDialog.setContentText("Positive numbers only are allowed");
        Optional<String> userChoice = textInputDialog.showAndWait();
        return userChoice.orElse(null);
    }
    private void setSpeed() {
        delayTime = (int) speedSlider.getValue();
        speedSlider.setValue(delayTime);
        timer.setDelay(delayTime);
        speedLabel.setText(" Delay time: " + delayTime + " ms ");

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (timer.isRunning()){
            if (!queensProblemAlgorithm.isRunning()){
                stopTime=System.currentTimeMillis();
                totalDelayTime+=delayTime;
                Platform.runLater(this::displayEstimatedTimeOfExecution);
                queensProblemAlgorithm.solveQueensProblem(isShowRoute,infoText);
            }else{
                Platform.runLater(() ->{
                    new ZoomOut(btnStart).play();
                    btnStart.setVisible(false);
                    btnReSetup.setVisible(true);
                    new ZoomIn(btnReSetup).play();
                    btnExit.setVisible(true);
                    new ZoomIn(btnExit).play();
                    displayActualTimeOfExecution();
                });
                timer.stop();
            }
        }
    }
    private void displayActualTimeOfExecution(){
        int estimatedTimeOfExecution= (int) (stopTime-startTime);
        int timeOfExecution=estimatedTimeOfExecution-totalDelayTime;

        if (timeOfExecution>999){
            long calculatedTime=timeOfExecution/1000;
            if (calculatedTime>59){
                minute= (int) (calculatedTime/60);
                if (minute>59){
                    int hours=minute/60;
                    int newMinute=minute-(hours*60);
                    int seconds= (int) (calculatedTime-((hours*3600)+(newMinute*60)));
                    lblActualTimeOfExecution.setText("ATE:: "+hours+"hrs : "+newMinute+"min : " +seconds+" sec");

                }else{
                    int seconds= (int) (calculatedTime-(minute* 60L));
                    lblActualTimeOfExecution.setText("ATE:: "+minute+"min : " +seconds+" sec");
                }
            }else{
                lblActualTimeOfExecution.setText("ATE:: "+calculatedTime+" sec");
            }
        }else{
            lblActualTimeOfExecution.setText("ATE:: "+timeOfExecution+" ms");
        }
    }

    private void displayEstimatedTimeOfExecution() {
        long milliseconds=stopTime-startTime;
        if (milliseconds>999){
            long calculatedTime=milliseconds/1000;
            if (calculatedTime>59){
                minute= (int) (calculatedTime/60);
                if (minute>59){
                    int hours=minute/60;
                    int newMinute=minute-(hours*60);
                    int seconds= (int) (calculatedTime-((hours*3600)+(newMinute*60)));
                    lblEstimatedTimeOfExecution.setText("ETE:: "+hours+"hrs : "+newMinute+"min : " +seconds+" sec");

                }else{
                    int seconds= (int) (calculatedTime-(minute* 60L));
                    lblEstimatedTimeOfExecution.setText("ETE:: "+minute+"min : " +seconds+" sec");
                }
            }else{
                lblEstimatedTimeOfExecution.setText("ETE:: "+calculatedTime+" sec");
            }
        }else{
            lblEstimatedTimeOfExecution.setText("ETE:: "+milliseconds+" ms");
        }

    }

    EventHandler<javafx.event.ActionEvent> eventEventHandler = event -> {
        if (event.getSource() == btnStart) {
            new ZoomIn(btnStart).play();
            switch (btnStart.getText()) {
                    case "START":
                        totalDelayTime=0;
                        startTime=System.currentTimeMillis();
                        queensProblemAlgorithm=new QueensProblemAlgorithm();
                        queensProblemAlgorithm.setUp(mGrid);
                        timer.start();
                        btnStart.setText("PAUSE");
                        new ZoomOut(btnExit).play();
                        btnExit.setVisible(false);
                        break;
                    case "PAUSE":
                        timer.stop();
                        btnReSetup.setVisible(true);
                        new ZoomIn(btnReSetup).play();
                        btnExit.setVisible(true);
                        new ZoomIn(btnExit).play();
                        btnStart.setText("RESUME");
                        break;
                    case "RESUME":
                        timer.start();
                        new ZoomOut(btnReSetup).play();
                        btnReSetup.setVisible(false);
                        new ZoomOut(btnExit).play();
                        btnExit.setVisible(false);
                        btnStart.setText("PAUSE");
                        break;
                }
            }else if (event.getSource()==showRoutes){
            isShowRoute=showRoutes.isSelected();
        }else if (event.getSource()==btnReSetup){
            setUpGrid(gridContent,root);
            new ZoomOut(btnReSetup).play();
            btnReSetup.setVisible(false);
            btnStart.setText("START");
            btnStart.setVisible(true);
            new ZoomIn(btnStart).play();
        }else if (event.getSource()==btnExit){
            new NotificationWorker().darkNotify("CLICK ME TO EXIT",mGrid)
                    .onAction(new EventHandler<javafx.event.ActionEvent>() {
                        @Override
                        public void handle(javafx.event.ActionEvent event) {
                            Stage stage= (Stage) btnExit.getScene().getWindow();
                            stage.close();
                            System.exit(0);
                        }
                    }).showConfirm();
        }

    };
}
