package MD5;
/*
*
* */
public class Converter {

    ///16进制字符串转化为Long类型
    public static int hexStringToHexLong(String hex) {
        return Integer.parseInt(hex);
    }

    public static String intToBinary(int num){
        String binary = Integer.toBinaryString(num);
        //System.out.println(binary.length() + ":" + binary);
        while(binary.length() < 32){
            binary = "0" + binary;
        }

        return binary.substring(binary.length() - 32, binary.length());
    }

    public static String longToBinary(long num){
        String binary = Long.toBinaryString(num);
        //System.out.println(binary.length() + ":" + binary);
        while(binary.length() < 32){
            binary = "0" + binary;
        }

        return binary.substring(binary.length() - 32, binary.length());
    }

    ///二进制字符串转化为16进制字符串
    public static String binaryToHex(String binary) {
        String hex = "0x";
        long count = binary.length();
        for (int i = 0; i < count; i++) {
            char bin1 = binary.charAt(i);
            char bin2 = binary.charAt(i+1);
            char bin3 = binary.charAt(i+2);
            char bin4 = binary.charAt(i+3);
            String fourBinary = String.valueOf(bin1) + 
            String.valueOf(bin2) + 
            String.valueOf(bin3) + 
            String.valueOf(bin4);
            hex += Converter.fourBinaryToHex(fourBinary);
            i = i + 3;
        }
        return hex;
    }

    private static String fourBinaryToHex(String binary) {
        String hex = "";
        switch (binary) {
            case "0000":
                hex = "0";
                break;
            case "0001":
                hex = "1";
                break;
            case "0010":
                hex = "2";
                break;
            case "0011":
                hex = "3";
                break;
            case "0100":
                hex = "4";
                break;
            case "0101":
                hex = "5";
                break;
            case "0110":
                hex = "6";
                break;
            case "0111":
                hex = "7";
                break;
            case "1000":
                hex = "8";
                break;
            case "1001":
                hex = "9";
                break;
            case "1010":
                hex = "a";
                break;
            case "1011":
                hex = "b";
                break;
            case "1100":
                hex = "c";
                break;
            case "1101":
                hex = "d";
                break;
            case "1110":
                hex = "e";
                break;
            case "1111":
                hex = "f";
                break;
            default:
                break;
        }

        return hex;
    }
}
