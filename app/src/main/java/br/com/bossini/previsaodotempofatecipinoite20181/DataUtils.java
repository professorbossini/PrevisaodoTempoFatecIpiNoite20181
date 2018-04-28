package br.com.bossini.previsaodotempofatecipinoite20181;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rodrigo on 27/04/18.
 */

public class DataUtils {
    public static String obtemDiaDaSemana (long dt){
        SimpleDateFormat sdf =
                new SimpleDateFormat ("EEEE");
        Date date = new Date();
        date.setTime(dt * 1000);
        return sdf.format (date);
    }
}
