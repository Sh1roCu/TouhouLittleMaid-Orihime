package com.github.tartaricacid.touhoulittlemaid.util;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public final class GetJarResources {
    private GetJarResources() {
    }

    // Fabric: 26.1的实现方式与原版女仆的不一样，注意cherry-pick时不要修改（
    public static void copyFolder(String sourcePath, Path targetPath) throws IOException, URISyntaxException {
        URL url = TouhouLittleMaid.class.getResource(sourcePath);
        if (url == null) {
            return;
        }
        URI uri = url.toURI();
        Path sourceFolderPath = Paths.get(uri);
        try (Stream<Path> stream = Files.walk(sourceFolderPath, Integer.MAX_VALUE)) {
            stream.forEach(source -> {
                Path relativePath = sourceFolderPath.relativize(source);
                String relativePathString = relativePath.toString().replace('\\', '/');
                Path target = targetPath.resolve(relativePathString);
                try {
                    if (Files.isDirectory(source)) {
                        Files.createDirectories(target);
                    } else {
                        // 确保目标目录存在
                        Path parentDir = target.getParent();
                        if (parentDir != null && !Files.isDirectory(parentDir)) {
                            Files.createDirectories(parentDir);
                        }
                        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    TouhouLittleMaid.LOGGER.error("Failed to copy file from {} to target: {}", source, e.getMessage());
                } catch (Exception e) {
                    TouhouLittleMaid.LOGGER.error("Unexpected error during file copy: {}", e.getMessage());
                }
            });
        }
    }
}
