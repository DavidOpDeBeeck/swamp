# Swamp
Swamp allows for a fast and easy deployment of all your projects.

## Development

#### Configuration
```
> cd swamp-api/src/main/resources
> (vi|nano|notepad) application.properties
```

#### Installation
```
> (./gradlew|gradlew.bat) flywayMigrate
> (./gradlew|gradlew.bat) :swamp-api:bootRun
```
```
> cd swamp/swamp-ui
> gulp start
```

## Production

Swamp is currently not production ready.

## Versions

- [v0.1-alpha](https://github.com/DavidOpDeBeeck/swamp/releases/tag/v0.1-alpha)
- [v0.2-alpha](https://github.com/DavidOpDeBeeck/swamp/releases/tag/v0.2-alpha)

## Scheduling flow

![Scheduling flow](https://raw.github.com/DavidOpDeBeeck/swamp/master/model/scheduler-flow.png)
