package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {
    public static final int WIDTH = 1210;
    public static final int HEIGHT = 850;

    // animation
    boolean animation = true;
    long animInterval = 500_000_000;
    public AnimationTimer timer = new AnimationTimer(){
        long offset = 0;
        @Override
        public void handle(long now){// now[nsec]
            if((now - offset) >= animInterval){
                offset = now;

                Controller.map.updateCells();
                Controller.map.paint();
            }
        }
    };

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("LifeGame");
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);

        primaryStage.show();
        timer.start();
        scene.setOnKeyPressed(this::keyPressed);
    }

    private void keyPressed(KeyEvent event){
        switch(event.getCode()){
            case M:
                if(animation){
                    timer.stop();
                }else{
                    timer.start();
                }
                animation = !animation;
                break;
            case K:
                animInterval /= 2;
                break;
            case L:
                animInterval *= 2;
                break;
            case SPACE:
                if(!animation) {
                    Controller.map.updateCells();
                    Controller.map.paint();
                }
                break;
            case ENTER:
                Controller.map.randomSetCells();
                Controller.map.paint();
                break;
        }
    }


    public static void main(String[] args) {
        System.out.println("hoge");
        launch(args);
    }
}
