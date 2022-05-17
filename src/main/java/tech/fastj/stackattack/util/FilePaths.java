package tech.fastj.stackattack.util;

import tech.fastj.engine.FastJEngine;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collections;
import java.util.Objects;

public class FilePaths {

    public static final Path IntroAnimation = pathResource("/img/stackattack_intro_small.png", "jar");
    public static final Path MainMenuMusic = pathResource("/audio/Stack_Attack_Is_Back.wav", "jar");
    public static final Path GameMusic = pathResource("/audio/Snapalong.wav", "jar");
    public static final Path IntroReadySFX = pathResource("/audio/Intro_Ready.wav", "jar");
    public static final Path BeginGameSFX = pathResource("/audio/Begin_Game.wav", "jar");
    public static final Path BlockSnapSFX = pathResource("/audio/Block_Snap.wav", "jar");
    public static final Path PerfectBlockSnapSFX = pathResource("/audio/Block_Snap_Perfect.wav", "jar");
    public static final Path LoseSFX = pathResource("/audio/Lose_Game.wav", "jar");

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

    public static Path pathResource(String resourcePath, String expectedScheme) {
        try {
            URI resource = Objects.requireNonNull(FilePaths.class.getResource(resourcePath), "Couldn't find resource " + resourcePath).toURI();
            checkFileSystem(resource, expectedScheme);
            return Paths.get(resource);
        } catch (Exception exception) {
            FastJEngine.forceCloseGame();
            throw new IllegalStateException(exception);
        }
    }

    private static void checkFileSystem(URI resource, String expectedScheme) throws IOException {
        if (!expectedScheme.equalsIgnoreCase(resource.getScheme())) {
            return;
        }

        for (FileSystemProvider provider : FileSystemProvider.installedProviders()) {
            if (!provider.getScheme().equalsIgnoreCase(expectedScheme)) {
                continue;
            }

            try {
                provider.getFileSystem(resource);
            } catch (FileSystemNotFoundException e) {
                // the file system doesn't exist yet...
                // in this case we need to initialize it first:
                provider.newFileSystem(resource, Collections.emptyMap());
            }
        }
    }
}
