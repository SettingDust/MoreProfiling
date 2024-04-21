Feel free to open ticket on GitHub for new events

Recommended to use with

- https://modrinth.com/mod/worldgen-profiling that added events for feature and surface
- https://modrinth.com/mod/modernfix that added a jvm flag `-Dmodernfix.debugReloader=true` for enable the resource
  reloader profiling

## Feature

- Option for profiling launch with JFR profiler bundled with Minecraft and Java. Need to enable in config file. Disabled
  by default.
- Option for dumping resource reload profiling. Need to enable debug reloader through ModernFix or any method.
- Option for profiling world loading.
- Option for suppress profiler logging profiling info json.
