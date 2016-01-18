package com.letv.portal.enumeration.cloudvm;

import com.letv.portal.enumeration.IntEnum;

public enum CloudvmNetworkStatusEnum implements IntEnum {
	
   CREATING(1),
   AVAILABLE(2),
   BINDED(3),
   ERROR(4),
   UNRECOGNIZED(5);

   private Integer code;

   private CloudvmNetworkStatusEnum(Integer code) {
       this.code = code;
   }

   public Integer getCode() {
       return code;
   }

   @Override
   public int toInt() {
       return code;
   }
   
}