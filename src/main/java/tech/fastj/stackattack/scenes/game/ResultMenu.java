package tech.fastj.stackattack.scenes.game;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Point;
import tech.fastj.math.Pointf;
import tech.fastj.math.Transform2D;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.game.RenderStyle;
import tech.fastj.graphics.game.Text2D;
import tech.fastj.graphics.ui.UIElement;
import tech.fastj.graphics.ui.elements.Button;
import tech.fastj.graphics.util.DrawUtil;

import tech.fastj.input.mouse.events.MouseActionEvent;
import tech.fastj.systems.control.Scene;
import tech.fastj.systems.control.SimpleManager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import tech.fastj.stackattack.ui.ContentBox;
import tech.fastj.stackattack.user.User;
import tech.fastj.stackattack.util.Fonts;
import tech.fastj.stackattack.util.Shapes;

public class ResultMenu extends UIElement<MouseActionEvent> {

    private Polygon2D backgroundScreen;
    private Text2D gameEndText;
    private ContentBox scoreBox;
    private ContentBox blocksStackedBox;
    private Button playAgainButton;
    private Button mainMenuButton;

    public ResultMenu(MainGame origin, User user) {
        super(origin);

        Pointf center = FastJEngine.getCanvas().getCanvasCenter();
        Point end = FastJEngine.getCanvas().getResolution().copy();
        Pointf[] backgroundMesh = DrawUtil.createBox(50f, 50f, end.subtract(120, 140).asPointf());

        setCollisionPath(DrawUtil.createPath(backgroundMesh));

        backgroundScreen = Polygon2D.create(backgroundMesh)
                .withFill(new Color(Color.lightGray.getRed(), Color.lightGray.getGreen(), Color.lightGray.getBlue(), 100))
                .withOutline(Shapes.ThickerRoundedStroke, Color.black)
                .withRenderStyle(RenderStyle.FillAndOutline)
                .build();

        gameEndText = Text2D.create("Game Ended.")
                .withFont(Fonts.TitleTextFont)
                .withTransform(Pointf.subtract(center, 160f), Transform2D.DefaultRotation, Transform2D.DefaultScale)
                .build();

        scoreBox = new ContentBox(
                origin,
                "Final Score",
                user.getHasHighScore() ? user.getScore() + " (New Record!)" : user.getScore() + ""
        );
        scoreBox.getStatDisplay().setFont(Fonts.StatTextFont);
        scoreBox.translate(Pointf.subtract(center, 100f, 90f));

        blocksStackedBox = new ContentBox(
                origin,
                "Blocks Stacked",
                user.getHasHighBlocksStacked() ? user.getNumberStacked() + " (New Record!)" : user.getNumberStacked() + ""
        );
        blocksStackedBox.getStatDisplay().setFont(Fonts.StatTextFont);
        blocksStackedBox.translate(Pointf.subtract(center, 100f, 65f));

        playAgainButton = new Button(origin, backgroundScreen.getCenter().add(-100f, 100f), Shapes.ButtonSize);
        playAgainButton.setText("Play Again");
        playAgainButton.setFill(Color.white);
        playAgainButton.setFont(Fonts.ButtonTextFont);
        playAgainButton.setOnAction(mouseButtonEvent -> {
            mouseButtonEvent.consume();
            FastJEngine.runAfterRender(() -> origin.changeState(GameState.Intro));
        });

        mainMenuButton = new Button(origin, backgroundScreen.getCenter().subtract(100f, 0f), Shapes.ButtonSize);
        mainMenuButton.setText("Quit Game");
        mainMenuButton.setFill(Color.white);
        mainMenuButton.setFont(Fonts.ButtonTextFont);
        mainMenuButton.setOnAction(mouseButtonEvent -> {
            mouseButtonEvent.consume();
            FastJEngine.runAfterRender(FastJEngine.getDisplay()::close);
        });

        origin.drawableManager.removeUIElement(playAgainButton);
        origin.drawableManager.removeUIElement(mainMenuButton);
        origin.drawableManager.removeUIElement(scoreBox);
        origin.drawableManager.removeUIElement(blocksStackedBox);
    }

    @Override
    public void render(Graphics2D g) {
        AffineTransform oldTransform = (AffineTransform) g.getTransform().clone();
        g.transform(getTransformation());

        backgroundScreen.render(g);
        gameEndText.render(g);
        scoreBox.render(g);
        blocksStackedBox.render(g);
        playAgainButton.render(g);
        mainMenuButton.render(g);

        g.setTransform(oldTransform);
    }

    @Override
    public void destroy(Scene origin) {
        super.destroyTheRest(origin);
        if (backgroundScreen != null) {
            backgroundScreen.destroy(origin);
            backgroundScreen = null;
        }

        if (gameEndText != null) {
            gameEndText.destroy(origin);
            gameEndText = null;
        }

        if (scoreBox != null) {
            scoreBox.destroy(origin);
            scoreBox = null;
        }

        if (blocksStackedBox != null) {
            blocksStackedBox.destroy(origin);
            blocksStackedBox = null;
        }

        if (playAgainButton != null) {
            playAgainButton.destroy(origin);
            playAgainButton = null;
        }

        if (mainMenuButton != null) {
            mainMenuButton.destroy(origin);
            mainMenuButton = null;
        }
    }

    @Override
    public void destroy(SimpleManager origin) {
        super.destroyTheRest(origin);
        if (backgroundScreen != null) {
            backgroundScreen.destroy(origin);
            backgroundScreen = null;
        }

        if (gameEndText != null) {
            gameEndText.destroy(origin);
            gameEndText = null;
        }

        if (scoreBox != null) {
            scoreBox.destroy(origin);
            scoreBox = null;
        }

        if (blocksStackedBox != null) {
            blocksStackedBox.destroy(origin);
            blocksStackedBox = null;
        }

        if (playAgainButton != null) {
            playAgainButton.destroy(origin);
            playAgainButton = null;
        }

        if (mainMenuButton != null) {
            mainMenuButton.destroy(origin);
            mainMenuButton = null;
        }
    }
}
