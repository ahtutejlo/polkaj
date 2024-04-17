package io.emeraldpay.polkaj.scaletypes.v14;

public class MetadataV14Pojo {
    private Integer magic;
    private Integer version;
    private Meta meta;

    public MetadataV14Pojo() {}

    public MetadataV14Pojo(Integer magic, Integer version, Meta meta) {
        this.magic = magic;
        this.version = version;
        this.meta = meta;
    }

    public Integer getMagic() {
        return magic;
    }

    public void setMagic(Integer magic) {
        this.magic = magic;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
