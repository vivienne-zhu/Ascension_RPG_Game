package application;


import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class represents the actions that occur during the battle phase. This class ensures that
 * proper information is displayed to the user during the course of the battle. It also gives the 
 * user the ability to actually fight the enemy via various effect linked buttons. Finally, this
 * class contains the logic behind battle animations, level up mechanics, and enemy decision making.
 * 
 * @author David Cai, Shari Sinclair and Jiayu Zhu
 */
public class BattlePhase {

	private Button attackBtn;
	private Button defendBtn;
	private Button healBtn;
	private Button magicAtkBtn;
	private VBox itemBag;
	private HBox hbBtn;
	private Button chooseEnemyBtn;
	private Button chooseEnemyTwoBtn;
	private Button chooseEnemyThreeBtn;
	private Text error;
	private Text dialogue;
	private Text dialogueTwo;
	private Text dialogueThree;
	private Text heroStam;
	private Text heroMana;
	private Text enemyStam;
	private Text enemyTwoStam;
	private Text enemyThreeStam;
	private Text heroName;
	private Text enemyName;
	private Text enemyTwoName;
	private Text enemyThreeName;
	private Stage primaryStage;
	private int floor;
	private HashSet<Integer> dead = new HashSet<Integer>();
	private int totalEnemyHealth;
	private Timeline animateOne;
	private Timeline animateTwo;
	private Timeline animateThree;
	private Timeline animateHero;
	private Rectangle staminaBar;
	private Rectangle manaBar;
	private Rectangle fullStamBar;
	private Rectangle fullManaBar;
	private Rectangle enemyOneStamBar;
	private Rectangle enemyTwoStamBar;
	private Rectangle enemyThreeStamBar;
	private Rectangle enemyOneFullStamBar;
	private Rectangle enemyTwoFullStamBar;
	private Rectangle enemyThreeFullStamBar;
	private boolean magic;
	private SoundEffect se;
	private int atkUp;

	public BattlePhase(Stage primaryStage, int floor, int totalEnemyHealth) {
		this.primaryStage = primaryStage;
		this.floor = floor;
		this.totalEnemyHealth = totalEnemyHealth;
		this.se = new SoundEffect();
		setMagic(false);
		
	}

	/**
	 * This method will display relevant combat information like  player/enemy names and health
	 * @param hero The character the player controls
	 * @param allEnemies The hashMap of all enemies
	 * @param floor The current floor the hero is on
	 */
	public void dispCombatInfo(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, int floor) {
		// Initialize stamina bar for hero
		staminaBar = new Rectangle(300.0, 10, Color.RED);
		staminaBar.setWidth(300 * (double) hero.getCurrentStamina() / (double) hero.getStamina());
		staminaBar.setArcWidth(20.0); 
		staminaBar.setArcHeight(15.0);  
		staminaBar.setStroke(Color.BLACK);

		// Initialize full stamina bar for hero
		fullStamBar = new Rectangle(300.0, 10, Color.BLACK);
		fullStamBar.setWidth(300 * (double) hero.getCurrentStamina() / (double) hero.getStamina());
		fullStamBar.setArcWidth(20.0); 
		fullStamBar.setArcHeight(15.0);  
		fullStamBar.setStroke(Color.BLACK);

		// Initialize stamina bar for enemy one
		enemyOneStamBar = new Rectangle(200.0, 10, Color.RED);
		enemyOneStamBar.setWidth(200 * (double) allEnemies.get(floor).get(0).getCurrentStamina() / (double) allEnemies.get(floor).get(0).getStamina());
		enemyOneStamBar.setArcWidth(20.0); 
		enemyOneStamBar.setArcHeight(15.0);  
		enemyOneStamBar.setStroke(Color.BLACK);

		// Initialize full stamina bar for enemy one
		enemyOneFullStamBar = new Rectangle(200.0, 10, Color.BLACK);
		enemyOneFullStamBar.setWidth(200 * (double) allEnemies.get(floor).get(0).getCurrentStamina() / (double) allEnemies.get(floor).get(0).getStamina());
		enemyOneFullStamBar.setArcWidth(20.0); 
		enemyOneFullStamBar.setArcHeight(15.0);  
		enemyOneFullStamBar.setStroke(Color.BLACK);

		if (allEnemies.get(floor).size() > 1) {
			// Initialize stamina bar for enemy two
			enemyTwoStamBar = new Rectangle(200.0, 10, Color.RED);
			enemyTwoStamBar.setWidth(200 * (double) allEnemies.get(floor).get(1).getCurrentStamina() / (double) allEnemies.get(floor).get(1).getStamina());
			enemyTwoStamBar.setArcWidth(20.0); 
			enemyTwoStamBar.setArcHeight(15.0);  
			enemyTwoStamBar.setStroke(Color.BLACK);

			// Initialize full stamina bar for enemy two
			enemyTwoFullStamBar = new Rectangle(200.0, 10, Color.BLACK);
			enemyTwoFullStamBar.setWidth(200 * (double) allEnemies.get(floor).get(1).getCurrentStamina() / (double) allEnemies.get(floor).get(1).getStamina());
			enemyTwoFullStamBar.setArcWidth(20.0); 
			enemyTwoFullStamBar.setArcHeight(15.0);  
			enemyTwoFullStamBar.setStroke(Color.BLACK);
		}

		if (allEnemies.get(floor).size() > 2) {
			// Initialize stamina bar for enemy three
			enemyThreeStamBar = new Rectangle(200.0, 10, Color.RED);
			enemyThreeStamBar.setWidth(200 * (double) allEnemies.get(floor).get(2).getCurrentStamina() / (double) allEnemies.get(floor).get(2).getStamina());
			enemyThreeStamBar.setArcWidth(20.0); 
			enemyThreeStamBar.setArcHeight(15.0);  
			enemyThreeStamBar.setStroke(Color.BLACK);

			// Initialize full stamina bar for enemy three
			enemyThreeFullStamBar = new Rectangle(200.0, 10, Color.BLACK);
			enemyThreeFullStamBar.setWidth(200 * (double) allEnemies.get(floor).get(2).getCurrentStamina() / (double) allEnemies.get(floor).get(2).getStamina());
			enemyThreeFullStamBar.setArcWidth(20.0); 
			enemyThreeFullStamBar.setArcHeight(15.0);  
			enemyThreeFullStamBar.setStroke(Color.BLACK);
		}
				
		// To display current stamina of hero and enemy (using tester enemy[0]).
		heroName = new Text(hero.getType() + ": " + hero.getName());
		heroName.setStyle(" -fx-font: normal bold 20px 'serif' ");
		heroName.setFill(Color.WHITE);
		heroStam = new Text("Stamina: " + hero.getCurrentStamina() + " / " + hero.getStamina());
		heroStam.setFill(Color.WHITE);
		heroStam.setStyle(" -fx-font: normal bold 20px 'serif' ");
		enemyName = new Text("Enemy Type: " + allEnemies.get(floor).get(0).getType());
		enemyName.setStyle(" -fx-font: normal bold 20px 'serif' ");
		enemyName.setFill(Color.WHITE);
		enemyStam = new Text("Stamina: " + allEnemies.get(floor).get(0).getCurrentStamina());
		enemyStam.setStyle(" -fx-font: normal bold 20px 'serif' ");
		enemyStam.setFill(Color.WHITE);
		if (hero.getType().equals("Mage")) {
			heroMana = new Text("Mana: "  + hero.getCurrentMana()+ " / " + hero.getMana());
			heroMana.setFill(Color.LIGHTBLUE);
			heroMana.setStyle(" -fx-font: normal bold 20px 'serif' ");
			
			// Initialize mana bar
			manaBar = new Rectangle(200.0, 10, Color.BLUE);
		    manaBar.setArcWidth(20.0); 
		    manaBar.setArcHeight(15.0);  
		    manaBar.setStroke(Color.BLACK);
		    manaBar.setVisible(false);
		    
			// Initialize full mana bar
			fullManaBar = new Rectangle(200.0, 10, Color.BLACK);
		    fullManaBar.setArcWidth(20.0); 
		    fullManaBar.setArcHeight(15.0);  
		    fullManaBar.setStroke(Color.BLACK);
		    fullManaBar.setVisible(false);
		}

		if (allEnemies.get(floor).size() > 1) {
			enemyTwoName = new Text("Enemy Type: " + allEnemies.get(floor).get(1).getType());
			enemyTwoName.setStyle(" -fx-font: normal bold 20px 'serif' ");
			enemyTwoName.setFill(Color.WHITE);
			enemyTwoStam = new Text("Stamina: " + allEnemies.get(floor).get(1).getCurrentStamina());
			enemyTwoStam.setStyle(" -fx-font: normal bold 20px 'serif' ");
			enemyTwoStam.setFill(Color.WHITE);
		}
		if (allEnemies.get(floor).size() > 2) {
			enemyThreeName = new Text("Enemy Type: " + allEnemies.get(floor).get(2).getType());
			enemyThreeName.setStyle(" -fx-font: normal bold 20px 'serif' ");
			enemyThreeName.setFill(Color.WHITE);
			enemyThreeStam = new Text("Stamina: " + allEnemies.get(floor).get(2).getCurrentStamina());
			enemyThreeStam.setStyle(" -fx-font: normal bold 20px 'serif' ");
			enemyThreeStam.setFill(Color.WHITE);
		}
	}

