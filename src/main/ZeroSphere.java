package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ZeroSphere extends Application
{
	static Stage window;
	static Scene sceneZero;
	static BorderPane rootZero;
	static Main profile;

	/*
	 * 		INICJALIZACJA OKNA I START APLIKACJI
	 */
	@Override
	public void start(Stage primaryStage) throws Exception 
	{	
		ZeroSphere.window = primaryStage;
		ZeroSphere.window.setTitle("PM");
		initZeroScreen();	
	}
	private void initZeroScreen() throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ZeroSphereView.fxml"));
		rootZero = loader.load();
		sceneZero = new Scene(rootZero);
		rootZero.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		window.setScene(sceneZero);
		window.show();
		window.setResizable(false);
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}

	public static void goMainScreen() throws IOException
	{
		// NOWY PROFIL
		profile = new Main(window);
		profile.data = new Data(profile);
		profile.sceneSetter(profile.mainSceneGetter());
		Data.textLogs.appendText("Rozpoczêto nowy program.\n");
	}
	public static Main profileGetter()
	{
		return profile;
	}

}
