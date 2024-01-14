import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapData {
    public static final int TYPE_SPACE = 0;
    public static final int TYPE_WALL = 1;
    public static final int TYPE_OTHER_FLAG=2;
    public static final int TYPE_TIME = 3;
    private static final String mapImageFiles[] = {
            "png/SPACE.png",
            "png/WALL.png",
            "png/other_flag_01.png",
            "png/time.png"
    };

    private Image[] mapImages;
    private ImageView[][] mapImageViews;
    private int[][] maps;
    private int width; // width of the map
    private int height; // height of the map
    private int times;

    MapData(int x, int y,int times) {
        mapImages=new Image[4];
        mapImageViews = new ImageView[y][x];
        for (int i = 0; i < 4; i ++) {
            mapImages[i] = new Image(mapImageFiles[i]);
        }

        width = x;
        height = y;
        maps = new int[y][x];

        fillMap(MapData.TYPE_WALL);
        digMap(1, 3);
        setMap(19,13,MapData.TYPE_OTHER_FLAG);
        setMultipleItems(MapData.TYPE_TIME,times);
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
