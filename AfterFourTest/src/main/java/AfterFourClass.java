
public class AfterFourClass {

    public int[] afterFourMethod(int[] arr) throws RuntimeException {
        int[] outArr = null;
        int lastFourIndex = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 4) {
                lastFourIndex = i;
            }
        }
        if (lastFourIndex == -1) {
            throw new RuntimeException();
        }
        outArr = new int[arr.length - 1 - lastFourIndex];
        for (int i = 0; i < arr.length-lastFourIndex-1; i++) {
            outArr[i] = arr[lastFourIndex+i+1];
        }
        return outArr;
    }

    public boolean check14(int[] arr){
        boolean exist1=false,exist4=false;
        for (int i=0;i < arr.length;i++){
            if(arr[i] == 4){
                exist4 = true;

            } else if(arr[i] == 1)
            {
                exist1 = true;
            }
            else{
                return false; //Если в метод передали массив, где не только 1 и 4, то возвращаем false
            }
        }
        if(exist4 && exist1){
            return true;
        }
        return false;
    }
}
