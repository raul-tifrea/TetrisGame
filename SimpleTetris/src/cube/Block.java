package cube;

import java.awt.*;

public class Block extends Rectangle {
    public int x,y;
    public static final int SIZE = 30;
    public Color c;

    public Block(Color c){
        this.c = c;
    }

    public void draw(Graphics2D g){
        int margin = 2;

        g.setColor(c);
        g.fillRect(x+margin,y+margin,SIZE-(margin*2),SIZE-(margin*2));
    }
}
