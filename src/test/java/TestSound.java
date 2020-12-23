import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.nogacz.snake.board.Sound;

public class TestSound {

    @Test
    public void testValidFormat() {
        boolean ex = false;
        try {
            Sound s = new Sound("sounds/Iceland Theme.mp3");
            s.play();
        } catch (Exception e) {
            ex = true;
        }
        Assert.assertTrue(ex);
    }

    @Test
    public void testFileNotFound() {
        boolean ex = false;
        try {
            Sound s = new Sound("sounds/a.wav");
            s.play();
        } catch (Exception e) {
            ex = true;
        }
        Assert.assertTrue(ex);
    }

    @Test
    public void testPlay() {
        Sound s = null;
        try {
            s = spy(new Sound("sounds/GameSound.wav"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        doNothing().when(s).play();
        s.play();
        verify(s, times(1)).play();
    }

    @Test
    public void testLoop() {
        try {
            Sound s = spy(new Sound("sounds/GameSound.wav"));
            doNothing().when(s).loop();
            s.loop();
            verify(s, times(1)).loop();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testStop() {
        try {
            Sound s = spy(new Sound("sounds/GameSound.wav"));
            s.stop();
            verify(s, times(1)).stop();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
