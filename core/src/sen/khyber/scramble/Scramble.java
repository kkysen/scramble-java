package sen.khyber.scramble;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import lombok.experimental.ExtensionMethod;

/**
 * 
 * 
 * @author Khyber Sen
 */
@ExtensionMethod(Assets.class)
public class Scramble extends Game {
    
    private Batch batch;
    
    private StageScreen<OptionsStage> optionsScreen;
    private StageScreen<ScrambleStage> scrambleScreen;
    
    private OptionsStage options;
    private ScrambleStage scramble;
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        optionsScreen = new OptionsScreen(batch);
        scrambleScreen = new ScrambleScreen(batch);
        options = optionsScreen.getStage();
        scramble = scrambleScreen.getStage();
        setScreen(optionsScreen);
    }
    
    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
        if (getScreen() == optionsScreen && options.isFinished()) {
            scramble.createBoard(options.getBoardSize());
            setScreen(scrambleScreen);
        } else if (getScreen() == scrambleScreen && scramble.isFinished()) {
            setScreen(optionsScreen);
        }
    }
    
    @Override
    public void dispose() {
        super.dispose();
        optionsScreen.dispose();
        scrambleScreen.dispose();
        Disposables.dispose();
    }
    
}
