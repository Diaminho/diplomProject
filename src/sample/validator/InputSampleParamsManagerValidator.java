package sample.validator;

public class InputSampleParamsManagerValidator {

    public static String validateSize(Integer size, Integer maxSize){
        if (size==null){
            return "Неверно введен размер выборки";
        }
        else if (size<=0){
            return "Размер выборки не может быть равен или меньше 0";
        }
        else if(size>maxSize){
            return "Размер выборки не может быть больше количества материалов";
        }
        else {
            return "Ok";
        }
    }

    public static String validateAc(Integer ac, Integer maxAc){
        if (ac==0){
            return "Неверно введено выборочное число";
        }
        else if (ac<=0){
            return "Выборочное число не может быть меньше 0";
        }
        else if(ac>=maxAc){
            return "Выборочное число не может быть больше или равно количества элементов в выборке";
        }
        else {
            return "Ok";
        }
    }

    public static String validateCount(Integer count){
        if (count==0){
            return "Неверно введено количество генераций";
        }
        else if (count<=0){
            return "Количество генераций не может быть меньше 0";
        }
        else {
            return "Ok";
        }
    }
}
