/*   1:    */ package com.genymotion.api;
/*   2:    */ 
/*   3:    */ import android.content.Context;
/*   4:    */ import android.location.Location;
/*   5:    */ import android.location.LocationListener;
/*   6:    */ import android.location.LocationManager;
/*   7:    */ import android.os.Build.VERSION;
/*   8:    */ import android.os.Bundle;
/*   9:    */ import android.os.HandlerThread;
/*  10:    */ import android.os.RemoteException;
/*  11:    */ import com.genymotion.genyd.IGenydService;
/*  12:    */ 
/*  13:    */ public class Gps
/*  14:    */ {
/*  15: 22 */   private Object mLocationLock = new Object();
/*  16:    */   private IGenydService genyd;
/*  17:    */   
/*  18:    */   public static enum Status
/*  19:    */   {
/*  20: 33 */     ENABLED,  DISABLED;
/*  21:    */     
/*  22:    */     private Status() {}
/*  23:    */   }
/*  24:    */   
/*  25:    */   Gps(IGenydService genyd)
/*  26:    */   {
/*  27: 44 */     this.genyd = genyd;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Gps setAccuracy(float accuracy)
/*  31:    */   {
/*  32: 55 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.Gps.setAccuracy(float)");
/*  33:    */     try
/*  34:    */     {
/*  35: 55 */       this.genyd.setGpsAccuracy(accuracy);
/*  36:    */     }
/*  37:    */     catch (RemoteException e)
/*  38:    */     {
/*  39: 57 */       throw new GenymotionException("Unable to communicate with Genymotion", e);
/*  40:    */     }
/*  41: 60 */     GenymotionManager.checkError(this.genyd);
/*  42:    */     
/*  43:    */ 
/*  44: 63 */     Location targetLocation = new Location("DummyProvider");
/*  45: 64 */     targetLocation.setAccuracy(accuracy);
/*  46: 65 */     waitForTargetLocation(targetLocation);
/*  47:    */     
/*  48: 67 */     return this;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Gps setAltitude(double altitude)
/*  52:    */   {
/*  53: 78 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.Gps.setAltitude(double)");
/*  54:    */     try
/*  55:    */     {
/*  56: 78 */       this.genyd.setGpsAltitude(altitude);
/*  57:    */     }
/*  58:    */     catch (RemoteException e)
/*  59:    */     {
/*  60: 80 */       throw new GenymotionException("Unable to communicate with Genymotion", e);
/*  61:    */     }
/*  62: 83 */     GenymotionManager.checkError(this.genyd);
/*  63:    */     
/*  64:    */ 
/*  65: 86 */     Location targetLocation = new Location("DummyProvider");
/*  66: 87 */     targetLocation.setAltitude(altitude);
/*  67: 88 */     waitForTargetLocation(targetLocation);
/*  68:    */     
/*  69: 90 */     return this;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Gps setBearing(float bearing)
/*  73:    */   {
/*  74:101 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.Gps.setBearing(float)");
/*  75:    */     try
/*  76:    */     {
/*  77:101 */       this.genyd.setGpsBearing(bearing);
/*  78:    */     }
/*  79:    */     catch (RemoteException e)
/*  80:    */     {
/*  81:103 */       throw new GenymotionException("Unable to communicate with Genymotion", e);
/*  82:    */     }
/*  83:106 */     GenymotionManager.checkError(this.genyd);
/*  84:    */     
/*  85:    */ 
/*  86:109 */     Location targetLocation = new Location("DummyProvider");
/*  87:110 */     targetLocation.setBearing(bearing);
/*  88:111 */     waitForTargetLocation(targetLocation);
/*  89:    */     
/*  90:113 */     return this;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Gps setLatitude(double latitude)
/*  94:    */   {
/*  95:124 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.Gps.setLatitude(double)");
/*  96:    */     try
/*  97:    */     {
/*  98:124 */       this.genyd.setGpsLatitude(latitude);
/*  99:    */     }
/* 100:    */     catch (RemoteException e)
/* 101:    */     {
/* 102:126 */       throw new GenymotionException("Unable to communicate with Genymotion", e);
/* 103:    */     }
/* 104:129 */     GenymotionManager.checkError(this.genyd);
/* 105:    */     
/* 106:    */ 
/* 107:132 */     Location targetLocation = new Location("DummyProvider");
/* 108:133 */     targetLocation.setLatitude(latitude);
/* 109:134 */     waitForTargetLocation(targetLocation);
/* 110:    */     
/* 111:136 */     return this;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Gps setLongitude(double longitude)
/* 115:    */   {
/* 116:147 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.Gps.setLongitude(double)");
/* 117:    */     try
/* 118:    */     {
/* 119:147 */       this.genyd.setGpsLongitude(longitude);
/* 120:    */     }
/* 121:    */     catch (RemoteException e)
/* 122:    */     {
/* 123:149 */       throw new GenymotionException("Unable to communicate with Genymotion", e);
/* 124:    */     }
/* 125:152 */     GenymotionManager.checkError(this.genyd);
/* 126:    */     
/* 127:    */ 
/* 128:155 */     Location targetLocation = new Location("DummyProvider");
/* 129:156 */     targetLocation.setLongitude(longitude);
/* 130:157 */     waitForTargetLocation(targetLocation);
/* 131:    */     
/* 132:159 */     return this;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Status getStatus()
/* 136:    */   {
/* 137:172 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.Gps.getStatus()");Status res = Status.DISABLED;
/* 138:    */     try
/* 139:    */     {
/* 140:175 */       res = this.genyd.getGpsStatus() ? Status.ENABLED : Status.DISABLED;
/* 141:    */     }
/* 142:    */     catch (RemoteException e)
/* 143:    */     {
/* 144:177 */       throw new GenymotionException("Unable to communicate with Genymotion", e);
/* 145:    */     }
/* 146:180 */     GenymotionManager.checkError(this.genyd);
/* 147:    */     
/* 148:182 */     return res;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Gps setStatus(Status status)
/* 152:    */   {
/* 153:196 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.Gps.setStatus(com.genymotion.api.Gps$Status)");
/* 154:    */     try
/* 155:    */     {
/* 156:196 */       this.genyd.setGpsStatus(status == Status.ENABLED);
/* 157:    */     }
/* 158:    */     catch (RemoteException e)
/* 159:    */     {
/* 160:198 */       throw new GenymotionException("Unable to communicate with Genymotion", e);
/* 161:    */     }
/* 162:201 */     GenymotionManager.checkError(this.genyd);
/* 163:    */     
/* 164:203 */     return this;
/* 165:    */   }
/* 166:    */   
/* 167:    */   private void waitForTargetLocation(final Location targetLocation)
/* 168:    */   {
/* 169:213 */     Context ctx = GenymotionManager.genymotionManager.context;
/* 170:214 */     LocationManager locMan = (LocationManager)ctx.getSystemService("location");
/* 171:    */     
/* 172:216 */     LocationListener locListener = new LocationListener()
/* 173:    */     {
/* 174:    */       public void onLocationChanged(Location lastLocation)
/* 175:    */       {
/* 176:220 */         if ((targetLocation.getLatitude() != 0.0D) && 
/* 177:221 */           (targetLocation.getLatitude() != lastLocation.getLatitude())) {
/* 178:222 */           return;
/* 179:    */         }
/* 180:225 */         if ((targetLocation.getLongitude() != 0.0D) && 
/* 181:226 */           (targetLocation.getLongitude() != lastLocation.getLongitude())) {
/* 182:227 */           return;
/* 183:    */         }
/* 184:230 */         if ((targetLocation.hasAccuracy()) && (
/* 185:231 */           (!lastLocation.hasAccuracy()) || (targetLocation.getAccuracy() != lastLocation.getAccuracy()))) {
/* 186:232 */           return;
/* 187:    */         }
/* 188:235 */         if ((targetLocation.hasBearing()) && (
/* 189:236 */           (!lastLocation.hasBearing()) || (targetLocation.getBearing() != lastLocation.getBearing()))) {
/* 190:237 */           return;
/* 191:    */         }
/* 192:240 */         if ((targetLocation.hasSpeed()) && (
/* 193:241 */           (!lastLocation.hasSpeed()) || (targetLocation.getSpeed() != lastLocation.getSpeed()))) {
/* 194:242 */           return;
/* 195:    */         }
/* 196:245 */         if ((targetLocation.hasAltitude()) && (
/* 197:246 */           (!lastLocation.hasAltitude()) || (targetLocation.getAltitude() != lastLocation.getAltitude()))) {
/* 198:247 */           return;
/* 199:    */         }
/* 200:253 */         synchronized (Gps.this.mLocationLock)
/* 201:    */         {
/* 202:254 */           Gps.this.mLocationLock.notify();
/* 203:    */         }
/* 204:    */       }
/* 205:    */       
/* 206:    */       public void onStatusChanged(String s, int i, Bundle bundle) {}
/* 207:    */       
/* 208:    */       public void onProviderEnabled(String s) {}
/* 209:    */       
/* 210:    */       public void onProviderDisabled(String s) {}
/* 211:267 */     };
/* 212:268 */     HandlerThread handlerThread = new HandlerThread("waitForTargetLocation");
/* 213:269 */     handlerThread.start();
/* 214:    */     
/* 215:271 */     locMan.requestLocationUpdates("gps", 0L, 0.0F, locListener, handlerThread.getLooper());
/* 216:    */     try
/* 217:    */     {
/* 218:274 */       synchronized (this.mLocationLock)
/* 219:    */       {
/* 220:275 */         this.mLocationLock.wait(10000L);
/* 221:    */       }
/* 222:    */     }
/* 223:    */     catch (InterruptedException ie) {}
/* 224:281 */     locMan.removeUpdates(locListener);
/* 225:283 */     if (Build.VERSION.SDK_INT >= 18) {
/* 226:284 */       handlerThread.quitSafely();
/* 227:    */     } else {
/* 228:286 */       handlerThread.quit();
/* 229:    */     }
/* 230:    */   }
/* 231:    */ }


/* Location:           C:\temp\genymotion-api-1.0.0.jar
 * Qualified Name:     com.genymotion.api.Gps
 * JD-Core Version:    0.7.0.1
 */