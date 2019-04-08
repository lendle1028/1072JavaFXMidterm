package lendle.courses.soa.midterm;

import java.io.File;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;


public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root=new BorderPane();
        WebView webView=new WebView();
        root.setCenter(webView);
        TableView tableView=new TableView();
        TableColumn urlColumn=new TableColumn("URL");
        urlColumn.setCellValueFactory(new PropertyValueFactory<URLEntry, String>("url"));
        tableView.getColumns().addAll(urlColumn);
        ObservableList<URLEntry> list=FXCollections.observableArrayList(
                new URLEntry("http://www.yzu.edu.tw"),
                new URLEntry("http://www.google.com.tw")
        );
        tableView.setItems(list);
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount()==2){
                    URLEntry entry=(URLEntry) tableView.getSelectionModel().getSelectedItem();
                    webView.getEngine().load(entry.getUrl());
                }
            }
        });
        root.setLeft(tableView);
        Scene scene = new Scene(root, 800, 600);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //webView.getEngine().load(new File("/home/lendle/Desktop/temp_download/citation-327793836.txt").toURI().toString());
                //webView.getEngine().load(new File("/home/lendle/Desktop/temp_download/IEEEtran_HOWTO.pdf").toURI().toString());
                webView.getEngine().load(new File("/home/lendle/Desktop/temp_download/元智大學教師申請升等研究資料_106.07.27.docx").toURI().toString());
            }
        });
        
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
