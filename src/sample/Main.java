package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private Button hurt = new Button("Hurt");
    private Button slash = new Button("Slash");
    private Button die = new Button("Die");
    private AnimatedImage sprite = new AnimatedImage();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage theStage) {
        theStage.setTitle("Sprite Animation");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        Canvas canvas = new Canvas(900, 800);
        //root.getChildren().add(canvas);

        //create buttons across the bottom
        HBox hbox = new HBox();
        hbox.setSpacing(20);
        hbox.setAlignment(Pos.CENTER);

        //set the text size on the buttons
        hurt.setStyle("-fx-font-size:40");
        slash.setStyle("-fx-font-size:40");
        die.setStyle("-fx-font-size:40");

        //default
        getAnimatedImage("hurt");

        //associated the buttons with the methods
        hurt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getAnimatedImage("hurt");
            }
        });

        slash.setOnAction(e -> getAnimatedImage("slash"));
        die.setOnAction(e -> getAnimatedImage("die"));


        hbox.getChildren().addAll(hurt,slash,die);

        vbox.getChildren().addAll(canvas, hbox);
        root.getChildren().add(vbox);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        //create an animated sprite and load it into
        //an array

        Image background = new Image( "Images/hill.jpg" );

        final long startNanoTime = System.nanoTime();

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                gc.drawImage(background,0,0);
                gc.drawImage(sprite.getFrame(t), 50, -100);
            }
        }.start();

        theStage.show();
    }

    private Image getImage(int spriteNum, String spriteFile) {

        if (spriteNum < 10)
            return new Image(spriteFile+"0"+spriteNum+".png");
        else
            return new Image(spriteFile+spriteNum+".png");
    }

    private void getAnimatedImage(String actionName)
    {
        if (actionName.equalsIgnoreCase("hurt")){
            Image[] imageArray = new Image[12];
            for (int i = 0; i < 12; i++)
                imageArray[i] = getImage(i,"Images/0_Reaper_Man_Hurt_0");
            sprite.setFrames(imageArray);
        }
        else if (actionName.equalsIgnoreCase("slash"))
        {
            Image[] imageArray = new Image[12];
            for (int i = 0; i < 12; i++)
                imageArray[i] = getImage(i,"Images/0_Reaper_Man_Slashing in the Air_0");
            sprite.setFrames(imageArray);
        }
        else if (actionName.equalsIgnoreCase("die"))
        {
        Image[] imageArray = new Image[15];
        for (int i = 0; i < 15; i++)
            imageArray[i] = getImage(i,"Images/0_Reaper_Man_Dying_0");
        sprite.setFrames(imageArray);
        }
        sprite.setDuration(0.100);
    }

}
