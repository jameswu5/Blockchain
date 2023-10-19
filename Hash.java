// import java.security.MessageDigest;

public class Hash {

    public String hash(String text) {

        // Convert the text to binary
        String binaryString = convertToBinary(text);

        return "";
    }

    public static String convertToBinary(String text) {
        byte[] bytes = text.getBytes();
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                sb.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }

        return sb.toString();
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