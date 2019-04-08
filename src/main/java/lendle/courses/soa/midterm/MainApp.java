package lendle.courses.soa.midterm;

import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;


public class MainApp extends Application {
    private TabPane tabPane=null;
    private TableView recentList=null;
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root=new BorderPane();
        FlowPane menuPane=new FlowPane();
        Button webButton=new Button("Browser");
        Button imageButton=new Button("Image");
        menuPane.getChildren().addAll(webButton, imageButton);
        root.setTop(menuPane);
        
        tabPane=new TabPane();
        tabPane.setSide(Side.BOTTOM);
        root.setCenter(tabPane);
        
        recentList=new TableView();
        TableColumn urlColumn=new TableColumn("URL");
        urlColumn.setCellValueFactory(new PropertyValueFactory<AddressEntry, String>("url"));
        recentList.getColumns().addAll(urlColumn);
        ObservableList<AddressEntry> list=FXCollections.observableArrayList(new AddressEntry("http://www.yzu.edu.tw"),
                new AddressEntry("http://www.google.com.tw")
        );
        recentList.setItems(list);
        recentList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount()==2){
                    //URLEntry entry=(URLEntry) tableView.getSelectionModel().getSelectedItem();
                    //webView.getEngine().load(entry.getUrl());
                }
            }
        });
        root.setLeft(recentList);
        
        webButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BorderPane mainPane=new BorderPane();
                BorderPane addressPane=new BorderPane();
                final TextField address=new TextField();
                Button goButton=new Button("GO");
                addressPane.setCenter(address);
                addressPane.setRight(goButton);
                mainPane.setTop(addressPane);
                WebView webView=new WebView();
                mainPane.setCenter(webView);
                webView.getEngine().setJavaScriptEnabled(true);
                Tab tab=new Tab("Browser", mainPane);
                tabPane.getTabs().add(tab);
                goButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        webView.getEngine().load(address.getText());
                        tab.setText(address.getText());
                    }
                });
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

}
