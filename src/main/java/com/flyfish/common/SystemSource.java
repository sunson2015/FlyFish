package com.flyfish.common;

/**
 * description: 
 *<p>
 * @author
 *<p>
 * create date：2016年5月16日 上午11:23:54 
 *</p>
  * @version 1.0  
*/
public enum SystemSource {
    WEB(1), WAP(2), ANDROID(3), IOS(4);

    private int valueId;

    SystemSource(int valueId) {
        this.valueId = valueId;
    }

   
    
    public int getValueId() {
        return valueId;
    }

    public void setValueId(int valueId) {
        this.valueId = valueId;
    }
 
}
