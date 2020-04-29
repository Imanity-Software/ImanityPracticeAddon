package spg.lgdev.addon.practice.config;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class FileConfig {

	private File file;

	public File getFile() {
		return this.file;
	}


	private FileConfiguration config;

	public FileConfiguration getConfig() {
		return this.config;
	}

	public FileConfig(JavaPlugin plugin, String fileName) {
		this.file = new File(plugin.getDataFolder(), fileName);

		if (!this.file.exists()) {
			this.file.getParentFile().mkdirs();

			if (plugin.getResource(fileName) == null) {
				try {
					this.file.createNewFile();
				} catch (IOException e) {
					plugin.getLogger().severe("Failed to create new file " + fileName);
				}
			} else {
				plugin.saveResource(fileName, false);
			}
		}

		this.config = YamlConfiguration.loadConfiguration(this.file);
	}

	public void save() {
		try {
			this.config.save(this.file);
		} catch (IOException e) {
			Bukkit.getLogger().severe("Could not save config file " + this.file.toString());
			e.printStackTrace();
		}
	}

}