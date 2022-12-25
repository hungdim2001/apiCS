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
    public static String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
