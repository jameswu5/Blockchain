// import java.security.MessageDigest;

public class Hash {

    public static String hash(String text) {
        // Preprocess the string
        String preprocessedString = preprocessString(text);
        return preprocessedString;
    }

    public static String preprocessString(String text) {
        // Convert the text to binary
        StringBuilder sb = convertToBinary(text);
        long binaryLength = sb.length();

        // Append a single 1
        sb.append(1);

        // Pad with zeros until data length = 512k - 64 (for some integer k)
        int padLength = 512 - (sb.length() + 64) & (0b111111111);
        sb.append("0".repeat(padLength));
        
        // Pad with length of original string (64 bits)
        String binaryRepresentation = Long.toBinaryString(binaryLength);
        int leadingZeros = 64 - binaryRepresentation.length();
        sb.append("0".repeat(leadingZeros));
        sb.append(binaryRepresentation);
        return sb.toString();
    }

    private static StringBuilder convertToBinary(String text) {
        byte[] bytes = text.getBytes();
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                sb.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }

        return sb;
    }


    
    /*
        public static String hash(String text) {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
            byte[] digest = md.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = String.format("$02x", b);
                hexString.append(hex);
            }

            return hexString.toString();

        }
    
     */
}