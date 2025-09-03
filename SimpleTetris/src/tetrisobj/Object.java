package tetrisobj;

import main.KeyHandler;
import main.PlayManager;

import java.awt.*;

public class Object {
    public Block[] b = new Block[4];
    public Block[] auxb = new Block[4];
    int autoDropCNT = 0;
    public int direction = 1;
    boolean leftCollision, rightCollision, bottomCollision,topCollision;
    public boolean active = true;
    int deactivate = 0;
    public boolean deactivated;


    public void create(Color c){
        for(int i = 0; i < 4; i++){
            b[i] = new Block(c);
        }
        for(int i = 0; i < 4; i++){
            auxb[i] = new Block(c);
        }

    }

    public void setXY(int x, int y){}

    public void updateXY(int direction){
        checkRotationCollision();
        if(leftCollision == false && rightCollision == false && bottomCollision == false && topCollision == false){
            this.direction = direction;
            for(int i = 0; i < 4; i++){
                b[i].x = auxb[i].x;
            }
            for(int i = 0; i < 4; i++){
                b[i].y = auxb[i].y;
            }
        }

    }

    public void getDirection1(){

    }
    public void getDirection2(){

    }
    public void getDirection3(){

    }
    public void getDirection4(){

    }

    public void checkMovementCollision(){
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticCollision();

        for(int i = 0; i < b.length; i++){
            if(b[i].x == PlayManager.left_x){
                leftCollision = true;
            }
        }

        for(int i = 0; i < b.length; i++){
            if(b[i].x + Block.SIZE == PlayManager.right_x){
                rightCollision = true;
            }
        }

        for(int i = 0; i < b.length; i++){
            if(b[i].y + Block.SIZE == PlayManager.bottom_y){
                bottomCollision = true;
            }
        }
    }

    public void checkRotationCollision(){
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;
        topCollision = false;

        checkStaticCollision();

        for(int i = 0; i < b.length; i++){
            if(auxb[i].y < PlayManager.top_y){
                topCollision = true;
            }
        }

        for(int i = 0; i < b.length; i++){
            if(auxb[i].x < PlayManager.left_x){
                leftCollision = true;
            }
        }

        for(int i = 0; i < b.length; i++){
            if(auxb[i].x + Block.SIZE > PlayManager.right_x){
                rightCollision = true;
            }
        }

        for(int i = 0; i < b.length; i++){
            if(auxb[i].y + Block.SIZE > PlayManager.bottom_y){
                bottomCollision = true;
            }
        }
    }

    private void checkStaticCollision(){
        for(int i = 0; i < PlayManager.staticBlocks.size(); i++){
            int targetX = PlayManager.staticBlocks.get(i).x;
            int targetY = PlayManager.staticBlocks.get(i).y;

            for(int j = 0; j < b.length; j++){
                if(b[j].x == targetX && b[j].y + Block.SIZE == targetY){
                    bottomCollision = true;
                }
            }

            for(int j = 0; j < b.length; j++){
                if(b[j].x - Block.SIZE == targetX && b[j].y == targetY){
                    leftCollision = true;
                }
            }

            for(int j = 0; j < b.length; j++){
                if(b[j].x + Block.SIZE == targetX && b[j].y == targetY){
                    rightCollision = true;
                }
            }
        }
    }

    public void deactivating(){//how much time after hitting you can move the object
        deactivate++;
        if(deactivate == 55){
            deactivate = 0;
            checkMovementCollision();

            if(bottomCollision){
                active = false;
            }

        }
    }

    public void update(){
        if(deactivated){
            deactivating();
        }


        if(bottomCollision){
            deactivated = true;
        }else {
            autoDropCNT++;
            if(autoDropCNT == PlayManager.dropInterval){
                for(int i = 0; i < 4; i++){
                    b[i].y += Block.SIZE;
                }
                autoDropCNT = 0;
            }

        }

        if(KeyHandler.upPressed){
            switch (direction){
                case 1 : getDirection2();break;
                case 2 : getDirection3();break;
                case 3 : getDirection4();break;
                case 4 : getDirection1();break;
            }
            KeyHandler.upPressed = false;
        }

        checkMovementCollision();

        if(KeyHandler.downPressed){
            if(bottomCollision ==  false){
                for(int i = 0; i < 4; i++){
                    b[i].y += Block.SIZE;
                }
                autoDropCNT = 0;
            }

            KeyHandler.downPressed = false;
        }
        if(KeyHandler.leftPressed){
            if(leftCollision == false){
                for(int i = 0; i < 4; i++){
                    b[i].x -= Block.SIZE;
                }
                autoDropCNT = 0;
            }
            KeyHandler.leftPressed = false;
        }
        if(KeyHandler.rightPressed){
            if(rightCollision == false){
                for(int i = 0; i < 4; i++){
                    b[i].x += Block.SIZE;
                }
                autoDropCNT = 0;

            }
            KeyHandler.rightPressed = false;
        }


    }

    public void draw(Graphics2D g){
        int margin = 2;
        g.setColor(b[0].c);
        g.fillRect(b[0].x+margin, b[0].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
        g.fillRect(b[1].x+margin, b[1].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
        g.fillRect(b[2].x+margin, b[2].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
        g.fillRect(b[3].x+margin, b[3].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
    }
}
