package lendle.courses.soa.midterm;

import java.io.File;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

public class MainApp extends Application {

    private TabPane tabPane = null;
    private ListView<String> recentList = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        FlowPane menuPane = new FlowPane();
        Button webButton = new Button("Browser");
        Button imageButton = new Button("Image");
        menuPane.getChildren().addAll(webButton, imageButton);
        root.setTop(menuPane);

        tabPane = new TabPane();
        tabPane.setSide(Side.BOTTOM);
        root.setCenter(tabPane);

        recentList = new ListView<String>();

        ObservableList<String> list = FXCollections.observableArrayList();
        recentList.setItems(list);
        recentList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                    String url = (String) recentList.getSelectionModel().getSelectedItem();
                    if (url.endsWith(".png") || url.endsWith(".gif") || url.endsWith(".jpg") || url.endsWith(".jpeg")) {
                        openImageTab(primaryStage, url);
                    } else {
                        openWebTab(url);
                    }
                }
            }
        });
        root.setLeft(recentList);

        webButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openWebTab(null);
            }

        });

        imageButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openImageTab(primaryStage, null);

            }
        });

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void openWebTab(String url) {
        BorderPane mainPane = new BorderPane();
        BorderPane addressPane = new BorderPane();
        final TextField address = new TextField();
        Button goButton = new Button("GO");
        addressPane.setCenter(address);
        addressPane.setRight(goButton);
        mainPane.setTop(addressPane);
        WebView webView = new WebView();
        mainPane.setCenter(webView);
        webView.getEngine().setJavaScriptEnabled(true);
        Tab tab = new Tab("Browser", mainPane);
        tabPane.getTabs().add(tab);
        if (url != null) {
            webView.getEngine().load(url);
            tab.setText(url);
        }
        goButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                webView.getEngine().load(address.getText());
                tab.setText(address.getText());
                if (recentList.getItems().contains(address.getText()) == false) {
                    recentList.getItems().add(address.getText());
                }
            }
        });
    }

    private void openImageTab(Stage stage, String url) {
        if (url == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.png", "*.jpg", "*.gif", "*.jpeg"),
                    new FileChooser.ExtensionFilter("PNG Images", "*.png"),
                    new FileChooser.ExtensionFilter("JPG Images", "*.jpg", "*.jpeg"),
                    new FileChooser.ExtensionFilter("GIF Images", "*.gif")
            );
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                url = file.toURI().toString();
            }
            if (recentList.getItems().contains(url) == false) {
                recentList.getItems().add(url);
            }
        }
        if (url != null) {
            Image image = new Image(url);
            ImageView imageView = new ImageView(image);
            ScrollPane scrollPane = new ScrollPane(imageView);
            tabPane.getTabs().add(new Tab(url, scrollPane));
        }
    }
}
