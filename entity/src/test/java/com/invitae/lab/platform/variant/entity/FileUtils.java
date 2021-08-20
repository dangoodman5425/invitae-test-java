package com.invitae.lab.platform.variant.entity;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

final class FileUtils {

    private FileUtils() {

    }

    /**
     * Given a filepath retrieves the content as a strng
     *
     * @param filePath file path for resource
     * @return String repr of the file resource
     * @throws IOException if resource not found
     */
    public static String getFileAsString(final String filePath) throws IOException {
        return Resources.toString(Resources.getResource(filePath),
                StandardCharsets.UTF_8);
    }
}