	/**
	 * This method will properly create and format the three dialogue boxes used for combat information
	 */
	public void dispDialogue() {
		// To display dialogue and other relevant battle info
		dialogue = new Text("");
		dialogue.setStyle(" -fx-font: normal bold 24px 'serif' ");
		dialogue.setFill(Color.WHITE);
		dialogueTwo = new Text("");
		dialogueTwo.setStyle(" -fx-font: normal bold 24px 'serif' ");
		dialogueTwo.setFill(Color.WHITE);
		dialogueThree = new Text("");
		dialogueThree.setStyle(" -fx-font: normal bold 24px 'serif' ");
		dialogueThree.setFill(Color.WHITE);
	}

	/**
	 * This method will create the necessary action buttons during the battle phase
	 */
	public void initButtons(GameCharacters hero) {

		// Creating buttons for player to fight enemies
		this.attackBtn = new Button("Attack");
		attackBtn.setStyle("sdfsdf");
		attackBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		this.defendBtn = new Button("Defend");
		defendBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		this.healBtn = new Button("Heal");
		healBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		this.magicAtkBtn = new Button("Magic Atk");
		magicAtkBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		magicAtkBtn.setVisible(false);
		if(hero.getType().equals("Mage")) {
			magicAtkBtn.setVisible(true);
			manaBar.setVisible(true);
			fullManaBar.setVisible(true);
		} 		

		this.hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.CENTER_LEFT);
		hbBtn.getChildren().addAll(attackBtn, defendBtn, healBtn);

