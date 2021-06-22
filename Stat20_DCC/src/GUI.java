import java.io.File;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {
	Character character = new Character();

	@Override
	public void start (Stage primaryStage) throws Exception {
		openScreen(primaryStage);
	}

	private void openScreen(Stage primaryStage) {
		//Two text fields for the player's and character's name each. Arranged horizontally.
		TextField tfPlayerName = new TextField();
		TextField tfCharacterName = new TextField();
		HBox hbPlayerName = new HBox(29, new Label ("Player Name"), tfPlayerName);
		HBox hbCharacterName = new HBox(10, new Label ("Character Name"), tfCharacterName);
		HBox hbNames = new HBox(20, hbPlayerName, hbCharacterName);

		//Two options for gender. Arranged vertically.
		RadioButton rbMale = new RadioButton("Male");
		rbMale.setUserData("Male");
		RadioButton rbFemale = new RadioButton("Female");
		rbFemale.setUserData("Female");
		ToggleGroup genderGroup = new ToggleGroup();
		rbMale.setToggleGroup(genderGroup);
		rbFemale.setToggleGroup(genderGroup);
		VBox vbGender = new VBox(10, new Label("Gender"), rbMale, rbFemale);

		//Slider for alignment with three slots, ranging from lawful to neutral to chaotic.
		Slider law = new Slider(0, 2, 1);
		law.setSnapToTicks(true);
		law.setShowTickMarks(true);
		law.setMajorTickUnit(4);
		HBox hbLaw = new HBox(new Label("Lawful"), law, new Label("Chaotic"));
		hbLaw.setAlignment(Pos.CENTER);
		hbLaw.setPadding(new Insets(0, 0, 0, 50));
		HBox hbGenderAndLaw = new HBox(vbGender, hbLaw);

		GridPane gBaseStats = new GridPane();
		gBaseStats.setHgap(10);
		gBaseStats.setVgap(5);
		gBaseStats.addColumn(0, new Label("Strength:"), new Label("Agility:"), new Label("Stamina:"),
				new Label("Personality:"), new Label("Luck:"), new Label("Intelligence:"));
		TextField tfStr = new TextField("0");
		TextField tfAgl = new TextField("0");
		TextField tfSta = new TextField("0");
		TextField tfPer = new TextField("0");
		TextField tfLuc = new TextField("0");
		TextField tfInt = new TextField("0");
		Button btRollBaseStats = new Button ("Roll Stats");
		btRollBaseStats.setOnAction(e -> rollBaseStats(tfStr, tfAgl, tfSta, tfPer, tfLuc, tfInt, gBaseStats));
		gBaseStats.addColumn(1, tfStr, tfAgl, tfSta, tfPer, tfLuc, tfInt, btRollBaseStats);
		//Additional: Sticking in a modifiers column!
		//I need to get the mod column to be internal to the page runtime, not function-only. The function will
		//have to do the updating.

		//Combobox with 100 options for occupations (Yes, DCC actually has 100 options!)
		Label lOccupation = new Label("Occupation");
		ComboBox<String> cbOccupation = new ComboBox<String>();
		cbOccupation.getItems().addAll("occ1", "occ2", "occ3", "occ4");
		cbOccupation.setVisibleRowCount(4);
		HBox hbOccupationStats = new HBox(10, gBaseStats, lOccupation, cbOccupation);

		//Boxes to declare what the character's HP, CP,
		Label lHP = new Label("HP");
		TextField tfHP = new TextField("0");
		Button btRollHP = new Button ("Roll HP");
		btRollHP.setOnAction(e -> rollHP(tfHP));

		//Boxes to declare what the character's equipment/weapons are

		//Terminal buttons, for clearing the sheet or saving it.
		Button btClear = new Button("Clear All");
		Button btSave = new Button("Save Sheet");
		HBox hbButtons = new HBox(20, btClear, btSave);
		hbButtons.setAlignment(Pos.BASELINE_CENTER);
		hbButtons.setPadding(new Insets(20));
		VBox allBoxes = new VBox(20, hbNames, hbGenderAndLaw, hbOccupationStats, hbButtons);
		allBoxes.setPadding(new Insets(30));

		Pane pane = new Pane(allBoxes);

		Scene scene = new Scene(pane);
		primaryStage.setTitle("Stat20: Level 0 Character Creator--Dungeon Crawl Classic"); 		// Set title
	    primaryStage.setScene(scene); 								// Place the scene in the stage
	    primaryStage.show();

	    btClear.setOnAction(e -> clearAll(primaryStage));
		btSave.setOnAction(e -> saveSheet(primaryStage));
	}
	private void rollBaseStats(TextField str, TextField agl, TextField sta, TextField per, TextField luc, TextField intl, GridPane gBaseStats){
		character.randAbilityScores();
		str.setText(Integer.toString(character.abilityScores[0]));
		agl.setText(Integer.toString(character.abilityScores[1]));
		sta.setText(Integer.toString(character.abilityScores[2]));
		per.setText(Integer.toString(character.abilityScores[3]));
		luc.setText(Integer.toString(character.abilityScores[4]));
		intl.setText(Integer.toString(character.abilityScores[5]));
		//Additional: Sticking in a modifiers column!
		int[] modLabelText = new int[6];
		for (int v = 0; v < 6; v++) {
			modLabelText[v] = character.getModifier(character.abilityScores[v]);
			//System.out.println(modLabelText[v]);
			}
		gBaseStats.addColumn(2, new Label(Integer.toString(modLabelText[0])),
			new Label(Integer.toString(modLabelText[1])),
			new Label(Integer.toString(modLabelText[2])),
			new Label(Integer.toString(modLabelText[3])),
			new Label(Integer.toString(modLabelText[4])),
			new Label(Integer.toString(modLabelText[5])) );
	}
	private void rollHP(TextField hp) {
		character.randHP();
		hp.setText(Integer.toString(character.hp));
	}
	private void clearAll(Stage prevStage){

	}
	private void saveSheet(Stage prevStage){

	}
	public static void main(String[] args) {
		launch(args);
	}
}
