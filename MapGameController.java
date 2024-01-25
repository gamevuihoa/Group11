import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapData {
    public static final int TYPE_SPACE = 0;
    public static final int TYPE_WALL = 1;
    public static final int TYPE_OTHER_FLAG=2;
    public static final int TYPE_TIME = 3;
    public static final int TYPE_FLOOR =4;
    private static final String mapImageFiles[] = {import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class MapGameController implements Initializable {
    public MapData mapData;
    public MoveChara chara;
    public GridPane mapGrid;
    public ImageView[] mapImageViews;
    Timeline timeline;
    private Integer timeSeconds = 60;  
    public Label scoreLabel;
    public Label timeleftLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapData = new MapData(21, 15,3,3);
        chara = new MoveChara(1, 1, mapData,this);
        mapImageViews = new ImageView[mapData.getHeight() * mapData.getWidth()];
        for (int y = 0; y < mapData.getHeight(); y ++) {
            for (int x = 0; x < mapData.getWidth(); x ++) {
                int index = y * mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x, y);
            }
        }
        drawMap(chara, mapData);
        setupTimeline();
    }
    
        private void setupTimeline() {
            timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            KeyFrame frame = new KeyFrame(Duration.seconds(1), event -> {
                timeSeconds--;
                updateTimeleftLabel();
                if (timeSeconds <= 0) {
                    timeline.stop();
                    gameOver();
                }
            });
            timeline.getKeyFrames().add(frame);
            timeline.playFromStart();
        }

        public void increaseTimeSeconds(int secondsToAdd) {
            this.timeSeconds += secondsToAdd;
            updateTimeleftLabel();
        }

    
    public void newgame() {
        mapData = new MapData(21, 15, 3, 3);
        chara = new MoveChara(1, 1, mapData,this);
        mapImageViews = new ImageView[mapData.getHeight() * mapData.getWidth()];
        for (int y = 0; y < mapData.getHeight(); y ++) {
            for (int x = 0; x < mapData.getWidth(); x ++) {
                int index = y * mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x, y);
            }
        }
        drawMap(chara, mapData);
        if (timeline != null) {
            timeline.stop(); 
        }
        timeSeconds = 60; 
        timeline.playFromStart(); 
        StageDB.getMainSound().stop();
        StageDB.getMainSound().play();
        updateTimeleftLabel();
    }

    
    private void gameOver() {
        StageDB.getMainStage().hide();
        StageDB.getMainSound().stop();
        StageDB.getGameOverStage().show();
        StageDB.getGameOverSound().play();
    }   
    
    private void updateTimeleftLabel() {
        String timeString = String.valueOf(timeSeconds);
        timeleftLabel.setText("Time Left: " + timeString);
    }    

    // Draw the map
    public void drawMap(MoveChara c, MapData m) {
        int cx = c.getPosX();
        int cy = c.getPosY();
        scoreLabel.setText("Score: " + c.getScore());
        mapGrid.getChildren().clear();
        for (int y = 0; y < mapData.getHeight(); y ++) {
            for (int x = 0; x < mapData.getWidth(); x ++) {
                int index = y * mapData.getWidth() + x;
                if (x == cx && y == cy) {
                    mapGrid.add(c.getCharaImageView(), x, y);
                } else {
                    mapGrid.add(mapImageViews[index], x, y);
                }
            }
        }
    }
    

    // Get users' key actions
    public void keyAction(KeyEvent event) {
        int[] keySelectNumbers = {1, 2, 3, 4};
        int[] exceptionNumbers = {0, 0, 0, 0};
        int[] selectNumbers = chara.getSelectedNumbers();
        if (selectNumbers != null && selectNumbers != exceptionNumbers) {
            keySelectNumbers = selectNumbers;
        }
        int A = keySelectNumbers[0];
        int W = keySelectNumbers[1];
        int S = keySelectNumbers[2];
        int D = keySelectNumbers[3];
        KeyCode key = event.getCode();
        System.out.println("keycode:" + key);
        if (key == KeyCode.A) {
            if (A == 1) {
                leftButtonAction();
            } else if (A == 2) {
                upButtonAction();
            } else if (A == 3) {
                downButtonAction();
            } else if (A == 4) {
                rightButtonAction();
            }
        } else if (key == KeyCode.W) {
            if (W == 1) {
                leftButtonAction();
            } else if (W == 2) {
                upButtonAction();
            } else if (W == 3) {
                downButtonAction();
            } else if (W == 4) {
                rightButtonAction();
            }
        } else if (key == KeyCode.S) {
            if (S == 1) {
                leftButtonAction();
            } else if (S == 2) {
                upButtonAction();
            } else if (S == 3) {
                downButtonAction();
            } else if (S == 4) {
                rightButtonAction();
            }
        } else if (key == KeyCode.D) {
            if (D == 1) {
                leftButtonAction();
            } else if (D == 2) {
                upButtonAction();
            } else if (D == 3) {
                downButtonAction();
            } else if (D == 4) {
                rightButtonAction();
            }
        } else if (key == KeyCode.O) {
            openGoalAction();
        }    
    }



    // Operations for going the cat up
    public void upButtonAction() {
        printAction("UP");
        chara.setCharaDirection(MoveChara.TYPE_UP);
        chara.move(0, -1);
        drawMap(chara, mapData);
    }

    // Operations for going the cat down
    public void downButtonAction() {
        printAction("DOWN");
        chara.setCharaDirection(MoveChara.TYPE_DOWN);
        chara.move(0, 1);
        drawMap(chara, mapData);
    }

    // Operations for going the cat right
    public void leftButtonAction() {
        printAction("LEFT");
        chara.setCharaDirection(MoveChara.TYPE_LEFT);
        chara.move(-1, 0);
        drawMap(chara, mapData);
    }

    // Operations for going the cat right
    public void rightButtonAction() {
        printAction("RIGHT");
        chara.setCharaDirection(MoveChara.TYPE_RIGHT);
        chara.move(1, 0);
        drawMap(chara, mapData);
    }
    
    public void openGoalAction () {
        if (mapData.getMap(chara.getPosX(),chara.getPosY()) == MapData.TYPE_OTHER_FLAG) {
                printAction("OPEN");
                StageDB.getMainSound().stop();
                timeline.stop();
                mapData.setMap(chara.getPosX(),chara.getPosY(),MapData.TYPE_OTHER_FLAG);
                drawMap(chara, mapData);
                if (GoalWindow.onOpen()) { 
                    newgame();
                }
            
        }
    }
    
    @FXML
    public void func1ButtonAction(ActionEvent event) {
        try {
            System.out.println("func1");
            StageDB.getMainStage().hide();
            StageDB.getMainSound().stop();
            StageDB.getGameOverStage().show();
            StageDB.getGameOverSound().play();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    public void func2ButtonAction(ActionEvent event) {
        mapData = new MapData(21, 15,3,3);
        chara = new MoveChara(1, 1, mapData,this);
        mapImageViews = new ImageView[mapData.getHeight() * mapData.getWidth()];
        for (int y = 0; y < mapData.getHeight(); y ++) {
            for (int x = 0; x < mapData.getWidth(); x ++) {
                int index = y * mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x, y);
            }
        }
        drawMap(chara, mapData);
        if (timeline != null) {
            timeline.stop(); 
        }
        timeSeconds = 60; 
        timeline.playFromStart(); 
        StageDB.getMainSound().stop();
        StageDB.getMainSound().play();
        updateTimeleftLabel();        
    }

    @FXML
    public void func3ButtonAction(ActionEvent event) {
        System.out.println("func3: Nothing to do");
    }

    @FXML
    public void func4ButtonAction(ActionEvent event) {
        System.out.println("func4: Nothing to do");
    }

    // Print actions of user inputs
    public void printAction(String actionString) {
        System.out.println("Action: " + actionString);
    }
    

}

            "png/SPACE.png",
            "png/WALL.png",
            "png/other_flag_01.png",
            "png/time.png",
            "png/rotating_floor.png"
    };

    private Image[] mapImages;
    private ImageView[][] mapImageViews;
    private int[][] maps;
    private int width; // width of the map
    private int height; // height of the map
    private int times;

    MapData(int x, int y,int times, int floor) {
        mapImages=new Image[5];
        mapImageViews = new ImageView[y][x];
        for (int i = 0; i < 5; i ++) {
            mapImages[i] = new Image(mapImageFiles[i]);
        }

        width = x;
        height = y;
        maps = new int[y][x];

        fillMap(MapData.TYPE_WALL);
        digMap(1, 3);
        setMap(19,13,MapData.TYPE_OTHER_FLAG);
        setMultipleItems(MapData.TYPE_TIME,times);
        setMultipleItems(MapData.TYPE_FLOOR,floor);
        setImageViews();
    }

    // fill two-dimentional arrays with a given number (maps[y][x])
    private void fillMap(int type) {
        for (int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x++) {
                maps[y][x] = type;
            }
        }
    }
    
    //アイテム取得後のマップの画像を再読込
    public void updateImageView(int x, int y) {
    mapImageViews[y][x].setImage(mapImages[maps[y][x]]);
  }

  //アイテム1個をマップにランダムに配置する
  public void setItem(int type) {
    int x, y;
    x = (int) (Math.random() * width);
    y = (int) (Math.random() * height);
    if (getMap(x, y) != MapData.TYPE_SPACE) {
      setItem(type);
    } else if (getMap(x, y) == MapData.TYPE_SPACE) {
      setMap(x, y, type);
    }
  }
    public void setMultipleItems(int type, int n) {
    for (int i = 0; i < n; i++) {
      setItem(type);
    }
  }
    // dig walls for making roads
    private void digMap(int x, int y) {
        setMap(x, y, MapData.TYPE_SPACE);
        int[][] dl = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };
        int[] tmp;

        for (int i = 0; i < dl.length; i ++) {
            int r = (int) (Math.random() * dl.length);
            tmp = dl[i];
            dl[i] = dl[r];
            dl[r] = tmp;
        }

        for (int i = 0; i < dl.length; i ++) {
            int dx = dl[i][0];
            int dy = dl[i][1];
            if (getMap(x + dx * 2, y + dy * 2) == MapData.TYPE_WALL) {
                setMap(x + dx, y + dy, MapData.TYPE_SPACE);
                digMap(x + dx * 2, y + dy * 2);
            }
        }
    }

    public int getMap(int x, int y) {
        if (x < 0 || width <= x || y < 0 || height <= y) {
            return -1;
        }
        return maps[y][x];
    }

    public void setMap(int x, int y, int type) {
        if (x < 1 || width <= x - 1 || y < 1 || height <= y - 1) {
            return;
        }
        maps[y][x] = type;
    }

    public ImageView getImageView(int x, int y) {
        return mapImageViews[y][x];
    }

    public void setImageViews() {
        for (int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x++) {
                mapImageViews[y][x] = new ImageView(mapImages[maps[y][x]]);
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
