/*  1:   */ package com.genymotion.api;
/*  2:   */ 
/*  3:   */ public class GenymotionException
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   public GenymotionException() {}
/*  7:   */   
/*  8:   */   public GenymotionException(String message)
/*  9:   */   {
/* 10:20 */     super(message);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public GenymotionException(Throwable cause)
/* 14:   */   {
/* 15:25 */     super(cause);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public GenymotionException(String message, Throwable cause)
/* 19:   */   {
/* 20:30 */     super(message, cause);
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\temp\genymotion-api-1.0.0.jar
 * Qualified Name:     com.genymotion.api.GenymotionException
 * JD-Core Version:    0.7.0.1
 */