# PluginHider

A simple Minecraft plugin that allows server administrators to hide the plugin list from regular users.

## Features

- Hide plugin list from regular users
- Customizable permission system
- Toggle plugin visibility
- Custom messages support
- Easy configuration

## Requirements

- Minecraft Server 1.13 or higher
- Paper/Spigot server
- Java 17 or higher

## Installation

1. Download the latest release
2. Place the jar file in your server's `plugins` folder
3. Restart your server

## Commands

- `/pluginhider reload` - Reload the configuration
- `/pluginhider toggle` - Toggle plugin hiding
- `/pluginhider status` - Show current status
- Alias: `/plhide`

## Permissions

- `pluginhider.admin` - Access to PluginHider commands (default: op)
- `pluginhider.see` - Permission to see the plugin list (default: op)

## Configuration

The plugin creates a `config.yml` file with the following options:

```yaml
enabled: true
messages:
  custom-enabled: false
  plugins-list: "&fPlugins (0): "
```

## Building from Source

1. Clone this repository
2. Run `./gradlew build` (Linux/Mac) or `gradlew.bat build` (Windows)
3. Find the compiled jar in `build/libs/` directory

## Contributing

Feel free to submit issues and pull requests!

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Author

- **iaMJ** - *Initial work*

## Support

If you encounter any issues or have questions, please open an issue on GitHub. 