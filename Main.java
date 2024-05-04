package application;
	
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeSet;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


public class Main extends Application {
	static int[] rep = new int[256];
	// static String outCode;
	static String fileName;
	static String fileEnd;
	static String filePath;

	static String fileName2;
	static String fileEnd2;
	static String filePath2;

	static File in;
	static TextField pathField;
	static Button compress;

	static TextField pathField2;
	static Button decompress;
	static Stage stage;
	static BorderPane root;

	static Button head;
	static Button statistics;
	static WriteData write1;

	static VBox rightBox;
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;

			root = new BorderPane();
			Scene scene = new Scene(root, 900, 700);
			root.setStyle("-fx-background-color:LIGHTBLUE");
			
			Label ll = new Label();
			ll.setText("Chose File to Compress : ");
			ll.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,15)); // set font to the label
			ll.setTextFill(Color.RED);


			
			Label l2 = new Label();
			l2.setText("Chose File to Decompress : ");
			l2.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,15)); // set font to the label
			l2.setTextFill(Color.RED);
			
			Label l3 = new Label();
			l3.setText("Compress & Decompress ");
			l3.setFont(Font.font("Time New Roman" , FontWeight.BOLD , FontPosture.ITALIC ,20)); // set font to the label


			VBox topBox = new VBox(20);
			topBox.setId("TOP");
			topBox.setAlignment(Pos.CENTER);

			pathField = new TextField();
			pathField.setMinWidth(300);
			pathField.setMinHeight(30);
			Label pathLabel = new Label("File Path");
			HBox box = new HBox(30);
			topBox.setAlignment(Pos.CENTER);
			topBox.setPadding(new Insets(20, 0, 0, 50));
			Button browse = new Button("Browse");
			browse.setMinWidth(100);
			browse.setPrefWidth(200);

			topBox.getChildren().addAll(l3, box);

			compress = new Button("Compress");
			compress.setDisable(true);
			box.getChildren().addAll(ll,compress, browse);
			compress.setPrefWidth(200);



			VBox bottomBox = new VBox(20);
			bottomBox.setId("BOTTOM");
			Label pathLabel2 = new Label("File Path");
			pathField2 = new TextField();
			pathField2.setMinWidth(300);
			pathField2.setMinHeight(30);
			HBox box2 = new HBox(30);
			bottomBox.setAlignment(Pos.CENTER);
			bottomBox.setPadding(new Insets(20, 0, 0, 50));
			Button browse2 = new Button("Browse");
			browse2.setMinWidth(100);
			browse2.setPrefWidth(200);

			decompress = new Button("Decompress");
			decompress.setDisable(true);
			decompress.setPrefWidth(200);
			//bottomBox.getChildren().add(decompress);
			box2.getChildren().addAll(l2 ,decompress, browse2);
			Label l4 = new Label();
			l4.setText("\n\n");
			bottomBox.getChildren().addAll(box2 , l4);


			browse.setOnAction(e -> readOriginFileName());
			compress.setOnAction(e -> startCompress());

			browse2.setOnAction(e -> readCompressedFile());
			decompress.setOnAction(e -> startDecompress());

			head = new Button("Header");
			statistics = new Button("Statistics");

			VBox leftBox = new VBox(30);
			leftBox.setId("LEFT");
			leftBox.setMinWidth(150);
			leftBox.setAlignment(Pos.CENTER);
			leftBox.getChildren().addAll(head, statistics);
			head.setMinWidth(150);
			statistics.setMinWidth(150);
			root.setLeft(leftBox);

			head.setDisable(true);
			statistics.setDisable(true);

			head.setOnAction(e -> {
				root.setCenter(write1.area);
				root.setRight(null);
			});

			rightBox = new VBox();

			statistics.setOnAction(e -> {
				rightBox = write1.rightBox;
				rightBox.setId("RIGHT");
				root.setRight(rightBox);
				root.setCenter(write1.statTable);
			});

			root.setTop(topBox);
			root.setBottom(bottomBox);
			

			primaryStage.setScene(scene);
			primaryStage.setTitle("Huffman Code Compresser");
			BackgroundFill fill = new BackgroundFill(Color.ANTIQUEWHITE, CornerRadii.EMPTY, Insets.EMPTY); 
			Background background = new Background(fill); 
			root.setBackground(background);
			//Image image = new Image("icon.png");
			//primaryStage.getIcons().add(image);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void readOriginFileName() {
		compress.setDisable(false);
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		in = fileChooser.showOpenDialog(null);
		fileName = in.getName();
		int ind = in.getPath().lastIndexOf(".");
		filePath = in.getPath().substring(0, ind) + ".huff";
		int index = fileName.lastIndexOf(".");
		fileEnd = fileName.substring(index + 1);
		pathField.setText(in.getPath());
		System.out.println(fileName);
		System.out.println(in.getPath());
		System.out.println(fileEnd);
	}

	static void startCompress() {
		try {
			head.setDisable(false);
			statistics.setDisable(false);
			ReadFile read = new ReadFile();
			read.readFile(rep, in);
			HuffmanCode encode = new HuffmanCode();
			encode.generateCodes(rep);
			write1 = new WriteData();
			write1.compress(encode.codes, rep, in, filePath, fileEnd);
		} catch (Exception e) {
			System.out.println("error  " + e.getMessage());
		}
	}

	static void readCompressedFile() {
		decompress.setDisable(false);
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		in = fileChooser.showOpenDialog(null);
		fileName = in.getName();
		int ind = fileName.lastIndexOf(".");
		fileName = fileName.substring(0, ind);
		int index = in.getPath().lastIndexOf(".");
		filePath = in.getPath().substring(0, index) + "-.";
		pathField2.setText(in.getPath());

	}

	static void startDecompress() {
		try {
			WriteData write = new WriteData();
			write.decompress(in, filePath, fileName);

		} catch (Exception e) {
			System.out.println("error  " + e.getMessage());
		}
	}

	public static void main(String[] args) throws IOException {

		launch(args);
	}
}