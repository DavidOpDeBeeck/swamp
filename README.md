# Swamp

Swamp allows for a fast and easy deployment of all your projects.

## Development

### Configuration

#### REST API Database

```
> cd swamp-api/src/main/resources
> (vi|nano|notepad) application.properties
```

#### Flyway Database

```
> cd .
> (vi|nano|notepad) gradle.properties
```

### Installation

```
> (./gradlew|gradlew.bat) flywayMigrate
> (./gradlew|gradlew.bat) :swamp-api:bootRun -Dversion=0.3-migration.alpha
```

```
> cd swamp/swamp-ui
> gulp start
```

## Production

Swamp is currently not production ready.

## Versions

- [v0.1-migration.alpha](https://github.com/DavidOpDeBeeck/swamp/releases/tag/v0.1-migration.alpha)
- [v0.2-migration.alpha](https://github.com/DavidOpDeBeeck/swamp/releases/tag/v0.2-migration.alpha)

## Scheduling flow

![Scheduling flow](https://raw.github.com/DavidOpDeBeeck/swamp/master/model/scheduler-flow.png)
