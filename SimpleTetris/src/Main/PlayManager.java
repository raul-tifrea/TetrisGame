package main;

import tetrisobj.*;
import tetrisobj.Object;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.util.ArrayList;
import java.util.Random;

public class PlayManager {
//block size 30px
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    Object currobj;
    final int OBJ_START_X;
    final int OBJ_START_Y;
    Object nextobj;
    final int NEXT_START_X;
    final int NEXT_START_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();
    public static int dropInterval = 60;
    boolean effectCntOn;
    int effectCnt;
    boolean gameOver;
    int level = 1;
    int lines;
    int score;

    ArrayList<Integer> effectList = new ArrayList<>();

    public PlayManager() {
        left_x = (GamePanel.WIDTH/2)-(WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        OBJ_START_X = left_x+(WIDTH/2)- Block.SIZE;
        OBJ_START_Y = top_y + Block.SIZE;

        NEXT_START_X = right_x + 184;
        NEXT_START_Y = top_y + 495;

        currobj = pickObj();
        currobj.setXY(OBJ_START_X, OBJ_START_Y);

        nextobj = pickObj();
        nextobj.setXY(NEXT_START_X, NEXT_START_Y);
    }

    public Object pickObj(){
        Object obj = null;
        int i = new Random().nextInt(7);

        switch(i){
            case 0 : obj = new Obj_L1(); break;
            case 1 : obj = new Obj_L2(); break;
            case 2 : obj = new Obj_Bar(); break;
            case 3 : obj = new Obj_T(); break;
            case 4 : obj = new Obj_Z1(); break;
            case 5 : obj = new Obj_Z2(); break;
            case 6 : obj = new Obj_Square(); break;
        }
        return obj;
    }

    public void update(){
        if(currobj.active == false){
            for(int i = 0; i < 4; i++){
                staticBlocks.add(currobj.b[i]);
            }
            currobj.deactivated = false;

            if(currobj.b[0].y == OBJ_START_Y){
                gameOver = true;
            }

            currobj = nextobj;
            currobj.setXY(OBJ_START_X, OBJ_START_Y);
            nextobj = pickObj();
            nextobj.setXY(NEXT_START_X, NEXT_START_Y);
            checkDelete();
        }else{
            currobj.update();
        }

    }

    private void checkDelete(){
        int x = left_x;
        int y = top_y;
        int blockCnt = 0;
        int lineCnt = 0;
        while(x < right_x && y < bottom_y){
            for(int i = 0; i<staticBlocks.size(); i++){
                if(staticBlocks.get(i).x == x && staticBlocks.get(i).y == y){// if there is a static block at this position
                    blockCnt++;
                }
            }
            x += Block.SIZE;// go right
            if(x == right_x){//reset go to the next row
                if(blockCnt == 12){//delete the row
                    effectCntOn = true;
                    effectList.add(y);//add coord y
                    for(int i = staticBlocks.size()-1; i >= 0; i--){
                        if(staticBlocks.get(i).y == y){
                            staticBlocks.remove(i);
                        }
                    }
                    lineCnt++;
                    lines++;
                    if(lines % 10 == 0 && dropInterval > 1){
                        level++;
                        if(dropInterval > 10){
                            dropInterval -= 10;
                        }else{
                            dropInterval -= 1;
                        }
                    }
                    //after delete, slide down the other blocks
                    for(int i = 0; i < staticBlocks.size(); i++){
                        if(staticBlocks.get(i).y < y){
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }
                blockCnt = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }
        if(lineCnt > 0){
            int singleLineScore = 10 * level;
            score += singleLineScore * lineCnt;
        }
    }

    public void draw(Graphics2D g){

        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(10f));
        g.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8);
        g.setStroke(new BasicStroke(4f));
        int x = right_x+100;
        int y = bottom_y-200;
        g.drawRect(x, y, 200, 200);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString("NEXT", x+60,y+60);

        g.drawRect(x,top_y,200,300);
        x += 30;
        y = top_y+70;
        g.drawString("LEVEL: " + level, x, y);
        y+=90;
        g.drawString("LINES: " + lines, x, y);
        y+=90;
        g.drawString("SCORE: " + score, x, y);

        if(currobj != null){
            currobj.draw(g);
        }

        nextobj.draw(g);
        for(int i = 0; i < staticBlocks.size(); i++){
            staticBlocks.get(i).draw(g);
        }

        g.setFont(g.getFont().deriveFont(Font.BOLD, 50f));
        if(gameOver == true){
            x = left_x + 25;
            y = top_y + 320;

            String text = "GAME OVER";

            FontRenderContext frc = g.getFontRenderContext();
            GlyphVector gv = g.getFont().createGlyphVector(frc, text);
            Shape textShape = gv.getOutline(x, y);


            g.setColor(Color.black);
            g.setStroke(new BasicStroke(5f));
            g.draw(textShape);


            g.setColor(Color.red);
            g.fill(textShape);

        }else if(KeyHandler.pausePressed){
            x = left_x + 80;
            y = top_y + 320;
            String text = "PAUSED";

            FontRenderContext frc = g.getFontRenderContext();
            GlyphVector gv = g.getFont().createGlyphVector(frc, text);
            Shape textShape = gv.getOutline(x, y);


            g.setColor(Color.black);
            g.setStroke(new BasicStroke(5f));
            g.draw(textShape);


            g.setColor(Color.GREEN);
            g.fill(textShape);
        }

        if(effectCntOn == true){
            effectCnt++;

            g.setColor(Color.red);
            for(int i = 0; i < effectList.size(); i++){
                g.fillRect(left_x, effectList.get(i), WIDTH, Block.SIZE);
            }

            if(effectCnt == 15){
                effectCntOn = false;
                effectList.clear();
                effectCnt = 0;
            }
        }

        x = 100;
        y = top_y + 320;
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 60));
        g.drawString("TETRIS", x, y);

    }

}
