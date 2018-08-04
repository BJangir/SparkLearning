/**
 * Created by Administrator on 2/25/2018.
 */
public class Learning1 {
  public static void main(String[] args) {

//Arrays
    arrayTest1();
    arrayTest2();


  }
//multi dimentional array
  private static void arrayTest2() {
 int [][] b=new int[1][2];
    int [][] bc=new int[][]{{1,2},{1,2}};
    int[][] clone = bc.clone();
    System.out.println(clone==bc);
    //A clone of a multidimensional array (like Object[][]) is a "shallow copy" however, which is to say that it creates only a single new array with each element array a reference to an original element array but subarrays are shared.

  }

  //single dim array
  private static void arrayTest1() {
  int[] a=new int[10];
  int [] b=new int[]{1,2,3};
  for( int i=0;i<b.length;i++){
    System.out.println(b[i]);
  }
    int[] clone = b.clone();
    System.out.println(clone==b);


  }
}
