package com.fhg.fabric;

import com.fhg.client.FreeLook;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;

public class FasterHappyGhastFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		KeyMappingHelper.registerKeyMapping(FreeLook.KEY);
	}
}
