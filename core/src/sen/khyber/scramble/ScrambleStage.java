package sen.khyber.scramble;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class ScrambleStage extends FitStage {
    
    private Board board;
    private int boardSize;
    
    private final TimerBar timer;
    
    private static float center(final float parentSize, final float childSize) {
        return (parentSize - childSize) * 0.5f;
    }
    
    public ScrambleStage(final Batch batch, final StageScreen<? extends ScrambleStage> screen) {
        super(batch, screen);
        final Skin skin = Skins.get();
        timer = new TimerBar(0, false, skin.get("timerStyle", ProgressBarStyle.class));
        addActor(timer);
        final float percent = 0.02f;
        final float oppPercent = 1 - 2 * percent;
        timer.setBounds(getWidth() * percent, getHeight() * oppPercent, getWidth() * oppPercent,
                getHeight() * percent);
        System.out.println(toString(timer));
    }
    
    public void createBoard(final int boardSize) {
        if (board != null) {
            board.remove();
        }
        board = new Board(boardSize, Lexicon.english(), Letters.latin());
        
        final float width = getWidth();
        final float height = getHeight();
        final float boardWidth = board.getWidth();
        final float boardHeight = board.getHeight();
        
        final float marginPercent = 0.9f;
        final float scaleX = width * marginPercent / boardWidth;
        final float scaleY = height * marginPercent / boardHeight;
        final float scale = Math.min(scaleX, scaleY);
        board.setScale(scale);
        
        board.setPosition(center(width, boardWidth * scale),
                center(height, boardHeight * scale));
        
        addActor(board);
        
        System.out.println(board);
        timer.setDuration(60);
        timer.restart();
        System.out.println(timer);
    }
    
    private void createBoard() {
        createBoard(boardSize);
    }
    
    @Override
    public void act(final float delta) {
        if (board == null) {
            createBoard(5);
        }
        super.act(delta);
        if (board.isSolved()) {
            createBoard();
        }
    }
    
    private boolean gameOver() {
        return timer.isDone() || board.isSolved();
    }
    
    private boolean exited() {
        return true;// TODO
    }
    
    @Override
    public boolean isFinished() {
        return gameOver() && exited();
    }
    
}
