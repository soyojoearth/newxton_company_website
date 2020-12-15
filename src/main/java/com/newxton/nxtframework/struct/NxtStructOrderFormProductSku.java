package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/24
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructOrderFormProductSku implements Comparable<NxtStructOrderFormProductSku> {

    public String skuKeyName;
    public String skuValueName;

    @Override
    public int compareTo(NxtStructOrderFormProductSku productSku) {
        char a = this.skuKeyName == null ? 0 : this.skuKeyName.toCharArray()[0];
        char b = productSku.getSkuKeyName() == null ? 0 : productSku.getSkuKeyName().toCharArray()[0];
        return  a - b;
    }

    public String getSkuKeyName() {
        return skuKeyName;
    }

    public void setSkuKeyName(String skuKeyName) {
        this.skuKeyName = skuKeyName;
    }

    public String getSkuValueName() {
        return skuValueName;
    }

    public void setSkuValueName(String skuValueName) {
        this.skuValueName = skuValueName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((skuKeyName == null) ? 0 : skuKeyName.hashCode());
        result = prime * result + ((skuValueName == null) ? 0 : skuValueName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NxtStructOrderFormProductSku other = (NxtStructOrderFormProductSku) obj;
        if (skuKeyName == null) {
            if (other.skuKeyName != null)
                return false;
        } else if (!skuKeyName.equals(other.skuKeyName))
            return false;
        if (skuValueName == null) {
            if (other.skuValueName != null)
                return false;
        } else if (!skuValueName.equals(other.skuValueName))
            return false;
        return true;
    }

}
