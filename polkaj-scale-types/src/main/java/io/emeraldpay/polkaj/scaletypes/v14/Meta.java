package io.emeraldpay.polkaj.scaletypes.v14;

import java.util.List;

public class Meta {

    private Lookup lookup;
    private List<Pallet> pallets;
    private Extrinsic extrinsic;
    private Integer type;

    public Meta(Lookup lookup, List<Pallet> pallets, Extrinsic extrinsic, Integer type) {
        this.lookup = lookup;
        this.pallets = pallets;
        this.extrinsic = extrinsic;
        this.type = type;
    }

    public Meta() {
    }

    public Lookup getLookup() {
        return lookup;
    }

    public void setLookup(Lookup lookup) {
        this.lookup = lookup;
    }

    public List<Pallet> getPallets() {
        return pallets;
    }

    public void setPallets(List<Pallet> pallets) {
        this.pallets = pallets;
    }

    public Extrinsic getExtrinsic() {
        return extrinsic;
    }

    public void setExtrinsic(Extrinsic extrinsic) {
        this.extrinsic = extrinsic;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
