/*   1:    */ package com.genymotion.api;
/*   2:    */ 
/*   3:    */ import android.content.Context;
/*   4:    */ import android.os.RemoteException;
/*   5:    */ import android.util.Log;
/*   6:    */ import com.genymotion.genyd.IGenydService;
/*   7:    */ import java.lang.reflect.InvocationTargetException;
/*   8:    */ import java.lang.reflect.Method;
/*   9:    */ 
/*  10:    */ public final class GenymotionManager
/*  11:    */ {
/*  12:    */   static final String TAG = "GenymotionApi";
/*  13:    */   static final int TIMEOUT = 10000;
/*  14: 22 */   static GenymotionManager genymotionManager = null;
/*  15: 23 */   Context context = null;
/*  16: 24 */   private static Battery battery = null;
/*  17: 25 */   private static Gps gps = null;
/*  18: 26 */   private static Id id = null;
/*  19: 27 */   private static Orientation orientation = null;
/*  20: 28 */   private static Radio radio = null;
/*  21:    */   private String version;
/*  22: 30 */   private IGenydService genyd = null;
/*  23:    */   
/*  24:    */   private GenymotionManager(Context pContext)
/*  25:    */   {
/*  26: 35 */     if (!isGenymotionDevice()) {
/*  27: 36 */       throw new NotGenymotionDeviceException();
/*  28:    */     }
/*  29: 39 */     if (pContext != null)
/*  30:    */     {
/*  31: 40 */       this.context = pContext;
/*  32: 41 */       this.genyd = ((IGenydService)this.context.getSystemService("Genyd"));
/*  33: 43 */       if (this.genyd == null) {
/*  34: 44 */         throw new GenymotionException("This version of Genymotion is not compatible with the Genymotion API. Please upgrade your Genymotion Device.");
/*  35:    */       }
/*  36: 48 */       checkToken(this.genyd);
/*  37:    */     }
/*  38:    */     else
/*  39:    */     {
/*  40: 51 */       throw new GenymotionException("You must provide a valid Android Context.");
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static GenymotionManager getGenymotionManager(Context context)
/*  45:    */     throws GenymotionException
/*  46:    */   {
/*  47: 63 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.GenymotionManager.getGenymotionManager(android.content.Context)");
/*  48: 63 */     if (genymotionManager == null)
/*  49:    */     {
/*  50: 64 */       if (context.getApplicationContext() != null) {
/*  51: 65 */         genymotionManager = new GenymotionManager(context.getApplicationContext());
/*  52:    */       } else {
/*  53: 67 */         genymotionManager = new GenymotionManager(context);
/*  54:    */       }
/*  55: 69 */       genymotionManager.version = getGenymotionVersion();
/*  56:    */     }
/*  57: 71 */     return genymotionManager;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Battery getBattery()
/*  61:    */   {
/*  62: 80 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.GenymotionManager.getBattery()");
/*  63: 80 */     if (battery == null) {
/*  64: 81 */       battery = new Battery(this.genyd);
/*  65:    */     }
/*  66: 83 */     return battery;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Gps getGps()
/*  70:    */   {
/*  71: 92 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.GenymotionManager.getGps()");
/*  72: 92 */     if (gps == null) {
/*  73: 93 */       gps = new Gps(this.genyd);
/*  74:    */     }
/*  75: 95 */     return gps;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Id getId()
/*  79:    */   {
/*  80:104 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.GenymotionManager.getId()");
/*  81:104 */     if (id == null) {
/*  82:105 */       id = new Id(this.genyd);
/*  83:    */     }
/*  84:107 */     return id;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Orientation getOrientation()
/*  88:    */   {
/*  89:116 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.GenymotionManager.getOrientation()");
/*  90:116 */     if (orientation == null) {
/*  91:117 */       orientation = new Orientation(this.genyd);
/*  92:    */     }
/*  93:119 */     return orientation;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Radio getRadio()
/*  97:    */   {
/*  98:128 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.GenymotionManager.getRadio()");
/*  99:128 */     if (radio == null) {
/* 100:129 */       radio = new Radio(this.genyd);
/* 101:    */     }
/* 102:131 */     return radio;
/* 103:    */   }
/* 104:    */   
/* 105:    */   static void checkApi(String apk, String methodSign)
/* 106:    */   {
/* 107:135 */     if (genymotionManager == null) {
/* 108:136 */       return;
/* 109:    */     }
/* 110:139 */     if (compareVersion(apk, genymotionManager.version) > 0) {
/* 111:140 */       throw new MethodUnavailableException(methodSign + " doesn't exists on this version of Genymotion: " + genymotionManager.version + ". You need at least the version: " + apk);
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static boolean isGenymotionDevice()
/* 116:    */   {
/* 117:152 */     GenymotionManager.checkApi("2.2", "com.genymotion.api.GenymotionManager.isGenymotionDevice()");return !getGenymotionVersion().isEmpty();
/* 118:    */   }
/* 119:    */   
/* 120:    */   static int compareVersion(String v1, String v2)
/* 121:    */   {
/* 122:166 */     String[] vals1 = v1.split("\\.");
/* 123:167 */     String[] vals2 = v2.split("\\.");
/* 124:    */     
/* 125:169 */     int i = 0;
/* 126:170 */     int m = Math.max(vals1.length, vals2.length);
/* 127:173 */     while (i < m)
/* 128:    */     {
/* 129:174 */       int d1 = i < vals1.length ? Integer.valueOf(vals1[i]).intValue() : 0;
/* 130:175 */       int d2 = i < vals2.length ? Integer.valueOf(vals2[i]).intValue() : 0;
/* 131:176 */       int diff = Integer.signum(d1 - d2);
/* 132:178 */       if (diff != 0) {
/* 133:179 */         return diff;
/* 134:    */       }
/* 135:182 */       i++;
/* 136:    */     }
/* 137:185 */     return 0;
/* 138:    */   }
/* 139:    */   
/* 140:    */   static enum ErrorCode
/* 141:    */   {
/* 142:195 */     NoError,  TokenError,  GpsNotEnableError,  InvalidValueError;
/* 143:    */     
/* 144:    */     private ErrorCode() {}
/* 145:    */   }
/* 146:    */   
/* 147:    */   static enum TokenValidity
/* 148:    */   {
/* 149:220 */     Unknown,  Valid,  NoSignature,  InvalidSignature,  InvalidData,  InvalidUuid,  TokenExpired,  LicenseExpired,  EmptyToken,  TokenNotSet;
/* 150:    */     
/* 151:    */     private TokenValidity() {}
/* 152:    */   }
/* 153:    */   
/* 154:    */   static void checkToken(IGenydService service)
/* 155:    */   {
/* 156:269 */     int token = 0;
/* 157:    */     try
/* 158:    */     {
/* 159:272 */       token = service.getTokenValidity();
/* 160:    */     }
/* 161:    */     catch (RemoteException e)
/* 162:    */     {
/* 163:274 */       throw new GenymotionException(e.getMessage(), e.getCause());
/* 164:    */     }
/* 165:277 */     String pleaseRestart = " Please run Genymotion and connect to Genymotion Cloud to force a license check.";
/* 166:278 */     switch (1.$SwitchMap$com$genymotion$api$GenymotionManager$TokenValidity[TokenValidity.values()[token].ordinal()])
/* 167:    */     {
/* 168:    */     case 1: 
/* 169:    */       break;
/* 170:    */     case 2: 
/* 171:282 */       throw new GenymotionException("License activation has expired." + pleaseRestart);
/* 172:    */     case 3: 
/* 173:284 */       throw new GenymotionException("Last license check reported that license has expired." + pleaseRestart);
/* 174:    */     case 4: 
/* 175:286 */       throw new GenymotionException("License has not been verified using Genymotion Cloud." + pleaseRestart);
/* 176:    */     case 5: 
/* 177:288 */       throw new GenymotionException("Unable to connect to Genymotion via Genymotion API.");
/* 178:    */     case 6: 
/* 179:290 */       throw new GenymotionException("This software requires a valid Genymotion license.");
/* 180:    */     case 7: 
/* 181:    */     case 8: 
/* 182:    */     case 9: 
/* 183:    */     case 10: 
/* 184:    */     default: 
/* 185:296 */       throw new GenymotionException("License cannot be verified because check data are invalid." + pleaseRestart);
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   static void checkError(IGenydService service)
/* 190:    */   {
/* 191:301 */     int errCode = 0;
/* 192:    */     try
/* 193:    */     {
/* 194:304 */       errCode = service.getError();
/* 195:    */     }
/* 196:    */     catch (RemoteException e)
/* 197:    */     {
/* 198:306 */       throw new GenymotionException(e.getMessage(), e.getCause());
/* 199:    */     }
/* 200:309 */     switch (1.$SwitchMap$com$genymotion$api$GenymotionManager$ErrorCode[ErrorCode.values()[errCode].ordinal()])
/* 201:    */     {
/* 202:    */     case 1: 
/* 203:    */       break;
/* 204:    */     case 2: 
/* 205:313 */       throw new GenymotionException("Gps Not Enabled");
/* 206:    */     case 3: 
/* 207:315 */       throw new GenymotionException("Invalid Value");
/* 208:    */     case 4: 
/* 209:317 */       checkToken(service);
/* 210:    */     }
/* 211:    */   }
/* 212:    */   
/* 213:    */   static String getGenymotionVersion()
/* 214:    */   {
/* 215:322 */     String version = "";
/* 216:    */     try
/* 217:    */     {
/* 218:325 */       Class systemProperties = null;
/* 219:326 */       systemProperties = Class.forName("android.os.SystemProperties");
/* 220:328 */       if (systemProperties != null)
/* 221:    */       {
/* 222:330 */         Method getProperty = systemProperties.getDeclaredMethod("get", new Class[] { String.class });
/* 223:331 */         if (getProperty != null) {
/* 224:333 */           version = (String)getProperty.invoke(null, new Object[] { "ro.genymotion.version" });
/* 225:    */         }
/* 226:    */       }
/* 227:    */     }
/* 228:    */     catch (ClassNotFoundException e)
/* 229:    */     {
/* 230:337 */       Log.e("GenymotionApi", e.getMessage(), e);
/* 231:    */     }
/* 232:    */     catch (NoSuchMethodException e)
/* 233:    */     {
/* 234:339 */       Log.e("GenymotionApi", e.getMessage(), e);
/* 235:    */     }
/* 236:    */     catch (IllegalAccessException e)
/* 237:    */     {
/* 238:341 */       Log.e("GenymotionApi", e.getMessage(), e);
/* 239:    */     }
/* 240:    */     catch (IllegalArgumentException e)
/* 241:    */     {
/* 242:343 */       Log.e("GenymotionApi", e.getMessage(), e);
/* 243:    */     }
/* 244:    */     catch (InvocationTargetException e)
/* 245:    */     {
/* 246:345 */       Log.e("GenymotionApi", e.getMessage(), e);
/* 247:    */     }
/* 248:348 */     return version;
/* 249:    */   }
/* 250:    */ }


/* Location:           C:\temp\genymotion-api-1.0.0.jar
 * Qualified Name:     com.genymotion.api.GenymotionManager
 * JD-Core Version:    0.7.0.1
 */