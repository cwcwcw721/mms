/*   1:    */ package com.genymotion.api;
/*   2:    */ 
/*   3:    */ import android.content.Context;
/*   4:    */ import android.os.Build.VERSION;
/*   5:    */ import android.os.Handler;
/*   6:    */ import android.os.HandlerThread;
/*   7:    */ import android.os.Looper;
/*   8:    */ import android.os.RemoteException;
/*   9:    */ import android.provider.Settings.Secure;
/*  10:    */ import com.genymotion.genyd.IGenydService;
/*  11:    */ 
/*  12:    */ public class Id
/*  13:    */ {
/*  14:    */   private IGenydService genyd;
/*  15: 22 */   private Object mLock = new Object();
/*  16:    */   
/*  17:    */   Id(IGenydService genyd)
/*  18:    */   {
/*  19: 25 */     this.genyd = genyd;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Id setAndroidId(String id)
/*  23:    */   {
/*  24: 36 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.Id.setAndroidId(java.lang.String)");
/*  25: 36 */     if ((id.length() > 16) || (!id.matches("^[\\da-fA-F]+$"))) {
/*  26: 37 */       throw new IllegalArgumentException("Android ID should be less than 16 hex chars long");
/*  27:    */     }
/*  28:    */     try
/*  29:    */     {
/*  30: 40 */       this.genyd.setAndroidId(id);
/*  31:    */     }
/*  32:    */     catch (RemoteException e)
/*  33:    */     {
/*  34: 42 */       throw new GenymotionException("Unable to communicate with Genymotion", e);
/*  35:    */     }
/*  36: 45 */     waitForTargetId(id);
/*  37:    */     
/*  38: 47 */     return this;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Id setRandomAndroidId()
/*  42:    */   {
/*  43: 57 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.Id.setRandomAndroidId()");
/*  44:    */     String id;
/*  45:    */     try
/*  46:    */     {
/*  47: 57 */       this.genyd.setRandomAndroidId();
/*  48: 58 */       id = this.genyd.getAndroidId();
/*  49:    */     }
/*  50:    */     catch (RemoteException e)
/*  51:    */     {
/*  52: 60 */       throw new GenymotionException("Unable to communicate with Genymotion", e);
/*  53:    */     }
/*  54: 63 */     waitForTargetId(id);
/*  55:    */     
/*  56: 65 */     return this;
/*  57:    */   }
/*  58:    */   
/*  59:    */   private void waitForTargetId(final String targetId)
/*  60:    */   {
/*  61: 76 */     HandlerThread handlerThread = new HandlerThread("waitForTargetId");
/*  62: 77 */     handlerThread.start();
/*  63:    */     
/*  64:    */ 
/*  65: 80 */     Looper looper = handlerThread.getLooper();
/*  66: 81 */     final Handler handler = new Handler(looper);
/*  67: 82 */     Runnable runCheckId = new Runnable()
/*  68:    */     {
/*  69:    */       public void run()
/*  70:    */       {
/*  71: 86 */         String currentAndroidId = Settings.Secure.getString(GenymotionManager.genymotionManager.context.getContentResolver(), "android_id");
/*  72: 87 */         if (!currentAndroidId.equals(targetId))
/*  73:    */         {
/*  74: 91 */           handler.postDelayed(this, 100L);
/*  75: 92 */           return;
/*  76:    */         }
/*  77: 96 */         synchronized (Id.this.mLock)
/*  78:    */         {
/*  79: 97 */           Id.this.mLock.notify();
/*  80:    */         }
/*  81:    */       }
/*  82:100 */     };
/*  83:101 */     handler.postDelayed(runCheckId, 100L);
/*  84:    */     try
/*  85:    */     {
/*  86:105 */       synchronized (this.mLock)
/*  87:    */       {
/*  88:106 */         this.mLock.wait(10000L);
/*  89:    */       }
/*  90:    */     }
/*  91:    */     catch (InterruptedException ie) {}
/*  92:112 */     if (Build.VERSION.SDK_INT >= 18) {
/*  93:113 */       handlerThread.quitSafely();
/*  94:    */     } else {
/*  95:115 */       handlerThread.quit();
/*  96:    */     }
/*  97:    */   }
/*  98:    */ }


/* Location:           C:\temp\genymotion-api-1.0.0.jar
 * Qualified Name:     com.genymotion.api.Id
 * JD-Core Version:    0.7.0.1
 */