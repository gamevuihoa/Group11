import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.animation.AnimationTimer;
import java.util.Arrays;
import java.util.Random;

public class MoveChara {
    public static final int TYPE_DOWN = 0;
    public static final int TYPE_LEFT = 1;
    public static final int TYPE_RIGHT = 2;
    public static final int TYPE_UP = 3;

    private final String[] directions = { "Down", "Left", "Right", "Up" };
    private final String[] animationNumbers = { "1", "2", "3" };
    private final String pngPathPre = "png/Chara_";
    private final String pngPathSuf = ".png";

    public int[] selectedNumbers;

    private int posX;
    private int posY;

    private MapData mapData;

    private Image[][] charaImages;
    private ImageView[] charaImageViews;
    private ImageAnimation[] charaImageAnimations;

    private int charaDirection;
    private int score;	
    private MapGameController gameController;

    MoveChara(int startX, int startY, MapData mapData, MapGameController gameController) {
        this.mapData = mapData;
        this.gameController = gameController;

        charaImages = new Image[4][3];
        charaImageViews = new ImageView[4];
        charaImageAnimations = new ImageAnimation[4];

        for (int i = 0; i < 4; i++) {
            charaImages[i] = new Image[3];
            for (int j = 0; j < 3; j++) {
                charaImages[i][j] = new Image(
                        pngPathPre + directions[i] + animationNumbers[j] + pngPathSuf);
            }
            charaImageViews[i] = new ImageView(charaImages[i][0]);
            charaImageAnimations[i] = new ImageAnimation(
                    charaImageViews[i], charaImages[i]);
        }

        posX = startX;
        posY = startY;

        setCharaDirection(TYPE_RIGHT); // start with right-direction
    }  

    // set the man's direction
    public void setCharaDirection(int cd) {
        charaDirection = cd;
        for (int i = 0; i < 4; i++) {
            if (i == charaDirection) {
                charaImageAnimations[i].start();
            } else {
                charaImageAnimations[i].stop();
            }
        }
    }

    // check whether the man can move on
    private boolean isMovable(int dx, int dy) {
        if (mapData.getMap(posX + dx, posY + dy) == MapData.TYPE_WALL) {
            return false;
        } else if (mapData.getMap(posX + dx, posY + dy) == MapData.TYPE_SPACE) {
            return true;
        }
        return true;
    }

    // move the man
    public boolean move(int dx, int dy) {
        if (isMovable(dx, dy)) {
            posX += dx;
            posY += dy;
	    System.out.println("chara[X,Y]:" + posX + "," + posY);
		if (hasItem(posX,posY)) {
            int itemType = mapData.getMap(posX,posY);
                if(itemType == MapData.TYPE_TIME){
                    //表示されている時間を増やすプログラムをここに書く
                    gameController.increaseTimeSeconds(10);
		    score += 100;
                    mapData.setMap(posX, posY, MapData.TYPE_SPACE);
                    mapData.updateImageView(posX, posY);
                }else if(itemType == MapData.TYPE_FLOOR){
                    //方向キーを変えるために配列の要素をシャッフルする
                    int[] numbers = {1, 2, 3, 4};
                    shuffleArray(numbers);
                    selectedNumbers = Arrays.copyOfRange(numbers, 0, 4);
                } else if(mapData.getMap(posX, posY) == MapData.TYPE_OTHER_FLAG){
                    gameController.openGoalAction();
                System.out.println("Game Clear!");
                }
        }
            return true;
        } else {
            return false;
        }
    }

        


    public int[] getSelectedNumbers() {
        return selectedNumbers;
    }


	// check the place if there is a pickable item
public boolean hasItem (int x, int y){
    if (mapData.getMap(x,y) >= MapData.TYPE_TIME && mapData.getMap(x,y) <= MapData.TYPE_FLOOR) {
        return true;
    } else {
        return false;
    }
}

    // getter: direction of the man
    public ImageView getCharaImageView() {
        return charaImageViews[charaDirection];
    }

    // getter: x-positon of the man
    public int getPosX() {
        return posX;
    }

    // getter: y-positon of the man
    public int getPosY() {
        return posY;
    }

    public int getScore(){
        return score;
    }

    public int setScore(int score){
        return this.score = score;
    }	

    // Show the man animation
    private class ImageAnimation extends AnimationTimer {

        private ImageView charaView = null;
        private Image[] charaImages = null;
        private int index = 0;

        private long duration = 500 * 1000000L; // 500[ms]
        private long startTime = 0;

        private long count = 0L;
        private long preCount;
        private boolean isPlus = true;

        public ImageAnimation(ImageView charaView, Image[] images) {
            this.charaView = charaView;
            this.charaImages = images;
            this.index = 0;
        }

        @Override
        public void handle(long now) {
            if (startTime == 0) {
                startTime = now;
            }

            preCount = count;
            count = (now - startTime) / duration;
            if (preCount != count) {
                if (isPlus) {
                    index++;
                } else {
                    index--;
                }
                if (index < 0 || 2 < index) {
                    index = 1;
                    isPlus = !isPlus; // true == !false, false == !true
                }
                charaView.setImage(charaImages[index]);
            }
        }
    }

    private static void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // 要素を入れ替える
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
