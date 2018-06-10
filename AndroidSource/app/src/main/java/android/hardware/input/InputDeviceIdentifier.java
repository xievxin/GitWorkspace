/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.hardware.input;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Wrapper for passing identifying information for input devices.
 *
 * @hide
 */
public final class InputDeviceIdentifier implements Parcelable {
    private final String mDescriptor;
    private final int mVendorId;
    private final int mProductId;

    public InputDeviceIdentifier(String descriptor, int vendorId, int productId) {
        this.mDescriptor = descriptor;
        this.mVendorId = vendorId;
        this.mProductId = productId;
    }

    private InputDeviceIdentifier(Parcel src) {
        mDescriptor = src.readString();
        mVendorId = src.readInt();
        mProductId = src.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mDescriptor);
        dest.writeInt(mVendorId);
        dest.writeInt(mProductId);
    }

    public String getDescriptor() {
        return mDescriptor;
    }

    public int getVendorId() {
        return mVendorId;
    }

    public int getProductId() {
        return mProductId;
    }

    public static final Creator<InputDeviceIdentifier> CREATOR =
            new Creator<InputDeviceIdentifier>() {

        @Override
        public InputDeviceIdentifier createFromParcel(Parcel source) {
            return new InputDeviceIdentifier(source);
        }

        @Override
        public InputDeviceIdentifier[] newArray(int size) {
            return new InputDeviceIdentifier[size];
        }

    };
}