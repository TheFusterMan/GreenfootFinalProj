import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Actor
{
    public Player() {
        //setImage("player.png");
    }
    
    public void act()
    {
        GameWorld world = (GameWorld) getWorld();
        
        if (Greenfoot.isKeyDown("space") && world.getGameState() == GameWorld.GameState.IDLE) {
            world.spawnBobber();
            world.setGameState(GameWorld.GameState.WAITING_FOR_BITE);
        }
    }
}
