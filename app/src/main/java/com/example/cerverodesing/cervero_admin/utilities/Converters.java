package com.example.cerverodesing.cervero_admin.utilities;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Cervero Desing on 28/01/2017.
 */

public class Converters {

    public static Locale MODE = new Locale("es", "CO");
    static NumberFormat myFormat = NumberFormat.getCurrencyInstance(MODE);



    public static String DoubleToCurrency(Double value){
        return myFormat.format(value);
    }

    public static String DateToString(Date value, String formato){
        SimpleDateFormat sdf = new SimpleDateFormat(formato, MODE);
        return sdf.format(value);
    }

}
