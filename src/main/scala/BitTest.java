import java.util.BitSet;

/**
 * Created by Administrator on 2/25/2018.
 */
public class BitTest {

  public static void main(String[] args) {

    BitSet bs1=new BitSet(3);
    BitSet bs2=new BitSet(3);
    BitSet bs3=new BitSet();

    bs1.set(0);
    bs1.set(2);
    bs1.set(3);

    int[] intArray = bits2Ints(bs1);

    for (int i = 0; i < intArray.length; i++){
      System.out.println("-"+i+"---"+intArray[i]);
      System.out.println(toBinary(intArray[i]));

    }


    int n = 2;
    BitSet bs4 = BitSet.valueOf(new long[]{n});
    bs2.set(0);
    bs2.set(3);
    bs1.xor(bs2);


    int a =2<<3;


    System.out.println(bs1+"----"+bs2+"------"+bs4+"---"+a);


  }

  static int[] bits2Ints(BitSet bs) {
    int[] temp = new int[bs.size() / 32];

    for (int i = 0; i < temp.length; i++)
      for (int j = 0; j < 32; j++)
        if (bs.get(i * 32 + j))
          temp[i] |= 1 << j;

    return temp;
  }

  static String toBinary(int num) {
    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < 32; i++) {
      sb.append(((num & 1) == 1) ? '1' : '0');
      num >>= 1;
    }

    return sb.reverse().toString();
  }



}
