package org.moChiThirst.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    // Lưu tất cả file theo tên (key = "config", "messages", "data/players", ...)
    private static final Map<String, File>              files   = new HashMap<>();
    private static final Map<String, FileConfiguration> configs = new HashMap<>();

    private static Plugin plugin;
    // Đăng ký file mặc định
    public static void setup(Plugin p) {
        plugin = p;

        register("config",   "config.yml");
        register("messages", "messages.yml");
    }

    // Tạo file mới
    //
    // Ví dụ:
    //   ConfigManager.register("quests", "quests.yml");
    //   ConfigManager.register("regions/world", "regions/world.yml");
    public static void register(String key, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);

        // Tạo folder nếu chưa có
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        // Tạo file nếu chưa có
        if (!file.exists()) {
            // Ưu tiên copy từ jar (có default values)
            if (plugin.getResource(fileName) != null) {
                plugin.saveResource(fileName, false);
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    plugin.getLogger().severe("Không thể tạo file: " + fileName);
                }
            }
        }

        files.put(key, file);
        configs.put(key, YamlConfiguration.loadConfiguration(file));
    }

    // Lấy config theo key
    //
    // Ví dụ:
    //   ConfigManager.get("messages").getString("welcome")
    //   ConfigManager.get("data/players").set("Steve.score", 100)
    public static FileConfiguration get(String key) {
        FileConfiguration config = configs.get(key);
        if (config == null) {
            throw new IllegalArgumentException("Không tìm thấy : '" + key + "'. Hãy register trước.");
        }
        return config;
    }

    // ---------------------------------------------------------------
    // Lưu
    // ---------------------------------------------------------------

    /** Lưu một file cụ thể xuống disk. */
    public static void save(String key) {
        File file = files.get(key);
        FileConfiguration config = configs.get(key);
        if (file == null || config == null) return;
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Không thể lưu config: " + key);
        }
    }

    // Lưu tất cả file
    public static void saveAll() {
        for (String key : files.keySet()) {
            save(key);
        }
    }

    // Reload một file cụ thể
    public static void reload(String key) {
        File file = files.get(key);
        if (file == null) return;
        configs.put(key, YamlConfiguration.loadConfiguration(file));
        plugin.getLogger().info("Đã reload: " + file.getName());
    }

    // Reload tất cả file
    public static void reloadAll() {
        for (String key : files.keySet()) {
            reload(key);
        }
        plugin.getLogger().info("Đã reload " + files.size() + " files");
    }

    public static String getPrefix() {
        return get("messages").getString("prefix");
    }

    // Lấy tên các file đã lưu
    public static String[] getFileList() {
        return files.keySet().toArray(new String[0]);
    }
}