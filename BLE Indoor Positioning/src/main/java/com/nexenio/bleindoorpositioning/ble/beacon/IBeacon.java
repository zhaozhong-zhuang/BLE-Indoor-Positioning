package com.nexenio.bleindoorpositioning.ble.beacon;

import com.nexenio.bleindoorpositioning.ble.advertising.AdvertisingPacket;
import com.nexenio.bleindoorpositioning.ble.advertising.AdvertisingPacketUtil;
import com.nexenio.bleindoorpositioning.ble.advertising.IBeaconAdvertisingPacket;
import com.nexenio.bleindoorpositioning.location.provider.IBeaconLocationProvider;
import com.nexenio.bleindoorpositioning.location.provider.LocationProvider;

import java.math.BigInteger;
import java.util.UUID;

/**
 * Created by steppschuh on 15.11.17.
 */

public class IBeacon extends Beacon {

    public static final int CALIBRATION_DISTANCE_DEFAULT = 1;

    private UUID proximityUuid;
    private int major;
    private int minor;

    public IBeacon() {
        this.calibratedDistance = CALIBRATION_DISTANCE_DEFAULT;
    }

    @Override
    public LocationProvider createLocationProvider() {
        return new IBeaconLocationProvider(this);
    }

    @Override
    public void applyPropertiesFromAdvertisingPacket(AdvertisingPacket advertisingPacket) {
        super.applyPropertiesFromAdvertisingPacket(advertisingPacket);
        if (advertisingPacket instanceof IBeaconAdvertisingPacket) {
            IBeaconAdvertisingPacket iBeaconAdvertisingPacket = (IBeaconAdvertisingPacket) advertisingPacket;
            proximityUuid = AdvertisingPacketUtil.toUuid(iBeaconAdvertisingPacket.getProximityUuidBytes());
            major = new BigInteger(iBeaconAdvertisingPacket.getMajorBytes()).intValue();
            minor = new BigInteger(iBeaconAdvertisingPacket.getMinorBytes()).intValue();
            calibratedRssi = iBeaconAdvertisingPacket.getMeasuredPowerByte();
        }
    }

    /*
        Getter & Setter
     */

    public UUID getProximityUuid() {
        return proximityUuid;
    }

    public void setProximityUuid(UUID proximityUuid) {
        this.proximityUuid = proximityUuid;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

}