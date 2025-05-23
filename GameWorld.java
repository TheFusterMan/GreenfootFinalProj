import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class GameWorld extends World
{
    private Menu menu;
    
    public enum GameState {
        IDLE,
        WAITING_FOR_BITE,
        FISHING_MINIGAME,
        FISH_CAUGHT,
        FISH_ESCAPED
    }
    
    private GameState currentGameState = GameState.IDLE;
    private Bobber bobber;
    
    private ProgressBar catchingProgressBar;
    private Container minigameContainer;
    private Bobber minigameBobberIcon;
    private Fish minigameFishIcon;
    
    private int timeLeftInFrames = 0;
    private int timeLeftInSeconds;
    private final int FPS = 60;
    
    private int playerCoins = 0;
    private Icon coinIcon = new Icon("coin.png");
    
    public GameState getGameState() { return currentGameState; }
    public void setGameState(GameState newState) { currentGameState = newState; }
    
    public GameWorld(Menu menu)
    {    
        super(800, 600, 1); 
        this.menu = menu;
        
        addObject(new Button("menu_button.png", () -> {
            Greenfoot.setWorld(menu);
        }), 100, 30);
        addObject(new Player(), getWidth() / 2, 10);
    }
    
    public void act() {
        switch (currentGameState) {
            case IDLE:
                addObject(coinIcon, getWidth() - coinIcon.getImage().getWidth(), 20);
                showText("" + playerCoins, getWidth() - 2 * coinIcon.getImage().getWidth(), 20);
                
                break;
            case WAITING_FOR_BITE:
                spawnFish();
                break;
            case FISHING_MINIGAME:
                bobber.shake();
                
                if (minigameContainer == null) {
                    startMinigame();
                }
                
                if (minigameBobberIcon.checkIntersection(minigameFishIcon)) catchingProgressBar.changeProgress(0.005);
                else catchingProgressBar.changeProgress(-0.005);
                
                if (catchingProgressBar.getProgress() >= 1) setGameState(GameState.FISH_CAUGHT);
                else if (catchingProgressBar.getProgress() <= 0) setGameState(GameState.FISH_ESCAPED);
                break;
            case FISH_CAUGHT:
                //ТУТ НАДО ВСЕ УДАЛЯТЬ
                // Начислить монетки
                // Вернуться в IDLE
                
                removeObject(catchingProgressBar);
                removeObject(minigameFishIcon);
                removeObject(minigameBobberIcon);
                removeObject(minigameContainer);
                removeObject(bobber);
                
                minigameContainer = null;
                
                playerCoins += 10;
                
                setGameState(GameState.IDLE);
                break;
            case FISH_ESCAPED:
                //И ТУТ ТОЖЕ НАДО ВСЕ УДАЛЯТЬ
                // Показать сообщение, подождать, вернуться в IDLE
                removeObject(catchingProgressBar);
                removeObject(minigameFishIcon);
                removeObject(minigameBobberIcon);
                removeObject(minigameContainer);
                removeObject(bobber);
                
                minigameContainer = null;
                // handleEndMinigameState();
                setGameState(GameState.IDLE);
                break;
        }
    }
    
    public void startMinigame() {
        minigameContainer = new Container();
        addObject(minigameContainer, getWidth() / 2, getHeight() / 2);
        
        minigameFishIcon = new Fish(minigameContainer);
        minigameBobberIcon = new Bobber(minigameContainer);
        addObject(minigameFishIcon, getWidth() / 2, getHeight() / 2);
        addObject(minigameBobberIcon, getWidth() / 2, getHeight() / 2);
    
        catchingProgressBar = new ProgressBar(200, 10);
        addObject(catchingProgressBar, getWidth() / 2, getHeight() * 3 / 4);
    }
    
    public void spawnBobber() {
        bobber = new Bobber();
        addObject(bobber, 150, 450);
    }
    
    public void spawnFish() {
        if (timeLeftInFrames > 0) {
            timeLeftInFrames -= 1;
            if (timeLeftInFrames <= 0) {
                setGameState(GameState.FISHING_MINIGAME);
            }
        }
        else {
            timeLeftInSeconds = Greenfoot.getRandomNumber(5);
            timeLeftInFrames = timeLeftInSeconds * FPS;
        }
    }
}
