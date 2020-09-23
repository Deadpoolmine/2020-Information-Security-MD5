package MD5;
import java.util.ArrayList;

/**
 * MD5
 */
public class MD5 {
    public static long _A = 0x01234567;
    public static long _B = 0x89abcdef;
    public static long _C = 0xfedcba98;
    public static long _D = 0x76543210;
    public static int[][] _S = new int[][]{
        new int[]{7,12,17,22},
        new int[]{5,9,14,20},
        new int[]{4,11,16,23},
        new int[]{6,10,15,21}
    };
    public static int[][] _M = new int[][]{
        new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},
        new int[]{1,6,11,0,5,10,15,4,9,14,3,8,13,2,7,12},
        new int[]{5,8,11,14,1,4,7,10,13,0,3,6,9,12,15,2},
        new int[]{0,7,14,5,12,3,10,1,8,15,6,13,4,11,2,9},
    };
    
    public static void main(String[] args) {
        //System.out.println(Math.sin(90 * Math.PI / 180));
        //long test = (Math.pow(2, 34) - Math.pow(2, 33));
        //System.out.println(Converter.longToBinary(test));
        //System.out.println((Converter.longToBinary(test<<2|test>>>(32-2))));
        String message = generateSampleMessage();
        String encryptedMsg = encrypt(message,_A,_B,_C,_D);
        System.out.println(Converter.binaryToHex(encryptedMsg));
        System.out.println("长度："+encryptedMsg.length());
    }

    public static String encrypt(String message, long A, long B, long C, long D) {
        String msg = inflate(message);
        ArrayList<String> X = new ArrayList<>();
        int n = msg.length() / 512;
        long a,b,c,d;
        for (int i = 0; i < n; i++) {
            ///初始化X[0...15], 每次处理512位
            for (int j = 0; j < 16; j++) {
                X.add(msg.substring(512 * i + 32 * j, 512 * i + 32 * (j + 1)));
            }
            a = A;b = B;c = C;d = D;
            long[] abcd = fourAlgorithm(X, a, b, c , d);
            a = abcd[0];b = abcd[1];c = abcd[2];d = abcd[3];
            A += a;B += b;C += c;D += d;
        }
        return Converter.longToBinary(A) + Converter.longToBinary(B)
        + Converter.longToBinary(C) + Converter.longToBinary(D);
    }

    private static long[] fourAlgorithm(ArrayList<String> X, long a, long b, long c, long d){
        long[] abcd = new long[4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 16; j++) {
                if(i == 0){
                    a = fourAlgorithmFunc(a, b, c, X.get(_M[i][j]), _S[i][0], T(i * 16 + j + 1), E(b,c,d));
                    d = fourAlgorithmFunc(d, a, b, X.get(_M[i][j + 1]), _S[i][1], T(i * 16 + j + 2), E(a,b,c));
                    c = fourAlgorithmFunc(c, d, a, X.get(_M[i][j + 2]), _S[i][2], T(i * 16 + j + 3), E(d,a,b));
                    b = fourAlgorithmFunc(b, c, d, X.get(_M[i][j + 3]), _S[i][3], T(i * 16 + j + 4), E(c,d,a));
                }
                else if(i == 1){
                    a = fourAlgorithmFunc(a, b, c, X.get(_M[i][j]), _S[i][0], T(i * 16 + j + 1), F(b,c,d));
                    d = fourAlgorithmFunc(d, a, b, X.get(_M[i][j + 1]), _S[i][1], T(i * 16 + j + 2), F(a,b,c));
                    c = fourAlgorithmFunc(c, d, a, X.get(_M[i][j + 2]), _S[i][2], T(i * 16 + j + 3), F(d,a,b));
                    b = fourAlgorithmFunc(b, c, d, X.get(_M[i][j + 3]), _S[i][3], T(i * 16 + j + 4), F(c,d,a));
                }
                else if(i == 2){
                    a = fourAlgorithmFunc(a, b, c, X.get(_M[i][j]), _S[i][0], T(i * 16 + j + 1), G(b,c,d));
                    d = fourAlgorithmFunc(d, a, b, X.get(_M[i][j + 1]), _S[i][1], T(i * 16 + j + 2), G(a,b,c));
                    c = fourAlgorithmFunc(c, d, a, X.get(_M[i][j + 2]), _S[i][2], T(i * 16 + j + 3), G(d,a,b));
                    b = fourAlgorithmFunc(b, c, d, X.get(_M[i][j + 3]), _S[i][3], T(i * 16 + j + 4), G(c,d,a));
                }
                else {
                    a = fourAlgorithmFunc(a, b, c, X.get(_M[i][j]), _S[i][0], T(i * 16 + j + 1), H(b,c,d));
                    d = fourAlgorithmFunc(d, a, b, X.get(_M[i][j + 1]), _S[i][1], T(i * 16 + j + 2), H(a,b,c));
                    c = fourAlgorithmFunc(c, d, a, X.get(_M[i][j + 2]), _S[i][2], T(i * 16 + j + 3), H(d,a,b));
                    b = fourAlgorithmFunc(b, c, d, X.get(_M[i][j + 3]), _S[i][3], T(i * 16 + j + 4), H(c,d,a));
                }
                j = j + 3;
            }
        }
        abcd[0] = a;abcd[1] = b;abcd[2] = c;abcd[3] = d;
        return abcd;
    }

    private static long T(long i){
        //System.out.println(i + ":" + (long)(Math.pow(2, 32) * Math.abs(Math.sin(i))));
        //System.out.println(i + ":" + Converter.binaryToHex(Converter.longToBinary((long)(Math.pow(2, 32) * Math.abs(Math.sin(i))))));
        System.out.println("第"+i+"轮：");
        long result = (long)(Math.pow(2, 32) * Math.abs(Math.sin(i)));
        return result;
    }
    private static long fourAlgorithmFunc(long a, long b, long c, String M, Integer s, long t, long sp){
        int m = Converter.hexStringToHexLong(M);   //m 32位
        int shiftedValue = (int)(a+sp+m+t);
        //System.out.println("ShiftedValue:"+Converter.binaryToHex(Converter.longToBinary(shiftedValue)));
        long result = b + (shiftedValue<<s|shiftedValue>>>(32-s));
        System.out.println("结果："+ Converter.binaryToHex(Converter.longToBinary(result)));
        System.out.println("------------------------");
        return result;
    }
    private static long E(long X, long Y, long Z) {
        return (long)((X&Y)|(~X&Z));
    }
    private static long F(long X, long Y, long Z){
        return (long)((X&Z)|(Y&~Z));
    }
    private static long G(long X, long Y, long Z){
        return (long)(X^Y^Z);
    }
    private static long H(long X, long Y, long Z){
        return (long)(Y^(X|~Z));
    }

    private static String generateSampleMessage(){
        String sample = "";
        for (long i = 0; i < 512; i++) {
            sample += "0";
        }
        return sample;
    }

    private static String inflate(String message){
        String inflatedMessage = message;
        int messageLength = inflatedMessage.length();
        if(inflatedMessage.length() % 512 != 0){
            int inflateCount = 512 * (messageLength / 512) - messageLength;
            String messageLengthBinary = Integer.toBinaryString(messageLength);
            int restCount = inflateCount - messageLengthBinary.length();
            if(restCount > 0){
                inflatedMessage += "1";
                if(restCount > 1){
                    for (int i = 1; i <= restCount - 1; i++) {    
                        inflatedMessage += "0";   
                    }
                }
            }
            inflatedMessage += messageLengthBinary;
        }
        return inflatedMessage;
    }
}