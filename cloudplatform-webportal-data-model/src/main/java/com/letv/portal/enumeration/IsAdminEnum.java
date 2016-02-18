package com.letv.portal.enumeration;


public enum IsAdminEnum {
	
   YES(true),
   NO(false);

   private Boolean code;

   private IsAdminEnum(Boolean code) {
       this.code = code;
   }

   public Boolean getCode() {
       return code;
   }

   
}