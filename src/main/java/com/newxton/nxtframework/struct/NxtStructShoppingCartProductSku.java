package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/19
 * @address Shenzhen, China
 * @copyright NxtFramework
 */
public class NxtStructShoppingCartProductSku implements Comparable<NxtStructShoppingCartProductSku> {

    public String skuKeyName;
    public String skuValueName;

    @Override
    public int compareTo(NxtStructShoppingCartProductSku productSku) {
    	int c1 = 0;
    	if (this.skuKeyName != null){
			for (char c : this.skuKeyName.toCharArray()) {
				c1 += c;
			}
		}
		int c2 = 0;
		if (productSku.getSkuKeyName() != null) {
			for (char c : productSku.getSkuKeyName().toCharArray()) {
				c2 += c;
			}
		}
        return  c1 - c2;
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
		NxtStructShoppingCartProductSku other = (NxtStructShoppingCartProductSku) obj;
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
