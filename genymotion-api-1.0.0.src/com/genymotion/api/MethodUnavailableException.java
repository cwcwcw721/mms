/*  1:   */ package com.genymotion.api;
/*  2:   */ 
/*  3:   */ public class MethodUnavailableException
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   public MethodUnavailableException() {}
/*  7:   */   
/*  8:   */   public MethodUnavailableException(String message)
/*  9:   */   {
/* 10:19 */     super(message);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public MethodUnavailableException(Throwable cause)
/* 14:   */   {
/* 15:24 */     super(cause);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public MethodUnavailableException(String message, Throwable cause)
/* 19:   */   {
/* 20:29 */     super(message, cause);
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\temp\genymotion-api-1.0.0.jar
 * Qualified Name:     com.genymotion.api.MethodUnavailableException
 * JD-Core Version:    0.7.0.1
 */