package lk.ijse.fitnesscentre.util;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.paint.Paint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFiledValid(TextField textField, String text){
        String field = "";

        switch (textField){

            case TRAINERID:
                field = "^T([0-9]{3})$"; break;

            case NAME:
                field = "^[A-z|\\s]{3,}$"; break;

            case ADDRESS:
                field = "^([A-z0-9]|[-/,.@+]|\\s){4,}$"; break;

            case CONTACT:
                field = "^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$"; break;

            case EXPERIENCE:
                field = "^(\\d{1,2}|100)\\s(years|months)$"; break;

            case SCHEDULEID:
                field = "^([S][0-9]{3})$"; break;

            case DESCRIPTION:
                field = "^([\\w\\s.,!?()-]+)$"; break;

            case USERNAME:
                field = "^[a-zA-Z0-9_-]{3,16}$"; break;

            case EMAIL:
                field = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$"; break;

            case PASSWORD:
                field = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$"; break;

            case PRODUCTID:
                field = "^PRD([0-9]{3})$"; break;

            case PRICE:
                field = "^(^\\d+(\\.\\d{1,2})?)$"; break;

            case COUNT:
                field = "^\\d+$"; break;

            case PURCHASEID:
                field = "^PUR([0-9]{3})$"; break;

            case DATE:
                field = "^(19|20)\\d\\d-(0[1-9]|1[0-2])-(0[1-9]|1\\d|2[0-8])$|^(19|20)\\d\\d-(0[13-9]|1[0-2])-(29|30)$|^(19|20)\\d\\d-(0[13578]|1[02])-31$|^(19|20)([02468][048]|[13579][26])-02-29$"; break;

            case TIME:
                field = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$"; break;

            case PAYMENTID:
                field = "^PAY([0-9]{3})$"; break;

            case MEMBERSHIPID:
                field = "^MS([0-9]{3})$"; break;

            case MEMBERID:
                field = "^M([0-9]{3})$"; break;

            case ATTENDANCEID:
                field = "^AT([0-9]{3})$"; break;

            case OTP:
                field = "^\\d{5}$"; break;
        }

        Pattern pattern = Pattern.compile(field);

        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean setTextColor(TextField location, JFXTextField field){
        if (Regex.isTextFiledValid(location,field.getText())){
            field.setFocusColor(Paint.valueOf("Green"));
            field.setUnFocusColor(Paint.valueOf("Green"));
            return true;
        }else {
            field.setFocusColor(Paint.valueOf("Red"));
            field.setUnFocusColor(Paint.valueOf("Red"));
            return false;
        }
    }

    public static boolean setTextColor(TextField location, JFXPasswordField field){
        if (Regex.isTextFiledValid(location,field.getText())){
            field.setFocusColor(Paint.valueOf("Green"));
            field.setUnFocusColor(Paint.valueOf("Green"));
            return true;
        } else {
            field.setFocusColor(Paint.valueOf("Red"));
            field.setUnFocusColor(Paint.valueOf("Red"));
            return false;
        }
    }
}
