package io.emeraldpay.polkaj.scaletypes.v14;

import java.util.List;

public class Extrinsic {

    private int type;
    private int version;
    private List<SignedExtension> signedExtensions;

    public static class SignedExtension {
        private String identifier;
        private int type;
        private int additionalSigned;

        public SignedExtension(String identifier, int type, int additionalSigned) {
            this.identifier = identifier;
            this.type = type;
            this.additionalSigned = additionalSigned;
        }

        public SignedExtension() {}

        public String getIdentifier() {
            return identifier;
        }

        public void setIdentifier(String identifier) {
            this.identifier = identifier;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getAdditionalSigned() {
            return additionalSigned;
        }

        public void setAdditionalSigned(int additionalSigned) {
            this.additionalSigned = additionalSigned;
        }
    }
}
