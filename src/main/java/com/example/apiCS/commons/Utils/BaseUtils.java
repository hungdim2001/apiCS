package com.example.apiCS.commons.Utils;

import com.example.apiCS.commons.Constant.FilenameExtension;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public final class BaseUtils {
    public static boolean CheckFilenameExtension(String filenameExtension) {
        if (!FilenameExtension.FILENAME_EXTENSION.contains(filenameExtension)) {
            return false;
        }
        return true;
    }
    public static String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
