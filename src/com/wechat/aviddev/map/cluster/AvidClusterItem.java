package com.wechat.aviddev.map.cluster;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class AvidClusterItem  implements ClusterItem {

	private final LatLng mPosition;

    public AvidClusterItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }
	
	@Override
	public LatLng getPosition() {
		return mPosition;
	}

}
