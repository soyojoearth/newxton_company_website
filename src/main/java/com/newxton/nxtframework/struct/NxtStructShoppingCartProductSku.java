package com.newxton.nxtframework.struct;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/11/19
 * @address Shenzhen, China
 */
public class NxtStructShoppingCartProductSku implements Comparable<NxtStructShoppingCartProductSku> {

    public String skuKeyName;
    public String skuValueName;

    @Override
    public String toString() {
        return "skuKeyName:"+skuKeyName+" skuValueName:"+skuValueName;
    }

    @Override
    public int compareTo(NxtStructShoppingCartProductSku productSku) {
        char a = this.skuKeyName == null ? 0 : this.skuKeyName.toCharArray()[0];
        char b = productSku.getSkuKeyName() == null ? 0 : productSku.getSkuKeyName().toCharArray()[0];
        return  a - b;
    }

    public boolean equal(Object another){
        //先判断是不是自己
        if (this == another) {
            return true;
        }
        //再判断是不是NxtStructShoppingCartProductSku类
        if (another instanceof NxtStructShoppingCartProductSku) {
            NxtStructShoppingCartProductSku anotherObject = (NxtStructShoppingCartProductSku) another;
            if (this.toString().equals(anotherObject.toString())){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
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

}
