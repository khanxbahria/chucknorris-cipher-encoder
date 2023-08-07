
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UI.mainloop();

    }
}

class UI {
    private static final Scanner scanner = new Scanner(System.in);
    public static void mainloop() {
        String actionInput = null;
        while (!"exit".equals(actionInput)){
            System.out.println("Please input operation (encode/decode/exit):");
            actionInput = scanner.nextLine();

            switch (actionInput) {
                case "encode":
                    encode();
                    break;
                case "decode":
                    decode();
                    break;
                case "exit":
                    break;
                default:
                    System.out.printf("""
                    There is no '%s' operation
                                      """.formatted(actionInput));
            }
            System.out.println();
        }
    }
    private static void encode() {
        System.out.println("Input string:");
        String str = scanner.nextLine();
        String encoded = ChuckNorris.encode(str);
        System.out.println("Encoded string:");
        System.out.println(encoded);
    }

    private static void decode() {
        System.out.println("Input encoded string:");
        String str = scanner.nextLine();
        String decoded = ChuckNorris.decode(str);
        System.out.println("Decoded string:");
        System.out.println(decoded);
    }
}

class ChuckNorris {
    public static String encode(String text) {
        return binaryStringToChuckNorris(ASCIIToBinaryString(text));
    }

    public static String binaryStringToChuckNorris(String binaryString) {
        StringBuilder sbChuckNorris = new StringBuilder();
        int length = 1;
        char blockDigit = binaryString.charAt(0);
        for(int i=1; i<binaryString.length(); i++) {
            if (binaryString.charAt(i) == blockDigit) {
                length++;
            } else {
                sbChuckNorris.append(encodeBinaryChunk(blockDigit, length) + " ");
                length = 1;
                blockDigit = blockDigit == '1' ? '0' : '1';
            }
        }
        sbChuckNorris.append(encodeBinaryChunk(blockDigit, length));
        return sbChuckNorris.toString();
    }

    private static String encodeBinaryChunk(char blockDigit, int length) {
        String blockDigitNotation = blockDigit == '1' ? "0" : "00";
        return blockDigitNotation + " " + "0".repeat(length);
    }

    
    private static String ASCIIToBinaryString(String ASCIIStr) {
        StringBuilder sbBinaryString = new StringBuilder();
        for (int i=0; i<ASCIIStr.length();i++) {
            String binaryString = Integer.toBinaryString(ASCIIStr.charAt(i));
            String formattedBinaryString = String.format("%07d", Integer.parseInt(binaryString));
            sbBinaryString.append(formattedBinaryString);
        }
        return sbBinaryString.toString();
    }

    public static String decode(String encoded) {
        String bs = chuckNorrisToBinaryString(encoded);
        System.out.println("Binary string: " + bs);
        return binaryStringToASCII(bs);
    }

    private static String decodeChunk(String blockCode, String lengthCode) {
        if ("0".equals(blockCode)) {
            int length = lengthCode.length();
            return "1".repeat(length);
        } else {
            return lengthCode;
        }
    }
    
    private static String chuckNorrisToBinaryString(String chuckNorrisStr) {
        StringBuilder sbBinaryString = new StringBuilder();
        Scanner scanner = new Scanner(chuckNorrisStr);
        while (scanner.hasNext()) {
            String decodedChunk = decodeChunk(scanner.next(), scanner.next());
            sbBinaryString.append(decodedChunk);
        }
        return sbBinaryString.toString();
    }
    
    private static String binaryStringToASCII(String binaryString) {
        StringBuilder sbASCIIStr = new StringBuilder();
        for (int i = 0; i + 7 - 1 < binaryString.length(); i += 7) {
            String binarySubstring = binaryString.substring(i, i + 7);
            sbASCIIStr.append((char) Integer.parseInt(binarySubstring, 2));
        }
        return sbASCIIStr.toString();
    }
}
