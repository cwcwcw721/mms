/*   1:    */ package com.genymotion.genyd;
/*   2:    */ 
/*   3:    */ import android.os.Binder;
/*   4:    */ import android.os.IBinder;
/*   5:    */ import android.os.IInterface;
/*   6:    */ import android.os.Parcel;
/*   7:    */ import android.os.RemoteException;
/*   8:    */ 
/*   9:    */ public abstract interface IGenydService
/*  10:    */   extends IInterface
/*  11:    */ {
/*  12:    */   public abstract int getError()
/*  13:    */     throws RemoteException;
/*  14:    */   
/*  15:    */   public abstract int getTokenValidity()
/*  16:    */     throws RemoteException;
/*  17:    */   
/*  18:    */   public abstract int getBatteryLevel()
/*  19:    */     throws RemoteException;
/*  20:    */   
/*  21:    */   public abstract void setBatteryLevel(int paramInt)
/*  22:    */     throws RemoteException;
/*  23:    */   
/*  24:    */   public abstract int getBatteryMode()
/*  25:    */     throws RemoteException;
/*  26:    */   
/*  27:    */   public abstract void setBatteryMode(int paramInt)
/*  28:    */     throws RemoteException;
/*  29:    */   
/*  30:    */   public abstract int getBatteryStatus()
/*  31:    */     throws RemoteException;
/*  32:    */   
/*  33:    */   public abstract void setBatteryStatus(int paramInt)
/*  34:    */     throws RemoteException;
/*  35:    */   
/*  36:    */   public abstract double getGpsAccuracy()
/*  37:    */     throws RemoteException;
/*  38:    */   
/*  39:    */   public abstract void setGpsAccuracy(double paramDouble)
/*  40:    */     throws RemoteException;
/*  41:    */   
/*  42:    */   public abstract double getGpsAltitude()
/*  43:    */     throws RemoteException;
/*  44:    */   
/*  45:    */   public abstract void setGpsAltitude(double paramDouble)
/*  46:    */     throws RemoteException;
/*  47:    */   
/*  48:    */   public abstract double getGpsBearing()
/*  49:    */     throws RemoteException;
/*  50:    */   
/*  51:    */   public abstract void setGpsBearing(double paramDouble)
/*  52:    */     throws RemoteException;
/*  53:    */   
/*  54:    */   public abstract double getGpsLatitude()
/*  55:    */     throws RemoteException;
/*  56:    */   
/*  57:    */   public abstract void setGpsLatitude(double paramDouble)
/*  58:    */     throws RemoteException;
/*  59:    */   
/*  60:    */   public abstract double getGpsLongitude()
/*  61:    */     throws RemoteException;
/*  62:    */   
/*  63:    */   public abstract void setGpsLongitude(double paramDouble)
/*  64:    */     throws RemoteException;
/*  65:    */   
/*  66:    */   public abstract boolean getGpsStatus()
/*  67:    */     throws RemoteException;
/*  68:    */   
/*  69:    */   public abstract void setGpsStatus(boolean paramBoolean)
/*  70:    */     throws RemoteException;
/*  71:    */   
/*  72:    */   public abstract String getAndroidId()
/*  73:    */     throws RemoteException;
/*  74:    */   
/*  75:    */   public abstract void setAndroidId(String paramString)
/*  76:    */     throws RemoteException;
/*  77:    */   
/*  78:    */   public abstract void setRandomAndroidId()
/*  79:    */     throws RemoteException;
/*  80:    */   
/*  81:    */   public abstract String getDeviceId()
/*  82:    */     throws RemoteException;
/*  83:    */   
/*  84:    */   public abstract void setDeviceId(String paramString)
/*  85:    */     throws RemoteException;
/*  86:    */   
/*  87:    */   public abstract void setRandomDeviceId()
/*  88:    */     throws RemoteException;
/*  89:    */   
/*  90:    */   public abstract double getOrientationAngle()
/*  91:    */     throws RemoteException;
/*  92:    */   
/*  93:    */   public abstract void setOrientationAngle(double paramDouble)
/*  94:    */     throws RemoteException;
/*  95:    */   
/*  96:    */   public static abstract class Stub
/*  97:    */     extends Binder
/*  98:    */     implements IGenydService
/*  99:    */   {
/* 100:    */     private static final String DESCRIPTOR = "com.genymotion.genyd.IGenydService";
/* 101:    */     static final int TRANSACTION_getError = 1;
/* 102:    */     static final int TRANSACTION_getTokenValidity = 2;
/* 103:    */     static final int TRANSACTION_getBatteryLevel = 3;
/* 104:    */     static final int TRANSACTION_setBatteryLevel = 4;
/* 105:    */     static final int TRANSACTION_getBatteryMode = 5;
/* 106:    */     static final int TRANSACTION_setBatteryMode = 6;
/* 107:    */     static final int TRANSACTION_getBatteryStatus = 7;
/* 108:    */     static final int TRANSACTION_setBatteryStatus = 8;
/* 109:    */     static final int TRANSACTION_getGpsAccuracy = 9;
/* 110:    */     static final int TRANSACTION_setGpsAccuracy = 10;
/* 111:    */     static final int TRANSACTION_getGpsAltitude = 11;
/* 112:    */     static final int TRANSACTION_setGpsAltitude = 12;
/* 113:    */     static final int TRANSACTION_getGpsBearing = 13;
/* 114:    */     static final int TRANSACTION_setGpsBearing = 14;
/* 115:    */     static final int TRANSACTION_getGpsLatitude = 15;
/* 116:    */     static final int TRANSACTION_setGpsLatitude = 16;
/* 117:    */     static final int TRANSACTION_getGpsLongitude = 17;
/* 118:    */     static final int TRANSACTION_setGpsLongitude = 18;
/* 119:    */     static final int TRANSACTION_getGpsStatus = 19;
/* 120:    */     static final int TRANSACTION_setGpsStatus = 20;
/* 121:    */     static final int TRANSACTION_getAndroidId = 21;
/* 122:    */     static final int TRANSACTION_setAndroidId = 22;
/* 123:    */     static final int TRANSACTION_setRandomAndroidId = 23;
/* 124:    */     static final int TRANSACTION_getDeviceId = 24;
/* 125:    */     static final int TRANSACTION_setDeviceId = 25;
/* 126:    */     static final int TRANSACTION_setRandomDeviceId = 26;
/* 127:    */     static final int TRANSACTION_getOrientationAngle = 27;
/* 128:    */     static final int TRANSACTION_setOrientationAngle = 28;
/* 129:    */     
/* 130:    */     public Stub()
/* 131:    */     {
/* 132: 15 */       attachInterface(this, "com.genymotion.genyd.IGenydService");
/* 133:    */     }
/* 134:    */     
/* 135:    */     public static IGenydService asInterface(IBinder obj)
/* 136:    */     {
/* 137: 23 */       if (obj == null) {
/* 138: 24 */         return null;
/* 139:    */       }
/* 140: 26 */       IInterface iin = obj.queryLocalInterface("com.genymotion.genyd.IGenydService");
/* 141: 27 */       if ((iin != null) && ((iin instanceof IGenydService))) {
/* 142: 28 */         return (IGenydService)iin;
/* 143:    */       }
/* 144: 30 */       return new Proxy(obj);
/* 145:    */     }
/* 146:    */     
/* 147:    */     public IBinder asBinder()
/* 148:    */     {
/* 149: 34 */       return this;
/* 150:    */     }
/* 151:    */     
/* 152:    */     public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
/* 153:    */       throws RemoteException
/* 154:    */     {
/* 155: 38 */       switch (code)
/* 156:    */       {
/* 157:    */       case 1598968902: 
/* 158: 42 */         reply.writeString("com.genymotion.genyd.IGenydService");
/* 159: 43 */         return true;
/* 160:    */       case 1: 
/* 161: 47 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 162: 48 */         int _result = getError();
/* 163: 49 */         reply.writeNoException();
/* 164: 50 */         reply.writeInt(_result);
/* 165: 51 */         return true;
/* 166:    */       case 2: 
/* 167: 55 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 168: 56 */         int _result = getTokenValidity();
/* 169: 57 */         reply.writeNoException();
/* 170: 58 */         reply.writeInt(_result);
/* 171: 59 */         return true;
/* 172:    */       case 3: 
/* 173: 63 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 174: 64 */         int _result = getBatteryLevel();
/* 175: 65 */         reply.writeNoException();
/* 176: 66 */         reply.writeInt(_result);
/* 177: 67 */         return true;
/* 178:    */       case 4: 
/* 179: 71 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 180:    */         
/* 181: 73 */         int _arg0 = data.readInt();
/* 182: 74 */         setBatteryLevel(_arg0);
/* 183: 75 */         reply.writeNoException();
/* 184: 76 */         return true;
/* 185:    */       case 5: 
/* 186: 80 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 187: 81 */         int _result = getBatteryMode();
/* 188: 82 */         reply.writeNoException();
/* 189: 83 */         reply.writeInt(_result);
/* 190: 84 */         return true;
/* 191:    */       case 6: 
/* 192: 88 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 193:    */         
/* 194: 90 */         int _arg0 = data.readInt();
/* 195: 91 */         setBatteryMode(_arg0);
/* 196: 92 */         reply.writeNoException();
/* 197: 93 */         return true;
/* 198:    */       case 7: 
/* 199: 97 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 200: 98 */         int _result = getBatteryStatus();
/* 201: 99 */         reply.writeNoException();
/* 202:100 */         reply.writeInt(_result);
/* 203:101 */         return true;
/* 204:    */       case 8: 
/* 205:105 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 206:    */         
/* 207:107 */         int _arg0 = data.readInt();
/* 208:108 */         setBatteryStatus(_arg0);
/* 209:109 */         reply.writeNoException();
/* 210:110 */         return true;
/* 211:    */       case 9: 
/* 212:114 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 213:115 */         double _result = getGpsAccuracy();
/* 214:116 */         reply.writeNoException();
/* 215:117 */         reply.writeDouble(_result);
/* 216:118 */         return true;
/* 217:    */       case 10: 
/* 218:122 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 219:    */         
/* 220:124 */         double _arg0 = data.readDouble();
/* 221:125 */         setGpsAccuracy(_arg0);
/* 222:126 */         reply.writeNoException();
/* 223:127 */         return true;
/* 224:    */       case 11: 
/* 225:131 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 226:132 */         double _result = getGpsAltitude();
/* 227:133 */         reply.writeNoException();
/* 228:134 */         reply.writeDouble(_result);
/* 229:135 */         return true;
/* 230:    */       case 12: 
/* 231:139 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 232:    */         
/* 233:141 */         double _arg0 = data.readDouble();
/* 234:142 */         setGpsAltitude(_arg0);
/* 235:143 */         reply.writeNoException();
/* 236:144 */         return true;
/* 237:    */       case 13: 
/* 238:148 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 239:149 */         double _result = getGpsBearing();
/* 240:150 */         reply.writeNoException();
/* 241:151 */         reply.writeDouble(_result);
/* 242:152 */         return true;
/* 243:    */       case 14: 
/* 244:156 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 245:    */         
/* 246:158 */         double _arg0 = data.readDouble();
/* 247:159 */         setGpsBearing(_arg0);
/* 248:160 */         reply.writeNoException();
/* 249:161 */         return true;
/* 250:    */       case 15: 
/* 251:165 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 252:166 */         double _result = getGpsLatitude();
/* 253:167 */         reply.writeNoException();
/* 254:168 */         reply.writeDouble(_result);
/* 255:169 */         return true;
/* 256:    */       case 16: 
/* 257:173 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 258:    */         
/* 259:175 */         double _arg0 = data.readDouble();
/* 260:176 */         setGpsLatitude(_arg0);
/* 261:177 */         reply.writeNoException();
/* 262:178 */         return true;
/* 263:    */       case 17: 
/* 264:182 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 265:183 */         double _result = getGpsLongitude();
/* 266:184 */         reply.writeNoException();
/* 267:185 */         reply.writeDouble(_result);
/* 268:186 */         return true;
/* 269:    */       case 18: 
/* 270:190 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 271:    */         
/* 272:192 */         double _arg0 = data.readDouble();
/* 273:193 */         setGpsLongitude(_arg0);
/* 274:194 */         reply.writeNoException();
/* 275:195 */         return true;
/* 276:    */       case 19: 
/* 277:199 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 278:200 */         boolean _result = getGpsStatus();
/* 279:201 */         reply.writeNoException();
/* 280:202 */         reply.writeInt(_result ? 1 : 0);
/* 281:203 */         return true;
/* 282:    */       case 20: 
/* 283:207 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 284:    */         
/* 285:209 */         boolean _arg0 = 0 != data.readInt();
/* 286:210 */         setGpsStatus(_arg0);
/* 287:211 */         reply.writeNoException();
/* 288:212 */         return true;
/* 289:    */       case 21: 
/* 290:216 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 291:217 */         String _result = getAndroidId();
/* 292:218 */         reply.writeNoException();
/* 293:219 */         reply.writeString(_result);
/* 294:220 */         return true;
/* 295:    */       case 22: 
/* 296:224 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 297:    */         
/* 298:226 */         String _arg0 = data.readString();
/* 299:227 */         setAndroidId(_arg0);
/* 300:228 */         reply.writeNoException();
/* 301:229 */         return true;
/* 302:    */       case 23: 
/* 303:233 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 304:234 */         setRandomAndroidId();
/* 305:235 */         reply.writeNoException();
/* 306:236 */         return true;
/* 307:    */       case 24: 
/* 308:240 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 309:241 */         String _result = getDeviceId();
/* 310:242 */         reply.writeNoException();
/* 311:243 */         reply.writeString(_result);
/* 312:244 */         return true;
/* 313:    */       case 25: 
/* 314:248 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 315:    */         
/* 316:250 */         String _arg0 = data.readString();
/* 317:251 */         setDeviceId(_arg0);
/* 318:252 */         reply.writeNoException();
/* 319:253 */         return true;
/* 320:    */       case 26: 
/* 321:257 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 322:258 */         setRandomDeviceId();
/* 323:259 */         reply.writeNoException();
/* 324:260 */         return true;
/* 325:    */       case 27: 
/* 326:264 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 327:265 */         double _result = getOrientationAngle();
/* 328:266 */         reply.writeNoException();
/* 329:267 */         reply.writeDouble(_result);
/* 330:268 */         return true;
/* 331:    */       case 28: 
/* 332:272 */         data.enforceInterface("com.genymotion.genyd.IGenydService");
/* 333:    */         
/* 334:274 */         double _arg0 = data.readDouble();
/* 335:275 */         setOrientationAngle(_arg0);
/* 336:276 */         reply.writeNoException();
/* 337:277 */         return true;
/* 338:    */       }
/* 339:280 */       return super.onTransact(code, data, reply, flags);
/* 340:    */     }
/* 341:    */     
/* 342:    */     private static class Proxy
/* 343:    */       implements IGenydService
/* 344:    */     {
/* 345:    */       private IBinder mRemote;
/* 346:    */       
/* 347:    */       Proxy(IBinder remote)
/* 348:    */       {
/* 349:287 */         this.mRemote = remote;
/* 350:    */       }
/* 351:    */       
/* 352:    */       public IBinder asBinder()
/* 353:    */       {
/* 354:291 */         return this.mRemote;
/* 355:    */       }
/* 356:    */       
/* 357:    */       public String getInterfaceDescriptor()
/* 358:    */       {
/* 359:295 */         return "com.genymotion.genyd.IGenydService";
/* 360:    */       }
/* 361:    */       
/* 362:    */       public int getError()
/* 363:    */         throws RemoteException
/* 364:    */       {
/* 365:300 */         Parcel _data = Parcel.obtain();
/* 366:301 */         Parcel _reply = Parcel.obtain();
/* 367:    */         int _result;
/* 368:    */         try
/* 369:    */         {
/* 370:304 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 371:305 */           this.mRemote.transact(1, _data, _reply, 0);
/* 372:306 */           _reply.readException();
/* 373:307 */           _result = _reply.readInt();
/* 374:    */         }
/* 375:    */         finally
/* 376:    */         {
/* 377:310 */           _reply.recycle();
/* 378:311 */           _data.recycle();
/* 379:    */         }
/* 380:313 */         return _result;
/* 381:    */       }
/* 382:    */       
/* 383:    */       public int getTokenValidity()
/* 384:    */         throws RemoteException
/* 385:    */       {
/* 386:317 */         Parcel _data = Parcel.obtain();
/* 387:318 */         Parcel _reply = Parcel.obtain();
/* 388:    */         int _result;
/* 389:    */         try
/* 390:    */         {
/* 391:321 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 392:322 */           this.mRemote.transact(2, _data, _reply, 0);
/* 393:323 */           _reply.readException();
/* 394:324 */           _result = _reply.readInt();
/* 395:    */         }
/* 396:    */         finally
/* 397:    */         {
/* 398:327 */           _reply.recycle();
/* 399:328 */           _data.recycle();
/* 400:    */         }
/* 401:330 */         return _result;
/* 402:    */       }
/* 403:    */       
/* 404:    */       public int getBatteryLevel()
/* 405:    */         throws RemoteException
/* 406:    */       {
/* 407:335 */         Parcel _data = Parcel.obtain();
/* 408:336 */         Parcel _reply = Parcel.obtain();
/* 409:    */         int _result;
/* 410:    */         try
/* 411:    */         {
/* 412:339 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 413:340 */           this.mRemote.transact(3, _data, _reply, 0);
/* 414:341 */           _reply.readException();
/* 415:342 */           _result = _reply.readInt();
/* 416:    */         }
/* 417:    */         finally
/* 418:    */         {
/* 419:345 */           _reply.recycle();
/* 420:346 */           _data.recycle();
/* 421:    */         }
/* 422:348 */         return _result;
/* 423:    */       }
/* 424:    */       
/* 425:    */       public void setBatteryLevel(int level)
/* 426:    */         throws RemoteException
/* 427:    */       {
/* 428:352 */         Parcel _data = Parcel.obtain();
/* 429:353 */         Parcel _reply = Parcel.obtain();
/* 430:    */         try
/* 431:    */         {
/* 432:355 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 433:356 */           _data.writeInt(level);
/* 434:357 */           this.mRemote.transact(4, _data, _reply, 0);
/* 435:358 */           _reply.readException();
/* 436:    */         }
/* 437:    */         finally
/* 438:    */         {
/* 439:361 */           _reply.recycle();
/* 440:362 */           _data.recycle();
/* 441:    */         }
/* 442:    */       }
/* 443:    */       
/* 444:    */       public int getBatteryMode()
/* 445:    */         throws RemoteException
/* 446:    */       {
/* 447:367 */         Parcel _data = Parcel.obtain();
/* 448:368 */         Parcel _reply = Parcel.obtain();
/* 449:    */         int _result;
/* 450:    */         try
/* 451:    */         {
/* 452:371 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 453:372 */           this.mRemote.transact(5, _data, _reply, 0);
/* 454:373 */           _reply.readException();
/* 455:374 */           _result = _reply.readInt();
/* 456:    */         }
/* 457:    */         finally
/* 458:    */         {
/* 459:377 */           _reply.recycle();
/* 460:378 */           _data.recycle();
/* 461:    */         }
/* 462:380 */         return _result;
/* 463:    */       }
/* 464:    */       
/* 465:    */       public void setBatteryMode(int mode)
/* 466:    */         throws RemoteException
/* 467:    */       {
/* 468:384 */         Parcel _data = Parcel.obtain();
/* 469:385 */         Parcel _reply = Parcel.obtain();
/* 470:    */         try
/* 471:    */         {
/* 472:387 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 473:388 */           _data.writeInt(mode);
/* 474:389 */           this.mRemote.transact(6, _data, _reply, 0);
/* 475:390 */           _reply.readException();
/* 476:    */         }
/* 477:    */         finally
/* 478:    */         {
/* 479:393 */           _reply.recycle();
/* 480:394 */           _data.recycle();
/* 481:    */         }
/* 482:    */       }
/* 483:    */       
/* 484:    */       public int getBatteryStatus()
/* 485:    */         throws RemoteException
/* 486:    */       {
/* 487:399 */         Parcel _data = Parcel.obtain();
/* 488:400 */         Parcel _reply = Parcel.obtain();
/* 489:    */         int _result;
/* 490:    */         try
/* 491:    */         {
/* 492:403 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 493:404 */           this.mRemote.transact(7, _data, _reply, 0);
/* 494:405 */           _reply.readException();
/* 495:406 */           _result = _reply.readInt();
/* 496:    */         }
/* 497:    */         finally
/* 498:    */         {
/* 499:409 */           _reply.recycle();
/* 500:410 */           _data.recycle();
/* 501:    */         }
/* 502:412 */         return _result;
/* 503:    */       }
/* 504:    */       
/* 505:    */       public void setBatteryStatus(int status)
/* 506:    */         throws RemoteException
/* 507:    */       {
/* 508:416 */         Parcel _data = Parcel.obtain();
/* 509:417 */         Parcel _reply = Parcel.obtain();
/* 510:    */         try
/* 511:    */         {
/* 512:419 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 513:420 */           _data.writeInt(status);
/* 514:421 */           this.mRemote.transact(8, _data, _reply, 0);
/* 515:422 */           _reply.readException();
/* 516:    */         }
/* 517:    */         finally
/* 518:    */         {
/* 519:425 */           _reply.recycle();
/* 520:426 */           _data.recycle();
/* 521:    */         }
/* 522:    */       }
/* 523:    */       
/* 524:    */       public double getGpsAccuracy()
/* 525:    */         throws RemoteException
/* 526:    */       {
/* 527:432 */         Parcel _data = Parcel.obtain();
/* 528:433 */         Parcel _reply = Parcel.obtain();
/* 529:    */         double _result;
/* 530:    */         try
/* 531:    */         {
/* 532:436 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 533:437 */           this.mRemote.transact(9, _data, _reply, 0);
/* 534:438 */           _reply.readException();
/* 535:439 */           _result = _reply.readDouble();
/* 536:    */         }
/* 537:    */         finally
/* 538:    */         {
/* 539:442 */           _reply.recycle();
/* 540:443 */           _data.recycle();
/* 541:    */         }
/* 542:445 */         return _result;
/* 543:    */       }
/* 544:    */       
/* 545:    */       public void setGpsAccuracy(double value)
/* 546:    */         throws RemoteException
/* 547:    */       {
/* 548:449 */         Parcel _data = Parcel.obtain();
/* 549:450 */         Parcel _reply = Parcel.obtain();
/* 550:    */         try
/* 551:    */         {
/* 552:452 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 553:453 */           _data.writeDouble(value);
/* 554:454 */           this.mRemote.transact(10, _data, _reply, 0);
/* 555:455 */           _reply.readException();
/* 556:    */         }
/* 557:    */         finally
/* 558:    */         {
/* 559:458 */           _reply.recycle();
/* 560:459 */           _data.recycle();
/* 561:    */         }
/* 562:    */       }
/* 563:    */       
/* 564:    */       public double getGpsAltitude()
/* 565:    */         throws RemoteException
/* 566:    */       {
/* 567:464 */         Parcel _data = Parcel.obtain();
/* 568:465 */         Parcel _reply = Parcel.obtain();
/* 569:    */         double _result;
/* 570:    */         try
/* 571:    */         {
/* 572:468 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 573:469 */           this.mRemote.transact(11, _data, _reply, 0);
/* 574:470 */           _reply.readException();
/* 575:471 */           _result = _reply.readDouble();
/* 576:    */         }
/* 577:    */         finally
/* 578:    */         {
/* 579:474 */           _reply.recycle();
/* 580:475 */           _data.recycle();
/* 581:    */         }
/* 582:477 */         return _result;
/* 583:    */       }
/* 584:    */       
/* 585:    */       public void setGpsAltitude(double value)
/* 586:    */         throws RemoteException
/* 587:    */       {
/* 588:481 */         Parcel _data = Parcel.obtain();
/* 589:482 */         Parcel _reply = Parcel.obtain();
/* 590:    */         try
/* 591:    */         {
/* 592:484 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 593:485 */           _data.writeDouble(value);
/* 594:486 */           this.mRemote.transact(12, _data, _reply, 0);
/* 595:487 */           _reply.readException();
/* 596:    */         }
/* 597:    */         finally
/* 598:    */         {
/* 599:490 */           _reply.recycle();
/* 600:491 */           _data.recycle();
/* 601:    */         }
/* 602:    */       }
/* 603:    */       
/* 604:    */       public double getGpsBearing()
/* 605:    */         throws RemoteException
/* 606:    */       {
/* 607:496 */         Parcel _data = Parcel.obtain();
/* 608:497 */         Parcel _reply = Parcel.obtain();
/* 609:    */         double _result;
/* 610:    */         try
/* 611:    */         {
/* 612:500 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 613:501 */           this.mRemote.transact(13, _data, _reply, 0);
/* 614:502 */           _reply.readException();
/* 615:503 */           _result = _reply.readDouble();
/* 616:    */         }
/* 617:    */         finally
/* 618:    */         {
/* 619:506 */           _reply.recycle();
/* 620:507 */           _data.recycle();
/* 621:    */         }
/* 622:509 */         return _result;
/* 623:    */       }
/* 624:    */       
/* 625:    */       public void setGpsBearing(double value)
/* 626:    */         throws RemoteException
/* 627:    */       {
/* 628:513 */         Parcel _data = Parcel.obtain();
/* 629:514 */         Parcel _reply = Parcel.obtain();
/* 630:    */         try
/* 631:    */         {
/* 632:516 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 633:517 */           _data.writeDouble(value);
/* 634:518 */           this.mRemote.transact(14, _data, _reply, 0);
/* 635:519 */           _reply.readException();
/* 636:    */         }
/* 637:    */         finally
/* 638:    */         {
/* 639:522 */           _reply.recycle();
/* 640:523 */           _data.recycle();
/* 641:    */         }
/* 642:    */       }
/* 643:    */       
/* 644:    */       public double getGpsLatitude()
/* 645:    */         throws RemoteException
/* 646:    */       {
/* 647:528 */         Parcel _data = Parcel.obtain();
/* 648:529 */         Parcel _reply = Parcel.obtain();
/* 649:    */         double _result;
/* 650:    */         try
/* 651:    */         {
/* 652:532 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 653:533 */           this.mRemote.transact(15, _data, _reply, 0);
/* 654:534 */           _reply.readException();
/* 655:535 */           _result = _reply.readDouble();
/* 656:    */         }
/* 657:    */         finally
/* 658:    */         {
/* 659:538 */           _reply.recycle();
/* 660:539 */           _data.recycle();
/* 661:    */         }
/* 662:541 */         return _result;
/* 663:    */       }
/* 664:    */       
/* 665:    */       public void setGpsLatitude(double value)
/* 666:    */         throws RemoteException
/* 667:    */       {
/* 668:545 */         Parcel _data = Parcel.obtain();
/* 669:546 */         Parcel _reply = Parcel.obtain();
/* 670:    */         try
/* 671:    */         {
/* 672:548 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 673:549 */           _data.writeDouble(value);
/* 674:550 */           this.mRemote.transact(16, _data, _reply, 0);
/* 675:551 */           _reply.readException();
/* 676:    */         }
/* 677:    */         finally
/* 678:    */         {
/* 679:554 */           _reply.recycle();
/* 680:555 */           _data.recycle();
/* 681:    */         }
/* 682:    */       }
/* 683:    */       
/* 684:    */       public double getGpsLongitude()
/* 685:    */         throws RemoteException
/* 686:    */       {
/* 687:560 */         Parcel _data = Parcel.obtain();
/* 688:561 */         Parcel _reply = Parcel.obtain();
/* 689:    */         double _result;
/* 690:    */         try
/* 691:    */         {
/* 692:564 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 693:565 */           this.mRemote.transact(17, _data, _reply, 0);
/* 694:566 */           _reply.readException();
/* 695:567 */           _result = _reply.readDouble();
/* 696:    */         }
/* 697:    */         finally
/* 698:    */         {
/* 699:570 */           _reply.recycle();
/* 700:571 */           _data.recycle();
/* 701:    */         }
/* 702:573 */         return _result;
/* 703:    */       }
/* 704:    */       
/* 705:    */       public void setGpsLongitude(double value)
/* 706:    */         throws RemoteException
/* 707:    */       {
/* 708:577 */         Parcel _data = Parcel.obtain();
/* 709:578 */         Parcel _reply = Parcel.obtain();
/* 710:    */         try
/* 711:    */         {
/* 712:580 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 713:581 */           _data.writeDouble(value);
/* 714:582 */           this.mRemote.transact(18, _data, _reply, 0);
/* 715:583 */           _reply.readException();
/* 716:    */         }
/* 717:    */         finally
/* 718:    */         {
/* 719:586 */           _reply.recycle();
/* 720:587 */           _data.recycle();
/* 721:    */         }
/* 722:    */       }
/* 723:    */       
/* 724:    */       public boolean getGpsStatus()
/* 725:    */         throws RemoteException
/* 726:    */       {
/* 727:592 */         Parcel _data = Parcel.obtain();
/* 728:593 */         Parcel _reply = Parcel.obtain();
/* 729:    */         boolean _result;
/* 730:    */         try
/* 731:    */         {
/* 732:596 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 733:597 */           this.mRemote.transact(19, _data, _reply, 0);
/* 734:598 */           _reply.readException();
/* 735:599 */           _result = 0 != _reply.readInt();
/* 736:    */         }
/* 737:    */         finally
/* 738:    */         {
/* 739:602 */           _reply.recycle();
/* 740:603 */           _data.recycle();
/* 741:    */         }
/* 742:605 */         return _result;
/* 743:    */       }
/* 744:    */       
/* 745:    */       public void setGpsStatus(boolean value)
/* 746:    */         throws RemoteException
/* 747:    */       {
/* 748:609 */         Parcel _data = Parcel.obtain();
/* 749:610 */         Parcel _reply = Parcel.obtain();
/* 750:    */         try
/* 751:    */         {
/* 752:612 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 753:613 */           _data.writeInt(value ? 1 : 0);
/* 754:614 */           this.mRemote.transact(20, _data, _reply, 0);
/* 755:615 */           _reply.readException();
/* 756:    */         }
/* 757:    */         finally
/* 758:    */         {
/* 759:618 */           _reply.recycle();
/* 760:619 */           _data.recycle();
/* 761:    */         }
/* 762:    */       }
/* 763:    */       
/* 764:    */       public String getAndroidId()
/* 765:    */         throws RemoteException
/* 766:    */       {
/* 767:625 */         Parcel _data = Parcel.obtain();
/* 768:626 */         Parcel _reply = Parcel.obtain();
/* 769:    */         String _result;
/* 770:    */         try
/* 771:    */         {
/* 772:629 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 773:630 */           this.mRemote.transact(21, _data, _reply, 0);
/* 774:631 */           _reply.readException();
/* 775:632 */           _result = _reply.readString();
/* 776:    */         }
/* 777:    */         finally
/* 778:    */         {
/* 779:635 */           _reply.recycle();
/* 780:636 */           _data.recycle();
/* 781:    */         }
/* 782:638 */         return _result;
/* 783:    */       }
/* 784:    */       
/* 785:    */       public void setAndroidId(String value)
/* 786:    */         throws RemoteException
/* 787:    */       {
/* 788:642 */         Parcel _data = Parcel.obtain();
/* 789:643 */         Parcel _reply = Parcel.obtain();
/* 790:    */         try
/* 791:    */         {
/* 792:645 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 793:646 */           _data.writeString(value);
/* 794:647 */           this.mRemote.transact(22, _data, _reply, 0);
/* 795:648 */           _reply.readException();
/* 796:    */         }
/* 797:    */         finally
/* 798:    */         {
/* 799:651 */           _reply.recycle();
/* 800:652 */           _data.recycle();
/* 801:    */         }
/* 802:    */       }
/* 803:    */       
/* 804:    */       public void setRandomAndroidId()
/* 805:    */         throws RemoteException
/* 806:    */       {
/* 807:657 */         Parcel _data = Parcel.obtain();
/* 808:658 */         Parcel _reply = Parcel.obtain();
/* 809:    */         try
/* 810:    */         {
/* 811:660 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 812:661 */           this.mRemote.transact(23, _data, _reply, 0);
/* 813:662 */           _reply.readException();
/* 814:    */         }
/* 815:    */         finally
/* 816:    */         {
/* 817:665 */           _reply.recycle();
/* 818:666 */           _data.recycle();
/* 819:    */         }
/* 820:    */       }
/* 821:    */       
/* 822:    */       public String getDeviceId()
/* 823:    */         throws RemoteException
/* 824:    */       {
/* 825:671 */         Parcel _data = Parcel.obtain();
/* 826:672 */         Parcel _reply = Parcel.obtain();
/* 827:    */         String _result;
/* 828:    */         try
/* 829:    */         {
/* 830:675 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 831:676 */           this.mRemote.transact(24, _data, _reply, 0);
/* 832:677 */           _reply.readException();
/* 833:678 */           _result = _reply.readString();
/* 834:    */         }
/* 835:    */         finally
/* 836:    */         {
/* 837:681 */           _reply.recycle();
/* 838:682 */           _data.recycle();
/* 839:    */         }
/* 840:684 */         return _result;
/* 841:    */       }
/* 842:    */       
/* 843:    */       public void setDeviceId(String value)
/* 844:    */         throws RemoteException
/* 845:    */       {
/* 846:688 */         Parcel _data = Parcel.obtain();
/* 847:689 */         Parcel _reply = Parcel.obtain();
/* 848:    */         try
/* 849:    */         {
/* 850:691 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 851:692 */           _data.writeString(value);
/* 852:693 */           this.mRemote.transact(25, _data, _reply, 0);
/* 853:694 */           _reply.readException();
/* 854:    */         }
/* 855:    */         finally
/* 856:    */         {
/* 857:697 */           _reply.recycle();
/* 858:698 */           _data.recycle();
/* 859:    */         }
/* 860:    */       }
/* 861:    */       
/* 862:    */       public void setRandomDeviceId()
/* 863:    */         throws RemoteException
/* 864:    */       {
/* 865:703 */         Parcel _data = Parcel.obtain();
/* 866:704 */         Parcel _reply = Parcel.obtain();
/* 867:    */         try
/* 868:    */         {
/* 869:706 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 870:707 */           this.mRemote.transact(26, _data, _reply, 0);
/* 871:708 */           _reply.readException();
/* 872:    */         }
/* 873:    */         finally
/* 874:    */         {
/* 875:711 */           _reply.recycle();
/* 876:712 */           _data.recycle();
/* 877:    */         }
/* 878:    */       }
/* 879:    */       
/* 880:    */       public double getOrientationAngle()
/* 881:    */         throws RemoteException
/* 882:    */       {
/* 883:718 */         Parcel _data = Parcel.obtain();
/* 884:719 */         Parcel _reply = Parcel.obtain();
/* 885:    */         double _result;
/* 886:    */         try
/* 887:    */         {
/* 888:722 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 889:723 */           this.mRemote.transact(27, _data, _reply, 0);
/* 890:724 */           _reply.readException();
/* 891:725 */           _result = _reply.readDouble();
/* 892:    */         }
/* 893:    */         finally
/* 894:    */         {
/* 895:728 */           _reply.recycle();
/* 896:729 */           _data.recycle();
/* 897:    */         }
/* 898:731 */         return _result;
/* 899:    */       }
/* 900:    */       
/* 901:    */       public void setOrientationAngle(double angle)
/* 902:    */         throws RemoteException
/* 903:    */       {
/* 904:735 */         Parcel _data = Parcel.obtain();
/* 905:736 */         Parcel _reply = Parcel.obtain();
/* 906:    */         try
/* 907:    */         {
/* 908:738 */           _data.writeInterfaceToken("com.genymotion.genyd.IGenydService");
/* 909:739 */           _data.writeDouble(angle);
/* 910:740 */           this.mRemote.transact(28, _data, _reply, 0);
/* 911:741 */           _reply.readException();
/* 912:    */         }
/* 913:    */         finally
/* 914:    */         {
/* 915:744 */           _reply.recycle();
/* 916:745 */           _data.recycle();
/* 917:    */         }
/* 918:    */       }
/* 919:    */     }
/* 920:    */   }
/* 921:    */ }


/* Location:           C:\temp\genymotion-api-1.0.0.jar
 * Qualified Name:     com.genymotion.genyd.IGenydService
 * JD-Core Version:    0.7.0.1
 */