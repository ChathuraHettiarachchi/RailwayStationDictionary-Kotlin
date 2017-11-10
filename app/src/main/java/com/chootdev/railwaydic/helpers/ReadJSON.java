package com.chootdev.railwaydic.helpers;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Choota on 10/15/17.
 * Email   : chathura93@yahoo.com
 * GitHub  : https://github.com/chathurahettiarachchi
 * Company : Fidenz Technologies
 */

public class ReadJSON {
    public static String jsonFileToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
