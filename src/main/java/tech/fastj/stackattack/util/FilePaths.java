package tech.fastj.stackattack.util;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;

public class FilePaths {

    public static final Path IntroAnimation = Path.of("src/main/resources/stackattack_intro_small.png");

    public static final InputStream NotoSansRegular = streamResourceFromFolder("/notosans/NotoSans-Regular.ttf");
    public static final InputStream NotoSansBold = streamResourceFromFolder("/notosans/NotoSans-Bold.ttf");
    public static final InputStream NotoSansBoldItalic = streamResourceFromFolder("/notosans/NotoSans-BoldItalic.ttf");
    public static final InputStream NotoSansItalic = streamResourceFromFolder("/notosans/NotoSans-Italic.ttf");
    public static final InputStream NotoSansMono = streamResourceFromFolder("/notosansmono/NotoSansMono-VariableFont_wdth,wght.ttf");
    public static InputStream streamResourceFromFolder(String resourcePath) {
        return Objects.requireNonNull(
                FilePaths.class.getResourceAsStream(resourcePath),
                "Couldn't find resource " + resourcePath
        );
    }
}
