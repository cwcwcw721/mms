/*  1:   */ package com.genymotion.api;
/*  2:   */ 
/*  3:   */ public class NotGenymotionDeviceException
/*  4:   */   extends GenymotionException
/*  5:   */ {
/*  6:   */   public NotGenymotionDeviceException()
/*  7:   */   {
/*  8:13 */     super("This device is not a Genymotion Virtual Device, you cannot use Genymotion API.");
/*  9:   */   }
/* 10:   */   
/* 11:   */   public NotGenymotionDeviceException(String message)
/* 12:   */   {
/* 13:17 */     super(message);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public NotGenymotionDeviceException(Throwable cause)
/* 17:   */   {
/* 18:21 */     super(cause);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public NotGenymotionDeviceException(String message, Throwable cause)
/* 22:   */   {
/* 23:25 */     super(message, cause);
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\temp\genymotion-api-1.0.0.jar
 * Qualified Name:     com.genymotion.api.NotGenymotionDeviceException
 * JD-Core Version:    0.7.0.1
 */