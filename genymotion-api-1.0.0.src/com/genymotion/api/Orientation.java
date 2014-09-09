/*  1:   */ package com.genymotion.api;
/*  2:   */ 
/*  3:   */ import android.os.RemoteException;
/*  4:   */ import com.genymotion.genyd.IGenydService;
/*  5:   */ 
/*  6:   */ class Orientation
/*  7:   */ {
/*  8:   */   private IGenydService genyd;
/*  9:   */   private static final int ACCELEROMETER_WRITE_MIN_PERIOD = 200;
/* 10:   */   
/* 11:   */   Orientation(IGenydService genyd)
/* 12:   */   {
/* 13:22 */     this.genyd = genyd;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Orientation setAngle(double angle)
/* 17:   */   {
/* 18:34 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.Orientation.setAngle(double)");
/* 19:   */     try
/* 20:   */     {
/* 21:34 */       this.genyd.setOrientationAngle(angle);
/* 22:   */       
/* 23:36 */       Thread.sleep(200L);
/* 24:   */     }
/* 25:   */     catch (RemoteException e)
/* 26:   */     {
/* 27:38 */       throw new GenymotionException("Unable to communicate with Genymotion", e);
/* 28:   */     }
/* 29:   */     catch (InterruptedException e) {}
/* 30:44 */     GenymotionManager.checkError(this.genyd);
/* 31:   */     
/* 32:   */ 
/* 33:   */ 
/* 34:48 */     return this;
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\temp\genymotion-api-1.0.0.jar
 * Qualified Name:     com.genymotion.api.Orientation
 * JD-Core Version:    0.7.0.1
 */