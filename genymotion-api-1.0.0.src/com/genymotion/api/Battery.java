/*   1:    */ package com.genymotion.api;
/*   2:    */ 
/*   3:    */ import android.content.BroadcastReceiver;
/*   4:    */ import android.content.Context;
/*   5:    */ import android.content.Intent;
/*   6:    */ import android.content.IntentFilter;
/*   7:    */ import android.os.Build.VERSION;
/*   8:    */ import android.os.Handler;
/*   9:    */ import android.os.HandlerThread;
/*  10:    */ import android.os.Looper;
/*  11:    */ import android.os.RemoteException;
/*  12:    */ import com.genymotion.genyd.IGenydService;
/*  13:    */ 
/*  14:    */ public class Battery
/*  15:    */ {
/*  16: 20 */   private Object mLock = new Object();
/*  17:    */   private IGenydService genyd;
/*  18:    */   
/*  19:    */   public static enum Mode
/*  20:    */   {
/*  21: 30 */     HOST(0),  MANUAL(1);
/*  22:    */     
/*  23:    */     private final int value;
/*  24:    */     
/*  25:    */     private Mode(int newValue)
/*  26:    */     {
/*  27: 40 */       this.value = newValue;
/*  28:    */     }
/*  29:    */     
/*  30:    */     public int getValue()
/*  31:    */     {
/*  32: 48 */       return this.value;
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static enum Status
/*  37:    */   {
/*  38: 60 */     CHARGING(0),  DISCHARGING(1),  NOTCHARGING(2),  FULL(3);
/*  39:    */     
/*  40:    */     private final int value;
/*  41:    */     
/*  42:    */     private Status(int newValue)
/*  43:    */     {
/*  44: 80 */       this.value = newValue;
/*  45:    */     }
/*  46:    */     
/*  47:    */     public int getValue()
/*  48:    */     {
/*  49: 88 */       return this.value;
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   private int getStatusToBatteryManagerValue(Status status)
/*  54:    */   {
/*  55: 98 */     switch (2.$SwitchMap$com$genymotion$api$Battery$Status[status.ordinal()])
/*  56:    */     {
/*  57:    */     case 1: 
/*  58: 99 */       return 2;
/*  59:    */     case 2: 
/*  60:100 */       return 3;
/*  61:    */     case 3: 
/*  62:101 */       return 4;
/*  63:    */     case 4: 
/*  64:102 */       return 5;
/*  65:    */     }
/*  66:103 */     return 4;
/*  67:    */   }
/*  68:    */   
/*  69:    */   private Status getStatusFromBatteryManagerValue(int status)
/*  70:    */   {
/*  71:112 */     switch (status)
/*  72:    */     {
/*  73:    */     case 2: 
/*  74:113 */       return Status.CHARGING;
/*  75:    */     case 3: 
/*  76:114 */       return Status.DISCHARGING;
/*  77:    */     case 4: 
/*  78:115 */       return Status.NOTCHARGING;
/*  79:    */     case 5: 
/*  80:116 */       return Status.FULL;
/*  81:    */     }
/*  82:117 */     return Status.NOTCHARGING;
/*  83:    */   }
/*  84:    */   
/*  85:    */   Battery(IGenydService genyd)
/*  86:    */   {
/*  87:126 */     this.genyd = genyd;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Battery setLevel(int level)
/*  91:    */   {
/*  92:138 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.Battery.setLevel(int)");
/*  93:    */     try
/*  94:    */     {
/*  95:138 */       this.genyd.setBatteryLevel(level);
/*  96:    */     }
/*  97:    */     catch (RemoteException e)
/*  98:    */     {
/*  99:140 */       throw new GenymotionException("Unable to communicate with Genymotion", e);
/* 100:    */     }
/* 101:143 */     GenymotionManager.checkError(this.genyd);
/* 102:    */     
/* 103:    */ 
/* 104:146 */     Intent targetBatteryStatus = new Intent();
/* 105:147 */     targetBatteryStatus.putExtra("level", level);
/* 106:148 */     targetBatteryStatus.putExtra("scale", 100);
/* 107:149 */     waitForTargetPowerState(targetBatteryStatus);
/* 108:    */     
/* 109:151 */     return this;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Mode getMode()
/* 113:    */   {
/* 114:160 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.Battery.getMode()");Mode mode = Mode.HOST;
/* 115:    */     try
/* 116:    */     {
/* 117:163 */       mode = Mode.values()[this.genyd.getBatteryMode()];
/* 118:    */     }
/* 119:    */     catch (RemoteException e)
/* 120:    */     {
/* 121:165 */       throw new GenymotionException("Unable to communicate with Genymotion", e);
/* 122:    */     }
/* 123:168 */     GenymotionManager.checkError(this.genyd);
/* 124:    */     
/* 125:170 */     return mode;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Battery setMode(Mode mode)
/* 129:    */   {
/* 130:180 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.Battery.setMode(com.genymotion.api.Battery$Mode)");
/* 131:    */     try
/* 132:    */     {
/* 133:180 */       this.genyd.setBatteryMode(mode.getValue());
/* 134:    */     }
/* 135:    */     catch (RemoteException e)
/* 136:    */     {
/* 137:182 */       throw new GenymotionException("Unable to communicate with Genymotion", e);
/* 138:    */     }
/* 139:185 */     GenymotionManager.checkError(this.genyd);
/* 140:    */     
/* 141:187 */     return this;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public Battery setStatus(Status status)
/* 145:    */   {
/* 146:199 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.Battery.setStatus(com.genymotion.api.Battery$Status)");
/* 147:    */     try
/* 148:    */     {
/* 149:199 */       this.genyd.setBatteryStatus(status.getValue());
/* 150:    */     }
/* 151:    */     catch (RemoteException e)
/* 152:    */     {
/* 153:201 */       throw new GenymotionException("Unable to communicate with Genymotion", e);
/* 154:    */     }
/* 155:204 */     GenymotionManager.checkError(this.genyd);
/* 156:    */     
/* 157:    */ 
/* 158:207 */     Intent targetBatteryStatus = new Intent();
/* 159:208 */     targetBatteryStatus.putExtra("status", getStatusToBatteryManagerValue(status));
/* 160:209 */     waitForTargetPowerState(targetBatteryStatus);
/* 161:    */     
/* 162:211 */     return this;
/* 163:    */   }
/* 164:    */   
/* 165:    */   private void waitForTargetPowerState(final Intent target)
/* 166:    */   {
/* 167:220 */     BroadcastReceiver listener = new BroadcastReceiver()
/* 168:    */     {
/* 169:    */       public void onReceive(Context context, Intent intent)
/* 170:    */       {
/* 171:223 */         int status = intent.getIntExtra("status", -1);
/* 172:224 */         int targetStatus = target.getIntExtra("status", -1);
/* 173:227 */         if ((target.hasExtra("status")) && (status != targetStatus)) {
/* 174:228 */           return;
/* 175:    */         }
/* 176:232 */         if ((target.hasExtra("level")) && (target.hasExtra("scale")))
/* 177:    */         {
/* 178:234 */           int level = intent.getIntExtra("level", -1);
/* 179:235 */           int scale = intent.getIntExtra("scale", -1);
/* 180:236 */           double batteryPct = level / scale;
/* 181:    */           
/* 182:238 */           int targetLevel = target.getIntExtra("level", -1);
/* 183:239 */           int targetScale = target.getIntExtra("scale", -1);
/* 184:240 */           double targetBatteryPct = targetLevel / targetScale;
/* 185:243 */           if (Math.abs(batteryPct - targetBatteryPct) >= 0.001D) {
/* 186:244 */             return;
/* 187:    */           }
/* 188:    */         }
/* 189:249 */         synchronized (Battery.this.mLock)
/* 190:    */         {
/* 191:250 */           Battery.this.mLock.notify();
/* 192:    */         }
/* 193:    */       }
/* 194:254 */     };
/* 195:255 */     HandlerThread handlerThread = new HandlerThread("waitForTargetLocation");
/* 196:256 */     handlerThread.start();
/* 197:    */     
/* 198:    */ 
/* 199:259 */     Looper looper = handlerThread.getLooper();
/* 200:260 */     Handler handler = new Handler(looper);
/* 201:    */     
/* 202:262 */     Context ctx = GenymotionManager.genymotionManager.context;
/* 203:263 */     IntentFilter filter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
/* 204:264 */     ctx.registerReceiver(listener, filter, null, handler);
/* 205:    */     try
/* 206:    */     {
/* 207:268 */       synchronized (this.mLock)
/* 208:    */       {
/* 209:269 */         this.mLock.wait(10000L);
/* 210:    */       }
/* 211:    */     }
/* 212:    */     catch (InterruptedException ie) {}
/* 213:275 */     ctx.unregisterReceiver(listener);
/* 214:277 */     if (Build.VERSION.SDK_INT >= 18) {
/* 215:278 */       handlerThread.quitSafely();
/* 216:    */     } else {
/* 217:280 */       handlerThread.quit();
/* 218:    */     }
/* 219:    */   }
/* 220:    */ }


/* Location:           C:\temp\genymotion-api-1.0.0.jar
 * Qualified Name:     com.genymotion.api.Battery
 * JD-Core Version:    0.7.0.1
 */