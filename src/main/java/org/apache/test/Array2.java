package org.apache.test;

public class Array2 {

  public static void main(String[] args) {


    //int[] arr=new int[]{3,30,34,5,9};
    //int[] arr=new int[]{54,546, 548, 60};
    //int[] arr=new int[]{138,359,178,398,295,905,610,232,746,176,636,299,143,400,969,413,261,558,595,9,396,969,114,531,7,963,943,366,83};
//   int[] arr=new int[]{624,421,145,969,736,916,626,535,43,12,680,153,245,296,819,397,693,816,992,34,670,398,554,548,826,211,663,212,809,378,762,626,336,869,996,777,768,440,875,332,557,302,873,561,95,985,756,790,408,16,194,770,681,456,856,507,964,503,677,109,250,332,845,639,809,998,652,850,204,732,532,15,420,776,10,181,930,224,55,261,738,546,318,526,201,257
//    };
   /* int[] arr=new int[]{891,885,814,442,128,180,785,538,871,562,582,166,803,733,333,855,760,848,378,463,11,820,151,378,942,837,721,
        300,113,760,957,391,153,49,15,45,919,151,102,296,822,732,502,246,962,58,511,929,806,174,138,670,97,504,422,676,519,301,490,
        263,55,264,644,890,251};*/
    //9534330
   // String[] aa = convertToStringArray(arr);

    // Arrays.sort(arr);
    //Arrays.sort(aa, Collections.<String>reverseOrder());
   // String result = getResult(aa);

 /*   for(int s:arr){
      System.out.println(s);
    }*/
    /*System.out.println("=========");
    for(String s1:aa){
      System.out.println(s1);
    }*/

    int n=64;
    int counter=0;
    for (int i=0;i<n;i++){
      for (int j=0;j<n;j++){
        counter++;
      }
    }
    System.out.println(counter);
  }

  private static String getResult(String[] aa) {
    StringBuffer stringBuffer=new StringBuffer();
    for(int j=0;j<aa.length;j++){
      String max="";
      int index=0;
      for(int i=0;i<aa.length;i++){
        if(aa[i].isEmpty()){
          continue;
        }
          if(max.isEmpty()){
            max=aa[i];
            index=i;
            continue;
          }
          int s1Lenth=max.length();
          int s2Lenth=aa[i].length();
        String temp="";
        if(s1Lenth>s2Lenth){
            temp = giveCorrectMaxData(max, aa[i]);
        } else {
            temp = giveCorrectMaxData(aa[i], max);
          }
          if(!temp.equalsIgnoreCase(max)){
            index=i;
            max=temp;
          }
      }

      stringBuffer.append(aa[index]);
      aa[index]="";

    }
   return  stringBuffer.toString();

  }

  private static String giveCorrectMaxData(String o1, String o2) {
     int o1Lenth= o1.length();
     int o2Lenth=o2.length();
     int i=0;
     for (;i<o2Lenth;i++){
       char o1Char=o1.charAt(i);
       char o2Char=o2.charAt(i);
       if(o1Char>o2Char){
        return o1;
       }
       else if(o2Char>o1Char){
         return o2;
       }
     }
     if(i==(o1Lenth)){
       return o1;
     }
    char o1FCha=o1.charAt(0);
    char o2FCha=o1.charAt(i);
     if(o1FCha>o2FCha){
       return o2;
     }
     return o1;
  }

  private static String[] convertToStringArray(int[] arr) {
    String[] a = new String[arr.length];
    for (int i = 0; i < arr.length; i++) {
      a[i] = arr[i] + "";
    }
    return a;
  }
}
