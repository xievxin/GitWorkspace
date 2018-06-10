/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\GitWorkspace\\TestApp\\app\\src\\main\\aidl\\com\\xx\\process\\IChuangke.aidl
 */
package com.xx.process;
// Declare any non-default types here with import statements

public interface IChuangke extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.xx.process.IChuangke
{
private static final java.lang.String DESCRIPTOR = "com.xx.process.IChuangke";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.xx.process.IChuangke interface,
 * generating a proxy if needed.
 */
public static com.xx.process.IChuangke asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.xx.process.IChuangke))) {
return ((com.xx.process.IChuangke)iin);
}
return new com.xx.process.IChuangke.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.xx.process.IChuangke
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
}
}
}
