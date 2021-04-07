import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * @author Zane Barker
 */
public class Wall {

    private final Stack<String> wall;

    public Wall(List<String> tileset){
        Objects.requireNonNull(tileset);
        wall = new Stack<>();
        initWall(tileset);
    }

    public String toString(){
        return wall.toString();
    }

    public String drawTile(){
        return wall.pop();
    }

    private void initWall(List<String> tileset){
        for(int i=0; i<4; i++){
            wall.addAll(tileset);
        }
        shuffle();
    }

    private void shuffle(){
        for(int i = 0; i < wall.size(); i++) {
            int newIndex = (int)(Math.random()*wall.size());
            String temp = wall.elementAt(i);
            wall.setElementAt(wall.elementAt(newIndex),i);
            wall.setElementAt(temp,newIndex);
        }
    }
}