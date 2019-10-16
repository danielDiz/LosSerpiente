package com.tetris.model;

import android.os.SystemClock;

import com.tetris.model.impl.ShapeCube;
import com.tetris.model.impl.ShapeI;
import com.tetris.model.impl.ShapeL;
import com.tetris.model.impl.ShapeLInverted;
import com.tetris.model.impl.ShapeT;
import com.tetris.model.impl.ShapeZ;
import com.tetris.model.impl.ShapeZInverted;

public class Shape extends Pixel {

    protected Block[] blocks;

    protected boolean falling; //True if the shape is falling

    protected long last_fall_update; //Last time the piece felt

    protected Block rotation_block; //Block which 'rotates'
    protected int rotation_cycle; //Number of cycles the shape has
    protected int rotation; //Rotation cycle we are in

    //Constructors
    //Shapeless shape


    protected Shape(int width, int height,int spawnY) {
        super(4, spawnY, width, height);

        blocks = new Block[4];
        blocks[0] = new Block();
        blocks[1] = new Block();
        blocks[2] = new Block();
        blocks[3] = new Block();

        falling = true;

        //Rotation initiation
        rotation_block = blocks[1];
        rotation_cycle = 1;
        rotation = 0;

    }

    protected Shape(Shape s) {
        this.blocks = s.getBlocks();
        this.falling = s.isFalling();
        this.last_fall_update = s.getLast_fall_update();
    }

    //Shape defined by type
    public static Shape randomShape(int type,int spawnY) {
        switch (type) {
            case 1:
                return new ShapeCube(spawnY);
            case 2:
                return new ShapeI(spawnY);
            case 3:
                return new ShapeL(spawnY);
            case 4:
                return new ShapeLInverted(spawnY);
            case 5:
                return new ShapeZ(spawnY);
            case 6:
                return new ShapeZInverted(spawnY);
            case 7:
            default:
                return new ShapeT(spawnY);
        }
    }

    //Update falling
    public void update() {
        if (falling) {
            if (needsFallUpdate()) { //Check if it needs to fall more
                moveDown();
            }
            if (collide()) {    //Check if it collided with something
                moveUp();
                falling = false;    //Set shape fall to false
            }
        }
    }


    //SHAPE INTERACTIONS
    //Checks if our shape collide with anything
    public boolean collide() {
        for (Block block : getBlocks()) {
            if (block.collide()) {
                return true;
            }
        }
        return false;
    }

    //Checks if enough time has passed for the shape to update its position
    public boolean needsFallUpdate() {
        long updateInterval = 230;

        if (SystemClock.uptimeMillis() - last_fall_update > updateInterval) {
            last_fall_update = SystemClock.uptimeMillis();
            return true;
        }

        return false;
    }




    //MOVEMENT
    //Move whole shape 1 down
    public void moveDown() {
        for (Block block : blocks) {
            block.moveDown();
        }
        moveBy(0, 1);
    }

    //Move whole shape 1 up
    public void moveUp() {
        for (Block block : blocks) {
            block.moveUp();
        }
        moveBy(0, -1);
    }

    //Move whole shape 1 left
    public void moveLeft() {
        for (Block block : blocks) {
            block.moveLeft();
        }
        moveBy(-1, 0);
    }

    //Move whole shape 1 right
    public void moveRight() {
        for (Block block : blocks) {
            block.moveRight();
        }
        moveBy(1, 0);
    }

    //Applies rotation to the shape
    public void doRotation() {
        int old_x, old_y;
        if (rotation_block != null) {
            for (int i = 1; i <= (rotation % rotation_cycle); ++i) {
                for (Block block : blocks) {
                    old_x = block.getX();
                    old_y = block.getY();
                    block.setX(rotation_block.getX() + (rotation_block.getY() - old_y));
                    block.setY(rotation_block.getY() - (rotation_block.getX() - old_x));
                }
            }
        }
    }

    public void rotate() {
        rotation += 1;
    }

    public void unrotate() {
        rotation -= 1;
    }

    public Block[] getBlocks() {
        return blocks;
    }

    public boolean isFalling() {
        return falling;
    }

    public long getLast_fall_update() {
        return last_fall_update;
    }

    public int getRotation() {
        return rotation;
    }

}
