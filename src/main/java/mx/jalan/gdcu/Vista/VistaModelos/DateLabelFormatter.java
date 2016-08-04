/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.jalan.gdcu.Vista.VistaModelos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 *
 * @author Jorge
 */
public class DateLabelFormatter extends AbstractFormatter{

    private String fechaPatron = "dd-MM-yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(fechaPatron);
    
    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(fechaPatron);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if(value != null){
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }else{
            return "";
        }
    }
    
    
}
