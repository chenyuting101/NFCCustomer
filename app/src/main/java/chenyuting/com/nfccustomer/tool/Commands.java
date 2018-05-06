package chenyuting.com.nfccustomer.tool;

/**
 * Created by chenyuting on 9/22/17.
 */

public class Commands {
    //public static final String
    //Send data self-defined++++++
    public static final String COMMAND = "command";
    public static final String NFC_COMMAND = "nfcCommand";
    public static final String COMMAND_GET_NEW_RECEIPT = "get new receipt";
    public static final String COMMAND_USE_ADVERTISEMENT = "use advertisement";
    //ins is 00000010
    public static final String SEND_NEW_RECEIPT_DATA = "88020000";
    //ins is 00000110
    public static final String USE_ADVERTISEMENT = "88060000";
    public static final String PRIVATE_KEY_STR =
            "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDr9Z90/JhMQMgj\n" +
                    "9vDyfKOFePviz1jYD1FuixyZDvch1oExh5JuCOWbINNS15BoLkcf0mfuAX7pdOJK\n" +
                    "eu+byMyEfjeLPMvbo3Q9lA/h8A9Ebia9F+NPt4H17bo/znh08hHAjw19CaBhEoho\n" +
                    "+2lo4N7HyWEVcUbhWpARRXHQ7mjArPTBXzVkfCbcvwMLPI/jkPgOuZm1Cw2L8o68\n" +
                    "bZc/sE387eZGYHyae6R1PiOqRDObL51g+L9EAekLxlhu1KPjTtEuhp6QgrDTCyXe\n" +
                    "/BMR5prjNSohXFOi0QbaBMC1lSPBxmwi5S1nPR3G2lrXhZIuoPPJKZZWPGgwPdm1\n" +
                    "LvFkS7lTAgMBAAECggEATXhGUzV/l0GJtG0mLKcJVRAkuxHSnJVi6gVYbVRBhWWU\n" +
                    "4zscs23OXMFJQKCvs9TF20fMgZMSJPoQNd3o/1/M7g82k0txrvoadwE+ubKEgLYd\n" +
                    "l+XY5tpcG+9iQAK8/8BeXbdZ+VyIwHZCPR2WPS3fVv7iFhUr7V87f8AqHyu3TtKY\n" +
                    "wU1v+ygMI4LHgdj9bcSy/l2o+rVG7WplRrENb5OFbX4JVUPmXC7ga6BEq2BRUKli\n" +
                    "1X7YX19NPmEbszzsmmmNd3juyJZxUDY2vkuJHo6mK/CRdECA9Pz/kH+9CUC9s/AR\n" +
                    "1AfMfhccZpIi/ZAHtTNV/6MZlm6HQgYc7/k0hwID0QKBgQD/wSPqC/t0YBHFSiPo\n" +
                    "3P1UhkWhduLXQoSiojFVmvZoU8SM7nioTZiflk7pMSTb7073N0M0g1aA5cCX6mmK\n" +
                    "Pz+ig/DdMLSORhilchEcroIaR23KS5DTH2DU4hZ0KRhypCNrVPNkTV4b/T8cVyUs\n" +
                    "dzWziZllHpdLx6o2BDyageKAmwKBgQDsL54Kb/nuEDs6AtADtevr7dABVAKGX37b\n" +
                    "Lt+uDvvefIQtV7NXeFV6dd7pz9UCl5V2Yjn2qTYgCojvBU8nl7tINcsvLr+0Ut50\n" +
                    "bTlNkpG3Y8q8lznimmCXRTa/tJiv9yyyTZkSMGv2qsX/USqwQIOdZQoXkl/b4aRY\n" +
                    "FMP7AkQpqQKBgHQVA+oNOjqeCDLV7eqZs1oT/7LDtT4i8PSoTWAfj8vWiZW1/4nU\n" +
                    "fnugy3xIVASHX/4RVS2Wl0K2BW5udSzMfGOVI1Qh8THKWxFs7ptoEaE/3nM9TSSm\n" +
                    "T1sQq0RaCsCuA22KB1b1TsvBbE2+uB6wlO1CfR9KfT1mCLmvvpoabeYVAoGBAOYT\n" +
                    "1N7SCiQwx6FRUBXtSC+CcNh6YCNKL6eQ1/EVQ6KOpawN/PS+knZDEK2v+g8WX1D9\n" +
                    "iv2QBNvdp1tATsWA9732OUJzReiBuZatN98rYqvFmJ9yqb7nKCT0FctWQE9ad74R\n" +
                    "/YVMij+2SD7ZcL5VemD/Jn+j5aGrA4+SWv/QS1pRAoGABScAySB9RdPtFMA5kvZ+\n" +
                    "gBwzixtwGI7naJEzaV1mAVMrUhG+0wU0Wvclti8eLy0CxRx6IRmHN1ilNVKWtV2u\n" +
                    "o6PAheZQrdGI5MyQVpiqA8wmKCGwtwo1Gluw51jytbYDuiQ3SrhiO+ETas2i14P4\n" +
                    "lskY0HlUTo4LTSQLL8o6tPQ=";
    public static final String PUBLIC_KEY_STR =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6/WfdPyYTEDII/bw8nyj\n" +
                    "hXj74s9Y2A9RboscmQ73IdaBMYeSbgjlmyDTUteQaC5HH9Jn7gF+6XTiSnrvm8jM\n" +
                    "hH43izzL26N0PZQP4fAPRG4mvRfjT7eB9e26P854dPIRwI8NfQmgYRKIaPtpaODe\n" +
                    "x8lhFXFG4VqQEUVx0O5owKz0wV81ZHwm3L8DCzyP45D4DrmZtQsNi/KOvG2XP7BN\n" +
                    "/O3mRmB8mnukdT4jqkQzmy+dYPi/RAHpC8ZYbtSj407RLoaekIKw0wsl3vwTEeaa\n" +
                    "4zUqIVxTotEG2gTAtZUjwcZsIuUtZz0dxtpa14WSLqDzySmWVjxoMD3ZtS7xZEu5\n" +
                    "UwIDAQAB";

}
