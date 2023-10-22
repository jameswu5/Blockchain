// import java.security.MessageDigest;

public class Hash {

    private static final int[] HashValues = {
        0x6a09e667,
        0xbb67ae85,
        0x3c6ef372,
        0xa54ff53a,
        0x510e527f,
        0x9b05688c,
        0x1f83d9ab,
        0x5be0cd19
    };
    
    private static final int[] RoundConstants = {
        0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
        0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
        0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
        0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
        0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
        0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
        0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
        0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
    };

    public static String hash(String text) {
        // Preprocess the string
        String preprocessedString = preprocessString(text);
        int[] schedule = createMessageSchedule(preprocessedString);
        return compress(schedule);
    }

    private static String preprocessString(String text) {
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

    private static int[] createMessageSchedule(String preprocessedString) {
        if (preprocessedString.length() != 512) {
            System.out.println("preprocessedString not length 512");
            return new int[0];
        }

        // mS means messageSchedule
        int[] ms = new int[64];
        for (int i = 0; i < 16; i++) {
            ms[i] = Integer.parseInt(preprocessedString.substring(i * 32, (i + 1) * 32), 2);
        }
        
        // Modify the zeroes at the end of array
        for (int i = 16; i < 64; i++) {
            int s0 = rightRotate(ms[i - 15], 7)
                   ^ rightRotate(ms[i - 15], 18)
                   ^ (ms[i - 15] >>> 3);
            
            int s1 = rightRotate(ms[i - 2], 17)
                   ^ rightRotate(ms[i - 2], 19)
                   ^ (ms[i - 2] >>> 10);
            
            ms[i] = ms[i - 16] + s0 + ms[i - 7] + s1;

        }

        return ms;
    }

    private static int rightRotate(int value, int shift) {
        return (value >>> shift) | (value << (32 - shift));
    }

    private static String compress(int[] messageSchedule) {

        int[] v = new int[8];
        for (int i = 0; i < 8; i++) {
            v[i] = HashValues[i];
        }

        for (int i = 0; i < 64; i++) {
            int s1 = rightRotate(v[4], 6)
                   ^ rightRotate(v[4], 11)
                   ^ rightRotate(v[4], 25);
            int ch = (v[4] & v[5]) ^ (~v[4] & v[6]);
            int temp1 = v[7] + s1 + ch + RoundConstants[i] + messageSchedule[i];
            int s0 = rightRotate(v[0], 2)
                   ^ rightRotate(v[0], 13)
                   ^ rightRotate(v[0], 22);
            int maj = (v[0] & v[1]) ^ (v[0] & v[2]) ^ (v[1] & v[2]);
            int temp2 = s0 + maj;
            v[7] = v[6];
            v[6] = v[5];
            v[5] = v[4];
            v[4] = v[3] + temp1;
            v[3] = v[2];
            v[2] = v[1];
            v[1] = v[0];
            v[0] = temp1 + temp2;
        }

        int[] fin = new int[8];
        for (int i = 0; i < 8; i++) {
            fin[i] = HashValues[i] + v[i];
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(Integer.toHexString(fin[i]));
        }

        return sb.toString();
        
    }

    // For testing only
    private static void print32BitIntegerAsBinary(int num) {
        String binaryString = Integer.toBinaryString(num);
        System.out.print("0".repeat(32 - binaryString.length()));
        System.out.println(binaryString);
    }
}