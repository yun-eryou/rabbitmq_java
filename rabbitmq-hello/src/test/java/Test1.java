public class Test1 {
    public static void main(String[] args) {

        for (int i = 0; i < 1000; i++) {
            int sum=0;
            for (int j = i; j <1000 ; j++) {
                sum+=j;
                if (sum>1000){
                    break;
                }
                else if (sum==1000){
                    for (int k = i; k <j ; k++) {
                        System.out.print(k+"   ");
                    }
                    System.out.println();
                }
            }
        }
        System.out.println("你好未来");
        System.out.println("我有无限的希望");
    }
}
