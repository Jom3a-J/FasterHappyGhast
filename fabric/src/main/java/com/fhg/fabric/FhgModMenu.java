package com.fhg.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

/**
 * Adds the config button to the Mod Menu entry. Only loaded when Mod Menu is installed, so Mod Menu
 * stays an optional dependency.
 */
public class FhgModMenu implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return FhgConfigScreen::create;
	}
}
