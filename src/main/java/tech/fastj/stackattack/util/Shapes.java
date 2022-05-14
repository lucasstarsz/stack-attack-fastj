package tech.fastj.stackattack.util;

import tech.fastj.math.Maths;
import tech.fastj.math.Pointf;
import tech.fastj.graphics.game.Polygon2D;
import tech.fastj.graphics.game.RenderStyle;
import tech.fastj.graphics.gradients.Gradients;
import tech.fastj.graphics.util.DrawUtil;

public class Shapes {

    private static final Pointf[] BlockMesh = {
            new Pointf(),
            new Pointf(100f, 0f),
            new Pointf(100f, 30f),
            new Pointf(0f, 30f)
    };

    public static Polygon2D generateBlock() {
        boolean gradientFill = Maths.randomBoolean();
        return Polygon2D.create(BlockMesh)
                .withRenderStyle(RenderStyle.FillAndOutline)
                .withFill(gradientFill ? DrawUtil.randomColor() : Gradients.randomLinearGradient(BlockMesh[0], BlockMesh[2]))
                .build();
    }
}
