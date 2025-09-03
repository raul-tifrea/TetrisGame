package cube;

import java.awt.*;

public class Obj_Bar extends Object{
    public Obj_Bar(){
        create(Color.GREEN);
    }

    public void setXY(int x, int y){
        //* * * *
        b[0].x=x;
        b[0].y=y;
        b[1].x=b[0].x-Block.SIZE;
        b[1].y=b[0].y;
        b[2].x=b[0].x + Block.SIZE;
        b[2].y=b[0].y;
        b[3].x=b[0].x + Block.SIZE*2;
        b[3].y=b[0].y;
    }

    public void getDirection1(){
        auxb[0].x=b[0].x;
        auxb[0].y=b[0].y;
        auxb[1].x=b[0].x-Block.SIZE;
        auxb[1].y=b[0].y;
        auxb[2].x=b[0].x+Block.SIZE;
        auxb[2].y=b[0].y;
        auxb[3].x=b[0].x+Block.SIZE*2;
        auxb[3].y=b[0].y;
        updateXY(1);
    }

    public void getDirection2(){
        //*
        //*
        //*
        //*
        auxb[0].x=b[0].x;
        auxb[0].y=b[0].y;
        auxb[1].x=b[0].x;
        auxb[1].y=b[0].y-Block.SIZE;
        auxb[2].x=b[0].x;
        auxb[2].y=b[0].y+Block.SIZE;
        auxb[3].x=b[0].x;
        auxb[3].y=b[0].y+Block.SIZE*2;
        updateXY(2);
    }

    public void getDirection3(){
        //* * * *
        auxb[0].x=b[0].x;
        auxb[0].y=b[0].y;
        auxb[1].x=b[0].x+Block.SIZE;
        auxb[1].y=b[0].y;
        auxb[2].x=b[0].x-Block.SIZE;
        auxb[2].y=b[0].y;
        auxb[3].x=b[0].x-Block.SIZE*2;
        auxb[3].y=b[0].y;
        updateXY(3);

    }

    public void getDirection4(){
        //*
        //*
        //*
        //*
        auxb[0].x=b[0].x;
        auxb[0].y=b[0].y;
        auxb[1].x=b[0].x;
        auxb[1].y=b[0].y+Block.SIZE;
        auxb[2].x=b[0].x;
        auxb[2].y=b[0].y-Block.SIZE;
        auxb[3].x=b[0].x;
        auxb[3].y=b[0].y-Block.SIZE*2;
        updateXY(4);
    }



}
