/*  1:   */ package com.genymotion.api;
/*  2:   */ 
/*  3:   */ import android.os.RemoteException;
/*  4:   */ import com.genymotion.genyd.IGenydService;
/*  5:   */ 
/*  6:   */ public class Radio
/*  7:   */ {
/*  8:   */   private IGenydService genyd;
/*  9:   */   
/* 10:   */   Radio(IGenydService genyd)
/* 11:   */   {
/* 12:17 */     this.genyd = genyd;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public Radio setDeviceId(String id)
/* 16:   */   {
/* 17:38 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.Radio.setDeviceId(java.lang.String)");
/* 18:   */     try
/* 19:   */     {
/* 20:38 */       this.genyd.setDeviceId(id);
/* 21:   */     }
/* 22:   */     catch (RemoteException e)
/* 23:   */     {
/* 24:40 */       throw new GenymotionException("Unable to communicate with Genymotion", e);
/* 25:   */     }
/* 26:43 */     GenymotionManager.checkError(this.genyd);
/* 27:   */     
/* 28:45 */     return this;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Radio setRandomDeviceId()
/* 32:   */   {
/* 33:57 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.Radio.setRandomDeviceId()");
/* 34:   */     try
/* 35:   */     {
/* 36:57 */       this.genyd.setRandomDeviceId();
/* 37:   */     }
/* 38:   */     catch (RemoteException e)
/* 39:   */     {
/* 40:59 */       throw new GenymotionException("Unable to communicate with Genymotion", e);
/* 41:   */     }
/* 42:62 */     GenymotionManager.checkError(this.genyd);
/* 43:   */     
/* 44:64 */     return this;
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\temp\genymotion-api-1.0.0.jar
 * Qualified Name:     com.genymotion.api.Radio
 * JD-Core Version:    0.7.0.1
 */