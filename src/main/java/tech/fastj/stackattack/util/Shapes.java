package tech.fastj.stackattack.util;

import tech.fastj.engine.FastJEngine;
import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.math.Transform2D;
import tech.fastj.graphics.display.FastJCanvas;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.game.RenderStyle;
import tech.fastj.graphics.gradients.Gradients;
import tech.fastj.graphics.util.DrawUtil;

import java.awt.BasicStroke;
import java.awt.Color;

public class Shapes {

    private static Pointf[] getBlockMesh(Pointf size) {
        return DrawUtil.createBox(Pointf.origin(), size);
    }

    public static final BasicStroke ThickStroke = new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    public static final BasicStroke ThickerRoundedStroke = new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    public static final BasicStroke ThickEdgedStroke = new BasicStroke(4, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);

    public static final Pointf ButtonSize = new Pointf(200f, 50f);
    private static final int GroundHeight = 200;

    public static Polygon2D generateBlock(Pointf size) {
        FastJCanvas canvas = FastJEngine.getCanvas();
        boolean gradientFill = Maths.randomBoolean();
        Pointf[] blockMesh = getBlockMesh(size);
        return Polygon2D.create(blockMesh)
                .withRenderStyle(RenderStyle.FillAndOutline)
                .withFill(gradientFill ? DrawUtil.randomColor() : Gradients.randomLinearGradient(blockMesh[0], blockMesh[2]))
                .withOutline(ThickStroke, Color.black)
                .withTransform(new Pointf(0f, canvas.getCanvasCenter().y + 66f), Transform2D.DefaultRotation, Transform2D.DefaultScale)
                .build();
    }

    public static Polygon2D generateGround(float width) {
        FastJCanvas canvas = FastJEngine.getCanvas();
        Pointf[] mesh = DrawUtil.createBox(
                Pointf.origin(),
                new Pointf(width, GroundHeight)
        );

        return Polygon2D.create(mesh)
                .withRenderStyle(RenderStyle.FillAndOutline)
                .withOutline(ThickStroke, Color.green.darker().darker())
                .withFill(Color.green.darker())
                .withTransform(canvas.getCanvasCenter().add(-width / 2f, 100f), Transform2D.DefaultRotation, Transform2D.DefaultScale)
                .build();
    }

    public static void hardenOutline(Polygon2D block) {
        block.setOutline(ThickEdgedStroke, Color.black);
    }

    public static void goldenOutline(Polygon2D block) {
        block.setOutline(ThickEdgedStroke, Colors.PerfectGold);
    }
}