		// Button to choose enemy
		this.chooseEnemyBtn = new Button("Attack");
		chooseEnemyBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		chooseEnemyBtn.setVisible(false);
		this.chooseEnemyTwoBtn = new Button("Attack");
		chooseEnemyTwoBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		chooseEnemyTwoBtn.setVisible(false);
		this.chooseEnemyThreeBtn = new Button("Attack");
		chooseEnemyThreeBtn.setStyle(" -fx-font: normal bold 20px 'serif' ");
		chooseEnemyThreeBtn.setVisible(false);
	}


	/**
	 * This method handles the heal function in the battle phase. 
	 * 
	 * @param hero The players chosen hero
	 * @param gc the GraphicsContext needed to display/remove images in the GUI
	 */
	public void healFunction(GameCharacters hero, GraphicsContext gc) {
		this.itemBag = new VBox();		
		itemBag.setMaxWidth(200);

		// Error message 
		this.error = new Text("you can't see me");
		this.error.setStyle(" -fx-font: normal bold 18px 'serif';  ");
		this.error.setVisible(false);
		this.error.setFill(Color.WHITE);

		// cheap potion button
		String btnInfo1 = hero.itemInfo(hero.getCp());	
		Button potionBtn = new Button(btnInfo1);
		potionBtn.setStyle(" -fx-font: normal bold 18px 'serif' ");

		potionBtn.setMaxWidth(200);
		potionBtn.setOnAction(event -> {
			hero.usePotion(hero.getCp(), this.error);
			heroStam.setText("Stamina: " + hero.getCurrentStamina() + " / " + hero.getStamina());
			staminaBar.setWidth(300 * (double) hero.getCurrentStamina() / (double) hero.getStamina());
			potionBtn.setText(hero.itemInfo(hero.getCp()));
			if (this.error.isVisible() == false) {
			    Timeline timeline = new Timeline(); 
				timeline.setCycleCount(1);
				KeyFrame frame = new KeyFrame(Duration.millis(100), ae -> {
				animateHero.stop();
				hero.displayCharacter(gc, false, false,true);});
				timeline.getKeyFrames().addAll(frame);
				Timeline timeline2 = new Timeline(); 
				timeline2.setCycleCount(1);
				KeyFrame frame2 = new KeyFrame(Duration.millis(100), ae -> 
				animateHero.play());
				timeline2.getKeyFrames().addAll(frame2);
				SequentialTransition sequence = new SequentialTransition(timeline, timeline2);
				sequence.play(); 
				//hero.displayCharacter(gc, false, false,false));
			}

		});

		// hyper potion button 
		String btnInfo2 = hero.itemInfo(hero.getHp());		
		Button hyperPotionBtn = new Button(btnInfo2);
		hyperPotionBtn.setStyle(" -fx-font: normal bold 18px 'serif' ");
		hyperPotionBtn.setMaxWidth(200);
		hyperPotionBtn.setOnAction(event -> {
			hero.usePotion(hero.getHp(), this.error);
			hyperPotionBtn.setText(hero.itemInfo(hero.getHp()));
			heroStam.setText("Stamina: " + hero.getCurrentStamina() + " / " + hero.getStamina());
			staminaBar.setWidth(300 * (double) hero.getCurrentStamina() / (double) hero.getStamina());
			if (this.error.isVisible() == false) {
				Timeline timeline = new Timeline(); 
				timeline.setCycleCount(1);
				KeyFrame frame = new KeyFrame(Duration.millis(100), ae -> {
				animateHero.stop();
				hero.displayCharacter(gc, false, false,true);});
				timeline.getKeyFrames().addAll(frame);
				Timeline timeline2 = new Timeline(); 
				timeline2.setCycleCount(1);
				KeyFrame frame2 = new KeyFrame(Duration.millis(100), ae -> 
				animateHero.play());
				timeline2.getKeyFrames().addAll(frame2);
				SequentialTransition sequence = new SequentialTransition(timeline, timeline2);
				sequence.play(); 
				//hero.displayCharacter(gc, false, false,false));
			}
		});

		// revive button 
		String btnInfo3 = "Revive:\t";
		if (hero.isHasRevive() == true) {
			btnInfo3 += "1.0";
		} else {
			btnInfo3 += "0.0";
		}
		Button reviveBtn = new Button(btnInfo3);	
		reviveBtn.setStyle(" -fx-font: normal bold 18px 'serif' ");
		reviveBtn.setMaxWidth(200);
		reviveBtn.setDisable(true);

		// set background
		itemBag.setStyle("-fx-background-color: gainsboro");
		itemBag.getChildren().addAll(potionBtn, hyperPotionBtn, reviveBtn);
		itemBag.setVisible(false);

	}
	
	/**
	 * This method attaches the proper events to button clicks. Namely it gives action
	 * to the attack, defend, heal, and choose enemy buttons.
	 * @param allEnemies The HashMap of all enemies
	 * @param hero The character the player controls
	 * @param gc The GraphicsContext used to delete and draw pictures to canvas
	 * @param transition The scene displayed after player clears the floor
	 * @param youWin The scene displayed after player wins the game
	 * @param reviveScene The scene displayed giving the player the option to revive
	 * @param gameOverScreen The scene displayed when the player loses the game
	 * @param battleMusic  The music that plays during the battle phase
	 * @param gameOverMusic The music that plays when the game is over
	 * @param youWinMusic The music that plays when you win the game
	 */
	public void eventButtons(HashMap<Integer, ArrayList<GameCharacters>> allEnemies, GameCharacters hero, 
			GraphicsContext gc, Scene transition, Scene youWin, Scene reviveScene, Scene gameOverScreen, 
			MediaPlayer battleMusic, MediaPlayer gameOverMusic , MediaPlayer youWinMusic) {
		//Event handling for when attack button is pressed
		attackBtn.setOnAction(event -> {
			itemBag.setVisible(false);
			error.setVisible(false);
			setMagic(false);

			disableButtons(true, attackBtn, healBtn, defendBtn, magicAtkBtn);
			hero.setIsDefending(false);
			if (dead.contains(1) && dead.contains(2)) {
				chooseEnemyBtn.setVisible(true);
			} else if (dead.contains(0) && dead.contains(2)) {
				chooseEnemyTwoBtn.setVisible(true);
			} else if (dead.contains(0) && dead.contains(1)) {
				chooseEnemyThreeBtn.setVisible(true);
			} else if (dead.contains(0)) {
				chooseEnemyTwoBtn.setVisible(true);
				chooseEnemyThreeBtn.setVisible(true);
			} else if (dead.contains(1)) {
				chooseEnemyBtn.setVisible(true);
				chooseEnemyThreeBtn.setVisible(true);
			} else if (dead.contains(2)) {
				chooseEnemyBtn.setVisible(true);
				chooseEnemyTwoBtn.setVisible(true);
			} else {
				chooseEnemyBtn.setVisible(true);
				chooseEnemyTwoBtn.setVisible(true);
				chooseEnemyThreeBtn.setVisible(true);
			}

		});


		magicAtkBtn.setOnAction(event ->{
			setMagic(true);
			itemBag.setVisible(false);
			error.setVisible(false);

			disableButtons(true, attackBtn, healBtn, defendBtn, magicAtkBtn);
			hero.setIsDefending(false);
			if (dead.contains(1) && dead.contains(2)) {
				chooseEnemyBtn.setVisible(true);
			} else if (dead.contains(0) && dead.contains(2)) {
				chooseEnemyTwoBtn.setVisible(true);
			} else if (dead.contains(0) && dead.contains(1)) {
				chooseEnemyThreeBtn.setVisible(true);
			} else if (dead.contains(0)) {
				chooseEnemyTwoBtn.setVisible(true);
				chooseEnemyThreeBtn.setVisible(true);
			} else if (dead.contains(1)) {
				chooseEnemyBtn.setVisible(true);
				chooseEnemyThreeBtn.setVisible(true);
			} else if (dead.contains(2)) {
				chooseEnemyBtn.setVisible(true);
				chooseEnemyTwoBtn.setVisible(true);
			} else {
				chooseEnemyBtn.setVisible(true);
				chooseEnemyTwoBtn.setVisible(true);
				chooseEnemyThreeBtn.setVisible(true);
			}

		});

		//Event handling for when defend button is pressed
		defendBtn.setOnAction(event -> {
			itemBag.setVisible(false);
			error.setVisible(false);
			setMagic(false);

			Image defendIcon = new Image("defendIcon.png", 80, 80, false, false);
			gc.drawImage(defendIcon, 150, 280); //draw defend icon
			disableButtons(true, attackBtn, healBtn, defendBtn, magicAtkBtn); //disable buttons
			hero.setIsDefending(true);
			hero.setIsEmpowered(true);
			enemyTurn(hero, allEnemies, heroStam, dialogue, dialogueTwo, dialogueThree, gc, floor, reviveScene, gameOverScreen, battleMusic, gameOverMusic);
			//Enable buttons after 1.5 secs per enemy
			Timeline timeline = new Timeline(); 
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(1500 * (allEnemies.get(floor).size() - dead.size())), ae -> 
			disableButtons(false, attackBtn, healBtn, defendBtn, magicAtkBtn));
			timeline.getKeyFrames().add(frame);
			timeline.play();

			//Delete icon after 1.5 secs per enemy
			Timeline icon = new Timeline(); 
			icon.setCycleCount(1);
			KeyFrame iconDisable = new KeyFrame(Duration.millis(1500 * (allEnemies.get(floor).size() - dead.size())), ae -> 
			gc.clearRect(150, 280, 80, 80));
			icon.getKeyFrames().add(iconDisable);
			icon.play();
		});

		//Event handling for when heal button is pressed
		this.healFunction(hero, gc);
		healBtn.setOnAction(event -> {
			this.itemBag.setVisible(true);			
		});

		// Actions to take after button to choose enemy is chosen
		chooseEnemyBtn.setOnAction(event -> {
			chooseEnemyBtnEvent(0, hero, allEnemies, transition, youWin, reviveScene, gameOverScreen, gc, battleMusic, gameOverMusic, youWinMusic);
		});

		// Actions to take after button to choose enemy is chosen
		chooseEnemyTwoBtn.setOnAction(event -> {
			chooseEnemyBtnEvent(1, hero, allEnemies, transition, youWin, reviveScene, gameOverScreen, gc, battleMusic, gameOverMusic, youWinMusic);
		});

		// Actions to take after button to choose enemy is chosen
		chooseEnemyThreeBtn.setOnAction(event -> {
			chooseEnemyBtnEvent(2, hero, allEnemies, transition, youWin, reviveScene, gameOverScreen, gc, battleMusic, gameOverMusic, youWinMusic);
		});
	}

	/**
	 * This method properly formats the GridPane layout used to display most information
	 * like character name, character health, the three dialogue boxes, and various buttons
	 * @param enemyCount The number of enemies on the floor
	 * @param hero The players chosen hero GameCharacters
	 * @return The GridPane itself so it can be used in GameGUI.java
	 */
	public GridPane gridLayout(int enemyCount, GameCharacters hero) {
		// Adding all nodes to grid
		GridPane grid = new GridPane();

		//Placements for various textboxes and buttons
		grid.add(heroName, 0, 0);
		grid.add(heroStam, 0, 1);
		grid.add(fullStamBar, 0, 2);
		grid.add(staminaBar, 0, 2);
		grid.add(hbBtn, 0, 3);
		grid.add(itemBag, 0, 4);
		grid.add(error, 0, 5);
		if(hero.getType().equals("Mage")) {
			grid.add(heroMana, 1, 1);
			grid.add(fullManaBar, 1, 2);
			grid.add(manaBar, 1, 2);
			grid.add(magicAtkBtn, 1, 3);
			GridPane.setHalignment(heroMana, HPos.CENTER);
			GridPane.setHalignment(magicAtkBtn, HPos.CENTER);
		}
		if (enemyCount == 1) {
			grid.add(dialogue, 2, 5);
			grid.add(dialogueTwo, 2, 6);
			grid.add(dialogueThree, 2, 7);
			grid.add(enemyName, 4, 0);
			grid.add(enemyStam, 4, 1);
			grid.add(enemyOneFullStamBar, 4, 2);
			grid.add(enemyOneStamBar, 4, 2);
			grid.add(chooseEnemyBtn, 4, 3);
		} else if (enemyCount == 2) {
			grid.add(dialogue, 1, 5);
			grid.add(dialogueTwo, 1, 6);
			grid.add(dialogueThree, 1, 7);
			grid.add(enemyName, 4, 0);
			grid.add(enemyStam, 4, 1);
			grid.add(enemyOneFullStamBar, 4, 2);
			grid.add(enemyOneStamBar, 4, 2);
			grid.add(chooseEnemyBtn, 4, 3);
			grid.add(enemyTwoName, 3, 0);
			grid.add(enemyTwoStam, 3, 1);
			grid.add(enemyTwoFullStamBar, 3, 2);
			grid.add(enemyTwoStamBar, 3, 2);
			grid.add(chooseEnemyTwoBtn, 3, 3);
		} else {
			grid.add(dialogue, 2, 5);
			grid.add(dialogueTwo, 2, 6);
			grid.add(dialogueThree, 2, 7);
			grid.add(enemyName, 4, 0);
			grid.add(enemyStam, 4, 1);
			grid.add(enemyOneFullStamBar, 4, 2);
			grid.add(enemyOneStamBar, 4, 2);
			grid.add(chooseEnemyBtn, 4, 3);
			grid.add(enemyTwoName, 3, 0);
			grid.add(enemyTwoStam, 3, 1);
			grid.add(enemyTwoFullStamBar, 3, 2);
			grid.add(enemyTwoStamBar, 3, 2);
			grid.add(chooseEnemyTwoBtn, 3, 3);
			grid.add(enemyThreeName, 2, 0);
			grid.add(enemyThreeStam, 2, 1);
			grid.add(enemyThreeFullStamBar, 2, 2);
			grid.add(enemyThreeStamBar, 2, 2);
			grid.add(chooseEnemyThreeBtn, 2, 3);
		}

		//Set vertical and horizontal gap spacing
		grid.setVgap(10);
		grid.setHgap(30);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setAlignment(Pos.TOP_CENTER);

		//Set location of grid
		grid.setLayoutX(20);
		grid.setLayoutY(60);
		grid.setMinSize(1200, 700);

		//Add specific size constraints to lock in formatting
		grid.getColumnConstraints().add(new ColumnConstraints(300));
		grid.getColumnConstraints().add(new ColumnConstraints(200));
		grid.getColumnConstraints().add(new ColumnConstraints(200));
		grid.getColumnConstraints().add(new ColumnConstraints(200));
		grid.getColumnConstraints().add(new ColumnConstraints(200));
		
		if (enemyCount > 1) {
			GridPane.setHalignment(enemyTwoName, HPos.CENTER);
			GridPane.setHalignment(enemyTwoStam, HPos.CENTER);
		}
		if (enemyCount > 2) {
			GridPane.setHalignment(enemyThreeName, HPos.CENTER);
			GridPane.setHalignment(enemyThreeStam, HPos.CENTER);
		}

		//Center all text within each grid panel
		GridPane.setHalignment(dialogue, HPos.CENTER);
		GridPane.setHalignment(dialogueTwo, HPos.CENTER);
		GridPane.setHalignment(dialogueThree, HPos.CENTER);
		GridPane.setHalignment(heroName, HPos.CENTER);
		GridPane.setHalignment(heroStam, HPos.CENTER);
		GridPane.setHalignment(enemyName, HPos.CENTER);
		GridPane.setHalignment(enemyStam, HPos.CENTER);
		GridPane.setHalignment(chooseEnemyBtn, HPos.CENTER);
		GridPane.setHalignment(chooseEnemyTwoBtn, HPos.CENTER);
		GridPane.setHalignment(chooseEnemyThreeBtn, HPos.CENTER);
		GridPane.setHalignment(itemBag, HPos.CENTER);
		hbBtn.setAlignment(Pos.CENTER);

		//Make gridlines visible - only for development phase
		grid.setGridLinesVisible(true);

		return grid;
	}

	/**
	 * This method allows us the animate the enemy characters image
	 * 
	 * @param allEnemies The ArrayList of enemies for that floor
	 * @param gc The GraphicsContext needed to display/remove images
	 */
	public void idleAnimate(HashMap<Integer, ArrayList<GameCharacters>> allEnemies, GraphicsContext gc) {
		if (allEnemies.get(floor).size() > 0) {
			animateOne = new Timeline();
			animateOne.setCycleCount(Timeline.INDEFINITE);
			KeyFrame frame = new KeyFrame(Duration.millis(5), ae -> 
			allEnemies.get(floor).get(0).displayCharacter(gc, false, false, false));
			KeyFrame frameTwo = new KeyFrame(Duration.millis(5), ae -> 
			allEnemies.get(floor).get(0).displayCharacter(gc, true, false, false));
			KeyFrame frameThree = new KeyFrame(Duration.millis(5), ae -> 
			allEnemies.get(floor).get(0).displayCharacter(gc, false, false, false));
			animateOne.getKeyFrames().add(frame);
			animateOne.getKeyFrames().add(frameTwo);
			animateOne.getKeyFrames().add(frameThree);
			animateOne.play();
		}

		if (allEnemies.get(floor).size() > 1) {
			animateTwo = new Timeline();
			animateTwo.setCycleCount(Timeline.INDEFINITE);
			KeyFrame frame = new KeyFrame(Duration.millis(5), ae -> 
			allEnemies.get(floor).get(1).displayCharacter(gc, false, false, false));
			KeyFrame frameTwo = new KeyFrame(Duration.millis(5), ae -> 
			allEnemies.get(floor).get(1).displayCharacter(gc, true, false, false));
			KeyFrame frameThree = new KeyFrame(Duration.millis(5), ae -> 
			allEnemies.get(floor).get(1).displayCharacter(gc, false, false, false));
			animateTwo.getKeyFrames().add(frame);
			animateTwo.getKeyFrames().add(frameTwo);
			animateTwo.getKeyFrames().add(frameThree);
			animateTwo.play();
		}

		if (allEnemies.get(floor).size() > 2) {;
		animateThree = new Timeline();
		animateThree.setCycleCount(Timeline.INDEFINITE);
		KeyFrame frame = new KeyFrame(Duration.millis(5), ae -> 
		allEnemies.get(floor).get(2).displayCharacter(gc, false, false, false));
		KeyFrame frameTwo = new KeyFrame(Duration.millis(5), ae -> 
		allEnemies.get(floor).get(2).displayCharacter(gc, true, false, false));
		KeyFrame frameThree = new KeyFrame(Duration.millis(5), ae -> 
		allEnemies.get(floor).get(2).displayCharacter(gc, false, false, false));
		animateThree.getKeyFrames().add(frame);
		animateThree.getKeyFrames().add(frameTwo);
		animateThree.getKeyFrames().add(frameThree);
		animateThree.play();
		}
	}
	
	/**
	 * 
	 * This animates the hero image in the GUI
	 * 
	 * @param hero The chosen game character of the player
	 * @param gc the GraphicsContext need to display/remove images from the GUI
	 */
	public void heroAnimate(GameCharacters hero, GraphicsContext gc) {
			animateHero = new Timeline();
			animateHero.setCycleCount(Timeline.INDEFINITE);
			KeyFrame frame = new KeyFrame(Duration.millis(5), ae -> 
			hero.displayCharacter(gc, false, false, false));
			KeyFrame frameTwo = new KeyFrame(Duration.millis(5), ae -> 
			hero.displayCharacter(gc, true, false, false));
			KeyFrame frameThree = new KeyFrame(Duration.millis(5), ae -> 
			hero.displayCharacter(gc, false, false, false));
			animateHero.getKeyFrames().add(frame);
			animateHero.getKeyFrames().add(frameTwo);
			animateHero.getKeyFrames().add(frameThree);
			animateHero.play();
		}

	/**
	 * This method covers the events that occur after pressing a chooseEnemyBtn.
	 * @param enemy The index of the enemy to be attacked
	 * @param hero The chosen game character of the player
	 * @param allEnemies HashMap of all enemy characters
	 * @param transition The scene displayed after player clears the floor
	 * @param youWin The scene displayed after player wins the game
	 * @param reviveScene The scene displayed giving the player the option to revive
	 * @param gameOverScreen The scene displayed when the player loses the game
	 * @param gc the GraphicsContext need to display/remove images from the GUI
	 * @param battleMusic  The music that plays during the battle phase
	 * @param gameOverMusic The music that plays when the game is over
	 * @param youWinMusic The music that plays when you win the game
	 */
	public void chooseEnemyBtnEvent(int enemy, GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies,
			Scene transition, Scene youWin, Scene reviveScene, Scene gameOverScreen, GraphicsContext gc, MediaPlayer battleMusic, MediaPlayer gameOverMusic,MediaPlayer youWinMusic) {
		if (isMagic() == true) {
			Timeline timeline = new Timeline(); 
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> mageTurn(hero, allEnemies, enemyStam, dialogue, 
					dialogueTwo, dialogueThree, enemy, gc, primaryStage, transition, youWin, battleMusic , youWinMusic));
			timeline.getKeyFrames().add(frame);
			Timeline timelineTwo = new Timeline();
			timelineTwo.setCycleCount(1);
			KeyFrame frameTwo = new KeyFrame(Duration.millis(1400), ae -> enemyTurn(hero, allEnemies, heroStam, 
					dialogue, dialogueTwo, dialogueThree, gc, floor, reviveScene, gameOverScreen, battleMusic, gameOverMusic));
			timelineTwo.getKeyFrames().add(frameTwo);
			SequentialTransition sequence = new SequentialTransition(timeline, timelineTwo);
			sequence.play();

			//Enable buttons after 2 seconds per enemy
			Timeline enable = new Timeline(); 
			enable.setCycleCount(1);
			KeyFrame frameEnable = new KeyFrame(Duration.millis(1600 * (allEnemies.get(floor).size() - dead.size())), ae -> 
			disableButtons(false, attackBtn, healBtn, defendBtn, magicAtkBtn));
			enable.getKeyFrames().add(frameEnable);
			enable.play();

			chooseEnemyBtn.setVisible(false);
			chooseEnemyTwoBtn.setVisible(false);
			chooseEnemyThreeBtn.setVisible(false);
		} else {
			Timeline timeline = new Timeline(); 
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> heroTurn(hero, allEnemies, enemyStam, dialogue, 
					dialogueTwo, dialogueThree, enemy, gc, primaryStage, transition, youWin, battleMusic, youWinMusic));
			timeline.getKeyFrames().add(frame);
			Timeline timelineTwo = new Timeline();
			timelineTwo.setCycleCount(1);
			KeyFrame frameTwo = new KeyFrame(Duration.millis(1400), ae -> enemyTurn(hero, allEnemies, heroStam, 
					dialogue, dialogueTwo, dialogueThree, gc, floor, reviveScene, gameOverScreen, battleMusic, gameOverMusic));
			timelineTwo.getKeyFrames().add(frameTwo);
			SequentialTransition sequence = new SequentialTransition(timeline, timelineTwo);
			sequence.play();

			//Enable buttons after 2 seconds per enemy
			Timeline enable = new Timeline(); 
			enable.setCycleCount(1);
			KeyFrame frameEnable = new KeyFrame(Duration.millis(1600 * (allEnemies.get(floor).size() - dead.size())), ae -> 
			disableButtons(false, attackBtn, healBtn, defendBtn, magicAtkBtn));
			enable.getKeyFrames().add(frameEnable);
			enable.play();

			chooseEnemyBtn.setVisible(false);
			chooseEnemyTwoBtn.setVisible(false);
			chooseEnemyThreeBtn.setVisible(false);
		}
	}

	/**
	 * This method creates display text for when it is the heroes turn to attack and updates necessary variables.
	 * 
	 * @param hero The players chosen character
	 * @param allEnemies The HashMap of all enemies 
	 * @param enemyStam The current stamina of the enemy
	 * @param dialogue Text that updates the player on what is currently happening.
	 * @param dialogueTwo Text that updates the player on what is currently happening.
	 * @param dialogueThree Text that updates the player on what is currently happening.
	 * @param choice  The enemy character the hero would like to attack (if there are multiple)
	 * @param gc The GraphicalContext needed to display/remove the enemy character image in the GUI.
	 * @param primaryStage the primary stage/ window of GUI
	 * @param transition the transition screen scene
	 * @param youWin the you win screen scene
	 * @param battleMusic Music that plays in the battle phase
	 * @param youWinMusic Music that plays when you win the game
	 */
	public void heroTurn(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, Text enemyStam, Text dialogue, 
			Text dialogueTwo, Text dialogueThree, int choice, GraphicsContext gc, Stage primaryStage, Scene transition, Scene youWin, MediaPlayer battleMusic, MediaPlayer youWinMusic) {

		//Move hero forward 
		Timeline timeline = new Timeline(); 
		if (choice == 0) {
			timeline.setCycleCount(580);
		} else if (choice == 1) {
			timeline.setCycleCount(380);
		} else {
			timeline.setCycleCount(180);
		}
		KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> move(hero, gc, true, allEnemies, floor));
		timeline.getKeyFrames().add(frame);

		//Play hit sound clip (needs to play before hero arrives)
		Timeline sound = new Timeline();
		KeyFrame soundFrame = new KeyFrame(Duration.millis(1), ae -> se.swingSound());
		sound.getKeyFrames().add(soundFrame);
		
		Timeline slash = new Timeline();
		slash.setCycleCount(10);
		KeyFrame slashFrame = new KeyFrame(Duration.millis(1), ae -> hero.displaySlashImage(gc, false, 
				allEnemies.get(floor).get(choice).getX(), allEnemies.get(floor).get(choice).getY()));
		slash.getKeyFrames().add(slashFrame);

		Timeline enemyRed = new Timeline();
		KeyFrame turnRed = new KeyFrame(Duration.millis(1), ae -> {
			if (choice == 0) {
				animateOne.stop();
			} else if (choice == 1) {
				animateTwo.stop();
			} else {
				animateThree.stop();
			}
			allEnemies.get(floor).get(choice).displayCharacter(gc, false, true, false);
		});
		enemyRed.getKeyFrames().add(turnRed);

		//Finish moving forward
		Timeline finishMove = new Timeline(); 
		finishMove.setCycleCount(130);
		KeyFrame finishFrame = new KeyFrame(Duration.millis(1), ae -> move(hero, gc, true, allEnemies, floor));
		finishMove.getKeyFrames().add(finishFrame);

		//Hero hits enemy
		Timeline hit = new Timeline();
		KeyFrame frameTwo = new KeyFrame(Duration.millis(1), ae -> {
			if (choice == 0) {
				animateOne.play();
			} else if (choice == 1) {
				animateTwo.play();
			} else {
				animateThree.play();
			}
			hitEnemy(hero, allEnemies, choice, dialogue, dialogueTwo, dialogueThree, enemyStam, gc, primaryStage, floor, transition, youWin, dead, battleMusic, youWinMusic);
		});

		hit.getKeyFrames().add(frameTwo);

		//Move hero backward
		Timeline timelineTwo = new Timeline();
		if (choice == 0) {
			timelineTwo.setCycleCount(710);
		} else if (choice == 1) {
			timelineTwo.setCycleCount(510);
		} else {
			timelineTwo.setCycleCount(310);
		}
		KeyFrame frameThree = new KeyFrame(Duration.millis(1), ae -> move(hero, gc, false, allEnemies, floor));
		timelineTwo.getKeyFrames().add(frameThree);

		SequentialTransition sequence = new SequentialTransition(timeline, sound, slash, enemyRed, finishMove, hit, timelineTwo);
		sequence.play();    	
	}

	/**
	 * This method creates animation for when a mage a magic attack on their turn and updates necessary variables.
	 * 
	 * @param hero the hero chosen by the player
	 * @param allEnemies The HashMap of all enemies 
	 * @param enemyStam The current stamina of the enemy
	 * @param dialogue Text that updates the player on what is currently happening.
	 * @param dialogue2 Text that updates the player on what is currently happening.
	 * @param dialogue3 Text that updates the player on what is currently happening.
	 * @param choice  The enemy character the hero would like to attack (if there are multiple)
	 * @param gc The GraphicalContext needed to display/remove the enemy character image in the GUI.
	 * @param primaryStage the primary stage/ window of GUI
	 * @param transition The scene after the player clears the floor
	 * @param youWin The screen after the player wins the game
	 * @param battleMusic  The music that plays during the battle phase
	 * @param youWinMusic The music that plays when you win the game
	 */
	public void mageTurn(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, Text enemyStam, Text dialogue, 
			Text dialogueTwo, Text dialogueThree, int choice, GraphicsContext gc, Stage primaryStage, Scene transition, Scene youWin, MediaPlayer battleMusic, MediaPlayer youWinMusic) {

		//Move magic blast forward
		Timeline timeline = new Timeline(); 
		if (choice == 0) {
			timeline.setCycleCount(600);
		} else if (choice == 1) {
			timeline.setCycleCount(400);
		} else {
			timeline.setCycleCount(200);
		}
		KeyFrame frame = new KeyFrame(Duration.millis(1), ae -> moveMagic(hero, hero, gc, allEnemies, floor, true, choice));
		hero.setMagicx(290);
		hero.setMagicy(520);
		timeline.getKeyFrames().add(frame);
		
		//Play hit sound clip 
		Timeline sound = new Timeline();
		KeyFrame soundFrame = new KeyFrame(Duration.millis(1), ae -> se.magicSound());
		sound.getKeyFrames().add(soundFrame);

		Timeline enemyRed = new Timeline();
		KeyFrame turnRed = new KeyFrame(Duration.millis(1), ae -> {
			if (choice == 0) {
				animateOne.stop();
			} else if (choice == 1) {
				animateTwo.stop();
			} else {
				animateThree.stop();
			}
			allEnemies.get(floor).get(choice).displayCharacter(gc, false, true, false);
		});
		enemyRed.getKeyFrames().add(turnRed);


		//Magic hits enemy
		Timeline hit = new Timeline();
		KeyFrame frameTwo = new KeyFrame(Duration.millis(100), ae -> {
			if (choice == 0) {
				animateOne.play();
			} else if (choice == 1) {
				animateTwo.play();
			} else {
				animateThree.play();
			}
			hitEnemy(hero, allEnemies, choice, dialogue, dialogueTwo, dialogueThree, enemyStam, gc, primaryStage, floor, transition, youWin, dead, battleMusic, youWinMusic); 
		});

		hit.getKeyFrames().add(frameTwo);
		
		Timeline magicClear = new Timeline();
		KeyFrame clear = new KeyFrame(Duration.millis(1), ae ->  
			hero.displayMagicAtkImage(gc, true, hero.getMagicx(),hero.getMagicy()));
		magicClear.getKeyFrames().add(clear);
		
		
		SequentialTransition sequence = new SequentialTransition(timeline, sound, enemyRed, hit, magicClear);
		sequence.play();    	
	}
	
	/**
	 * This method is called when an hero hits an enemy. It is unique
	 * from the hitHero method due to different dialogue that appears.
	 * 
	 * @param hero Player controlled hero GameCharacters
	 * @param allEnemies The HashMap of enemies
	 * @param choice The player choice of which enemy to attack
	 * @param dialogue The first textbox used to update battle info
	 * @param dialogueTwo The second textbox used to update battle info
	 * @param dialogueThree The third textbox used to update battle info
	 * @param enemyStam The textbox used to display enemy health
	 * @param gc GraphicsContext to clear character after death
	 * @param primaryStage Primary stage/window t display GUI
	 * @param floor Current floor number the hero is on
	 * @param transition The scene after the player clears the floor
	 * @param youWin The screen after the player wins the game
	 * @param dead HashSet for dead enemies
	 * @param battleMusic  The music that plays during the battle phase
	 * @param youWinMusic The music that plays when you win the game
	 */
	public void hitEnemy(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, int choice, Text dialogue, Text dialogueTwo, Text dialogueThree,
			Text enemyStam, GraphicsContext gc, Stage primaryStage, int floor, Scene transition, Scene youWin, 
			HashSet<Integer> dead, MediaPlayer battleMusic, MediaPlayer youWinMusic) {

		GameCharacters enemy = allEnemies.get(floor).get(choice);
		int attackAmount = 0;
		if (isMagic() == true) {
			attackAmount = hero.magicAttack(enemy, hero.isEmpowered());
			setMagic(false);
			if (hero.getCurrentMana() < 0) {
				hero.setCurrentMana(0);
			} else {
				hero.setCurrentMana(hero.getCurrentMana() - 50);
			}
			if (hero.getCurrentMana() < 50) {
				magicAtkBtn.setDisable(true);
			}
			heroMana.setText("Mana: " + hero.getCurrentMana() + " / " + hero.getMana());
			manaBar.setWidth(200 * (double) hero.getCurrentMana() / (double) hero.getMana());
			if (choice == 0) {
				enemyOneStamBar.setWidth(220 * (double) enemy.getCurrentStamina() / (double) enemy.getStamina());
			} else if (choice == 1) {
				enemyTwoStamBar.setWidth(220 * (double) enemy.getCurrentStamina() / (double) enemy.getStamina());
			} else {
				enemyThreeStamBar.setWidth(220 * (double) enemy.getCurrentStamina() / (double) enemy.getStamina());
			}
		} else {
			attackAmount = hero.attack(enemy, false, hero.isEmpowered());
			if (choice == 0) {
				enemyOneStamBar.setWidth(220 * (double) enemy.getCurrentStamina() / (double) enemy.getStamina());
			} else if (choice == 1) {
				enemyTwoStamBar.setWidth(220 * (double) enemy.getCurrentStamina() / (double) enemy.getStamina());
			} else {
				enemyThreeStamBar.setWidth(220 * (double) enemy.getCurrentStamina() / (double) enemy.getStamina());
			}
		}

		totalEnemyHealth -= attackAmount;
		hero.setIsEmpowered(false);
		enemy.displayCharacter(gc, false, true,false); //turn enemy red on attack	

		//If enemy dies, update information and delete enemy picture
		if (enemy.getCurrentStamina() <= 0) {
			// Add death sound effect 		
			se.enemyDeathSound();
			
			dead.add(choice);
			dialogueTwo.setText("You have killed the enemy."); 
			dialogueThree.setText("");//XP stuff and gold stuff will be here
			enemy.displayCharacter(gc, true, false, false); //deleting picture
			if (choice == 0) {
				animateOne.stop();
				enemyStam.setVisible(false);
				enemyName.setVisible(false);
			} else if (choice == 1) {
				animateTwo.stop();
				enemyTwoStam.setVisible(false);
				enemyTwoName.setVisible(false);
			} else {
				animateThree.stop();
				enemyThreeStam.setVisible(false);
				enemyThreeName.setVisible(false);
			}
			//	allEnemies.get(floor).remove(choice);	
		} else {
			//After 0.1 seconds revert color only if not dead
			Timeline timeline = new Timeline(); 
			timeline.setCycleCount(1);
			KeyFrame frame = new KeyFrame(Duration.millis(100), ae -> 
			enemy.displayCharacter(gc, false, false, false));
			timeline.getKeyFrames().add(frame);
			timeline.play();
		}
		//If all enemies dead, move on to next floor
		if (totalEnemyHealth == 0) {
			//Transition to next screen after battle after 5 seconds
			int xp = 50 * allEnemies.get(floor).size() + floor * 10;
			hero.setXp(hero.getXp() + xp);
			if (hero.getXp() >= (50 + hero.getLevel() * 80)) {
				hero.levelUp();
				hero.setXp(0);
				
			}
			if (floor < 10) {
				Timeline moveOn = new Timeline();
				moveOn.setCycleCount(1);
				KeyFrame frame = new KeyFrame(Duration.millis(3000), ae ->  primaryStage.setScene(transition));
				moveOn.getKeyFrames().add(frame);
				moveOn.play();
			} else if (floor == 10){
			    	battleMusic.stop(); 
			    	youWinMusic.play();
				Timeline moveOn = new Timeline();
				moveOn.setCycleCount(1);
				KeyFrame frame = new KeyFrame(Duration.millis(3000), ae -> primaryStage.setScene(youWin));
				moveOn.getKeyFrames().add(frame);
				moveOn.play();
			}
		}

		if (choice == 0 ) {
			enemyStam.setText("Stamina: " + enemy.getCurrentStamina());
		} else if (choice == 1) {
			enemyTwoStam.setText("Stamina: " + enemy.getCurrentStamina());
		} else {
			enemyThreeStam.setText("Stamina: " + enemy.getCurrentStamina());
		}
		dialogue.setText("You dealt " + attackAmount + " damage!");
		//dialogueTwo.setText("");
		dialogueThree.setText("");
	}

	/**
	 * This method allows us to move either the hero character or the enemies forward and
	 * backward for the animation of an attack. It will first clear the current picture
	 * off the canvas, move the X axis of image either forward or backward depending 
	 * on the boolean and repaint in the new location
	 * @param character The character we are moving
	 * @param gc The GraphicsContext used to delete and repaint
	 * @param forward Whether we are moving forward or backward
	 * @param allEnemies The HashMap of enemies
	 * @param floor The current floor the hero is on
	 */
	public void move(GameCharacters character, GraphicsContext gc, boolean forward, 
			HashMap<Integer, ArrayList<GameCharacters>> allEnemies, int floor) {

		//Clear current picture
		character.displayCharacter(gc, true, false, false);

		//Move character accordingly depending on boolean
		if (forward) {
			character.setX(character.getX() + 1);
		} else {
			character.setX(character.getX() - 1);
		}

		//Draw new picture
		character.displayCharacter(gc, false, false,false);

		if (allEnemies.get(floor).size() == 3) {
			if (character.getX() + character.getWidth() >= allEnemies.get(floor).get(1).getX() && !dead.contains(1)) {
				allEnemies.get(floor).get(1).displayCharacter(gc, false, false, false);
			}
			if (character.getX() + character.getWidth() >= allEnemies.get(floor).get(2).getX() && !dead.contains(2)) {
				allEnemies.get(floor).get(2).displayCharacter(gc, false, false, false);
			}
		} else if (allEnemies.get(floor).size() == 2) {
			if (character.getX() + character.getWidth() >= allEnemies.get(floor).get(1).getX() && !dead.contains(1)) {
				allEnemies.get(floor).get(1).displayCharacter(gc, false, false, false);
			}				
		}
	}
	
	/**
	 * This method allows us to move the image of the magic attack towards the enemy
	 * 
	 * @param character The character using the magic attack
	 * @param hero The players chosen hero character
	 * @param gc The GraphicsContext used to delete and repaint
	 * @param allEnemies  the HashMap with all the enemies
	 * @param floor the floor number the hero is on
	 * @param isHero Boolean to know whether character using magic is a hero
	 * @param choice the enemy the hero has chosen to attack
	 */
	public void moveMagic(GameCharacters character, GameCharacters hero, GraphicsContext gc, 
			HashMap<Integer, ArrayList<GameCharacters>> allEnemies, int floor, Boolean isHero, int choice) {

		//Clear current picture
		character.displayMagicAtkImage(gc, true, character.getMagicx(),character.getMagicy());

		//Move magic accordingly 
		if (isHero && character.getMagicx() < allEnemies.get(floor).get(choice).getX()) {
			character.setMagicx(character.getMagicx() + 1);
			character.displayMagicAtkImage(gc, false, character.getMagicx(),character.getMagicy());
		} else if (!isHero) {
			character.setMagicx(character.getMagicx() - 1);	
		}
		
		character.displayMagicAtkImage(gc, false, character.getMagicx(),character.getMagicy());


		if (allEnemies.get(floor).size() == 3) {
			if (character.getMagicx() >= allEnemies.get(floor).get(1).getX() && !dead.contains(1)) {
				allEnemies.get(floor).get(1).displayCharacter(gc, false, false, false);
			}
			if (character.getMagicx() >= allEnemies.get(floor).get(2).getX() && !dead.contains(2)) {
				allEnemies.get(floor).get(2).displayCharacter(gc, false, false, false);
			}
		} else if (allEnemies.get(floor).size() == 2) {
			if (character.getMagicx() >= allEnemies.get(floor).get(1).getX() && !dead.contains(1)) {
				allEnemies.get(floor).get(1).displayCharacter(gc, false, false, false);
			}				
		}
	}

	/**
	 * This method creates display text for when it is the enemies turn to attack and updates necessary variables.
	 * 
	 * @param hero Players chosen hero character
	 * @param allEnemies The HashMap of all enemies 
	 * @param heroStam The current stamina of the hero
	 * @param dialogue Text that updates the player on what is currently happening.
	 * @param dialogueTwo The second textbox used to update battle info
	 * @param dialogueThree The third textbox used to update battle info
	 * @param gc GraphicsContext to clear character after death
	 * @param floor Current floor number the hero is on
	 * @param reviveScene The scene giving the player the option to use a revive
	 * @param gameOverScreen The screen after the player loses the game
	 * @param battleMusic  The music that plays during the battle phase
	 * @param gameOverMusic The music that plays when the game is over
	 */
	public void enemyTurn(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, Text heroStam, Text dialogue, Text dialogueTwo, 
			Text dialogueThree, GraphicsContext gc, int floor, Scene reviveScene, Scene gameOverScreen, MediaPlayer battleMusic, MediaPlayer gameOverMusic) {

		//If enemies are still alive
		if (totalEnemyHealth > 0) {
			if (hero.isDefending()) {
				dialogue.setText("It is the enemy's turn.");
				dialogueTwo.setText("");
				dialogueThree.setText("");
			} else {
				dialogueTwo.setText("It is the enemy's turn.");
			}
			singleEnemyAttacks(hero, allEnemies, gc, reviveScene, gameOverScreen, battleMusic, gameOverMusic); 
		}
	}

	public void singleEnemyAttacks(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies,
			GraphicsContext gc, Scene reviveScene, Scene gameOverScreen, MediaPlayer battleMusic, MediaPlayer gameOverMusic) {
		if (hero.getCurrentStamina() > 0) {
			//	final Integer innerI = new Integer(i);

			Timeline posOneForward = new Timeline();
			Timeline posOneHit = new Timeline();
			Timeline posOneBackward = new Timeline();
			Timeline posTwoForward = new Timeline();
			Timeline posTwoHit = new Timeline();
			Timeline posTwoBackward = new Timeline();
			Timeline posThreeForward = new Timeline();
			Timeline posThreeHit = new Timeline();
			Timeline posThreeBackward = new Timeline();
			Timeline posOneNoise = new Timeline();
			Timeline posTwoNoise = new Timeline();
			Timeline posThreeNoise = new Timeline();
			

			if (!dead.contains(0)) {
				enemyMoveTimeline(0, allEnemies, gc, posOneForward, posTwoForward, posThreeForward, posOneBackward, posTwoBackward,
						posThreeBackward, posOneHit, posTwoHit, posThreeHit, posOneNoise, posTwoNoise, posThreeNoise, hero, reviveScene, gameOverScreen, battleMusic, gameOverMusic); 
			}
			if (!dead.contains(1) && (allEnemies.get(floor).size() == 2 || allEnemies.get(floor).size() == 3)) {
				enemyMoveTimeline(1, allEnemies, gc, posOneForward, posTwoForward, posThreeForward, posOneBackward, posTwoBackward,
						posThreeBackward, posOneHit, posTwoHit, posThreeHit, posOneNoise, posTwoNoise, posThreeNoise, hero, reviveScene, gameOverScreen, battleMusic, gameOverMusic);
			}
			if (!dead.contains(2) && allEnemies.get(floor).size() == 3) {
				enemyMoveTimeline(2, allEnemies, gc, posOneForward, posTwoForward, posThreeForward, posOneBackward, posTwoBackward,
						posThreeBackward, posOneHit, posTwoHit, posThreeHit, posOneNoise, posTwoNoise, posThreeNoise, hero, reviveScene, gameOverScreen, battleMusic, gameOverMusic);
			}
			if (!dead.contains(0) && !dead.contains(1) && !dead.contains(2)) { //AAA
				SequentialTransition sequence = new SequentialTransition(posOneForward, posOneNoise, posOneHit, posOneBackward, posTwoForward, posTwoNoise, posTwoHit, posTwoBackward, 
						posThreeForward, posThreeNoise, posThreeHit, posThreeBackward);
				sequence.play();
			} else if (!dead.contains(0) && dead.contains(1) && dead.contains(2)) { //DDA
				SequentialTransition sequence = new SequentialTransition(posOneForward, posOneNoise, posOneHit, posOneBackward);
				sequence.play();
			} else if (dead.contains(0) && !dead.contains(1) && dead.contains(2)) { //DAD
				SequentialTransition sequence = new SequentialTransition(posTwoForward, posTwoNoise, posTwoHit, posTwoBackward);
				sequence.play();
			} else if (dead.contains(0) && dead.contains(1) && !dead.contains(2)) { //ADD
				SequentialTransition sequence = new SequentialTransition(posThreeForward, posThreeNoise, posThreeHit, posThreeBackward);
				sequence.play();
			} else if (!dead.contains(0) && !dead.contains(1) && dead.contains(2)) { //DAA
				SequentialTransition sequence = new SequentialTransition(posOneForward, posOneNoise, posOneHit, posOneBackward, posTwoForward, posTwoNoise, posTwoHit, posTwoBackward);
				sequence.play();
			} else if (dead.contains(0) && !dead.contains(1) && !dead.contains(2)) { //AAD
				SequentialTransition sequence = new SequentialTransition(posTwoForward, posTwoNoise, posTwoHit, posTwoBackward, posThreeForward, posThreeNoise, posThreeHit, posThreeBackward);
				sequence.play();
			} else if (!dead.contains(0) && dead.contains(1) && !dead.contains(2)) { //ADA
				SequentialTransition sequence = new SequentialTransition(posOneForward, posOneNoise, posOneHit, posOneBackward, posThreeForward, posThreeNoise, posThreeHit, posThreeBackward);
				sequence.play();
			}
		}
	}

	/**
	 * This method takes in the various timelines and adds the appropriate values to them depending on which enemy 
	 * is attacking. This will control how far each enemy should move according to its position.
	 *  
	 * @param position Integer position (0 is right most)
	 * @param allEnemies HashMap of all Enemies
	 * @param gc GraphicsContext to draw and redraw enemies
	 * @param posOneForward Timeline to move 0th enemy to hero
	 * @param posTwoForward Timeline to move 1st enemy to hero
	 * @param posThreeForward Timeline to move 2nd enemy to hero
	 * @param posOneBackward Timeline to move 0th enemy back
	 * @param posTwoBackward Timeline to move 1st enemy back
	 * @param posThreeBackward Timeline to move 2nd enemy back
	 * @param posOneHit Timeline to let 0th enemy hit hero
	 * @param posTwoHit Timeline to let 1st enemy hit hero
	 * @param posThreeHit Timeline to let 2nd enemy hit hero
	 * @param posOneNoise Timeline to play attack sound for 0th enemy 
	 * @param posTwoNoise Timeline to play attack sound for 1st enemy 
	 * @param posThreeNoise Timeline to play attack sound for 2nd enemy
	 * @param hero Player controlled hero GameCharacters
	 * @param reviveScene Scene to show revive option
	 * @param gameOverScreen Scene to show game over
	 * @param battleMusic Music that plays during battle phase
	 * @param gameOverMusic Music that plays when the game is over
	 */
	public void enemyMoveTimeline(int position, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, GraphicsContext gc,
			Timeline posOneForward, Timeline posTwoForward, Timeline posThreeForward, Timeline posOneBackward, Timeline posTwoBackward, 
			Timeline posThreeBackward, Timeline posOneHit, Timeline posTwoHit, Timeline posThreeHit, Timeline posOneNoise, Timeline posTwoNoise, Timeline posThreeNoise,
			GameCharacters hero, Scene reviveScene, Scene gameOverScreen, MediaPlayer battleMusic, MediaPlayer gameOverMusic) {
		//Move enemy forward and backwards
		KeyFrame moveForward;
		KeyFrame moveBackward;
		if (!(allEnemies.get(floor).get(position) instanceof RangedEnemy)) {
			moveForward = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(floor).get(position), gc, false,
					allEnemies, floor));
			moveBackward = new KeyFrame(Duration.millis(1), ae -> move(allEnemies.get(floor).get(position), gc, true,
					allEnemies, floor));
		} else {
			moveForward = new KeyFrame(Duration.millis(1), ae -> moveMagic(allEnemies.get(floor).get(position), 
					hero, gc, allEnemies, floor, false, 0));
			moveBackward = new KeyFrame(Duration.millis(1), ae -> {
				allEnemies.get(floor).get(position).displayMagicAtkImage(gc, true, allEnemies.get(floor).get(position).getMagicx(), 
						allEnemies.get(floor).get(position).getMagicy());
				allEnemies.get(floor).get(position).setMagicx(allEnemies.get(floor).get(position).getOldMagicx()); 
			});
		}
		
		KeyFrame soundFrame;
		KeyFrame soundFrameTwo;
		KeyFrame soundFrameThree;
		
		//Hit noises
		if (allEnemies.get(floor).size() > 0) {
			posOneNoise.setCycleCount(1);
			if (allEnemies.get(floor).get(0) instanceof RangedEnemy) {
				soundFrame = new KeyFrame(Duration.millis(1), ae -> se.magicSound());
			} else if (allEnemies.get(floor).get(0) instanceof MeleeEnemy) {
				soundFrame = new KeyFrame(Duration.millis(1), ae -> se.swingSound());
			} else {
				soundFrame = new KeyFrame(Duration.millis(1), ae -> se.swingSound());
			}
			posOneNoise.getKeyFrames().add(soundFrame);
		}
			
		if (allEnemies.get(floor).size() > 1) {
			posTwoNoise.setCycleCount(1);
			if (allEnemies.get(floor).get(1) instanceof RangedEnemy) {
				soundFrameTwo = new KeyFrame(Duration.millis(1), ae -> se.magicSound());
			} else if (allEnemies.get(floor).get(1) instanceof MeleeEnemy) {
				soundFrameTwo = new KeyFrame(Duration.millis(1), ae -> se.swingSound());
			} else {
				soundFrameTwo = new KeyFrame(Duration.millis(1), ae -> se.swingSound());
			}
			posTwoNoise.getKeyFrames().add(soundFrameTwo);
		}
		
		if (allEnemies.get(floor).size() > 2) {
			posThreeNoise.setCycleCount(1);
			if (allEnemies.get(floor).get(2) instanceof RangedEnemy) {
				soundFrameThree = new KeyFrame(Duration.millis(1), ae -> se.magicSound());
			} else if (allEnemies.get(floor).get(2) instanceof MeleeEnemy) {
				soundFrameThree = new KeyFrame(Duration.millis(1), ae -> se.swingSound());
			} else {
				soundFrameThree = new KeyFrame(Duration.millis(1), ae -> se.swingSound());
			}
			posThreeNoise.getKeyFrames().add(soundFrameThree);
		}
		
		//Enemy hits hero	
		KeyFrame frameTwo = new KeyFrame(Duration.millis(1), ae -> {
			hitHero(hero, allEnemies, dialogueTwo, dialogueThree, heroStam, position, gc, reviveScene, gameOverScreen, battleMusic, gameOverMusic, false);
		});
		
		//Boss Outrage hits hero	
		KeyFrame bossHit = new KeyFrame(Duration.millis(1), ae -> {
			hitHero(hero, allEnemies, dialogueTwo, dialogueThree, heroStam, position, gc, reviveScene, gameOverScreen, battleMusic, gameOverMusic, true);
		});
		
		if (position == 0) { //boss can only be this position
			posOneForward.setCycleCount(745);
			posOneForward.getKeyFrames().add(moveForward);
			posOneBackward.setCycleCount(745);
			posOneBackward.getKeyFrames().add(moveBackward);	
			if (allEnemies.get(floor).get(0) instanceof BossEnemy && 
					((double) allEnemies.get(floor).get(0).getCurrentStamina() / 
							(double) allEnemies.get(floor).get(0).getStamina() < 0.34)) {
				posOneHit.getKeyFrames().add(bossHit);
			} else {
				posOneHit.getKeyFrames().add(frameTwo);
			}
		} else if (position == 1) {
			posTwoForward.setCycleCount(505);
			posTwoForward.getKeyFrames().add(moveForward);
			posTwoBackward.setCycleCount(505);
			posTwoBackward.getKeyFrames().add(moveBackward);
			posTwoHit.getKeyFrames().add(frameTwo);
		} else { 
			posThreeForward.setCycleCount(275);
			posThreeForward.getKeyFrames().add(moveForward);
			posThreeBackward.setCycleCount(275);
			posThreeBackward.getKeyFrames().add(moveBackward);
			posThreeHit.getKeyFrames().add(frameTwo);
		}
	}

	/**
	 * This method is called when an enemy hits the hero. It is unique
	 * from the hitEnemy method due to different dialogue that appears.
	 * 
	 * @param hero Player controlled hero GameCharacters
	 * @param allEnemies The HashMap of all enemies
	 * @param dialogueTwo The second textbox used to update battle info
	 * @param dialogueThree The third textbox used to update battle info
	 * @param heroStam The textbox used to display hero health
	 * @param i The counter for which enemy attacks
	 * @param gc GraphicsContext to clear character after death
	 * @param reviveScene Scene to show revive option
	 * @param gameOverScreen Scene to show game over
	 * @param battleMusic Music that plays during battle phase
	 * @param gameOverMusic Music that plays when the game is over
	 */
	public void hitHero(GameCharacters hero, HashMap<Integer, ArrayList<GameCharacters>> allEnemies, 
			Text dialogueTwo, Text dialogueThree, Text heroStam, int i, GraphicsContext gc, Scene reviveScene, 
			Scene gameOverScreen, MediaPlayer battleMusic, MediaPlayer gameOverMusic, Boolean outrage) { 
		int attackAmount = allEnemies.get(floor).get(i).attack(hero, outrage, false);
		Timeline heroRed = new Timeline();
		heroRed.setCycleCount(1);
		KeyFrame redHero = new KeyFrame(Duration.millis(1), ae -> {
		animateHero.stop();
		hero.displayCharacter(gc, false, true, false);}); //turn hero red on attack
		heroRed.getKeyFrames().add(redHero);
		heroRed.play();
		
		//After 0.1 seconds revert color
		Timeline timeline = new Timeline(); 
		timeline.setCycleCount(1); //hero.displayCharacter(gc, false, false,false));
		KeyFrame frame = new KeyFrame(Duration.millis(100), ae -> 
		animateHero.play());
		timeline.getKeyFrames().add(frame);
		timeline.play();

		heroStam.setText("Stamina: " + hero.getCurrentStamina() + " / " + hero.getStamina());
		staminaBar.setWidth(300 * (double) hero.getCurrentStamina() / (double) hero.getStamina());
		if (hero.isDefending()) {
		    	if (attackAmount <= 0) {
		    	    dialogueTwo.setText(""); // You took 0 damage!
		    	} else {
		    	    dialogueTwo.setText("You took " + attackAmount + " damage!");
		    	}
		} else {
			dialogueThree.setText("You took " + attackAmount + " damage!");
		}
		if (attackAmount <= 0) {
			dialogueThree.setText("The enemy's attack had no effect on you!");

		} else {
			if (hero.isDefending()) {
				dialogueThree.setText("Your defense blocked " + attackAmount + " damage!");
			}
			if (hero.getCurrentStamina() <= 0) {
				hero.displayCharacter(gc, true, false,false);
			}
		}

		// if hero gets killed 
		if (hero.getCurrentStamina() == 0) {
		    se.heroDeathSound();
			if (hero.isHasRevive() == true) {
				Timeline moveOn = new Timeline();
				moveOn.setCycleCount(1);
				KeyFrame frame1 = new KeyFrame(Duration.millis(4000), ae ->  primaryStage.setScene(reviveScene));
				moveOn.getKeyFrames().add(frame1);
				moveOn.play();
			} else {
			    	battleMusic.stop();
			    	gameOverMusic.play();
				Timeline moveOn = new Timeline();
				moveOn.setCycleCount(1);
				KeyFrame frame1 = new KeyFrame(Duration.millis(4000), ae ->  primaryStage.setScene(gameOverScreen));
				moveOn.getKeyFrames().add(frame1);
				moveOn.play();
			}
		}
	}

	/** This method disables/enables all user input buttons
	 * 
	 * @param disable If true, disable all buttons. Enable otherwise.
	 * @param attackBtn Button to attack
	 * @param healBtn Button to use item
	 * @param defendBtn Button to defend
	 * @param magicAtkBtn Button to do a magic attack
	 */
	public void disableButtons(boolean disable, Button attackBtn, Button healBtn, Button defendBtn, Button magicAtkBtn) {
		attackBtn.setDisable(disable);
		healBtn.setDisable(disable);
		defendBtn.setDisable(disable);
		magicAtkBtn.setDisable(disable);
	}

	/**
	 * @return the magic
	 */
	public boolean isMagic() {
		return magic;
	}


	/**
	 * @param magic the magic to set
	 */
	public void setMagic(boolean magic) {
		this.magic = magic;
	}

}