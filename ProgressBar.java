import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class ProgressBar extends Actor
{
    private int width;
    private int height;

    private Color backgroundColor;
    private Color foregroundColor;

    private double progress;
    
    public ProgressBar(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.backgroundColor = Color.BLACK;
        this.foregroundColor = Color.GREEN;
        this.progress = 0.35;
        updateImage();
    }
    
    public void changeProgress(double value)
    {
        this.progress += value;
        updateImage();
    }

    
    public double getProgress() {
        return progress;
    }
    
    private void updateImage()
    {
        GreenfootImage image = new GreenfootImage(width, height);

        image.setColor(backgroundColor);
        image.fillRect(0, 0, width, height);

        int filledWidth = (int) (width * progress);

        image.setColor(foregroundColor);
        image.fillRect(0, 0, filledWidth, height);

        setImage(image);
    }
}
