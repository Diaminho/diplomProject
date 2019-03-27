package sample.validator;

public class MaterialsListManagerValidator {

    public static String validateVolume(Integer volume){
        if (volume==null){
            return "Неверно заполнено поле количество";
        }
        else if (volume.compareTo(0)==0){
            return "Количество материала не может быть равно 0";
        }
        else if (volume<0){
            return "Количество материала не может быть отрицательным";
        }
        else {
            return "Ok";
        }
    }
}
