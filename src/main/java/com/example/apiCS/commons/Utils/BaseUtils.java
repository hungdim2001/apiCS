package com.example.apiCS.commons.Utils;

import com.example.apiCS.commons.Constant.FilenameExtension;

public final class BaseUtils {
    public static boolean CheckFilenameExtension(String filenameExtension) {
        if (!FilenameExtension.FILENAME_EXTENSION.contains(filenameExtension)) {
            return false;
        }
        return true;
    }
}
