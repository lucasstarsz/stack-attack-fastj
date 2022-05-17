package tech.fastj.stackattack.util;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;

public class FilePaths {

    public static final Path IntroAnimation = Path.of("img/stackattack_intro_small.png");
    public static final Path MainMenuMusic = Path.of("audio/Stack_Attack_Is_Back.wav");
    public static final Path GameMusic = Path.of("audio/Snapalong.wav");
    public static final Path IntroReadySFX = Path.of("audio/Intro_Ready.wav");
    public static final Path BeginGameSFX = Path.of("audio/Begin_Game.wav");
    public static final Path BlockSnapSFX = Path.of("audio/Block_Snap.wav");
    public static final Path PerfectBlockSnapSFX = Path.of("audio/Block_Snap_Perfect.wav");
    public static final Path FasterBlocksSFX = Path.of("audio/Increase_Block_Speed.wav");
    public static final Path LoseSFX = Path.of("audio/Lose_Game.wav");

    public static final InputStream NotoSansRegular = streamResource("/notosans/NotoSans-Regular.ttf");
    public static final InputStream NotoSansBold = streamResource("/notosans/NotoSans-Bold.ttf");
    public static final InputStream NotoSansBoldItalic = streamResource("/notosans/NotoSans-BoldItalic.ttf");
    public static final InputStream NotoSansItalic = streamResource("/notosans/NotoSans-Italic.ttf");
    public static final InputStream NotoSansMono = streamResource("/notosansmono/NotoSansMono-VariableFont_wdth,wght.ttf");

    public static InputStream streamResource(String resourcePath) {
        return Objects.requireNonNull(
                FilePaths.class.getResourceAsStream(resourcePath),
                "Couldn't find resource " + resourcePath
        );
    }
}
